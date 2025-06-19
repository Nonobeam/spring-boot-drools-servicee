package per.nonobeam.rules;

import lombok.Getter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import per.nonobeam.rules.web.model.request.LoanRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LoanEligibilityUnit implements RuleUnitData {

    private final DataStore<LoanRequest> requests;
    private final List<String> firedRules;
    private final List<String> logs;

    public LoanEligibilityUnit() {
        this(DataSource.createStore());
    }
    public LoanEligibilityUnit(DataStore<LoanRequest> requests) {
        this.requests = requests;
        this.firedRules = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
}
