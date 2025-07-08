package per.nonobeam.rules.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import per.nonobeam.rules.events.RuleDefinitionEvent;
import per.nonobeam.rules.web.service.RuleDefinitionService;

@Component
@RequiredArgsConstructor
public class PersistentRedis {

  private final RuleDefinitionService ruleDefinitionService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleEntityChange(RuleDefinitionEvent event) {
    ruleDefinitionService.cacheRuleDefinition(event.entity());
  }
}
