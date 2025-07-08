package per.nonobeam.rules.events;

import per.nonobeam.rules.web.model.core.RuleDefinition;

public record RuleDefinitionEvent(RuleDefinition entity, String action)
    implements DomainEvent<RuleDefinition> {}
