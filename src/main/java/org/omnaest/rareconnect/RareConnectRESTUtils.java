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
package org.omnaest.rareconnect;

import java.util.List;

import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityResponse;
import org.omnaest.rareconnect.domain.CommunityStatistic;
import org.omnaest.rareconnect.domain.ForumPost;
import org.omnaest.rareconnect.domain.ForumPostsResponse;
import org.omnaest.rareconnect.domain.StoriesResponse;
import org.omnaest.rareconnect.domain.Story;
import org.omnaest.utils.rest.client.JSONRestClient;
import org.omnaest.utils.rest.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RareConnectRESTUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(RareConnectRESTUtils.class);

	public static List<Community> getCommunities()
	{
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org/api/v1/communities?%24populate%5B0%5D=slugs&%24populate%5B1%5D=translations&%24limit=1000&%24sort%5Bname%5D=1";
		LOG.info("Request for " + url);
		CommunityResponse response = restClient.requestGet(url, CommunityResponse.class);
		return response.getData();
	}

	public static CommunityStatistic getCommunityStatistic(String id)
	{
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org/api/v1/community-stats/" + id;
		LOG.info("Request for " + url);
		CommunityStatistic response = restClient.requestGet(url, CommunityStatistic.class);
		return response;
	}

	public static List<Story> getStories(String communityId)
	{
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org/api/v1/community-stories?communityId=" + communityId
				+ "&%24limit=5&%24populate%5B0%5D=author&%24populate%5B1%5D=translations";
		LOG.info("Request for " + url);
		StoriesResponse response = restClient.requestGet(url, StoriesResponse.class);
		return response.getData();
	}

	public static List<ForumPost> getFormPosts(String streamId, int skip, int limit)
	{
		if (limit > 25)
		{
			throw new RuntimeException("Limit cannot be larger than 25");
		}
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org/api/v1/posts?stream_id=" + streamId
				+ "&post_type=forum&%24limit=25&%24sort%5Bcreated_at%5D=-1&%24populate%5B0%5D=author&%24populate%5B1%5D=editor&%24populate%5B2%5D=translations&%24populate%5B3%5D=community&%24limit="
				+ limit + "&%24skip=" + skip;
		LOG.info("Request for " + url);
		ForumPostsResponse response = restClient.requestGet(url, ForumPostsResponse.class);
		return response.getData();
	}

}
