package org.omnaest.rareconnect.accessor;

import java.util.Optional;
import java.util.stream.Stream;

public interface ForumPostAccessor
{
    public String getId();

    public String getTitle();

    public String getMessage();

    public String getAuthorName();

    public Optional<CommentAccessor> findCommentById(String commentId);

    public Stream<CommentAccessor> getComments();

    public ForumPostAccessor comment(String message);
}
