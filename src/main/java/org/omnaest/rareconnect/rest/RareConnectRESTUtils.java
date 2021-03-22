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

import java.util.List;
import java.util.Optional;

import org.omnaest.rareconnect.domain.Community;
import org.omnaest.rareconnect.domain.CommunityResponse;
import org.omnaest.rareconnect.domain.CommunityStatistic;
import org.omnaest.rareconnect.domain.ForumPost;
import org.omnaest.rareconnect.domain.ForumPostsResponse;
import org.omnaest.rareconnect.domain.Notifications;
import org.omnaest.rareconnect.domain.StoriesResponse;
import org.omnaest.rareconnect.domain.Story;
import org.omnaest.rareconnect.domain.UserDetail;
import org.omnaest.utils.rest.client.RestClient;
import org.omnaest.utils.rest.client.RestClient.MediaType;
import org.omnaest.utils.rest.client.RestClient.RequestBuilderWithUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RareConnectRESTUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(RareConnectRESTUtils.class);

    public static Optional<Login> login(String username, String password)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/authentication";
        LOG.debug("Request for " + url);
        Login response = restClient.request()
                                   .toUrl(url)
                                   .post(new LoginBody(username, password), Login.class);
        return Optional.ofNullable(response);
    }

    public static UserDetail getUserDetails(Login login)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/users/" + login.getUser()
                                                                        .getId();
        LOG.debug("Request for " + url);
        return restClient.request()
                         .toUrl(url)
                         .withHeader("Authorization", "Bearer " + login.getAccessToken())
                         .get(UserDetail.class);
    }

    public static List<Community> getCommunities()
    {
        return getCommunities(Optional.empty());
    }

    public static List<Community> getCommunities(Optional<Login> login)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/communities?%24populate%5B0%5D=slugs&%24populate%5B1%5D=translations&%24limit=1000&%24sort%5Bname%5D=1";
        LOG.debug("Request for " + url);
        return applyLoginTo(restClient.request()
                                      .toUrl(url),
                            login).get(CommunityResponse.class)
                                  .getData();
    }

    private static RequestBuilderWithUrl applyLoginTo(RequestBuilderWithUrl requestBuilder, Optional<Login> login)
    {
        if (login.isPresent())
        {
            requestBuilder.withHeader("Authorization", "Bearer " + login.map(Login::getAccessToken)
                                                                        .orElse(""));
        }
        return requestBuilder;
    }

    public static Notifications getNotifications(String profileId, boolean isSeen)
    {
        return getNotifications(profileId, isSeen, Optional.empty());
    }

    public static Notifications getNotifications(String profileId, boolean isSeen, Optional<Login> login)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/notifications?profile_id=" + profileId + "&is_seen=" + isSeen + "&%24limit=0";
        LOG.debug("Request for " + url);
        return applyLoginTo(restClient.request()
                                      .toUrl(url),
                            login).get(Notifications.class);
    }

    public static CommunityStatistic getCommunityStatistic(String id)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/community-stats/" + id;
        LOG.debug("Request for " + url);
        CommunityStatistic response = restClient.requestGet(url, CommunityStatistic.class);
        return response;
    }

    public static List<Story> getStories(String communityId)
    {
        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/community-stories?communityId=" + communityId
                + "&%24limit=5&%24populate%5B0%5D=author&%24populate%5B1%5D=translations";
        LOG.debug("Request for " + url);
        StoriesResponse response = restClient.requestGet(url, StoriesResponse.class);
        return response.getData();
    }

    public static List<ForumPost> getForumPosts(String streamId, int skip, int limit)
    {
        return getForumPosts(streamId, skip, limit, Optional.empty());
    }

    public static List<ForumPost> getForumPosts(String streamId, int skip, int limit, Optional<Login> login)
    {
        if (limit > 25)
        {
            throw new RuntimeException("Limit cannot be larger than 25");
        }

        RestClient restClient = createRestClient();
        String url = "https://www.rareconnect.org/api/v1/posts?stream_id=" + streamId + "&is_pinned=false&post_type=forum&%24limit=" + limit
                + "&%24sort%5Bhot_score%5D=-1&%24populate%5B0%5D=author&%24populate%5B1%5D=editor&%24populate%5B2%5D=translations&%24populate%5B3%5D=community"
                + "&%24skip=" + skip;
        LOG.debug("Request for " + url);
        ForumPostsResponse response = applyLoginTo(restClient.request()
                                                             .toUrl(url),
                                                   login).get(ForumPostsResponse.class);
        return response.getData();
    }

    private static RestClient createRestClient()
    {
        return RestClient.newJSONRestClient()
                         .withoutSSLHostnameVerification()
                         //                         .withDefaultLocalhostProxy()
                         .withAcceptMediaType(MediaType.APPLICATION_JSON);
    }

    protected static class LoginBody
    {
        @JsonProperty("strategy")
        private String strategy = "local";

        @JsonProperty("login")
        private String username;

        @JsonProperty("password")
        private String password;

        public LoginBody(String username, String password)
        {
            super();
            this.username = username;
            this.password = password;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Login
    {
        @JsonProperty
        private String accessToken;

        @JsonProperty
        private User user;

        public String getAccessToken()
        {
            return this.accessToken;
        }

        public User getUser()
        {
            return this.user;
        }

        @Override
        public String toString()
        {
            return "LoginResponse [accessToken=" + this.accessToken + ", user=" + this.user + "]";
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class User
        {
            @JsonProperty
            private String id;

            @JsonProperty
            private String email;

            @JsonProperty
            private String role;

            @JsonProperty
            private boolean isVerified;

            public String getId()
            {
                return this.id;
            }

            public String getEmail()
            {
                return this.email;
            }

            public String getRole()
            {
                return this.role;
            }

            public boolean isVerified()
            {
                return this.isVerified;
            }

            @Override
            public String toString()
            {
                return "User [id=" + this.id + ", email=" + this.email + ", role=" + this.role + ", isVerified=" + this.isVerified + "]";
            }

        }

    }

}
