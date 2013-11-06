package org.camunda.bpm.needle;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import de.holisticon.toolbox.needle.provider.DefaultInstanceInjectionProvider;

public class LittleNeedleHowto {

  public static interface SomeRemoteService {

    String getName();

  }

  public static class SomeDelegate implements JavaDelegate {

    @Inject
    private SomeRemoteService someRemoteService;

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
      execution.setVariable("name", someRemoteService.getName());
    }

  }

  @Rule
  public final NeedleRule needle = new NeedleRule(DefaultInstanceInjectionProvider.providerFor(new SomeRemoteService() {

    @Override
    public String getName() {
      return "foo";
    }
  }));

  @ObjectUnderTest
  private SomeDelegate delegate;

  @Inject
  private SomeRemoteService someRemoteService;

  @Mock
  private DelegateExecution execution;

  @Test
  public void shouldSetNameToFoo() throws Exception {

    delegate.execute(execution);

    verify(execution).setVariable("name", "foo");

  }
}
