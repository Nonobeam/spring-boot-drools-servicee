package per.nonobeam.rules.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.nonobeam.rules.web.model.request.CreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.service.RuleDefinitionService;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleDefinitionController {
    private final RuleDefinitionService service;

    @PostMapping
    public ResponseEntity<RuleDefinitionResponse> create(@RequestBody CreateRuleDefinitionRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
