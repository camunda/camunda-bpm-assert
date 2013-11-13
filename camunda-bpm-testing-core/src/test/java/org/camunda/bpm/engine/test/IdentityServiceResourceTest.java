package org.camunda.bpm.engine.test;

import static org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;
import static org.junit.Assert.assertNotNull;

import org.camunda.bpm.engine.IdentityService;
import org.junit.Rule;
import org.junit.Test;

public class IdentityServiceResourceTest {

  @Rule
  public final ProcessEngineTestRule processEngineRule = mostUsefulProcessEngineConfiguration().buildProcessEngineRule();

  @Rule
  public final IdentityServiceResource identityServiceResource = new IdentityServiceResource(processEngineRule.getIdentityService());

  private final IdentityService identityService = identityServiceResource.getIdentityService();

  @Test
  public void shouldInitUserAndGroup() {
    identityServiceResource.saveNewGroup("bar");
    identityServiceResource.saveNewGroup("notNeeded");
    identityServiceResource.saveNewUser("foo");
    identityServiceResource.saveNewUser("notNeeded");
    identityServiceResource.saveNewMembership("foo", "bar");

    assertNotNull(identityService.createUserQuery().userId("foo").memberOfGroup("bar").singleResult());
  }

}
