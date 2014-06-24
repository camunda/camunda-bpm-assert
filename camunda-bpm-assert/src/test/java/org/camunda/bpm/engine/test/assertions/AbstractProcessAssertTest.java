package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@SuppressWarnings("unchecked")
public class AbstractProcessAssertTest {
  
  ProcessEngine processEngine;
  Class<AbstractProcessAssert> anAssertClass;
  Class anActualClass;
  Object anActual;

  Iterator<Class<AbstractProcessAssert>> allAsserts;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    ProcessEngineAssertions.init(processEngine);
    allAsserts = Arrays.asList((Class<AbstractProcessAssert>[]) new Class[] {
      JobAssert.class, 
      ProcessDefinitionAssert.class, 
      ProcessInstanceAssert.class, 
      TaskAssert.class
    }).iterator();
  }

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  public void testConstructorPattern() throws Exception {
    while(allAsserts.hasNext()) {
      mockActual(allAsserts.next());
      assertThat(newInstanceFromExpectedConstructor()).isNotNull();
    }
  }

  @Test
  public void testFactoryMethodPattern() throws Exception {
    while(allAsserts.hasNext()) {
      mockActual(allAsserts.next());
      assertThat(newInstanceFromExpectedFactoryMethod()).isNotNull();
    }
  }
  
  @Test
  public void testLastAssert_BeforeFirstAssert() {
    while(allAsserts.hasNext()) {
      mockActual(allAsserts.next());
      assertThat(AbstractProcessAssert.getLastAssert(anAssertClass)).isNull();
    }
  }

  @Test
  public void testLastAssert_AfterFirstAssert() {
    while(allAsserts.hasNext()) {
      mockActual(allAsserts.next());
      AbstractProcessAssert assertInstance = newInstanceFromExpectedFactoryMethod();
      assertThat(assertInstance).isNotNull();
      assertThat(AbstractProcessAssert.getLastAssert(anAssertClass)).isSameAs(assertInstance);
    }
  }

  @Test
  public void testLastAssert_AfterSecondAssert() {
    while(allAsserts.hasNext()) {
      mockActual(allAsserts.next());
      AbstractProcessAssert assertInstance1 = newInstanceFromExpectedFactoryMethod();
      assertThat(assertInstance1).isNotNull();
      AbstractProcessAssert assertInstance2 = newInstanceFromExpectedFactoryMethod();
      assertThat(assertInstance2).isNotNull();
      assertThat(AbstractProcessAssert.getLastAssert(anAssertClass)).isSameAs(assertInstance2);
      assertThat(assertInstance1).isNotSameAs(assertInstance2);
    }
  }

  private <A extends AbstractProcessAssert> A newInstanceFromExpectedConstructor() {
    Constructor constructor = null; 
    try {
      constructor = anAssertClass.getDeclaredConstructor(ProcessEngine.class, anActualClass);
    } catch (NoSuchMethodException e) {
      fail("Cannot find expected constructor!", e);
    }
    assert constructor != null;
    A assertInstance = null;
    try {
      assertInstance = (A) constructor.newInstance(processEngine, Mockito.mock(anActualClass));
    } catch (Exception e) {
      fail("Cannot create instance from constructor!", e);
    }
    return assertInstance;
  }

  private <A extends AbstractProcessAssert> A newInstanceFromExpectedFactoryMethod() {
    Method method = null;
    try {
      method = anAssertClass.getDeclaredMethod("assertThat", ProcessEngine.class, anActualClass);
    } catch (NoSuchMethodException e) {
      fail("Cannot find expected factory method!", e);
    }
    assert method != null;
    A assertInstance = null;
    try {
      assertInstance = (A) method.invoke(anAssertClass, processEngine, anActual);
    } catch (Exception e) {
      fail("Cannot create instance from constructor!", e);
    }
    return assertInstance;
  }

  private void mockActual(Class assertClass) {
    anAssertClass = assertClass;
    assertThat(assertClass).isNotNull();
    ParameterizedType type = (ParameterizedType) assertClass.getGenericSuperclass();
    assertThat(type.getActualTypeArguments()).hasSize(2);
    assertThat(type.getActualTypeArguments()[0]).isSameAs(assertClass);
    assertThat(type.getActualTypeArguments()[1]).isInstanceOf(Class.class);
    anActualClass = (Class) type.getActualTypeArguments()[1];
    assertThat(anActualClass).isNotNull();
    anActual = Mockito.mock(anActualClass);
  }

}
