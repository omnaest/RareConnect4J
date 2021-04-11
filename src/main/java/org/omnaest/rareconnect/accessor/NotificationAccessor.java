package org.omnaest.rareconnect.accessor;

import java.util.Optional;

public interface NotificationAccessor
{
    public boolean isAnyTypeOf(Type... type);

    public Optional<String> getThreadTitle();

    public Optional<String> getPostMessage();

    public Optional<CommentNotificationAccessor> asCommentNotificiation();

    public Optional<ForumPostNotificiationAccessor> asForumPostNotificiation();

    public enum Type
    {
        POSTED_IN_SUBSCRIBED_COMMUNITY, JOINED_SUBSCRIBED_COMMUNITY, LIKED, COMMENTED_THREAD, FOLLOWED
    }

    public static interface MessageNotificationAccessor
    {
        public String getMessage();
    }

    public static interface CommentNotificationAccessor extends MessageNotificationAccessor
    {
        public Optional<CommentAccessor> getComment();
    }

    public static interface ForumPostNotificiationAccessor extends MessageNotificationAccessor
    {
        public String getTitle();

        public Optional<ForumPostAccessor> getForumPost();
    }
}