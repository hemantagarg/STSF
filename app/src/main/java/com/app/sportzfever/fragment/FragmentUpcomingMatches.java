package com.app.sportzfever.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.app.sportzfever.adapter.AdapterUpcomingMatches;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingMatches;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentUpcomingMatches extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;

    private AdapterUpcomingMatches adapterUpcomingMatches;
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

    public static FragmentUpcomingMatches fragment_teamJoin_request;
    private final String TAG = FragmentUpcomingMatches.class.getSimpleName();

    public static FragmentUpcomingMatches getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentUpcomingMatches();
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
        Fragment_UpcomingMatch_Details fragmentupcomingdetals = new Fragment_UpcomingMatch_Details();
        Bundle b = new Bundle();
        b.putString("eventId", arrayList.get(position).getEventId());

        fragmentupcomingdetals.setArguments(b);
        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);

    }}



    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_UPCOMINGMATCHES + AppUtils.getAuthToken(context);
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
                getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
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
                        modelUpcomingMatches.setTournamentName(jo.getString("tournamentName"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
                        modelUpcomingMatches.setTeam2Name(jo.getString("team2Name"));
                        modelUpcomingMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelUpcomingMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));


                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelUpcomingMatches.setTime(j1.getString("time"));
                        modelUpcomingMatches.setDate(j1.getString("date"));
                        modelUpcomingMatches.setYear(j1.getString("year"));
                        modelUpcomingMatches.setMonthName(j1.getString("monthName"));
                        modelUpcomingMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelUpcomingMatches.setRowType(1);

                        arrayList.add(modelUpcomingMatches);
                    }


                    adapterUpcomingMatches = new AdapterUpcomingMatches(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterUpcomingMatches);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Upcoming Matches found");

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

                        modelUpcomingMatches = new ModelUpcomingMatches();

                        modelUpcomingMatches.setLocation(jo.getString("location"));
                        modelUpcomingMatches.setTournamentName(jo.getString("tournamentName"));
                        modelUpcomingMatches.setTeam1Name(jo.getString("team1Name"));
                        modelUpcomingMatches.setTeam2Name(jo.getString("team2Name"));
                        modelUpcomingMatches.setTeam1profilePicture(jo.getString("team1profilePicture"));
                        modelUpcomingMatches.setTeam2profilePicture(jo.getString("team2profilePicture"));

                        JSONObject j1 = jo.getJSONObject("matchDate");

                        modelUpcomingMatches.setTime(j1.getString("time"));
                        modelUpcomingMatches.setDate(j1.getString("date"));
                        modelUpcomingMatches.setYear(j1.getString("year"));
                        modelUpcomingMatches.setMonthName(j1.getString("monthName"));
                        modelUpcomingMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelUpcomingMatches.setRowType(1);

                        arrayList.add(modelUpcomingMatches);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

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

