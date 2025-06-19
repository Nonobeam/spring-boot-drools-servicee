package per.nonobeam.rules.web.model.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateRuleDefinitionRequest(
        String name,
        String type,
        String action,
        int priority,
        LocalDateTime effectiveStart,
        LocalDateTime effectiveEnd,
        String status,
        UUID templateVersionId
) {}