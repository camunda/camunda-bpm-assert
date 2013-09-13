package org.camunda.bpm.engine.impl.fluent;

import java.util.Map.Entry;

import org.camunda.bpm.engine.fluent.FluentProcessVariable;

/**
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessVariableImpl implements FluentProcessVariable {

  private final String name;
  private final Object value;

  protected FluentProcessVariableImpl(final String name, final Object value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public Entry<String, Object> getDelegate() {
    return new Entry<String, Object>() {

      @Override
      public Object setValue(final Object value) {
        throw new UnsupportedOperationException();
      }

      @Override
      public Object getValue() {
        return value;
      }

      @Override
      public String getKey() {
        return name;
      }
    };
  }

}
