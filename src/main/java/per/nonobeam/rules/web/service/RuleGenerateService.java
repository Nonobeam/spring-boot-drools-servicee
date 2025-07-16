package per.nonobeam.rules.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.web.model.core.DataType;
import per.nonobeam.rules.web.model.core.Operator;
import per.nonobeam.rules.web.model.core.RuleCondition;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;
import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.repository.RuleConditionGroupRepository;
import per.nonobeam.rules.web.repository.RuleConditionRepository;

@Service
@RequiredArgsConstructor
public class RuleGenerateService implements RuleGenerate {

  private final RuleConditionRepository ruleConditionRepository;
  private final RuleConditionGroupRepository ruleConditionGroupRepository;

  public String generateRule(RuleDefinition rule) {
    String template = rule.getTemplateVersion().getContent();
    String conditions = generateConditions(rule.getId());

    return template
        .replace("${name}", rule.getName())
        .replace("${priority}", String.valueOf(rule.getPriority()))
        .replace("${conditions}", conditions);
  }

  private String generateConditions(UUID ruleDefinitionId) {
    List<RuleConditionGroup> groups =
        ruleConditionGroupRepository.findByRuleDefinitionId(ruleDefinitionId);
    Map<UUID, List<RuleCondition>> conditionsMap =
        ruleConditionRepository
            .findByGroupIds(groups.stream().map(RuleConditionGroup::getId).toList())
            .stream()
            .collect(Collectors.groupingBy(c -> c.getGroup().getId()));

    return buildGroupCondition(groups, conditionsMap, null);
  }

  private String buildGroupCondition(
      List<RuleConditionGroup> groups,
      Map<UUID, List<RuleCondition>> conditionsMap,
      UUID parentGroupId) {
    String parentOp = getGroupOperator(groups, parentGroupId);

    return groups.stream()
        .filter(
            g -> {
              UUID currentParentId = g.getParentGroup() != null ? g.getParentGroup().getId() : null;
              return Objects.equals(currentParentId, parentGroupId);
            })
        .map(
            group -> {
              String op = group.getOperator().getSymbol();

              var conditionExprs =
                  conditionsMap.getOrDefault(group.getId(), List.of()).stream()
                      .map(this::convertCondition)
                      .toList();

              String childExpr = buildGroupCondition(groups, conditionsMap, group.getId());

              List<String> all = new ArrayList<>(conditionExprs);
              if (!childExpr.isBlank()) all.add(childExpr);

              if (all.isEmpty()) return "";
              return "(" + String.join(" " + op + " ", all) + ")";
            })
        .filter(s -> !s.isBlank())
        .collect(Collectors.joining(" " + parentOp + " "));
  }

  private String convertCondition(RuleCondition condition) {
    String left = convertOperand(condition.getLeftOperand(), condition.getDataType(), true);
    String right = convertOperand(condition.getRightOperand(), condition.getDataType(), false);
    return left + " " + condition.getOperator().getSymbol() + " " + right;
  }

  private String convertOperand(String operand, DataType dataType, boolean isLeft) {
    return switch (dataType) {
      case STR -> isLeft ? attribute(operand) : "\"" + operand + "\"";
      case NUM -> isLeft ? " ((Number) " + attribute(operand) + ").longValue()" : operand + "L";
      case BOOL, VAR -> isLeft ? attribute(operand) : operand;
    };
  }

  private String attribute(String key) {
    return "attributes[\"" + key + "\"]";
  }

  private String getGroupOperator(List<RuleConditionGroup> groups, UUID parentGroupId) {
    return groups.stream()
        .filter(
            g -> {
              UUID currentParentId = g.getParentGroup() != null ? g.getParentGroup().getId() : null;
              return Objects.equals(currentParentId, parentGroupId);
            })
        .findFirst()
        .map(g -> g.getOperator().name())
        .orElse(Operator.AND.getSymbol());
  }
}
