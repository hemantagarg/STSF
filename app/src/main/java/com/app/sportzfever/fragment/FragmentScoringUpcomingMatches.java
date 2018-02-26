package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterSoringUpcomingMatches;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ChoiceDialogClickListener;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingMatches;
import com.app.sportzfever.models.dbmodels.Avatar;
import com.app.sportzfever.models.dbmodels.CricketBall;
import com.app.sportzfever.models.dbmodels.CricketInning;
import com.app.sportzfever.models.dbmodels.CricketOver;
import com.app.sportzfever.models.dbmodels.CricketScoreCard;
import com.app.sportzfever.models.dbmodels.CricketSelectedTeamPlayers;
import com.app.sportzfever.models.dbmodels.Event;
import com.app.sportzfever.models.dbmodels.GeneralProfile;
import com.app.sportzfever.models.dbmodels.MatchScorer;
import com.app.sportzfever.models.dbmodels.MatchTeamRoles;
import com.app.sportzfever.models.dbmodels.Matches;
import com.app.sportzfever.models.dbmodels.Roster;
import com.app.sportzfever.models.dbmodels.User;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.SportzDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentScoringUpcomingMatches extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterSoringUpcomingMatches adapterUpcomingMatches;
    private ModelUpcomingMatches modelUpcomingMatches;
    private ArrayList<ModelUpcomingMatches> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";
    private FloatingActionButton floating_add;
    View view_about;
    public static FragmentScoringUpcomingMatches fragment_teamJoin_request;
    private final String TAG = FragmentScoringUpcomingMatches.class.getSimpleName();
    private SportzDatabase db;

    public static FragmentScoringUpcomingMatches getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentScoringUpcomingMatches();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragment__scoring_ucoming_matches, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {

        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Upcoming Matches");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, false);

    }

    /*****************************************************************************
     * Function name - manageHeaderClick
     * Description - manage the click on the left and right image view of header
     *****************************************************************************/
    private HeaderViewClickListener manageHeaderClick() {
        return new HeaderViewClickListener() {
            @Override
            public void onClickOfHeaderLeftView() {
                AppUtils.showLog(TAG, "onClickOfHeaderLeftView");
                context.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDatabase();
        getOffLineData();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        floating_add = (FloatingActionButton) view.findViewById(R.id.floating_add);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        manageHeaderView();
        setlistener();
    }
    public void setDatabase() {
        db = null;
        try {
            db = new SportzDatabase(context);
            db.open();

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            db.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        getServicelistRefresh();
    }

    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getServicelistRefresh();
            }
        });

        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCreateMatch fragment_postFeed = new FragmentCreateMatch();
                Bundle bundle = new Bundle();
                fragment_postFeed.setArguments(bundle);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragment_postFeed, true);
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            if (!arrayList.get(position).getInviteStatus().equalsIgnoreCase(AppConstant.PENDING)) {
                if (arrayList.get(position).getMatchStatus().equals("NOT STARTED")) {
                    if (arrayList.get(position).getCanEditLineup().equals("1")) {
                        FragmentScoringPrepareLineup fragmentupcomingdetals = new FragmentScoringPrepareLineup();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getEventId());
                        b.putString("matchId", arrayList.get(position).getMatchId());
                        b.putString("team1Id", arrayList.get(position).getTeam1Id());
                        b.putString("team2Id", arrayList.get(position).getTeam2Id());
                        b.putString("overs", arrayList.get(position).getNumberOfOvers());
                        b.putString("team1ScorerName", arrayList.get(position).getTeam1ScorerName());
                        b.putString("team2ScorerName", arrayList.get(position).getTeam2ScorerName());
                        b.putString("isScorerForTeam1", arrayList.get(position).getIsScorerForTeam1());
                        b.putString("isScorerForTeam2", arrayList.get(position).getIsScorerForTeam2());
                        b.putBoolean("isTeam1", true);
                        b.putString("playersCount", arrayList.get(position).getNumberOfPlayers());
                        b.putString("title", arrayList.get(position).getTeam1Name());
                        b.putString("team1Name", arrayList.get(position).getTeam1Name());
                        b.putString("team2Name", arrayList.get(position).getTeam2Name());
                        fragmentupcomingdetals.setArguments(b);
                        fragmentupcomingdetals.setTargetFragment(FragmentScoringUpcomingMatches.this, AppConstant.FRAGMENT_CODE);
                        Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
                    } else {
                        String scorer1 = "<font color='#3d85f3'>" + arrayList.get(position).getTeam1ScorerName() + "</font>";
                        String scorer2 = "<font color='#3d85f3'>" + arrayList.get(position).getTeam2ScorerName() + "</font>";

                        String message = "You are not the designated scorer for this match" + "\n\n" + "Scorer for " + arrayList.get(position).getTeam1Name() + ":" + "\n" + scorer1
                                + "\n" + "Scorer for " + arrayList.get(position).getTeam2Name() + ":" + "\n" + scorer2 + "\n\n" + "Please ask your captain to make you match scorer if you want to do scoring.";
                        AppUtils.showDialogMessage(context, message.replace("\n", "<br />"));
                    }
                } else {
                    if (arrayList.get(position).getTeam1BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_STARTED)) {
                        checkActiveScorer(position);
                    } else {
                        if (arrayList.get(position).getTeam1BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_ENDED) || arrayList.get(position).getTeam1BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                            if (arrayList.get(position).getIsScorerForTeam2().equalsIgnoreCase(AppConstant.NO) &&
                                    arrayList.get(position).getTeam2BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                                AppUtils.showDialogMessage(context, getString(R.string.not_authorized_scorer));
                            } else if (arrayList.get(position).getIsScorerForTeam2().equalsIgnoreCase(AppConstant.YES) &&
                                    arrayList.get(position).getTeam2BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                                FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                                Bundle b = new Bundle();
                                b.putString("eventId", arrayList.get(position).getEventId());
                                b.putString("IsScorerForTeam2", arrayList.get(position).getIsScorerForTeam2());
                                fragmentSoringMatchDetails.setArguments(b);
                                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentSoringMatchDetails, true);
                            } else if (arrayList.get(position).getTeam2BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_STARTED)) {
                                checkActiveScorer(position);
                            } else if (arrayList.get(position).getIsScorerForTeam1().equalsIgnoreCase(AppConstant.NO) &&
                                    arrayList.get(position).getTeam1BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                                AppUtils.showDialogMessage(context, getString(R.string.cannot_start_inning));
                            } else if (arrayList.get(position).getIsScorerForTeam1().equalsIgnoreCase(AppConstant.YES) &&
                                    arrayList.get(position).getTeam1BattingStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                                FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                                Bundle b = new Bundle();
                                b.putString("eventId", arrayList.get(position).getEventId());
                                b.putString("IsScorerForTeam2", arrayList.get(position).getIsScorerForTeam1());
                                fragmentSoringMatchDetails.setArguments(b);
                                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentSoringMatchDetails, true);
                            }
                        }
                    }
                }
            } else {
                AppUtils.customAlertDialogWithoutTitle(context, getString(R.string.match_not_accepted), "OK", new ChoiceDialogClickListener() {
                    @Override
                    public void onClickOfPositive() {
                    }

                    @Override
                    public void onClickOfNegative() {

                    }
                });
            }
        }
    }

    private void checkActiveScorer(int position) {
        if (arrayList.get(position).getIsActiveScorer().equals("1")) {
            FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
            Bundle b = new Bundle();
            b.putString("eventId", arrayList.get(position).getEventId());
            b.putString("IsScorerForTeam2", arrayList.get(position).getIsScorerForTeam2());
            fragmentSoringMatchDetails.setArguments(b);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentSoringMatchDetails, true);
        } else {
            String message = String.format(context.getResources().getString(R.string.another_scorer_start_scoring_message), arrayList.get(position).getActiveScorerName());
            AppUtils.showDialogMessage(context, message.replace("\n", "<br />"));
        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SCORING_MATCHES;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbjectNoProgress(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Function to get all the data needed for offline scoring
    private void getOffLineData() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_OffLineDATA + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(10, context, this).getqueryJsonbjectNoProgress(url, new JSONObject(), Request.Method.GET);

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
                if (context != null && isAdded()) {
                    getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
                }
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelUpcomingMatches = new ModelUpcomingMatches();

                        modelUpcomingMatches.setId(jo.getString("id"));
                        modelUpcomingMatches.setEventId(jo.getString("eventId"));
                        modelUpcomingMatches.setInviteStatus(jo.getString("inviteStatus"));
                        modelUpcomingMatches.setLocation(jo.getString("location"));
                        modelUpcomingMatches.setTeam1Id(jo.getString("team1Id"));
                        modelUpcomingMatches.setTeam2Id(jo.getString("team2Id"));
                        modelUpcomingMatches.setMatchStatus(jo.getString("matchStatus"));
                        modelUpcomingMatches.setNumberOfOvers(jo.getString("noOfOvers"));
                        modelUpcomingMatches.setMatchId(jo.getString("matchId"));
                        modelUpcomingMatches.setTeam2BattingStatus(jo.getString("team2BattingStatus"));
                        modelUpcomingMatches.setTeam1BattingStatus(jo.getString("team1BattingStatus"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
                        modelUpcomingMatches.setIsScorerForTeam1(jo.getString("isScorerForTeam1"));
                        modelUpcomingMatches.setIsCurrentInningScorer(jo.getString("isCurrentInningScorer"));
                        modelUpcomingMatches.setIsScorerForTeam2(jo.getString("isScorerForTeam2"));
                        modelUpcomingMatches.setTeam2Name(jo.getString("team2Name"));
                        modelUpcomingMatches.setNumberOfPlayers(jo.getString("numberOfPlayers"));
                        modelUpcomingMatches.setTeam1profilePicture(jo.getString("team1Profile"));
                        modelUpcomingMatches.setTeam2profilePicture(jo.getString("team2Profile"));
                        modelUpcomingMatches.setCanEditLineup(jo.getString("canEditLineup"));
                        modelUpcomingMatches.setIsActiveScorer(jo.getString("isActiveScorer"));
                        modelUpcomingMatches.setActiveScorerName(jo.getString("activeScorerName"));
                        modelUpcomingMatches.setIsTeam1ScoringOnSf(jo.getString("isTeam1ScoringOnSf"));
                        modelUpcomingMatches.setIsTeam2ScoringOnSf(jo.getString("isTeam2ScoringOnSf"));

                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelUpcomingMatches.setTime(j1.getString("time"));
                        modelUpcomingMatches.setDate(j1.getString("date"));
                        modelUpcomingMatches.setYear(j1.getString("year"));
                        modelUpcomingMatches.setMonthName(j1.getString("monthName"));
                        modelUpcomingMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelUpcomingMatches.setRowType(1);

                        JSONObject scorers = jo.getJSONObject("scorers");
                        JSONArray team1Scorer = scorers.getJSONArray("team1Scorer");
                        String scorer1Name = "";
                        for (int j = 0; j < team1Scorer.length(); j++) {
                            JSONObject jsonObject = team1Scorer.getJSONObject(j);
                            if (scorer1Name.equalsIgnoreCase("")) {
                                scorer1Name = jsonObject.getString("scorerName");
                            } else {
                                scorer1Name = scorer1Name + ", " + jsonObject.getString("scorerName");
                            }
                        }
                        modelUpcomingMatches.setTeam1ScorerName(scorer1Name);

                        JSONArray team2Scorer = scorers.getJSONArray("team2Scorer");
                        String scorer2Name = "";
                        for (int j = 0; j < team2Scorer.length(); j++) {
                            JSONObject jsonObject = team2Scorer.getJSONObject(j);
                            if (scorer2Name.equalsIgnoreCase("")) {
                                scorer2Name = jsonObject.getString("scorerName");
                            } else {
                                scorer2Name = scorer2Name + ", " + jsonObject.getString("scorerName");
                            }
                        }
                        modelUpcomingMatches.setTeam2ScorerName(scorer2Name);
                        arrayList.add(modelUpcomingMatches);
                    }
                    adapterUpcomingMatches = new AdapterSoringUpcomingMatches(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterUpcomingMatches);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("Teams currently warming up. New matches will be scheduled soon.");
                    }

                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("Teams currently warming up. New matches will be scheduled soon.");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelUpcomingMatches = new ModelUpcomingMatches();

                        modelUpcomingMatches.setLocation(jo.getString("location"));
                        modelUpcomingMatches.setInviteStatus(jo.getString("inviteStatus"));
                        modelUpcomingMatches.setTournamentName(jo.getString("tournamentName"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
                        modelUpcomingMatches.setTeam2Name(jo.getString("team2Name"));
                        modelUpcomingMatches.setMatchId(jo.getString("matchId"));
                        modelUpcomingMatches.setNumberOfOvers(jo.getString("noOfOvers"));
                        modelUpcomingMatches.setNumberOfPlayers(jo.getString("numberOfPlayers"));
                        modelUpcomingMatches.setTeam1Id(jo.getString("team1Id"));
                        modelUpcomingMatches.setIsCurrentInningScorer(jo.getString("isCurrentInningScorer"));
                        modelUpcomingMatches.setTeam2BattingStatus(jo.getString("team2BattingStatus"));
                        modelUpcomingMatches.setTeam1BattingStatus(jo.getString("team1BattingStatus"));
                        modelUpcomingMatches.setIsScorerForTeam1(jo.getString("isScorerForTeam1"));
                        modelUpcomingMatches.setIsScorerForTeam2(jo.getString("isScorerForTeam2"));
                        modelUpcomingMatches.setTeam2Id(jo.getString("team2Id"));
                        modelUpcomingMatches.setMatchStatus(jo.getString("matchStatus"));
                        modelUpcomingMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelUpcomingMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));
                        modelUpcomingMatches.setCanEditLineup(jo.getString("canEditLineup"));
                        modelUpcomingMatches.setIsActiveScorer(jo.getString("isActiveScorer"));
                        modelUpcomingMatches.setActiveScorerName(jo.getString("activeScorerName"));
                        modelUpcomingMatches.setIsTeam1ScoringOnSf(jo.getString("isTeam1ScoringOnSf"));
                        modelUpcomingMatches.setIsTeam2ScoringOnSf(jo.getString("isTeam2ScoringOnSf"));

                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelUpcomingMatches.setTime(j1.getString("time"));
                        modelUpcomingMatches.setDate(j1.getString("date"));
                        modelUpcomingMatches.setYear(j1.getString("year"));
                        modelUpcomingMatches.setMonthName(j1.getString("monthName"));
                        modelUpcomingMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelUpcomingMatches.setRowType(1);
                        JSONObject scorers = jo.getJSONObject("scorers");
                        JSONArray team1Scorer = scorers.getJSONArray("team1Scorer");
                        String scorer1Name = "";
                        for (int j = 0; j < team1Scorer.length(); j++) {
                            JSONObject jsonObject = team1Scorer.getJSONObject(j);
                            if (scorer1Name.equalsIgnoreCase("")) {
                                scorer1Name = jsonObject.getString("scorerName");
                            } else {
                                scorer1Name = scorer1Name + ", " + jsonObject.getString("scorerName");
                            }
                        }
                        modelUpcomingMatches.setTeam1ScorerName(scorer1Name);

                        JSONArray team2Scorer = scorers.getJSONArray("team2Scorer");
                        String scorer2Name = "";
                        for (int j = 0; j < team2Scorer.length(); j++) {
                            JSONObject jsonObject = team2Scorer.getJSONObject(j);
                            if (scorer2Name.equalsIgnoreCase("")) {
                                scorer2Name = jsonObject.getString("scorerName");
                            } else {
                                scorer2Name = scorer2Name + ", " + jsonObject.getString("scorerName");
                            }
                        }
                        modelUpcomingMatches.setTeam2ScorerName(scorer2Name);

                        arrayList.add(modelUpcomingMatches);
                    }

                    adapterUpcomingMatches.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterUpcomingMatches.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
                }
            }
            else if(position == 10)
            {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    JSONArray avatars = data.getJSONArray("avatar");
                    JSONArray cricket_selected_team_players = data.getJSONArray("cricket_selected_team_players");
                    JSONArray events = data.getJSONArray("events");
                    JSONArray general_profiles = data.getJSONArray("general_profile");
                    JSONArray matches = data.getJSONArray("matches");
                    JSONArray match_scorers = data.getJSONArray("match_scorer");
                    JSONArray match_team_roles = data.getJSONArray("match_team_roles");
                    JSONArray rosters = data.getJSONArray("roster");
                    JSONArray teams = data.getJSONArray("team");
                    JSONArray users = data.getJSONArray("user");

                    JSONArray cricket_balls = data.getJSONArray("cricket_balls");
                    JSONArray cricket_overs = data.getJSONArray("cricket_overs");
                    JSONArray cricket_innings = data.getJSONArray("cricket_innings");
                    JSONArray cricket_scorecard = data.getJSONArray("cricket_scorecard");

                    List<User> userTableRecord = new ArrayList<>();
                    List<Avatar> avatarTableRecord = new ArrayList<>();
                    List<CricketSelectedTeamPlayers> cricketSelectedTeamPlayerTableRecord = new ArrayList<>();
                    List<Event> eventTableRecord = new ArrayList<>();
                    List<GeneralProfile> generalProfileTableRecord = new ArrayList<>();
                    List<Matches> matchesTableRecord = new ArrayList<>();
                    List<MatchScorer> matchScorerTableRecord = new ArrayList<>();
                    List<MatchTeamRoles> matchTeamRolesTableRecord = new ArrayList<>();
                    List<Roster> rosterTableRecord = new ArrayList<>();
                    List<com.app.sportzfever.models.dbmodels.Team> teamTableRecord = new ArrayList<>();

                    List<CricketBall> cricketBallTableRecord = new ArrayList<>();
                    List<CricketInning> cricketInningTableRecord = new ArrayList<>();
                    List<CricketOver> cricketOverTableRecord = new ArrayList<>();
                    List<CricketScoreCard> cricketScoreCardTableRecord = new ArrayList<>();

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        Gson gson = new Gson();
                        User userObj = gson.fromJson(user.toString(), User.class);
                        userTableRecord.add(userObj);
                        Log.e("response", userObj.toString());

                    }
                    for (int i = 0; i < teams.length(); i++) {
                        JSONObject team = teams.getJSONObject(i);
                        Gson gson = new Gson();
                        com.app.sportzfever.models.dbmodels.Team teamObj = gson.fromJson(team.toString(), com.app.sportzfever.models.dbmodels.Team.class);
                        Log.e("response", teamObj.toString());
                        teamTableRecord.add(teamObj);
                    }
                    for (int i = 0; i < rosters.length(); i++) {
                        JSONObject roster = rosters.getJSONObject(i);
                        Gson gson = new Gson();
                        Roster rosterObj = gson.fromJson(roster.toString(), Roster.class);
                        Log.e("response", rosterObj.toString());
                        rosterTableRecord.add(rosterObj);
                    }
                    for (int i = 0; i < match_team_roles.length(); i++) {
                        JSONObject match_team_role = match_team_roles.getJSONObject(i);
                        Gson gson = new Gson();
                        MatchTeamRoles match_team_rolesObj = gson.fromJson(match_team_role.toString(), MatchTeamRoles.class);
                        Log.e("response", match_team_rolesObj.toString());
                        matchTeamRolesTableRecord.add(match_team_rolesObj);
                    }
                    for (int i = 0; i < match_scorers.length(); i++) {
                        JSONObject match_scorer = match_scorers.getJSONObject(i);
                        Gson gson = new Gson();
                        MatchScorer match_scorerObj = gson.fromJson(match_scorer.toString(), MatchScorer.class);
                        Log.e("response", match_scorerObj.toString());
                        matchScorerTableRecord.add(match_scorerObj);
                    }
                    for (int i = 0; i < matches.length(); i++) {
                        JSONObject match = matches.getJSONObject(i);
                        Gson gson = new Gson();
                        Matches matchObj = gson.fromJson(match.toString(), Matches.class);
                        Log.e("response", matchObj.toString());
                        matchesTableRecord.add(matchObj);
                    }
                    for (int i = 0; i < avatars.length(); i++) {
                        JSONObject avatar = avatars.getJSONObject(i);
                        Gson gson = new Gson();
                        Avatar avatarObj = gson.fromJson(avatar.toString(), Avatar.class);
                        Log.e("response", avatarObj.toString());
                        avatarTableRecord.add(avatarObj);
                    }

                    for (int i = 0; i < events.length(); i++) {
                        JSONObject event = events.getJSONObject(i);
                        Gson gson = new Gson();
                        Event eventObj = gson.fromJson(event.toString(), Event.class);
                        Log.e("response", eventObj.toString());
                        eventTableRecord.add(eventObj);
                    }

                    for (int i = 0; i < general_profiles.length(); i++) {
                        JSONObject general_profile = general_profiles.getJSONObject(i);
                        Gson gson = new Gson();
                        GeneralProfile general_profileObj = gson.fromJson(general_profile.toString(), GeneralProfile.class);
                        Log.e("response", general_profileObj.toString());
                        generalProfileTableRecord.add(general_profileObj);
                    }

                    for (int i = 0; i < cricket_selected_team_players.length(); i++) {
                        JSONObject cricket_selected_team_player = cricket_selected_team_players.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketSelectedTeamPlayers cricket_selected_team_playerObj = gson.fromJson(cricket_selected_team_player.toString(), CricketSelectedTeamPlayers.class);
                        Log.e("response", cricket_selected_team_playerObj.toString());
                        cricketSelectedTeamPlayerTableRecord.add(cricket_selected_team_playerObj);
                    }

                    for (int i = 0; i < cricket_balls.length(); i++) {
                        JSONObject cricket_ball = cricket_balls.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketBall cricketBallObj = gson.fromJson(cricket_ball.toString(), CricketBall.class);
                        Log.e("response", cricketBallObj.toString());
                        cricketBallTableRecord.add(cricketBallObj);
                    }
                    for (int i = 0; i < cricket_innings.length(); i++) {
                        JSONObject cricket_inning = cricket_innings.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketInning cricketInningObj = gson.fromJson(cricket_inning.toString(), CricketInning.class);
                        Log.e("response", cricketInningObj.toString());
                        cricketInningTableRecord.add(cricketInningObj);
                    }
                    for (int i = 0; i < cricket_overs.length(); i++) {
                        JSONObject cricket_over = cricket_overs.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketOver cricketOverObj = gson.fromJson(cricket_over.toString(), CricketOver.class);
                        Log.e("response", cricketOverObj.toString());
                        cricketOverTableRecord.add(cricketOverObj);
                    }
                    for (int i = 0; i < cricket_scorecard.length(); i++) {
                        JSONObject cricket_scorecar = cricket_scorecard.getJSONObject(i);
                        Gson gson = new Gson();
                        CricketScoreCard cricketScoreCardObj = gson.fromJson(cricket_scorecar.toString(), CricketScoreCard.class);
                        Log.e("response", cricketScoreCardObj.toString());
                        cricketScoreCardTableRecord.add(cricketScoreCardObj);
                    }

                    InsertDataInDb(userTableRecord, avatarTableRecord, cricketSelectedTeamPlayerTableRecord, eventTableRecord, generalProfileTableRecord, matchesTableRecord, matchScorerTableRecord, matchTeamRolesTableRecord, rosterTableRecord, teamTableRecord, cricketBallTableRecord, cricketInningTableRecord, cricketOverTableRecord, cricketScoreCardTableRecord);


                    //JSONObject match = data.getJSONObject("match");


                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //lalit code for offline scoring
    // Inserting the data fetched from server
    private void InsertDataInDb(List<User> userTableRecord, List<Avatar> avatarTableRecord, List<CricketSelectedTeamPlayers> cricketSelectedTeamPlayerTableRecord, List<Event> eventTableRecord, List<GeneralProfile> generalProfileTableRecord, List<Matches> matchesTableRecord, List<MatchScorer> matchScorerTableRecord, List<MatchTeamRoles> matchTeamRolesTableRecord, List<Roster> rosterTableRecord, List<com.app.sportzfever.models.dbmodels.Team> teamTableRecord, List<CricketBall> cricketBallTableRecord, List<CricketInning> cricketInningTableRecord, List<CricketOver> cricketOverTableRecord, List<CricketScoreCard> cricketScoreCardTableRecord) {

        try {
            if (db != null)
            {
                db.open();
                db.cleanDataBase(false);
                for (int i = 0; i < userTableRecord.size(); i++) {
                    db.insertUser(userTableRecord.get(i));
                }
                for (int i = 0; i < teamTableRecord.size(); i++) {
                    db.insertTeam(teamTableRecord.get(i));
                }
                for (int i = 0; i < rosterTableRecord.size(); i++) {
                    db.insertRoster(rosterTableRecord.get(i));
                }
                for (int i = 0; i < matchTeamRolesTableRecord.size(); i++) {
                    db.insertMatchTeamRoles(matchTeamRolesTableRecord.get(i));
                }
                for (int i = 0; i < matchScorerTableRecord.size(); i++) {
                    db.insertMatchScorer(matchScorerTableRecord.get(i));
                }
                for (int i = 0; i < matchesTableRecord.size(); i++) {
                    db.insertMatchData(matchesTableRecord.get(i));
                }
                for (int i = 0; i < avatarTableRecord.size(); i++) {
                    db.insertAvatar(avatarTableRecord.get(i));
                }

                for (int i = 0; i < eventTableRecord.size(); i++) {
                    db.insertEventData(eventTableRecord.get(i));
                }

                for (int i = 0; i < generalProfileTableRecord.size(); i++) {
                    db.insertGeneralProfileData(generalProfileTableRecord.get(i));
                }

                for (int i = 0; i < cricketSelectedTeamPlayerTableRecord.size(); i++) {
                    db.insertCricketSelectedTeamPlayer(cricketSelectedTeamPlayerTableRecord.get(i));
                }

                /*String str1 = db.getMatchStatisticsDetails(Integer.parseInt(eventId));

                JSONObject jObject = new JSONObject(str1);
                data = jObject.getJSONObject("data");
                JSONObject match = data.getJSONObject("match");
                String matctstatus= match.getString("team1Id");*/

                /*for (int i = 0; i < cricketBallTableRecord.size(); i++) {
                    db.insertBallData(cricketBallTableRecord.get(i));
                }
                for (int i = 0; i < cricketInningTableRecord.size(); i++) {
                    db.insertInningData(cricketInningTableRecord.get(i));
                }
                for (int i = 0; i < cricketOverTableRecord.size(); i++) {
                    db.insertOverData(cricketOverTableRecord.get(i));
                }
                for (int i = 0; i < cricketScoreCardTableRecord.size(); i++) {
                    db.insertScoreCardData(cricketScoreCardTableRecord.get(i));
                }*/
            }
        } catch (Exception e) {
            Log.e("dcd", e.getMessage());
            // TODO: handle exception
        } finally {
            db.close();
            //   cursor.close();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

