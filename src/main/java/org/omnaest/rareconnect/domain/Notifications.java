package org.omnaest.rareconnect.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notifications
{
    @JsonProperty
    private List<NotificationEntry> data;

    @JsonProperty
    private int total;

    @JsonProperty
    private int limit;

    @JsonProperty
    private int skip;

    public int getTotal()
    {
        return this.total;
    }

    public int getLimit()
    {
        return this.limit;
    }

    public int getSkip()
    {
        return this.skip;
    }

    public List<NotificationEntry> getData()
    {
        return this.data;
    }

    @Override
    public String toString()
    {
        return "Notifications [data=" + this.data + ", total=" + this.total + ", limit=" + this.limit + ", skip=" + this.skip + "]";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NotificationEntry
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

        @JsonProperty
        private NotificationEntry.Actor actor;

        @JsonProperty("target_object")
        private NotificationEntry.TargetObject targetObject;

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

        public NotificationEntry.Actor getActor()
        {
            return this.actor;
        }

        public NotificationEntry.TargetObject getTargetObject()
        {
            return this.targetObject;
        }

        public static enum Verb
        {
            POSTED_IN_SUBSCRIBED_COMMUNITY, JOINED_SUBSCRIBED_COMMUNITY, LIKED, COMMENTED_THREAD, COMMENTED
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TargetObject
        {
            @JsonProperty
            private String id;

            @JsonProperty
            private String name;

            @JsonProperty
            private String description;

            @JsonProperty("stream_id")
            private String streamId;

            @JsonProperty("editor_id")
            private String editorId;

            @JsonProperty("author_id")
            private String authorId;

            @JsonProperty
            private String visibility;

            @JsonProperty("post_type")
            private String postType;

            @JsonProperty("created_at")
            private String createdAt;

            @JsonProperty("updated_at")
            private String updatedAt;

            @JsonProperty
            private List<TargetObject.Translation> translations;

            public String getId()
            {
                return this.id;
            }

            public String getName()
            {
                return this.name;
            }

            public String getDescription()
            {
                return this.description;
            }

            public String getStreamId()
            {
                return this.streamId;
            }

            public String getEditorId()
            {
                return this.editorId;
            }

            public String getAuthorId()
            {
                return this.authorId;
            }

            public String getVisibility()
            {
                return this.visibility;
            }

            public String getPostType()
            {
                return this.postType;
            }

            public String getCreatedAt()
            {
                return this.createdAt;
            }

            public String getUpdatedAt()
            {
                return this.updatedAt;
            }

            public List<TargetObject.Translation> getTranslations()
            {
                return this.translations;
            }

            public static enum Visibility
            {
                USERS
            }

            public static enum PostType
            {
                FORUM
            }

            @Override
            public String toString()
            {
                return "TargetObject [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", streamId=" + this.streamId
                        + ", editorId=" + this.editorId + ", authorId=" + this.authorId + ", visibility=" + this.visibility + ", postType=" + this.postType
                        + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", translations=" + this.translations + "]";
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Translation
            {
                @JsonProperty
                private String id;

                @JsonProperty("lang")
                private String language;

                @JsonProperty("is_reviewed")
                private boolean isReviewed;

                @JsonProperty("title")
                private String title;

                @JsonProperty("body")
                private String body;

                public String getId()
                {
                    return this.id;
                }

                public String getLanguage()
                {
                    return this.language;
                }

                public boolean isReviewed()
                {
                    return this.isReviewed;
                }

                public String getTitle()
                {
                    return this.title;
                }

                public String getBody()
                {
                    return this.body;
                }

                @Override
                public String toString()
                {
                    return "Translation [id=" + this.id + ", language=" + this.language + ", isReviewed=" + this.isReviewed + ", title=" + this.title
                            + ", body=" + this.body + "]";
                }

                public static enum Language
                {
                    FR, ES, DE, IT, CS, RU, SH, TR, EN, PT
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Actor
        {
            @JsonProperty
            private String id;

            @JsonProperty("user_id")
            private String userId;

            @JsonProperty("username")
            private String userName;

            public String getId()
            {
                return this.id;
            }

            public String getUserId()
            {
                return this.userId;
            }

            public String getUserName()
            {
                return this.userName;
            }

        }
    }

}