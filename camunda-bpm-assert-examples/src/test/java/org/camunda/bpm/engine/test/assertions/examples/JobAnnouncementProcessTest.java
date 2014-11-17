package org.camunda.bpm.engine.test.assertions.examples;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.claim;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.processDefinition;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.taskQuery;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.withVariables;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.examples.jobannouncement.JobAnnouncement;
import org.camunda.bpm.engine.test.assertions.examples.jobannouncement.JobAnnouncementService;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAnnouncementProcessTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Mock
  public JobAnnouncementService jobAnnouncementService;

  @Mock
  public JobAnnouncement jobAnnouncement;

  // Some boilerplate - we can easily get rid of again by
  @Before
  // deciding where to ultimately put the jUnit integration
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    Mocks.register("jobAnnouncementService", jobAnnouncementService);
    Mocks.register("jobAnnouncement", jobAnnouncement);
  }

  @After
  public void tearDown() {
    Mocks.reset();
  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement.bpmn", "camunda-testing-job-announcement-publication.bpmn" })
  public void testHappyPath() {

    when(jobAnnouncement.getId()).thenReturn(1L);
    when(jobAnnouncementService.findRequester(1L)).thenReturn("gonzo");
    when(jobAnnouncementService.findEditor(1L)).thenReturn("fozzie");

    final ProcessInstance processInstance = startProcess();

    assertThat(processInstance).isStarted().isNotEnded().task().hasDefinitionKey("edit").hasCandidateGroup("engineering").isNotAssigned();

    claim(task(), "fozzie");

    assertThat(task()).isAssignedTo("fozzie");
    // and just to show off more possibilities...
    assertThat(processInstance).task(taskQuery().taskAssignee("fozzie")).hasDefinitionKey("edit");
    assertThat(processInstance).task("edit").isAssignedTo("fozzie");

    complete(task());

    assertThat(processInstance).task().hasDefinitionKey("review").isAssignedTo("gonzo");

    complete(task(), withVariables("approved", true));

    assertThat(processInstance).task().hasDefinitionKey("publish").hasCandidateGroup("engineering").isNotAssigned();

    assertThat(processInstance).hasVariables();
    assertThat(processInstance).hasVariables("jobAnnouncementId", "approved");
    assertThat(processInstance).variables().hasSize(2).containsEntry("jobAnnouncementId", jobAnnouncement.getId()).containsEntry("approved", true);

    // claim and complete could be combined, too
    complete(claim(task(), "fozzie"), withVariables("twitter", true, "facebook", true));

    verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
    verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

    assertThat(processInstance).hasPassedInOrder("edit", "review", "publish", "publication", "mail");

    assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

    assertThat(processDefinition()).hasActiveInstances(0);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement.bpmn", "camunda-testing-job-announcement-publication.bpmn" })
  public void should_fail_if_processInstance_is_not_waiting_at_expected_task() {
    final ProcessInstance processInstance = startProcess();

    try {
      assertThat(processInstance).task("review").isNotAssigned();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("to be waiting at [review], but it is actually waiting at [edit]");
    }

  }


  @Test
  @Deployment(resources = { "camunda-testing-job-announcement.bpmn", "camunda-testing-job-announcement-publication.bpmn" })
  public void should_not_fail_if_processInstance_is_waiting_at_expected_task() {
    final ProcessInstance processInstance = startProcess();

    assertThat(processInstance).task("edit").isNotAssigned();
  }

  private ProcessInstance startProcess() {
    return runtimeService().startProcessInstanceByKey("camunda-testing-job-announcement",
          withVariables("jobAnnouncementId", jobAnnouncement.getId())
      );
  }
}
