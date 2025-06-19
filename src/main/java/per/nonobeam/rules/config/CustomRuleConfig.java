package per.nonobeam.rules.config;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.drools.ruleunits.api.conf.RuleConfig;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.internal.event.rule.RuleEventListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomRuleConfig implements RuleConfig {
  @Override
  public List<AgendaEventListener> getAgendaEventListeners() {
    return List.of(new DebugAgendaEventListener());
  }

  @Override
  public List<RuleRuntimeEventListener> getRuleRuntimeListeners() {
    return List.of(new DebugRuleRuntimeEventListener());
  }

  @Override
  public List<RuleEventListener> getRuleEventListeners() {
    return List.of();
  }
}
