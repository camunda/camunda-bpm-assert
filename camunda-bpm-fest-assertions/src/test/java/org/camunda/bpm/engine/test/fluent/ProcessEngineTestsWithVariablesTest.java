package org.camunda.bpm.engine.test.fluent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.camunda.bpm.engine.test.fluent.ProcessEngineTests.*;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@RunWith(value = Parameterized.class)
public class ProcessEngineTestsWithVariablesTest {

  List<String> keys;
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

    keys = new ArrayList<String>();
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
    // Given
    Map<String, Object> returnedMap;
    // When
    if (keys.get(2) != null) 
      returnedMap = withVariables(keys.get(0), values.get(0), keys.get(1), values.get(1), keys.get(2), values.get(2));
    else if (keys.get(1) != null)
      returnedMap = withVariables(keys.get(0), values.get(0), keys.get(1), values.get(1));
    else
      returnedMap = withVariables(keys.get(0), values.get(0));
    // Then
    assertThat(returnedMap).isEqualTo(expectedMap);
  }

  @Test
  public void testWithVariables_NoStringKeys() {
    // Given
    Map<String, Object> returnedMap;
    // When
    try {
      if (keys.get(2) != null)
        returnedMap = withVariables(keys.get(0), values.get(0), keys.get(1), values.get(1), values.get(2), keys.get(2));
      else if (keys.get(1) != null)
        returnedMap = withVariables(keys.get(0), values.get(0), values.get(1), keys.get(1));
      else
        returnedMap = withVariables(null, values.get(0));
      fail("IllegalArgumentException expected!");
    // Then
    } catch (Throwable t) {
      assertThat(t).isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  public void testWithVariables_NullKeys() {
    // Given
    Map<String, Object> returnedMap;
    // When
    try {
      if (keys.get(2) != null)
        returnedMap = withVariables(keys.get(0), values.get(0), keys.get(1), values.get(1), null, values.get(2));
      else if (keys.get(1) != null)
        returnedMap = withVariables(keys.get(0), values.get(0), null, values.get(1));
      else
        returnedMap = withVariables(null, values.get(0));
      fail("IllegalArgumentException expected!");
    // Then
    } catch (Throwable t) {
      assertThat(t).isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  public void testWithVariables_NullValues() {
    // Given
    Map<String, Object> returnedMap;
    // When
    if (keys.get(2) != null)
      returnedMap = withVariables(keys.get(0), null, keys.get(1), null, keys.get(2), null);
    else if (keys.get(1) != null)
      returnedMap = withVariables(keys.get(0), null, keys.get(1), null);
    else
      returnedMap = withVariables(keys.get(0), null);
    // Then
    assertThat(returnedMap.keySet()).isEqualTo(expectedMap.keySet());
    assertThat(returnedMap.values()).containsOnly((Object) null);
  }

}
