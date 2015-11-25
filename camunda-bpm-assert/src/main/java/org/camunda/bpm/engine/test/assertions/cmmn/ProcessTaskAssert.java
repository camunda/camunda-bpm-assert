package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessTaskAssert extends AbstractCaseAssert<ProcessTaskAssert, CaseExecution> {

	protected ProcessTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
		super(engine, actual, ProcessTaskAssert.class);
	}

	public static ProcessTaskAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
		return new ProcessTaskAssert(engine, actual);
	}

	@Override
	public ProcessTaskAssert isAvailable() {
		return super.isAvailable();
	}

	@Override
	public ProcessTaskAssert isEnabled() {
		return super.isEnabled();
	}

	@Override
	public ProcessTaskAssert isDisabled() {
		return super.isDisabled();
	}

	@Override
	public ProcessTaskAssert isActive() {
		return super.isActive();
	}

	@Override
	public ProcessTaskAssert isSuspended() {
		return super.isSuspended();
	}

	@Override
	public ProcessTaskAssert isCompleted() {
		return super.isCompleted();
	}

	@Override
	public ProcessTaskAssert isFailed() {
		return super.isFailed();
	}

	@Override
	public ProcessTaskAssert isTerminated() {
		return super.isTerminated();
	}

}
