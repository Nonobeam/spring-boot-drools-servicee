package per.nonobeam.rules.evaluate.executor;

import org.kie.api.runtime.KieSession;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.session.EligibilitySession;
import per.nonobeam.rules.web.model.request.IncomingEvent;

import java.util.List;

public class RuleEngineRedisExecutor implements RuleEngineExecutor {

  private final EligibilitySession eligibilitySession;

  public RuleEngineRedisExecutor(EligibilitySession eligibilitySession) {
    this.eligibilitySession = eligibilitySession;
  }

  public EligibilityUnit execute(String ruleScript, String externalId, IncomingEvent request, List<Object> extraFacts) {
    try (KieSession session = eligibilitySession.getOrCreateSession(externalId, ruleScript)) {
      EligibilityUnit result = new EligibilityUnit();
      session.insert(result);
      session.insert(request);
      extraFacts.forEach(session::insert);
      session.fireAllRules();
      return result;
    }
  }
}
