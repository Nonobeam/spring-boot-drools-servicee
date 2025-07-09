package per.nonobeam.rules.web.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.config.KieBaseCache;
import per.nonobeam.rules.web.model.core.ConditionOperator;
import per.nonobeam.rules.web.model.core.DataType;
import per.nonobeam.rules.web.model.core.Operator;
import per.nonobeam.rules.web.model.core.RuleCondition;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;
import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.model.core.RuleStatus;
import per.nonobeam.rules.web.model.core.RuleTemplateVersion;
import per.nonobeam.rules.web.model.request.ConditionGroupRequest;
import per.nonobeam.rules.web.model.request.ConditionRequest;
import per.nonobeam.rules.web.model.request.CreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.repository.RuleConditionGroupRepository;
import per.nonobeam.rules.web.repository.RuleConditionRepository;
import per.nonobeam.rules.web.repository.RuleDefinitionRepository;
import per.nonobeam.rules.web.repository.RuleTemplateVersionRepository;

@Service
@RequiredArgsConstructor
public class RuleDefinitionService {

  private final KieBaseCache kieBaseCache;
  private final RedisService redisService;
  private final RuleConditionService ruleConditionService;
  private final RuleConditionRepository conditionRepository;
  private final RuleDefinitionRepository ruleDefinitionRepository;
  private final RuleConditionGroupRepository conditionGroupRepository;
  private final RuleTemplateVersionRepository templateVersionRepository;

  public RuleDefinitionResponse create(CreateRuleDefinitionRequest request) {
    RuleTemplateVersion version =
        templateVersionRepository.findById(request.getTemplateVersionId()).orElseThrow();

    RuleDefinition ruleDefinition =
        RuleDefinition.builder()
            .id(UUID.randomUUID())
            .externalId(request.getExternalId())
            .name(request.getName())
            .type(request.getType())
            .action(request.getAction())
            .priority(request.getPriority())
            .effectiveStart(request.getEffectiveStart())
            .effectiveEnd(request.getEffectiveEnd())
            .status(RuleStatus.ACTIVE)
            .templateVersion(version)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    ruleDefinitionRepository.save(ruleDefinition);

    if (request.getConditionGroups() != null) {
      for (ConditionGroupRequest groupRequest : request.getConditionGroups()) {
        saveGroupRecursive(groupRequest, null, ruleDefinition);
      }
    }

    return RuleDefinitionResponse.mapToResponse(ruleDefinition);
  }

  private void saveGroupRecursive(
      ConditionGroupRequest request, RuleConditionGroup parent, RuleDefinition rule) {
    RuleConditionGroup group =
        RuleConditionGroup.builder()
            .id(UUID.randomUUID())
            .ruleDefinition(rule)
            .parentGroup(parent)
            .operator(Operator.valueOf(request.getOperator()))
            .groupOrder(request.getGroupOrder())
            .build();

    conditionGroupRepository.save(group);

    if (request.getConditions() != null) {
      for (ConditionRequest cond : request.getConditions()) {
        RuleCondition condition =
            RuleCondition.builder()
                .id(UUID.randomUUID())
                .group(group)
                .leftOperand(cond.getLeftOperand())
                .operator(ConditionOperator.valueOf(cond.getOperator()))
                .rightOperand(cond.getRightOperand())
                .dataType(DataType.valueOf(cond.getDataType()))
                .conditionOrder(cond.getConditionOrder())
                .build();
        conditionRepository.save(condition);
      }
    }

    if (request.getSubGroups() != null) {
      for (ConditionGroupRequest subGroup : request.getSubGroups()) {
        saveGroupRecursive(subGroup, group, rule);
      }
    }
  }

  public void cacheRuleDefinition(RuleDefinition rule) {
    String script = ruleConditionService.generateConditions(rule.getId());
    redisService.cacheEligibilityRuleScript(rule.getExternalId(), script);

    KieBase base = new KieHelper().addContent(script, ResourceType.DRL).build();
    kieBaseCache.put(rule.getExternalId(), base);
  }
}
