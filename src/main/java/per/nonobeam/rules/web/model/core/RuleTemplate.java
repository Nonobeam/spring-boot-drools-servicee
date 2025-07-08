package per.nonobeam.rules.web.model.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rule_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleTemplate {

  @Id private UUID id;

  private String name;

  private String description;

  private LocalDateTime createdAt;
}
