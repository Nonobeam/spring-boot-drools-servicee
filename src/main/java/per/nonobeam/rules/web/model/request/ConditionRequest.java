package per.nonobeam.rules.web.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionRequest {

  @NotBlank private String leftOperand;

  @NotBlank private String operator; // ==, !=, <, >, <=, >=

  @NotBlank private String rightOperand;

  @NotBlank private String dataType; // string, number, boolean, variable
}
