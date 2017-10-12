package com.app.sportzfever.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.ViewMatchScoreCard;
import com.app.sportzfever.adapter.AdapterAllTournamentMatches;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAllTournamentMatches;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentTournamentAllMatches extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private Button btn_batting, btn_bowling, btn_fielding;
    private AdapterAllTournamentMatches adapterAllTournamentMatches;
    private ModelAllTournamentMatches modelAllTournamentMatches;
    private ArrayList<ModelAllTournamentMatches> arrayList;
   // private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private TextView text_nodata;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static FragmentTournamentAllMatches fragment_teamJoin_request;
    private final String TAG = FragmentTournamentAllMatches.class.getSimpleName();

    public static FragmentTournamentAllMatches getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentTournamentAllMatches();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_tournament_matches, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        btn_fielding = (Button) view.findViewById(R.id.btn_fielding);
        btn_bowling = (Button) view.findViewById(R.id.btn_bowling);
        btn_batting = (Button) view.findViewById(R.id.btn_batting);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();

        getServicelistRefresh();
    }

    private void setlistener() {
    /*    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getServicelistRefresh();
            }
        });*/
        btn_batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
            }
        });
        btn_bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
            }
        });
        btn_fielding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));

            }
        });
/*
        list_request.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if ((AppUtils.isNetworkAvailable(context))) {

                    if (!maxlistLength.equalsIgnoreCase(arrayList.size() + "")) {
                        if (dy > 0) //check for scroll down
                        {
                            visibleItemCount = layoutManager.getChildCount();
                            totalItemCount = layoutManager.getItemCount();
                            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loading = false;
                                    modelNotification = new ModelNotification();
                                    modelNotification.setRowType(2);
                                    arrayList.add(modelNotification);
                                    adapterNotification.notifyDataSetChanged();

                                    skipCount = skipCount + 10;

                                    try {

                                        if (AppUtils.isNetworkAvailable(context)) {

                                            String url = JsonApiHelper.BASEURL + JsonApiHelper.BASEURL;
                                            //  new CommonAsyncTaskHashmap(1, context, Fragment_Chat.this).getqueryNoProgress(url);

                                        } else {
                                            Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    } else {

                        Log.e("maxlength", "*" + arrayList.size());
                    }
                }
            }
        });
*/

    }

    @Override
    public void onItemClickListener(int position, int flag) {

        Intent inte = new Intent(context, ViewMatchScoreCard.class);
        inte.putExtra("eventId", arrayList.get(position).getEventId());
        startActivity(inte);
    }



    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_ALLTOURNAMENTMATCHES +3+"/" + AppUtils.getAuthToken(context);
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
                    JSONArray data = jObject.getJSONArray("data");

                    //  data = jObject.getString("total");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelAllTournamentMatches = new ModelAllTournamentMatches();

                        modelAllTournamentMatches.setId(jo.getString("id"));
                        modelAllTournamentMatches.setEventId(jo.getString("eventId"));
                        modelAllTournamentMatches.setLocation(jo.getString("location"));
                        modelAllTournamentMatches.setFirstBattingTeamName(jo.getString("firstBattingTeamName"));
                        modelAllTournamentMatches.setSecondBattingTeamName(jo.getString("secondBattingTeamName"));
                        modelAllTournamentMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelAllTournamentMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));
                        modelAllTournamentMatches.setTournamentName(jo.getString("tournamentName"));
                        modelAllTournamentMatches.setMatchWinnerTeamName(jo.getString("MatchWinnerTeamName"));
                        modelAllTournamentMatches.setWinString(jo.getString("winString"));
                        modelAllTournamentMatches.setTotalRunsScoredTeam1(jo.getString("totalRunsScoredTeam1"));
                        modelAllTournamentMatches.setTotalRunsScoredTeam2(jo.getString("totalRunsScoredTeam2"));
                        modelAllTournamentMatches.setPlayedOversTeam1(jo.getString("playedOversTeam1"));
                        modelAllTournamentMatches.setPlayedOversTeam2(jo.getString("playedOversTeam2"));
                        modelAllTournamentMatches.setFirstBattingWickets(jo.getString("firstBattingWickets"));
                        modelAllTournamentMatches.setSecondBattingWickets(jo.getString("secondBattingWickets"));
                        modelAllTournamentMatches.setStageName(jo.getString("stageName"));

                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelAllTournamentMatches.setTime(j1.getString("time"));
                        modelAllTournamentMatches.setDate(j1.getString("date"));
                        modelAllTournamentMatches.setYear(j1.getString("year"));
                        modelAllTournamentMatches.setMonthName(j1.getString("monthName"));
                        modelAllTournamentMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelAllTournamentMatches.setRowType(1);

                        arrayList.add(modelAllTournamentMatches);
                    }

                    adapterAllTournamentMatches = new AdapterAllTournamentMatches(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterAllTournamentMatches);

                   /* if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }*/
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Past Matches found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Past Matches found");

                    /*if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }*/
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    JSONObject eventtime = jObject.optJSONObject("startDate");
                    //  maxlistLength = jObject.getString("total");


                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelAllTournamentMatches = new ModelAllTournamentMatches();

                        modelAllTournamentMatches.setLocation(jo.getString("location"));
                        modelAllTournamentMatches.setFirstBattingTeamName(jo.getString("firstBattingTeamName"));
                        modelAllTournamentMatches.setSecondBattingTeamName(jo.getString("secondBattingTeamName"));
                        modelAllTournamentMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelAllTournamentMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));

                        modelAllTournamentMatches.setTournamentName(jo.getString("tournamentName"));
                        modelAllTournamentMatches.setMatchWinnerTeamName(jo.getString("MatchWinnerTeamName"));
                        modelAllTournamentMatches.setWinString(jo.getString("winString"));

                        modelAllTournamentMatches.setTotalRunsScoredTeam1(jo.getString("totalRunsScoredTeam1"));
                        modelAllTournamentMatches.setTotalRunsScoredTeam2(jo.getString("totalRunsScoredTeam2"));
                        modelAllTournamentMatches.setPlayedOversTeam1(jo.getString("playedOversTeam1"));
                        modelAllTournamentMatches.setPlayedOversTeam2(jo.getString("playedOversTeam2"));
                        modelAllTournamentMatches.setFirstBattingWickets(jo.getString("firstBattingWickets"));
                        modelAllTournamentMatches.setSecondBattingWickets(jo.getString("secondBattingWickets"));
                        modelAllTournamentMatches.setStageName(jo.getString("stageName"));

                       JSONObject j1 = jo.getJSONObject("matchDate");

                        modelAllTournamentMatches.setTime(j1.getString("time"));
                        modelAllTournamentMatches.setDate(j1.getString("date"));
                        modelAllTournamentMatches.setYear(j1.getString("year"));
                        modelAllTournamentMatches.setMonthName(j1.getString("monthName"));
                        modelAllTournamentMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelAllTournamentMatches.setRowType(1);

                        arrayList.add(modelAllTournamentMatches);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

                    adapterAllTournamentMatches.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterAllTournamentMatches.notifyDataSetChanged();
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

