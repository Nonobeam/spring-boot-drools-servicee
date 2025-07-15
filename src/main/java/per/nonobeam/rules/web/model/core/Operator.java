package per.nonobeam.rules.web.model.core;

import lombok.Getter;

@Getter
public enum Operator {
  AND("&&"),
  OR("||"),
  NOT("!");

  private final String symbol;

  Operator(String symbol) {
    this.symbol = symbol;
  }
}
