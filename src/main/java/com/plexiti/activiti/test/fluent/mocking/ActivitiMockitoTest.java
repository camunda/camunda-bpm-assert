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
package com.plexiti.activiti.test.fluent.mocking;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import com.plexiti.activiti.test.fluent.ActivitiTest;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ActivitiMockitoTest extends ActivitiTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		Mockitos.register(this); 
	}

}
