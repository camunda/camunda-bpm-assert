package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsJobTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(job()).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        job();
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(job());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        job();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(job("ServiceTask_1")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(job());
    // Then
    assertThat(job("ServiceTask_2")).isNotNull();
    // And
    assertThat(job("ServiceTask_3")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        job("ServiceTask_1");
      }
    }, IllegalStateException.class);
  }
  
  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(job(jobQuery())).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        job(jobQuery());
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(job());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        job(jobQuery());
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    assertThat(job(processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_TwoActivities_processInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(job(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        job(processInstance);
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    assertThat(job("ServiceTask_1", processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobDefinitionKey_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // When
    execute(job(processInstance));
    // Then
    assertThat(job("ServiceTask_2", processInstance)).isNotNull();
    // And
    assertThat(job("ServiceTask_3", processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    assertThat(job(jobQuery(), processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_processInstance_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(job(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        job(jobQuery(), processInstance);
      }
    }, ProcessEngineException.class);
  }

}
