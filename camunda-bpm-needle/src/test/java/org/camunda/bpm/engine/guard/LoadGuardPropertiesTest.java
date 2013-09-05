package org.camunda.bpm.engine.guard;

import static org.camunda.bpm.engine.guard.GuardBpmnParseListener.RESOURCE_BUNDLE_GUARDS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.camunda.bpm.engine.guard.GuardBpmnParseListener;
import org.junit.Test;

import com.google.common.base.Function;

public class LoadGuardPropertiesTest {

  private final Function<String, Properties> function = GuardBpmnParseListener.LOAD_GUARD_PROPERTIES;

  @Test
  public void shouldReturnEmptyPropertiesWhenRBNameIsNull() {
    assertThat(function.apply(null).size(), is(0));
  }

  @Test
  public void shouldReturnEmptyPropertiesWhenRBDoesNotExist() {
    assertThat(function.apply("foo").size(), is(0));
  }

  @Test
  public void shouldReturnLoadedPropertiesWhenRBDoesExist() {
    assertThat(function.apply(RESOURCE_BUNDLE_GUARDS).size(), is(2));
  }

}
