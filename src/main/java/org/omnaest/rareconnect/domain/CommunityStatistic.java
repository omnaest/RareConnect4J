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
package org.omnaest.rareconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityStatistic
{
	@JsonProperty
	private GenderStatistic gender;

	@JsonProperty
	private PostsStatistic posts;

	@JsonProperty("profile_type")
	private ProfileStatistic profileType;

	@JsonProperty
	private RoleStatistic role;

	public GenderStatistic getGender()
	{
		return this.gender;
	}

	public void setGender(GenderStatistic gender)
	{
		this.gender = gender;
	}

	public PostsStatistic getPosts()
	{
		return this.posts;
	}

	public void setPosts(PostsStatistic posts)
	{
		this.posts = posts;
	}

	public ProfileStatistic getProfileType()
	{
		return this.profileType;
	}

	public void setProfileType(ProfileStatistic profileType)
	{
		this.profileType = profileType;
	}

	public RoleStatistic getRole()
	{
		return this.role;
	}

	public void setRole(RoleStatistic role)
	{
		this.role = role;
	}

}
