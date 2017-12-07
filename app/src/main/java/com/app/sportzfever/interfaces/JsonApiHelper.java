package com.app.sportzfever.interfaces;

/**
 * Created by hemanta on 29-07-2017.
 */

public interface JsonApiHelper {

    String BASEURL = JsonApiHelper.BASEURL_LIVE;
    String WEBVIEWBASEURL = "https://www.sportzfever.com/";
    String WEBVIEWBASEURLBETA = "https://www.betasportzfever.com/";
     String BASEURL_LIVE = "https://sfscoring.sportzfever.com/";
    String TESTURL = "http://sfscoring.betasportzfever.com/";
    String LOGIN = "sflogin";
    String FORGOT = "forgetPassword";
    String GET_FEEDS = "getFeeds/";
    String GET_FEEDS_BY_AVTAR = "getFeedByAvatar/";
    String GET_FEEDS_MATCH = "getFeedOfMatchTournament/match/";
    String GET_FEEDS_TOURNAMENT = "getFeedOfMatchTournament/TOURNAMENT/";
    String GET_AVTARFEEDS = "getFeedByAvatar/";
    String GET_NOTIFICATION = "getUserNotifications/";
    String GET_NOTIFICATIONTEAM = "getTeamNotifications/";
    String GET_FRIENDREQUEST = "getFriendRequests/";
    String GET_USERFRIENDLIST = "getFriendsOfUser/";
    String GET_ALLTEAMTOURNAMENTFIXTEURES = "getAllUpComingTournamentOfTeamForFixtureAcceptance/";
    String GET_ALLTEAMTOURNAMENTFIXTEURESDETAILS = "getTeamsTournamentFixturesInvitation/";
    String GET_FOLLOWERLIST = "getAvatarFollowers/";
    String GET_FOLLOWINGLIST = "getFollowingAvatarsByUser/";
    String ACCEPTREJECTTOURNAMENTINVITATION = "RespondToTournamentTeamsInvitation/";
    String GET_TEAMJOINREQUEST = "getAllTeamInvitation/";
    String GET_MATCHINVITATIONAVAILABILITY = "getMatchAndPrctiseInvitations/";
    String GETALLSPORT = "getAllSport/";
    String GETMENU = "getMenu/";
    String GET_UPCOMINGEVENTS = "getUpcomingEvent/";
    String GET_TOURNAMENTFIXTURE = "getTournamentFixtures/";
    String GET_COMMENTS = "getComments/";
    String GET_AVTARPROFILEBIO = "getAvatarDetailsBio/";
    String UPDATE_AVTARDETAILS = "updateAvatarDetails";
    String UPDATE_PROFILEDETAILS = "generalProfileUpdate";
    String GET_LIKES = "getLikes/";
    String CREATE_TEAM_AVTAR = "createTeamAvatar";
    String UPDATE_PROFILE_PICTURE = "updateProfilePicture";
    String CREATE_TEAM = "createTeam";
    String GET_TOURNAMENT_DETAIL = "getTournamentDetail/";
    String GET_TEAM_PROFILE = "getTeamProfile/";
    String FOLLOW_UNFOLLOW = "followUnfollow";
    String JOINTEAM = "joinTeamRequest/";
    String LEAVETEAM = "leaveTeam/";
    String RespondToTOURNAMENTFIXTURE = "RespondToTeamTournamentFixture/";
    String GET_USERABOUT = "getUserProfile/";
    String GET_SHARE = "getShares/";
    String GET_FRIENDLIST = "getFriendsList/";
    String GET_PASTMATCHES = "getAllPastMatches/";
    String SEARCH = "search/";
    String GET_ALLTOURNAMENT = "getAllUpcomingTournaments/";
    String GET_ALLTOURNAMENTMATCHES = "getTournamentMatchDetails/";
    String GET_ALLMYTOURNAMENT = "getAllTournamentsByOrganiser/";
    String GET_LIVEMATCHES = "getAllLiveMatches/";
    String GET_UPCOMINGMATCHES = "getAllUpcomingMatches/";
    String GET_RECENTCHAT = "getRecentChatData/";
    String SHAREFEED = "shareFeed";
    String LIKEUNLIKEPOST = "likeAndUnlikePost";
    String UPDATESTATUS = "updateStatus";
    String DELETESTATUS = "deleteStatus/";
    String AVTARMYTEAMIADMIN = "getAvatarDetailsMyTeams/";
    String ALLTOURNAMNENTTEAM = "getTeamsInTournament/";
    String ALLSPORTTEAMDETIAL = "getTeamProfile/";
    String ALLTOURNAMNENTALBUMS = "getAlbumsImages/";
    String ALLTOURNAMNENTIMAGES = "getAlbumMatchTournament/tournament/";
    String ALLSPORTAVTARALBUMS = "getAlbumsAvatar/";
    String ALLSPORTAVTARALBUMSDETAILS = "getAlbumsImages/";
    String ALLTOURNAMNENTPOINTTABLES = "getGroupAndTeams/";
    String GALLERY = "getAlbums/";
    String STATS = "getPlayerStats/";
    String COMMENTARY = "commentary/";
    String GET_LIVE_SCORE = "getLiveScore/EVENT/";
    String UPCOMINGMATCHDETAILS = "getMatchStatisticsDetails/EVENT/";
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
    String ADDASFRIEND = "friendUnfriend";
    String RESPONDTOMATCHANDPRACTICEINVITATION = "RespondToMatchAndPractiseInvitation/";
    String RESPONDTOTEAMJOININVITATIONFROMTEAM = "RespondToTeamJoinInvitationFromTeam/";

    String SEND_MESSAGE = "postUserChat";
    String POST_GROUP_CHAT = "postGroupChat";
    String GET_CHATBOX_DATA = "getChatboxData/";
    String GET_GROUP_CHATBOX_DATA = "getGroupChatboxData/";
    String GET_ORGNIZERDATA = "tournamentMobilePageAction/";

}
