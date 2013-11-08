package org.camunda.bpm.engine.test;

import static org.camunda.bpm.engine.test.Expressions.getRegistered;
import static org.camunda.bpm.engine.test.Expressions.registerInstance;
import static org.camunda.bpm.engine.test.function.NameForType.juelNameFor;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.test.mock.FluentExecutionListenerMock;
import org.camunda.bpm.engine.test.mock.FluentJavaDelegateMock;
import org.camunda.bpm.engine.test.mock.FluentTaskListenerMock;
import org.camunda.bpm.engine.test.mock.verify.ExecutionListenerVerification;
import org.camunda.bpm.engine.test.mock.verify.JavaDelegateVerification;
import org.camunda.bpm.engine.test.mock.verify.MockitoVerification;
import org.camunda.bpm.engine.test.mock.verify.TaskListenerVerification;

/**
 * Util class for mocking DelegateExpressions as used in the modeller.
 */
public final class DelegateExpressions {

  public static FluentJavaDelegateMock registerJavaDelegateMock(final String name) {
    final FluentJavaDelegateMock delegateMock = new FluentJavaDelegateMock();
    registerInstance(name, delegateMock.getMock());
    return delegateMock;
  }

  public static FluentExecutionListenerMock registerExecutionListenerMock(final String name) {
    final FluentExecutionListenerMock delegateMock = new FluentExecutionListenerMock();
    registerInstance(name, delegateMock.getMock());
    return delegateMock;
  }

  public static FluentTaskListenerMock registerTaskListenerMock(final String name) {
    final FluentTaskListenerMock delegateMock = new FluentTaskListenerMock();
    registerInstance(name, delegateMock.getMock());
    return delegateMock;
  }

  public static JavaDelegate getRegisteredJavaDelegate(final String name) {
    return getRegistered(name);
  }

  /**
   * @see #getRegisteredJavaDelegate(String)
   * @param javaDelegateType
   *          type to get the name from
   * @return the registered {@link JavaDelegate}
   */
  public static JavaDelegate getRegisteredJavaDelegate(final Class<JavaDelegate> javaDelegateType) {
    return getRegisteredJavaDelegate(juelNameFor(javaDelegateType));
  }

  /**
   * @see #getRegisteredExecutionListener(String)
   * @param executionListernerType
   *          type to get the name from
   * @return the registered {@link ExecutionListener}
   */
  public static ExecutionListener getRegisteredExecutionListener(final Class<ExecutionListener> executionListernerType) {
    return getRegisteredExecutionListener(juelNameFor(executionListernerType));
  }

  public static ExecutionListener getRegisteredExecutionListener(final String name) {
    return getRegistered(name);
  }

  /**
   * @see #getRegisteredTaskListener(String)
   * @param taskListenerType
   *          type to get the name from
   * @return the registered {@link TaskListener}
   */
  public static TaskListener getRegisteredTaskListener(final Class<TaskListener> taskListenerType) {
    return getRegistered(juelNameFor(taskListenerType));
  }

  public static TaskListener getRegisteredTaskListener(final String name) {
    return getRegistered(name);
  }

  public static MockitoVerification<DelegateExecution> verifyJavaDelegate(final Class<? extends JavaDelegate> javaDelegateType) {
    return verifyJavaDelegate(getRegisteredJavaDelegate(juelNameFor(javaDelegateType)));
  }

  public static MockitoVerification<DelegateExecution> verifyJavaDelegate(final String name) {
    return verifyJavaDelegate(getRegisteredJavaDelegate(name));
  }

  public static MockitoVerification<DelegateExecution> verifyJavaDelegate(final JavaDelegate javaDelegateMock) {
    return new JavaDelegateVerification(javaDelegateMock);
  }

  public static MockitoVerification<DelegateExecution> verifyExecutionListener(final Class<? extends ExecutionListener> executionListenerType) {
    return verifyExecutionListener(getRegisteredExecutionListener(juelNameFor(executionListenerType)));
  }

  public static MockitoVerification<DelegateExecution> verifyExecutionListener(final String name) {
    return verifyExecutionListener(getRegisteredExecutionListener(name));
  }

  public static MockitoVerification<DelegateExecution> verifyExecutionListener(final ExecutionListener executionListenerMock) {
    return new ExecutionListenerVerification(executionListenerMock);
  }

  public static MockitoVerification<DelegateTask> verifyTaskListener(final Class<? extends TaskListener> taskListenerType) {
    return verifyTaskListener(getRegisteredTaskListener(juelNameFor(taskListenerType)));
  }

  public static MockitoVerification<DelegateTask> verifyTaskListener(final String name) {
    return verifyTaskListener(getRegisteredTaskListener(name));
  }

  public static MockitoVerification<DelegateTask> verifyTaskListener(final TaskListener taskListenerMock) {
    return new TaskListenerVerification(taskListenerMock);
  }

  private DelegateExpressions() {
    // do not instantiate
  }

}
