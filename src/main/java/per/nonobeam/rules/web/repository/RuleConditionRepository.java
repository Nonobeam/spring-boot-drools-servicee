package per.nonobeam.rules.web.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleCondition;

@Repository
public interface RuleConditionRepository extends JpaRepository<RuleCondition, UUID> {

  @Query("SELECT c FROM RuleCondition c WHERE c.group.id IN :groupIds")
  List<RuleCondition> findByGroupIds(@Param("groupIds") List<UUID> groupIds);
}
