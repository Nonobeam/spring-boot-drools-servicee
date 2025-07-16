package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.evaluate.RuleExecutionGateway;
import per.nonobeam.rules.web.model.request.IncomingEvent;

@Service
@RequiredArgsConstructor
public class EligibilityService {

  private final RuleExecutionGateway gateway;

  public EligibilityUnit evaluate(IncomingEvent event) {
    return gateway.with(event).register(new Object(), new Object()).run();
  }
}
