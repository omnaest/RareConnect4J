package org.omnaest.rareconnect.accessor;

public interface CommentAccessor
{
    public String getId();

    public String getMessage();

    public String getAuthorName();

    public ForumPostAccessor getForumPost();

}
