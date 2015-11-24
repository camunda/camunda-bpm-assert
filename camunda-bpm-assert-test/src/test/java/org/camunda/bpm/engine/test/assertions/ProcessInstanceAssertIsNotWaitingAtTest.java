package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsNotWaitingAtTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_Only_Activity_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // Then
    assertThat(processInstance).isNotWaitingAt("UserTask_2");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_Only_Activity_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt("UserTask_1");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_Non_Existing_Activity_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // Then
    assertThat(processInstance).isNotWaitingAt("NonExistingUserTask");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_One_Of_Two_Activities_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isNotWaitingAt("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_One_Of_Two_Activities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt("UserTask_2");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt("UserTask_1", "UserTask_2");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt("UserTask_2", "UserTask_3");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingAt.bpmn"
  })
  public void testIsNotWaitingAt_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingAt"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        //noinspection NullArgumentToVariableArgMethod
        assertThat(processInstance).isNotWaitingAt(null);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt("ok", null);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingAt(null, "ok");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        String[] args = new String[]{};
        assertThat(processInstance).isNotWaitingAt(args);
      }
    });
  }

}
