package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.impl.calendar.DateTimeUtil;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasDueDateTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Success() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // Then
    assertThat(jobQuery().singleResult()).hasDueDate(jobQuery().singleResult().getDuedate());
  }

  @Test
  @Deployment(resources = {"bpmn/JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Failure() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasDueDate(DateTimeUtil.now().minusDays(1).toDate());
      }
    });
  }

  @Test
  @Deployment(resources = {"bpmn/JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Error_Null() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.assertThat(jobQuery().singleResult()).hasDueDate(null);
      }
    });
  }

}
