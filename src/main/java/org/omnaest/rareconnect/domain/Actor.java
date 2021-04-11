package org.omnaest.rareconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor
{
    @JsonProperty
    private String id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("profile_type")
    private String profileType;

    @JsonProperty
    private String avatar;

    @JsonProperty("website_url")
    private String website;

    @JsonProperty("lang")
    private String language;

    @JsonProperty
    private String description;

    @JsonProperty
    private String gender;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty
    private String visibility;

    @JsonProperty("is_removed")
    private boolean isRemoved;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty
    private int age;

    @JsonProperty
    private String role;

    @JsonProperty("hasEmail")
    private boolean hasEmail;

    @JsonProperty("isEmailVerified")
    private boolean isEmailVerifiied;

    @JsonProperty("canContactByEmail")
    private boolean canContactByEmail;

    public String getId()
    {
        return this.id;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getProfileType()
    {
        return this.profileType;
    }

    public String getAvatar()
    {
        return this.avatar;
    }

    public String getWebsite()
    {
        return this.website;
    }

    public String getLanguage()
    {
        return this.language;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    public String getVisibility()
    {
        return this.visibility;
    }

    public boolean isRemoved()
    {
        return this.isRemoved;
    }

    public String getCreatedAt()
    {
        return this.createdAt;
    }

    public String getUpdatedAt()
    {
        return this.updatedAt;
    }

    public int getAge()
    {
        return this.age;
    }

    public String getRole()
    {
        return this.role;
    }

    public boolean isHasEmail()
    {
        return this.hasEmail;
    }

    public boolean isEmailVerifiied()
    {
        return this.isEmailVerifiied;
    }

    public boolean isCanContactByEmail()
    {
        return this.canContactByEmail;
    }

}