package per.nonobeam.rules.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.nonobeam.rules.EligibilityUnit;
import per.nonobeam.rules.web.model.request.CreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.request.IncomingEvent;
import per.nonobeam.rules.web.model.request.ListCreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.model.response.RuleListResponse;
import per.nonobeam.rules.web.service.EligibilityService;
import per.nonobeam.rules.web.service.RuleDefinitionService;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleDefinitionController {

  private final RuleDefinitionService service;
  private final EligibilityService eligibilityService;

  @PostMapping("/create")
  public ResponseEntity<RuleDefinitionResponse> create(
      @Valid @RequestBody CreateRuleDefinitionRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PostMapping("/multi/create")
  public ResponseEntity<List<RuleDefinitionResponse>> create(
      @Valid @RequestBody ListCreateRuleDefinitionRequest request) {
    return ResponseEntity.ok(service.createAll(request));
  }

  @GetMapping("/list")
  public ResponseEntity<List<RuleListResponse>> list() {
    return ResponseEntity.ok(service.list());
  }

  @GetMapping("/{ruleId}")
  public ResponseEntity<RuleDefinitionResponse> getRuleDefinition(@PathVariable UUID ruleId) {
    RuleDefinitionResponse response = service.getRuleDefinition(ruleId);
    return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
  }

  //  @GetMapping("/definition")
  //  public ResponseEntity<RuleDefinitionResponse> getRuleDefinition(String externalId) {
  //    RuleDefinitionResponse response = service.getRuleDefinition(externalId);
  //    return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
  //  }

  @PostMapping("/evaluate")
  public ResponseEntity<String> handleEvent(@Valid @RequestBody IncomingEvent request) {

    EligibilityUnit result = eligibilityService.evaluate(request);

    if (result.getTotalScore() == 0) {
      System.out.println("Reject");
      return ResponseEntity.badRequest().body(String.join(", ", result.getLogs()));
    }

    // TODO INITIATE booking process or any other business logic

    return ResponseEntity.ok("Booking processed successfully");
  }
}
