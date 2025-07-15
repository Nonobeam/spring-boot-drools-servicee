package per.nonobeam.rules.web.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleDefinition;

@Repository
public interface RuleDefinitionRepository extends JpaRepository<RuleDefinition, UUID> {
  Optional<RuleDefinition> findByExternalId(String externalId);
}
