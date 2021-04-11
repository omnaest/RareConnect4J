package org.omnaest.rareconnect;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.rareconnect.accessor.CommunityAccessor;
import org.omnaest.rareconnect.accessor.RareConnectAccessor;

public class RareConnectUtilsTest
{

    @Test
    @Ignore
    public void testFetchAllCommunities() throws Exception
    {
        String username = "";
        String password = "";
        RareConnectAccessor rareConnectAccessor = RareConnectUtils.newInstance()
                                                                  .login(username, password);
        CommunityAccessor community = rareConnectAccessor.findCommunityByNameContains("IMBS")
                                                         .get();

        //        community.findPostsByTitleContaining("benzimidazole")
        //                 .forEach(post ->
        //                 {
        //                     System.out.println(post.getTitle());
        //                     System.out.println(post.getMessage());
        //                     System.out.println("----");
        //
        //                     post.getComments()
        //                         .forEach(comment ->
        //                         {
        //                             System.out.println(comment.getMessage());
        //                             System.out.println("-----------------------");
        //                         });
        //                     //                     post.comment("That is super interesting!");
        //
        //                     System.out.println();
        //                 });

        rareConnectAccessor.fetchAllNotifications()
                           .forEach(notification ->
                           {
                               //                               notification.getThreadTitle()
                               //                                           .ifPresent(title ->
                               //                                           {
                               //                                               System.out.println(title);
                               //                                           });
                               //                               notification.getPostMessage()
                               //                                           .ifPresent(message ->
                               //                                           {
                               //                                               System.out.println(message);
                               //                                           });

                               notification.asCommentNotificiation()
                                           .ifPresent(commentNotification ->
                                           {
                                               System.out.println(commentNotification.getComment()
                                                                                     .map(c -> c.getMessage()));
                                           });
                           });
    }

}
