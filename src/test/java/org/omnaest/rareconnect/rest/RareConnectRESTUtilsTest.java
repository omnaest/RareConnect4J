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
package org.omnaest.rareconnect.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.ForumPost;
import org.omnaest.rareconnect.domain.UserDetail;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils.Login;
import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.PredicateUtils;
import org.omnaest.utils.StringUtils;

public class RareConnectRESTUtilsTest
{

    @Test
    @Ignore
    public void testGetCommunities() throws Exception
    {
        try
        {
            List<Community> communities = RareConnectRESTUtils.getCommunities();
            System.out.println(JSONHelper.prettyPrint(communities));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    @Ignore
    public void testGetFilterCommunities() throws Exception
    {
        try
        {
            List<Community> communities = RareConnectRESTUtils.getCommunities();
            System.out.println(JSONHelper.prettyPrint(communities.stream()
                                                                 .filter(c -> c.getName()
                                                                               .contains("IMBS"))
                                                                 .collect(Collectors.toList())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

    }

    @Test
    @Ignore
    public void testGetForumPosts() throws Exception
    {
        String streamId = "439ca325-c213-44c8-900e-c1ed96eb6c11";//"cdf5087d-7224-41fe-b16f-6afa689fad1f";
        List<ForumPost> formPosts = RareConnectRESTUtils.getForumPosts(streamId, 0, 10);
        System.out.println(formPosts.size());
        System.out.println(JSONHelper.prettyPrint(formPosts));
    }

    @Test
    @Ignore
    public void testGetForumEntriesTrimethylaminuria()
    {

        RareConnectRESTUtils.getCommunities()
                            .stream()
                            .filter(iCommunity -> org.apache.commons.lang3.StringUtils.contains(iCommunity.getName(), "Trimethylaminuria"))
                            .forEach(community ->
                            {

                                //                                                  
                                //                                                  .findFirst()
                                //                                                  .get();

                                List<String> csvTokens = new ArrayList<>();

                                String name = community.getName();
                                csvTokens.add(name);

                                String streamId = community.getStreamId();
                                //                                System.out.println(streamId);

                                Map<String, AtomicInteger> keywordToCounter = new LinkedHashMap<>();
                                Arrays.asList("fecal", "poo", "poop", "cabbage", "garbage", "urine", "ammonia", "sulphur", "sulfur", "fish", "sweat", "musty",
                                              "dead", "animal", "vomit", "acetic", "rubbish", "burned", "rubber") //"suicide", "death", "job"
                                      .forEach(keyword ->
                                      {
                                          keywordToCounter.put(keyword, new AtomicInteger());
                                      });

                                int numberOfPosts = 20000;
                                int currentOffset = 0;
                                int currentNumberOfThreads = 0;
                                for (int ii = 0; ii < numberOfPosts / 25; ii++)
                                {
                                    //                                    System.out.println("Group: " + ii);
                                    List<ForumPost> forumPosts = RareConnectRESTUtils.getForumPosts(streamId, currentOffset, 25);
                                    currentOffset += 25;
                                    currentNumberOfThreads += forumPosts.size();

                                    if (forumPosts.isEmpty())
                                    {
                                        break;
                                    }

                                    Stream<String> posts = forumPosts.stream()
                                                                     .map(ipost -> ipost.getBody()
                                                                                        .toLowerCase());

                                    posts.flatMap(ibody -> (Stream<String>) StringUtils.splitToStream(ibody, " ,.-?!'\")(/:"))
                                         .filter(PredicateUtils.notBlank())
                                         .forEach(keyword ->
                                         {
                                             if (keywordToCounter.containsKey(keyword))
                                             {
                                                 keywordToCounter.get(keyword)
                                                                 .incrementAndGet();
                                             }
                                         });

                                }

                                System.out.println("Number of threads: " + currentNumberOfThreads);

                                StringBuilder sb = new StringBuilder();
                                sb.append("token;count\n");
                                keywordToCounter.entrySet()
                                                .forEach(entry ->
                                                {
                                                    sb.append(entry.getKey() + ";" + entry.getValue()
                                                                                          .get());
                                                    sb.append("\n");
                                                });

                                System.out.println(sb);

                            });
    }

    @Test
    @Ignore
    public void testLogin() throws Exception
    {
        String username = "user";
        Optional<Login> login = RareConnectRESTUtils.login(username, "password");
        UserDetail userDetails = RareConnectRESTUtils.getUserDetails(login.get());
        assertNotNull(userDetails);

        assertEquals(login.get()
                          .getUser()
                          .getId(),
                     userDetails.getProfile()
                                .getUserId());
        assertEquals(username, userDetails.getProfile()
                                          .getUsername());
    }

}
