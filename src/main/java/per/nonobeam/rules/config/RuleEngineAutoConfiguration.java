package per.nonobeam.rules.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.nonobeam.rules.evaluate.executor.RuleEngineExecutor;
import per.nonobeam.rules.evaluate.executor.RuleEngineRedisExecutor;
import per.nonobeam.rules.evaluate.resolver.RuleScriptRedisResolver;
import per.nonobeam.rules.evaluate.resolver.RuleScriptResolver;
import per.nonobeam.rules.session.EligibilitySession;
import per.nonobeam.rules.web.service.RedisService;
import per.nonobeam.rules.web.service.RuleDefinitionService;
import per.nonobeam.rules.web.service.RuleGenerate;

@Configuration
public class RuleEngineAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RuleScriptResolver ruleScriptResolver(
          RedisService redisService,
          RuleDefinitionService ruleDefinitionService,
          RuleGenerate ruleGenerate
  ) {
    return new RuleScriptRedisResolver(redisService, ruleGenerate, ruleDefinitionService);
  }

  @Bean
  @ConditionalOnMissingBean
  public RuleEngineExecutor ruleEngineExecutor(EligibilitySession eligibilitySession) {
    return new RuleEngineRedisExecutor(eligibilitySession);
  }
}
