package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.camunda.bpm.engine.test.assertions.cmmn.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsTest {

  ProcessEngine processEngine;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    init(processEngine);
  }

  @After
  public void tearDown() {
    reset();
  }

  @Test
  public void testAssertThat_CaseInstance() throws Exception {
    //Given
    CaseInstance caseInstance = Mockito.mock(CaseInstance.class);
    // When
    CaseInstanceAssert returnedAssert = assertThat(caseInstance);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseInstance);
  }

  @Test
  public void testAssertThat_CaseExecution() throws Exception {
    //Given
    CaseExecution caseExecution = Mockito.mock(CaseExecution.class);
    // When
    CaseExecutionAssert returnedAssert = assertThat(caseExecution);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseExecution);
  }

  @Test
  public void testAssertThat_CaseDefinition() throws Exception {
    //Given
    CaseDefinition caseDefinition = Mockito.mock(CaseDefinition.class);
    // When
    CaseDefinitionAssert returnedAssert = assertThat(caseDefinition);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseDefinition);
  }

}
