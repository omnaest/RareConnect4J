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

import org.omnaest.rareconnect.RareConnectRESTUtils;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityStatistic;

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

}
