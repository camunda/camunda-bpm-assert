package org.camunda.bpm.engine.test.function;

import static com.google.common.base.Throwables.propagate;

import org.mockito.Mockito;

public final class CreateInstance {

  public static <T> T mockInstance(final Class<T> type) {
    return Mockito.mock(type);
  }

  @SuppressWarnings("unchecked")
  public static <T> T newInstanceByDefaultConstructor(final Class<T> type) {
    try {
      return (T) type.getConstructors()[0].newInstance();
    } catch (final Exception e) {
      throw propagate(e);
    }

  }
}
