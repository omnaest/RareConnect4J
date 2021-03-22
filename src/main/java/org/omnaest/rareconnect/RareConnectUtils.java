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

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.omnaest.rareconnect.accessor.CommunityAccessor;
import org.omnaest.rareconnect.accessor.RareConnectAccessor;
import org.omnaest.rareconnect.internal.CommunityAccessorImpl;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils.Login;

public class RareConnectUtils
{

    public static RareConnectAccessor newInstance()
    {
        return new RareConnectAccessor()
        {
            private Optional<Login> login = Optional.empty();

            @Override
            public RareConnectAccessor login(String username, String password)
            {
                this.login = RareConnectRESTUtils.login(username, password);
                return this;
            }

            @Override
            public Optional<CommunityAccessor> findCommunityByNameContains(String communityNamePart)
            {
                return this.getAllCommunities()
                           .filter(community -> StringUtils.contains(community.getName(), communityNamePart))
                           .findFirst();
            }

            @Override
            public Optional<CommunityAccessor> findCommunityByName(String communityName)
            {
                return this.getAllCommunities()
                           .filter(community -> StringUtils.equals(community.getName(), communityName))
                           .findFirst();
            }

            @Override
            public Stream<CommunityAccessor> getAllCommunities()
            {
                return RareConnectRESTUtils.getCommunities(this.login)
                                           .stream()
                                           .map(community -> new CommunityAccessorImpl(community, () -> this.login));
            }

        };
    }

}
