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

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.*;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.ActivitiTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;

import com.plexiti.activiti.test.fluent.mocking.Mockitos;
import com.plexiti.activiti.test.fluent.assertions.Assertions.*;
import static org.fest.assertions.api.Assertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public abstract class ActivitiFluentTest extends ActivitiTestCase {

    private Map<String, TestProcessInstance> processes = new HashMap<String, TestProcessInstance>();

    @Before
    public void setUp() throws Exception {
        super.setUp();

        /*
         * We need to be able to reach the Activiti engine services from
         * the fluent assertion classes later.
         */
        ActivitiFluentTestHelper.setUpEngineAndServices(
                processEngine,
                repositoryService,
                runtimeService,
                taskService,
                historicDataService,
                identityService,
                managementService,
                formService
        );

        /*
         * Initialize @Mock annotations and register the mocks
         */
        MockitoAnnotations.initMocks(this);
        Mockitos.register(this);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
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
