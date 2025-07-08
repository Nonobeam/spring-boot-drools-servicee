package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.config.KieBaseCache;
import per.nonobeam.rules.web.model.request.IncomingEvent;

@Service
@RequiredArgsConstructor
public class EligibilityService {

  private final KieBaseCache kieBaseCache;

  public EligibilityUnit evaluate(IncomingEvent req) {
    String externalId = (String) req.getAttributes().get("externalId");

    KieBase kieBase = kieBaseCache.get(externalId);
    if (kieBase == null) {
      throw new IllegalArgumentException("No compiled rule for externalId: " + externalId);
    }

    try (KieSession kieSession = kieBase.newKieSession()) {
      EligibilityUnit unit = new EligibilityUnit();
      unit.getRequests().add(req);

      kieSession.insert(unit);
      kieSession.fireAllRules();
      return unit;
    }
  }
}
