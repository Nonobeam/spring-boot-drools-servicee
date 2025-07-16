package per.nonobeam.rules.web.model.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListCreateRuleDefinitionRequest {
  private List<CreateRuleDefinitionRequest> rules;
}
