package per.nonobeam.rules.web.model.response;

import per.nonobeam.rules.web.model.core.Operator;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;

import java.util.List;
import java.util.UUID;

public record ConditionGroupResponse(
        UUID id,
        UUID parentGroupId,
        Operator operator,
        int groupOrder,
        List<RuleConditionResponse> conditions
) {
  public static ConditionGroupResponse from(RuleConditionGroup group) {
    return new ConditionGroupResponse(
            group.getId(),
            group.getParentGroup() != null ? group.getParentGroup().getId() : null,
            group.getOperator(),
            group.getGroupOrder(),
            group.getConditions().stream()
                    .map(RuleConditionResponse::from)
                    .toList()
    );
  }
}
