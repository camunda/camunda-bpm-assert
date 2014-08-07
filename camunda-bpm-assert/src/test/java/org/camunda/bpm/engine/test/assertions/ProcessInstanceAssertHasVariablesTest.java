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
public class ProcessInstanceAssertHasVariablesTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_One_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", withVariables("aVariable", "aValue")
    );
    // Then
    assertThat(processInstance).hasVariables();
    // And
    assertThat(processInstance).hasVariables("aVariable");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_One_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", withVariables("aVariable", "aValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("aVariable", "anotherVariable");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_Two_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", withVariables("firstVariable", "firstValue", "secondVariable", "secondValue")
    );
    // Then
    assertThat(processInstance).hasVariables();
    // And
    assertThat(processInstance).hasVariables("firstVariable");
    // And
    assertThat(processInstance).hasVariables("secondVariable");
    // And
    assertThat(processInstance).hasVariables("firstVariable", "secondVariable");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_Two_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", withVariables("firstVariable", "firstValue", "secondVariable", "secondValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("firstVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("secondVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("firstVariable", "secondVariable", "anotherVariable");
      }
    });
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_None_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables();
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasVariables("aVariable");
      }
    });
  }

}
