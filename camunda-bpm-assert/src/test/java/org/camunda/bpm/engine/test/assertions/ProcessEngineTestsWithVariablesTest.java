package org.camunda.bpm.engine.test.assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@RunWith(value = Parameterized.class)
public class ProcessEngineTestsWithVariablesTest {

  List<Object> keys;
  List<Object> values;
  Map<String, Object> expectedMap;

  public ProcessEngineTestsWithVariablesTest(String key1, Object value1, String key2, Object value2, String key3, Object value3) {

    expectedMap = new HashMap<String, Object>();
    if (key1 != null)
      expectedMap.put(key1, value1);
    if (key2 != null)
      expectedMap.put(key2, value2);
    if (key3 != null)
      expectedMap.put(key3, value3);

    keys = new ArrayList<Object>();
    keys.add(key1);
    keys.add(key2);
    keys.add(key3);

    values = new ArrayList<Object>();
    values.add(value1);
    values.add(value2);
    values.add(value3);

  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] { 
      { "key1", 1 , null  , null, null  , null }, 
      { "key1", 1 , "key2", 2   , null  , null }, 
      { "key1", 1 , "key2", 2   , "key3", 3    } 
    };
    return Arrays.asList(data);
  }

  @Test
  public void testWithVariables() {
    // When we simply constuct the map with the given data
    Map<String, Object> returnedMap = returnedMap(keys, values);
    // Then we expect to find the expected Map
    assertThat(returnedMap).isEqualTo(expectedMap);
  }

  @Test
  public void testWithVariables_NoStringKeys() {
    // Given we replace the last key with its integer value
    keys.set(keys.size() - 1, values.get(values.size() - 1));
    // When we construct the variables map
    try {
      returnedMap(keys, values);
      fail("IllegalArgumentException or AssertionError expected!");
    // Then we expect an exception to be thrown
    } catch (Throwable t) {
      assertThat(t).isInstanceOfAny(IllegalArgumentException.class, AssertionError.class);
    }
  }

  @Test
  public void testWithVariables_NullKeys() {
    // Given we replace the last key with a null pointer
    keys.set(keys.size() - 1, null);
    // When we construct the variables map
    try {
      returnedMap(keys, values);
      fail("IllegalArgumentException expected!");
    // Then we expect an exception to be thrown
    } catch (Throwable t) {
      assertThat(t).isInstanceOfAny(IllegalArgumentException.class, AssertionError.class);
    }
  }

  @Test
  public void testWithVariables_NullValues() {
    // Given we replace all values with a null pointer
    int idx = values.size(); 
    while (idx > 0)
      values.set(--idx, null);
    // When we construct the variables map
    Map<String, Object> returnedMap = returnedMap(keys, values);
    // Then we expect the keys to match the expectedMap keys
    assertThat(returnedMap.keySet()).isEqualTo(expectedMap.keySet());
    // And we expect all the values to be null
    assertThat(returnedMap.values()).containsOnly((Object) null);
  }
  
  private static Map<String, Object> returnedMap(List<Object> keys, List<Object> values) {
    Map<String, Object> returnedMap;
    if (keys.get(2) != null)
      returnedMap = withVariables((String) keys.get(0), values.get(0), keys.get(1), values.get(1), keys.get(2), values.get(2));
    else if (keys.get(1) != null)
      returnedMap = withVariables((String) keys.get(0), values.get(0), keys.get(1), values.get(1));
    else
      returnedMap = withVariables((String) keys.get(0), values.get(0));
    return returnedMap;
  }

}
