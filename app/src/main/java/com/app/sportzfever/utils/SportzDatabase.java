package com.app.sportzfever.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.sportzfever.models.dbmodels.Avatar;
import com.app.sportzfever.models.dbmodels.CricketBall;
import com.app.sportzfever.models.dbmodels.CricketBallJson;
import com.app.sportzfever.models.dbmodels.CricketInning;
import com.app.sportzfever.models.dbmodels.CricketOver;
import com.app.sportzfever.models.dbmodels.CricketScoreCard;
import com.app.sportzfever.models.dbmodels.CricketSelectedTeamPlayers;
import com.app.sportzfever.models.dbmodels.Event;
import com.app.sportzfever.models.dbmodels.GeneralProfile;
import com.app.sportzfever.models.dbmodels.MatchScorer;
import com.app.sportzfever.models.dbmodels.MatchSfPlayerName;
import com.app.sportzfever.models.dbmodels.MatchTeamRoles;
import com.app.sportzfever.models.dbmodels.Matches;
import com.app.sportzfever.models.dbmodels.Roster;
import com.app.sportzfever.models.dbmodels.TossJson;
import com.app.sportzfever.models.dbmodels.User;
import com.app.sportzfever.models.dbmodels.apimodel.BattingStat;
import com.app.sportzfever.models.dbmodels.apimodel.BattingStatViewModel;
import com.app.sportzfever.models.dbmodels.apimodel.BowlingStat;
import com.app.sportzfever.models.dbmodels.apimodel.BowlingViewModal;
import com.app.sportzfever.models.dbmodels.apimodel.ExtraAndFOW;
import com.app.sportzfever.models.dbmodels.apimodel.ExtraRuns;
import com.app.sportzfever.models.dbmodels.apimodel.Inning;
import com.app.sportzfever.models.dbmodels.apimodel.Match;
import com.app.sportzfever.models.dbmodels.apimodel.MatchDate;
import com.app.sportzfever.models.dbmodels.apimodel.MatchStaticsData;
import com.app.sportzfever.models.dbmodels.apimodel.OverBall;
import com.app.sportzfever.models.dbmodels.apimodel.ResponseModel;
import com.app.sportzfever.models.dbmodels.apimodel.Scorers;
import com.app.sportzfever.models.dbmodels.apimodel.StartSecondInningResponseModel;
import com.app.sportzfever.models.dbmodels.apimodel.Team;
import com.app.sportzfever.models.dbmodels.apimodel.TeamScorer;
import com.app.sportzfever.models.dbmodels.apimodel.TeamSquad;
import com.app.sportzfever.models.dbmodels.apimodel.UniverseResponseModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SportzDatabase {
    private static final String TAG = "SportzDatabase";
    private static final String DATABASE_NAME = "Medicine_Detail";
    private static String DATABASE_TABLE;
    private static final int DATABASE_VERSION = 1;


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public SportzDatabase(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, "database", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try
            {

                // db.execSQL("create table medData(match_id text primary key,id text,team1Id text, team2Id text)");
                db.execSQL("create TABLE Toss_local(Id INTEGER primary key AUTOINCREMENT NOT NULL, cricket_inning_id INTEGER NOT NULL DEFAULT 0,jsonData TEXT,serverinningId INTEGER NOT NULL DEFAULT 0  )");
                db.execSQL("create TABLE cricket_balls_local(Id INTEGER primary key AUTOINCREMENT NOT NULL, cricket_balls_id INTEGER NOT NULL DEFAULT 0,jsonData TEXT,serverID INTEGER NOT NULL DEFAULT 0  )");
                db.execSQL("create TABLE cricket_balls(localId INTEGER primary key AUTOINCREMENT NOT NULL, id INTEGER NOT NULL DEFAULT 0,syncStatus INTEGER NOT NULL DEFAULT 0, ballCountInOver TEXT NOT NULL,inningOverCount TEXT NOT NULL,runScored TEXT NOT NULL,extraRuns TEXT NOT NULL,isFour TEXT NOT NULL,isSix TEXT NOT NULL,runScoredOnNoBall TEXT ,isNoBall TEXT NOT NULL,isWideBall TEXT NOT NULL,runScoredOnWideball TEXT DEFAULT NULL,isBye TEXT NOT NULL,runScoredOnBye TEXT DEFAULT NULL,isLegBye TEXT NOT NULL,runScoredOnLegBye TEXT DEFAULT NULL,isWicket TEXT NOT NULL,wicketType TEXT DEFAULT NULL,comments TEXT DEFAULT NULL,batsmanId TEXT DEFAULT NULL,bowlerId TEXT DEFAULT NULL,inningId TEXT DEFAULT NULL,overId TEXT DEFAULT NULL,matchId TEXT DEFAULT NULL,caughtById TEXT DEFAULT NULL,runOutById TEXT DEFAULT NULL,stumpedById TEXT DEFAULT NULL,outBatsmanId TEXT DEFAULT NULL)");
                db.execSQL("CREATE TABLE cricket_innings (totalOvers TEXT NOT NULL, id INTEGER NOT NULL DEFAULT 0,syncStatus INTEGER NOT NULL DEFAULT 0 ,wickets TEXT NOT NULL  ,isDeclared TEXT  ,bowlingTeamId TEXT  ,isScoredOnSF TEXT NOT NULL  ,matchId TEXT  ,inningNumber TEXT NOT NULL ,playing TEXT  ,daySession TEXT  ,totalRunsScored TEXT NOT NULL ,state TEXT NOT NULL  ,extras TEXT  ,playedOvers TEXT NOT NULL ,battingTeamId TEXT  ,localId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,day TEXT );");
                db.execSQL("CREATE TABLE cricket_overs (current TEXT,id INTEGER NOT NULL DEFAULT 0,syncStatus INTEGER NOT NULL DEFAULT 0,wicketsInOver TEXT,overNumber TEXT NOT NULL ,matchId TEXT,extraRunsInOver TEXT,isMaiden TEXT NOT NULL ,runsScoredInOver TEXT,inningId TEXT,localId INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT,bowlerId TEXT);");
                db.execSQL("CREATE TABLE cricket_scorecard (status TEXT NOT NULL, id INTEGER NOT NULL DEFAULT 0,syncStatus INTEGER NOT NULL DEFAULT 0 ,sixes TEXT NOT NULL ,runs TEXT NOT NULL ,balls TEXT NOT NULL ,fours TEXT NOT NULL ,matchId TEXT,strikeRate TEXT NOT NULL ,playOrder TEXT NOT NULL ,inningId TEXT,onStrike TEXT NOT NULL ,batsmanId TEXT,localId INTEGER NOT NULL  PRIMARY KEY  AUTOINCREMENT);");
                db.execSQL("CREATE TABLE user (id INTEGER,passwordRequestHash TEXT,fbId TEXT,activeStatus TEXT NOT NULL,twitterAuthTokenSecret TEXT,friendPrivacy TEXT NOT NULL,dateOfBirth date,fbAuthToken TEXT,deviceType TEXT,emailConfirmationHash TEXT,fbAuthTokenSecret TEXT,email TEXT NOT NULL,deviceToken TEXT,twitterAuthToken TEXT,invite TEXT,deviceId TEXT,password TEXT NOT NULL,tokenExpires TEXT,passwordRequestExpire TEXT,firstName TEXT NOT NULL,twitterId TEXT,lastName TEXT NOT NULL,imagesPrivacy TEXT NOT NULL,passwordRequestedAt TEXT,alias TEXT,token TEXT,lastLogin TEXT,gender TEXT,emailConfirmed TEXT NOT NULL )");
                db.execSQL("CREATE TABLE avatar (id INTEGER,profileBackgroundImage TEXT,description TEXT ,avatarType TEXT,coverImage TEXT,userId TEXT,alias TEXT,createDate TEXT NOT NULL ,sportId TEXT,profilePicture TEXT,name TEXT NOT NULL)");
                db.execSQL("CREATE TABLE team (id INTEGER,teamCreatedDate TEXT NOT NULL ,club TEXT DEFAULT NULL ,longitude TEXT NOT NULL ,location TEXT,owner TEXT,lattitude TEXT NOT NULL ,captain TEXT,isActive INTEGER NOT NULL ,avatar TEXT)");
                db.execSQL("CREATE TABLE tournament (id INTEGER,tournamentOrganizerId TEXT,tournamentStartDate TEXT,sportId TEXT,isDeleted TEXT,winPoint INTEGER NOT NULL ,noOfTeam TEXT NOT NULL ,losePoint TEXT NOT NULL ,profilePicture TEXT DEFAULT NULL ,roundOfPlay TEXT NOT NULL ,type TEXT NOT NULL ,tournamentEndDate TEXT,location TEXT,tournamentStateId TEXT ,userId TEXT NOT NULL ,lastEnrollmentDate TEXT,noOfOvers TEXT NOT NULL ,isActive TEXT NOT NULL,name TEXT NOT NULL ,aboutTournament TEXT NOT NULL ,isPublished TEXT NOT NULL,drawPoint TEXT NOT NULL ,noOfGroup TEXT NOT NULL ,tournamentResultId TEXT )");
                db.execSQL("CREATE TABLE event (id INTEGER,status TEXT,startDate TEXT NOT NULL ,endDate TEXT,description TEXT NOT NULL ,title TEXT NOT NULL ,eventType TEXT DEFAULT NULL ,isDeleted TEXT NOT NULL ,userId TEXT NOT NULL ,team1 TEXT,team2 TEXT,eventImage TEXT,calendarId TEXT,longitude TEXT NOT NULL ,location TEXT,locationUrl TEXT,isActive TEXT NOT NULL ,lattitude TEXT NOT NULL )");
                db.execSQL("CREATE TABLE match_scorer (id INTEGER,readStatus TEXT NOT NULL ,scorerOrder TEXT NOT NULL ,inviteStatus TEXT,scorerId TEXT,matchId TEXT NOT NULL ,inviteSentOn TEXT NOT NULL,team TEXT)");
                db.execSQL("CREATE TABLE matchsfplayername (id INTEGER,avatarName TEXT NOT NULL ,teamId TEXT NOT NULL ,matchId TEXT NOT NULL ,avatarId TEXT NOT NULL )");
                db.execSQL("CREATE TABLE roster (id INTEGER,readStatus TEXT NOT NULL ,requestRespondedAt TEXT DEFAULT NULL, requestSentAt TEXT DEFAULT NULL ,avatar TEXT ,playerOrder TEXT ,team TEXT ,requestStatus TEXT NOT NULL)");
                db.execSQL("CREATE TABLE match_team_roles (id integer,ViceCaptain TEXT,matchId TEXT NOT NULL ,CaptainAvatar TEXT NOT NULL ,teamId TEXT NOT NULL ,WicketKeeperAvatar TEXT)");
                db.execSQL("CREATE TABLE matches (id INTEGER NOT NULL,inviteStatus TEXT,matchDate date NOT NULL ,numberOfOvers TEXT ,matchStatus TEXT,tossSelection TEXT,eventId TEXT ,team2CheckAvailibility TEXT NOT NULL ,team1Id TEXT ,matchResultId TEXT ,location TEXT,tie TEXT,matchType TEXT,numberOfInnings TEXT ,readStatus TEXT NOT NULL ,dl TEXT NOT NULL ,description TEXT ,activeScorerId TEXT ,isTeam2ScoringOnSf TEXT NOT NULL ,tossResultId TEXT ,calendarId TEXT ,leagueId TEXT ,isTeam1ScoringOnSf TEXT NOT NULL ,team1CheckAvailibility TEXT NOT NULL ,points TEXT,numberOfPlayers TEXT ,tournamentId TEXT ,team2Id TEXT )");
                db.execSQL("CREATE TABLE general_profile ( id INTEGER, profileBackgroundImage TEXT, about text DEFAULT NULL , profilePicture TEXT, phoneNumberVisiblity TEXT, hometown TEXT, coverImage TEXT, longitude TEXT NOT NULL  , createDate date NOT NULL , phoneNumber TEXT, user TEXT, currentLocation TEXT, lattitude TEXT NOT NULL)");
                db.execSQL("CREATE TABLE cricket_selected_team_players ( id integer, readStatus TEXT NOT NULL , invitationSendOn TEXT DEFAULT NULL , inviteStatus TEXT, matchId TEXT NOT NULL , isInPlayingBench TEXT NOT NULL , teamId TEXT NOT NULL , role TEXT, isInPlayingSquad TEXT NOT NULL , position TEXT, avatarId TEXT NOT NULL , invitationAnsweredOn TEXT)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            onCreate(db);
        }
    }

    public SportzDatabase open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }

    public void cleanDataBase(boolean resetCurrentLocalMatch) {
        List<String> tables = new ArrayList<>();
        tables.add("user");
        tables.add("avatar");
        tables.add("team");
        tables.add("match_scorer");
        tables.add("tournament");
        tables.add("event");
        tables.add("matchsfplayername");
        tables.add("roster");
        tables.add("match_team_roles");
        tables.add("general_profile");
        tables.add("matches");
        tables.add("cricket_selected_team_players");
        // TODO: 2/20/2018 Do not add these tables in list(just uncomment the if block), adding these tables will clear offline data for local match....(i tried but on uncommenting the code dialog box for selecting bowler and batsmen doesn't hide )
       // if(resetCurrentLocalMatch) {
            tables.add("cricket_scorecard");
            tables.add("cricket_overs");
            tables.add("cricket_innings");
            tables.add("cricket_balls");
            tables.add("cricket_balls_local");
            tables.add("Toss_local");
        //}
        for (String tableName : tables) {
            db.execSQL("Delete from " + tableName);
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='" + tableName + "';");
        }
    }

    public int insertBallData(CricketBall cricketBall) {
        ContentValues cv = new ContentValues();
        cv.put("ballCountInOver", cricketBall.getBallCountInOver());
        cv.put("inningOverCount", cricketBall.getInningOverCount());
        cv.put("runScored", cricketBall.getRunScored());
        cv.put("extraRuns", cricketBall.getExtraRuns());

        cv.put("isFour", cricketBall.isFour());
        cv.put("isSix", cricketBall.isSix());
        cv.put("runScoredOnNoBall", cricketBall.getRunScoredOnNoBall());
        cv.put("isNoBall", cricketBall.isNoBall());

        cv.put("isWideBall", cricketBall.isWideBall());
        cv.put("runScoredOnWideball", cricketBall.getRunScoredOnWideball());
        cv.put("isBye", cricketBall.isBye());
        cv.put("runScoredOnBye", cricketBall.getRunScoredOnBye());

        cv.put("isLegBye", cricketBall.isLegBye());
        cv.put("runScoredOnLegBye", cricketBall.getRunScoredOnLegBye());
        cv.put("isWicket", cricketBall.isWicket());
        cv.put("wicketType", cricketBall.getWicketType());

        cv.put("comments", cricketBall.getComments());
        cv.put("batsmanId", cricketBall.getBatsmanId());
        cv.put("bowlerId", cricketBall.getBowlerId());
        cv.put("inningId", cricketBall.getInningId());

        cv.put("overId", cricketBall.getOverId());
        cv.put("matchId", cricketBall.getMatchId());
        cv.put("id", cricketBall.getId());
        if (cricketBall.getCaughtById() != null && !cricketBall.getCaughtById().isEmpty() && !cricketBall.getCaughtById().equalsIgnoreCase("0")) {
            cv.put("caughtById", cricketBall.getCaughtById());
        }
        if (cricketBall.getRunOutById() != null && !cricketBall.getRunOutById().isEmpty() && !cricketBall.getRunOutById().equalsIgnoreCase("0")) {
            cv.put("runOutById", cricketBall.getRunOutById());
        }
        if (cricketBall.getStumpedById() != null && !cricketBall.getStumpedById().isEmpty() && !cricketBall.getStumpedById().equalsIgnoreCase("0")) {
            cv.put("stumpedById", cricketBall.getStumpedById());
        }
        if (cricketBall.getOutBatsmanId() != null && !cricketBall.getOutBatsmanId().isEmpty() && !cricketBall.getOutBatsmanId().equalsIgnoreCase("0")) {
            cv.put("outBatsmanId", cricketBall.getOutBatsmanId());
        }
        if (cricketBall.getId() > 0) {
            cv.put("syncStatus", "1");
        }
        db.insert("cricket_balls", null, cv);
        String query = "SELECT ROWID from cricket_balls order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }

        if (cricketBall.getId() <= 0) {
            cricketBall.setId(lastId);
            updateBallID(cricketBall);
        }

        cricketBall.setId(lastId);


        return lastId;
    }

    public int insertBallDataLocal(String jsonData,int cricketBallId)
    {
        ContentValues cv = new ContentValues();
        cv.put("jsonData", jsonData);
        cv.put("cricket_balls_id", cricketBallId);

        db.insert("cricket_balls_local", null, cv);
        String query = "SELECT ROWID from cricket_balls_local order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertTossDataLocal(String jsonData,int inningId)
    {
        ContentValues cv = new ContentValues();
        cv.put("jsonData", jsonData);
        cv.put("cricket_inning_id", inningId);

        db.insert("Toss_local", null, cv);
        String query = "SELECT ROWID from Toss_local order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertInningData(CricketInning cricketInning) {
        ContentValues cv = new ContentValues();
        cv.put("totalOvers", cricketInning.getTotalOvers());
        cv.put("wickets", cricketInning.getWickets());
        cv.put("isDeclared", cricketInning.isDeclared());
        cv.put("bowlingTeamId", cricketInning.getBowlingTeamId());
        cv.put("isScoredOnSF", cricketInning.isScoredOnSF());
        cv.put("matchId", cricketInning.getMatchId());
        cv.put("inningNumber", cricketInning.getInningNumber());
        cv.put("playing", cricketInning.isPlaying());
        cv.put("daySession", cricketInning.getDaySession());
        cv.put("totalRunsScored", cricketInning.getTotalRunsScored());
        cv.put("state", cricketInning.getState());
        cv.put("extras", cricketInning.getExtras());
        cv.put("playedOvers", cricketInning.getPlayedOvers());
        cv.put("battingTeamId", cricketInning.getBattingTeamId());
        cv.put("day", cricketInning.getDay());
        cv.put("id", cricketInning.getId());
        if (cricketInning.getId() > 0) {
            cv.put("syncStatus", "1");
        }
        db.insert("cricket_innings", null, cv);
        String query = "SELECT ROWID from cricket_innings order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }

        if (cricketInning.getId() <= 0) {
            cricketInning.setId(lastId);
            updateInningID(cricketInning);
        }
        cricketInning.setId(lastId);
        return lastId;
    }

    public int insertOverData(CricketOver cricketOver) {
        ContentValues cv = new ContentValues();
        cv.put("current", cricketOver.getCurrent());
        cv.put("wicketsInOver", cricketOver.getWicketsInOver());
        cv.put("overNumber", cricketOver.getOverNumber());
        cv.put("matchId", cricketOver.getMatchId());
        cv.put("extraRunsInOver", cricketOver.getExtraRunsInOver());
        cv.put("isMaiden", cricketOver.getIsMaiden());
        cv.put("runsScoredInOver", cricketOver.getRunsScoredInOver());
        cv.put("inningId", cricketOver.getInningId());
        cv.put("bowlerId", cricketOver.getBowlerId());
        cv.put("id", cricketOver.getId());
        if (cricketOver.getId() > 0) {

            cv.put("syncStatus", "1");
        }
        db.insert("cricket_overs", null, cv);
        String query = "SELECT ROWID from cricket_overs order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }

        if (cricketOver.getId() <= 0) {
            cricketOver.setId(lastId);
            updateOverID(cricketOver);
        }
        cricketOver.setId(lastId);

        return lastId;
    }

    public int insertScoreCardData(CricketScoreCard cricketScoreCard) {
        ContentValues cv = new ContentValues();
        cv.put("status", cricketScoreCard.getStatus());
        cv.put("sixes", cricketScoreCard.getSixes());
        cv.put("runs", cricketScoreCard.getRuns());
        cv.put("balls", cricketScoreCard.getBalls());
        cv.put("fours", cricketScoreCard.getFours());
        cv.put("matchId", cricketScoreCard.getMatchId());
        cv.put("strikeRate", cricketScoreCard.getStrikeRate());
        cv.put("playOrder", cricketScoreCard.getPlayOrder());
        cv.put("inningId", cricketScoreCard.getInningId());
        cv.put("onStrike", cricketScoreCard.isOnStrike());
        cv.put("batsmanId", cricketScoreCard.getBatsmanId());
        cv.put("id", cricketScoreCard.getId());
        if (cricketScoreCard.getId() > 0) {
            cv.put("syncStatus", "1");
        }
        db.insert("cricket_scorecard", null, cv);
        String query = "SELECT ROWID from cricket_scorecard order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }

        if (cricketScoreCard.getId() <= 0) {
            cricketScoreCard.setId(lastId);
            updateScoreCardID(cricketScoreCard);
        }
        cricketScoreCard.setId(lastId);
        return lastId;
    }

    public int insertEventData(Event event) {
        ContentValues cv = new ContentValues();
        cv.put("id", event.getId());
        cv.put("status", event.getStatus());
        cv.put("startDate", event.getStartDate());
        cv.put("endDate", event.getEndDate());
        cv.put("description", event.getDescription());
        cv.put("title", event.getTitle());
        cv.put("eventType", event.getEventType());
        cv.put("isDeleted", event.getIsDeleted());
        cv.put("userId", event.getUserId());
        cv.put("team1", event.getTeam1());
        cv.put("team2", event.getTeam2());
        cv.put("eventImage", event.getEventImage());
        cv.put("calendarId", event.getCalendarId());
        cv.put("longitude", event.getLongitude());
        cv.put("location", event.getLocation());
        cv.put("locationUrl", event.getLocationUrl());
        cv.put("isActive", event.getIsActive());
        cv.put("lattitude", event.getLattitude());

        db.insert("event", null, cv);
        String query = "SELECT ROWID from event order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertMatchData(Matches matches) {

        ContentValues cv = new ContentValues();
        cv.put("id", matches.getId());
        cv.put("inviteStatus", matches.getInviteStatus());
        cv.put("matchDate", matches.getMatchDate());
        cv.put("numberOfOvers", matches.getNumberOfOvers());
        cv.put("matchStatus", matches.getMatchStatus());
        cv.put("tossSelection", matches.getTossSelection());
        cv.put("eventId", matches.getEventId());
        cv.put("team2CheckAvailibility", matches.getTeam2CheckAvailibility());
        cv.put("team1Id", matches.getTeam1Id());
        cv.put("matchResultId", matches.getMatchResultId());
        cv.put("location", matches.getLocation());
        cv.put("tie", matches.getTie());
        cv.put("matchType", matches.getMatchType());
        cv.put("numberOfInnings", matches.getNumberOfInnings());
        cv.put("readStatus", matches.getReadStatus());
        cv.put("dl", matches.getDl());
        cv.put("description", matches.getDescription());
        cv.put("activeScorerId", matches.getActiveScorerId());
        cv.put("isTeam2ScoringOnSf", matches.getIsTeam2ScoringOnSf());
        cv.put("tossResultId", matches.getTossResultId());
        cv.put("calendarId", matches.getCalendarId());
        cv.put("leagueId", matches.getLeagueId());
        cv.put("isTeam1ScoringOnSf", matches.getIsTeam1ScoringOnSf());
        cv.put("team1CheckAvailibility", matches.getTeam1CheckAvailibility());
        cv.put("points", matches.getPoints());
        cv.put("numberOfPlayers", matches.getNumberOfPlayers());
        cv.put("tournamentId", matches.getTournamentId());
        cv.put("team2Id", matches.getTeam2Id());

        db.insert("matches", null, cv);
        String query = "SELECT ROWID from matches order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertAvatar(Avatar avatar) {

        ContentValues cv = new ContentValues();
        cv.put("id", avatar.getId());
        cv.put("name", avatar.getName());
        cv.put("profileBackgroundImage", avatar.getProfileBackgroundImage());
        cv.put("description", avatar.getDescription());
        cv.put("avatarType", avatar.getAvatarType());
        cv.put("coverImage", avatar.getCoverImage());
        cv.put("userId", avatar.getUserId());
        cv.put("alias", avatar.getAlias());
        cv.put("createDate", avatar.getCreateDate());
        cv.put("sportId", avatar.getSportId());
        cv.put("profilePicture", avatar.getProfilePicture());


        db.insert("avatar", null, cv);
        String query = "SELECT ROWID from avatar order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertGeneralProfileData(GeneralProfile generalProfile) {
        ContentValues cv = new ContentValues();
        cv.put("id", generalProfile.getId());
        cv.put("user", generalProfile.getUser());
        cv.put("about", generalProfile.getAbout());
        cv.put("hometown", generalProfile.getHometown());
        cv.put("currentLocation", generalProfile.getCurrentLocation());
        cv.put("lattitude", generalProfile.getLattitude());
        cv.put("longitude", generalProfile.getLongitude());
        cv.put("phoneNumber", generalProfile.getPhoneNumber());
        cv.put("phoneNumberVisiblity", generalProfile.getPhoneNumberVisiblity());
        cv.put("profilePicture", generalProfile.getProfilePicture());
        cv.put("profileBackgroundImage", generalProfile.getProfileBackgroundImage());
        cv.put("coverImage", generalProfile.getCoverImage());
        cv.put("createDate", generalProfile.getCreateDate());
        db.insert("general_profile", null, cv);
        String query = "SELECT ROWID from general_profile order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertCricketSelectedTeamPlayer(CricketSelectedTeamPlayers cricketselectedteamplayers) {

        ContentValues cv = new ContentValues();
        cv.put("id", cricketselectedteamplayers.getId());
        cv.put("readStatus", cricketselectedteamplayers.getReadStatus());
        cv.put("invitationSendOn", cricketselectedteamplayers.getInvitationSendOn());
        cv.put("inviteStatus", cricketselectedteamplayers.getInviteStatus());
        cv.put("matchId", cricketselectedteamplayers.getMatchId());
        cv.put("isInPlayingBench", cricketselectedteamplayers.getIsInPlayingBench());
        cv.put("teamId", cricketselectedteamplayers.getTeamId());
        cv.put("role", cricketselectedteamplayers.getRole());
        cv.put("isInPlayingSquad", cricketselectedteamplayers.getIsInPlayingSquad());
        cv.put("position", cricketselectedteamplayers.getPosition());
        cv.put("avatarId", cricketselectedteamplayers.getAvatarId());
        cv.put("invitationAnsweredOn", cricketselectedteamplayers.getInvitationAnsweredOn());


        db.insert("cricket_selected_team_players", null, cv);
        String query = "SELECT ROWID from cricket_selected_team_players order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertMatchScorer(MatchScorer matchScorer) {

        ContentValues cv = new ContentValues();
        cv.put("id", matchScorer.getId());
        cv.put("readStatus", matchScorer.getReadStatus());
        cv.put("scorerOrder", matchScorer.getScorerOrder());
        cv.put("inviteStatus", matchScorer.getInviteStatus());
        cv.put("scorerId", matchScorer.getScorerId());
        cv.put("matchId", matchScorer.getMatchId());
        cv.put("inviteSentOn", matchScorer.getInviteSentOn());
        cv.put("team", matchScorer.getTeam());


        db.insert("match_scorer", null, cv);
        String query = "SELECT ROWID from match_scorer order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertMatchSfPlayerName(MatchSfPlayerName matchsfplayername) {

        ContentValues cv = new ContentValues();
        cv.put("id", matchsfplayername.getId());
        cv.put("avatarName", matchsfplayername.getAvatarName());
        cv.put("teamId", matchsfplayername.getTeamId());
        cv.put("matchId", matchsfplayername.getMatchId());
        cv.put("avatarId", matchsfplayername.getAvatarId());

        db.insert("matchsfplayername", null, cv);
        String query = "SELECT ROWID from matchsfplayername order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertMatchTeamRoles(MatchTeamRoles matchTeamRoles) {

        ContentValues cv = new ContentValues();
        cv.put("id", matchTeamRoles.getId());
        cv.put("ViceCaptain", matchTeamRoles.getViceCaptain());
        cv.put("matchId", matchTeamRoles.getMatchId());
        cv.put("CaptainAvatar", matchTeamRoles.getCaptainAvatar());
        cv.put("teamId", matchTeamRoles.getTeamId());
        cv.put("WicketKeeperAvatar", matchTeamRoles.getWicketKeeperAvatar());
        db.insert("match_team_roles", null, cv);
        String query = "SELECT ROWID from match_team_roles order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertRoster(Roster roster) {

        ContentValues cv = new ContentValues();
        cv.put("id", roster.getId());
        cv.put("readStatus", roster.getReadStatus());
        cv.put("requestStatus", roster.getRequestStatus());
        cv.put("requestRespondedAt", roster.getRequestRespondedAt());
        cv.put("requestSentAt", roster.getRequestSentAt());
        cv.put("avatar", roster.getAvatar());
        cv.put("playerOrder", roster.getPlayerOrder());
        cv.put("team", roster.getTeam());


        db.insert("roster", null, cv);
        String query = "SELECT ROWID from roster order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertTeam(com.app.sportzfever.models.dbmodels.Team team) {

        ContentValues cv = new ContentValues();
        cv.put("id", team.getId());
        cv.put("teamCreatedDate", team.getTeamCreatedDate());
        cv.put("club", team.getClub());
        cv.put("longitude", team.getLongitude());
        cv.put("location", team.getLocation());
        cv.put("owner", team.getOwner());
        cv.put("avatar", team.getAvatar());
        cv.put("lattitude", team.getLattitude());
        cv.put("captain", team.getCaptain());
        cv.put("isActive", team.getIsActive());


        db.insert("team", null, cv);
        String query = "SELECT ROWID from team order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public int insertUser(User user) {

        ContentValues cv = new ContentValues();
        cv.put("id", user.getId());
        if (user.getPasswordRequestHash() != null) {
            cv.put("passwordRequestHash", user.getPasswordRequestHash());
        }
        if (user.getFbId() != null) {
            cv.put("fbId", user.getFbId());
        }
        if (user.getActiveStatus() != null) {
            cv.put("activeStatus", user.getActiveStatus());
        }
        if (user.getTwitterAuthTokenSecret() != null) {
            cv.put("twitterAuthTokenSecret", user.getTwitterAuthTokenSecret());
        }
        if (user.getFriendPrivacy() != null) {
            cv.put("friendPrivacy", user.getFriendPrivacy());
        }
        if (user.getDateOfBirth() != null) {
            cv.put("dateOfBirth", user.getDateOfBirth());
        }
        if (user.getFbAuthToken() != null) {
            cv.put("fbAuthToken", user.getFbAuthToken());
        }
        if (user.getDeviceType() != null) {
            cv.put("deviceType", user.getDeviceType());
        }
        if (user.getEmailConfirmationHash() != null) {
            cv.put("emailConfirmationHash", user.getEmailConfirmationHash());
        }
        if (user.getFbAuthTokenSecret() != null) {
            cv.put("fbAuthTokenSecret", user.getFbAuthTokenSecret());
        }
        if (user.getEmail() != null) {
            cv.put("email", user.getEmail());
        }
        if (user.getDeviceToken() != null) {
            cv.put("deviceToken", user.getDeviceToken());
        }
        if (user.getTwitterAuthToken() != null) {
            cv.put("twitterAuthToken", user.getTwitterAuthToken());
        }
        if (user.getInvite() != null) {
            cv.put("invite", user.getInvite());
        }
        if (user.getDeviceId() != null) {
            cv.put("deviceId", user.getDeviceId());
        }
        if (user.getPassword() != null) {
            cv.put("password", user.getPassword());
        }
        if (user.getTokenExpires() != null) {
            cv.put("tokenExpires", user.getTokenExpires());
        }
        if (user.getPasswordRequestExpire() != null) {
            cv.put("passwordRequestExpire", user.getPasswordRequestExpire());
        }
        if (user.getFirstName() != null) {
            cv.put("firstName", user.getFirstName());
        }
        if (user.getTwitterId() != null) {
            cv.put("twitterId", user.getTwitterId());
        }
        if (user.getLastName() != null) {
            cv.put("lastName", user.getLastName());
        }
        if (user.getImagesPrivacy() != null) {
            cv.put("imagesPrivacy", user.getImagesPrivacy());
        }
        if (user.getPasswordRequestedAt() != null) {
            cv.put("passwordRequestedAt", user.getPasswordRequestedAt());
        }
        if (user.getAlias() != null) {
            cv.put("alias", user.getAlias());
        }
        if (user.getToken() != null) {
            cv.put("token", user.getToken());
        }
        if (user.getLastLogin() != null) {
            cv.put("lastLogin", user.getLastLogin());
        }
        if (user.getGender() != null) {
            cv.put("gender", user.getGender());
        }
        if (user.getEmailConfirmed() != null) {
            cv.put("emailConfirmed", user.getEmailConfirmed());
        }


        db.insert("user", null, cv);
        String query = "SELECT ROWID from user order by ROWID DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        int lastId = 0;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }

    public CricketBall fetchLatestBallOfInning(int matchId1, int inningId1) {
        CricketBall cricketBall = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_balls where matchId= '" + matchId1 + "' and inningId= '" + inningId1 + "' order by id desc", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int ballId = cursor.getInt(cursor.getColumnIndex("id"));
                String inningOverCount = cursor.getString(cursor.getColumnIndex("inningOverCount"));
                String ballCountInOver = cursor.getString(cursor.getColumnIndex("ballCountInOver"));
                String runScored = cursor.getString(cursor.getColumnIndex("runScored"));
                String extraRuns = cursor.getString(cursor.getColumnIndex("extraRuns"));
                String isFour = cursor.getString(cursor.getColumnIndex("isFour"));

                String isSix = cursor.getString(cursor.getColumnIndex("isSix"));
                String runScoredOnNoBall = cursor.getString(cursor.getColumnIndex("runScoredOnNoBall"));
                String isNoBall = cursor.getString(cursor.getColumnIndex("isNoBall"));
                String isWideBall = cursor.getString(cursor.getColumnIndex("isWideBall"));
                String runScoredOnWideball = cursor.getString(cursor.getColumnIndex("runScoredOnWideball"));

                String isBye = cursor.getString(cursor.getColumnIndex("isBye"));
                String runScoredOnBye = cursor.getString(cursor.getColumnIndex("runScoredOnBye"));
                String isLegBye = cursor.getString(cursor.getColumnIndex("isLegBye"));
                String runScoredOnLegBye = cursor.getString(cursor.getColumnIndex("runScoredOnLegBye"));
                String isWicket = cursor.getString(cursor.getColumnIndex("isWicket"));

                String wicketType = cursor.getString(cursor.getColumnIndex("wicketType"));
                String comments = cursor.getString(cursor.getColumnIndex("comments"));
                String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));

                String overId = cursor.getString(cursor.getColumnIndex("overId"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String caughtById = cursor.getString(cursor.getColumnIndex("caughtById"));
                String runOutById = cursor.getString(cursor.getColumnIndex("runOutById"));
                String stumpedById = cursor.getString(cursor.getColumnIndex("stumpedById"));
                String outBatsmanId = cursor.getString(cursor.getColumnIndex("outBatsmanId"));

                cricketBall = new CricketBall(ballCountInOver, inningOverCount, runScored, extraRuns, isFour, isSix, runScoredOnNoBall, isNoBall, isWideBall, runScoredOnWideball, isBye, runScoredOnBye,
                        isLegBye, runScoredOnLegBye, isWicket, wicketType, comments, batsmanId, bowlerId, inningId, overId, matchId, caughtById, runOutById, stumpedById, outBatsmanId);
                cricketBall.setId(ballId);

            }
        } catch (Exception e) {
            cricketBall = null;
            e.printStackTrace();
        }
        return cricketBall;
    }

    public CricketBall fetchBallById(int id) {
        CricketBall cricketBall = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_balls where id = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int ballId = cursor.getInt(cursor.getColumnIndex("id"));
                String inningOverCount = cursor.getString(cursor.getColumnIndex("inningOverCount"));
                String ballCountInOver = cursor.getString(cursor.getColumnIndex("ballCountInOver"));
                String runScored = cursor.getString(cursor.getColumnIndex("runScored"));
                String extraRuns = cursor.getString(cursor.getColumnIndex("extraRuns"));
                String isFour = cursor.getString(cursor.getColumnIndex("isFour"));

                String isSix = cursor.getString(cursor.getColumnIndex("isSix"));
                String runScoredOnNoBall = cursor.getString(cursor.getColumnIndex("runScoredOnNoBall"));
                String isNoBall = cursor.getString(cursor.getColumnIndex("isNoBall"));
                String isWideBall = cursor.getString(cursor.getColumnIndex("isWideBall"));
                String runScoredOnWideball = cursor.getString(cursor.getColumnIndex("runScoredOnWideball"));

                String isBye = cursor.getString(cursor.getColumnIndex("isBye"));
                String runScoredOnBye = cursor.getString(cursor.getColumnIndex("runScoredOnBye"));
                String isLegBye = cursor.getString(cursor.getColumnIndex("isLegBye"));
                String runScoredOnLegBye = cursor.getString(cursor.getColumnIndex("runScoredOnLegBye"));
                String isWicket = cursor.getString(cursor.getColumnIndex("isWicket"));

                String wicketType = cursor.getString(cursor.getColumnIndex("wicketType"));
                String comments = cursor.getString(cursor.getColumnIndex("comments"));
                String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));

                String overId = cursor.getString(cursor.getColumnIndex("overId"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String caughtById = cursor.getString(cursor.getColumnIndex("caughtById"));
                String runOutById = cursor.getString(cursor.getColumnIndex("runOutById"));
                String stumpedById = cursor.getString(cursor.getColumnIndex("stumpedById"));
                String outBatsmanId = cursor.getString(cursor.getColumnIndex("outBatsmanId"));

                cricketBall = new CricketBall(ballCountInOver, inningOverCount, runScored, extraRuns, isFour, isSix, runScoredOnNoBall, isNoBall, isWideBall, runScoredOnWideball, isBye, runScoredOnBye,
                        isLegBye, runScoredOnLegBye, isWicket, wicketType, comments, batsmanId, bowlerId, inningId, overId, matchId, caughtById, runOutById, stumpedById, outBatsmanId);
                cricketBall.setId(ballId);

            }
        } catch (Exception e) {
            cricketBall = null;
            e.printStackTrace();
        }
        return cricketBall;
    }

    public List<CricketBallJson> fetchBallDataJson() {
        List<CricketBallJson> cricketBall = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_balls_local", null);
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        CricketBallJson cricketBallJson= new CricketBallJson();

                        cricketBallJson.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                        cricketBallJson.setServerId(cursor.getInt(cursor.getColumnIndex("serverID")));
                        cricketBallJson.setJsonData(cursor.getString(cursor.getColumnIndex("jsonData")));

                        cricketBall.add(cricketBallJson);
                        cursor.moveToNext();
                    }
                }
                //cursor.moveToFirst();
            }
        } catch (Exception e) {
            cricketBall = new ArrayList<>();
            e.printStackTrace();
            Log.d("l",e.getMessage());
        }
        return cricketBall;
    }
    public List<TossJson> fetchTossDataJson() {
        List<TossJson> tossJsons = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from Toss_local", null);
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        TossJson tossJson= new TossJson();

                        tossJson.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                        tossJson.setServerinningId(cursor.getInt(cursor.getColumnIndex("serverinningId")));
                        tossJson.setCricket_inning_id(cursor.getInt(cursor.getColumnIndex("cricket_inning_id")));
                        tossJson.setJsonData(cursor.getString(cursor.getColumnIndex("jsonData")));

                        tossJsons.add(tossJson);
                        cursor.moveToNext();
                    }
                }
                //cursor.moveToFirst();
            }
        } catch (Exception e) {
            tossJsons = new ArrayList<>();
            e.printStackTrace();
            Log.d("l",e.getMessage());
        }
        return tossJsons;
    }

    public Match fetchMatchByMatchId(int id) {
        Match match = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * from matches  where id=" + id + "", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                match = new Match();
                int matchId = cursor.getInt(cursor.getColumnIndex("id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String matchDate = cursor.getString(cursor.getColumnIndex("matchDate"));

                String tossResultId = cursor.getString(cursor.getColumnIndex("tossResultId"));
                String tie = cursor.getString(cursor.getColumnIndex("tie"));
                String tossSelection = cursor.getString(cursor.getColumnIndex("tossSelection"));
                String matchType = cursor.getString(cursor.getColumnIndex("matchType"));
                String matchStatus = cursor.getString(cursor.getColumnIndex("matchStatus"));
                String numberOfInnings = cursor.getString(cursor.getColumnIndex("numberOfInnings"));
                String inviteStatus = cursor.getString(cursor.getColumnIndex("inviteStatus"));
                String team1Id = cursor.getString(cursor.getColumnIndex("team1Id"));

                String team2Id = cursor.getString(cursor.getColumnIndex("team2Id"));

                String tournamentId = cursor.getString(cursor.getColumnIndex("tournamentId"));
                String matchResultId = cursor.getString(cursor.getColumnIndex("matchResultId"));
                String eventId = cursor.getString(cursor.getColumnIndex("eventId"));
                String activeScorerId = cursor.getString(cursor.getColumnIndex("activeScorerId"));
                String numberOfPlayers = cursor.getString(cursor.getColumnIndex("numberOfPlayers"));
                String numberOfOvers = cursor.getString(cursor.getColumnIndex("numberOfOvers"));
                String isTeam1ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam1ScoringOnSf"));
                String isTeam2ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam2ScoringOnSf"));

                match.setId(matchId);
                match.setDescription(description);
                match.setLocation(location);
                match.setMatchDate(matchDate);
                match.setTie(tie);
                match.setTossSelection(tossSelection);
                match.setMatchType(matchType);
                match.setMatchStatus((matchStatus == null) ? "" : matchStatus);
                match.setNumberOfInnings(numberOfInnings);
                match.setInviteStatus((inviteStatus == null) ? "" : inviteStatus);
                match.setTeam1Id((team1Id == null) ? "0" : team1Id);

                match.setTeam2Id((team2Id == null) ? "0" : team2Id);

                match.setTournamentId((tournamentId == null) ? "0" : tournamentId);
                match.setMatchResultId((matchResultId == null) ? "0" : matchResultId);
                match.setEventId(eventId);
                match.setActiveScorerId((activeScorerId == null) ? "0" : activeScorerId);
                match.setNumberOfPlayers(numberOfPlayers);
                match.setNumberOfOvers(numberOfOvers);
                match.setIsTeam1ScoringOnSf(isTeam1ScoringOnSf);
                match.setIsTeam2ScoringOnSf(isTeam2ScoringOnSf);

                match.setTossResultId((tossResultId == null) ? "0" : tossResultId);


                //match.setMatchDate1(matchDate1);

            }
        } catch (Exception e) {
            match = null;
            e.printStackTrace();
        }
        return match;
    }

    public Matches fetchDBMatchByMatchId(int id) {
        Matches match = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * from matches  where id=" + id + "", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int matchId = cursor.getInt(cursor.getColumnIndex("id"));
                String inviteStatus = cursor.getString(cursor.getColumnIndex("inviteStatus"));
                String matchDate = cursor.getString(cursor.getColumnIndex("matchDate"));
                String numberOfOvers = cursor.getString(cursor.getColumnIndex("numberOfOvers"));
                String matchStatus = cursor.getString(cursor.getColumnIndex("matchStatus"));
                String tossSelection = cursor.getString(cursor.getColumnIndex("tossSelection"));
                String eventId = cursor.getString(cursor.getColumnIndex("eventId"));
                String team2CheckAvailibility = cursor.getString(cursor.getColumnIndex("team2CheckAvailibility"));
                String team1Id = cursor.getString(cursor.getColumnIndex("team1Id"));
                String team2Id = cursor.getString(cursor.getColumnIndex("team2Id"));
                String matchResultId = cursor.getString(cursor.getColumnIndex("matchResultId"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String tie = cursor.getString(cursor.getColumnIndex("tie"));
                String matchType = cursor.getString(cursor.getColumnIndex("matchType"));
                String numberOfInnings = cursor.getString(cursor.getColumnIndex("numberOfInnings"));
                String readStatus = cursor.getString(cursor.getColumnIndex("readStatus"));
                String dl = cursor.getString(cursor.getColumnIndex("dl"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String activeScorerId = cursor.getString(cursor.getColumnIndex("activeScorerId"));
                String isTeam2ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam2ScoringOnSf"));

                String tossResultId = cursor.getString(cursor.getColumnIndex("tossResultId"));
                String calendarId = cursor.getString(cursor.getColumnIndex("calendarId"));


                String leagueId = cursor.getString(cursor.getColumnIndex("leagueId"));
                String isTeam1ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam1ScoringOnSf"));
                String team1CheckAvailibility = cursor.getString(cursor.getColumnIndex("team1CheckAvailibility"));

                String points = cursor.getString(cursor.getColumnIndex("points"));
                String numberOfPlayers = cursor.getString(cursor.getColumnIndex("numberOfPlayers"));
                String tournamentId = cursor.getString(cursor.getColumnIndex("tournamentId"));

                match = new Matches(inviteStatus, matchDate, numberOfOvers, matchStatus, tossSelection, eventId, team2CheckAvailibility, team1Id, matchResultId, location, tie, matchType, numberOfInnings, readStatus, dl, description, activeScorerId, isTeam2ScoringOnSf, tossResultId, calendarId, leagueId, isTeam1ScoringOnSf, team1CheckAvailibility, points, numberOfPlayers, tournamentId, team2Id);
                match.setId(matchId);
            }
        } catch (Exception e) {
            match = null;
            e.printStackTrace();
        }
        return match;
    }

    public List<OverBall> fetchOverBallById(int inningId, int matchId, int overId) {
        List<OverBall> cricketBall = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT cb.id, cb.inningOverCount, cb.isWicket, cb.batsmanId, cb.bowlerId, ((select lower(name) from avatar where id=cb.bowlerId)|| ' ('|| (select (lower(firstName)|| ' '|| lower(lastName)) from user where id in (select userId from avatar where id=cb.bowlerId))|| ')'|| ' to '|| ((select lower(name) from avatar where id=cb.batsmanId)|| ' ('|| (select (lower(firstName)|| ' '|| lower(lastName)) from user where id in (select userId from avatar where id=cb.batsmanId))|| ')')) as bowlingString,   CASE WHEN (isFour>0 and runScored=4 and isWicket=0)  THEN 'FOUR runs'  WHEN (isSix>0 and runScored=6 and isWicket=0)  THEN 'SIX runs' WHEN (runScored=0 and isWicket=0)  THEN 'no run' WHEN (runScored=0 and isWicket=1) THEN 'OUT' WHEN (runScored>0 and isWicket=1) THEN 'OUT' WHEN (runScored=1 and isWicket=0) THEN '1 run' ELSE (runScored||' Runs') END as runScored,   CASE WHEN isWicket==1 and isNoBall=0 and isWideBall=0 THEN 'W'  WHEN isWicket=1 and isNoBall=1 and isWideBall=0 THEN 'nb+W'  WHEN isWicket=1 and isNoBall=0 and isWideBall=1 THEN 'wd+W'  WHEN (isWicket=0 and isNoBall=1 and runScored=1) THEN 'nb'  WHEN (isWicket=0 and isNoBall=1 and runScored>1 and isBye=0 and isLegBye=0) THEN ('nb+'|| runScored-1)  WHEN (isWicket=0 and isLegBye=1 and runScored=1 and isNoBall=0) THEN '1lb'  WHEN (isWicket=0 and isNoBall=0 and isLegBye=1 and runScored>1 and isNoBall=0) THEN (runScored|| 'lb')  WHEN (isWicket=0 and isBye=1 and runScored=1) THEN '1b'  WHEN (isWicket=0 and isNoBall=0 and isBye=1 and runScored>1) THEN (runScored|| 'b')  WHEN (isWicket=0 and isNoBall=1 and isBye=1 and runScored>1) THEN ('nb+'|| runScoredOnBye|| 'b')  WHEN (isWicket=0 and isNoBall=1 and isLegBye=1 and runScored>1) THEN ('nb+'|| runScoredOnLegBye|| 'lb')  WHEN (isWicket=0 and isWideBall=1 and runScored=1) THEN 'wd'  WHEN (isWicket=0 and isWideBall=1 and runScored>1) THEN (runScored|| 'wd')  WHEN (isWicket=0 and isWideBall=0 and isBye=0 and isLegBye=0 and isNoBall=0 and runScored=0) THEN 0  WHEN (isWicket=0 and isWideBall=0 and isBye=0 and isLegBye=0 and isNoBall=0 and runScored>0) THEN runScored  ELSE  0  END as overString FROM cricket_balls as cb  LEFT JOIN avatar pba ON cb.batsmanId=pba.id  LEFT JOIN user pbu ON pba.userId=pbu.id  LEFT JOIN avatar bba ON cb.bowlerId=bba.id  LEFT JOIN user bbu ON bba.userId=bbu.id  LEFT JOIN avatar oba ON cb.outBatsmanId=oba.id  LEFT JOIN user obu ON oba.userId=obu.id  LEFT JOIN avatar ctba ON cb.caughtById=ctba.id  LEFT JOIN  user ctbu ON ctba.userId=ctbu.id  LEFT JOIN avatar roba ON cb.runOutById=roba.id  LEFT JOIN user robu ON roba.userId=robu.id  LEFT JOIN avatar stba ON cb.stumpedById=stba.id  LEFT JOIN user stbu ON stba.userId=stbu.id  LEFT JOIN cricket_innings ci ON ci.id=cb.inningId WHERE cb.inningId = '" + inningId + "' AND ci.matchId=  '" + matchId + "' and cb.overId ='" + overId + "' ORDER BY cb.id", null);
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        OverBall overBall = new OverBall();
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String inningOverCount = cursor.getString(cursor.getColumnIndex("inningOverCount"));
                        String isWicket = cursor.getString(cursor.getColumnIndex("isWicket"));
                        String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                        String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId"));
                        String bowlingString = cursor.getString(cursor.getColumnIndex("bowlingString"));
                        String runScored = cursor.getString(cursor.getColumnIndex("runScored"));
                        String overString = cursor.getString(cursor.getColumnIndex("overString"));

                        overBall.setId(id);
                        overBall.setInningOverCount(inningOverCount);
                        overBall.setIsWicket(isWicket);
                        overBall.setBatsmanId(batsmanId);
                        overBall.setBowlerId(bowlerId);
                        overBall.setBowlingString(bowlingString);
                        overBall.setRunScored(runScored);
                        overBall.setOverString(overString);

                        cricketBall.add(overBall);
                        cursor.moveToNext();
                    }
                }
                cursor.moveToFirst();
            }
        } catch (Exception e) {
            cricketBall = new ArrayList<>();
            e.printStackTrace();
        }
        return cricketBall;
    }

    public CricketInning fetchInningById(int id) {
        CricketInning cricketInning = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_innings where id = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int inningId = cursor.getInt(cursor.getColumnIndex("id"));
                String totalOvers = cursor.getString(cursor.getColumnIndex("totalOvers"));
                String wickets = cursor.getString(cursor.getColumnIndex("wickets"));
                String isDeclared = cursor.getString(cursor.getColumnIndex("isDeclared"));
                String bowlingTeamId = cursor.getString(cursor.getColumnIndex("bowlingTeamId"));
                String isScoredOnSF = cursor.getString(cursor.getColumnIndex("isScoredOnSF"));

                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String inningNumber = cursor.getString(cursor.getColumnIndex("inningNumber"));
                String playing = cursor.getString(cursor.getColumnIndex("playing"));
                String daySession = cursor.getString(cursor.getColumnIndex("daySession"));
                String totalRunsScored = cursor.getString(cursor.getColumnIndex("totalRunsScored"));

                String state = cursor.getString(cursor.getColumnIndex("state"));
                String extras = cursor.getString(cursor.getColumnIndex("extras"));
                String playedOvers = cursor.getString(cursor.getColumnIndex("playedOvers"));
                String battingTeamId = cursor.getString(cursor.getColumnIndex("battingTeamId"));
                String day = cursor.getString(cursor.getColumnIndex("day"));

                cricketInning = new CricketInning(totalOvers, wickets, isDeclared, bowlingTeamId, isScoredOnSF, matchId,
                        inningNumber, playing, daySession, totalRunsScored,
                        state, extras, playedOvers, battingTeamId, day);
                cricketInning.setId(inningId);

            }
        } catch (Exception e) {
            cricketInning = null;
            e.printStackTrace();
        }
        return cricketInning;
    }

    public List<Inning> fetchInningsOfMatch(int matchId1) {
        List<Inning> innings = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT c.*,t1.avatar as battingTeamAvatarId,t2.avatar as bowlingTeamAvatarId,lower(a1.name) as battingTeamName," +
                    "lower(a2.name) as bowlingTeamName,a1.profilePicture as battingTeamProfilePic,a2.profilePicture as bowlingTeamIdProfilePic," +
                    " (select max(id) from cricket_overs where inningId = c.id) as currentOverId " +
                    "FROM cricket_innings c JOIN team t1 on t1.id=c.battingTeamId " +
                    "JOIN team t2 on t2.id=c.bowlingTeamId " +
                    "JOIN avatar a1 on a1.id= t1.avatar " +
                    "JOIN avatar a2 on a2.id=t2.avatar " +
                    "JOIN matches m on m.id=c.matchId WHERE m.id='" + matchId1 + "'", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Inning inning = new Inning();

                        int inningId = cursor.getInt(cursor.getColumnIndex("id"));
                        String inningNumber = cursor.getString(cursor.getColumnIndex("inningNumber"));
                        String totalRunsScored = cursor.getString(cursor.getColumnIndex("totalRunsScored"));
                        String isDeclared = cursor.getString(cursor.getColumnIndex("isDeclared"));
                        String extras = cursor.getString(cursor.getColumnIndex("extras"));
                        String playing = cursor.getString(cursor.getColumnIndex("playing"));
                        String day = cursor.getString(cursor.getColumnIndex("day"));
                        String daySession = cursor.getString(cursor.getColumnIndex("daySession"));
                        String totalOvers = cursor.getString(cursor.getColumnIndex("totalOvers"));
                        String playedOvers = cursor.getString(cursor.getColumnIndex("playedOvers"));
                        String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                        String battingTeamId = cursor.getString(cursor.getColumnIndex("battingTeamId"));
                        String bowlingTeamId = cursor.getString(cursor.getColumnIndex("bowlingTeamId"));
                        String state = cursor.getString(cursor.getColumnIndex("state"));
                        String isScoredOnSF = cursor.getString(cursor.getColumnIndex("isScoredOnSF"));
                        String wickets = cursor.getString(cursor.getColumnIndex("wickets"));
                        String battingTeamAvatarId = cursor.getString(cursor.getColumnIndex("battingTeamAvatarId"));
                        String bowlingTeamAvatarId = cursor.getString(cursor.getColumnIndex("bowlingTeamAvatarId"));
                        String battingTeamName = cursor.getString(cursor.getColumnIndex("battingTeamName"));
                        String bowlingTeamName = cursor.getString(cursor.getColumnIndex("bowlingTeamName"));
                        String battingTeamProfilePic = cursor.getString(cursor.getColumnIndex("battingTeamProfilePic"));
                        String bowlingTeamIdProfilePic = cursor.getString(cursor.getColumnIndex("bowlingTeamIdProfilePic"));
                        String currentOverId = cursor.getString(cursor.getColumnIndex("currentOverId"));


                        inning.setId(inningId);
                        inning.setInningNumber(inningNumber);
                        inning.setTotalRunsScored(totalRunsScored);
                        inning.setIsDeclared(isDeclared);
                        inning.setExtras(extras);
                        inning.setPlaying(playing);
                        inning.setDay(day);
                        inning.setDaySession(daySession);
                        inning.setTotalOvers(totalOvers);
                        inning.setPlayedOvers(playedOvers);
                        inning.setMatchId(matchId);
                        inning.setBattingTeamId(battingTeamId);
                        inning.setBowlingTeamId(bowlingTeamId);
                        inning.setState(state);
                        inning.setDrinksBreak(state);
                        inning.setIsScoredOnSF(isScoredOnSF);
                        inning.setWickets(wickets);
                        inning.setBattingTeamAvatarId(battingTeamAvatarId);
                        inning.setBowlingTeamAvatarId(bowlingTeamAvatarId);
                        inning.setBattingTeamName(battingTeamName);
                        inning.setBowlingTeamName(bowlingTeamName);
                        inning.setBattingTeamProfilePic(battingTeamProfilePic);
                        inning.setBowlingTeamIdProfilePic(bowlingTeamIdProfilePic);
                        inning.setCurrentOverId(currentOverId);


                        innings.add(inning);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            innings = new ArrayList<>();
            e.printStackTrace();
        }
        return innings;
    }

    public Inning fetchInningByInningNumber(int inningNumber1, int matchId1) {
        Inning inning = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT c.inningNumber,c.totalRunsScored,c.totalOvers,c.playedOvers,c.matchId from cricket_innings c WHERE  c.matchId= '" + matchId1 + "'AND c.inningNumber='" + inningNumber1 + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int inningId = cursor.getInt(cursor.getColumnIndex("id"));
                String inningNumber = cursor.getString(cursor.getColumnIndex("inningNumber"));
                String totalRunsScored = cursor.getString(cursor.getColumnIndex("totalRunsScored"));
                String isDeclared = cursor.getString(cursor.getColumnIndex("isDeclared"));
                String extras = cursor.getString(cursor.getColumnIndex("extras"));
                String playing = cursor.getString(cursor.getColumnIndex("playing"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                String daySession = cursor.getString(cursor.getColumnIndex("daySession"));
                String totalOvers = cursor.getString(cursor.getColumnIndex("totalOvers"));
                String playedOvers = cursor.getString(cursor.getColumnIndex("playedOvers"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String battingTeamId = cursor.getString(cursor.getColumnIndex("battingTeamId"));
                String bowlingTeamId = cursor.getString(cursor.getColumnIndex("bowlingTeamId"));
                String state = cursor.getString(cursor.getColumnIndex("state"));
                String isScoredOnSF = cursor.getString(cursor.getColumnIndex("isScoredOnSF"));
                String wickets = cursor.getString(cursor.getColumnIndex("wickets"));
                String battingTeamAvatarId = cursor.getString(cursor.getColumnIndex("battingTeamAvatarId"));
                String bowlingTeamAvatarId = cursor.getString(cursor.getColumnIndex("bowlingTeamAvatarId"));
                String battingTeamName = cursor.getString(cursor.getColumnIndex("battingTeamName"));
                String bowlingTeamName = cursor.getString(cursor.getColumnIndex("bowlingTeamName"));
                String battingTeamProfilePic = cursor.getString(cursor.getColumnIndex("battingTeamProfilePic"));
                String bowlingTeamIdProfilePic = cursor.getString(cursor.getColumnIndex("bowlingTeamIdProfilePic"));
                String currentOverId = cursor.getString(cursor.getColumnIndex("currentOverId"));
                String drinksBreak = cursor.getString(cursor.getColumnIndex("drinksBreak"));
                String batsmanOnStrike = cursor.getString(cursor.getColumnIndex("batsmanOnStrike"));
                String batsmanOnNonStrike = cursor.getString(cursor.getColumnIndex("batsmanOnNonStrike"));
                String previousBowlerId = cursor.getString(cursor.getColumnIndex("previousBowlerId"));
                String currentBowlerId = cursor.getString(cursor.getColumnIndex("currentBowlerId"));
                String inningScoreString = cursor.getString(cursor.getColumnIndex("inningScoreString"));
                String wicketFallNumber = cursor.getString(cursor.getColumnIndex("wicketFallNumber"));
                String overRate = cursor.getString(cursor.getColumnIndex("overRate"));

                inning.setId(inningId);
                inning.setInningNumber(inningNumber);
                inning.setTotalRunsScored(totalRunsScored);
                inning.setIsDeclared(isDeclared);
                inning.setExtras(extras);
                inning.setPlaying(playing);
                inning.setDay(day);
                inning.setDaySession(daySession);
                inning.setTotalOvers(totalOvers);
                inning.setPlayedOvers(playedOvers);
                inning.setMatchId(matchId);
                inning.setBattingTeamId(battingTeamId);
                inning.setBowlingTeamId(bowlingTeamId);
                inning.setState(state);
                inning.setIsScoredOnSF(isScoredOnSF);
                inning.setWickets(wickets);
                inning.setBattingTeamAvatarId(battingTeamAvatarId);
                inning.setBowlingTeamAvatarId(bowlingTeamAvatarId);
                inning.setBattingTeamName(battingTeamName);
                inning.setBowlingTeamName(bowlingTeamName);
                inning.setBattingTeamProfilePic(battingTeamProfilePic);
                inning.setBowlingTeamIdProfilePic(bowlingTeamIdProfilePic);
                inning.setCurrentOverId(currentOverId);
                inning.setDrinksBreak(drinksBreak == null ? "0" : drinksBreak);
                inning.setBatsmanOnStrike(batsmanOnStrike);
                inning.setBatsmanOnNonStrike(batsmanOnNonStrike);
                inning.setPreviousBowlerId(previousBowlerId);
                inning.setCurrentBowlerId(currentBowlerId);
                inning.setInningScoreString(inningScoreString);
                inning.setWicketFallNumber(wicketFallNumber);
                inning.setOverRate(overRate);


            }
        } catch (Exception e) {
            inning = null;
            e.printStackTrace();
        }
        return inning;
    }

    public CricketOver fetchOverById(int id) {
        CricketOver cricketOver = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_overs where id = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int overId = cursor.getInt(cursor.getColumnIndex("id"));
                String current = cursor.getString(cursor.getColumnIndex("current"));
                String wicketsInOver = cursor.getString(cursor.getColumnIndex("wicketsInOver"));
                String overNumber = cursor.getString(cursor.getColumnIndex("overNumber"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String extraRunsInOver = cursor.getString(cursor.getColumnIndex("extraRunsInOver"));

                String isMaiden = cursor.getString(cursor.getColumnIndex("isMaiden"));
                String runsScoredInOver = cursor.getString(cursor.getColumnIndex("runsScoredInOver"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId"));

                cricketOver = new CricketOver(current, wicketsInOver, overNumber, matchId, extraRunsInOver, isMaiden, runsScoredInOver, inningId, bowlerId);
                cricketOver.setId(overId);

            }
        } catch (Exception e) {
            cricketOver = null;
            e.printStackTrace();
        }
        return cricketOver;
    }

    public CricketScoreCard fetchScoreCardById(int id) {
        CricketScoreCard cricketScoreCard = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_scorecard where id = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int scorecardId = cursor.getInt(cursor.getColumnIndex("id"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String sixes = cursor.getString(cursor.getColumnIndex("sixes"));
                String runs = cursor.getString(cursor.getColumnIndex("runs"));
                String balls = cursor.getString(cursor.getColumnIndex("balls"));
                String fours = cursor.getString(cursor.getColumnIndex("fours"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String strikeRate = cursor.getString(cursor.getColumnIndex("strikeRate"));
                String playOrder = cursor.getString(cursor.getColumnIndex("playOrder"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                String onStrike = cursor.getString(cursor.getColumnIndex("onStrike"));
                String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                cricketScoreCard = new CricketScoreCard(status, sixes, runs, balls, fours, matchId,
                        strikeRate, playOrder, inningId, onStrike, batsmanId);
                cricketScoreCard.setId(scorecardId);

            }
        } catch (Exception e) {
            cricketScoreCard = null;
            e.printStackTrace();
        }
        return cricketScoreCard;
    }

    public List<CricketScoreCard> fetchNotOutBatsmenScoreCardOFInning(int id) {
        List<CricketScoreCard> cricketScoreCards = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_scorecard where  status = 'NOT OUT' and inningId = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        int scorecardId = cursor.getInt(cursor.getColumnIndex("id"));
                        String status = cursor.getString(cursor.getColumnIndex("status"));
                        String sixes = cursor.getString(cursor.getColumnIndex("sixes"));
                        String runs = cursor.getString(cursor.getColumnIndex("runs"));
                        String balls = cursor.getString(cursor.getColumnIndex("balls"));
                        String fours = cursor.getString(cursor.getColumnIndex("fours"));
                        String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                        String strikeRate = cursor.getString(cursor.getColumnIndex("strikeRate"));
                        String playOrder = cursor.getString(cursor.getColumnIndex("playOrder"));
                        String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                        String onStrike = cursor.getString(cursor.getColumnIndex("onStrike"));
                        String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                        CricketScoreCard cricketScoreCard = new CricketScoreCard(status, sixes, runs, balls, fours, matchId,
                                strikeRate, playOrder, inningId, onStrike, batsmanId);
                        cricketScoreCard.setId(scorecardId);
                        cricketScoreCards.add(cricketScoreCard);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            cricketScoreCards = new ArrayList<>();
            e.printStackTrace();
        }
        return cricketScoreCards;
    }

    public List<CricketScoreCard> fetchBatsmenScoreCardOFInning(int id) {
        List<CricketScoreCard> cricketScoreCards = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_scorecard where inningId = '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        int scorecardId = cursor.getInt(cursor.getColumnIndex("id"));
                        String status = cursor.getString(cursor.getColumnIndex("status"));
                        String sixes = cursor.getString(cursor.getColumnIndex("sixes"));
                        String runs = cursor.getString(cursor.getColumnIndex("runs"));
                        String balls = cursor.getString(cursor.getColumnIndex("balls"));
                        String fours = cursor.getString(cursor.getColumnIndex("fours"));
                        String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                        String strikeRate = cursor.getString(cursor.getColumnIndex("strikeRate"));
                        String playOrder = cursor.getString(cursor.getColumnIndex("playOrder"));
                        String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                        String onStrike = cursor.getString(cursor.getColumnIndex("onStrike"));
                        String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                        CricketScoreCard cricketScoreCard = new CricketScoreCard(status, sixes, runs, balls, fours, matchId,
                                strikeRate, playOrder, inningId, onStrike, batsmanId);
                        cricketScoreCard.setId(scorecardId);
                        cricketScoreCards.add(cricketScoreCard);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            cricketScoreCards = new ArrayList<>();
            e.printStackTrace();
        }
        return cricketScoreCards;
    }

    public Team fetchTeam(int teamId, int userId) {


        Team team = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM team WHERE id= '" + teamId + "'" +
                    "AND ((owner =(SELECT id FROM avatar a WHERE a.userId='" + userId + "' AND a.avatarType='PLAYER')) " +
                    "OR (captain =(SELECT id FROM avatar a WHERE a.userId='" + userId + "'  AND a.avatarType='PLAYER')))", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } catch (Exception e) {
            team = null;
            e.printStackTrace();
        }
        return team;
    }

    public Match fetchMatchByEventId(int id) {
        Match match = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT m.id,m.description,m.location,m.matchDate,m.tie,m.points,m.leagueId," +
                    "m.calendarId,m.tossSelection,m.matchType," +
                    "m.numberOfInnings,m.inviteStatus,m.matchStatus," +
                    "CASE WHEN (m.matchStatus='NOT STARTED')" +
                    "THEN m.team1Id ELSE " +
                    "(select battingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) " +
                    "END as team1Id, (select id from cricket_innings where inningNumber=1 and matchId=m.id) as team1InningId," +
                    " CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN m.team2Id ELSE (select bowlingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) END as team2Id, " +
                    "(select id from cricket_innings where inningNumber=2 and matchId=m.id) as team2InningId, " +
                    "CASE WHEN (m.matchStatus='NOT STARTED') THEN m.isTeam1ScoringOnSf " +
                    "ELSE (case WHEN (m.team1Id=(select battingTeamId from cricket_innings where inningNumber=1 and matchId=m.id)) " +
                    "THEN m.isTeam1ScoringOnSf ELSE (m.isTeam2ScoringOnSf) END ) END as isTeam1ScoringOnSf, " +
                    "CASE WHEN (m.matchStatus='NOT STARTED') THEN m.isTeam2ScoringOnSf " +
                    "ELSE ( case WHEN (m.team1Id=(select bowlingTeamId from cricket_innings where inningNumber=1 and matchId=m.id)) " +
                    "THEN m.isTeam1ScoringOnSf ELSE (m.isTeam2ScoringOnSf) END ) END as isTeam2ScoringOnSf, " +
                    "CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT lower(a.name) FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team1Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT lower(a.name) FROM team t JOIN avatar a ON a.id=t.avatar " +
                    "WHERE t.id in (select battingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) AND a.avatarType='TEAM') " +
                    "END AS team1Name, CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT lower(a.name) FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team2Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT lower(a.name) FROM team t JOIN avatar a ON a.id=t.avatar " +
                    "WHERE t.id in (select bowlingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) AND a.avatarType='TEAM') " +
                    "END AS team2Name, CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT a.id FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team1Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT a.id FROM team t JOIN avatar a ON a.id=t.avatar " +
                    "WHERE t.id in (select battingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) AND a.avatarType='TEAM') " +
                    "END AS team1AvatarId , CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT a.id FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team2Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT a.id FROM team t JOIN avatar a ON a.id=t.avatar " +
                    "WHERE t.id in (select bowlingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) AND a.avatarType='TEAM') " +
                    "END AS team2AvatarId , CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT a.profilePicture FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team1Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT a.profilePicture FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id in (select battingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) " +
                    "AND a.avatarType='TEAM') END AS team1ProfilePic, CASE WHEN (m.matchStatus='NOT STARTED') " +
                    "THEN (SELECT a.profilePicture FROM team t JOIN avatar a ON a.id=t.avatar WHERE t.id=m.team2Id AND a.avatarType='TEAM') " +
                    "ELSE (SELECT a.profilePicture FROM team t JOIN avatar a ON a.id=t.avatar " +
                    "WHERE t.id in (select bowlingTeamId from cricket_innings where inningNumber=1 and matchId=m.id) AND a.avatarType='TEAM') " +
                    "END AS team2ProfilePic, m.tournamentId,m.matchResultId," +
                    "(select name from tournament where id=m.tournamentId) as tournamentName," +
                    "m.tossResultId,m.eventId,m.activeScorerId,m.readStatus,m.numberOfPlayers,m.numberOfOvers," +
                    "(SELECT startDate from event where id=m.eventId) as matchStartDate FROM matches m WHERE m.eventId= '"
                    + id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                match = new Match();
                int matchId = cursor.getInt(cursor.getColumnIndex("id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String matchDate = cursor.getString(cursor.getColumnIndex("matchDate"));

                String tossResultId = cursor.getString(cursor.getColumnIndex("tossResultId"));
                String tie = cursor.getString(cursor.getColumnIndex("tie"));
                String tossSelection = cursor.getString(cursor.getColumnIndex("tossSelection"));
                String matchType = cursor.getString(cursor.getColumnIndex("matchType"));
                String matchStatus = cursor.getString(cursor.getColumnIndex("matchStatus"));
                String numberOfInnings = cursor.getString(cursor.getColumnIndex("numberOfInnings"));
                String inviteStatus = cursor.getString(cursor.getColumnIndex("inviteStatus"));
                String team1Id = cursor.getString(cursor.getColumnIndex("team1Id"));
                String team1InningId = cursor.getString(cursor.getColumnIndex("team1InningId"));
                String team1Name = cursor.getString(cursor.getColumnIndex("team1Name"));
                String team1ProfilePic = cursor.getString(cursor.getColumnIndex("team1ProfilePic"));
                String team2Id = cursor.getString(cursor.getColumnIndex("team2Id"));
                String team2InningId = cursor.getString(cursor.getColumnIndex("team2InningId"));
                String team2Name = cursor.getString(cursor.getColumnIndex("team2Name"));
                String team2ProfilePic = cursor.getString(cursor.getColumnIndex("team2ProfilePic"));
                String tournamentId = cursor.getString(cursor.getColumnIndex("tournamentId"));
                String matchResultId = cursor.getString(cursor.getColumnIndex("matchResultId"));
                String eventId = cursor.getString(cursor.getColumnIndex("eventId"));
                String activeScorerId = cursor.getString(cursor.getColumnIndex("activeScorerId"));
                String numberOfPlayers = cursor.getString(cursor.getColumnIndex("numberOfPlayers"));
                String numberOfOvers = cursor.getString(cursor.getColumnIndex("numberOfOvers"));
                String isTeam1ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam1ScoringOnSf"));
                String isTeam2ScoringOnSf = cursor.getString(cursor.getColumnIndex("isTeam2ScoringOnSf"));
                String team1AvatarId = cursor.getString(cursor.getColumnIndex("team1AvatarId"));
                String team2AvatarId = cursor.getString(cursor.getColumnIndex("team2AvatarId"));
                String tournamentName = cursor.getString(cursor.getColumnIndex("tournamentName"));


                match.setId(matchId);
                match.setDescription(description);
                match.setLocation(location);
                match.setMatchDate(matchDate);
                match.setTie(tie);
                match.setTossSelection(tossSelection);
                match.setMatchType(matchType);
                match.setMatchStatus((matchStatus == null) ? "" : matchStatus);
                match.setNumberOfInnings(numberOfInnings);
                match.setInviteStatus((inviteStatus == null) ? "" : inviteStatus);
                match.setTeam1Id((team1Id == null) ? "0" : team1Id);
                match.setTeam1InningId((team1InningId == null) ? "0" : team1InningId);
                match.setTeam1Name((team1Name == null) ? "" : team1Name);
                match.setTeam1ProfilePic(team1ProfilePic);
                match.setTeam2Id((team2Id == null) ? "0" : team2Id);
                match.setTeam2InningId((team2InningId == null) ? "0" : team2InningId);
                match.setTeam2Name((team2Name == null) ? "" : team2Name);
                match.setTeam2ProfilePic(team2ProfilePic);
                match.setTournamentId((tournamentId == null) ? "0" : tournamentId);
                match.setMatchResultId((matchResultId == null) ? "0" : matchResultId);
                match.setEventId(eventId);
                match.setActiveScorerId((activeScorerId == null) ? "0" : activeScorerId);
                match.setNumberOfPlayers(numberOfPlayers);
                match.setNumberOfOvers(numberOfOvers);
                match.setIsTeam1ScoringOnSf(isTeam1ScoringOnSf);
                match.setIsTeam2ScoringOnSf(isTeam2ScoringOnSf);
                match.setTeam1AvatarId((team1AvatarId == null) ? "0" : team1AvatarId);
                match.setTeam2AvatarId((team2AvatarId == null) ? "0" : team2AvatarId);
                match.setTournamentName(tournamentName);
                match.setTossResultId((tossResultId == null) ? "0" : tossResultId);


                //match.setMatchDate1(matchDate1);

            }
        } catch (Exception e) {
            match = null;
            e.printStackTrace();
        }
        return match;
    }

    public String getGhostPlayerName(int id, int matchId, int teamId) {
        String name = "";
        if (id > 326 && id < 338) {

            Cursor cursor = null;
            try {
                cursor = db.rawQuery("SELECT * from matchsfplayername where avatarId= '" + id + "' and matchId = '" + matchId + "' and teamId = '" + teamId + "'"
                        + id + "'", null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    name = cursor.getString(cursor.getColumnIndex("avatarName"));


                }
            } catch (Exception e) {
                name = "";
                e.printStackTrace();
            }
        }
        return name;
    }

    public List<TeamScorer> fetchTeamScorer(int teamId, int matchId1) {
        List<TeamScorer> matchScorers = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT ms.*, (u.firstName||' '||u.lastName) as scorerName,gp.profilePicture FROM match_scorer ms " +
                    "INNER JOIN user u ON ms.scorerId=u.id INNER JOIN general_profile gp on u.id=gp.user " +
                    "WHERE ms.matchId='" + matchId1 + "' AND ms.team='" + teamId + "' AND ms.inviteStatus='ACCEPTED' order by ms.scorerOrder", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        TeamScorer scorer = new TeamScorer();

                        scorer.setProfilePicture(cursor.getString(cursor.getColumnIndex("profilePicture")));
                        scorer.setScorerId(cursor.getString(cursor.getColumnIndex("scorerId")));
                        scorer.setScorerName(cursor.getString(cursor.getColumnIndex("scorerName")));
                        scorer.setTeam(cursor.getString(cursor.getColumnIndex("team")));

                        matchScorers.add(scorer);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            matchScorers = new ArrayList<>();
            e.printStackTrace();
        }
        return matchScorers;
    }

    public List<TeamSquad> fetchTeamSquad(int teamId, int matchId1) {
        List<TeamSquad> teamSquads = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT cstp.id as playSquadId, cstp.position as playPosition,lower(cstp.role) as playerRole, avatar.id as playerAvatarId," +
                    " avatar.profilePicture,cstp.teamId as playerTeamId, (user.firstName||' '||user.lastName) as name " +
                    "FROM cricket_selected_team_players as cstp INNER JOIN avatar ON cstp.avatarId = avatar.id " +
                    "INNER JOIN user ON user.id = avatar.userId WHERE cstp.matchId = '" + matchId1 + "' AND cstp.inviteStatus = 'ACCEPTED' AND cstp.isInPlayingSquad=1 AND isInPlayingBench=0 AND cstp.teamId = '" + teamId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        TeamSquad squad = new TeamSquad();

                        squad.setProfilePicture(cursor.getString(cursor.getColumnIndex("profilePicture")));
                        squad.setPlayerRole(cursor.getString(cursor.getColumnIndex("playerRole")));
                        squad.setPlayerAvatarId(cursor.getString(cursor.getColumnIndex("playerAvatarId")));
                        squad.setName(cursor.getString(cursor.getColumnIndex("name")));


                        teamSquads.add(squad);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            teamSquads = new ArrayList<>();
            e.printStackTrace();
        }
        return teamSquads;
    }

    public List<BattingStatViewModel> fetchBattingStats(int inningId1) {
        List<BattingStatViewModel> battingStats = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT distinct cs.playOrder,cs.status,cs.onStrike,cs.runs,cs.balls,cs.fours,cs.sixes,cs.strikeRate,cs.matchId,cs.inningId,cs.batsmanId,cb.bowlerId,cb.caughtById,cb.runOutById,cb.stumpedById,  (osbnu.firstName||' '||osbnu.lastName) as batsmanAvatarName,  ('/'||LOWER(osbn.avatarType)||'/Cricket'||'/'||osbn.id) as batsmanAvatarUrl,  (bbu.firstName||' '||bbu.lastName) as bowlerUserFullName, bba.name as bowlerAvatarName, bba.profilePicture as bowlerAvatarPic,  (obu.firstName||' '||obu.lastName) as outBatsmanUserFullName, oba.name as outBatsmanAvatarName, oba.profilePicture as outBatsmanAvatarPic,  (ctbu.firstName||' '||ctbu.lastName) as caughtByUserFullName, ctba.name as caughtByAvatarName, ctba.profilePicture as caughtByAvatarPic,  (robu.firstName||' '||robu.lastName) as runoutByUserFullName, roba.name as runoutByAvatarName, roba.profilePicture as runoutByAvatarPic,  (stbu.firstName||' '||stbu.lastName) as stumpedByUserFullName, stba.name as stumpedByAvatarName,stba.profilePicture as stumpedByAvatarPic  from cricket_scorecard cs LEFT JOIN cricket_balls cb ON cb.batsmanId=cs.batsmanId and cb.inningId ='" + inningId1 + "' LEFT JOIN avatar bba ON cb.bowlerId=bba.id LEFT JOIN user bbu ON bba.userId=bbu.id  LEFT JOIN avatar oba ON cb.outBatsmanId=oba.id LEFT JOIN user obu ON oba.userId=obu.id LEFT JOIN avatar osbn ON cs.batsmanId=osbn.id LEFT JOIN user osbnu ON osbn.userId=osbnu.id LEFT JOIN avatar ctba ON cb.caughtById=ctba.id LEFT JOIN  user ctbu ON ctba.userId=ctbu.id LEFT JOIN avatar roba ON cb.runOutById=roba.id LEFT JOIN user robu ON roba.userId=robu.id LEFT JOIN avatar stba ON cb.stumpedById=stba.id LEFT JOIN user stbu ON stba.userId=stbu.id WHERE cs.inningId = '" + inningId1 + "'", null);
            battingStats = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        BattingStatViewModel battingStat = new BattingStatViewModel();
                        battingStat.setPlayOrder(cursor.getString(cursor.getColumnIndex("playOrder")));
                        battingStat.setBatsmanAvatarName(cursor.getString(cursor.getColumnIndex("batsmanAvatarName")));
                        battingStat.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                        battingStat.setOnStrike(cursor.getString(cursor.getColumnIndex("onStrike")));
                        battingStat.setRuns(cursor.getString(cursor.getColumnIndex("runs")));

                        battingStat.setBalls(cursor.getString(cursor.getColumnIndex("balls")));
                        battingStat.setFours(cursor.getString(cursor.getColumnIndex("fours")));
                        battingStat.setSixes(cursor.getString(cursor.getColumnIndex("sixes")));
                        battingStat.setStrikeRate(cursor.getString(cursor.getColumnIndex("strikeRate")));
                        battingStat.setBatsmanId(cursor.getString(cursor.getColumnIndex("batsmanId")));

                        String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId"));
                        String stumpedById = cursor.getString(cursor.getColumnIndex("stumpedById"));
                        String caughtById = cursor.getString(cursor.getColumnIndex("caughtById"));
                        String runOutById = cursor.getString(cursor.getColumnIndex("runOutById"));

                        if (bowlerId == null || bowlerId.equalsIgnoreCase("")) {
                            battingStat.setBowlerId("0");
                        } else {
                            battingStat.setBowlerId(bowlerId);
                        }

                        if (stumpedById == null || stumpedById.equalsIgnoreCase("")) {
                            battingStat.setStumpedById("0");
                        } else {
                            battingStat.setStumpedById(stumpedById);
                        }

                        if (caughtById == null || caughtById.equalsIgnoreCase("")) {
                            battingStat.setCaughtById("0");
                        } else {
                            battingStat.setCaughtById(caughtById);
                        }

                        if (runOutById == null || runOutById.equalsIgnoreCase("")) {
                            battingStat.setRunOutById("0");
                        } else {
                            battingStat.setRunOutById(runOutById);
                        }


                        battingStat.setBowlerUserFullName(cursor.getString(cursor.getColumnIndex("bowlerUserFullName")));
                        battingStat.setBowlerAvatarName(cursor.getString(cursor.getColumnIndex("bowlerAvatarName")));
                        battingStat.setBowlerAvatarPic(cursor.getString(cursor.getColumnIndex("bowlerAvatarPic")));
                        battingStat.setOutBatsmanUserFullName(cursor.getString(cursor.getColumnIndex("outBatsmanUserFullName")));
                        battingStat.setOutBatsmanAvatarName(cursor.getString(cursor.getColumnIndex("outBatsmanAvatarName")));
                        battingStat.setOutBatsmanAvatarPic(cursor.getString(cursor.getColumnIndex("outBatsmanAvatarPic")));
                        battingStat.setCaughtByUserFullName(cursor.getString(cursor.getColumnIndex("caughtByUserFullName")));
                        battingStat.setCaughtByAvatarName(cursor.getString(cursor.getColumnIndex("caughtByAvatarName")));
                        battingStat.setCaughtByAvatarPic(cursor.getString(cursor.getColumnIndex("caughtByAvatarPic")));
                        battingStat.setRunoutByUserFullName(cursor.getString(cursor.getColumnIndex("runoutByUserFullName")));

                        battingStat.setRunoutByAvatarName(cursor.getString(cursor.getColumnIndex("runoutByAvatarName")));
                        battingStat.setRunoutByAvatarPic(cursor.getString(cursor.getColumnIndex("runoutByAvatarPic")));
                        battingStat.setStumpedByUserFullName(cursor.getString(cursor.getColumnIndex("stumpedByUserFullName")));
                        battingStat.setStumpedByAvatarName(cursor.getString(cursor.getColumnIndex("stumpedByAvatarName")));
                        battingStat.setStumpedByAvatarPic(cursor.getString(cursor.getColumnIndex("stumpedByAvatarPic")));


                        boolean found = false;
                        int p = 0;
                        for (int a = 0; a < battingStats.size(); a++) {
                            if (battingStats.get(a).getBatsmanId().equalsIgnoreCase(battingStat.getBatsmanId())) {
                                battingStats.get(a).setPlayOrder(battingStat.getPlayOrder());
                                battingStats.get(a).setBatsmanAvatarName(battingStat.getBatsmanAvatarName());
                                battingStats.get(a).setStatus(battingStat.getStatus());
                                battingStats.get(a).setOnStrike(battingStat.getOnStrike());
                                battingStats.get(a).setRuns(battingStat.getRuns());
                                battingStats.get(a).setBalls(battingStat.getBalls());
                                battingStats.get(a).setFours(battingStat.getFours());
                                battingStats.get(a).setSixes(battingStat.getSixes());
                                battingStats.get(a).setStrikeRate(battingStat.getStrikeRate());
                                battingStats.get(a).setBatsmanId(battingStat.getBatsmanId());
                                battingStats.get(a).setBowlerUserFullName(battingStat.getBowlerUserFullName());
                                battingStats.get(a).setBowlerAvatarName(battingStat.getBowlerAvatarName());
                                battingStats.get(a).setBowlerAvatarPic(battingStat.getBowlerAvatarPic());
                                battingStats.get(a).setOutBatsmanUserFullName(battingStat.getOutBatsmanUserFullName());
                                battingStats.get(a).setOutBatsmanAvatarName(battingStat.getOutBatsmanAvatarName());
                                battingStats.get(a).setOutBatsmanAvatarPic(battingStat.getOutBatsmanAvatarPic());
                                battingStats.get(a).setCaughtByUserFullName(battingStat.getCaughtByUserFullName());
                                battingStats.get(a).setCaughtByAvatarName(battingStat.getCaughtByAvatarName());
                                battingStats.get(a).setCaughtByAvatarPic(battingStat.getCaughtByAvatarPic());
                                battingStats.get(a).setRunoutByUserFullName(battingStat.getRunoutByUserFullName());
                                battingStats.get(a).setRunoutByAvatarName(battingStat.getRunoutByAvatarName());
                                battingStats.get(a).setRunoutByAvatarPic(battingStat.getRunoutByAvatarPic());
                                battingStats.get(a).setStumpedByUserFullName(battingStat.getStumpedByUserFullName());
                                battingStats.get(a).setStumpedByAvatarName(battingStat.getStumpedByAvatarName());
                                battingStats.get(a).setStumpedByAvatarPic(battingStat.getStumpedByAvatarPic());
                                found = true;
                            }
                        }

                        if (found == false) {

                            battingStats.add(battingStat);
                        }
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            battingStats = new ArrayList<>();
            e.printStackTrace();
        }
        return battingStats;
    }

    public List<TeamSquad> fetchGhostPlayers(int numberOfPlayer, int teamId, int matchId1) {
        List<TeamSquad> teamSquads = new ArrayList<>();
        Cursor cursor = null;
        try {
            int avatarId = 327;
            int userId = 352;
            for (int i = 1; i <= numberOfPlayer; i++) {
                String name = "";
                cursor = db.rawQuery("SELECT * from matchsfplayername where avatarId= '" + avatarId + "' and matchId='" + matchId1 + "' and teamId='" + teamId + "'", null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    name = cursor.getString(cursor.getColumnIndex("name"));
                }
                TeamSquad squad = new TeamSquad();
                squad.setProfilePicture("/assets/defaults/images/default_avatar.png");
                squad.setPlayerRole("");
                squad.setPlayerAvatarId(String.valueOf(avatarId));
                squad.setName(name);

                avatarId++;
                teamSquads.add(squad);

            }

        } catch (Exception e) {
            teamSquads = new ArrayList<>();
            e.printStackTrace();
        }
        return teamSquads;
    }

    public CricketScoreCard fetchScoreCardOfBatsmanInInning(int matchId1, int inningId1, int batsmanId1) {
        CricketScoreCard cricketScoreCard = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_scorecard where matchId = '" + matchId1 + "' and inningId = '" + inningId1 + "' and batsmanId = '" + batsmanId1 + "' ", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int scorecardId = cursor.getInt(cursor.getColumnIndex("id"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String sixes = cursor.getString(cursor.getColumnIndex("sixes"));
                String runs = cursor.getString(cursor.getColumnIndex("runs"));
                String balls = cursor.getString(cursor.getColumnIndex("balls"));
                String fours = cursor.getString(cursor.getColumnIndex("fours"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String strikeRate = cursor.getString(cursor.getColumnIndex("strikeRate"));
                String playOrder = cursor.getString(cursor.getColumnIndex("playOrder"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                String onStrike = cursor.getString(cursor.getColumnIndex("onStrike"));
                String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                cricketScoreCard = new CricketScoreCard(status, sixes, runs, balls, fours, matchId,
                        strikeRate, playOrder, inningId, onStrike, batsmanId);
                cricketScoreCard.setId(scorecardId);

            }
        } catch (Exception e) {
            cricketScoreCard = null;
            e.printStackTrace();
        }
        return cricketScoreCard;
    }

    public List<CricketBall> fetchBallsOfInning(int matchId, int inningId) {
        Cursor cursor = null;
        List<CricketBall> cricketBalls = new ArrayList<>();
        try {
            cursor = db.rawQuery("SELECT * FROM cricket_balls WHERE inningId='" + inningId + "' AND matchId='" + matchId + "' ORDER BY id DESC", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        int ballId = cursor.getInt(cursor.getColumnIndex("id"));
                        String inningOverCount = cursor.getString(cursor.getColumnIndex("inningOverCount"));
                        String ballCountInOver = cursor.getString(cursor.getColumnIndex("ballCountInOver"));
                        String runScored = cursor.getString(cursor.getColumnIndex("runScored "));
                        String extraRuns = cursor.getString(cursor.getColumnIndex("extraRuns "));
                        String isFour = cursor.getString(cursor.getColumnIndex("isFour"));
                        String isSix = cursor.getString(cursor.getColumnIndex("isSix "));
                        String runScoredOnNoBall = cursor.getString(cursor.getColumnIndex("runScoredOnNoBall "));
                        String isNoBall = cursor.getString(cursor.getColumnIndex("isNoBall "));
                        String isWideBall = cursor.getString(cursor.getColumnIndex("isWideBall "));
                        String runScoredOnWideball = cursor.getString(cursor.getColumnIndex("runScoredOnWideball "));
                        String isBye = cursor.getString(cursor.getColumnIndex("isBye"));
                        String runScoredOnBye = cursor.getString(cursor.getColumnIndex("runScoredOnBye "));
                        String isLegBye = cursor.getString(cursor.getColumnIndex("isLegBye "));
                        String runScoredOnLegBye = cursor.getString(cursor.getColumnIndex("runScoredOnLegBye "));
                        String isWicket = cursor.getString(cursor.getColumnIndex("isWicket "));
                        String wicketType = cursor.getString(cursor.getColumnIndex("wicketType "));
                        String comments = cursor.getString(cursor.getColumnIndex("comments "));
                        String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId "));
                        String bowlerId = cursor.getString(cursor.getColumnIndex("bowlerId "));
                        String inningId1 = cursor.getString(cursor.getColumnIndex("inningId "));
                        String overId = cursor.getString(cursor.getColumnIndex("overId "));
                        String matchId1 = cursor.getString(cursor.getColumnIndex("matchId "));
                        String caughtById = cursor.getString(cursor.getColumnIndex("caughtById "));
                        String runOutById = cursor.getString(cursor.getColumnIndex(" runOutById "));
                        String stumpedById = cursor.getString(cursor.getColumnIndex("stumpedById "));
                        String outBatsmanId = cursor.getString(cursor.getColumnIndex("outBatsmanId"));
                        CricketBall cricketBall = new CricketBall(ballCountInOver, inningOverCount, runScored, extraRuns, isFour, isSix, runScoredOnNoBall, isNoBall, isWideBall, runScoredOnWideball, isBye, runScoredOnBye,
                                isLegBye, runScoredOnLegBye, isWicket, wicketType, comments, batsmanId, bowlerId, inningId1, overId, matchId1, caughtById, runOutById, stumpedById, outBatsmanId);
                        cricketBall.setId(ballId);
                        cricketBalls.add(cricketBall);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            cricketBalls = new ArrayList<>();
            e.printStackTrace();
        }

        return cricketBalls;
    }

    public List<CricketOver> fetchAllOvers() {
        Cursor cursor = null;
        List<CricketOver> cricketOvers = new ArrayList<>();
        try {
            cursor = db.rawQuery("select * from cricket_balls", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
        } catch (Exception e) {
            cricketOvers = new ArrayList<>();
            e.printStackTrace();
        }
        return cricketOvers;
    }

    public List<BowlingViewModal> fetchBowlingViewModal(int id) {
        List<BowlingViewModal> bowling = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT sum(co.isMaiden) as maiden, min(co.overNumber) as overNumber, (SELECT ROUND( ( (count(*) / 6) +((count(*) % 6))/10.0),1) FROM cricket_balls WHERE bowlerId =co.bowlerId and inningId='" + id + "' and isNoBall = '0' and isWideBall='0') as numberOfOvers, co.bowlerId,(usr.firstName||' '||usr.lastName) as bowlerAvatarName,sum(co.runsScoredInOver) as runs, ((SELECT sum(iswicket) from cricket_balls cb WHERE cb.bowlerId = co.bowlerId and wicketType != 'RUN OUT' and cb.matchId=co.matchId)) as wickets, sum(co.extraRunsInOver) as extras, (sum(co.runsScoredInOver) / count(co.bowlerId)) as economy FROM cricket_overs co LEFT JOIN avatar avtr ON avtr.id=co.bowlerId LEFT JOIN user usr ON avtr.userid=usr.id WHERE inningId='" + id + "' and bowlerId is not null GROUP BY co.bowlerId order by overNumber", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        BowlingViewModal bowlingViewModal = new BowlingViewModal();
                        bowlingViewModal.setMaiden(cursor.getString(cursor.getColumnIndex("maiden")));
                        bowlingViewModal.setOverNumber(cursor.getString(cursor.getColumnIndex("overNumber")));
                        bowlingViewModal.setNumberOfOvers(cursor.getString(cursor.getColumnIndex("numberOfOvers")));
                        bowlingViewModal.setBowlerId(cursor.getString(cursor.getColumnIndex("bowlerId")));
                        bowlingViewModal.setBowlerAvatarName(cursor.getString(cursor.getColumnIndex("bowlerAvatarName")));
                        bowlingViewModal.setRuns(cursor.getString(cursor.getColumnIndex("runs")));
                        bowlingViewModal.setWickets(cursor.getString(cursor.getColumnIndex("wickets")));
                        bowlingViewModal.setExtras(cursor.getString(cursor.getColumnIndex("extras")));
                        bowlingViewModal.setEconomy(cursor.getString(cursor.getColumnIndex("economy")));
                        bowling.add(bowlingViewModal);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            bowling = new ArrayList<>();
            e.printStackTrace();
        }
        return bowling;
    }

    public int noBallCount(int innId, int bowllerId, int matchId) {
        int noBallcount = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT count(*) as noBallcount FROM cricket_balls WHERE matchId='" + matchId + "' AND inningId='" + innId + "' AND isNoBall=1 AND bowlerId='" + bowllerId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                noBallcount = cursor.getInt(cursor.getColumnIndex("noBallcount"));

            }
        } catch (Exception e) {
            noBallcount = 0;
            e.printStackTrace();
        }
        return noBallcount;
    }

    public int wideBallCount(int innId, int bowllerId, int matchId) {
        int wideBall = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM cricket_balls WHERE matchId='" + matchId + "' AND inningId='" + innId + "' AND isWideBall=1 AND bowlerId='" + bowllerId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        wideBall += (cursor.getInt(cursor.getColumnIndex("runScoredOnWideball"))) + 1;
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wideBall;
    }

    public int dotBallCount(int innId, int bowllerId, int matchId) {
        int dotCount = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT count(*) as dotCount FROM cricket_balls WHERE matchId='" + matchId + "' AND inningId='" + innId + "' AND isWideBall=0 AND isNoBall=0 AND bowlerId='" + bowllerId + "' AND runScored=0", null);

            if (cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst();
                dotCount = cursor.getInt(cursor.getColumnIndex("dotCount"));

            }
        } catch (Exception e) {
            dotCount = 0;
            e.printStackTrace();
        }
        return dotCount;
    }

    public List<CricketBall> legbyeOrByeBalls(int innId, int bowllerId, int matchId) {
        List<CricketBall> cricketBalls = new ArrayList<>();
        CricketBall cb = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM cricket_balls WHERE matchId='" + matchId + "' AND inningId='" + innId + "' AND (isLegBye=1 or isBye=1) AND bowlerId='" + bowllerId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        cb = new CricketBall("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
                        cb.setRunScoredOnBye(cursor.getString(cursor.getColumnIndex("'runScoredOnBye'")));
                        cb.setRunScoredOnLegBye(cursor.getString(cursor.getColumnIndex("'runScoredOnLegBye'")));
                        cricketBalls.add(cb);
                        cursor.moveToNext();
                    }
                }

            }
        } catch (Exception e) {
            cricketBalls = new ArrayList<>();
            e.printStackTrace();
        }
        return cricketBalls;
    }

    public Team fetchTeamDetail(int teamId1) {
        Team team = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT a.id as teamAvatarId, a.name, a.profilePicture, t.id as teamId FROM avatar a JOIN team t ON t.avatar = a.id WHERE a.avatarType='TEAM' " +
                    "AND t.id = '" + teamId1 + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String teamAvatarId = cursor.getString(cursor.getColumnIndex("teamAvatarId"));
                String teamId = cursor.getString(cursor.getColumnIndex("teamId"));
                String profilePicture = cursor.getString(cursor.getColumnIndex("profilePicture"));
                team = new Team();
                team.setName(name);
                team.setProfilePicture(profilePicture);
                team.setTeamAvatarId(teamAvatarId);
                team.setIsTeamScoringOnSf("");
                team.setTeamId(teamId);

            }
        } catch (Exception e) {
            team = null;
            e.printStackTrace();
        }
        return team;
    }

    public MatchScorer fetchMatchTeamScorer(int userId, int matchId, int teamId) {
        MatchScorer matchScorer = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * from  match_scorer where scorerOrder in (1,2,3) and scorerId='" + userId + "' and team='" + teamId + "' and matchId='" + matchId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String readStatus = cursor.getString(cursor.getColumnIndex("readStatus"));
                String scorerOrder = cursor.getString(cursor.getColumnIndex("scorerOrder"));
                String inviteStatus = cursor.getString(cursor.getColumnIndex("inviteStatus"));
                String scorerId = cursor.getString(cursor.getColumnIndex("scorerId"));
                String matchId1 = cursor.getString(cursor.getColumnIndex("matchId"));
                String inviteSentOn = cursor.getString(cursor.getColumnIndex("inviteSentOn"));
                String team = cursor.getString(cursor.getColumnIndex("team"));

                matchScorer = new MatchScorer(readStatus, scorerOrder, inviteStatus, scorerId, matchId1, inviteSentOn, team);

                matchScorer.setId(id);

            }
        } catch (Exception e) {
            matchScorer = null;
            e.printStackTrace();
        }
        return matchScorer;
    }

    public ExtraAndFOW fetchFOW(int inningId) {
        ExtraAndFOW extraAndFOW = null;
        List<String> fow = new ArrayList<>();
        ExtraRuns er = new ExtraRuns();
        ;

        Cursor cursor = null;
        try {
            extraAndFOW = new ExtraAndFOW();
            cursor = db.rawQuery("SELECT cs.id,cs.inningOverCount, cs.runScored,cs.isWicket,cs.extraRuns,cs.isWideBall,cs.runScoredOnNoBall,cs.runScoredOnWideball,cs.runScoredOnBye,cs.runScoredOnLegBye,cs.isNoBall,cs.isBye,cs.isLegBye,cs.outBatsmanId, av.id,av.name as outBatsmanName FROM cricket_balls cs LEFT JOIN avatar av ON cs.outBatsmanId=av.id WHERE inningId='" + inningId + "'", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        int run = 0;
                        int wicket = 0;
                        int runtoadd = 0;

                        String runScored = cursor.getString(cursor.getColumnIndex("runScored"));
                        String isWicket = cursor.getString(cursor.getColumnIndex("isWicket"));
                        String isWideBall = cursor.getString(cursor.getColumnIndex("isWideBall"));
                        String isBye = cursor.getString(cursor.getColumnIndex("isBye"));
                        String extraRuns = cursor.getString(cursor.getColumnIndex("extraRuns"));
                        String runScoredOnBye = cursor.getString(cursor.getColumnIndex("runScoredOnBye"));
                        String isNoBall = cursor.getString(cursor.getColumnIndex("isNoBall"));
                        String isLegBye = cursor.getString(cursor.getColumnIndex("isLegBye"));
                        String runScoredOnLegBye = cursor.getString(cursor.getColumnIndex("runScoredOnLegBye"));
                        String inningOverCount = cursor.getString(cursor.getColumnIndex("inningOverCount"));
                        String outBatsmanName = cursor.getString(cursor.getColumnIndex("outBatsmanName"));
                        run += Integer.parseInt(runScored);
                        if (isWicket.equalsIgnoreCase("1")) {
                            wicket++;
                            fow.add(run + "-" + wicket + " (" + outBatsmanName + ", " + inningOverCount + ")");
                        }

                        // if($f['isWideBall']){

                        // 	//$extras['wd']+=$f['extraRuns'];

                        // }
                        if (isWideBall.equalsIgnoreCase("1")) {
                            runtoadd = 0;
                            if (isBye.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - Integer.parseInt(runScoredOnBye);
                            } else {
                                runtoadd = Integer.parseInt(extraRuns);
                            }
                            er.setWd(er.getWd() + runtoadd);
                        }
                        if (isBye.equalsIgnoreCase("1") && !isNoBall.equalsIgnoreCase("1")) {
                            runtoadd = 0;
                            if (isWideBall.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - 1;
                            } else {
                                runtoadd = Integer.parseInt(extraRuns);
                            }
                            er.setB(er.getB() + runtoadd);
                        } else if (isBye.equalsIgnoreCase("1") && isNoBall.equalsIgnoreCase("1")) {
                            runtoadd = 0;
                            if (isWideBall.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - 1;
                            } else if (isNoBall.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - 1;
                            } else {
                                runtoadd = Integer.parseInt(extraRuns);
                            }
                            er.setB(er.getB() + runtoadd);
                        }
                        if (isLegBye.equalsIgnoreCase("1") && !isNoBall.equalsIgnoreCase("1")) {
                            er.setLb(er.getLb() + Integer.parseInt(extraRuns));
                        } else if (isLegBye.equalsIgnoreCase("1") && isNoBall.equalsIgnoreCase("1")) {
                            runtoadd = 0;
                            if (isNoBall.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - 1;
                            } else {
                                runtoadd = Integer.parseInt(extraRuns);
                            }
                            er.setLb(er.getLb() + runtoadd);
                        }
                        runtoadd = 0;
                        if (isNoBall.equalsIgnoreCase("1")) {
                            if (isLegBye.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - Integer.parseInt(runScoredOnLegBye);
                            } else if (isBye.equalsIgnoreCase("1")) {
                                runtoadd = Integer.parseInt(extraRuns) - Integer.parseInt(runScoredOnBye);
                            } else {
                                runtoadd = Integer.parseInt(extraRuns);
                            }
                            er.setNb(er.getNb() + runtoadd);
                        }

                        cursor.moveToNext();
                    }
                }
            }
            extraAndFOW.setFow(fow);
            extraAndFOW.setExtraRuns(er);
        } catch (Exception e) {
            extraAndFOW = null;
            e.printStackTrace();
        }
        return extraAndFOW;
    }

    public int fetchInningNumberOfWicket(int matchId, int inningId) {
        int wickets = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT SUM(wicketsInOver) as wicketsInOver from cricket_overs WHERE matchId='" + matchId + "' and inningId='" + inningId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                wickets = cursor.getInt(cursor.getColumnIndex("wicketsInOver"));
            }
        } catch (Exception e) {
            wickets = 0;
            e.printStackTrace();
        }
        return wickets;
    }

    public int fetchDotBallOfBatsman(int batsmanId, int matchId, int inningId) {
        int dotballs = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT  count(*) as dotballs FROM cricket_balls WHERE matchId='" + matchId + "'  AND inningId='" + inningId + "'  AND isWideBall=0 AND batsmanId='" + batsmanId + "' AND runScored=0", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                dotballs = cursor.getInt(cursor.getColumnIndex("dotballs"));
            }
        } catch (Exception e) {
            dotballs = 0;
            e.printStackTrace();
        }
        return dotballs;
    }

    public void updateScoreCardData(CricketScoreCard cricketScoreCard) {
        //SQLiteDatabase data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", cricketScoreCard.getStatus());
        cv.put("sixes", cricketScoreCard.getSixes());
        cv.put("runs", cricketScoreCard.getRuns());
        cv.put("balls", cricketScoreCard.getBalls());
        cv.put("fours", cricketScoreCard.getFours());
        cv.put("matchId", cricketScoreCard.getMatchId());
        cv.put("strikeRate", cricketScoreCard.getStrikeRate());
        cv.put("playOrder", cricketScoreCard.getPlayOrder());
        cv.put("inningId", cricketScoreCard.getInningId());
        cv.put("onStrike", cricketScoreCard.isOnStrike());
        cv.put("batsmanId", cricketScoreCard.getBatsmanId());
        db.update("cricket_scorecard", cv, "id =\"" + cricketScoreCard.getId() + "\"", null);
    }

    public void updateScoreCardID(CricketScoreCard cricketScoreCard) {
        //SQLiteDatabase data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", cricketScoreCard.getStatus());
        cv.put("sixes", cricketScoreCard.getSixes());
        cv.put("runs", cricketScoreCard.getRuns());
        cv.put("balls", cricketScoreCard.getBalls());
        cv.put("fours", cricketScoreCard.getFours());
        cv.put("matchId", cricketScoreCard.getMatchId());
        cv.put("strikeRate", cricketScoreCard.getStrikeRate());
        cv.put("playOrder", cricketScoreCard.getPlayOrder());
        cv.put("inningId", cricketScoreCard.getInningId());
        cv.put("onStrike", cricketScoreCard.isOnStrike());
        cv.put("batsmanId", cricketScoreCard.getBatsmanId());
        if (cricketScoreCard.getId() > 0) {
            cv.put("id", cricketScoreCard.getId());
            cv.put("syncStatus", "0");
        }

        db.update("cricket_scorecard", cv, "localId =\"" + cricketScoreCard.getId() + "\"", null);
    }

    public void updateInningData(CricketInning cricketInning) {
        ContentValues cv = new ContentValues();
        cv.put("totalOvers", cricketInning.getTotalOvers());
        cv.put("wickets", cricketInning.getWickets());
        cv.put("isDeclared", cricketInning.isDeclared());
        cv.put("bowlingTeamId", cricketInning.getBowlingTeamId());
        cv.put("isScoredOnSF", cricketInning.isScoredOnSF());
        cv.put("matchId", cricketInning.getMatchId());
        cv.put("inningNumber", cricketInning.getInningNumber());
        cv.put("playing", cricketInning.isPlaying());
        cv.put("daySession", cricketInning.getDaySession());
        cv.put("totalRunsScored", cricketInning.getTotalRunsScored());
        cv.put("state", cricketInning.getState());
        cv.put("extras", cricketInning.getExtras());
        cv.put("playedOvers", cricketInning.getPlayedOvers());
        cv.put("battingTeamId", cricketInning.getBattingTeamId());
        cv.put("day", cricketInning.getDay());
        db.update("cricket_innings", cv, "id =\"" + cricketInning.getId() + "\"", null);
    }

    public void updateInningID(CricketInning cricketInning) {
        ContentValues cv = new ContentValues();
        cv.put("totalOvers", cricketInning.getTotalOvers());
        cv.put("wickets", cricketInning.getWickets());
        cv.put("isDeclared", cricketInning.isDeclared());
        cv.put("bowlingTeamId", cricketInning.getBowlingTeamId());
        cv.put("isScoredOnSF", cricketInning.isScoredOnSF());
        cv.put("matchId", cricketInning.getMatchId());
        cv.put("inningNumber", cricketInning.getInningNumber());
        cv.put("playing", cricketInning.isPlaying());
        cv.put("daySession", cricketInning.getDaySession());
        cv.put("totalRunsScored", cricketInning.getTotalRunsScored());
        cv.put("state", cricketInning.getState());
        cv.put("extras", cricketInning.getExtras());
        cv.put("playedOvers", cricketInning.getPlayedOvers());
        cv.put("battingTeamId", cricketInning.getBattingTeamId());
        cv.put("day", cricketInning.getDay());
        if (cricketInning.getId() > 0) {
            cv.put("id", cricketInning.getId());
            cv.put("syncStatus", "0");
        }

        db.update("cricket_innings", cv, "localId =\"" + cricketInning.getId() + "\"", null);
    }

    public void updateOverData(CricketOver cricketOver) {
        ContentValues cv = new ContentValues();
        cv.put("current", cricketOver.getCurrent());
        cv.put("wicketsInOver", cricketOver.getWicketsInOver());
        cv.put("overNumber", cricketOver.getOverNumber());
        cv.put("matchId", cricketOver.getMatchId());
        cv.put("extraRunsInOver", cricketOver.getExtraRunsInOver());
        cv.put("isMaiden", cricketOver.getIsMaiden());
        cv.put("runsScoredInOver", cricketOver.getRunsScoredInOver());
        cv.put("inningId", cricketOver.getInningId());
        cv.put("bowlerId", cricketOver.getBowlerId());

        db.update("cricket_overs", cv, "id =\"" + cricketOver.getId() + "\"", null);
    }

    public void updateMatchData(Matches matches) {
        ContentValues cv = new ContentValues();
        cv.put("id", matches.getId());
        cv.put("inviteStatus", matches.getInviteStatus());
        cv.put("matchDate", matches.getMatchDate());
        cv.put("numberOfOvers", matches.getNumberOfOvers());
        cv.put("matchStatus", matches.getMatchStatus());
        cv.put("tossSelection", matches.getTossSelection());
        cv.put("eventId", matches.getEventId());
        cv.put("team2CheckAvailibility", matches.getTeam2CheckAvailibility());
        cv.put("team1Id", matches.getTeam1Id());
        cv.put("matchResultId", matches.getMatchResultId());
        cv.put("location", matches.getLocation());
        cv.put("tie", matches.getTie());
        cv.put("matchType", matches.getMatchType());
        cv.put("numberOfInnings", matches.getNumberOfInnings());
        cv.put("readStatus", matches.getReadStatus());
        cv.put("dl", matches.getDl());
        cv.put("description", matches.getDescription());
        cv.put("activeScorerId", matches.getActiveScorerId());
        cv.put("isTeam2ScoringOnSf", matches.getIsTeam2ScoringOnSf());
        cv.put("tossResultId", matches.getTossResultId());
        cv.put("calendarId", matches.getCalendarId());
        cv.put("leagueId", matches.getLeagueId());
        cv.put("isTeam1ScoringOnSf", matches.getIsTeam1ScoringOnSf());
        cv.put("team1CheckAvailibility", matches.getTeam1CheckAvailibility());
        cv.put("points", matches.getPoints());
        cv.put("numberOfPlayers", matches.getNumberOfPlayers());
        cv.put("tournamentId", matches.getTournamentId());
        cv.put("team2Id", matches.getTeam2Id());

        db.update("matches", cv, "id =\"" + matches.getId() + "\"", null);
    }

    public void updateOverID(CricketOver cricketOver) {
        ContentValues cv = new ContentValues();
        cv.put("current", cricketOver.getCurrent());
        cv.put("wicketsInOver", cricketOver.getWicketsInOver());
        cv.put("overNumber", cricketOver.getOverNumber());
        cv.put("matchId", cricketOver.getMatchId());
        cv.put("extraRunsInOver", cricketOver.getExtraRunsInOver());
        cv.put("isMaiden", cricketOver.getIsMaiden());
        cv.put("runsScoredInOver", cricketOver.getRunsScoredInOver());
        cv.put("inningId", cricketOver.getInningId());
        cv.put("bowlerId", cricketOver.getBowlerId());
        if (cricketOver.getId() > 0) {
            cv.put("id", cricketOver.getId());
            cv.put("syncStatus", "0");
        }

        db.update("cricket_overs", cv, "localId =\"" + cricketOver.getId() + "\"", null);
    }

    public void updateBallData(CricketBall cricketBall) {
        //SQLiteDatabase data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ballCountInOver", cricketBall.getBallCountInOver());
        cv.put("inningOverCount", cricketBall.getInningOverCount());
        cv.put("runScored", cricketBall.getRunScored());
        cv.put("extraRuns", cricketBall.getExtraRuns());

        cv.put("isFour", cricketBall.isFour());
        cv.put("isSix", cricketBall.isSix());
        cv.put("runScoredOnNoBall", cricketBall.getRunScoredOnNoBall());
        cv.put("isNoBall", cricketBall.isNoBall());

        cv.put("isWideBall", cricketBall.isWideBall());
        cv.put("runScoredOnWideball", cricketBall.getRunScoredOnWideball());
        cv.put("isBye", cricketBall.isBye());
        cv.put("runScoredOnBye", cricketBall.getRunScoredOnBye());

        cv.put("isLegBye", cricketBall.isLegBye());
        cv.put("runScoredOnLegBye", cricketBall.getRunScoredOnLegBye());
        cv.put("isWicket", cricketBall.isWicket());
        cv.put("wicketType", cricketBall.getWicketType());

        cv.put("comments", cricketBall.getComments());
        cv.put("batsmanId", cricketBall.getBatsmanId());
        cv.put("bowlerId", cricketBall.getBowlerId());
        cv.put("inningId", cricketBall.getInningId());

        cv.put("overId", cricketBall.getOverId());
        cv.put("matchId", cricketBall.getMatchId());

        if (cricketBall.getCaughtById() != null && !cricketBall.getCaughtById().isEmpty()) {
            cv.put("caughtById", cricketBall.getCaughtById());
        }
        if (cricketBall.getRunOutById() != null && !cricketBall.getRunOutById().isEmpty()) {
            cv.put("runOutById", cricketBall.getRunOutById());
        }
        if (cricketBall.getStumpedById() != null && !cricketBall.getStumpedById().isEmpty()) {
            cv.put("stumpedById", cricketBall.getStumpedById());
        }
        if (cricketBall.getOutBatsmanId() != null && !cricketBall.getOutBatsmanId().isEmpty()) {
            cv.put("outBatsmanId", cricketBall.getOutBatsmanId());
        }

        db.update("cricket_balls", cv, "id =\"" + cricketBall.getId() + "\"", null);
    }

    public void updateBallID(CricketBall cricketBall) {
        //SQLiteDatabase data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ballCountInOver", cricketBall.getBallCountInOver());
        cv.put("inningOverCount", cricketBall.getInningOverCount());
        cv.put("runScored", cricketBall.getRunScored());
        cv.put("extraRuns", cricketBall.getExtraRuns());

        cv.put("isFour", cricketBall.isFour());
        cv.put("isSix", cricketBall.isSix());
        cv.put("runScoredOnNoBall", cricketBall.getRunScoredOnNoBall());
        cv.put("isNoBall", cricketBall.isNoBall());

        cv.put("isWideBall", cricketBall.isWideBall());
        cv.put("runScoredOnWideball", cricketBall.getRunScoredOnWideball());
        cv.put("isBye", cricketBall.isBye());
        cv.put("runScoredOnBye", cricketBall.getRunScoredOnBye());

        cv.put("isLegBye", cricketBall.isLegBye());
        cv.put("runScoredOnLegBye", cricketBall.getRunScoredOnLegBye());
        cv.put("isWicket", cricketBall.isWicket());
        cv.put("wicketType", cricketBall.getWicketType());

        cv.put("comments", cricketBall.getComments());
        cv.put("batsmanId", cricketBall.getBatsmanId());
        cv.put("bowlerId", cricketBall.getBowlerId());
        cv.put("inningId", cricketBall.getInningId());

        cv.put("overId", cricketBall.getOverId());
        cv.put("matchId", cricketBall.getMatchId());

        if (cricketBall.getCaughtById() != null && !cricketBall.getCaughtById().isEmpty()) {
            cv.put("caughtById", cricketBall.getCaughtById());
        }
        if (cricketBall.getRunOutById() != null && !cricketBall.getRunOutById().isEmpty()) {
            cv.put("runOutById", cricketBall.getRunOutById());
        }
        if (cricketBall.getStumpedById() != null && !cricketBall.getStumpedById().isEmpty()) {
            cv.put("stumpedById", cricketBall.getStumpedById());
        }
        if (cricketBall.getOutBatsmanId() != null && !cricketBall.getOutBatsmanId().isEmpty()) {
            cv.put("outBatsmanId", cricketBall.getOutBatsmanId());
        }
        if (cricketBall.getId() > 0) {
            cv.put("id", cricketBall.getId());
            cv.put("syncStatus", "0");
        }
        db.update("cricket_balls", cv, "localId =\"" + cricketBall.getId() + "\"", null);
    }
    public void updateBallServerID(int id,int serverId)
    {
        //SQLiteDatabase data = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("serverID", serverId);
            db.update("cricket_balls_local", cv, "id =\"" + id + "\"", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }
    }
    public void updateBallJson(int id,String jsonData)
    {
        //SQLiteDatabase data = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("jsonData", jsonData);
            db.update("cricket_balls_local", cv, "id =\"" + id + "\"", null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }
    }
    public void updateTossServerID(int id,int serverId)
    {
        //SQLiteDatabase data = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("serverinningId", serverId);
            db.update("Toss_local", cv, "id =\"" + id + "\"", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }
    }

    public void updateInningIdForMatch(int oldInningId, int newInningId)
    {
        try {


            db.execSQL("update cricket_balls set inningId = " + newInningId + " where inningId = " + oldInningId + "");
            db.execSQL("update cricket_innings set id = " + newInningId + " where id = " + oldInningId + "");
            db.execSQL("update cricket_overs set inningId = " + newInningId + " where inningId = " + oldInningId + "");
            db.execSQL("update cricket_scorecard set inningId = " + newInningId + " where inningId = " + oldInningId + "");
            List<CricketBallJson> ballJsons = fetchBallDataJson();

            for (int i = 0; i < ballJsons.size(); i++) {
                JSONObject ballJson = new JSONObject(ballJsons.get(i).getJsonData());
                ballJson.put("inningId", newInningId);
                updateBallJson(ballJsons.get(i).getId(),ballJson.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }
    }


    public void deleteTableRecord(String tableName, String id) {
        //SQLiteDatabase data = this.getReadableDatabase();
        db.delete(tableName, "id ='" + id + "'", null);
        //this.close();
    }

    public void deleteLocalBallOnUndo(String id) {
        //SQLiteDatabase data = this.getReadableDatabase();
        db.delete("cricket_balls_local", "cricket_balls_id ='" + id + "'", null);
        //this.close();
    }

    public CricketScoreCard fetchScoreCardOfMatchInning(int matchId1, int inningId1) {
        CricketScoreCard cricketScoreCard = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from cricket_scorecard where matchId = '" + matchId1 + "' and inningId = '" + inningId1 + "' order by playOrder desc", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                int scorecardId = cursor.getInt(cursor.getColumnIndex("id"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                String sixes = cursor.getString(cursor.getColumnIndex("sixes"));
                String runs = cursor.getString(cursor.getColumnIndex("runs"));
                String balls = cursor.getString(cursor.getColumnIndex("balls"));
                String fours = cursor.getString(cursor.getColumnIndex("fours"));
                String matchId = cursor.getString(cursor.getColumnIndex("matchId"));
                String strikeRate = cursor.getString(cursor.getColumnIndex("strikeRate"));
                String playOrder = cursor.getString(cursor.getColumnIndex("playOrder"));
                String inningId = cursor.getString(cursor.getColumnIndex("inningId"));
                String onStrike = cursor.getString(cursor.getColumnIndex("onStrike"));
                String batsmanId = cursor.getString(cursor.getColumnIndex("batsmanId"));
                cricketScoreCard = new CricketScoreCard(status, sixes, runs, balls, fours, matchId,
                        strikeRate, playOrder, inningId, onStrike, batsmanId);
                cricketScoreCard.setId(scorecardId);

            }
        } catch (Exception e) {
            cricketScoreCard = null;
            e.printStackTrace();
        }
        return cricketScoreCard;
    }

    public void deleteData(String match_id) {
        //	SQLiteDatabase data = this.getReadableDatabase();
        db.delete("medData", "match_id ='" + match_id + "'", null);
        //this.close();
    }

    public void deleteTable() {
        //SQLiteDatabase data = this.getReadableDatabase();
        db.delete("medData", null, null);
        //this.close();
    }

    // API FUNCTIONS

    // Api function for Undo Ball
    public String UndoBall(int matchId, int inningId) {
        UniverseResponseModel<String> result = new UniverseResponseModel<String>();Match match = fetchMatchByMatchId(matchId);
        if (match != null) {
            CricketInning cricketInning = fetchInningById(inningId);
            if (cricketInning != null) {
                CricketBall cricketBall = fetchLatestBallOfInning(matchId, inningId);
                if (cricketBall != null) {
                    CricketOver cricketOver = fetchOverById(Integer.parseInt(cricketBall.getOverId()));
                    if (cricketOver != null) {
                        int totalRunScored = Integer.parseInt(cricketBall.getRunScored());
                        int ballId = cricketBall.getId();
                        int extraRuns = Integer.parseInt(cricketBall.getExtraRuns());
                        int initialextraRuns = Integer.parseInt(cricketBall.getExtraRuns());
                        int isFour = Integer.parseInt(cricketBall.isFour());
                        int isNoBall = Integer.parseInt(cricketBall.isNoBall());
                        int isWideBall = Integer.parseInt(cricketBall.isWideBall());
                        int isSix = Integer.parseInt(cricketBall.isSix());
                        int isBye = Integer.parseInt(cricketBall.isBye());
                        int isLegBye = Integer.parseInt(cricketBall.isLegBye());
                        int isWicket = Integer.parseInt(cricketBall.isWicket());
                        int wicketType = Integer.parseInt(cricketBall.isWicket());
                        int bowlerId = Integer.parseInt(cricketBall.getBowlerId());
                        int batsmanId = Integer.parseInt(cricketBall.getBatsmanId());
                        int outBatsmanId = Integer.parseInt((cricketBall.getOutBatsmanId()==null)?"0":cricketBall.getOutBatsmanId());
                        int runScoredOnNoBall = Integer.parseInt(cricketBall.getRunScoredOnNoBall());
                        int runScoredOnWideBall = Integer.parseInt(cricketBall.getRunScoredOnWideball());
                        int runScoredOnBye = Integer.parseInt(cricketBall.getRunScoredOnBye());
                        int runScoredOnLegBye = Integer.parseInt(cricketBall.getRunScoredOnLegBye());
                        int totalRunsInInning = Integer.parseInt(cricketInning.getTotalRunsScored()) - totalRunScored;
                        int totalExtrasInInning = Integer.parseInt(cricketInning.getExtras()) - extraRuns;
                        int totalWickets = Integer.parseInt(cricketInning.getWickets());
                        if (isWicket == 1) {
                            cricketOver.setWicketsInOver(String.valueOf(Integer.parseInt(cricketOver.getWicketsInOver()) - 1));
                            cricketInning.getWickets();
                            totalWickets = Integer.parseInt(cricketInning.getWickets()) - 1;
                        }
                        double playedOvers = 0f;
                        String pplayedOvers = "0";

                        if (isNoBall == 1 || isWideBall == 1) {
                            pplayedOvers = cricketInning.getPlayedOvers();
                        } else {
                            int overNo = (int) (Float.parseFloat(cricketInning.getPlayedOvers()) / 1);
                            int overBallNo = (int) ((Float.parseFloat(cricketInning.getPlayedOvers()) * 10) % 10);
                            if (overBallNo == 0) {
                                playedOvers = Float.parseFloat(cricketInning.getPlayedOvers()) - 0.5f;
                                pplayedOvers = String.valueOf(playedOvers);
                            } else {
                                Float ll = Float.parseFloat(cricketInning.getPlayedOvers());
                                Float plo = Float.parseFloat(String.format("%.1f", ll));
                                playedOvers = plo - 0.1f;
                                Float playedOvers1 = Float.parseFloat(String.format("%.1f", playedOvers));
                                int overNo1 = (int) (playedOvers / 1);
                                int overBallNo1 = (int) (((playedOvers1) * 10f) % 10f);
                                if (overBallNo1 == 0) {
                                    playedOvers = overNo1;
                                    pplayedOvers = String.valueOf(overNo1);
                                } else {
                                    pplayedOvers = String.valueOf(playedOvers1);
                                }
                            }
                        }
                        cricketInning.setTotalRunsScored(String.valueOf(totalRunsInInning));
                        cricketInning.setDeclared("0");
                        cricketInning.setExtras(String.valueOf(totalExtrasInInning));
                        cricketInning.setWickets(String.valueOf(totalWickets));
                        cricketInning.setPlayedOvers(pplayedOvers);
                        cricketInning.setId(cricketInning.getId());

                        updateInningData(cricketInning);

                        cricketOver.setRunsScoredInOver(String.valueOf(Integer.parseInt(cricketOver.getRunsScoredInOver()) - totalRunScored));
                        cricketOver.setExtraRunsInOver(String.valueOf(Integer.parseInt(cricketOver.getExtraRunsInOver()) - extraRuns));
                        cricketOver.setIsMaiden(String.valueOf(Integer.parseInt(cricketOver.getIsMaiden()) > 0 ? 0 : 1));
                        cricketOver.setId(cricketOver.getId());
                        updateOverData(cricketOver);

                        int runScored = totalRunScored;

                        if (isNoBall == 1) {
                            extraRuns -= 1;
                            if (runScoredOnNoBall == 1) {
                                runScored -= runScoredOnNoBall;
                            }
                        }

                        if (isWideBall == 1) {
                            extraRuns -= 1;
                            if (runScoredOnWideBall == 1) {
                                extraRuns -= runScoredOnWideBall;
                            }
                        }

                        if (isBye == 1) {
                            if (runScoredOnBye == 1) {
                                extraRuns -= runScoredOnBye;
                            }
                        }

                        if (isLegBye == 1) {
                            if (runScoredOnLegBye == 1) {
                                extraRuns -= runScoredOnLegBye;
                            }
                        }
                        CricketScoreCard strikeBatsmanScoreCard = fetchScoreCardOfBatsmanInInning(matchId, inningId, batsmanId);
                       // if(Integer.parseInt( strikeBatsmanScoreCard.getRuns())>0) {
                            strikeBatsmanScoreCard.setRuns(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getRuns()) - runScored));
                       // }
                       // if(Integer.parseInt( strikeBatsmanScoreCard.getBalls())>0) {
                            strikeBatsmanScoreCard.setBalls(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getBalls()) - 1));
                       // }

                        if (isNoBall == 1 && runScored >= 1) {
                            strikeBatsmanScoreCard.setRuns(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getRuns()) + 1));
                        }
                        if (isWideBall == 1) {
                            int runtosub = 0;
                            if (cricketBall.isBye().equalsIgnoreCase("1")) {
                                runtosub = (initialextraRuns - runScoredOnBye);
                            } else {
                                runtosub = initialextraRuns;
                            }
                            strikeBatsmanScoreCard.setRuns(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getRuns()) + runtosub));
                            strikeBatsmanScoreCard.setBalls(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getBalls()) + 1));
                        }

                        if (isLegBye == 1) {
                            strikeBatsmanScoreCard.setRuns(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getRuns()) + runScoredOnLegBye));
                        }

                        if (isBye == 1) {
                            strikeBatsmanScoreCard.setRuns(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getRuns()) - runScoredOnBye));
                        }

                        if (isFour == 1) {
                            if (isWideBall == 0 && isLegBye == 0 && isBye == 0) {
                                strikeBatsmanScoreCard.setFours(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getFours()) - 1));
                            }
                        }
                        if (isSix == 1) {
                            strikeBatsmanScoreCard.setSixes(String.valueOf(Integer.parseInt(strikeBatsmanScoreCard.getSixes()) - 1));
                        }
                        if (isWicket == 1) {
                            if (outBatsmanId > 0 == batsmanId > 0) {
                                strikeBatsmanScoreCard.setStatus("NOT OUT");
                            } else {
                                CricketScoreCard cricketScoreCard2 = fetchScoreCardOfBatsmanInInning(matchId, inningId, batsmanId);
                                cricketScoreCard2.setStatus("NOT OUT");
                                cricketScoreCard2.setBatsmanId(String.valueOf(outBatsmanId));
                                cricketScoreCard2.setId(cricketScoreCard2.getId());
                                updateScoreCardData(cricketScoreCard2);
                            }
                        }
                        if (!strikeBatsmanScoreCard.getBalls().equalsIgnoreCase("0")) {
                            strikeBatsmanScoreCard.setStrikeRate(String.valueOf(Float.parseFloat(strikeBatsmanScoreCard.getRuns()) / Float.parseFloat(strikeBatsmanScoreCard.getBalls()) * 100));
                        } else {
                            strikeBatsmanScoreCard.setStrikeRate("0");
                        }

                        //update scorecard result
                        updateScoreCardData(strikeBatsmanScoreCard);
                        deleteTableRecord("cricket_balls", String.valueOf(ballId));
                        deleteLocalBallOnUndo(String.valueOf(ballId));
                        if (cricketBall.getBallCountInOver().equalsIgnoreCase("1")) {
                            deleteTableRecord("cricket_overs", String.valueOf(cricketBall.getOverId()));
                        }

                        cricketBall = fetchLatestBallOfInning(matchId, inningId);
                        CricketScoreCard scoreCardToDelete = fetchScoreCardOfMatchInning(matchId, inningId);
                        if (cricketBall != null) {
                            if (cricketBall.isWicket().equalsIgnoreCase("1")) {
                                deleteTableRecord("cricket_scorecard", String.valueOf(scoreCardToDelete.getId()));
                            }
                        } else {
                            deleteTableRecord("cricket_scorecard", String.valueOf(scoreCardToDelete.getId()));
                        }
                        Gson gson = new Gson();
                        String balls = gson.toJson(cricketBall);

                        result.setResult("1");
                        result.setData(balls);
                        result.setMessage("Over not found.");

                    } else {
                        result.setResult("0");
                        result.setData("[]");
                        result.setMessage("Over not found.");
                    }
                } else {
                    result.setResult("0");
                    result.setData("[]");
                    result.setMessage("Ball not found.");
                }
            } else {
                result.setResult("0");
                result.setData("[]");
                result.setMessage("Inning not found.");
            }
        } else {
            result.setResult("0");
            result.setData("[]");
            result.setMessage("Match not found.");
        }
        Gson gson = new Gson();
        String res = gson.toJson(result);
        return res;
    }

    // Api function for StartSecond Inning
    public UniverseResponseModel<StartSecondInningResponseModel> StartSecondInning(int matchId, int userId) {

        UniverseResponseModel<StartSecondInningResponseModel> result = new UniverseResponseModel<StartSecondInningResponseModel>();
        Matches match = fetchDBMatchByMatchId(matchId);
        if (match != null) {

            List<Inning> innings = fetchInningsOfMatch(matchId);


            if (innings.size() > 0) {

                Inning firstInning = innings.get(0);
                String isfirstInningPlaying = firstInning.getPlaying();
                String inningnumber = firstInning.getInningNumber();
                if (inningnumber.equalsIgnoreCase("1") && isfirstInningPlaying.equalsIgnoreCase("0")) {
                    String battingTeamIdForSecondInning = firstInning.getBowlingTeamId();
                    String bowlingTeamIdForSecondInning = firstInning.getBattingTeamId();

                    MatchScorer scorer = fetchMatchTeamScorer(userId, matchId, Integer.parseInt(battingTeamIdForSecondInning));
                    if (scorer != null) {
                        try {

                            match.setActiveScorerId(String.valueOf(userId));
                            updateMatchData(match);
                            String inningNumber = "2";
                            String totalOvers = match.getNumberOfOvers();
                            String battingTeamId = battingTeamIdForSecondInning;
                            String bowlingTeamId = bowlingTeamIdForSecondInning;
                            String playing = "1";
                            CricketInning secondInning = new CricketInning(totalOvers, "0", "0", bowlingTeamId, "1", String.valueOf(matchId),
                                    inningNumber, playing, null, "0",
                                    "0", "0", "0", battingTeamId, null);
                            int inningId = insertInningData(secondInning);

                            StartSecondInningResponseModel startSecondInningResponseModel = new StartSecondInningResponseModel();
                            startSecondInningResponseModel.setBattingTeamId(battingTeamId);
                            startSecondInningResponseModel.setBowlingTeamId(bowlingTeamId);
                            startSecondInningResponseModel.setInningId(String.valueOf(inningId));
                            startSecondInningResponseModel.setActiveScorerId(String.valueOf(userId));
                            result.setData(startSecondInningResponseModel);
                            result.setResult("1");
                            result.setMessage("Successfully");
                        } catch (Exception pdot) {
                            result.setData(new StartSecondInningResponseModel());
                            result.setResult("0");
                            result.setMessage(pdot.getMessage());

                        }
                    } else {
                        result.setData(new StartSecondInningResponseModel());
                        result.setResult("0");
                        result.setMessage("you are not authorized scorer for this inning");

                    }
                } else {
                    result.setData(new StartSecondInningResponseModel());
                    result.setResult("0");
                    result.setMessage("First Inning is not Complete");
                }
            } else {
                result.setData(new StartSecondInningResponseModel());
                result.setResult("0");
                result.setMessage("First Inning is not started you can not start second innings");
            }
        } else {
            result.setData(new StartSecondInningResponseModel());
            result.setResult("0");
            result.setMessage("Match not found");
        }
        return result;
    }

    public String SaveToss(JSONObject jsonObject)
    {
        UniverseResponseModel<StartSecondInningResponseModel> tossResultData= new UniverseResponseModel<>();
        try {
            int matchId = jsonObject.getInt("matchId");
            int tossWinnerTeamId = jsonObject.getInt("tossWinnerTeamId");
            String tossSelection = jsonObject.getString("tossSelection");

             Matches match = fetchDBMatchByMatchId(matchId);
             if(match != null)
             {
                int battingTeamId=0;
                 int bowlingTeamId=0;
                 if(tossSelection.equalsIgnoreCase("BATTING"))
                 {
                     battingTeamId=tossWinnerTeamId;
                     bowlingTeamId= Integer.parseInt((match.getTeam1Id().equalsIgnoreCase(String.valueOf(battingTeamId)))?match.getTeam2Id():match.getTeam1Id());
                 }
                 else
                 {
                     battingTeamId=Integer.parseInt((match.getTeam1Id().equalsIgnoreCase(String.valueOf(tossWinnerTeamId)))?match.getTeam2Id():match.getTeam1Id());
                     bowlingTeamId=Integer.parseInt((match.getTeam1Id().equalsIgnoreCase(String.valueOf(battingTeamId)))?match.getTeam2Id():match.getTeam1Id());
                 }
                 if(battingTeamId>0 && bowlingTeamId>0)
                 {
                    match.setTossResultId(String.valueOf(tossWinnerTeamId));
                    match.setTossSelection(tossSelection);
                    match.setMatchStatus("STARTED");
                    match.setActiveScorerId(AppUtils.getUserId(context));
                    updateMatchData(match);

                     CricketInning secondInning = new CricketInning(match.getNumberOfOvers(), "0", "0", String.valueOf(bowlingTeamId), "1", String.valueOf(matchId),
                             "1", "1", null, "0",
                             "0", "0", "0", String.valueOf(battingTeamId), null);
                     int inningId = insertInningData(secondInning);

                     tossResultData.setResult("1");
                     tossResultData.setMessage("Successfully");
                     StartSecondInningResponseModel n= new StartSecondInningResponseModel();
                     n.setActiveScorerId(AppUtils.getUserId(context));
                     n.setBowlingTeamId(String.valueOf(bowlingTeamId));
                     n.setBattingTeamId(String.valueOf(battingTeamId));
                     n.setInningId(String.valueOf(inningId));
                     tossResultData.setData(n);
                 }
                 else
                 {
                     tossResultData.setResult("0");
                     tossResultData.setMessage("Unable to get Batting and Bowling team id");
                 }
             }
             else
             {
                 tossResultData.setResult("0");
                 tossResultData.setMessage("Match Not Found");
             }

             Gson gson= new Gson();
             String d = gson.toJson(tossResultData);
            return  d;

        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
    }

    // Api function for saving ball
    public void SaveBall(JSONObject jsonObject) {

        try {
            int matchId = jsonObject.getInt("matchId");
            int inningId = jsonObject.getInt("inningId");
            int runScored = jsonObject.getInt("runScored");
            String isFour = (jsonObject.getString("isFour").equalsIgnoreCase("true")) ? "1" : "0";
            String isSix = (jsonObject.getString("isSix").equalsIgnoreCase("true")) ? "1" : "0";
            String isNoBall = (jsonObject.getString("isNoBall").equalsIgnoreCase("true")) ? "1" : "0";
            String isWideBall = (jsonObject.getString("isWideBall").equalsIgnoreCase("true")) ? "1" : "0";
            int runScoredOnWideBall = (jsonObject.getString("runScoredOnWideBall") == "") ? 0 : jsonObject.getInt("runScoredOnWideBall");
            int runScoredOnNoBall = (jsonObject.getString("runScoredOnNoBall") == "") ? 0 : jsonObject.getInt("runScoredOnNoBall");
            String isBye = (jsonObject.getString("isBye").equalsIgnoreCase("true")) ? "1" : "0";
            int runScoredOnBye = (jsonObject.getString("runScoredOnBye") == "") ? 0 : jsonObject.getInt("runScoredOnBye");
            String isLegBye = (jsonObject.getString("isLegBye").equalsIgnoreCase("true")) ? "1" : "0";
            int runScoredOnLegBye = (jsonObject.getString("runScoredOnLegBye") == "") ? 0 : jsonObject.getInt("runScoredOnLegBye");
            String isWicket = (jsonObject.getString("isWicket").equalsIgnoreCase("true")) ? "1" : "0";
            String wicketType = jsonObject.getString("wicketType");
            int stumpedById = (jsonObject.getString("stumpedById") == "") ? 0 : jsonObject.getInt("stumpedById");
            int caughtById = (jsonObject.getString("caughtById") == "") ? 0 : jsonObject.getInt("caughtById");
            int runOutById = (jsonObject.getString("runOutById") == "") ? 0 : jsonObject.getInt("runOutById");
            int batsmanId = jsonObject.getInt("batsmanId");
            int nonStrikerbatsmanId = jsonObject.getInt("NonStrikerbatsmanId");
            int bowlerId = jsonObject.getInt("bowlerId");
            int overId = jsonObject.getInt("overId");
            String comments = jsonObject.getString("comments");
            int outBatsmanId = (jsonObject.getString("outBatsmanId") == "") ? 0 : jsonObject.getInt("outBatsmanId");
            String isDeclared = (jsonObject.getString("isDeclared").equalsIgnoreCase("true")) ? "1" : "0";
            int by = (jsonObject.getString("by") == "") ? 0 : jsonObject.getInt("by");
            Matches mm = fetchDBMatchByMatchId(matchId);
            CricketInning cricketInning = fetchInningById(inningId);
            if (overId < 0) {
                int playedOvers = Integer.parseInt(cricketInning.getPlayedOvers());
                int overNumber = playedOvers + 1;
                overId = insertOverData(new CricketOver("1", "0", String.valueOf(overNumber), String.valueOf(matchId), "0", "0", "0", String.valueOf(inningId), String.valueOf(bowlerId)));
            }
            CricketOver cricketOver = fetchOverById(overId);


            CricketScoreCard strikeBatsmanScoreCard = fetchScoreCardOfBatsmanInInning(matchId, inningId, batsmanId);
            CricketScoreCard NonStrikerBatsmanScoreCard = fetchScoreCardOfBatsmanInInning(matchId, inningId, nonStrikerbatsmanId);
            if (strikeBatsmanScoreCard == null) {
                List<CricketScoreCard> inningBatsmen = fetchBatsmenScoreCardOFInning(inningId);
                int order = inningBatsmen.size() + 1;
                int sco = insertScoreCardData(new CricketScoreCard("NOT OUT", "0", "0", "0", "0", String.valueOf(matchId),
                        "0", String.valueOf(order), String.valueOf(inningId), "0", String.valueOf(batsmanId)));
                strikeBatsmanScoreCard = fetchScoreCardById(sco);
            }
            if (NonStrikerBatsmanScoreCard == null) {
                List<CricketScoreCard> inningBatsmen = fetchBatsmenScoreCardOFInning(inningId);
                int order = inningBatsmen.size() + 1;
                int sco = insertScoreCardData(new CricketScoreCard("NOT OUT", "0", "0", "0", "0", String.valueOf(matchId),
                        "0", String.valueOf(order), String.valueOf(inningId), "0", String.valueOf(nonStrikerbatsmanId)));
                NonStrikerBatsmanScoreCard = fetchScoreCardById(sco);
            }
            int extraRuns = 0;
            List<CricketScoreCard> inningBatsmen = fetchBatsmenScoreCardOFInning(inningId);
            CricketBall previousBall = fetchLatestBallOfInning(matchId, inningId);
            if (isNoBall.equalsIgnoreCase("1")) {
                extraRuns += 1;
                runScored += runScoredOnNoBall;
            }

            if (isWideBall.equalsIgnoreCase("1")) {
                extraRuns += 1;
                extraRuns += runScoredOnWideBall;
            }

            if (isBye.equalsIgnoreCase("1")) {
                extraRuns += runScoredOnBye;
            }

            if (isLegBye.equalsIgnoreCase("1")) {
                extraRuns += runScoredOnLegBye;
            }
            runScored = runScored + extraRuns;

            Float inningOverCount = Float.parseFloat(cricketOver.getOverNumber()) - 1;

            int ballCountOver = 0;
            if (previousBall != null) {
                if (previousBall.getOverId().equalsIgnoreCase(String.valueOf(overId))) {
                    inningOverCount = Float.parseFloat(previousBall.getInningOverCount());
                    ballCountOver = Integer.parseInt(previousBall.getBallCountInOver());
                    if (previousBall.isNoBall().equalsIgnoreCase("1") || previousBall.isWideBall().equalsIgnoreCase("1")) {
                        ballCountOver += 1;
                    } else {
                        ballCountOver += 1;
                        inningOverCount += 0.1f;
                    }
                } else {
                    ballCountOver = 1;
                    inningOverCount += 0.1f;
                }
            } else {
                inningOverCount = 0.1f;
                ballCountOver = 1;
            }

            inningOverCount = Float.parseFloat(String.format(
                    "%.1f", inningOverCount));

            String iinningOverCount;
            if (((inningOverCount * 10) % 10) > 0) {
                iinningOverCount = String.valueOf(inningOverCount);
            } else {
                iinningOverCount = String.valueOf(Math.round(inningOverCount / 1));
            }

            int newBallInsertedId = insertBallData(new CricketBall(String.valueOf(ballCountOver), iinningOverCount, String.valueOf(runScored), String.valueOf(extraRuns)
                    , isFour, isSix, String.valueOf(runScoredOnNoBall), isNoBall, isWideBall,
                    String.valueOf(runScoredOnWideBall), isBye, String.valueOf(runScoredOnBye), isLegBye, String.valueOf(runScoredOnLegBye), isWicket, wicketType, comments, String.valueOf(batsmanId), String.valueOf(bowlerId)
                    , String.valueOf(inningId), String.valueOf(overId), String.valueOf(matchId), String.valueOf(caughtById), String.valueOf(runOutById), String.valueOf(stumpedById), String.valueOf(outBatsmanId)));
            CricketBall newBallInserted = fetchBallById(newBallInsertedId);
            insertBallDataLocal(jsonObject.toString(),newBallInsertedId);
            int strikeBatsmanRuns = Integer.parseInt(strikeBatsmanScoreCard.getRuns());
            int strikeBatsmanBalls = Integer.parseInt(strikeBatsmanScoreCard.getBalls());
            int strikeBatsmanFours = Integer.parseInt(strikeBatsmanScoreCard.getFours());
            int strikeBatsmanSixes = Integer.parseInt(strikeBatsmanScoreCard.getSixes());
            String strikeBatsmanStatus = strikeBatsmanScoreCard.getStatus();
            String strikeBatsmanStrikeRate;
            strikeBatsmanRuns += Integer.parseInt(newBallInserted.getRunScored());
            strikeBatsmanBalls += 1;

            if (newBallInserted.isNoBall().equalsIgnoreCase("1") && Integer.parseInt(newBallInserted.getRunScored()) >= 1) {
                strikeBatsmanRuns -= 1;
            }
            if (newBallInserted.isWideBall().equalsIgnoreCase("1")) {
                int runtosub = 0;
                if (newBallInserted.isBye().equalsIgnoreCase("1")) {
                    runtosub = (Integer.parseInt(newBallInserted.getExtraRuns()) - Integer.parseInt(newBallInserted.getRunScoredOnBye()));
                } else {
                    runtosub = (Integer.parseInt(newBallInserted.getExtraRuns()));
                }
                strikeBatsmanRuns -= runtosub;
                strikeBatsmanBalls -= 1;
            }

            if (newBallInserted.isLegBye().equalsIgnoreCase("1")) {
                strikeBatsmanRuns -= Integer.parseInt(newBallInserted.getRunScoredOnLegBye());
            }

            if (newBallInserted.isBye().equalsIgnoreCase("1")) {
                strikeBatsmanRuns -= Integer.parseInt(newBallInserted.getRunScoredOnBye());
            }

            if (newBallInserted.isFour().equalsIgnoreCase("1")) {
                if (newBallInserted.isWideBall().equalsIgnoreCase("0") && newBallInserted.isLegBye().equalsIgnoreCase("0") && newBallInserted.isBye().equalsIgnoreCase("0")) {
                    strikeBatsmanFours += 1;
                }
            }
            if (newBallInserted.isSix().equalsIgnoreCase("1")) {
                strikeBatsmanSixes += 1;
            }
            if (newBallInserted.isWicket().equalsIgnoreCase("1")) {

                if (newBallInserted.getOutBatsmanId().equalsIgnoreCase(newBallInserted.getBatsmanId())) {
                    strikeBatsmanStatus = newBallInserted.getWicketType().toUpperCase();
                } else {
                    NonStrikerBatsmanScoreCard.setStatus(newBallInserted.getWicketType().toUpperCase());
                    updateScoreCardData(NonStrikerBatsmanScoreCard);
                }
            }
            if (strikeBatsmanBalls > 0) {
                float v = Float.parseFloat(String.valueOf(strikeBatsmanRuns)) / Float.parseFloat(String.valueOf(strikeBatsmanBalls));
                v = v * 100;
                strikeBatsmanStrikeRate = String.valueOf(v);
            } else {
                strikeBatsmanStrikeRate = "0";
            }

            strikeBatsmanScoreCard.setRuns(String.valueOf(strikeBatsmanRuns));
            strikeBatsmanScoreCard.setStatus(String.valueOf(strikeBatsmanStatus));
            strikeBatsmanScoreCard.setBalls(String.valueOf(strikeBatsmanBalls));
            strikeBatsmanScoreCard.setFours(String.valueOf(strikeBatsmanFours));
            strikeBatsmanScoreCard.setSixes(String.valueOf(strikeBatsmanSixes));
            strikeBatsmanScoreCard.setStrikeRate(strikeBatsmanStrikeRate);
            updateScoreCardData(strikeBatsmanScoreCard);

            //update over

            cricketOver.setRunsScoredInOver(String.valueOf(Integer.parseInt(cricketOver.getRunsScoredInOver()) + runScored));

            int numberOfPlayers = Integer.parseInt(mm.getNumberOfPlayers());

            int totalRunsInInning = (Integer.parseInt(cricketInning.getTotalRunsScored()) + runScored);
            int totalExtrasInInning = (Integer.parseInt(cricketInning.getExtras()) + extraRuns);
            int totalWickets = Integer.parseInt(cricketInning.getWickets());
            if (newBallInserted.isWicket().equalsIgnoreCase("1")) {
                cricketOver.setWicketsInOver(String.valueOf(Integer.parseInt(cricketOver.getWicketsInOver()) + 1));
                ;
                totalWickets = totalWickets + 1;
            }
            cricketOver.setExtraRunsInOver(String.valueOf(Integer.parseInt(cricketOver.getExtraRunsInOver()) + extraRuns));
            if (Integer.parseInt(cricketOver.getRunsScoredInOver()) == 0 && (((inningOverCount * 10) % 10) == 6)) {
                cricketOver.setIsMaiden("1");
            } else {
                cricketOver.setIsMaiden("0");
            }

            updateOverData(cricketOver);


            Float playedOvers = 0f;
            if (isNoBall.equalsIgnoreCase("1") || isWideBall.equalsIgnoreCase("1")) {
                playedOvers = Float.parseFloat(cricketInning.getPlayedOvers());
            } else {

                if (((inningOverCount * 10) % 10) == 6) {
                    double j = Math.ceil(inningOverCount);
                    int h = (int) j;
                    playedOvers = Float.valueOf(h);
                } else {
                    playedOvers = inningOverCount;
                }
            }

            String playedOverss;
            if (playedOvers % 1 == 0) {
                playedOverss = String.valueOf(Math.round(playedOvers));
            } else {
                playedOverss = String.valueOf(playedOvers);
            }


            String isPlaying = (playedOverss.equalsIgnoreCase(cricketInning.getTotalOvers()) || (totalWickets == (numberOfPlayers - 1))) ? "0" : "1";

            cricketInning.setTotalRunsScored(String.valueOf(totalRunsInInning));
            cricketInning.setExtras(String.valueOf(totalExtrasInInning));
            cricketInning.setPlaying(isPlaying);
            cricketInning.setWickets(String.valueOf(totalWickets));
            cricketInning.setPlayedOvers(playedOverss);

            updateInningData(cricketInning);

            updateInningData(cricketInning);
            List<Inning> inningsForThisMatch = fetchInningsOfMatch(matchId);
            String tie = "";
            String matchWinner = "";

            if (inningsForThisMatch.size() > 1) {
                if ((Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored()) == Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored())) && (totalWickets == (numberOfPlayers - 1) || playedOverss == cricketInning.getTotalOvers())) {
                    tie = "YES";
                } else if ((Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored()) < Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored()))) {
                    matchWinner = inningsForThisMatch.get(1).getBattingTeamId();
                } else if ((Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored()) < Integer.parseInt(inningsForThisMatch.get(0).getTotalRunsScored())) && (totalWickets == (numberOfPlayers - 1) || playedOverss == cricketInning.getTotalOvers())) {
                    matchWinner = inningsForThisMatch.get(0).getBattingTeamId();
                }
            }

            if (playedOverss == cricketInning.getTotalOvers() || totalWickets == (numberOfPlayers - 1) || !tie.equalsIgnoreCase("") || !matchWinner.equalsIgnoreCase("")) {
                //if match has ended with this inning
                if (Integer.parseInt(cricketInning.getInningNumber()) == 2) {
                    //assuming its a one day match, end this inning and update match status

                    mm.setTie(tie);
                    mm.setMatchResultId(matchWinner);
                    updateMatchData(mm);

                    cricketInning.setPlaying("0");
                    updateInningData(cricketInning);
                    //update tournaments details

                /*$tournamentMatchQuery = $this->db->prepare("SELECT * from tournamentfixtures where matchId=:matchId");
                $tournamentMatchQuery->execute([
                        ":matchId"=>$match['id']
                                ]);

                if ($tournamentMatchQuery->rowCount()>0) {

                    $tournamentMatch = $tournamentMatchQuery->fetch();

                    if($matchWinner != null && $tie === null)
                    {
                        $matchLoser=($tournamentMatch['team1Id']==$matchWinner)?$tournamentMatch['team2Id']:$tournamentMatch['team1Id'];

                        $pointsMatchWinnerQuery = $this->db->prepare("SELECT tgt.*, t.winPoint,t.losePoint,t.drawPoint  from tournamentgroupsteam tgt inner join tournament t on tgt.tournamentId=t.id  where tgt.tournamentId=:tournamentId and tgt.teamId=:teamId and tgt.groupId=:groupId");
                        $pointsMatchWinnerQuery->execute([
                                ":tournamentId"=>$tournamentMatch['tournamentId'],
                                ":groupId"=>$tournamentMatch['groupId'],
                            ":teamId"=>$matchWinner
                                        ]);

                        if ($pointsMatchWinnerQuery->rowCount()>0) {
                            $pointsRow = $pointsMatchWinnerQuery->fetch();

                            $updateMatchWinnerPointQuery=$this->db->prepare("UPDATE tournamentgroupsteam tgt set tgt.won = :won, tgt.matches = :matches, tgt.points=:points where tgt.id = :id");
                            $updateMatchWinnerPointQuery->execute(
                                    [
                                    ":matches"=>($pointsRow['matches']+1),
                                    ":won"=>($pointsRow['won']+1),
                                    ":points"=>($pointsRow['points']+$pointsRow['winPoint']),
                                    ":id"=>$pointsRow['id']
                                            ]);

                        }

                        $pointsMatchLoserQuery = $this->db->prepare("SELECT tgt.*, t.winPoint,t.losePoint,t.drawPoint  from tournamentgroupsteam tgt inner join tournament t on tgt.tournamentId=t.id  where tgt.tournamentId=:tournamentId and tgt.teamId=:teamId and tgt.groupId=:groupId");
                        $pointsMatchLoserQuery->execute([
                                ":tournamentId"=>$tournamentMatch['tournamentId'],
                                ":groupId"=>$tournamentMatch['groupId'],
                            ":teamId"=>$matchLoser
                                        ]);

                        if ($pointsMatchLoserQuery->rowCount()>0) {
                            $pointsloserRow = $pointsMatchLoserQuery->fetch();

                            $updateMatchLoserPointQuery=$this->db->prepare("UPDATE tournamentgroupsteam tgt set tgt.lost = :lost, tgt.matches = :matches, tgt.points=:points where tgt.id = :id");
                            $updateMatchLoserPointQuery->execute(
                                    [

                                    ":matches"=>($pointsloserRow['matches']+1),
                                    ":lost"=>($pointsloserRow['lost']+1),
                                    ":points"=>($pointsloserRow['points']+$pointsloserRow['losePoint']),
                                    ":id"=>$pointsloserRow['id']
                                            ]);

                        }
                    }else{

                    }

                }*/
                } else {
                    mm.setActiveScorerId(null);
                    updateMatchData(mm);
                }
            }
        } catch (Exception ex) {
            Log.e("dd", ex.getMessage());

        }
    }

    // Api function for getmatchstaticsDetails
    public String getMatchStatisticsDetails(int eventId) {
        ResponseModel result = new ResponseModel();

        Match match = fetchMatchByEventId(eventId);

        String wonString = "";
        if (match != null) {
            String tossString = "";
            String matchStatus = match.getMatchStatus();
            String tossResultId = match.getTossResultId();
            String team1Id = match.getTeam1Id();
            String team2Id = match.getTeam2Id();

            result.setAllowedToEdit("1");

            if ((matchStatus.equalsIgnoreCase("STARTED") || matchStatus.equalsIgnoreCase("ENDED")) && Integer.parseInt(tossResultId) > 0) {
                if (Integer.parseInt(tossResultId) == Integer.parseInt(team1Id)) {
                    tossString = match.getTeam1Name() + " won the toss & elected to " + (match.getTossSelection().equalsIgnoreCase("BATTING") ? "bat" : "bowl");
                } else if (Integer.parseInt(tossResultId) == Integer.parseInt(team2Id)) {
                    tossString = match.getTeam2Name() + " won the toss & elected to " + (match.getTossSelection().equalsIgnoreCase("BATTING") ? "bat" : "bowl");
                }
            }
            if (matchStatus.equalsIgnoreCase("ENDED") && match.getTie().equalsIgnoreCase("YES")) {
                wonString = "Match Tied";
            }
            match.setDescription((match.getDescription() == null) ? "" : match.getDescription());
            match.setLocation((match.getLocation() == null) ? "" : match.getLocation());
            match.setMatchDate((match.getMatchDate() == null) ? "" : match.getMatchDate());
            match.setTie((match.getTie() == null) ? "" : match.getTie());
            match.setTossSelection((match.getTossSelection() == null) ? "" : match.getTossSelection());
            match.setMatchType((match.getMatchDate() == null) ? "" : match.getMatchDate());
            match.setMatchStatus((match.getMatchStatus() == null) ? "" : match.getMatchStatus());
            match.setNumberOfInnings((match.getNumberOfInnings() == null) ? "" : match.getNumberOfInnings());
            match.setInviteStatus((match.getInviteStatus() == null) ? "" : match.getInviteStatus());
            match.setTeam1Id((match.getTeam1Id() == null) ? "" : match.getTeam1Id());
            match.setTeam1InningId((match.getTeam1InningId() == null) ? "" : match.getTeam1InningId());
            match.setTeam1Name((match.getTeam1Name() == null) ? "" : match.getTeam1Name());
            match.setTeam1ProfilePic((match.getTeam1ProfilePic() == null) ? "" : match.getTeam1ProfilePic());
            match.setTeam2Id((match.getTeam2Id() == null) ? "" : match.getTeam2Id());
            match.setTeam2InningId((match.getTeam2InningId() == null) ? "" : match.getTeam2InningId());
            match.setTeam2Name((match.getTeam2Name() == null) ? "" : match.getTeam2Name());
            match.setTeam2ProfilePic((match.getTeam2ProfilePic() == null) ? "" : match.getTeam2ProfilePic());
            match.setTournamentId((match.getTournamentId() == null) ? "" : match.getTournamentId());
            match.setMatchResultId((match.getMatchResultId() == null) ? "" : match.getMatchResultId());
            match.setEventId((match.getEventId() == null) ? "" : match.getEventId());
            match.setActiveScorerId((match.getActiveScorerId() == null) ? "" : match.getActiveScorerId());
            match.setNumberOfPlayers((match.getNumberOfPlayers() == null) ? "" : match.getNumberOfPlayers());
            match.setNumberOfOvers((match.getNumberOfOvers() == null) ? "" : match.getNumberOfOvers());
            match.setIsTeam1ScoringOnSf((match.getIsTeam1ScoringOnSf() == null) ? "" : match.getIsTeam1ScoringOnSf());
            match.setIsTeam2ScoringOnSf((match.getIsTeam2ScoringOnSf() == null) ? "" : match.getIsTeam2ScoringOnSf());
            match.setTeam1AvatarId((match.getTeam1AvatarId() == null) ? "" : match.getTeam1AvatarId());
            match.setTeam2AvatarId((match.getTeam2AvatarId() == null) ? "" : match.getTeam2AvatarId());
            match.setTournamentName((match.getTournamentName() == null) ? "" : match.getTournamentName());
            match.setMatchTile((match.getMatchTile() == null) ? "" : match.getMatchTile());
            match.setWonString(wonString);
            match.setTossString(tossString);
            match.setTeam1Scorer((match.getTeam1Scorer() == null) ? "" : match.getTeam1Scorer());
            match.setTeam2Scorer((match.getTeam2Scorer() == null) ? "" : match.getTeam2Scorer());
            match.setTeam1ScoreString((match.getTeam1ScoreString() == null) ? "" : match.getTeam1ScoreString());
            match.setTeam2ScoreString((match.getTeam2ScoreString() == null) ? "" : match.getTeam2ScoreString());
            match.setMatchScheduleString((match.getMatchScheduleString() == null) ? "" : match.getMatchScheduleString());
            match.setSocialSharingString((match.getSocialSharingString() == null) ? "" : match.getSocialSharingString());
            match.setInningsPlayStatusString((match.getInningsPlayStatusString() == null) ? "" : match.getInningsPlayStatusString());
            match.setRunInBall((match.getRunInBall() == null) ? "" : match.getRunInBall());
            match.setRunInOver((match.getRunInOver() == null) ? "" : match.getRunInOver());
            match.setTeam1StatusPic((match.getTeam1StatusPic() == null) ? "" : match.getTeam1StatusPic());
            match.setTeam2StatusPic((match.getTeam2StatusPic() == null) ? "" : match.getTeam2StatusPic());
            match.setRunsRequired((match.getRunsRequired() == null) ? "" : match.getRunsRequired());
            match.setBallsRemaining((match.getBallsRemaining() == null) ? "" : match.getBallsRemaining());
            match.setRequiredRunRate((match.getRequiredRunRate() == null) ? "" : match.getRequiredRunRate());
            match.setMatchDate1((match.getMatchDate1() == null) ? new MatchDate() : match.getMatchDate1());
            result.setData(new MatchStaticsData());
            result.getData().setMatch(match);
            if (matchStatus.equalsIgnoreCase("ENDED") && Integer.parseInt(tossResultId) > 0) {
                result.getData().getMatch().setMatchScheduleString("Match was played on " + result.getData().getMatch().getMatchDate1().getMonthName() + " " + result.getData().getMatch().getMatchDate1().getDate() + " at " + result.getData().getMatch().getMatchDate1().getTime());
            }
            if (matchStatus.equalsIgnoreCase("ENDED") && Integer.parseInt(tossResultId) == 0) {
                result.getData().getMatch().setMatchScheduleString("Match was scheduled to start on " + result.getData().getMatch().getMatchDate1().getMonthName() + " " + result.getData().getMatch().getMatchDate1().getDate() + " at " + result.getData().getMatch().getMatchDate1().getTime());
            }
            // TODO: 1/29/2018 setmatch date in match
            if (matchStatus.equalsIgnoreCase("STARTED")) {
                result.getData().getMatch().setMatchScheduleString("Match started at " + result.getData().getMatch().getMatchDate1().getTime() + " on " + result.getData().getMatch().getMatchDate1().getMonthName() + " " + result.getData().getMatch().getMatchDate1().getDate());
            }
            if (matchStatus.equalsIgnoreCase("NOT STARTED")) {
                result.getData().getMatch().setMatchScheduleString("Match scheduled to begin at " + result.getData().getMatch().getMatchDate1().getTime() + " on " + result.getData().getMatch().getMatchDate1().getMonthName() + " " + result.getData().getMatch().getMatchDate1().getDate());
            }

            List<TeamScorer> team1Scorer = new ArrayList<>();
            List<TeamScorer> team2Scorer = new ArrayList<>();

            team1Scorer = fetchTeamScorer(Integer.parseInt(team1Id), result.getData().getMatch().getId());
            team2Scorer = fetchTeamScorer(Integer.parseInt(team2Id), result.getData().getMatch().getId());


            Scorers scorers = new Scorers();
            scorers.setTeam1Scorer(team1Scorer);
            scorers.setTeam2Scorer(team2Scorer);

            result.getData().setScorers(scorers);
            String t1Scorerstring = "";
            for (int i = 0; i < team1Scorer.size(); i++) {
                if (i < (team1Scorer.size() - 1)) {
                    t1Scorerstring = team1Scorer.get(i).getScorerName() + ",";
                } else {
                    t1Scorerstring = team1Scorer.get(i).getScorerName();
                }
            }
            result.getData().getMatch().setTeam1Scorer(t1Scorerstring);

            String t2Scorerstring = "";
            for (int i = 0; i < team2Scorer.size(); i++) {
                if (i < (team2Scorer.size() - 1)) {
                    t2Scorerstring = team2Scorer.get(i).getScorerName() + ",";
                } else {
                    t2Scorerstring = team2Scorer.get(i).getScorerName();
                }
            }
            result.getData().getMatch().setTeam2Scorer(t2Scorerstring);

            result.getData().getMatch().setTeam1Scorer(t1Scorerstring);
            result.getData().getMatch().setTeam2Scorer(t2Scorerstring);

                       /* Inning firstInningDetails= fetchInningByInningNumber(1,Integer.parseInt(result.getData().getMatch().getId()));
                        Inning secondInningDetails= fetchInningByInningNumber(2,Integer.parseInt(result.getData().getMatch().getId()));
*/
            Team team1Details = fetchTeamDetail(Integer.parseInt(team1Id));

            Team t1 = new Team();
            t1.setName(team1Details.getName());
            t1.setProfilePicture(team1Details.getProfilePicture());
            t1.setTeamAvatarId(team1Details.getTeamAvatarId());
            t1.setTeamId(team1Details.getTeamId());
            t1.setIsTeamScoringOnSf(team1Details.getTeamId().equalsIgnoreCase(result.getData().getMatch().getTeam1Id()) ? result.getData().getMatch().getIsTeam1ScoringOnSf() : result.getData().getMatch().getIsTeam2ScoringOnSf());
            result.getData().setTeam1(t1);


            //get Team One Squad
            result.getData().setTeam1Squad(fetchTeamSquad(Integer.parseInt(match.getTeam1Id()), match.getId()));


            Team team2Details = fetchTeamDetail(Integer.parseInt(team2Id));

            Team t2 = new Team();
            t2.setName(team2Details.getName());
            t2.setProfilePicture(team2Details.getProfilePicture());
            t2.setTeamAvatarId(team2Details.getTeamAvatarId());
            t2.setTeamId(team2Details.getTeamId());
            t2.setIsTeamScoringOnSf(team2Details.getTeamId().equalsIgnoreCase(result.getData().getMatch().getTeam1Id()) ? result.getData().getMatch().getIsTeam1ScoringOnSf() : result.getData().getMatch().getIsTeam2ScoringOnSf());
            result.getData().setTeam2(t2);


            //get Team two Squad
            result.getData().setTeam2Squad(fetchTeamSquad(Integer.parseInt(match.getTeam2Id()), match.getId()));

            // check if team scoring 0 then send ghost players
            if ((result.getData().getMatch().getIsTeam1ScoringOnSf().equalsIgnoreCase("0") && result.getData().getMatch().getIsTeam2ScoringOnSf().equalsIgnoreCase("0")) || (result.getData().getMatch().getIsTeam1ScoringOnSf().equalsIgnoreCase("1") && result.getData().getMatch().getIsTeam2ScoringOnSf().equalsIgnoreCase("1"))) {

            } else if (result.getData().getMatch().getIsTeam1ScoringOnSf().equalsIgnoreCase("1") && result.getData().getMatch().getIsTeam2ScoringOnSf().equalsIgnoreCase("0")) {
                if (Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) > 0 && Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) <= 11) {
                    result.getData().setTeam2Squad(fetchGhostPlayers(Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()), Integer.parseInt(result.getData().getMatch().getTeam2Id()), result.getData().getMatch().getId()));
                }
            } else if (result.getData().getMatch().getIsTeam1ScoringOnSf().equalsIgnoreCase("0") && result.getData().getMatch().getIsTeam2ScoringOnSf().equalsIgnoreCase("1")) {
                if (Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) > 0 && Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) <= 11) {
                    result.getData().setTeam1Squad(fetchGhostPlayers(Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()), Integer.parseInt(result.getData().getMatch().getTeam1Id()), result.getData().getMatch().getId()));
                }
            }

            List<Inning> matchInnings = fetchInningsOfMatch(match.getId());

            //inning(s) found
            List<Inning> innings = new ArrayList<>();
            //$Oldinnings = [];
            for (Inning inning : matchInnings) {
                List<CricketScoreCard> batsmen = fetchNotOutBatsmenScoreCardOFInning(inning.getId());
                //get Batsmen On Strike for next Ball
                int batsmanOnStrike = -1;
                int batsmanOnNonStrike = -1;
                CricketBall lastBall = fetchLatestBallOfInning(result.getData().getMatch().getId(), inning.getId());


                int currentBowlerId = -1;
                int previousBowlerId = -3;
                int currentOverId = (inning.getCurrentOverId() == null || inning.getCurrentOverId().isEmpty()) ? -1 : Integer.parseInt(inning.getCurrentOverId());

                if (lastBall != null) {
                    int runScoredOnBall = Integer.parseInt(lastBall.getRunScored());
                    // echo($runScoredOnBall);
                    if (lastBall.isNoBall().equalsIgnoreCase("1") || lastBall.isWideBall().equalsIgnoreCase("1")) {
                        runScoredOnBall = runScoredOnBall - 1;
                    }
                    if (runScoredOnBall % 2 == 0) {
                        for (CricketScoreCard b : batsmen) {
                            if (b.getBatsmanId().equalsIgnoreCase(lastBall.getBatsmanId())) {
                                batsmanOnStrike = Integer.parseInt(b.getBatsmanId());
                            } else {
                                batsmanOnNonStrike = Integer.parseInt(b.getBatsmanId());
                            }
                        }
                    } else {
                        for (CricketScoreCard b : batsmen) {
                            if (!b.getBatsmanId().equalsIgnoreCase(lastBall.getBatsmanId())) {
                                batsmanOnStrike = Integer.parseInt(b.getBatsmanId());
                            } else {
                                batsmanOnNonStrike = Integer.parseInt(b.getBatsmanId());
                            }
                        }
                    }
                    Float playedOvers = Float.parseFloat(lastBall.getInningOverCount());
                    //$stringBowledOver =(string)$playedOvers;
                    //$splitedArr=explode('.',$stringBowledOver);

                    int ballNo = (int) ((playedOvers * 10) % 10);
                    if (lastBall.isNoBall().equalsIgnoreCase("1") || lastBall.isWideBall().equalsIgnoreCase("1")) {
                        ballNo = ballNo - 1;
                    }
                    if (ballNo == 6) {
                        int j = batsmanOnStrike;
                        batsmanOnStrike = batsmanOnNonStrike;
                        batsmanOnNonStrike = j;
                        currentBowlerId = -1;
                        currentOverId = -1;
                    } else {
                        currentBowlerId = Integer.parseInt(lastBall.getBowlerId());
                    }
                    previousBowlerId = Integer.parseInt(lastBall.getBowlerId());
                }

                //recent balls

                List<OverBall> bowlingStats = fetchOverBallById(inning.getId(), result.getData().getMatch().getId(), currentOverId);
                inning.setOverBalls(bowlingStats);

                inning.setCurrentOverId(String.valueOf(currentOverId));
                inning.setBatsmanOnStrike(String.valueOf(batsmanOnStrike));
                inning.setBatsmanOnNonStrike(String.valueOf(batsmanOnNonStrike));
                inning.setPreviousBowlerId(String.valueOf(previousBowlerId));
                inning.setCurrentBowlerId(String.valueOf(currentBowlerId));

                String inningScoreString = "";
                if (inning.getWickets().equalsIgnoreCase(String.valueOf(Integer.parseInt(match.getNumberOfPlayers()) - 1))) {
                    inningScoreString = inning.getTotalRunsScored() + " All out (" + inning.getPlayedOvers() + " ov)";
                } else {
                    inningScoreString = inning.getTotalRunsScored() + "/" + inning.getWickets() + " (" + inning.getPlayedOvers() + " ov)";
                }
                inning.setInningScoreString(inningScoreString);


                Float playedOvers = Float.parseFloat(inning.getPlayedOvers());
                int ballNo = (int) (playedOvers % 1);
                        /*    String playOverFinal="";
                            if(lastBall!=null) {
                                if (ballNo == 6) {
                                    playOverFinal = String.valueOf((int) (Math.ceil(Float.parseFloat(lastBall.getInningOverCount()))));//     ceil($playOver['inningOverCount']);
                                } else {
                                    playOverFinal = lastBall.getInningOverCount();
                                }
                            }else
                            {
                                playOverFinal="0";
                            }
*/
                inning.setWicketFallNumber(String.valueOf(inning.getWickets()));
                float playedInningOvers = Float.parseFloat(inning.getPlayedOvers());
                int fullOverPlayed = (int) (playedInningOvers / 1);
                int ballsextraplayerd = (int) (playedInningOvers % 1);
                float totalOversplayes = fullOverPlayed + (ballsextraplayerd / 6);
                if (totalOversplayes > 0) {
                    float overrate = Integer.parseInt(inning.getTotalRunsScored()) / totalOversplayes;
                    inning.setOverRate(String.valueOf(overrate));
                } else {
                    inning.setOverRate("0");
                }

                //batting stats data
                List<BattingStatViewModel> stats = fetchBattingStats(inning.getId());

                List<BattingStat> bating = new ArrayList<>();
                for (BattingStatViewModel s : stats) {
                    String bowlerStringName1 = getGhostPlayerName(Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId(), Integer.parseInt(inning.getBowlingTeamId()));
                    String caughtByStringName1 = getGhostPlayerName(Integer.parseInt(s.getCaughtById()), result.getData().getMatch().getId(), Integer.parseInt(inning.getBowlingTeamId()));
                    String runOutByStringName1 = getGhostPlayerName(Integer.parseInt(s.getRunOutById()), result.getData().getMatch().getId(), Integer.parseInt(inning.getBowlingTeamId()));
                    String stumpedByStringName1 = getGhostPlayerName(Integer.parseInt(s.getStumpedById()), result.getData().getMatch().getId(), Integer.parseInt(inning.getBowlingTeamId()));

                    String bowlerStringName = (bowlerStringName1.equalsIgnoreCase("")) ? s.getBowlerUserFullName() : bowlerStringName1;
                    String caughtByStringName = (caughtByStringName1.equalsIgnoreCase("")) ? s.getCaughtByUserFullName() : caughtByStringName1;
                    String runOutByStringName = (runOutByStringName1.equalsIgnoreCase("")) ? s.getRunoutByUserFullName() : runOutByStringName1;
                    String stumpedByStringName = (stumpedByStringName1.equalsIgnoreCase("")) ? s.getStumpedByUserFullName() : stumpedByStringName1;
                    String outstring = "";
                    if (s.getStatus().equalsIgnoreCase("BOWLED")) {
                        outstring = "b " + bowlerStringName;
                    }
                    if (s.getStatus().equalsIgnoreCase("NOT OUT")) {
                        outstring = "NOT OUT";
                    }
                    if (s.getStatus().equalsIgnoreCase("CATCH OUT")) {
                        if (s.getBowlerUserFullName().equalsIgnoreCase(s.getCaughtByUserFullName()) || s.getStatus().equalsIgnoreCase("CAUGHT BEHIND")) {
                            outstring = "c&b " + bowlerStringName;
                        } else {
                            outstring = "c " + caughtByStringName + " b " + bowlerStringName;
                        }
                    }
                    if (s.getStatus().equalsIgnoreCase("STUMPED")) {
                        outstring = "st " + stumpedByStringName + " b " + bowlerStringName;
                    }
                    if (s.getStatus().equalsIgnoreCase("RUN OUT")) {
                        outstring = "runOut " + runOutByStringName;
                    }
                    if (s.getStatus().equalsIgnoreCase("LBW")) {
                        outstring = "lbw " + bowlerStringName;
                    }
                    if (s.getStatus().equalsIgnoreCase("HIT WICKET")) {
                        outstring = "Hit Wicket " + bowlerStringName;
                    }
                    if (s.getStatus().equalsIgnoreCase("HITBALL TWICE")) {
                        outstring = "Hit Ball Twice";
                    }
                    if (s.getStatus().equalsIgnoreCase("HANDLED BALL")) {
                        outstring = "Handled Ball";
                    }
                    if (s.getStatus().equalsIgnoreCase("OBSTRUCT TO FIELD")) {
                        outstring = "Obs. To Field";
                    }
                    if (s.getStatus().equalsIgnoreCase("TIME OUT")) {
                        outstring = "Time Out";
                    }
                    if (s.getStatus().equalsIgnoreCase("RETIRED OUT")) {
                        outstring = "Retired Out";
                    }
                    if (s.getStatus().equalsIgnoreCase("RETIRED HURT")) {
                        outstring = "Retired Hurt";
                    }
                    int dotballCount = fetchDotBallOfBatsman(Integer.parseInt(s.getBatsmanId()), result.getData().getMatch().getId(), inning.getId());
                    BattingStat d = new BattingStat();
                    d.setPlayOrder(s.getPlayOrder());
                    d.setBatsmanAvatarName(s.getBatsmanAvatarName());
                    d.setStatus(s.getStatus());
                    d.setOnStrike(s.getOnStrike());
                    d.setRuns(s.getRuns());
                    d.setDotball(String.valueOf(dotballCount));
                    d.setBalls(s.getBalls());
                    d.setFours(s.getFours());
                    d.setSixes(s.getSixes());
                    d.setStrikeRate(s.getStrikeRate());
                    d.setBatsmanId(s.getBatsmanId());
                    d.setOutString(outstring);
                    bating.add(d);
                }

                inning.setBattingStats(bating);


                //bowling stats
                List<BowlingViewModal> bowlingViewModal = fetchBowlingViewModal(inning.getId());
                List<BowlingStat> bowling = new ArrayList<>();
                List<CricketBall> cricketBalls = new ArrayList<>();
                for (BowlingViewModal s : bowlingViewModal) {
                    int bowllerRuns = 0;
                    bowllerRuns = Integer.parseInt(s.getRuns());
                    int noBall = noBallCount(inning.getId(), Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId());
                    int wideBall = wideBallCount(inning.getId(), Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId());
                    int dotBall = dotBallCount(inning.getId(), Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId());

                    cricketBalls = legbyeOrByeBalls(inning.getId(), Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId());
                    for (CricketBall crb : cricketBalls) {
                        if (crb.isBye().equalsIgnoreCase("1")) {
                            bowllerRuns = bowllerRuns - Integer.parseInt(crb.getRunScoredOnBye());
                        }
                        if (crb.isLegBye().equalsIgnoreCase("1")) {
                            bowllerRuns = bowllerRuns - Integer.parseInt(crb.getRunScoredOnLegBye());
                        }
                    }
                    Float bowllerPlayedOvers = Float.parseFloat(s.getNumberOfOvers());
                    int overNo = (int) (bowllerPlayedOvers / 1);
                    int overBallNo = (int) ((bowllerPlayedOvers * 10) % 10);
                    float eco = 0;
                    float kk = overBallNo / 6.0f;
                    float totalBowllerOversplayes = Float.parseFloat(String.valueOf(overNo)) + kk;
                    if (totalBowllerOversplayes > 0) {
                        eco = bowllerRuns / totalBowllerOversplayes;
                    } else {
                        eco = 0;
                    }
                    String bowllerPlayedOversString = "";
                    if (overBallNo == 0) {
                        bowllerPlayedOversString = String.valueOf(Integer.parseInt(s.getNumberOfOvers()));
                    } else {
                        bowllerPlayedOversString = s.getNumberOfOvers();
                    }
                    String bowlerStringName1 = getGhostPlayerName(Integer.parseInt(s.getBowlerId()), result.getData().getMatch().getId(), Integer.parseInt(inning.getBowlingTeamId()));
                    String bowlerStringName = (bowlerStringName1.equalsIgnoreCase("")) ? s.getBowlerAvatarName() : bowlerStringName1;
                    BowlingStat d = new BowlingStat();

                    d.setMaiden(s.getMaiden());
                    d.setNumberOfOvers(bowllerPlayedOversString);
                    d.setBowlerId(s.getBowlerId());
                    d.setName(bowlerStringName);
                    d.setRuns(s.getRuns());
                    d.setWickets(s.getWickets());
                    d.setExtras(s.getExtras());
                    d.setEconomy(String.valueOf(eco));
                    d.setTotalNoBall(String.valueOf(noBall));
                    d.setTotalWideBall(String.valueOf(wideBall));
                    d.setTotalDotBall(String.valueOf(dotBall));

                    bowling.add(d);
                }
                inning.setBowlingStats(bowling);


                //fall of wickets
                ExtraAndFOW extraAndFOWS = fetchFOW(inning.getId());
                if (extraAndFOWS != null) {
                    inning.setFow(extraAndFOWS.getFow());
                    inning.setExtraRuns(extraAndFOWS.getExtraRuns());
                } else {
                    inning.setFow(new ArrayList<String>());
                    inning.setExtraRuns(new ExtraRuns());
                }
                innings.add(inning);
            }
            Inning firstInningFullScoreCard = null;
            Inning secondInningFullScoreCard = null;
            int runsRequired = 0;
            int ballsRemaining = 0;
            float requiredRunRate = 0;

            for (int i = 0; i < innings.size(); i++) {
                if (innings.get(i).getInningNumber().equalsIgnoreCase("1")) {
                    firstInningFullScoreCard = innings.get(i);
                    result.getData().getMatch().setTeam1StatusPic("https://www.sportzfever.com/assets/public/images/bat.png");
                    result.getData().getMatch().setTeam2StatusPic("https://www.sportzfever.com/assets/public/images/bowl.png");
                    if (innings.get(i).getPlaying().equalsIgnoreCase("0")) {
                        runsRequired = Integer.parseInt(firstInningFullScoreCard.getTotalRunsScored()) + 1;
                        ballsRemaining = Integer.parseInt(firstInningFullScoreCard.getTotalOvers()) * 6;
                        requiredRunRate = (runsRequired / ballsRemaining) * 6;
                        result.getData().getMatch().setTeam2StatusPic("https://www.sportzfever.com/assets/public/images/bat.png");
                        result.getData().getMatch().setTeam1StatusPic("https://www.sportzfever.com/assets/public/images/bowl.png");
                    }

                    if (innings.get(i).getWickets().equalsIgnoreCase(String.valueOf(Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) - 1))) {
                        result.getData().getMatch().setTeam1ScoreString(innings.get(i).getTotalRunsScored() + " All out (" + innings.get(i).getPlayedOvers() + " ov)");
                    } else {
                        result.getData().getMatch().setTeam1ScoreString(innings.get(i).getTotalRunsScored() + "/" + innings.get(i).getWickets() + " (" + innings.get(i).getPlayedOvers() + " ov)");
                    }
                } else if (innings.get(i).getInningNumber().equalsIgnoreCase("2")) {
                    result.getData().getMatch().setTeam2StatusPic("https://www.sportzfever.com/assets/public/images/bat.png");
                    result.getData().getMatch().setTeam1StatusPic("https://www.sportzfever.com/assets/public/images/bowl.png");
                    secondInningFullScoreCard = innings.get(i);
                    runsRequired = Integer.parseInt(firstInningFullScoreCard.getTotalRunsScored()) - Integer.parseInt(secondInningFullScoreCard.getTotalRunsScored());
                    runsRequired = runsRequired + 1;

                    Float secondInningTotalOver = Float.parseFloat(secondInningFullScoreCard.getTotalOvers());
                    int secondInningTotalOveroverNo = (int) (secondInningTotalOver / 1);
                    int secondInningTotalOveroverBallNo = (int) ((secondInningTotalOver * 10) % 10);
                    int secondInningtotalOver = 0;
                    if (secondInningTotalOveroverNo > 0) {
                        secondInningtotalOver = (secondInningTotalOveroverNo * 6) + secondInningTotalOveroverBallNo;
                    } else {
                        secondInningtotalOver = (secondInningTotalOveroverNo * 6);
                    }

                    Float secondInningPlayedOvers = Float.parseFloat(secondInningFullScoreCard.getPlayedOvers());
                    int secondInningPlayedoverNo = (int) (secondInningPlayedOvers / 1);
                    int secondInningPlayedoverBallNo = (int) ((secondInningPlayedOvers * 10) % 10);
                    int secondInningPlayedtotalOver = 0;
                    if (secondInningPlayedoverNo > 0) {
                        secondInningPlayedtotalOver = (secondInningPlayedoverNo * 6) + secondInningPlayedoverBallNo;
                    } else {
                        secondInningPlayedtotalOver = (secondInningPlayedoverNo * 6);
                    }
                    ballsRemaining = secondInningtotalOver - secondInningPlayedtotalOver;
                    Float noor = (float) ballsRemaining / 6;
                    Float nobr = (float) ballsRemaining % 6;

                    int noor1 = (int) (noor / 1);
                    int noor2 = (int) ((noor * 10) % 10);
                    noor = (float) noor1;

                    Float TotalOverR = (noor + nobr / 6);
                    requiredRunRate = runsRequired / TotalOverR;
                    if (innings.get(i).getWickets().equalsIgnoreCase(String.valueOf(Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) - 1))) {
                        result.getData().getMatch().setTeam2ScoreString(innings.get(i).getTotalRunsScored() + " All out (" + innings.get(i).getPlayedOvers() + " ov)");
                    } else {
                        result.getData().getMatch().setTeam2ScoreString(innings.get(i).getTotalRunsScored() + "/" + innings.get(i).getWickets() + " (" + innings.get(i).getPlayedOvers() + " ov)");
                    }
                }
                result.getData().getMatch().setRunsRequired(String.valueOf(runsRequired));
                result.getData().getMatch().setBallsRemaining(String.valueOf(ballsRemaining));
                result.getData().getMatch().setRequiredRunRate(String.valueOf(requiredRunRate));
            }
            if ((result.getData().getMatch().getMatchStatus().equalsIgnoreCase("ENDED")) && (result.getData().getMatch().getTie().equalsIgnoreCase("YES"))) {

                if (result.getData().getMatch().getMatchResultId().equalsIgnoreCase(result.getData().getMatch().getTeam1Id())) {
                    wonString = result.getData().getMatch().getTeam1Name() + " won by " + (Integer.parseInt(firstInningFullScoreCard.getTotalRunsScored()) - Integer.parseInt(secondInningFullScoreCard.getTotalRunsScored())) + " runs";
                } else {
                    wonString = result.getData().getMatch().getTeam2Name() + " won by " + ((Integer.parseInt(result.getData().getMatch().getNumberOfPlayers()) - 1) - (Integer.parseInt(secondInningFullScoreCard.getWickets()))) + " wickets";
                }
                result.getData().getMatch().setWonString(wonString);
            }
            result.getData().setInnings(innings);


            if ((result.getData().getMatch().getMatchStatus().equalsIgnoreCase("ENDED") && (result.getData().getMatch().getTie().equalsIgnoreCase("YES")))) {
                String socialSharingString = "Match was played between " + result.getData().getMatch().getTeam1Name() + "[" + result.getData().getMatch().getTeam1ScoreString() + "] vs " + result.getData().getMatch().getTeam2Name() + "[" + result.getData().getMatch().getTeam2ScoreString() + "] at " + result.getData().getMatch().getLocation() + ". " + result.getData().getMatch().getWonString() + ". More details available at SportzFever - https://www.sportzfever.com/viewMatchScoreCard/" + result.getData().getMatch().getEventId();
                result.getData().getMatch().setSocialSharingString(socialSharingString);
            }
            if (result.getData().getMatch().getMatchStatus().equalsIgnoreCase("NOT STARTED")) {
                String socialSharingString = result.getData().getMatch().getMatchScheduleString() + " between " + result.getData().getMatch().getMatchTile() + ". Watch live scores and join the action @ SportzFever - https://www.sportzfever.com/viewMatchScoreCard/" + result.getData().getMatch().getEventId();
                result.getData().getMatch().setSocialSharingString(socialSharingString);
            }
            if (result.getData().getMatch().getMatchStatus().equalsIgnoreCase("STARTED")) {
                if (innings.size() == 1) {
                    String socialSharingString = "Match between " + result.getData().getMatch().getTeam1Name() + "[" + result.getData().getMatch().getTeam1ScoreString() + "] vs " + result.getData().getMatch().getTeam2Name() + "[" + result.getData().getMatch().getTeam2ScoreString() + "] at " + result.getData().getMatch().getLocation() + ". " + result.getData().getMatch().getTossString() + ". Watch live action @ SportzFever - https://www.sportzfever.com/viewMatchScoreCard/" + result.getData().getMatch().getEventId();
                    result.getData().getMatch().setSocialSharingString(socialSharingString);
                } else {
                    String socialSharingString = "Match between " + result.getData().getMatch().getTeam1Name() + "[" + result.getData().getMatch().getTeam1ScoreString() + "] vs " + result.getData().getMatch().getTeam2Name() + "[" + result.getData().getMatch().getTeam2ScoreString() + "] at " + result.getData().getMatch().getLocation() + ". " + result.getData().getMatch().getWonString() + ". Watch live action @ SportzFever - https://www.sportzfever.com/viewMatchScoreCard/" + result.getData().getMatch().getEventId();
                    result.getData().getMatch().setSocialSharingString(socialSharingString);
                }
            }
        } else {
            //match not found return message for not found
            result.setResult("0");
            result.setAllowedToEdit("0");
            result.setData(new MatchStaticsData());
            result.setMessage("Match not found.");
        }

        Gson gson = new Gson();
        String jsonData = gson.toJson(result);
        return jsonData;
    }
}
