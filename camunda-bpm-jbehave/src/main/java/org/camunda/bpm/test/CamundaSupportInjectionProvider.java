package org.camunda.bpm.test;

public class CamundaSupportInjectionProvider extends AbstractInjectionProvider<CamundaSupport> {

  /**
   * Default.
   */
  public CamundaSupportInjectionProvider() {
    // left empty
  }

  @Override
  public CamundaSupport getInjectedObject(final Class<?> type) {
    return CamundaSupport.getInstance();
  }

  @Override
  public Class<CamundaSupport> getType() {
    return CamundaSupport.class;
  }
}
