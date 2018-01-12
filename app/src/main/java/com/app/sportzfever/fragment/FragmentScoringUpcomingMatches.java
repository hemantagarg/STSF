package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingMatches;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentScoringUpcomingMatches extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
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

    public static FragmentScoringUpcomingMatches fragment_teamJoin_request;
    private final String TAG = FragmentScoringUpcomingMatches.class.getSimpleName();

    public static FragmentScoringUpcomingMatches getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentScoringUpcomingMatches();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_matches, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();

        getServicelistRefresh();
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
            if (arrayList.get(position).getMatchStatus().equals("NOT STARTED")) {
                if (arrayList.get(position).getCanEditLineup().equals("1")) {
                    FragmentScoringPrepareLineup fragmentupcomingdetals = new FragmentScoringPrepareLineup();
                    Bundle b = new Bundle();
                    b.putString("eventId", arrayList.get(position).getEventId());
                    b.putString("matchId", arrayList.get(position).getMatchId());
                    b.putString("team1Id", arrayList.get(position).getTeam1Id());
                    b.putString("team2Id", arrayList.get(position).getTeam2Id());
                    b.putBoolean("isTeam1", true);
                    b.putString("playersCount", arrayList.get(position).getNumberOfPlayers());
                    b.putString("title", arrayList.get(position).getTeam1Name());
                    b.putString("team1Name", arrayList.get(position).getTeam1Name());
                    b.putString("team2Name", arrayList.get(position).getTeam2Name());
                    fragmentupcomingdetals.setArguments(b);
                    fragmentupcomingdetals.setTargetFragment(FragmentScoringUpcomingMatches.this, AppConstant.FRAGMENT_CODE);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                } else {

                }
            } else {
                if (arrayList.get(position).getIsActiveScorer().equals("1")) {
                    FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                    Bundle b = new Bundle();
                    b.putString("eventId", arrayList.get(position).getEventId());
                    fragmentSoringMatchDetails.setArguments(b);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSoringMatchDetails, true);
                } else {
                    Toast.makeText(context, "you are not a scorer for this match", Toast.LENGTH_SHORT).show();
                }
            }
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

                    //  data = jObject.getString("total");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelUpcomingMatches = new ModelUpcomingMatches();

                        modelUpcomingMatches.setId(jo.getString("id"));
                        modelUpcomingMatches.setEventId(jo.getString("eventId"));

                        modelUpcomingMatches.setLocation(jo.getString("location"));
                        modelUpcomingMatches.setTeam1Id(jo.getString("team1Id"));
                        modelUpcomingMatches.setTeam2Id(jo.getString("team2Id"));
                        modelUpcomingMatches.setMatchStatus(jo.getString("matchStatus"));
                        modelUpcomingMatches.setMatchId(jo.getString("matchId"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
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
                        modelUpcomingMatches.setTournamentName(jo.getString("tournamentName"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
                        modelUpcomingMatches.setTeam2Name(jo.getString("team2Name"));
                        modelUpcomingMatches.setMatchId(jo.getString("matchId"));
                        modelUpcomingMatches.setNumberOfPlayers(jo.getString("numberOfPlayers"));
                        modelUpcomingMatches.setTeam1Id(jo.getString("team1Id"));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

