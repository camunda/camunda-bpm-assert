package org.camunda.bpm.engine.test;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.camunda.bpm.engine.test.function.CreateInstance.mockInstance;
import static org.camunda.bpm.engine.test.function.CreateInstance.newInstanceByDefaultConstructor;
import static org.camunda.bpm.engine.test.function.NameForType.juelNameFor;

import java.lang.reflect.Field;
import java.util.Collection;

import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.test.function.FindNestedClasses;
import org.camunda.bpm.engine.test.function.NameForType;
import org.camunda.bpm.engine.test.mock.FluentExecutionListenerMock;
import org.camunda.bpm.engine.test.mock.FluentJavaDelegateMock;
import org.camunda.bpm.engine.test.mock.FluentTaskListenerMock;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class that deleagtes to {@link Mocks#register(String, Object)} and
 * {@link Mocks#get(Object)} in atype-safe way.
 */
public final class Expressions {

  private static final Logger LOG = LoggerFactory.getLogger(Expressions.class);

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#registerMockJavaDelegate(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static FluentJavaDelegateMock registerMockJavaDelegate(final String name) {
    return DelegateExpressions.registerJavaDelegateMock(name);
  }

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#registerMockJavaDelegate(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static FluentExecutionListenerMock registerMockExecutionListener(final String name) {
    return DelegateExpressions.registerExecutionListenerMock(name);
  }

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#registerMockJavaDelegate(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static FluentTaskListenerMock registerMockTaskListener(final String name) {
    return DelegateExpressions.registerTaskListenerMock(name);
  }

  /**
   * Registers mock instances for every public static nested class found in
   * parentClass.
   * 
   * @param parentClass
   *          the parentClass to scan for nested public static types
   */
  public static void registerMockInstancesForNestedTypes(final Class<?> parentClass) {
    for (final Class<?> type : FindNestedClasses.PUBLIC_STATIC.apply(parentClass)) {
      registerMockInstance(type);
    }
  }

  /**
   * Registers mocks via {@link Mocks#register(String, Object)} for all
   * attributes with Named-types.
   * 
   * @param instance
   *          instance whos fields are registered (maybe Junit test or jbehave
   *          steps).
   */
  public static void registerInstancesForFields(final Object instance) {
    checkArgument(instance != null, "instance must not be null!");
    for (final Field field : instance.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        final Object value = field.get(instance);
        if (value != null) {
          registerInstance(juelNameFor(field.getType()), value);
        }
      } catch (final Exception e) {
        // fallthrough
      }
    }
  }

  /**
   * Registers new instances for every public static nested class found in
   * parentClass.
   * 
   * @param parentClass
   *          the parentClass to scan for nested public static types
   */
  public static void registerNewInstancesForNestedTypes(final Class<?> parentClass) {
    for (final Class<?> type : FindNestedClasses.PUBLIC_STATIC.apply(parentClass)) {
      registerNewInstance(type);
    }
  }

  /**
   * Creates and registers mock instance for every given type.
   * 
   * @param types
   *          collection of types to mock and register
   */
  public static void registerMockInstances(final Class<?>... types) {
    checkArgument(types != null, "types must not be null!");
    registerMockInstances(newArrayList(types));
  }

  /**
   * Creates and registers mock instance for every given type.
   * 
   * @param types
   *          collection of types to mock and register
   */
  public static void registerMockInstances(final Collection<Class<?>> types) {
    for (final Class<?> type : types) {
      registerMockInstance(type);
    }
  }

  /**
   * Creates a mock for the given type and registers it.
   * 
   * @param name
   *          the juel name under which the mock is registered
   * @param type
   *          the type of the mock to create
   * @return the registered mock instance
   */
  public static <T> T registerMockInstance(final String name, final Class<T> type) {
    return registerInstance(name, mockInstance(type));
  }

  /**
   * Creates a mock for the given type and registers it.
   * 
   * @param type
   *          the type of the mock to create
   * @return the registered mock instance
   */
  public static <T> T registerMockInstance(final Class<T> type) {
    return registerMockInstance(juelNameFor(type), type);
  }

  /**
   * Creates a new instance for the given type and registers it under the given
   * name.
   * 
   * @param name
   *          the name for the registered instance
   * @param type
   *          the type of the instance to create
   * @return the registered instance
   */
  public static <T> T registerNewInstance(final String name, final Class<T> type) {
    return registerInstance(name, newInstanceByDefaultConstructor(type));
  }

  /**
   * Creates a new instance for the given type using the default constructor and
   * registers it.
   * 
   * @see #registerNewInstance(String, Class)
   * @param type
   *          the type of the instance to create
   * @return the registered instance
   */
  public static <T> T registerNewInstance(final Class<T> type) {
    return registerNewInstance(juelNameFor(type), type);
  }

  /**
   * If you already have the instance, register it directly. Name is guessed via
   * {@link NameForType}.
   * 
   * @param instance
   *          the instance or mock to register
   * @return the registered instance
   */
  public static <T> T registerInstance(final T instance) {
    return registerInstance(juelNameFor(instance.getClass()), instance);
  }

  /**
   * Delegates to {@link Mocks#register(String, Object)}
   * 
   * @param name
   *          the juel name for the registered instance
   * @param instance
   *          the instance to register
   * @return the registered instance
   */
  public static <T> T registerInstance(final String name, final T instance) {
    checkArgument(isNotBlank(name), "name must not be blank!");
    checkArgument(instance != null, "instance must not be null!");
    LOG.debug(format("registered instance: name=%s, value=%s", name, instance));
    Mocks.register(name, instance);
    return instance;
  }

  /**
   * 
   * @param name
   *          juel name of the registered instance or mock
   * @return registered instance or mock of type
   */
  @SuppressWarnings("unchecked")
  public static <T> T getRegistered(final String name) {
    final T mock = (T) Mocks.get(name);
    checkState(mock != null, "no instance registered for name=" + name);
    return mock;
  }

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#getRegisteredJavaDelegate(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static JavaDelegate getRegisteredJavaDelegate(final String name) {
    return DelegateExpressions.getRegisteredJavaDelegate(name);
  }

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#getRegisteredExecutionListener(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static ExecutionListener getRegisteredExecutionListener(final String name) {
    return DelegateExpressions.getRegisteredExecutionListener(name);
  }

  /**
   * @deprecated moved to
   *             {@link DelegateExpressions#getRegisteredTaskListener(String)}
   * @param name
   *          the registered name
   * @return the registered instance
   */
  @Deprecated
  public static TaskListener getRegisteredTaskListener(final String name) {
    return getRegistered(name);
  }

  /**
   * @param type
   *          the type of the registered instance or mock
   * @return registered instance or mock for type
   */
  public static <T> T getRegistered(final Class<?> type) {
    return getRegistered(juelNameFor(type));
  }

  /**
   * @see Mocks#reset()
   */
  public static void reset() {
    Mocks.reset();
  }

  private Expressions() {
    // hide constructor
  }

}
