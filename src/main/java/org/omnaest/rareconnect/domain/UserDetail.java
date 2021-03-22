package org.omnaest.rareconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String email;

    @JsonProperty
    private UserDetail.Profile profile;

    public String getId()
    {
        return this.id;
    }

    public String getEmail()
    {
        return this.email;
    }

    public UserDetail.Profile getProfile()
    {
        return this.profile;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile
    {
        @JsonProperty
        private String id;

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty
        private String username;

        @JsonProperty
        private boolean isEmailVerified;

        @JsonProperty("profile_type")
        private String profileType;

        public String getId()
        {
            return this.id;
        }

        public String getUserId()
        {
            return this.userId;
        }

        public String getUsername()
        {
            return this.username;
        }

        public boolean isEmailVerified()
        {
            return this.isEmailVerified;
        }

        public String getProfileType()
        {
            return this.profileType;
        }

    }
}