package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasCandidateGroupTest extends ProcessAssertTestCase {

  private static final String CANDIDATE_GROUP = "candidateGroup";
  private static final String ASSIGNEE = "assignee";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // Then
    assertThat(processInstance).task().hasCandidateGroup(CANDIDATE_GROUP);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup(CANDIDATE_GROUP);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    taskService().deleteCandidateGroup(taskQuery().singleResult().getId(), CANDIDATE_GROUP);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup(CANDIDATE_GROUP);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    taskService().deleteCandidateGroup(taskQuery().singleResult().getId(), CANDIDATE_GROUP);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup(CANDIDATE_GROUP);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
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
        assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
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
        assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    assertThat(processInstance).task().hasCandidateGroup(CANDIDATE_GROUP);
    // And
    assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    taskService().addCandidateGroup(taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateGroup(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_NonExistingTask_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    final Task task = taskQuery().singleResult();
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateGroup(CANDIDATE_GROUP);
      }
    });
  }

  @Test
  @Deployment(resources = {
      "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_Assigned_Failure() {
    // Given
    final ProcessInstance pi = runtimeService().startProcessInstanceByKey(
        "TaskAssert-hasCandidateGroup"
    );
    // When
    claim(task(pi), ASSIGNEE);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task(pi)).hasCandidateGroup(CANDIDATE_GROUP);
      }
    });
  }
  
}
