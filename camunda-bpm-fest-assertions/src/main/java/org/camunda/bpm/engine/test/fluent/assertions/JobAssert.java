package org.camunda.bpm.engine.test.fluent.assertions;

import java.util.Date;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Job;
import org.fest.assertions.api.Assertions;

/**
 * Fluent assertions for {@link Job}
 * 
 */
public class JobAssert extends AbstractProcessAssert<JobAssert, Job> {

  protected JobAssert(final ProcessEngine engine, final Job actual) {
    super(engine, actual, JobAssert.class);
  }

  public static JobAssert assertThat(final ProcessEngine engine, final Job actual) {
    return new JobAssert(engine, actual);
  }

  /**
   * Assertion on the due date of the {@link org.camunda.bpm.engine.task.Task}.
   * 
   * @param expectedDueDate
   *          the due date
   * 
   * @return a {@link JobAssert} that can be further configured before starting
   *         the process instance
   * 
   * @see org.camunda.bpm.engine.task.Task#getDueDate()
   */
  public JobAssert hasDueDate(final Date expectedDueDate) {
    isNotNull();
    final Date actualDuedate = actual.getDuedate();
    Assertions.assertThat(actualDuedate)
        .overridingErrorMessage("Expected job '%s' to have '%s' as due date but has '%s'", actual.getId(), expectedDueDate, actualDuedate)
        .equals(expectedDueDate);
    return this;
  }

  /**
   * 
   * @param expectedId
   *          the job id
   * 
   * @return a {@link JobAssert} that can be further configured before starting
   *         the process instance
   */
  public JobAssert hasId(final String expectedId) {
    isNotNull();

    final String actualId = actual.getId();
    Assertions.assertThat(actualId).overridingErrorMessage("Expected job with id '%s', but was '%s'", expectedId, actualId).isEqualTo(expectedId);
    return this;
  }

}
