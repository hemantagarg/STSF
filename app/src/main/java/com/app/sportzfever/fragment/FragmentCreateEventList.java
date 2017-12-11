package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterUpcomingTournamentEvent;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.UpcomingEvent;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentCreateEventList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterUpcomingTournamentEvent adapterUpcomingEvent;
    private UpcomingEvent upcomingEvent;
    private ArrayList<UpcomingEvent> arrayList;
   // private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";
    private String teamid = "", teamavtarid = "";
    public static FragmentCreateEventList fragment_teamJoin_request;
    private final String TAG = FragmentCreateEventList.class.getSimpleName();
    private boolean isTeamOwnerOrCaptain = false;
    private ArrayList<String> listShare = new ArrayList<>();
    private ArrayAdapter<String> adapterShare;
    private Spinner spinnerShareWith;

    public static FragmentCreateEventList getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentCreateEventList();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragement_create_event, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        list_request = (RecyclerView) view.findViewById(R.id.floating_create_event);
        layoutManager = new LinearLayoutManager(context);
        spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        listShare.add("Event (Public)");
        listShare.add("Meet Up (With In Team )");
        listShare.add("Practise (With In Team )");
        listShare.add("Match (Public )");
        adapterShare = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listShare);
        spinnerShareWith.setAdapter(adapterShare);
        getBundle();
        setlistener();
        getServicelistRefresh();
    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            teamid = bundle.getString("teamid");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setlistener() {



    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            if (arrayList.get(position).getEventType().equalsIgnoreCase("MATCH")) {
                if (isTeamOwnerOrCaptain) {
                    FragmentCheckPlayerAvailability fragment_postFeed = new FragmentCheckPlayerAvailability();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", teamid);
                    bundle.putString("eventId", arrayList.get(position).getId());
                    bundle.putString("playersCount", arrayList.get(position).getPlayersCount());
                    fragment_postFeed.setArguments(bundle);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragment_postFeed, true);
                } else {
                    if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_ENDED)) {
                        Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                        FragmentUpcomingMatchDetails fragmentupcomingdetals = new FragmentUpcomingMatchDetails();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_STARTED)) {
                        Fragment_LiveMatch_Details fragmentupcomingdetals = new Fragment_LiveMatch_Details();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    }
                }
            }
        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_UPCOMINGEVENTS + AppUtils.getUserId(context) + "/" + 87 + "/" + AppUtils.getAuthToken(context);
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

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setId(jo.getString("id"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        upcomingEvent.setLocation(jo.getString("location"));
                        upcomingEvent.setEventType(jo.getString("eventType"));
                        upcomingEvent.setTeam1ProfilePicture(jo.getString("team1ProfilePicture"));
                        upcomingEvent.setTeam2ProfilePicture(jo.getString("team2ProfilePicture"));
                        upcomingEvent.setTeam1Name(jo.getString("team1Name"));
                        upcomingEvent.setPlayersCount(jo.getString("playersCount"));
                        upcomingEvent.setTeam2Name(jo.getString("team2Name"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        if (jo.has("matchStatus")) {
                            upcomingEvent.setMatchStatus(jo.getString("matchStatus"));
                        }
                        JSONObject j1 = jo.getJSONObject("startDate");

                        upcomingEvent.setDayName(j1.getString("dayName"));
                        upcomingEvent.setMonthName(j1.getString("monthName"));
                        upcomingEvent.setDate(j1.getString("date"));
                        upcomingEvent.setTime(j1.getString("time"));

                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }


                    adapterUpcomingEvent = new AdapterUpcomingTournamentEvent(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterUpcomingEvent);


                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Upcoming Event found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Upcoming Event found");

                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    JSONObject eventtime = jObject.optJSONObject("startDate");
                    //  maxlistLength = jObject.getString("total");


                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setId(jo.getString("id"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        upcomingEvent.setLocation(jo.getString("location"));
                        upcomingEvent.setEventType(jo.getString("eventType"));
                        upcomingEvent.setTeam1ProfilePicture(jo.getString("team1ProfilePicture"));
                        upcomingEvent.setTeam2ProfilePicture(jo.getString("team2ProfilePicture"));
                        upcomingEvent.setTeam1Name(jo.getString("team1Name"));
                        upcomingEvent.setTeam2Name(jo.getString("team2Name"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        if (jo.has("matchStatus")) {
                            upcomingEvent.setMatchStatus(jo.getString("matchStatus"));
                        }
                        JSONObject j1 = jo.getJSONObject("startDate");

                        upcomingEvent.setDayName(j1.getString("dayName"));
                        upcomingEvent.setMonthName(j1.getString("monthName"));
                        upcomingEvent.setDate(j1.getString("date"));
                        upcomingEvent.setTime(j1.getString("time"));

                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

                    adapterUpcomingEvent.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterUpcomingEvent.notifyDataSetChanged();
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

