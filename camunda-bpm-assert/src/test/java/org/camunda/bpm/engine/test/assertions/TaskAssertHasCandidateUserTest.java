package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
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
public class TaskAssertHasCandidateUserTest extends ProcessAssertTestCase {

  private static final String CANDIDATE_USER = "candidateUser";
  private static final String ASSIGNEE = "assignee";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUserPreDefined_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // Then
    assertThat(processInstance).task().hasCandidateUser(CANDIDATE_USER);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), CANDIDATE_USER);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), CANDIDATE_USER);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    assertThat(processInstance).task().hasCandidateUser(CANDIDATE_USER);
    // And
    assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUser(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_NonExistingTask_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    final Task task = taskQuery().singleResult();
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateUser(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_Assigned_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    final Task task = taskQuery().singleResult();
    taskService().setAssignee(task.getId(), ASSIGNEE);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateUser(CANDIDATE_USER);
      }
    });
  }

}
