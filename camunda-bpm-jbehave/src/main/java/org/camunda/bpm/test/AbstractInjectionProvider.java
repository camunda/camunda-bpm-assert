package org.camunda.bpm.test;

import de.akquinet.jbosscc.needle.injection.InjectionProvider;
import de.akquinet.jbosscc.needle.injection.InjectionTargetInformation;

/**
 * Abstract injection provider.
 * 
 * @author Holisticon
 * @param <T>
 *          type.
 */
public abstract class AbstractInjectionProvider<T> implements InjectionProvider<T> {

  @Override
  public boolean verify(final InjectionTargetInformation target) {
    return target.getType().isAssignableFrom(getType());
  }

  /**
   * Defines target type.
   * 
   * @return target type.
   */
  public abstract Class<T> getType();

  @Override
  public abstract T getInjectedObject(Class<?> type);

  @Override
  public Object getKey(final InjectionTargetInformation target) {
    return target.getType().getCanonicalName();
  }

}
