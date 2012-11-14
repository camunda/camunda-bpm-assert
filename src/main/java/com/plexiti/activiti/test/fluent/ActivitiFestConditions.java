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

import org.activiti.engine.history.HistoricActivityInstance;
import org.fest.assertions.api.ListAssert;
import org.fest.assertions.core.Condition;

import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.fest.assertions.data.Index;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFestConditions {
	
	public static Condition<DiagramLayout> containingNode(final String nodeId) {
		return new Condition<DiagramLayout>() {
			@Override
			public boolean matches(DiagramLayout diagramLayout) {
				assertThat(nodeId).isNotNull();
				return diagramLayout.getNode(nodeId) != null;
			}
		};
	}
}
