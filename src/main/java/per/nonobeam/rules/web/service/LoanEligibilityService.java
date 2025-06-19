package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.LoanEligibilityUnit;
import per.nonobeam.rules.config.CustomRuleConfig;
import per.nonobeam.rules.web.model.request.LoanRequest;

@Service
@RequiredArgsConstructor
public class LoanEligibilityService {

    private final CustomRuleConfig customRuleConfig;

    public LoanEligibilityUnit evaluate(LoanRequest req) {
        LoanEligibilityUnit unit = new LoanEligibilityUnit();
        unit.getRequests().add(req);
        try (RuleUnitInstance<LoanEligibilityUnit> instance = RuleUnitProvider.get()
                .createRuleUnitInstance(unit, customRuleConfig)) {
            instance.fire();
        }
        return unit;
    }
}
