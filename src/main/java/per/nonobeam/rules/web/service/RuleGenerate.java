package per.nonobeam.rules.web.service;

import per.nonobeam.rules.web.model.core.RuleDefinition;

public interface RuleGenerate {
  String generateRule(RuleDefinition rule);
}
