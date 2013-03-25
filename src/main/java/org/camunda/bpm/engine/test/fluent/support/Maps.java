package org.camunda.bpm.engine.test.fluent.support;

import java.util.HashMap;
import java.util.Map;

/**
 * @author martin.schimak@camunda.com
 */
public class Maps {

    public static Map<String, Object> parseMap(Object... keyValuePairs) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; keyValuePairs != null && i < keyValuePairs.length; i += 2) {
            map.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return map;
    }

}
