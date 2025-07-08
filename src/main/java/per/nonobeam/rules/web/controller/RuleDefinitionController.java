package per.nonobeam.rules.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.web.model.request.CreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.request.IncomingEvent;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.service.EligibilityService;
import per.nonobeam.rules.web.service.RuleDefinitionService;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleDefinitionController {

  private final RuleDefinitionService service;
  private final EligibilityService eligibilityService;

  @PostMapping("/create")
  public ResponseEntity<RuleDefinitionResponse> create(
      @Valid @RequestBody CreateRuleDefinitionRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PostMapping("/evaluate")
  public ResponseEntity<String> handleEvent(@Valid @RequestBody IncomingEvent request) {

    EligibilityUnit result = eligibilityService.evaluate(request);

    if (result.getRejected()) {
      return ResponseEntity.badRequest().body(String.join(", ", result.getLogs()));
    }

    // TODO INITIATE booking process or any other business logic

    return ResponseEntity.ok("Booking processed successfully");
  }
}
