package per.nonobeam.rules.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
import per.nonobeam.rules.web.model.response.ConditionGroupResponse;
import per.nonobeam.rules.web.model.response.RuleConditionResponse;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.model.response.RuleListResponse;
import per.nonobeam.rules.web.repository.RuleConditionGroupRepository;
import per.nonobeam.rules.web.repository.RuleConditionRepository;
import per.nonobeam.rules.web.repository.RuleDefinitionRepository;
import per.nonobeam.rules.web.repository.RuleTemplateVersionRepository;

@Service
@RequiredArgsConstructor
public class RuleDefinitionService {

  private final RedisService redisService;
  private final RuleGenerateService ruleGenerateService;
  private final RuleConditionRepository conditionRepository;
  private final RuleDefinitionRepository ruleDefinitionRepository;
  private final RuleConditionGroupRepository conditionGroupRepository;
  private final RuleTemplateVersionRepository templateVersionRepository;

  public RuleDefinitionResponse create(CreateRuleDefinitionRequest request) {
    RuleDefinition ruleDefinition = saveRuleDefinitionEntity(request);
    List<RuleConditionGroup> savedGroups = saveConditionGroupsRecursive(request, ruleDefinition);
    cacheRuleDefinition(ruleDefinition);
    List<ConditionGroupResponse> groupResponses =
        savedGroups.stream().map(ConditionGroupResponse::from).toList();

    return RuleDefinitionResponse.mapToResponse(ruleDefinition, groupResponses);
  }

  private RuleDefinition saveRuleDefinitionEntity(CreateRuleDefinitionRequest request) {
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

    return ruleDefinition;
  }

  private List<RuleConditionGroup> saveConditionGroupsRecursive(
      CreateRuleDefinitionRequest request, RuleDefinition ruleDefinition) {
    if (request.getConditionGroups() == null) return List.of();

    List<RuleConditionGroup> result = new ArrayList<>();
    for (ConditionGroupRequest groupRequest : request.getConditionGroups()) {
      RuleConditionGroup group = saveGroupRecursive(groupRequest, null, ruleDefinition);
      result.add(group);
    }
    return result;
  }

  private RuleConditionGroup saveGroupRecursive(
      ConditionGroupRequest request, RuleConditionGroup parent, RuleDefinition rule) {

    RuleConditionGroup group =
        RuleConditionGroup.builder()
            .id(UUID.randomUUID())
            .ruleDefinition(rule)
            .parentGroup(parent)
            .operator(Operator.valueOf(request.getOperator()))
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
                .build();
        if (group.getConditions() == null) {
          group.setConditions(new ArrayList<>());
        }
        group.getConditions().add(condition);
        conditionRepository.save(condition);
      }
    }

    if (request.getSubGroups() != null) {
      for (ConditionGroupRequest subGroup : request.getSubGroups()) {
        saveGroupRecursive(subGroup, group, rule);
      }
    }

    return group;
  }

  public void cacheRuleDefinition(RuleDefinition rule) {
    String script = ruleGenerateService.generateRule(rule);
    cacheStringRuleDefinition(rule.getExternalId(), script);
  }

  public void cacheStringRuleDefinition(String externalId, String script) {
    redisService.cacheEligibilityRuleScript(externalId, script);
  }

  public List<RuleListResponse> list() {
    return ruleDefinitionRepository.findAll().stream()
        .map(RuleListResponse::fromRuleDefinition)
        .collect(Collectors.toList());
  }

  public RuleDefinitionResponse getRuleDefinition(UUID id) {
    RuleDefinition rule = ruleDefinitionRepository.findById(id).orElseThrow();

    List<RuleConditionGroup> groups = conditionGroupRepository.findByRuleDefinitionId(id);
    List<UUID> groupIds = groups.stream().map(RuleConditionGroup::getId).toList();
    List<RuleCondition> conditions = conditionRepository.findByGroupIdIn(groupIds);

    Map<UUID, List<RuleCondition>> groupToConditions =
        conditions.stream().collect(Collectors.groupingBy(cond -> cond.getGroup().getId()));

    List<ConditionGroupResponse> groupResponses =
        groups.stream()
            .map(
                group ->
                    new ConditionGroupResponse(
                        group.getId(),
                        group.getParentGroup() != null ? group.getParentGroup().getId() : null,
                        group.getOperator(),
                        groupToConditions.getOrDefault(group.getId(), List.of()).stream()
                            .map(RuleConditionResponse::from)
                            .toList()))
            .toList();

    return RuleDefinitionResponse.mapToResponse(rule, groupResponses);
  }

  public RuleDefinition getRuleDefinitionEntity(String externalId) {
    return ruleDefinitionRepository
        .findByExternalId(externalId)
        .orElseThrow(
            () -> new IllegalArgumentException("Rule not found for external ID: " + externalId));
  }
}
