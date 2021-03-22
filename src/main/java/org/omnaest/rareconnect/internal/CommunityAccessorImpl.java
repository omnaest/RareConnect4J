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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.omnaest.rareconnect.accessor.CommunityAccessor;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityStatistic;
import org.omnaest.rareconnect.domain.ForumPost;
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
    public CommunityStatistic getStatistic()
    {
        return RareConnectRESTUtils.getCommunityStatistic(this.community.getId());
    }

    @Override
    public List<ForumPost> getForumPosts(int skip, int limit)
    {
        int pageSize = 25;
        int pageNumbers = (limit - 1) / pageSize;
        return IntStream.rangeClosed(0, pageNumbers)
                        .map(pageNumber -> pageNumber * pageSize)
                        .parallel()
                        .mapToObj(start -> this.getForumPostsInternal(start + skip, pageSize, this.loginProvider.get()))
                        .flatMap(posts -> posts.stream())
                        .limit(limit)
                        .collect(Collectors.toList());
    }

    private List<ForumPost> getForumPostsInternal(int skip, int limit, Optional<Login> login)
    {
        return RareConnectRESTUtils.getForumPosts(this.community.getStreamId(), skip, limit, login);
    }

    @Override
    public List<ForumPost> getAllForumPosts()
    {
        List<ForumPost> retlist = new ArrayList<>();
        int skip = 0;
        int limit = 25;
        List<ForumPost> forumPosts = this.getForumPosts(skip, limit);
        while (!forumPosts.isEmpty())
        {
            retlist.addAll(forumPosts);
            skip += limit;
            forumPosts = this.getForumPosts(skip, limit);
        }
        return retlist;
    }

}
