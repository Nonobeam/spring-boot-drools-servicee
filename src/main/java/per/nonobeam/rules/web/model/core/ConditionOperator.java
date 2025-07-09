package per.nonobeam.rules.web.model.core;

import lombok.Getter;

@Getter
public enum ConditionOperator {
  EQ("=="),
  NE("!="),
  LT("<"),
  GT(">"),
  LE("<="),
  GE(">=");

  private final String symbol;

  ConditionOperator(String symbol) {
    this.symbol = symbol;
  }
}
