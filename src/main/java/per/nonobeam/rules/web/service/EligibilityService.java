package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.model.request.IncomingEvent;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EligibilityService {

  private final RedisServiceImpl redisService;
  private final RuleGenerateService ruleGenerateService;
  private final RuleDefinitionService ruleDefinitionService;

  public EligibilityUnit evaluate(IncomingEvent req) {
    String externalId = (String) req.getAttributes().get("externalId");
    String ruleScript = redisService.getEligibilityRuleScript(externalId);
    if (ruleScript == null) {
      log.error("No rule script found for externalId: {}", externalId);
      RuleDefinition ruleDefinition = ruleDefinitionService.getRuleDefinitionEntity(externalId);
      ruleScript = ruleGenerateService.generateRule(ruleDefinition);
    }

    KieServices ks = KieServices.Factory.get();
    KieFileSystem kfs = ks.newKieFileSystem();
    kfs.write("src/main/resources/rule_" + externalId + ".drl",
            ResourceFactory.newByteArrayResource(ruleScript.getBytes(StandardCharsets.UTF_8)));
    ks.newKieBuilder(kfs).buildAll();
    KieContainer kc = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

    try (KieSession ksess = kc.newKieSession()) {
      EligibilityUnit unit = new EligibilityUnit();
      ksess.insert(unit);
      ksess.insert(req);
      ksess.fireAllRules();
      return unit;
    }
  }
}
