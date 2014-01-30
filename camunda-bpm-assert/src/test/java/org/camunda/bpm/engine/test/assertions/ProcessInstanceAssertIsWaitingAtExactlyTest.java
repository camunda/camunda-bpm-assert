package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Check;
import org.camunda.bpm.engine.test.assertions.helpers.FailingTestCaseHelper;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsWaitingAtExactlyTest extends FailingTestCaseHelper {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Only_Activity_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    assertThat(processInstance).isWaitingAtExactly("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Only_Activity_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingAtExactly("UserTask_2");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Non_Existing_Activity_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingAtExactly("NonExistingUserTask");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_One_Of_Two_Activities_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isWaitingAtExactly("UserTask_2", "UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_One_Of_Two_Activities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingAt("UserTask_1", "UserTask_2");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAtExactly_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        //noinspection NullArgumentToVariableArgMethod
        assertThat(processInstance).isWaitingAt(null);
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingAt("ok", null);
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingAt(null, "ok");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        String [] args = new String[] {};
        assertThat(processInstance).isWaitingAt(args);
      }
    });
  }

}
