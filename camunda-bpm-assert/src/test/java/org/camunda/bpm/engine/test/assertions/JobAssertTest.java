package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.test.assertions.helpers.Check;
import org.camunda.bpm.engine.test.assertions.helpers.FailingTestCaseHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertTest extends FailingTestCaseHelper {
  
  ProcessEngine processEngine;
  Job job;
  JobAssert jobAssert;

  @Before
  public void setUp() {
    processEngine = mock(ProcessEngine.class);
    job = mock(Job.class);
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(job).hasId("otherId");
      }
    });
  }

  @Test
  public void testHasId_Error_Null() {
    // Then
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasId(null);
      }
    });
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasDueDate(new Date(1000));
      }
    });
  }

  @Test
  public void testHasDueDate_Error_Null() {
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasDueDate(null);
      }
    });
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasProcessInstanceId("otherProcessInstanceId");
      }
    });
  }

  @Test
  public void testHasProcessInstanceId_Error_Null() {
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasProcessInstanceId(null);
      }
    });
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasExecutionId("otherExecutionId");
      }
    });
  }

  @Test
  public void testHasExecutionId_Error_Null() {
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasExecutionId(null);
      }
    });
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        JobAssert actualAssert = jobAssert.hasRetries(2);
      }
    });
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
    failure(new Check() {
      @Override
      public void when() {
        jobAssert.hasExceptionMessage();
      }
    });
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
    // Then
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasDeploymentId("otherDeploymentId");
      }
    });
  }

  @Test
  public void testHasDeploymentId_Error_Null() {
    failure(new Check() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job).hasDeploymentId(null);
      }
    });
  }

}
