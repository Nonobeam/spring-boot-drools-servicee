package per.nonobeam.rules;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import per.nonobeam.rules.web.model.request.IncomingEvent;

@Getter
@Setter
public class EligibilityUnit implements RuleUnitData {

  private final DataStore<IncomingEvent> requests;
  private final List<String> logs;
  private Boolean rejected;

  public EligibilityUnit() {
    this(DataSource.createStore());
  }

  public EligibilityUnit(DataStore<IncomingEvent> requests) {
    this.requests = requests;
    this.logs = new ArrayList<>();
    this.rejected = false;
  }
}
