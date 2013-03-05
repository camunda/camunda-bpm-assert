package com.plexiti.activiti.test.fluent.engine;

import org.activiti.engine.identity.User;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author martin.schimak@plexiti.com
 */
public interface TestProcessInstance {

    ProcessInstance getActualProcessInstance();

    void claim(Task task, String userId);

    void claim(Task task, User user);

    void complete(Task task, Object... variables);

    Task currentTask();

    List<Task> currentTasks();

    DiagramLayout diagramLayout();

    Execution execution();

    List<Execution> executions();

    void moveAlong();

    void moveTo(String targetActivity);

    TestProcessInstance start();

    TestProcessInstanceImpl withVariable(String name, Object value);

    TestProcessInstance withVariables(Map<String, Object> variables);

    TestProcessVariable variable(String variableName);

}
