package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class MilestoneAssert extends AbstractCaseAssert<MilestoneAssert, CaseExecution> {

	protected MilestoneAssert(final ProcessEngine engine, final CaseExecution actual) {
		super(engine, actual, MilestoneAssert.class);
	}

	protected static MilestoneAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
		return new MilestoneAssert(engine, actual);
	}

	@Override
	public MilestoneAssert isAvailable() {
		return super.isAvailable();
	}

	@Override
	public MilestoneAssert isSuspended() {
		return super.isSuspended();
	}

	@Override
	public MilestoneAssert isCompleted() {
		return super.isCompleted();
	}

	@Override
	public MilestoneAssert isTerminated() {
		return super.isTerminated();
	}
	
}
