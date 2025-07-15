package per.nonobeam.rules.web.model.core;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rule_condition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleCondition {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id", nullable = false)
  private RuleConditionGroup group;

  private String leftOperand;

  @Enumerated(EnumType.STRING)
  private ConditionOperator operator;

  private String rightOperand;

  @Enumerated(EnumType.STRING)
  private DataType dataType;
}
