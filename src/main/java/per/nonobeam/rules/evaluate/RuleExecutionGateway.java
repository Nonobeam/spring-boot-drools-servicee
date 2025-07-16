package per.nonobeam.rules.evaluate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.evaluate.executor.RuleEngineExecutor;
import per.nonobeam.rules.evaluate.resolver.RuleScriptResolver;
import static per.nonobeam.rules.web.model.core.ExternalConstant.EXTERNAL_ID;
import per.nonobeam.rules.web.model.request.IncomingEvent;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleExecutionGateway {

  private final RuleScriptResolver resolver;
  private final RuleEngineExecutor executor;

  public RuleExecutionFlow with(IncomingEvent event) {
    return new RuleExecutionFlow(event, resolver, executor);
  }

  @Getter
  public static class RuleExecutionFlow {

    private final IncomingEvent event;
    private final RuleScriptResolver resolver;
    private final RuleEngineExecutor executor;
    private final List<Object> extraFacts = new ArrayList<>();

    private String externalId;
    private String ruleScript;
    private EligibilityUnit result;

    public RuleExecutionFlow(IncomingEvent event, RuleScriptResolver resolver, RuleEngineExecutor executor) {
      this.event = event;
      this.resolver = resolver;
      this.executor = executor;
    }

    public RuleExecutionFlow register(Object... facts) {
      this.extraFacts.addAll(List.of(facts));
      return this;
    }

    public EligibilityUnit run() {
      return this
          .resolve()
          .execute( )
          .result();
    }

    public RuleExecutionFlow resolve() {
      this.externalId = extractExternalId();
      this.ruleScript = resolver.resolve(externalId);
      return this;
    }

    public RuleExecutionFlow execute() {
      this.result = executor.execute(ruleScript, externalId, event, extraFacts);
      return this;
    }

    public EligibilityUnit result() {
      return this.result;
    }

    private String extractExternalId() {
      Object raw = event.getAttributes().get(EXTERNAL_ID);
      if (raw == null) throw new IllegalArgumentException("Missing externalId");
      return raw.toString();
    }
  }
}
