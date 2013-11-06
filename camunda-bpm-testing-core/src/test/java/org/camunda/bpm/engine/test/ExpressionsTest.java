package org.camunda.bpm.engine.test;

import static org.camunda.bpm.engine.test.Expressions.*;
import static org.camunda.bpm.engine.test.function.NameForType.juelNameFor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import javax.inject.Named;

import org.junit.Test;

public class ExpressionsTest {

  @Named(FirstListener.NAME)
  public static class FirstListener {

    public static final String NAME = "firstListenerBean";
  }

  @Named
  public static class SecondListener {
  }

  public static class NoBean {

  }

  @Test
  public void shouldRegisterAndReturnInstance() {
    final FirstListener instance = new FirstListener();

    final FirstListener registered = registerInstance("a", instance);
    assertThat(registered, is(instance));

    final FirstListener got = getRegistered("a");
    assertThat(got, is(instance));
  }

  @Test
  public void shouldResetExpressions() throws Exception {
    registerInstance("a", new FirstListener());
    assertThat(getRegistered("a"), notNullValue());
    reset();
    assertThat(getRegistered("a"), nullValue());
  }

  @Test
  public void shouldRegisterMock() throws Exception {
    final FirstListener listener = registerMockInstance("a", FirstListener.class);
    when(listener.toString()).thenReturn("hello");
    assertThat(listener.toString(), is("hello"));
  }

  @Test
  public void shouldResolveName() throws Exception {
    assertThat(juelNameFor(FirstListener.class), is(FirstListener.NAME));
    assertThat(juelNameFor(SecondListener.class), is("secondListener"));
    assertThat(juelNameFor(NoBean.class), is("noBean"));
  }

  @Test
  public void shouldRegisterMocksForMultipleClasses() throws Exception {
    registerMockInstances(FirstListener.class, SecondListener.class);
    assertNotNull(getRegistered(FirstListener.class));
    assertNotNull(getRegistered(SecondListener.class));
  }
}
