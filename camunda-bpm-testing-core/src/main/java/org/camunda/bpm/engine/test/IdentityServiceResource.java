package org.camunda.bpm.engine.test;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.junit.rules.ExternalResource;

/**
 * External resource rule for creating users, groups and memberships. Deletes
 * all entities on @After.
 */
public class IdentityServiceResource extends ExternalResource {

  private final IdentityService identityService;

  /**
   * 
   * @param identityService
   *          the service gotten from the {@link ProcessEngine}
   */
  public IdentityServiceResource(final IdentityService identityService) {
    this.identityService = identityService;
  }

  @Override
  protected void after() {
    for (final User user : userQuery().list()) {
      final String userId = user.getId();
      // delete all memberships for this user
      for (final Group group : groupQuery().groupMember(userId).list()) {
        final String groupId = group.getId();
        identityService.deleteMembership(userId, groupId);
        identityService.deleteGroup(groupId);
      }
      identityService.deleteUser(user.getId());
    }
    for (final Group remainingGroup : groupQuery().list()) {
      identityService.deleteGroup(remainingGroup.getId());
    }
    assertThat(userQuery().list(), empty());
    assertThat(groupQuery().list(), empty());
  }

  private UserQuery userQuery() {
    return identityService.createUserQuery();
  }

  private GroupQuery groupQuery() {
    return identityService.createGroupQuery();
  }

  public IdentityService getIdentityService() {
    return identityService;
  }

  public User saveNewUser(final String userId) {
    final User user = identityService.newUser(userId);
    identityService.saveUser(user);
    return user;
  }

  public Group saveNewGroup(final String groupId) {
    final Group group = identityService.newGroup(groupId);
    identityService.saveGroup(group);
    return group;
  }

  public void saveNewMembership(final String userId, final String groupId) {
    identityService.createMembership(userId, groupId);
  }
}
