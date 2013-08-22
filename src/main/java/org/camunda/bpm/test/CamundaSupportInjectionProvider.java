package org.camunda.bpm.test;

public class CamundaSupportInjectionProvider extends AbstractInjectionProvider<CamundaSupport> {

	/**
	 * Default.
	 */
	public CamundaSupportInjectionProvider() {
		// left empty
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CamundaSupport getInjectedObject(Class<?> type) {
		return CamundaSupport.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<CamundaSupport> getType() {
		return CamundaSupport.class;
	}
}
