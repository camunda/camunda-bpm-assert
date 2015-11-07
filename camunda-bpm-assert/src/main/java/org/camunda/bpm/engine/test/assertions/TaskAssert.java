package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.db.DbSqlSession;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.assertj.core.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Assertions for a {@link Task}
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael@cordones.me>
 */
public class TaskAssert extends AbstractProcessAssert<TaskAssert, Task> {

  private static final String CANDIDATES_QUERY = "SELECT GROUP_ID_, USER_ID_ FROM ACT_RU_IDENTITYLINK WHERE TASK_ID_ = ?";

  protected TaskAssert(final ProcessEngine engine, final Task actual) {
    super(engine, actual, TaskAssert.class);
  }

  protected static TaskAssert assertThat(final ProcessEngine engine, final Task actual) {
    return new TaskAssert(engine, actual);
  }

  @Override
  protected Task getCurrent() {
    return taskQuery().taskId(actual.getId()).singleResult();
  }

  /**
   * Verifies the expectation that the {@link Task} is currently not assigned to 
   * any particular user.
   * 
   * @return  this {@link TaskAssert}
   */  
  public TaskAssert isNotAssigned() {
    Task current = getExistingCurrent();
    Assertions.assertThat(current.getAssignee())
      .overridingErrorMessage("Expecting %s not to be assigned, but found it to be assigned to user '%s'!",
        toString(current),
        current.getAssignee())
      .isNull();
    return this;
  }

  /**
   * Verifies the expectation that the {@link Task} is currently assigned to 
   * the specified user.
   * 
   * @param   userId id of the user the task should be currently assigned to.
   * @return  this {@link TaskAssert}
   */
  public TaskAssert isAssignedTo(final String userId) {
    Task current = getExistingCurrent();
    Assertions
      .assertThat(current.getAssignee())
      .overridingErrorMessage("Expecting %s to be assigned to user '%s', but found it to be assigned to '%s'!", 
        toString(current), 
        userId,
        current.getAssignee())
      .isEqualTo(userId);
    return this;
  }

  /**
   * Verifies the expectation that the {@link Task} is currently waiting to 
   * be assigned to a user of the specified candidate group.
   * 
   * @param   candidateGroupId id of the candidate group the task is assigned to
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasCandidateGroup(final String candidateGroupId) {
    return hasCandidateGroups(candidateGroupId);
  }

  /**
   * Verifies the expectation that the {@link Task} is currently waiting to
   * be assigned to a user of the specified candidate groups.
   *
   * @param   candidateGroupIds ids of the candidate groups the task is assigned to
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasCandidateGroups(final String... candidateGroupIds) {
    return hasCandidates("GROUP_ID_", "candidate groups", candidateGroupIds);
  }

  /**
   * Verifies the expectation that the {@link Task} is currently waiting to 
   * be assigned to a specified candidate user.
   *
   * @see #hasCandidateUsers(String...)
   * @param   candidateUserId id of the candidate user the task is assigned to
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasCandidateUser(final String candidateUserId) {
   return hasCandidateUsers(candidateUserId);
  }

  /**
   * Verifies the expectation that the {@link Task} is currently waiting to
   * be assigned to all of the specified candidate users.
   *
   * @param candidateUserIds ids of the candidate users the task is assigned to
   * @return this {@link TaskAssert}
   */
  public TaskAssert hasCandidateUsers(final String... candidateUserIds) {
    return hasCandidates("USER_ID_", "candidate users", candidateUserIds);
  }

  /**
   * Common implementation for candidate assertions, both candidate users and groups.
   *
   * @param field        the field in ACT_RU_IDENTITYLINK to query, one of GROUP_ID_ or USER_ID_.
   * @param name         the name to display in the error message (candidate users, candidate groups).
   * @param candidateIds the ids to assert on (either groups or users).
   * @return this {@link TaskAssert}
   */
  private TaskAssert hasCandidates(String field, String name, String... candidateIds) {
    Assertions.assertThat(candidateIds).isNotNull().isNotEmpty();
    final Task current = getExistingCurrent();

    List<String> actualCandidateIds = findIdentityLinks(current.getId(), field);

    Assertions.assertThat(actualCandidateIds)
        .overridingErrorMessage("Expecting %s to have %s '%s', but found %s!",
            toString(current),
            name,
            Arrays.asList(candidateIds),
            actualCandidateIds)
        .contains(candidateIds);

    return this;
  }

  /**
   * Verifies the due date of a {@link Task}.
   * 
   * @param   dueDate the date the task should be due at
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasDueDate(final Date dueDate) {
    Task current = getExistingCurrent();
    Assertions.assertThat(dueDate).isNotNull();
    Assertions.assertThat(current.getDueDate())
        .overridingErrorMessage("Expecting %s to be due at '%s', but found it to be due at '%s'!",
          toString(current),
          dueDate, 
          current.getDueDate()
        )
      .isEqualTo(dueDate);
    return this;
  }

  /**
   * Verifies the definition key of a {@link Task}. This key can be found
   * in the &lt;userTask id="myTaskDefinitionKey" .../&gt; attribute of the
   * process definition BPMN 2.0 XML file.
   * 
   * @param   taskDefinitionKey the expected value of the task/@id attribute
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasDefinitionKey(final String taskDefinitionKey) {
    Task current = getExistingCurrent();
    Assertions.assertThat(taskDefinitionKey).isNotNull();
    Assertions.assertThat(current.getTaskDefinitionKey())
      .overridingErrorMessage("Expecting %s to have definition key '%s', but found it to have '%s'!",
        toString(current),
        taskDefinitionKey,
        current.getTaskDefinitionKey()
      ).isEqualTo(taskDefinitionKey);
    return this;
  }
  
  /**
   * Verifies the expectation that the {@link Task} has a specified form key.
   * 
   * @param   formKey the expected form key.
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasFormKey(String formKey) {
    Task current = getExistingCurrent();
    String actualformKey = formService().getTaskFormKey(current.getProcessDefinitionId(), current.getTaskDefinitionKey());
    Assertions.assertThat(actualformKey)
      .overridingErrorMessage("Expecting %s to have a form key '%s', but found it to to have form key '%s'!", toString(current), formKey, actualformKey)
      .isEqualTo(formKey);
    return this;
  }
  
  /**
   * Verifies the internal id of a {@link Task}.
   * 
   * @param   id the expected value of the internal task id
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasId(final String id) {
    Task current = getExistingCurrent();
    Assertions.assertThat(id).isNotNull();
    Assertions.assertThat(current.getId())
      .overridingErrorMessage("Expecting %s to have internal id '%s', but found it to be '%s'!",
        toString(current),
        id,
        current.getId()
      ).isEqualTo(id);
    return this;
  }

  /**
   * Verifies the name (label) of a {@link Task}. This name can be found
   * in the &lt;userTask name="myName" .../&gt; attribute of the
   * process definition BPMN 2.0 XML file.
   * 
   * @param   name the expected value of the name
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasName(final String name) {
    Task current = getExistingCurrent();
    Assertions.assertThat(name).isNotNull();
    Assertions.assertThat(current.getName())
      .overridingErrorMessage("Expecting %s to have name '%s', but found it to be '%s'!",
        toString(current),
        name,
        current.getName()
      ).isEqualTo(name);
    return this;
  }

  /**
   * Verifies the description of a {@link Task}. This description can be found
   * in the &lt;userTask&gt;&lt;documentation&gt;description&lt;/documentation&gt;&lt;/userTask&gt; 
   * element of the process definition BPMN 2.0 XML file.
   * 
   * @param   description the expected value of the description
   * @return  this {@link TaskAssert}
   */
  public TaskAssert hasDescription(final String description) {
    Task current = getExistingCurrent();
    Assertions.assertThat(description).isNotNull();
    Assertions.assertThat(current.getDescription())
      .overridingErrorMessage("Expecting %s to have description '%s', but found it to be '%s'!", 
        toString(current),
        description,
        current.getDescription())
      .isEqualTo(description);
    return this;
  }

  @Override
  protected String toString(Task task) {
    return task != null ? 
      String.format("actual %s {" +
        "id='%s', " +
        "processInstanceId='%s', " +
        "taskDefinitionKey='%s', " +
        "name='%s'" +
        "}",
        Task.class.getSimpleName(),
        task.getId(),
        task.getProcessInstanceId(),
        task.getTaskDefinitionKey(),
        task.getName()
      ) : null;
  }

  /* TaskQuery, automatically narrowed to {@link ProcessInstance} of actual 
   * {@link Task} 
   */
  @Override
  protected TaskQuery taskQuery() {
    return super.taskQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* JobQuery, automatically narrowed to {@link ProcessInstance} of actual 
   * {@link Task} 
   */
  @Override
  protected JobQuery jobQuery() {
    return super.jobQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ProcessInstanceQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected ProcessInstanceQuery processInstanceQuery() {
    return super.processInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ExecutionQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected ExecutionQuery executionQuery() {
    return super.executionQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* VariableInstanceQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected VariableInstanceQuery variableInstanceQuery() {
    return super.variableInstanceQuery().processInstanceIdIn(actual.getProcessInstanceId());
  }

  /* HistoricActivityInstanceQuery, automatically narrowed to {@link ProcessInstance} of actual {@link Task} */
  @Override
  protected HistoricActivityInstanceQuery historicActivityInstanceQuery() {
    return super.historicActivityInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricDetailQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected HistoricDetailQuery historicDetailQuery() {
    return super.historicDetailQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricProcessInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricProcessInstanceQuery historicProcessInstanceQuery() {
    return super.historicProcessInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricTaskInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricTaskInstanceQuery historicTaskInstanceQuery() {
    return super.historicTaskInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricVariableInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricVariableInstanceQuery historicVariableInstanceQuery() {
    return super.historicVariableInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ProcessDefinitionQuery, automatically narrowed to {@link ProcessDefinition} 
   * of {@link ProcessInstance} of actual {@link Task} 
   */
  @Override
  protected ProcessDefinitionQuery processDefinitionQuery() {
    return super.processDefinitionQuery().processDefinitionId(actual.getProcessDefinitionId());
  }

  /**
   * Helper function that executes a native SQL query on QCT_RU_IDENTITYLINKS for current Task and field.
   *
   * @param field one of "GROUP_ID_", "USER_ID_"
   * @return list of candidates ids for comparison
   */
  private List<String> findIdentityLinks(final String taskId, final String field) {
    final ProcessEngineConfigurationImpl configuration = ((ProcessEngineImpl) engine).getProcessEngineConfiguration();
    final CommandExecutor executor = configuration.getCommandExecutorTxRequired();

    return configuration.getCommandExecutorTxRequired().execute(new Command<List<String>>() {
      @Override
      public List<String> execute(CommandContext commandContext) {
        final List<String> actualCandidateGroups = new ArrayList<String>();
        Connection c = commandContext
            .getSession(DbSqlSession.class).getSqlSession().getConnection();
        PreparedStatement preparedStatement = null;
        try {
          preparedStatement = c.prepareStatement(CANDIDATES_QUERY);
          preparedStatement.setString(1, taskId);

          final ResultSet resultSet = preparedStatement.executeQuery();

          while (resultSet.next()) {
            actualCandidateGroups.add(resultSet.getString(field));
          }

        } catch (SQLException e) {
          throw new RuntimeException(e);
        } finally {
          try {
            preparedStatement.close();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }

        return actualCandidateGroups;
      }
    });
  }

}
