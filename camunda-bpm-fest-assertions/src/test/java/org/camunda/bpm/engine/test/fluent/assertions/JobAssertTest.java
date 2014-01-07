package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.test.fluent.ProcessEngineAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.fest.assertions.api.Assertions.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertTest {
  
  ProcessEngine processEngine;
  Job job;
  JobAssert jobAssert;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    job = Mockito.mock(Job.class);
    ProcessEngineAssertions.init(processEngine);
    jobAssert = ProcessEngineAssertions.assertThat(job);
  }

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  public void testHasId_Success() {
    // Given
    when(job.getId()).thenReturn("id");
    // When
    JobAssert actualAssert = jobAssert.hasId("id");
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasId_Failure() {
    // Given
    when(job.getId()).thenReturn("id");
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasId("otherId");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasId_Error_Null() {
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasId(null);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasDueDate_Success() {
    // Given
    when(job.getDuedate()).thenReturn(new Date(999));
    // When
    JobAssert actualAssert = jobAssert.hasDueDate(new Date(999));
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasDueDate_Failure() {
    // Given
    when(job.getDuedate()).thenReturn(new Date(999));
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasDueDate(new Date(1000));
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasDueDate_Error_Null() {
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasDueDate(null);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasProcessInstanceId_Success() {
    // Given
    when(job.getProcessInstanceId()).thenReturn("processInstanceId");
    // When
    JobAssert actualAssert = jobAssert.hasProcessInstanceId("processInstanceId");
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasProcessInstanceId_Failure() {
    // Given
    when(job.getProcessInstanceId()).thenReturn("processInstanceId");
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasProcessInstanceId("otherProcessInstanceId");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasProcessInstanceId_Error_Null() {
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasProcessInstanceId(null);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasExecutionId_Success() {
    // Given
    when(job.getExecutionId()).thenReturn("executionId");
    // When
    JobAssert actualAssert = jobAssert.hasExecutionId("executionId");
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasExecutionId_Failure() {
    // Given
    when(job.getExecutionId()).thenReturn("executionId");
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasExecutionId("otherExecutionId");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasExecutionId_Error_Null() {
    try {
    // When
      ProcessEngineAssertions.assertThat(job).hasExecutionId(null);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {      
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasRetries_Success() {
    // Given
    when(job.getRetries()).thenReturn(3);
    // When
    JobAssert actualAssert = jobAssert.hasRetries(3);
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasRetries_Failure() {
    // Given
    when(job.getRetries()).thenReturn(3);
    try {
    // When
      JobAssert actualAssert = jobAssert.hasRetries(2);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasExceptionMessage_Success() {
    // Given
    when(job.getExceptionMessage()).thenReturn("exceptionMessage");
    // When
    JobAssert actualAssert = jobAssert.hasExceptionMessage();
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasExceptionMessage_Failure() {
    try {
    // When
      JobAssert actualAssert = jobAssert.hasExceptionMessage();
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasDeploymentId_Success() {
    // Given
    when(job.getDeploymentId()).thenReturn("deploymentId");
    // When
    JobAssert actualAssert = jobAssert.hasDeploymentId("deploymentId");
    // Then
    assertThat(actualAssert).isSameAs(jobAssert);
  }

  @Test
  public void testHasDeploymentId_Failure() {
    // Given
    when(job.getDeploymentId()).thenReturn("deploymentId");
    try {
      // When
      ProcessEngineAssertions.assertThat(job).hasDeploymentId("otherDeploymentId");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  public void testHasDeploymentId_Error_Null() {
    try {
      // When
      ProcessEngineAssertions.assertThat(job).hasDeploymentId(null);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
