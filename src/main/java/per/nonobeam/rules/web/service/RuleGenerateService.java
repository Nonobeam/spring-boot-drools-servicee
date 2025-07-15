package per.nonobeam.rules.web.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.web.model.core.DataType;
import per.nonobeam.rules.web.model.core.Operator;
import per.nonobeam.rules.web.model.core.RuleCondition;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;
import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.repository.RuleConditionGroupRepository;
import per.nonobeam.rules.web.repository.RuleConditionRepository;
import per.nonobeam.rules.web.repository.RuleTemplateVersionRepository;

@Service
@RequiredArgsConstructor
public class RuleGenerateService {

  private final RuleConditionRepository ruleConditionRepository;
  private final RuleTemplateVersionRepository ruleTemplateVersionRepository;
  private final RuleConditionGroupRepository ruleConditionGroupRepository;

  public String generateRule(RuleDefinition rule) {
    String template = rule.getTemplateVersion().getContent();
    String conditions = generateConditions(rule.getId());

    return template
            .replace("${name}", rule.getName())
            .replace("${priority}", String.valueOf(rule.getPriority()))
            .replace("${conditions}", conditions);
  }

  public String generateConditions(UUID ruleDefinitionId) {
    List<RuleConditionGroup> groups =
        ruleConditionGroupRepository.findByRuleDefinitionId(ruleDefinitionId);
    Map<UUID, List<RuleCondition>> conditionsMap =
        ruleConditionRepository
            .findByGroupIds(groups.stream().map(RuleConditionGroup::getId).toList())
            .stream()
            .collect(Collectors.groupingBy(c -> c.getGroup().getId()));

    return buildGroupCondition(groups, conditionsMap, null);
  }

  /**
   * Builds a condition string for a group of rule conditions based on their hierarchy and parent
   * group ID. This method recursively processes a list of `RuleConditionGroup` objects and their
   * associated conditions to construct a logical condition string. It filters groups by their
   * parent group ID, sorts them by their group order, and combines their conditions and nested
   * group conditions using logical operators.
   *
   * @param groups A list of `RuleConditionGroup` objects representing the condition groups.
   * @param conditionsMap A map where the key is the group ID and the value is a list of
   *     `RuleCondition` objects associated with that group.
   * @param parentGroupId The UUID of the parent group to filter the groups by.
   * @return A string representing the combined conditions for the groups and their nested
   *     conditions.
   */
  private String buildGroupCondition(
      List<RuleConditionGroup> groups,
      Map<UUID, List<RuleCondition>> conditionsMap,
      UUID parentGroupId) {
    return groups.stream()
        .filter(
            g -> {
              UUID currentParentId = g.getParentGroup() != null ? g.getParentGroup().getId() : null;
              return Objects.equals(currentParentId, parentGroupId);
            })
        .sorted(Comparator.comparing(RuleConditionGroup::getGroupOrder))
        .map(
            group -> {
              String inner = buildGroupCondition(groups, conditionsMap, group.getId());
              String conditions =
                  conditionsMap.getOrDefault(group.getId(), List.of()).stream()
                      .sorted(Comparator.comparing(RuleCondition::getConditionOrder))
                      .map(this::convertCondition)
                      .collect(Collectors.joining(" && "));

              return Stream.of(inner, conditions)
                  .filter(s -> s != null && !s.isBlank())
                  .collect(Collectors.joining(" && "));
            })
        .collect(Collectors.joining(" " + getGroupOperator(groups, parentGroupId) + " "));
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

  /**
   * Retrieves the operator for a group based on its parent group ID. This method filters the
   * provided list of `RuleConditionGroup` objects to find the first group whose parent group
   * matches the given `parentGroupId`. If a matching group is found, its operator is returned as a
   * string. If no matching group is found, the default operator "AND" is returned.
   *
   * @param groups A list of `RuleConditionGroup` objects to search through.
   * @param parentGroupId The UUID of the parent group to match against.
   * @return The operator of the matching group as a string, or "AND" if no match is found.
   */
  private String getGroupOperator(List<RuleConditionGroup> groups, UUID parentGroupId) {
    return groups.stream()
        .filter(
            g -> {
              UUID currentParentId = g.getParentGroup() != null ? g.getParentGroup().getId() : null;
              return Objects.equals(currentParentId, parentGroupId);
            })
        .findFirst()
        .map(g -> g.getOperator().name())
        .orElse(Operator.AND.name());
  }
}
