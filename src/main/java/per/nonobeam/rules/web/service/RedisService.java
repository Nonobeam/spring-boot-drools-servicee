package per.nonobeam.rules.web.service;

public interface RedisService {
  void cacheEligibilityRuleScript(String externalId, String ruleScript);

  String getEligibilityRuleScript(String externalId);
}
