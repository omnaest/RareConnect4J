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

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.ForumPost;
import org.omnaest.utils.JSONHelper;

public class RareConnectRESTUtilsTest
{

	@Test
	@Ignore
	public void testGetCommunities() throws Exception
	{
		List<Community> communities = RareConnectRESTUtils.getCommunities();
		System.out.println(JSONHelper.prettyPrint(communities));
	}

	@Test
	public void testGetFormPosts() throws Exception
	{
		String streamId = "cdf5087d-7224-41fe-b16f-6afa689fad1f";
		List<ForumPost> formPosts = RareConnectRESTUtils.getFormPosts(streamId, 3000, 25);
		System.out.println(formPosts.size());
		System.out.println(JSONHelper.prettyPrint(formPosts));
	}

}
