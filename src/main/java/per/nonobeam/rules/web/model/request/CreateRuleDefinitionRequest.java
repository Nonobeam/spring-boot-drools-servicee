package per.nonobeam.rules.web.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRuleDefinitionRequest {

  @NotBlank private String externalId;

  @NotBlank private String name;

  @NotBlank private String type;

  @NotBlank private String action;

  @Min(1)
  private int priority;

  private LocalDateTime effectiveStart;

  private LocalDateTime effectiveEnd;

  private UUID templateVersionId;

  private List<ConditionGroupRequest> conditionGroups;
}
