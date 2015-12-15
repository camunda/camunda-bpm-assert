package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CmmnAwareAssertions.class)
public class CmmnAwareTestsTest {

	@Mock
	private CaseInstance caseInstance;

	@Mock
	private CaseExecution caseExecution;

	@Mock
	private CaseDefinition caseDefinition;

	@Test
	public void assertThatCaseDefinition_should_delegate_to_CmmnAwareAssertions() throws Exception {
		CaseDefinitionAssert caseDefinitionAssert = mock(CaseDefinitionAssert.class);

		//prepare and mock static methods
		mockStatic(CmmnAwareAssertions.class);
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
		CaseExecutionAssert caseExecutionAssert = mock(CaseExecutionAssert.class);

		//prepare and mock static methods
		mockStatic(CmmnAwareAssertions.class);
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
		CaseInstanceAssert caseInstanceAssert = mock(CaseInstanceAssert.class);

		//prepare and mock static methods
		mockStatic(CmmnAwareAssertions.class);
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
	public void caseDefinitionQuery_should_() throws Exception {

	}

	@Test
	public void caseExecutionQuery_should_() throws Exception {

	}

	@Test
	public void caseExecution_should_() throws Exception {

	}

	@Test
	public void caseInstanceQuery_should_() throws Exception {

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