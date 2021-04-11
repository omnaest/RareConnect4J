package org.omnaest.rareconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationReducedEntry
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String verb;

    @JsonProperty("action_object_id")
    private String actionObjectId;

    @JsonProperty("action_object_type")
    private String actionObjectType;

    @JsonProperty("is_seen")
    private boolean isSeen;

    @JsonProperty("email_queued")
    private boolean emailQueued;

    @JsonProperty("email_sent")
    private boolean emailSent;

    @JsonProperty("profile_id")
    private String profileId;

    public String getId()
    {
        return this.id;
    }

    public String getVerb()
    {
        return this.verb;
    }

    public String getActionObjectId()
    {
        return this.actionObjectId;
    }

    public String getActionObjectType()
    {
        return this.actionObjectType;
    }

    public boolean isSeen()
    {
        return this.isSeen;
    }

    public boolean isEmailQueued()
    {
        return this.emailQueued;
    }

    public boolean isEmailSent()
    {
        return this.emailSent;
    }

    public String getProfileId()
    {
        return this.profileId;
    }

    public static enum Verb
    {
        POSTED_IN_SUBSCRIBED_COMMUNITY, JOINED_SUBSCRIBED_COMMUNITY, LIKED, COMMENTED_THREAD, COMMENTED
    }

}