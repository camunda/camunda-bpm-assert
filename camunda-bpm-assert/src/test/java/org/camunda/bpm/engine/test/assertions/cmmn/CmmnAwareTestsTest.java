package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.CaseDefinitionQuery;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.CaseInstanceQuery;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CmmnAwareAssertions.class, BpmnAwareTests.class, CmmnAwareTests.class})
public class CmmnAwareTestsTest {

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Mock
	private CaseInstance caseInstance;

	@Mock
	private CaseExecution caseExecution;

	@Mock
	private CaseDefinition caseDefinition;

	@Test
	public void assertThatCaseDefinition_should_delegate_to_CmmnAwareAssertions() throws Exception {

		//prepare and mock static methods
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
		mockStatic(CmmnAwareAssertions.class);
		CaseExecutionAssert caseExecutionAssert = mock(CaseExecutionAssert.class);
		when(CmmnAwareAssertions.assertThat(any(CaseExecution.class))).thenReturn(caseExecutionAssert);

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
		mockStatic(CmmnAwareAssertions.class);
		CaseInstanceAssert caseInstanceAssert = mock(CaseInstanceAssert.class);
		when(CmmnAwareAssertions.assertThat(any(CaseInstance.class))).thenReturn(caseInstanceAssert);

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
		mockStatic(CmmnAwareTests.class);
		when(CmmnAwareTests.caseExecutionQuery()).thenCallRealMethod();
		CaseService caseService = mock(CaseService.class);
		when(CmmnAwareTests.caseService()).thenReturn(caseService);

		CaseExecutionQuery caseExecutionQuery = mock(CaseExecutionQuery.class);
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
	public void caseExecution_should_() throws Exception {

	}

	@Test
	public void caseInstanceQuery_should_create_vanilla_query() throws Exception {

		//prepare and mock static methods
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
	public void caseService_should_() throws Exception {

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