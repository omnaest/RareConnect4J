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

public class RoleStatistic
{
	@JsonProperty
	private int	admin;
	@JsonProperty
	private int	member;
	@JsonProperty
	private int	moderator;
	@JsonProperty
	private int	total;

	public int getAdmin()
	{
		return this.admin;
	}

	public void setAdmin(int admin)
	{
		this.admin = admin;
	}

	public int getMember()
	{
		return this.member;
	}

	public void setMember(int member)
	{
		this.member = member;
	}

	public int getModerator()
	{
		return this.moderator;
	}

	public void setModerator(int moderator)
	{
		this.moderator = moderator;
	}

	public int getTotal()
	{
		return this.total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

}
