package per.nonobeam.rules.web.model.response;

import per.nonobeam.rules.web.model.core.RuleDefinition;

import java.util.UUID;

public record RuleListResponse(UUID id, String name, String externalId) {
  public static RuleListResponse fromRuleDefinition(RuleDefinition ruleDefinition) {
    return new RuleListResponse(
        ruleDefinition.getId(),
        ruleDefinition.getName(),
        ruleDefinition.getExternalId());
  }
}
