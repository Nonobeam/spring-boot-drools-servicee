package per.nonobeam.rules.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleDefinition;

import java.util.UUID;

@Repository
public interface RuleDefinitionRepository extends JpaRepository<RuleDefinition, UUID> {}