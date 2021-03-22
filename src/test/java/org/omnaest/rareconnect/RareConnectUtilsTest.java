package org.omnaest.rareconnect;

import org.junit.Test;
import org.omnaest.rareconnect.accessor.CommunityAccessor;

public class RareConnectUtilsTest
{

    @Test
    public void testGetAllCommunities() throws Exception
    {
        String username = "";
        String password = "";
        CommunityAccessor community = RareConnectUtils.newInstance()
                                                      .login(username, password)
                                                      .findCommunityByNameContains("IMBS")
                                                      .get();

        community.getForumPosts(0, 10);
    }

}
