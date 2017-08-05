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
import org.omnaest.utils.rest.client.JSONRestClient;
import org.omnaest.utils.rest.client.RestClient;

public class RareConnectRESTUtils
{
	public static List<Community> getCommunities()
	{
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org/api/v1/communities?%24populate%5B0%5D=slugs&%24populate%5B1%5D=translations&%24limit=1000&%24sort%5Bname%5D=1";
		CommunityResponse response = restClient.requestGet(url, CommunityResponse.class);
		return response.getData();
	}

	public static CommunityStatistic getCommunityStatistic(String id)
	{
		RestClient restClient = new JSONRestClient();
		String url = "https://www.rareconnect.org//api/v1/community-stats/" + id;
		CommunityStatistic response = restClient.requestGet(url, CommunityStatistic.class);
		return response;
	}

	/*
	 * /api/v1/community-stories?communityId=db936ccb-3e5c-49b1-8a69-01a2ec2683b1&%24limit=5&%24populate%5B0%5D=author&%24populate%5B1%5D=translations
	 * /api/v1/posts?stream_id=cdf5087d-7224-41fe-b16f-6afa689fad1f&post_type=forum&%24limit=25&%24sort%5Bcreated_at%5D=-1&%24populate%5B0%5D=author&%24populate
	 * %5B1%5D=editor&%24populate%5B2%5D=translations&%24populate%5B3%5D=community
	 */
}
