package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasCandidateGroupAssociatedTest extends ProcessAssertTestCase {

  private static final String CANDIDATE_GROUP = "candidateGroup";
  private static final String ASSIGNEE = "assignee";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();
  
  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_PreDefined_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // Then
    assertThat(processInstance).task().hasCandidateGroupAssociated("candidateGroup");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    taskService().deleteCandidateGroup(taskQuery().singleResult().getId(), "candidateGroup");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    taskService().deleteCandidateGroup(taskQuery().singleResult().getId(), "candidateGroup");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    assertThat(processInstance).task().hasCandidateGroupAssociated("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // When
    taskService().deleteCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("explicitCandidateGroupId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    assertThat(processInstance).task().hasCandidateGroupAssociated("candidateGroup");
    // And
    assertThat(processInstance).task().hasCandidateGroupAssociated("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroupAssociated(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_NonExistingTask_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    final Task task = taskQuery().singleResult();
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateGroupAssociated("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
      "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_Assigned_Success() {
    // Given
    final ProcessInstance pi = runtimeService().startProcessInstanceByKey(
        "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    claim(task(pi), ASSIGNEE);
    // Then
    assertThat(task(pi)).hasCandidateGroupAssociated(CANDIDATE_GROUP);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroupAssociated.bpmn"
  })
  public void testHasCandidateGroupAssociated_Assigned_Failure() {
    // Given
    final ProcessInstance pi = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroupAssociated"
    );
    // When
    taskService().deleteCandidateGroup(task(pi).getId(), CANDIDATE_GROUP);
    // And
    claim(task(pi), ASSIGNEE);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task(pi)).hasCandidateGroupAssociated(CANDIDATE_GROUP);
      }
    });
  }
  
}
