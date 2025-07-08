package per.nonobeam.rules.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class EntityChangeEvent extends ApplicationEvent {
  private final Object entity;
  private final String action;

  public EntityChangeEvent(Object source, Object entity, String action) {
    super(source);
    this.entity = entity;
    this.action = action;
  }
}
