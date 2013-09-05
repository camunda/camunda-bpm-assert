package org.camunda.bpm.needle.supplier;

import static de.holisticon.toolbox.needle.provider.InjectionProviderInstancesSupplier.Factory.createProvidersFor;
import static org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier.camundaInstancesSupplier;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.akquinet.jbosscc.needle.junit.NeedleRule;

public class CamundaInstancesSupplierTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Rule
  public final NeedleRule needle = new NeedleRule(createProvidersFor(camundaInstancesSupplier()));

  @Inject
  private ManagementService managementService;

  @Inject
  private IdentityService identityService;

  @Inject
  private RepositoryService repositoryService;

  @Inject
  private RuntimeService runtimeService;

  @Inject
  private FormService formService;

  @Inject
  private HistoryService historyService;

  @Inject
  private TaskService taskService;

  @Test
  public void shouldInjectManagementService() {
    assertThat(managementService.createJobQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectHistoryService() {
    assertThat(historyService.createHistoricActivityInstanceQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectIdentityService() {
    assertThat(identityService.createUserQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectFormService() {

    thrown.expect(ProcessEngineException.class);

    // throws exception when real service, fails for mock
    assertThat(formService.getRenderedStartForm("a"), notNullValue());
  }

  @Test
  public void shouldInjectRepositoryService() {
    assertThat(repositoryService.createProcessDefinitionQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectRuntimeService() {
    assertThat(runtimeService.createExecutionQuery().count(), is(0L));
  }

  @Test
  public void shouldInjectTaskService() {
    assertThat(taskService.createTaskQuery().count(), is(0L));
  }

}
