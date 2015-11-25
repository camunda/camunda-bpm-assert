package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CaseTaskAssert extends AbstractCaseAssert<CaseTaskAssert, CaseExecution> {

	protected CaseTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
		super(engine, actual, CaseTaskAssert.class);
	}

	public static CaseTaskAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
		return new CaseTaskAssert(engine, actual);
	}

	@Override
	public CaseTaskAssert isAvailable() {
		return super.isAvailable();
	}

	@Override
	public CaseTaskAssert isEnabled() {
		return super.isEnabled();
	}

	@Override
	public CaseTaskAssert isDisabled() {
		return super.isDisabled();
	}

	@Override
	public CaseTaskAssert isActive() {
		return super.isActive();
	}

	@Override
	public CaseTaskAssert isSuspended() {
		return super.isSuspended();
	}

	@Override
	public CaseTaskAssert isCompleted() {
		return super.isCompleted();
	}

	@Override
	public CaseTaskAssert isFailed() {
		return super.isFailed();
	}

	@Override
	public CaseTaskAssert isTerminated() {
		return super.isTerminated();
	}
	
}
