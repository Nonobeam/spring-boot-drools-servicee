package per.nonobeam.rules.events.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import per.nonobeam.rules.events.EntityEvent;
import per.nonobeam.rules.events.RuleDefinitionEvent;
import per.nonobeam.rules.web.model.core.RuleDefinition;

@Component
@RequiredArgsConstructor
public class RuleDefinitionListener {
  private final ApplicationEventPublisher publisher;

  @PrePersist
  public void prePersist(RuleDefinition entity) {
    publishEvent(entity, EntityEvent.CREATE);
  }

  @PreUpdate
  public void preUpdate(RuleDefinition entity) {
    publishEvent(entity, EntityEvent.UPDATE);
  }

  @PreRemove
  public void preRemove(RuleDefinition entity) {
    publishEvent(entity, EntityEvent.DELETE);
  }

  private void publishEvent(RuleDefinition entity, EntityEvent action) {
    publisher.publishEvent(new RuleDefinitionEvent(entity, action.name()));
  }
}
