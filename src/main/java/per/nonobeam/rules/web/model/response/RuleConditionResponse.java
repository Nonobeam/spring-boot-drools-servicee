package per.nonobeam.rules.web.model.response;

import per.nonobeam.rules.web.model.core.ConditionOperator;
import per.nonobeam.rules.web.model.core.DataType;
import per.nonobeam.rules.web.model.core.RuleCondition;

public record RuleConditionResponse(
    String leftOperand,
    ConditionOperator operator,
    String rightOperand,
    DataType dataType,
    int conditionOrder
) {
  public static RuleConditionResponse from(RuleCondition condition) {
    return new RuleConditionResponse(
        condition.getLeftOperand(),
        condition.getOperator(),
        condition.getRightOperand(),
        condition.getDataType(),
        condition.getConditionOrder()
    );
  }
}
