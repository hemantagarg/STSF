package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterHomeFriendSuggestion;
import com.app.sportzfever.adapter.AdapterHomeMatches;
import com.app.sportzfever.adapter.AdapterHomePlayers;
import com.app.sportzfever.adapter.AdapterHomeTeams;
import com.app.sportzfever.adapter.AdapterHomeTournaments;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelPastMatches;
import com.app.sportzfever.models.ModelSportTeamList;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Home extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_matches_around_you, list_team_around_you, list_friend_suggestion, list_players_around_you, list_tournaments_around_you;
    private Bundle b;
    private Context context;
    private AdapterHomeMatches adapterHomeMatches;
    private ModelPastMatches modelPastMatches;
    private ArrayList<ModelPastMatches> arrayListMatches = new ArrayList<>();
    private ArrayList<ModelSportTeamList> arrayListPlayers = new ArrayList<>();
    private ArrayList<ModelSportTeamList> arrayListTeams = new ArrayList<>();
    private ArrayList<ModelSportTeamList> arrayListTournaments = new ArrayList<>();
    private ArrayList<ModelSportTeamList> arrayListFriendSuggestion = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView text_nodata;
    int feedClickedPosition = 0;

    public static Fragment_Home fragment_userFeed;
    private final String TAG = Fragment_Home.class.getSimpleName();

    public static Fragment_Home getInstance() {
        if (fragment_userFeed == null)
            fragment_userFeed = new Fragment_Home();
        return fragment_userFeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        arrayListMatches = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(true);
        Dashboard.getInstance().selectTab(0);
        Dashboard.getInstance().manageHeaderVisibitlity(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_matches_around_you = (RecyclerView) view.findViewById(R.id.list_matches_around_you);
        list_tournaments_around_you = (RecyclerView) view.findViewById(R.id.list_tournaments_around_you);
        list_team_around_you = (RecyclerView) view.findViewById(R.id.list_team_around_you);
        list_friend_suggestion = (RecyclerView) view.findViewById(R.id.list_friend_suggestion);
        list_players_around_you = (RecyclerView) view.findViewById(R.id.list_players_around_you);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        list_matches_around_you.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list_players_around_you.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list_tournaments_around_you.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list_friend_suggestion.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list_team_around_you.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        arrayListMatches = new ArrayList<>();
        setlistener();
        getServicelistRefresh();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicelistRefresh();
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
            Bundle b = new Bundle();
            b.putString("eventId", arrayListMatches.get(position).getEventId());
            fragmentupcomingdetals.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
        } else if (flag == 2) {
            Fragment_Team_Details fragmentAvtar_details = new Fragment_Team_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayListTeams.get(position).getTeamId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);
        } else if (flag == 3) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayListPlayers.get(position).getId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);
        } else if (flag == 4) {
            FragmentTournament_Details tab2 = new FragmentTournament_Details();
            Bundle b = new Bundle();
            b.putString("id", arrayListTournaments.get(position).getId());
            b.putString("name", arrayListTournaments.get(position).getAvatarName());
            b.putString("date", "");
            b.putString("image", arrayListTournaments.get(position).getProfilePicture());
            tab2.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, tab2, true);
        } else if (flag == 6) {
            FragmentUser_Details fragmentUser_details = new FragmentUser_Details();
            Bundle b = new Bundle();
            b.putString("id", arrayListFriendSuggestion.get(position).getId());
            fragmentUser_details.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentUser_details, true);
        } else if (flag == 5) {
            addFriend("ADDFRIEND", arrayListFriendSuggestion.get(position).getId());
        }
    }


    private void addFriend(String ADDFRIEND, String id) {

        if (AppUtils.isNetworkAvailable(context)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fromUserId", AppUtils.getUserId(context));
                jsonObject.put("toUserId", id);
                jsonObject.put("type", ADDFRIEND);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ADDASFRIEND;
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  http://sfscoring.betasportzfever.com/getFeeds/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52

                String url = JsonApiHelper.BASEURL + JsonApiHelper.SFLANDING + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    arrayListMatches.clear();
                    arrayListTeams.clear();
                    arrayListPlayers.clear();
                    arrayListTournaments.clear();
                    arrayListFriendSuggestion.clear();

                    JSONArray allMatch = data.getJSONArray("allMatch");
                    for (int i = 0; i < allMatch.length(); i++) {

                        JSONObject jo = allMatch.getJSONObject(i);
                        modelPastMatches = new ModelPastMatches();

                        modelPastMatches.setId(jo.getString("id"));
                        modelPastMatches.setEventId(jo.getString("eventId"));
                        modelPastMatches.setLocation(jo.getString("location"));
                        modelPastMatches.setFirstBattingTeamName(jo.getString("firstBattingTeamName"));
                        modelPastMatches.setSecondBattingTeamName(jo.getString("secondBattingTeamName"));
                        modelPastMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelPastMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));
                        modelPastMatches.setTournamentName(jo.getString("tournamentName"));
                        modelPastMatches.setMatchWinnerTeamName(jo.getString("tossWinnerTeamName"));
                        if (jo.has("winString"))
                            modelPastMatches.setWinString(jo.getString("winString"));

                        modelPastMatches.setTotalRunsScoredTeam1(jo.getString("totalRunsScoredTeam1"));
                        modelPastMatches.setTotalRunsScoredTeam2(jo.getString("totalRunsScoredTeam2"));
                        modelPastMatches.setPlayedOversTeam1(jo.getString("playedOversTeam1"));
                        modelPastMatches.setPlayedOversTeam2(jo.getString("playedOversTeam2"));
                        modelPastMatches.setFirstBattingWickets(jo.getString("firstBattingWickets"));
                        modelPastMatches.setSecondBattingWickets(jo.getString("secondBattingWickets"));

                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelPastMatches.setTime(j1.getString("time"));
                        modelPastMatches.setDate(j1.getString("date"));
                        modelPastMatches.setYear(j1.getString("year"));
                        modelPastMatches.setMonthName(j1.getString("monthName"));
                        modelPastMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelPastMatches.setRowType(1);
                        arrayListMatches.add(modelPastMatches);
                    }
                    Log.e("size", "**" + arrayListMatches.size());
                    adapterHomeMatches = new AdapterHomeMatches(getActivity(), this, arrayListMatches);
                    list_matches_around_you.setAdapter(adapterHomeMatches);

                    JSONArray teams = data.getJSONArray("topTeamPlay");
                    for (int i = 0; i < teams.length(); i++) {
                        JSONObject headerobj = teams.getJSONObject(i);
                        ModelSportTeamList modelSportTeamList = new ModelSportTeamList();
                        modelSportTeamList.setRowType(1);
                        modelSportTeamList.setTeamId(headerobj.getString("teamId"));
                        modelSportTeamList.setCaptainName(headerobj.getString("captainName"));
                        modelSportTeamList.setTeamName(headerobj.getString("teamName"));
                        modelSportTeamList.setProfilePicture(headerobj.getString("teamProfilePicture"));
                        modelSportTeamList.setLocation(headerobj.getString("location"));
                        arrayListTeams.add(modelSportTeamList);
                    }
                    AdapterHomeTeams adapterHomeTeams = new AdapterHomeTeams(getActivity(), this, arrayListTeams);
                    list_team_around_you.setAdapter(adapterHomeTeams);


                    JSONArray topTournament = data.getJSONArray("topTournament");
                    for (int i = 0; i < topTournament.length(); i++) {
                        JSONObject headerobj = topTournament.getJSONObject(i);
                        ModelSportTeamList modelSportTeamList = new ModelSportTeamList();
                        modelSportTeamList.setRowType(1);
                        modelSportTeamList.setId(headerobj.getString("id"));
                        modelSportTeamList.setAvatarName(headerobj.getString("name"));
                        modelSportTeamList.setProfilePicture(headerobj.getString("profilePicture"));
                        modelSportTeamList.setNoOfGroup(headerobj.getString("noOfGroup"));
                        modelSportTeamList.setNoOfTeam(headerobj.getString("noOfTeam"));
                        modelSportTeamList.setdate(headerobj.getString("tournamentStartDate"));
                        modelSportTeamList.setType(headerobj.getString("type"));

                        arrayListTournaments.add(modelSportTeamList);
                    }
                    AdapterHomeTournaments adapterHomeTournament = new AdapterHomeTournaments(getActivity(), this, arrayListTournaments);
                    list_tournaments_around_you.setAdapter(adapterHomeTournament);

                    JSONArray friendsSuggestion = data.getJSONArray("friendsSuggestion");
                    for (int i = 0; i < friendsSuggestion.length(); i++) {
                        JSONObject headerobj = friendsSuggestion.getJSONObject(i);
                        ModelSportTeamList modelSportTeamList = new ModelSportTeamList();
                        modelSportTeamList.setRowType(1);
                        modelSportTeamList.setId(headerobj.getString("userId"));
                        modelSportTeamList.setAvatarName(headerobj.getString("userName"));
                        modelSportTeamList.setProfilePicture(headerobj.getString("profilePicture"));

                        arrayListFriendSuggestion.add(modelSportTeamList);
                    }
                    AdapterHomeFriendSuggestion adapterHomeFriendSuggestion = new AdapterHomeFriendSuggestion(getActivity(), this, arrayListFriendSuggestion);
                    list_friend_suggestion.setAdapter(adapterHomeFriendSuggestion);


                    JSONArray topBatsmanBowler = data.getJSONArray("topBatsmanBowler");
                    for (int i = 0; i < topBatsmanBowler.length(); i++) {
                        JSONObject headerobj = topBatsmanBowler.getJSONObject(i);
                        ModelSportTeamList modelSportTeamList = new ModelSportTeamList();
                        modelSportTeamList.setRowType(1);
                        modelSportTeamList.setSpeciality(headerobj.getString("speciality"));
                        if (modelSportTeamList.getSpeciality().equals(AppConstant.BATSMAN)) {
                            modelSportTeamList.setRuns(headerobj.getString("runs"));
                            modelSportTeamList.setHighestScore(headerobj.getString("highestScore"));
                            modelSportTeamList.setTotalMatch(headerobj.getString("totalMatch"));
                            modelSportTeamList.setTotalInning(headerobj.getString("totalInning"));
                            modelSportTeamList.setBalls(headerobj.getString("balls"));
                            modelSportTeamList.setStrikeRate(headerobj.getString("strikeRate"));
                            modelSportTeamList.setFours(headerobj.getString("fours"));
                            modelSportTeamList.setSixes(headerobj.getString("sixes"));
                            modelSportTeamList.setBattingAvg(headerobj.getString("battingAvg"));
                            modelSportTeamList.setId(headerobj.getString("id"));
                            modelSportTeamList.setBastsmanAvatarName(headerobj.getString("bastsmanAvatarName"));
                            modelSportTeamList.setBatsmanAvatarPic(headerobj.getString("batsmanAvatarPic"));
                        } else {
                            modelSportTeamList.setRuns(headerobj.getString("totalRun"));
                            modelSportTeamList.setTotalMatch(headerobj.getString("totalMatch"));
                            modelSportTeamList.setWickets(headerobj.getString("wickets"));
                            modelSportTeamList.setBest(headerobj.getString("best"));
                            modelSportTeamList.setStrikeRate(headerobj.getString("strikeRate"));
                            modelSportTeamList.setEconomy(headerobj.getString("economy"));
                            modelSportTeamList.setTotalOvers(headerobj.getString("totalOvers"));
                            modelSportTeamList.setBalls(headerobj.getString("totalBalls"));
                            modelSportTeamList.setBowlingAvg(headerobj.getString("bowlingAvg"));
                            modelSportTeamList.setId(headerobj.getString("id"));
                            modelSportTeamList.setBastsmanAvatarName(headerobj.getString("name"));
                            modelSportTeamList.setBatsmanAvatarPic(headerobj.getString("profilePicture"));
                        }
                        arrayListPlayers.add(modelSportTeamList);
                    }
                    AdapterHomePlayers adapterHomePlayers = new AdapterHomePlayers(getActivity(), this, arrayListPlayers);
                    list_players_around_you.setAdapter(adapterHomePlayers);


                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            } else if (position == 11) {
                Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setRefreshing(false);

        }

    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }

}

