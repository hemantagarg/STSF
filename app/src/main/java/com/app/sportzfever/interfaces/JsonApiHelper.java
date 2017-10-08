package com.app.sportzfever.interfaces;

/**
 * Created by hemanta on 29-07-2017.
 */

public interface JsonApiHelper {

    String BASEURL = JsonApiHelper.TESTURL;
    String WEBVIEWBASEURL = "https://www.betasportzfever.com/";
   // String BASEURL_LIVE = "https://sfscoring.sportzfever.com/";
    String TESTURL = "http://sfscoring.betasportzfever.com/";
    String LOGIN = "sflogin";
    String GET_FEEDS = "getFeeds/";
    String GET_NOTIFICATION = "getNotifications/";
    String GET_FRIENDREQUEST = "getFriendRequests/";
    String GET_TEAMJOINREQUEST = "getAllTeamInvitation/";
    String GET_MATCHINVITATIONAVAILABILITY = "getMatchAndPrctiseInvitations/";
    String GETALLSPORT = "getAllSport/";
    String GET_UPCOMINGEVENTS = "getUpcomingEvent/";
    String GET_COMMENTS = "getComments/";
    String GET_AVTARPROFILEBIO = "getAvatarDetailsBio/";
    String GET_LIKES = "getLikes/";
    String GET_SHARE = "getShares/";
    String GET_FRIENDLIST = "getFriendsList/";
    String GET_PASTMATCHES = "getAllPastMatches/";
    String GET_ALLTOURNAMENT = "getAllUpcomingTournaments/";
    String GET_ALLMYTOURNAMENT = "getAllTournamentsByOrganiser/";
    String GET_LIVEMATCHES = "getAllLiveMatches/";
    String GET_UPCOMINGMATCHES = "getAllUpcomingMatches/";
    String GET_RECENTCHAT = "getRecentChatData/";
    String SHAREFEED = "shareFeed";
    String LIKEUNLIKEPOST = "likeAndUnlikePost";
    String UPDATESTATUS = "updateStatus";
    String DELETESTATUS = "deleteStatus/";
    String AVTARMYTEAMIADMIN = "getAvatarDetailsMyTeams/";
    String LIKEANDUNLIKEFEED = "likeAndUnlikePost/";
    String CREATESTATUS = "createStatus";
    String GETUSERALBUMS = "getUserAlbums/";
    String POSTCOMMENT = "comment";
    String SCORING = "scoring/";
    String VIEWALLMATCHESSCORE = "viewMatchScoreCard/";
    String UPDATECOMMENT = "updateComment";
    String DELETECOMMENT = "deleteComment/";
    String GET_JOINEDGROUPCHAT = "getJoinedGroups/";
    String SIGNUP = "sfSignUp";
    String ACCEPTREJECTMATCHCHALLENGEINVITATION = "RespondToMatchchallengeInvitation/";
    String ACCEPTREJECTSCORECHALLENGEINVITATION = "RespondToMatchScorerInvitation/";
    String ACCEPTFRIENDREQUEST = "confirmFriend";
    String RESPONDTOMATCHANDPRACTICEINVITATION = "RespondToMatchAndPractiseInvitation/";
    String RESPONDTOTEAMJOININVITATIONFROMTEAM = "RespondToTeamJoinInvitationFromTeam/";

    String SEND_MESSAGE = "postUserChat";
    String POST_GROUP_CHAT = "postGroupChat";
    String GET_CHATBOX_DATA = "getChatboxData/";
    String GET_GROUP_CHATBOX_DATA = "getGroupChatboxData/";
}
