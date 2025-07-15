package per.nonobeam.rules.web.model.response;

import java.util.List;
import java.util.UUID;
import per.nonobeam.rules.web.model.core.Operator;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;

public record ConditionGroupResponse(
    UUID id, UUID parentGroupId, Operator operator, List<RuleConditionResponse> conditions) {
  public static ConditionGroupResponse from(RuleConditionGroup group) {
    return new ConditionGroupResponse(
        group.getId(),
        group.getParentGroup() != null ? group.getParentGroup().getId() : null,
        group.getOperator(),
        group.getConditions().stream().map(RuleConditionResponse::from).toList());
  }
}
