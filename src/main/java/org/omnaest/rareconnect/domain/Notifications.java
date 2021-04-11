package org.omnaest.rareconnect.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notifications
{
    @JsonProperty
    private List<NotificationEntry> data;

    @JsonProperty
    private int total;

    @JsonProperty
    private int limit;

    @JsonProperty
    private int skip;

    public int getTotal()
    {
        return this.total;
    }

    public int getLimit()
    {
        return this.limit;
    }

    public int getSkip()
    {
        return this.skip;
    }

    public List<NotificationEntry> getData()
    {
        return this.data;
    }

    @Override
    public String toString()
    {
        return "Notifications [data=" + this.data + ", total=" + this.total + ", limit=" + this.limit + ", skip=" + this.skip + "]";
    }

}