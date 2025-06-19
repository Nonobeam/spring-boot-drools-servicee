package per.nonobeam.rules.web.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RuleDefinitionResponse(
        UUID id,
        String name,
        String type,
        String action,
        int priority,
        LocalDateTime effectiveStart,
        LocalDateTime effectiveEnd,
        String status,
        UUID templateVersionId
) {}
