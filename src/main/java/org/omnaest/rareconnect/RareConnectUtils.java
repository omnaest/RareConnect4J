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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.omnaest.rareconnect.accessor.CommentAccessor;
import org.omnaest.rareconnect.accessor.CommunityAccessor;
import org.omnaest.rareconnect.accessor.ForumPostAccessor;
import org.omnaest.rareconnect.accessor.NotificationAccessor;
import org.omnaest.rareconnect.accessor.NotificationsAccessor;
import org.omnaest.rareconnect.accessor.RareConnectAccessor;
import org.omnaest.rareconnect.domain.NotificationEntry;
import org.omnaest.rareconnect.domain.NotificationEntry.ActionObject;
import org.omnaest.rareconnect.domain.NotificationEntry.TargetObject;
import org.omnaest.rareconnect.domain.NotificationReducedEntry;
import org.omnaest.rareconnect.domain.Notifications;
import org.omnaest.rareconnect.domain.NotificationsReducedData;
import org.omnaest.rareconnect.domain.UserDetail;
import org.omnaest.rareconnect.domain.UserDetail.Profile;
import org.omnaest.rareconnect.internal.CommunityAccessorImpl;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils;
import org.omnaest.rareconnect.rest.RareConnectRESTUtils.Login;
import org.omnaest.utils.PredicateUtils;
import org.omnaest.utils.element.bi.BiElement;

public class RareConnectUtils
{

    public static RareConnectAccessor newInstance()
    {
        return new RareConnectAccessorImpl();
    }

    public static interface RareConnectAccessorInternal extends RareConnectAccessor
    {

        public Optional<CommunityAccessor> findCommunityByStreamId(String streamId);

    }

    private static class RareConnectAccessorImpl implements RareConnectAccessorInternal
    {
        private Optional<Login>      login       = Optional.empty();
        private Optional<UserDetail> userDetails = Optional.empty();

        @Override
        public RareConnectAccessor login(String username, String password)
        {
            this.login = RareConnectRESTUtils.login(username, password);
            this.userDetails = Optional.ofNullable(RareConnectRESTUtils.getUserDetails(this.login.orElseThrow(() -> new IllegalStateException("Failed to login."))));
            return this;
        }

        @Override
        public Optional<CommunityAccessor> findCommunityByNameContains(String communityNamePart)
        {
            return this.fetchAllCommunities()
                       .filter(community -> StringUtils.contains(community.getName(), communityNamePart))
                       .findFirst();
        }

        @Override
        public Optional<CommunityAccessor> findCommunityByName(String communityName)
        {
            return this.fetchAllCommunities()
                       .filter(community -> StringUtils.equals(community.getName(), communityName))
                       .findFirst();
        }

        @Override
        public Stream<CommunityAccessor> fetchAllCommunities()
        {
            return RareConnectRESTUtils.getCommunities(this.login)
                                       .stream()
                                       .map(community -> new CommunityAccessorImpl(community, () -> this.login));
        }

        @Override
        public NotificationsAccessor fetchNewNotifications()
        {
            boolean isSeen = false;
            return this.fetchNotifications(isSeen);
        }

        @Override
        public NotificationsAccessor fetchAllNotifications()
        {
            boolean isSeen = true;
            return this.fetchNotifications(isSeen);
        }

        private NotificationsAccessor fetchNotifications(boolean isSeen)
        {
            String profileId = this.userDetails.map(UserDetail::getProfile)
                                               .map(Profile::getId)
                                               .orElseThrow(() -> new IllegalStateException("You need to login first."));
            Stream<Notifications> notifications = RareConnectRESTUtils.getNotifications(profileId, isSeen, this.login);
            return new NotificationsAccessorImpl(notifications.map(Notifications::getData)
                                                              .filter(PredicateUtils.notNull())
                                                              .flatMap(List::stream),
                                                 this);
        }

        @Override
        public NotificationsAccessor fetchNewNotificationsAndMarkAsSeen()
        {
            String profileId = this.userDetails.map(UserDetail::getProfile)
                                               .map(Profile::getId)
                                               .orElseThrow(() -> new IllegalStateException("You need to login first."));
            boolean isSeen = false;
            Stream<Notifications> unfilteredNotifications = RareConnectRESTUtils.getNotifications(profileId, isSeen, this.login);
            Stream<NotificationsReducedData> notifications = RareConnectRESTUtils.getNotificationsAndMarkAsSeen(profileId, this.login);

            Set<String> notificationIdsMarkedAsSeen = notifications.flatMap(NotificationsReducedData::stream)
                                                                   .filter(PredicateUtils.notNull())
                                                                   .map(NotificationReducedEntry::getId)
                                                                   .filter(PredicateUtils.notBlank())
                                                                   .collect(Collectors.toSet());

            return new NotificationsAccessorImpl(unfilteredNotifications.map(Notifications::getData)
                                                                        .filter(PredicateUtils.notNull())
                                                                        .flatMap(List::stream)
                                                                        .filter(PredicateUtils.map(NotificationEntry::getId)
                                                                                              .andIsContainedIn(notificationIdsMarkedAsSeen)),
                                                 this);
        }

        @Override
        public Optional<CommunityAccessor> findCommunityByStreamId(String streamId)
        {
            return this.fetchAllCommunities()
                       .filter(community -> StringUtils.equals(community.getStreamId(), streamId))
                       .findFirst();
        }

    }

    private static class NotificationsAccessorImpl implements NotificationsAccessor
    {
        private final Stream<NotificationEntry>   notifications;
        private final RareConnectAccessorInternal rareConnectAccessor;

        public NotificationsAccessorImpl(Stream<NotificationEntry> notifications, RareConnectAccessorInternal rareConnectAccessor)
        {
            this.notifications = notifications;
            this.rareConnectAccessor = rareConnectAccessor;
        }

        @Override
        public Stream<NotificationAccessor> stream()
        {
            return Optional.ofNullable(this.notifications)
                           .orElse(Stream.empty())
                           .filter(PredicateUtils.notNull())
                           .map(notification -> new NotificationAccessorImpl(notification, this.rareConnectAccessor));
        }

    }

    private static class NotificationAccessorImpl implements NotificationAccessor
    {
        private final NotificationEntry           notification;
        private final RareConnectAccessorInternal rareConnectAccessor;

        public NotificationAccessorImpl(NotificationEntry notification, RareConnectAccessorInternal rareConnectAccessor)
        {
            this.notification = notification;
            this.rareConnectAccessor = rareConnectAccessor;
        }

        @Override
        public Optional<String> getThreadTitle()
        {
            return Optional.ofNullable(this.notification)
                           .map(NotificationEntry::getTargetObject)
                           .map(TargetObject::getName);
        }

        @Override
        public Optional<String> getPostMessage()
        {
            return Optional.ofNullable(this.notification)
                           .map(NotificationEntry::getActionObject)
                           .map(ActionObject::getBody);
        }

        @Override
        public boolean isAnyTypeOf(Type... type)
        {
            return Arrays.asList(type)
                         .stream()
                         .map(Type::name)
                         .map(StringUtils::lowerCase)
                         .anyMatch(typeName -> StringUtils.equalsIgnoreCase(typeName, this.notification.getVerb()));
        }

        @Override
        public Optional<CommentNotificationAccessor> asCommentNotificiation()
        {
            RareConnectAccessorInternal rareConnectAccessor = this.rareConnectAccessor;

            return Optional.ofNullable(this.notification)
                           .filter(notification -> StringUtils.equalsIgnoreCase("comment", notification.getActionObjectType()))
                           .map(notification -> BiElement.of(notification.getActionObject(), notification.getTargetObject()))
                           .map(bi -> new CommentNotificationAccessor()
                           {
                               @Override
                               public String getMessage()
                               {
                                   return bi.getFirst()
                                            .getBody();
                               }

                               @Override
                               public Optional<CommentAccessor> getComment()
                               {
                                   String postId = bi.getSecond()
                                                     .getId();
                                   String streamId = bi.getSecond()
                                                       .getStreamId();
                                   String itemId = bi.getFirst()
                                                     .getItemId();
                                   return rareConnectAccessor.findCommunityByStreamId(streamId)
                                                             .flatMap(community -> community.findForumPostByPostId(postId))
                                                             .flatMap(post -> post.findCommentById(itemId));
                               }
                           });
        }

        @Override
        public Optional<ForumPostNotificiationAccessor> asForumPostNotificiation()
        {
            RareConnectAccessorInternal rareConnectAccessor = this.rareConnectAccessor;

            return Optional.ofNullable(this.notification)
                           .filter(notification -> StringUtils.equalsIgnoreCase("post", notification.getActionObjectType()))
                           .map(notification -> BiElement.of(notification.getActionObject(), notification.getTargetObject()))
                           .map(bi -> new ForumPostNotificiationAccessor()
                           {
                               @Override
                               public String getTitle()
                               {
                                   return Optional.ofNullable(bi.getFirst())
                                                  .map(ActionObject::getTitle)
                                                  .orElse("");
                               }

                               @Override
                               public String getMessage()
                               {
                                   return Optional.ofNullable(bi.getFirst())
                                                  .map(ActionObject::getBody)
                                                  .orElse("");
                               }

                               @Override
                               public Optional<ForumPostAccessor> getForumPost()
                               {
                                   String postId = bi.getSecond()
                                                     .getId();
                                   String streamId = bi.getSecond()
                                                       .getStreamId();
                                   return rareConnectAccessor.findCommunityByStreamId(streamId)
                                                             .flatMap(community -> community.findForumPostByPostId(postId));
                               }

                           });
        }
    }

}
