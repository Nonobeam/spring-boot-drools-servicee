package per.nonobeam.rules.web.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleTemplateVersion;

@Repository
public interface RuleTemplateVersionRepository extends JpaRepository<RuleTemplateVersion, UUID> {

  @Query("SELECT r.content FROM RuleTemplateVersion r WHERE r.isActive = true")
  List<String> findActiveDRLs();

  Optional<RuleTemplateVersion> getRuleTemplateVersionByTemplate_Id(UUID templateId);
}
