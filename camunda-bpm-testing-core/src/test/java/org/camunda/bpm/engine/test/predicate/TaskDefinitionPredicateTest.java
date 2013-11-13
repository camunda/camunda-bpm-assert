package org.camunda.bpm.engine.test.predicate;

import static org.camunda.bpm.engine.test.predicate.TaskDefinitionPredicate.ALWAYS_FALSE;
import static org.camunda.bpm.engine.test.predicate.TaskDefinitionPredicate.formkeyIsSet;
import static org.camunda.bpm.engine.test.predicate.TaskDefinitionPredicate.limitTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.impl.form.DefaultTaskFormHandler;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TaskDefinitionPredicateTest {

  private static final String TASK_KEY = "task_foo_bar";
  @Mock
  private TaskDefinition taskDefinition;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    when(taskDefinition.getKey()).thenReturn(TASK_KEY);
  }

  @Test
  public void should_evaluate_applyFor_for_Given_TaskKey() throws Exception {
    assertThat(limitTo(TASK_KEY, ALWAYS_FALSE).apply(taskDefinition), is(false));
    assertThat(limitTo("xxx", ALWAYS_FALSE).apply(taskDefinition), is(true));
  }

  @Test
  public void should_evaluate_candidate_user_groups_not_null() throws Exception {

  }

  @Test
  public void should_return_false_if_form_key_is_set() throws Exception {
    assertFalse("formkey should not be set", formkeyIsSet().apply(taskDefinition));
  }

  @Test
  public void should_return_true_if_form_key_is_set() throws Exception {
    final Expression expression = mock(Expression.class);
    when(expression.getExpressionText()).thenReturn("xxx");

    final DefaultTaskFormHandler handler = mock(DefaultTaskFormHandler.class);
    when(handler.getFormKey()).thenReturn(expression);
    when(taskDefinition.getTaskFormHandler()).thenReturn(handler);

    assertTrue("formkey should be set", formkeyIsSet().apply(taskDefinition));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailForTaskDefinitionNull() throws Exception {
    new TaskDefinitionPredicate() {

      @Override
      public boolean applyNullSafe(final TaskDefinition input) {
        return false;
      }

    }.apply(null);
  }

}
