package per.nonobeam.rules.web.model.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "rule_template_version",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"template_id", "version"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleTemplateVersion {

  @Id private UUID id;

  @ManyToOne
  @JoinColumn(name = "template_id", nullable = false)
  private RuleTemplate template;

  private int version;

  private String content;

  private Boolean isActive = true;

  private LocalDateTime createdAt;
}
