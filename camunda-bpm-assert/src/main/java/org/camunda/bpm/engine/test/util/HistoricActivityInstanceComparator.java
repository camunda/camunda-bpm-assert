package org.camunda.bpm.engine.test.util;

import org.camunda.bpm.engine.history.HistoricActivityInstance;

import java.util.Comparator;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class HistoricActivityInstanceComparator implements Comparator<HistoricActivityInstance> {

  @Override
  public int compare(HistoricActivityInstance instance1, HistoricActivityInstance instance2) {
    int compare = instance1.getEndTime().compareTo(instance2.getEndTime());
    if (compare == 0) {
      String string1 = instance1.getId().substring(instance1.getId().lastIndexOf(':') + 1);
      String string2 = instance2.getId().substring(instance2.getId().lastIndexOf(':') + 1);
      try {
        Long long1 = Long.parseLong(string1);
        Long long2 = Long.parseLong(string2);
        return long1.compareTo(long2);
      } catch (NumberFormatException e) {
        return string1.compareTo(string2);
      }
    }
    return compare;
  }
  
}
