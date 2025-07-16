package per.nonobeam.rules.session;

import org.kie.api.runtime.KieSession;

public interface EligibilitySession {
  KieSession getOrCreateSession(String externalId, String ruleScript);
}
