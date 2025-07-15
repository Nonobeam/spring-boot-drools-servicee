package per.nonobeam.rules.web.model.core;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "rule_condition_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleConditionGroup {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "rule_definition_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_rule_condition_group_rule_definition"))
  private RuleDefinition ruleDefinition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "parent_group_id",
      foreignKey = @ForeignKey(name = "fk_rule_condition_group_parent"))
  private RuleConditionGroup parentGroup;

  @Enumerated(EnumType.STRING)
  private Operator operator;

  private int groupOrder;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RuleCondition> conditions = new ArrayList<>();
}
