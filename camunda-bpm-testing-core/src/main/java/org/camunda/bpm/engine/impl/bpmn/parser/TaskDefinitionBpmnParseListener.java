package org.camunda.bpm.engine.impl.bpmn.parser;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.form.DefaultFormHandler;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.camunda.bpm.engine.test.predicate.TaskDefinitionPredicate;

/**
 * Specialization of the {@link BpmnParseListener} that focusses on analysing
 * {@link TaskDefinition}s.
 */
public abstract class TaskDefinitionBpmnParseListener extends AbstractBpmnParseListener {

  /**
   * 
   * @param predicates
   *          group of predicates
   * @return a Listener evaluating every predicate.
   */
  public static TaskDefinitionBpmnParseListener assertTaskDefinitionConstraints(final TaskDefinitionPredicate... predicates) {
    return new TaskDefinitionBpmnParseListener() {

      @Override
      public void parseUserTask(final TaskDefinition taskDefinition) {
        for (final TaskDefinitionPredicate predicate : predicates) {
          assertTrue(format(predicate.getMessage(), taskDefinition.getKey()), predicate.apply(taskDefinition));
        }
      }
    };

  }

  /**
   * null-safe getter for the tasks formKey.
   * 
   * @param taskDefinition
   *          the parsed task definition
   * @return the value of {@link DefaultFormHandler#getFormKey()}
   *         .getExpressionText()
   */
  public static final String getFormKeyExpressionText(final TaskDefinition taskDefinition) {
    if (taskDefinition == null || taskDefinition.getTaskFormHandler() == null) {
      return null;
    }

    final DefaultFormHandler defaultFormHandler = (DefaultFormHandler) taskDefinition.getTaskFormHandler();
    return defaultFormHandler.getFormKey() != null ? defaultFormHandler.getFormKey().getExpressionText() : null;
  }

  @Override
  public final void parseUserTask(final Element userTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
    // delegate this general call to concrete TaskDefinition method
    final ActivityBehavior behavior = activity.getActivityBehavior();
    if (behavior instanceof UserTaskActivityBehavior) {
      parseUserTask(((UserTaskActivityBehavior) behavior).getTaskDefinition());
    }
  }

  /**
   * 
   * @param taskDefinition
   *          the parsed {@link TaskDefinition}
   */
  public abstract void parseUserTask(final TaskDefinition taskDefinition);
}
