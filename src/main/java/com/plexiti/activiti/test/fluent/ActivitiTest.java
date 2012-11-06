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

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public abstract class ActivitiTest {
	
	private Map<String, TestProcessInstance> processes = new HashMap<String, TestProcessInstance>();

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	{
		ActivitiRuleHelper.set(activitiRule);
	}
	
	protected TestProcessInstance start(TestProcessInstance testProcess) {
		if (processes.containsKey(testProcess.processDefinitionKey)) {
			return processes.get(testProcess.processDefinitionKey);
		} else {
			processes.put(testProcess.processDefinitionKey, testProcess);
			testProcess.start();
			return testProcess;			
		}
	}
	
	protected TestProcessInstance process(String processDefinitionKey) {
		return processes.get(processDefinitionKey);
	}

	protected TestProcessInstance process() {
		assertThat(processes).hasSize(1);
		return processes.values().iterator().next();
	}

}
