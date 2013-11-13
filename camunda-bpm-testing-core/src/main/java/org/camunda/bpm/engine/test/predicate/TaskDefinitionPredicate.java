package org.camunda.bpm.engine.test.predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.camunda.bpm.engine.impl.bpmn.parser.TaskDefinitionBpmnParseListener.getFormKeyExpressionText;

import org.camunda.bpm.engine.impl.task.TaskDefinition;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public abstract class TaskDefinitionPredicate implements Predicate<TaskDefinition> {

  protected static final TaskDefinitionPredicate ALWAYS_FALSE = new TaskDefinitionPredicate() {

    @Override
    public boolean applyNullSafe(final TaskDefinition input) {
      return false;
    }
  };

  protected static final TaskDefinitionPredicate ALWAYS_TRUE = new TaskDefinitionPredicate() {

    @Override
    public boolean applyNullSafe(final TaskDefinition input) {
      return true;
    }
  };

  public static TaskDefinitionPredicate and(final TaskDefinitionPredicate... predicates) {
    return (TaskDefinitionPredicate) Predicates.and(predicates);
  }

  /**
   * Returns <code>true</code> if the taskDefinitionId does not match the parsed
   * key. Otherwise, the predicate is evaluated.
   * 
   * @param taskDefinitionId
   * @param predicate
   * @return
   */
  public static final TaskDefinitionPredicate limitTo(final String taskDefinitionId, final TaskDefinitionPredicate predicate) {
    checkArgument(isNotBlank(taskDefinitionId), "taskDefinitionId must not be blank!");
    checkArgument(predicate != null, "predicate must not be null!");

    return new TaskDefinitionPredicate() {

      @Override
      public boolean applyNullSafe(final TaskDefinition taskDefinition) {
        return !taskDefinitionId.equals(taskDefinition.getKey()) || predicate.apply(taskDefinition);
      }
    };
  }

  /**
   * Returns <code>true</code> if the formkey expression is set (not blank).
   */
  public static TaskDefinitionPredicate formkeyIsSet() {
    return new TaskDefinitionPredicate() {

      @Override
      public String getMessage() {
        return "formKey is not set for task: %s";
      }

      @Override
      public boolean applyNullSafe(final TaskDefinition taskDefinition) {
        return isNotBlank(getFormKeyExpressionText(taskDefinition));
      }
    };
  }

  public static TaskDefinitionPredicate candidateGroupsIsSet() {
    return new TaskDefinitionPredicate() {
      @Override
      public String getMessage() {
        return "candidateGroups is not set for task: %s";
      }

      @Override
      public boolean applyNullSafe(final TaskDefinition taskDefinition) {
        return false;
      }
    };
  }

  @Override
  public boolean apply(final TaskDefinition taskDefinition) {
    checkArgument(taskDefinition != null, "taskDefinition must not be null!");
    return applyNullSafe(taskDefinition);
  }

  public String getMessage() {
    return "taskDefinition predicate failed!";
  }

  public abstract boolean applyNullSafe(TaskDefinition taskDefinition);

}
