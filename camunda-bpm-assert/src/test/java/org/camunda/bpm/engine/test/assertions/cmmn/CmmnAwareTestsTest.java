package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.CaseDefinitionQuery;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.CaseInstanceQuery;
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.engine.test.assertions.helpers.CaseExecutionQueryFluentAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.camunda.bpm.engine.test.assertions.helpers.CamundaMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;

/**
 * You will notice that this test class does not cover all methods.
 * That's because simple, one-liner boilerplate code is not regarded as test-worthy.
 * That code is so simplistic, it is not expected to break easily.
 *
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CmmnAwareAssertions.class, BpmnAwareTests.class, CmmnAwareTests.class, AbstractAssertions.class})
public class CmmnAwareTestsTest {

	public static final String ACTIVITY_ID = "FOO";
	public static final String CASE_INSTANCE_ID = "BAR";

	@Mock
	private CaseInstance caseInstance;

	@Mock
	private CaseExecution caseExecution;

	@Mock
	private CaseDefinition caseDefinition;

	//mocked below
	private CaseExecutionQuery caseExecutionQuery;

	@Before
	public void prepareCaseInstance() {
		when(caseInstance.getId()).thenReturn(CASE_INSTANCE_ID);
	}

	@Before
	public void prepareCaseExecutionQuery() {
		caseExecutionQuery = mock(CaseExecutionQuery.class, new CaseExecutionQueryFluentAnswer());
		when(caseExecutionQuery.singleResult()).thenReturn(caseExecution);
		when(caseExecutionQuery.toString()).thenCallRealMethod();
	}

	@Test
	public void assertThatCaseDefinition_should_delegate_to_CmmnAwareAssertions() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseDefinitionAssert created
		mockStatic(CmmnAwareAssertions.class);
		CaseDefinitionAssert caseDefinitionAssert = mock(CaseDefinitionAssert.class);
		when(CmmnAwareAssertions.assertThat(any(CaseDefinition.class))).thenReturn(caseDefinitionAssert);

		//when calling the method under test with a non-null CaseDefinition object
		CaseDefinitionAssert actualCaseDefinitionAssert = CmmnAwareTests.assertThat(caseDefinition);

		//then the delegate is called with that CaseDefinition object
		verifyStatic();
		CmmnAwareAssertions.assertThat(caseDefinition);
		//and whatever the delegate returns, is returned by the tested method, too
		Assertions.assertThat(actualCaseDefinitionAssert).isSameAs(caseDefinitionAssert);
	}

	@Test
	public void assertThatCaseExecution_should_delegate_to_CmmnAwareAssertions() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseExecutionAssert created
		mockStatic(CmmnAwareAssertions.class);
		CaseExecutionAssert caseExecutionAssert = mock(CaseExecutionAssert.class);
		when(CmmnAwareAssertions.assertThat(anyCaseExecution())).thenReturn(caseExecutionAssert);

		//when calling the method under test with a non-null CaseExecution object
		CaseExecutionAssert actualCaseExecutionAssert = CmmnAwareTests.assertThat(caseExecution);

		//then the delegate is called with that CaseExecution object
		verifyStatic();
		CmmnAwareAssertions.assertThat(caseExecution);
		//and whatever the delegate returns, is returned by the tested method, too
		Assertions.assertThat(actualCaseExecutionAssert).isSameAs(caseExecutionAssert);
	}

	@Test
	public void assertThatCaseInstance_should_delegate_to_CmmnAwareAssertions() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseInstanceAssert created
		mockStatic(CmmnAwareAssertions.class);
		CaseInstanceAssert caseInstanceAssert = mock(CaseInstanceAssert.class);
		when(CmmnAwareAssertions.assertThat(anyCaseInstance())).thenReturn(caseInstanceAssert);

		//when calling the method under test with a non-null CaseInstance object
		CaseInstanceAssert actualCaseInstanceAssert = CmmnAwareTests.assertThat(caseInstance);

		//then the delegate is called with that CaseInstance object
		verifyStatic();
		CmmnAwareAssertions.assertThat(caseInstance);
		//and whatever the delegate returns, is returned by the tested method, too
		Assertions.assertThat(actualCaseInstanceAssert).isSameAs(caseInstanceAssert);
	}

	@Test
	public void caseDefinitionQuery_should_create_vanilla_query() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseDefinitionQuery created
		mockStatic(BpmnAwareTests.class);
		RepositoryService repositoryService = mock(RepositoryService.class);
		when(BpmnAwareTests.repositoryService()).thenReturn(repositoryService);

		CaseDefinitionQuery caseDefinitionQuery = mock(CaseDefinitionQuery.class);
		when(repositoryService.createCaseDefinitionQuery()).thenReturn(caseDefinitionQuery);

		//when getting a CaseDefinitionQuery
		CaseDefinitionQuery actualCaseDefinitionQuery = CmmnAwareTests.caseDefinitionQuery();

		//then the RepositoryService is used for creating a new one
		verify(repositoryService).createCaseDefinitionQuery();
		//and the new CaseDefinitionQuery is returned from the tested method
		Assertions.assertThat(actualCaseDefinitionQuery).isSameAs(caseDefinitionQuery);
		//and the returned CaseDefinitionQuery is a vanilla one
		verifyNoMoreInteractions(caseDefinitionQuery);
	}

	@Test
	public void caseExecutionQuery_should_create_vanilla_query() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseExecutionQuery created
		mockStatic(CmmnAwareTests.class);
		when(CmmnAwareTests.caseExecutionQuery()).thenCallRealMethod();
		CaseService caseService = mock(CaseService.class);
		when(CmmnAwareTests.caseService()).thenReturn(caseService);
		when(caseService.createCaseExecutionQuery()).thenReturn(caseExecutionQuery);

		//when getting a CaseExecutionQuery
		CaseExecutionQuery actualCaseExecutionQuery = CmmnAwareTests.caseExecutionQuery();

		//then the CaseService is used for creating a new one
		verify(caseService).createCaseExecutionQuery();
		//and the new CaseExecutionQuery is returned from the tested method
		Assertions.assertThat(actualCaseExecutionQuery).isSameAs(caseExecutionQuery);
		//and the returned CaseExecutionQuery is a vanilla one
		verifyNoMoreInteractions(caseExecutionQuery);
	}

	@Test
	public void caseExecutionStringCaseExecution_should_delegate_to_its_query_variant() throws Exception {
		//prepare and mock static methods
		//because we need control over the context of tested method
		mockStatic(CmmnAwareTests.class);
		when(CmmnAwareTests.caseExecution(anyString(), anyCaseInstance())).thenCallRealMethod();
		when(CmmnAwareTests.caseExecution(anyCaseExecutionQuery(), anyCaseInstance())).thenReturn(caseExecution);
		when(CmmnAwareTests.caseExecutionQuery()).thenReturn(caseExecutionQuery);

		//when getting the CaseExecution for activity FOO in a given case instance
		CaseExecution actualCaseExecution = CmmnAwareTests.caseExecution(ACTIVITY_ID, caseInstance);

		//then the caseExecutionQuery is enhanced with the activityId only
		verify(caseExecutionQuery).activityId(ACTIVITY_ID);
		verifyNoMoreInteractions(caseExecutionQuery);
		//and the call is delegated properly
		verifyStatic();
		CmmnAwareTests.caseExecution(caseExecutionQuery, caseInstance);
	}

	@Test
	public void caseInstanceQuery_should_create_vanilla_query() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseInstanceQuery created
		mockStatic(CmmnAwareTests.class);
		when(CmmnAwareTests.caseInstanceQuery()).thenCallRealMethod();
		CaseService caseService = mock(CaseService.class);
		when(CmmnAwareTests.caseService()).thenReturn(caseService);

		CaseInstanceQuery caseInstanceQuery = mock(CaseInstanceQuery.class);
		when(caseService.createCaseInstanceQuery()).thenReturn(caseInstanceQuery);

		//when getting a CaseInstanceQuery
		CaseInstanceQuery actualCaseInstanceQuery = CmmnAwareTests.caseInstanceQuery();

		//then the CaseService is used for creating a new one
		verify(caseService).createCaseInstanceQuery();
		//and the new CaseInstanceQuery is returned from the tested method
		Assertions.assertThat(actualCaseInstanceQuery).isSameAs(caseInstanceQuery);
		//and the returned CaseInstanceQuery is a vanilla one
		verifyNoMoreInteractions(caseInstanceQuery);
	}

	@Test
	public void caseService_should_return_the_processEngines_caseService() throws Exception {

		//prepare and mock static methods
		//because we need control over the CaseService created
		mockStatic(AbstractAssertions.class);
		ProcessEngine processEngine = mock(ProcessEngine.class);
		when(AbstractAssertions.processEngine()).thenReturn(processEngine);
		CaseService caseService = mock(CaseService.class);
		when(processEngine.getCaseService()).thenReturn(caseService);

		//when getting a CaseInstanceQuery
		CaseService actualCaseService = CmmnAwareTests.caseService();

		//then the returned CaseService is the same as
		Assertions.assertThat(actualCaseService).isSameAs(caseService);
	}

	@Test
	public void complete_should_() throws Exception {

	}

	@Test
	public void disable_should_() throws Exception {

	}

	@Test
	public void manuallyStart_should_() throws Exception {

	}
}