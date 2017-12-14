package org.camunda.bpm.engine.test.assertions.bpmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;

public class BpmnAwareTestsTest {

  @Test
  public void completeShouldThrowIllegalArgumentExceptionOnNullTask() throws Exception {
    try {
      complete(null);
      fail("IllegalArgumentException expected as task is null");
    } catch (IllegalArgumentException e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Illegal call of complete");
    }
  }

  @Test
  public void completeShouldThrowIllegalArgumentExceptionOnNullVariables() throws Exception {
    try {
      complete(new TaskEntity(), null);
      fail("IllegalArgumentException expected as variables is null");
    } catch (IllegalArgumentException e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Illegal call of complete");
    }
  }
}
