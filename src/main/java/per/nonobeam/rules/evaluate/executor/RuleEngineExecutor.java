package per.nonobeam.rules.evaluate.executor;

import java.util.List;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.web.model.request.IncomingEvent;

public interface RuleEngineExecutor {
  EligibilityUnit execute(
      String ruleScript, String externalId, IncomingEvent request, List<Object> extraFacts);
}
