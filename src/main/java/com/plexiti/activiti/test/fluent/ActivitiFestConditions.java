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

import static org.fest.assertions.api.Assertions.*;
import org.fest.assertions.core.Condition;

import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFestConditions {
	
	public static Condition<Task> assignedTo(final String userId) {
		return new Condition<Task>() {
			@Override
			public boolean matches(Task task) {
				assertThat(userId).isNotNull();
				return userId.equals(task.getAssignee());
			}
		};
	}
	
	public static Condition<Execution> atActivity(final String nodeId) {
		return new Condition<Execution>() {
			@Override
			public boolean matches(Execution execution) {
				assertThat(nodeId).isNotNull();
				assertThat(execution).isNotNull();

				String targetActivity = (String) ActivitiRuleHelper.get().getRuntimeService().getVariableLocal(execution.getId(), TestProcessInstance.ActivitiTargetActivity);
				boolean atActivity = ActivitiRuleHelper.get().getRuntimeService().getActiveActivityIds(execution.getId()).contains(nodeId);
				if (nodeId.equals(targetActivity) && atActivity) {
					throw new TestProcessInstance.ActivitiTargetActivityReached();
				}
				return atActivity;
			}
		};
	}

	public static Condition<DiagramLayout> containingNode(final String nodeId) {
		return new Condition<DiagramLayout>() {
			@Override
			public boolean matches(DiagramLayout diagramLayout) {
				assertThat(nodeId).isNotNull();
				return diagramLayout.getNode(nodeId) != null;
			}
		};
	}

	public static Condition<Execution> finished() {
		return new Condition<Execution>() {
			@Override
			public boolean matches(Execution execution) {
				return execution == null || execution.isEnded();
			}
		};
	}
	
	public static Condition<Task> inCandidateGroup(final String groupId) {
		return new Condition<Task>() {
			@Override
			public boolean matches(Task task) {
				assertThat(groupId).isNotNull();
				return ActivitiRuleHelper.get().getTaskService().createTaskQuery().taskId(task.getId()).taskCandidateGroup(groupId).singleResult() != null;
			}
		};
	}

	public static Condition<Execution> started() {
		return new Condition<Execution>() {
			@Override
			public boolean matches(Execution execution) {
				return execution != null && !execution.isEnded();
			}
		};
	}

	public static Condition<Task> unassigned() {
		return new Condition<Task>() {
			@Override
			public boolean matches(Task task) {
				return task.getAssignee() == null;
			}
		};
	}
	
}
