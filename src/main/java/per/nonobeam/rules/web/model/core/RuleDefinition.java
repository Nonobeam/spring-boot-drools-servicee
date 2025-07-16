package per.nonobeam.rules.web.model.core;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rule_definition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDefinition {

  @Id private UUID id;

  private String externalId;

  private String name;

  private String type;

  private String action;

  private int priority;

  private LocalDateTime effectiveStart;

  private LocalDateTime effectiveEnd;

  @Enumerated(EnumType.STRING)
  private RuleStatus status;

  @ManyToOne
  @JoinColumn(name = "template_version_id", nullable = false)
  private RuleTemplateVersion templateVersion;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
