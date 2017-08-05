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

public class GenderStatistic
{
	@JsonProperty
	private int	female;
	@JsonProperty
	private int	male;
	@JsonProperty
	private int	total;

	public int getFemale()
	{
		return this.female;
	}

	public void setFemale(int female)
	{
		this.female = female;
	}

	public int getMale()
	{
		return this.male;
	}

	public void setMale(int male)
	{
		this.male = male;
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