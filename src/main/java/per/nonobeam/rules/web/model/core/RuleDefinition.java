package per.nonobeam.rules.web.model.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rule_definition")
public class RuleDefinition {
    @Id
    private UUID id;

    private String name;
    private String type;
    private String action;
    private int priority;

    @Column(name = "effective_start")
    private LocalDateTime effectiveStart;

    @Column(name = "effective_end")
    private LocalDateTime effectiveEnd;

    private String status;

    @ManyToOne
    @JoinColumn(name = "template_version_id")
    private RuleTemplateVersion templateVersion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}