package org.camunda.bpm.engine.test.mock;

import static org.camunda.bpm.engine.test.mock.RegisterMock.resolveName;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Named;

import org.junit.Test;

public class RegisterMockTest {

  private static final String SECOND_LISTENER = "secondListener";

  @Named(FirstListener.NAME)
  public static final class FirstListener {

    public static final String NAME = "firstListener";
  }

  @Named
  public static final class SecondListener {
  }

  public static final class NoBean {

  }

  private FirstListener one = new FirstListener();
  private SecondListener second = new SecondListener();
  @SuppressWarnings("unused")
  private NoBean third = new NoBean();

  @Test
  public void shouldResolveNAME() throws Exception {
    assertThat(resolveName(FirstListener.class), is(FirstListener.NAME));
    assertThat(resolveName(SecondListener.class), is(SECOND_LISTENER));
  }

  @Test
  public void shouldRegisterAllNamedAttributeInstances() throws Exception {
    Mocks.reset();
    RegisterMock.registerMocksForFields(this);
    assertEquals("'First not registered'", one, Mocks.get(FirstListener.NAME));
    assertEquals("'Second not registered'", second, Mocks.get(SECOND_LISTENER));
    assertThat("'NoBean registered'", Mocks.get("noBean"), nullValue());

  }

}
