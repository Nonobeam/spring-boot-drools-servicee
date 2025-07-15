package per.nonobeam.rules.web.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleDefinition;

@Repository
public interface RuleDefinitionRepository extends JpaRepository<RuleDefinition, UUID> {

  @Query(
      """
    SELECT rd FROM RuleDefinition rd
    JOIN FETCH rd.templateVersion tv
    WHERE rd.status = 'ACTIVE'
    """)
  List<RuleDefinition> findActiveRulesWithTemplate();

  Optional<RuleDefinition> findByExternalId(String externalId);
}
