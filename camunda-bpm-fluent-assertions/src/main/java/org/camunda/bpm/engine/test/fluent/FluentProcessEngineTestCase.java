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
package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.junit.After;
import org.junit.Before;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public abstract class FluentProcessEngineTestCase extends ProcessEngineTestCase {

    private FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Override @Before
    protected void setUp() throws Exception {
        super.setUp();
        bpmnFluentTestRule.before();
    }

    @Override @After
    public void tearDown() throws Exception {
        super.tearDown();
        bpmnFluentTestRule.after();
    }

}
