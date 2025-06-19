package per.nonobeam.rules.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.nonobeam.rules.LoanEligibilityUnit;
import per.nonobeam.rules.web.model.request.LoanRequest;
import per.nonobeam.rules.web.model.response.LoanResponse;
import per.nonobeam.rules.web.service.LoanEligibilityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loan")
public class LoanEligibilityController {

  private final LoanEligibilityService service;

  @PostMapping("/evaluate")
  public LoanResponse evaluate(@RequestBody LoanRequest input) {
    LoanEligibilityUnit unit = service.evaluate(input);
    return LoanResponse.builder()
            .request(input)
            .firedRules(unit.getFiredRules())
            .logs(unit.getLogs())
            .build();
  }
}
