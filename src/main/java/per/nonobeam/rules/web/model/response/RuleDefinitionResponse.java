package per.nonobeam.rules.web.model.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.model.core.RuleStatus;

public record RuleDefinitionResponse(
        UUID id,
        String externalId,
        String name,
        String type,
        String action,
        int priority,
        LocalDateTime effectiveStart,
        LocalDateTime effectiveEnd,
        RuleStatus status,
        UUID templateVersionId,
        List<ConditionGroupResponse> conditionGroups
) {
  public static RuleDefinitionResponse mapToResponse(
          RuleDefinition entity,
          List<ConditionGroupResponse> groups
  ) {
    return new RuleDefinitionResponse(
            entity.getId(),
            entity.getExternalId(),
            entity.getName(),
            entity.getType(),
            entity.getAction(),
            entity.getPriority(),
            entity.getEffectiveStart(),
            entity.getEffectiveEnd(),
            entity.getStatus(),
            entity.getTemplateVersion().getId(),
            groups
    );
  }
}
