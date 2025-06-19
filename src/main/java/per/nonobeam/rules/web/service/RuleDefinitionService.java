package per.nonobeam.rules.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import per.nonobeam.rules.web.model.core.RuleDefinition;
import per.nonobeam.rules.web.model.core.RuleTemplateVersion;
import per.nonobeam.rules.web.model.request.CreateRuleDefinitionRequest;
import per.nonobeam.rules.web.model.response.RuleDefinitionResponse;
import per.nonobeam.rules.web.repository.RuleDefinitionRepository;
import per.nonobeam.rules.web.repository.RuleTemplateVersionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RuleDefinitionService {
    private final RuleDefinitionRepository repository;
    private final RuleTemplateVersionRepository templateVersionRepository;

    public RuleDefinitionResponse create(CreateRuleDefinitionRequest request) {
        RuleTemplateVersion version = templateVersionRepository.findById(request.templateVersionId())
                .orElseThrow();

        RuleDefinition entity = new RuleDefinition();
        entity.setId(UUID.randomUUID());
        entity.setName(request.name());
        entity.setType(request.type());
        entity.setAction(request.action());
        entity.setPriority(request.priority());
        entity.setEffectiveStart(request.effectiveStart());
        entity.setEffectiveEnd(request.effectiveEnd());
        entity.setStatus(request.status());
        entity.setTemplateVersion(version);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);

        return new RuleDefinitionResponse(
                entity.getId(), entity.getName(), entity.getType(), entity.getAction(),
                entity.getPriority(), entity.getEffectiveStart(), entity.getEffectiveEnd(),
                entity.getStatus(), entity.getTemplateVersion().getId()
        );
    }
}