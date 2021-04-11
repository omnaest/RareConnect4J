/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.rareconnect.internal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.omnaest.rareconnect.accessor.CommentAccessor;
import org.omnaest.rareconnect.accessor.CommunityAccessor;
import org.omnaest.rareconnect.accessor.ForumPostAccessor;
import org.omnaest.rareconnect.domain.Actor;
import org.omnaest.rareconnect.domain.Author;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityStatistic;
import org.omnaest.rareconnect.domain.ForumPost;
import org.omnaest.rareconnect.domain.ForumPostComment;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils.Login;

public class CommunityAccessorImpl implements CommunityAccessor
{
    private Community                 community;
    private Supplier<Optional<Login>> loginProvider;

    public CommunityAccessorImpl(Community community, Supplier<Optional<Login>> loginProvider)
    {
        super();
        this.community = community;
        this.loginProvider = loginProvider;
    }

    @Override
    public String getName()
    {
        return this.community.getName();
    }

    @Override
    public String getStreamId()
    {
        return this.community.getStreamId();
    }

    @Override
    public CommunityStatistic getStatistic()
    {
        return RareConnectRESTUtils.getCommunityStatistic(this.community.getId());
    }

    @Override
    public List<ForumPostAccessor> getForumPosts(int skip, int limit)
    {
        int pageSize = 25;
        int pageNumbers = (limit - 1) / pageSize;
        return IntStream.rangeClosed(0, pageNumbers)
                        .map(pageNumber -> pageNumber * pageSize)
                        .parallel()
                        .mapToObj(start -> this.getForumPostsInternal(start + skip, pageSize, this.loginProvider.get()))
                        .flatMap(posts -> posts.stream())
                        .limit(limit)
                        .map(forumPost -> new ForumPostAccessorImpl(forumPost, this.loginProvider))
                        .collect(Collectors.toList());
    }

    private List<ForumPost> getForumPostsInternal(int skip, int limit, Optional<Login> login)
    {
        return RareConnectRESTUtils.getForumPosts(this.community.getStreamId(), skip, limit, login);
    }

    @Override
    public Stream<ForumPostAccessor> getAllForumPosts()
    {
        AtomicBoolean hasStillEntries = new AtomicBoolean(true);
        return IntStream.range(0, 10000)
                        .filter(i -> hasStillEntries.get())
                        .mapToObj(pageIndex ->
                        {
                            int limit = 25;
                            int skip = pageIndex * limit;
                            List<ForumPostAccessor> forumPosts = this.getForumPosts(skip, limit);
                            return forumPosts;
                        })
                        .peek(entries -> hasStillEntries.set(!entries.isEmpty()))
                        .flatMap(List::stream);
    }

    @Override
    public Stream<ForumPostAccessor> findPostsByTitleContaining(String titleToken)
    {
        return this.getAllForumPosts()
                   .filter(post -> StringUtils.containsIgnoreCase(post.getTitle(), titleToken));
    }

    @Override
    public Optional<ForumPostAccessor> findForumPostByPostId(String postId)
    {
        return this.getAllForumPosts()
                   .filter(post -> StringUtils.equals(post.getId(), postId))
                   .findFirst();
    }

    @Override
    public CommunityAccessor postMessage(String title, String message)
    {
        RareConnectRESTUtils.postMessage(this.community.getStreamId(), title, message, this.loginProvider.get());
        return this;
    }

    public static class CommentAccessorImpl implements CommentAccessor
    {
        private final ForumPostComment  comment;
        private final ForumPostAccessor forumPost;

        public CommentAccessorImpl(ForumPostComment comment, ForumPostAccessor forumPost)
        {
            this.comment = comment;
            this.forumPost = forumPost;
        }

        @Override
        public String getMessage()
        {
            return this.comment.getBody();
        }

        @Override
        public String getId()
        {
            return this.comment.getId();
        }

        @Override
        public ForumPostAccessor getForumPost()
        {
            return this.forumPost;
        }

        @Override
        public String getAuthorName()
        {
            return Optional.ofNullable(this.comment.getAuthor())
                           .map(Author::getUserName)
                           .orElse("");
        }

    }

    public static class ForumPostAccessorImpl implements ForumPostAccessor
    {
        private final ForumPost                 forumPost;
        private final Supplier<Optional<Login>> loginProvider;

        public ForumPostAccessorImpl(ForumPost forumPost, Supplier<Optional<Login>> loginProvider)
        {
            this.forumPost = forumPost;
            this.loginProvider = loginProvider;
        }

        @Override
        public String getTitle()
        {
            return this.forumPost.getTitle();
        }

        @Override
        public String getId()
        {
            return this.forumPost.getId();
        }

        @Override
        public String getMessage()
        {
            return this.forumPost.getBody();
        }

        @Override
        public ForumPostAccessor comment(String message)
        {
            String itemId = this.forumPost.getId();
            RareConnectRESTUtils.comment(itemId, message, this.loginProvider.get())
                                .orElseThrow(() -> new IllegalStateException("Unable to comment to " + itemId));
            return this;
        }

        @Override
        public Stream<CommentAccessor> getComments()
        {
            AtomicBoolean hasStillEntries = new AtomicBoolean(true);
            return IntStream.range(0, 1000)
                            .filter(i -> hasStillEntries.get())
                            .mapToObj(pageIndex ->
                            {
                                int limit = 10;
                                int skip = limit * pageIndex;
                                return RareConnectRESTUtils.getPostComments(this.forumPost.getId(), skip, limit, this.loginProvider.get());
                            })
                            .peek(entries -> hasStillEntries.set(!entries.isEmpty()))
                            .flatMap(List::stream)
                            .map(comment -> new CommentAccessorImpl(comment, this));
        }

        @Override
        public Optional<CommentAccessor> findCommentById(String commentId)
        {
            return this.getComments()
                       .filter(comment -> StringUtils.equals(comment.getId(), commentId))
                       .findFirst();
        }

        @Override
        public String getAuthorName()
        {
            return Optional.ofNullable(this.forumPost)
                           .map(ForumPost::getAuthor)
                           .map(Actor::getUserName)
                           .orElse("");
        }

    }

}
