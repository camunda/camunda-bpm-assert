package com.plexiti.activiti.showcase.jobannouncement.service;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;

import java.util.List;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public interface JobAnnouncementService {

    List<JobAnnouncement> listPublished();
    List<JobAnnouncement> listInvolved(String involvedUser);

    void postToWebsite(Long jobAnnouncementId);
    void postToTwitter(Long jobAnnouncementId);
    void postToFacebook(Long jobAnnouncementId);

    void notifyAboutPostings(Long jobAnnouncementId);

    String findRequester(Long jobAnnouncementId);
    String findEditor(Long jobAnnouncementId);

}
