package per.nonobeam.rules.web.model.request;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionGroupRequest {

  private UUID parentGroupId;

  @NotBlank private String operator; // AND, OR, NOT

  @NotNull private List<ConditionRequest> conditions;

  private List<ConditionGroupRequest> subGroups;
}
