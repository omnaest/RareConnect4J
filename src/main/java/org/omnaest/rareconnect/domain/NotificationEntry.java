package org.omnaest.rareconnect.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationEntry extends NotificationReducedEntry
{
    @JsonProperty
    private Actor actor;

    @JsonProperty("target_object")
    private TargetObject targetObject;

    @JsonProperty("action_object")
    private ActionObject actionObject;

    public Actor getActor()
    {
        return this.actor;
    }

    public NotificationEntry.TargetObject getTargetObject()
    {
        return this.targetObject;
    }

    public NotificationEntry.ActionObject getActionObject()
    {
        return this.actionObject;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActionObject
    {
        @JsonProperty("id")
        private String id;

        @JsonProperty("lang")
        private String language;

        @JsonProperty("title")
        private String title;

        @JsonProperty("body")
        private String body;

        @JsonProperty("item_type")
        private String itemType;

        @JsonProperty("item_id")
        private String itemId;

        @JsonProperty("author_id")
        private String authorId;

        @JsonProperty("translations")
        private List<Translation> translations;

        public String getId()
        {
            return this.id;
        }

        public String getLanguage()
        {
            return this.language;
        }

        public String getTitle()
        {
            return this.title;
        }

        public String getBody()
        {
            return this.body;
        }

        public String getItemType()
        {
            return this.itemType;
        }

        public String getItemId()
        {
            return this.itemId;
        }

        public String getAuthorId()
        {
            return this.authorId;
        }

        public List<Translation> getTranslations()
        {
            return this.translations;
        }

        @Override
        public String toString()
        {
            return "ActionObject [id=" + this.id + ", language=" + this.language + ", body=" + this.body + ", itemType=" + this.itemType + ", itemId="
                    + this.itemId + ", authorId=" + this.authorId + ", translations=" + this.translations + "]";
        }

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
            return "TargetObject [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", streamId=" + this.streamId + ", editorId="
                    + this.editorId + ", authorId=" + this.authorId + ", visibility=" + this.visibility + ", postType=" + this.postType + ", createdAt="
                    + this.createdAt + ", updatedAt=" + this.updatedAt + ", translations=" + this.translations + "]";
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
                return "Translation [id=" + this.id + ", language=" + this.language + ", isReviewed=" + this.isReviewed + ", title=" + this.title + ", body="
                        + this.body + "]";
            }

            public static enum Language
            {
                FR, ES, DE, IT, CS, RU, SH, TR, EN, PT
            }
        }
    }
}