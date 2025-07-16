package per.nonobeam.rules.evaluate.resolver;

import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.service.RedisService;
import per.nonobeam.rules.web.service.RuleDefinitionService;
import per.nonobeam.rules.web.service.RuleGenerate;

import java.util.Optional;

public class RuleScriptRedisResolver implements RuleScriptResolver {

  private final RedisService redisService;
  private final RuleGenerate ruleGenerate;
  private final RuleDefinitionService ruleDefinitionService;

  public RuleScriptRedisResolver(RedisService redisService, RuleGenerate ruleGenerate, RuleDefinitionService ruleDefinitionService) {
    this.redisService = redisService;
    this.ruleGenerate = ruleGenerate;
    this.ruleDefinitionService = ruleDefinitionService;
  }

  public String resolve(String externalId) {
    return Optional.ofNullable(redisService.getEligibilityRuleScript(externalId))
            .orElseGet(() -> loadAndCacheRuleScript(externalId));
  }

  private String loadAndCacheRuleScript(String externalId) {
    RuleDefinition definition = ruleDefinitionService.getRuleDefinitionEntity(externalId);
    String script = ruleGenerate.generateRule(definition);
    redisService.cacheEligibilityRuleScript(externalId, script);
    return script;
  }
}
