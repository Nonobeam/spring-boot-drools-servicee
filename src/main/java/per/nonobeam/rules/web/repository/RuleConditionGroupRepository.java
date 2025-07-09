package per.nonobeam.rules.web.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleConditionGroup;

@Repository
public interface RuleConditionGroupRepository extends JpaRepository<RuleConditionGroup, UUID> {

  List<RuleConditionGroup> findByRuleDefinitionId(UUID ruleDefinitionId);
}
