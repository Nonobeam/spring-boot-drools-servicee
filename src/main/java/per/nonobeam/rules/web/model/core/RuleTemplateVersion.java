package per.nonobeam.rules.web.model.core;

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
@Table(name = "rule_template_version")
public class RuleTemplateVersion {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private RuleTemplate template;

    private int version;
    private String content;
    private Boolean isActive;
    private LocalDateTime createdAt;
}