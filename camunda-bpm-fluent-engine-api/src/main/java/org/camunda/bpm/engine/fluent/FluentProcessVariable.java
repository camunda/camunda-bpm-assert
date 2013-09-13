package org.camunda.bpm.engine.fluent;

import java.util.Map.Entry;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface FluentProcessVariable extends FluentDelegate<Entry<String, Object>> {

  String getName();

  Object getValue();

}
