package per.nonobeam.rules;

import lombok.Getter;
import lombok.Setter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import per.nonobeam.rules.web.model.request.IncomingEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EligibilityUnit implements RuleUnitData {
  private final DataStore<IncomingEvent> requests = DataSource.createStore();
  private List<String> logs = new ArrayList<>();
  private int totalScore = 0;
}
