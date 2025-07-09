package per.nonobeam.rules.web.model.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingEvent {
  private Map<String, Object> attributes;
  private String idempotentKey;
}
