package per.nonobeam.rules.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.kie.api.KieBase;
import org.springframework.stereotype.Component;

@Component
public class KieBaseCache {

  private final Map<String, KieBase> cache = new ConcurrentHashMap<>();

  public KieBase get(String externalId) {
    return cache.get(externalId);
  }

  public KieBase computeIfAbsent(String externalId, Supplier<KieBase> supplier) {
    return cache.computeIfAbsent(externalId, id -> supplier.get());
  }

  public KieBase put(String externalId, KieBase base) {
    return cache.put(externalId, base);
  }

  public void clear(String externalId) {
    cache.remove(externalId);
  }
}
