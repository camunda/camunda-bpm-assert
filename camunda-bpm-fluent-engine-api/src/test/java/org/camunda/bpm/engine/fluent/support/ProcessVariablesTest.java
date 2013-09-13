package org.camunda.bpm.engine.fluent.support;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProcessVariablesTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldFailWhenNumberOfArgsIsOdd() {
    thrown.expectMessage(is("parseMap() must have an even number of arguments, was length='3'"));

    ProcessVariableMaps.parseMap("foo", 1, "bar");
  }

  @Test
  public void shouldFailForNull() throws Exception {
    thrown.expectMessage(is("keyValuePairs must not be null!"));

    ProcessVariableMaps.parseMap((Object[]) null);
  }

}
