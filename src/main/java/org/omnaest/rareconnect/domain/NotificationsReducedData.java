package org.omnaest.rareconnect.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationsReducedData extends ArrayList<NotificationReducedEntry>
{
    private static final long serialVersionUID = 6202904614264850023L;
}