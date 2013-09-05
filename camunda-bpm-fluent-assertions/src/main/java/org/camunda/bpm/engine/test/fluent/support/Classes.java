package org.camunda.bpm.engine.test.fluent.support;

import java.lang.reflect.Field;

/**
 * @author martin.schimak@plexiti.com
 */
public class Classes {

  public static Field getFieldByType(Class<?> clazz, Class<?> fieldType) {
    for (Field field : clazz.getFields()) {
      if (field.getType().isAssignableFrom(fieldType)) {
        return field;
      }
    }
    throw new RuntimeException("Expected " + clazz + " to declare a field of type " + fieldType + ", but did not find any such (accessible) field.");
  }

}
