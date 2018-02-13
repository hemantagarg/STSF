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

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterPastMatches;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelPastMatches;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentPastMatches extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;

    private AdapterPastMatches adapterPastMatches;
    private ModelPastMatches modelPastMatches;
    private ArrayList<ModelPastMatches> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private TextView text_nodata;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static FragmentPastMatches fragment_teamJoin_request;
    private final String TAG = FragmentPastMatches.class.getSimpleName();

    public static FragmentPastMatches getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentPastMatches();
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

        Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
        Bundle b = new Bundle();
        b.putString("eventId", arrayList.get(position).getEventId());

        fragmentupcomingdetals.setArguments(b);
        Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);

    }

    private void getServicelist() {
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.betasportzfever.com/getNotifications/155
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_NOTIFICATION + AppUtils.getUserId(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_PASTMATCHES + AppUtils.getAuthToken(context);
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
            if (context!=null && isAdded()) {
                getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
            }
            if (position == 1) {

                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    //  data = jObject.getString("total");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelPastMatches = new ModelPastMatches();

                        modelPastMatches.setId(jo.getString("id"));
                        modelPastMatches.setEventId(jo.getString("eventId"));
                        modelPastMatches.setLocation(jo.getString("location"));
                        modelPastMatches.setFirstBattingTeamName(jo.getString("firstBattingTeamName"));
                        modelPastMatches.setSecondBattingTeamName(jo.getString("secondBattingTeamName"));
                        modelPastMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelPastMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));
                        modelPastMatches.setTournamentName(jo.getString("tournamentName"));
                        modelPastMatches.setMatchWinnerTeamName(jo.getString("MatchWinnerTeamName"));
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

                        arrayList.add(modelPastMatches);
                    }

                    adapterPastMatches = new AdapterPastMatches(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterPastMatches);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Past Matches found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Past Matches found");

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    JSONObject eventtime = jObject.optJSONObject("startDate");
                    //  maxlistLength = jObject.getString("total");

                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelPastMatches = new ModelPastMatches();

                        modelPastMatches.setLocation(jo.getString("location"));
                        modelPastMatches.setFirstBattingTeamName(jo.getString("firstBattingTeamName"));
                        modelPastMatches.setSecondBattingTeamName(jo.getString("secondBattingTeamName"));
                        modelPastMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelPastMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));

                        modelPastMatches.setTournamentName(jo.getString("tournamentName"));
                        modelPastMatches.setMatchWinnerTeamName(jo.getString("MatchWinnerTeamName"));
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

                        arrayList.add(modelPastMatches);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

                    adapterPastMatches.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterPastMatches.notifyDataSetChanged();
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

