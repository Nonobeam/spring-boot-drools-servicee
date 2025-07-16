package per.nonobeam.rules.web.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.rules.web.model.core.RuleTemplateVersion;

@Repository
public interface RuleTemplateVersionRepository extends JpaRepository<RuleTemplateVersion, UUID> {}
