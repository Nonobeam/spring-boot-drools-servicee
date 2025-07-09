package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final StringRedisTemplate redisTemplate;

  private static final String RULE_KEY_PREFIX = "drools:eligibility:shop:";

  @Override
  public void cacheEligibilityRuleScript(String shopId, String ruleScript) {
    String key = RULE_KEY_PREFIX + shopId;
    redisTemplate.opsForValue().set(key, ruleScript);
  }

  @Override
  public String getEligibilityRuleScript(String shopId) {
    String key = RULE_KEY_PREFIX + shopId;
    return redisTemplate.opsForValue().get(key);
  }
}
