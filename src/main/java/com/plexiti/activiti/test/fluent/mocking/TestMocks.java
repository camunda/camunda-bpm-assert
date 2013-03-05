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

import org.activiti.engine.test.mock.Mocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TestMocks {

	private static Logger log = Logger.getLogger(TestMocks.class.getName());

    public static void init(Object junitTest) {
        initMockito(junitTest);
    }

    /**
     * FIXME list
     *
     *  - Give a better error when mocked services have been declared as private class members
     */
	private static void initMockito(Object junitTest)
	{
        MockitoAnnotations.initMocks(junitTest);
		Class<?> clazz = junitTest.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i=0; i<fields.length; i++ ) {
			final Mock annotation = fields[i].getAnnotation(Mock.class);
			final boolean isAnnotated = annotation != null;
			final String classNameContains = "Mockito";
			try {
				Object value = fields[i].get(junitTest);
				if (value == null && isAnnotated) {
					throw new RuntimeException("Could not retrieve mock object bound to field '" + fields[i].getName() + "' of test class '" + clazz.getSimpleName() + "'. Value is null.");					
				} else if (value == null) {
					log.info("Could not inspect object bound to field '" + fields[i].getName() + "' of test class '" + clazz.getSimpleName() + "'. Value is null.");
				} else if (isAnnotated || value.getClass().getName().contains(classNameContains)) {
					String key = isAnnotated && annotation.name() != null && !annotation.name().equals("") ? annotation.name() : fields[i].getName();
					log.info("Due to " + (isAnnotated ? "Annotation" : "class name containing expected string '" + classNameContains + "'") + " registered object bound to field '" + key + "' of test class '" + clazz.getSimpleName() + "' as mock associated with key '" + key + "'.");
					Mocks.register(key, value);
				} else {
					log.info("Did not init object bound to field '" + fields[i].getName() + "' of test class '" + clazz.getSimpleName() + "' as mock. Class name does not contain the expected string '" + classNameContains + "'.");
				}
			} catch (IllegalAccessException e) {
				if (isAnnotated) {
					throw new RuntimeException("Could not retrieve mock object bound to field '" + fields[i].getName() + "' of test class '" + clazz.getSimpleName() + "'. Field must be declared as public.", e);
				}
				log.warning("Could not inspect object bound to field '" + fields[i].getName() + "' of test class '" + clazz.getSimpleName() + "'. Field is not accessible.");
			}
		}
	}

}
