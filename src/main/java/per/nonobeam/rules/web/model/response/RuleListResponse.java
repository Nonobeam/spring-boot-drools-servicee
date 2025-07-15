package per.nonobeam.rules.web.model.response;

import java.util.UUID;
import per.nonobeam.rules.web.model.core.RuleDefinition;

public record RuleListResponse(UUID id, String name, String externalId) {
  public static RuleListResponse fromRuleDefinition(RuleDefinition ruleDefinition) {
    return new RuleListResponse(
        ruleDefinition.getId(), ruleDefinition.getName(), ruleDefinition.getExternalId());
  }
}
