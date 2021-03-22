package org.omnaest.rareconnect.accessor;

import java.util.Optional;
import java.util.stream.Stream;

public interface RareConnectAccessor
{
    public RareConnectAccessor login(String username, String password);

    public Optional<CommunityAccessor> findCommunityByNameContains(String communityNamePart);

    public Optional<CommunityAccessor> findCommunityByName(String communityName);

    Stream<CommunityAccessor> getAllCommunities();
}