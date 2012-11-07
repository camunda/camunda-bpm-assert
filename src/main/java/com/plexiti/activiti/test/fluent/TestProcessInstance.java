/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plexiti.activiti.test.fluent;

import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.plexiti.helper.Console.println;
import static org.fest.assertions.api.Assertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TestProcessInstance {
	
	protected static final String ActivitiTargetActivity = "ActivitiTargetActivity";

	protected String processDefinitionKey;
	protected ProcessInstance processInstance;
	protected Map<String, Object> processVariables = new HashMap<String, Object>();
	
	public TestProcessInstance(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public List<String> activeActivities(Execution execution) {
		return ActivitiRuleHelper.get().getRuntimeService().getActiveActivityIds(execution.getId());
	}
	
	public String activeActivity(Execution execution) {
		List<String> activeActivities = activeActivities(execution);
		assertThat(activeActivities).as("By calling execution() you implicitedly assumed that exactly one such object exists.").hasSize(1);
		return activeActivities.get(0);
	}
	
	protected ExecutionQuery activitiExecutionQuery() { return ActivitiRuleHelper.get().getRuntimeService().createExecutionQuery(); }

	protected GroupQuery activitiGroupQuery() { return ActivitiRuleHelper.get().getIdentityService().createGroupQuery(); };
	
	protected TaskQuery activitiTaskQuery() { return ActivitiRuleHelper.get().getTaskService().createTaskQuery(); }
	protected UserQuery activitiUserQuery() { return ActivitiRuleHelper.get().getIdentityService().createUserQuery(); }
	public void claim(Task task, String userId) {
		ActivitiRuleHelper.get().getTaskService().claim(currentTask().getId(), userId);
	}
	public void claim(Task task, User user) {
		claim(currentTask(), user.getId());
	}

	public void complete(Task task, Object... variables) {
		ActivitiRuleHelper.get().getTaskService().complete(task.getId(), parseProcessVariables(variables));
	}
	
	public Task currentTask() {
		List<Task> tasks = currentTasks();
		assertThat(tasks).as("By calling task() you implicitedly assumed that exactly one such object exists.").hasSize(1);
		return tasks.get(0);
	}

	public List<Task> currentTasks() {
		return activitiTaskQuery().list();
	}

	public DiagramLayout diagramLayout() {
		DiagramLayout diagramLayout = ActivitiRuleHelper.get().getRepositoryService().getProcessDiagramLayout(processInstance().getProcessDefinitionId());
		assertThat(diagramLayout).overridingErrorMessage("Fatal error. Could not retrieve diagram layout!").isNotNull();				
		return diagramLayout;
	}
	
	public Execution execution() {
		List<Execution> executions = executions();
		if (executions.size() == 0) 
			return null;
		assertThat(executions).as("By calling execution() you implicitedly assumed that at most one such object exists.").hasSize(1);
		return executions.get(0);
	}

	public List<Execution> executions() {
		return activitiExecutionQuery().processInstanceId(processInstance().getId()).list();
	}
	
	public void moveAlong() {}
	
	public void moveTo(String targetActivity) {
		try {
			ActivitiRuleHelper.get().getRuntimeService().setVariable(processInstance.getId(), ActivitiTargetActivity, targetActivity);
			moveAlong(); 
		} catch (ActivitiTargetActivityReached e) {
			ActivitiRuleHelper.get().getRuntimeService().setVariable(processInstance.getId(), ActivitiTargetActivity, null);
		}
	}
	
	protected Map<String, Object> parseProcessVariables(Object... variables) {
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		for (int i=0; variables != null && i < variables.length; i+=2) {
			variablesMap.put((String) variables[i], variables[i+1]);
		}
		
		return variablesMap;
	}
	
	protected ProcessInstance processInstance() {
		assertThat(processInstance).overridingErrorMessage("No process instantiated yet. Call start(process) first!").isNotNull();
		return processInstance;
	}
	
	public TestProcessInstance start() {
		processVariables.put(ActivitiTargetActivity, null);
		processInstance = ActivitiRuleHelper.get().getRuntimeService().startProcessInstanceByKey(processDefinitionKey, processVariables); 
		println("Started process '" + processDefinitionKey +  "' (definition id: '" + processInstance.getProcessDefinitionId() + "', instance id: '" + processInstance.getId() + "').");			
		return this;
	}

	public TestProcessInstance withVariable(String name, Object value) {
		assertThat(processInstance).overridingErrorMessage("Process already started. Call start() after having set up all necessary process variables.").isNull();
		this.processVariables.put(name, value);
		return this;
	}
	
	protected static class ActivitiTargetActivityReached extends RuntimeException {
		private static final long serialVersionUID = 2282185191899085294L;
	}

}
