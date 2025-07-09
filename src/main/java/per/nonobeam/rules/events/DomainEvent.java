package per.nonobeam.rules.events;

public interface DomainEvent<T> {
  T entity();

  String action();
}
