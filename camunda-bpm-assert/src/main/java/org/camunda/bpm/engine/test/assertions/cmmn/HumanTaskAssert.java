package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class HumanTaskAssert extends AbstractCaseAssert<HumanTaskAssert, CaseExecution> {

	protected HumanTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
		super(engine, actual, HumanTaskAssert.class);
	}

	public static HumanTaskAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
		return new HumanTaskAssert(engine, actual);
	}

	@Override
	public HumanTaskAssert isAvailable() {
		return super.isAvailable();
	}

	@Override
	public HumanTaskAssert isEnabled() {
		return super.isEnabled();
	}

	@Override
	public HumanTaskAssert isDisabled() {
		return super.isDisabled();
	}

	@Override
	public HumanTaskAssert isActive() {
		return super.isActive();
	}

	@Override
	public HumanTaskAssert isSuspended() {
		return super.isSuspended();
	}

	@Override
	public HumanTaskAssert isCompleted() {
		return super.isCompleted();
	}

	@Override
	public HumanTaskAssert isFailed() {
		return super.isFailed();
	}

	@Override
	public HumanTaskAssert isTerminated() {
		return super.isTerminated();
	}

}
