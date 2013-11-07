package org.camunda.bpm.engine.test;

import static org.junit.Assert.assertNotNull;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.junit.Rule;
import org.junit.Test;

public class IdentityServiceResourceTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Rule
  public final IdentityServiceResource identityServiceResource = new IdentityServiceResource(TestHelper.getProcessEngine("camunda.cfg.xml")
      .getIdentityService());

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
