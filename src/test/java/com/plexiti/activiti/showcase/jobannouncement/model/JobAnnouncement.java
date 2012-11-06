package com.plexiti.activiti.showcase.jobannouncement.model;

import java.io.Serializable;
import java.util.Date;

import com.plexiti.helper.Strings;
import org.activiti.engine.task.Task;

public class JobAnnouncement implements Serializable {

    private static final long serialVersionUID = 7458230741177377962L;

    public static interface Edit {}

    private Long id;

    private String need;

    private String jobTitle;

    private String jobDescription;

    private String facebookPost;

    private String twitterMessage;

    private Date published;

    private String requester;

    private String editor;

    private String twitterUrl;

    private String facebookUrl;

    private Task task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = Strings.trimToNull(need);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = Strings.trimToNull(jobTitle);
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = Strings.trimToNull(jobDescription);
    }

    public String getFacebookPost() {
        return facebookPost;
    }

    public void setFacebookPost(String facebookPost) {
        this.facebookPost = Strings.trimToNull(facebookPost);
    }

    public String getTwitterMessage() {
        return twitterMessage;
    }

    public void setTwitterMessage(String message) {
        this.twitterMessage = Strings.trimToNull(message);
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = Strings.trimToNull(requester);
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = Strings.trimToNull(editor);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = Strings.trimToNull(twitterUrl);
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = Strings.trimToNull(facebookUrl);
    }

    public String getWebsiteUrl() {
        // TODO Construct correct URL here.
        return "http://the-job-announcement.com/view.jsf?id=" + id;
    }

    public String getTwitterMessageWithLink() {
        return getTwitterMessage() + " " + getWebsiteUrl();
    }

    public String getFacebookPostWithLink() {
        return getFacebookPost() + " " + getWebsiteUrl();
    }

}
