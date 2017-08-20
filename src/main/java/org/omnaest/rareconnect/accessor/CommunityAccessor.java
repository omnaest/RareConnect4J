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
package org.omnaest.rareconnect.accessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.omnaest.rareconnect.RareConnectRESTUtils;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityStatistic;
import org.omnaest.rareconnect.domain.ForumPost;

public class CommunityAccessor
{
	private Community community;

	public CommunityAccessor(Community community)
	{
		super();
		this.community = community;
	}

	public Community get()
	{
		return this.community;
	}

	public CommunityStatistic getStatistic()
	{
		return RareConnectRESTUtils.getCommunityStatistic(this.community.getId());
	}

	public List<ForumPost> getForumPosts(int skip, int limit)
	{
		int pageSize = 25;
		int pageNumbers = (limit - 1) / pageSize;
		return IntStream.rangeClosed(0, pageNumbers)
						.map(pageNumber -> pageNumber * pageSize)
						.parallel()
						.mapToObj(start -> this.getForumPostsInternal(start + skip, pageSize))
						.flatMap(posts -> posts.stream())
						.limit(limit)
						.collect(Collectors.toList());
	}

	private List<ForumPost> getForumPostsInternal(int skip, int limit)
	{
		return RareConnectRESTUtils.getFormPosts(this.community.getStreamId(), skip, limit);
	}

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
