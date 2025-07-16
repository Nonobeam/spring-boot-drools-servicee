package per.nonobeam.rules.session;

import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class EligibilitySessionCache implements EligibilitySession {

  private final ConcurrentMap<String, KieBase> cache = new ConcurrentHashMap<>();

  public KieSession getOrCreateSession(String externalId, String ruleScript) {
    KieBase kieBase = cache.computeIfAbsent(externalId, id -> {
      KieHelper kieHelper = new KieHelper();
      kieHelper.addContent(ruleScript, ResourceType.DRL);
      return kieHelper.build();
    });
    return kieBase.newKieSession();
  }
}
