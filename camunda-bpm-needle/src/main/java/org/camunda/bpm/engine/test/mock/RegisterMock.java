package org.camunda.bpm.engine.test.mock;

// CHECKSTYLE:OFF test class
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.inject.Named;

/**
 * Helper for {@link Mocks#register(String, Object)}.
 * 
 * @author Jan Galinski, Holisticon AG
 */
public class RegisterMock {

  /**
   * Registers mocks via {@link Mocks#register(String, Object)} for all
   * attributes with Named-types.
   * 
   * @param instance
   *          instance whos fields are registered (maybe Junit test or jbehave
   *          steps).
   */
  public static void registerMocksForFields(final Object instance) {
    for (final Field field : instance.getClass().getDeclaredFields()) {
      final Class<?> type = field.getType();

      if (type.isAnnotationPresent(Named.class)) {
        field.setAccessible(true);
        try {
          final Object value = field.get(instance);
          if (value != null) {
            register(resolveName(type), value);
          }
        } catch (final IllegalArgumentException e) {
          // fallthrough
        } catch (final IllegalAccessException e) {
          // fallthrough
        }
      }

    }
  }

  public static void register(final String name, final Object instance) {
    Mocks.register(name, instance);
  }

  public static void register(final Class<?>... types) {
    for (final Class<?> type : types) {
      register(resolveName(type), mock(type));
    }
  }

  public static void register(final Object... instances) {
    for (final Object instance : instances) {
      Mocks.register(resolveName(instance.getClass()), instance);
    }
  }

  /**
   * @param type
   *          named class
   * @return named value for juel expr
   */
  public static String resolveName(final Class<?> type) {
    checkArgument(type != null, "type must not be null!");

    String name = null;
    try {
      final Field declaredField = type.getDeclaredField("NAME");
      if (Modifier.isStatic(declaredField.getModifiers()) && Modifier.isFinal(declaredField.getModifiers())) {
        name = (String) declaredField.get(type.getClass());
      }
    } catch (final Exception e) {
      // ignore
    }

    return name != null ? name : uncapitalize(type.getSimpleName());
  }

}
