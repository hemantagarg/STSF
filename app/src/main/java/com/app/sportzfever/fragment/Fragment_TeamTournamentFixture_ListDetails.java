package com.app.sportzfever.fragment;

import android.app.Activity;
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
import com.app.sportzfever.adapter.AdapterTeamTournamentFixtureListDetails;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModeTeamTournamnetFixtureDetails;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_TeamTournamentFixture_ListDetails extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterTeamTournamentFixtureListDetails adapterTeamTournamentFixtureListDetails;
    private ModeTeamTournamnetFixtureDetails modeTeamTournamnetFixtureDetails;
    private TextView text_nodata;
    private ArrayList<ModeTeamTournamnetFixtureDetails> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    View view_about;
    private String tournamnetid = "", teamId = "";
    public static Fragment_TeamTournamentFixture_ListDetails fragment_friend_request;
    private final String TAG = Fragment_TeamTournamentFixture_ListDetails.class.getSimpleName();

    public static Fragment_TeamTournamentFixture_ListDetails getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_TeamTournamentFixture_ListDetails();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragment_tournament_fixture_detail, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();
        manageHeaderView();
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
        HeaderViewManager.getInstance().setHeading(true, "Tournament Fixture Detail");
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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();
        getBundle();
    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            tournamnetid = bundle.getString("tournamentid");
            teamId = bundle.getString("teamId");
            String name = bundle.getString("name");
            HeaderViewManager.getInstance().setHeading(true, name);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (flag == 2) {
            String teamNumber = "";
            if (teamId.equalsIgnoreCase(arrayList.get(position).getTeam1Id())) {
                teamNumber = "1";
            } else if (teamId.equalsIgnoreCase(arrayList.get(position).getTeam2Id())) {
                teamNumber = "2";
            }
            acceptReject(AppConstant.ACCEPTED, teamNumber, arrayList.get(position).getId());

        } else if (flag == 3) {
            String teamNumber = "";
            if (teamId.equalsIgnoreCase(arrayList.get(position).getTeam1Id())) {
                teamNumber = "1";
            } else if (teamId.equalsIgnoreCase(arrayList.get(position).getTeam2Id())) {
                teamNumber = "2";
            }
            acceptReject(AppConstant.REJECTED, teamNumber, arrayList.get(position).getId());

        }
    }

    private void acceptReject(String type, String teamNumber, String fixtureId) {
        if (AppUtils.isNetworkAvailable(context)) {
            try {
                JSONObject main = new JSONObject();

                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", fixtureId);
                jsonObject.put("teamNumber", teamNumber);
                array.put(jsonObject);

                main.put("team", array);
                // http://sfscoring.betasportzfever.com/RespondToTeamTournamentFixture/{REJECTED}{ACCEPTED}/{tournamentId}/{teamId}
                String url = JsonApiHelper.BASEURL + JsonApiHelper.RespondToTOURNAMENTFIXTURE + type + "/" + tournamnetid + "/" + teamId;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, main, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void getServicelistRefresh() {
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_ALLTEAMTOURNAMENTFIXTEURESDETAILS + tournamnetid + "/" + teamId + "/" + AppUtils.getAuthToken(context);
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
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    //  maxlistLength = jObject.getString("total");
                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);
                        JSONObject jo1 = jo.getJSONObject("matchDate");
                        modeTeamTournamnetFixtureDetails = new ModeTeamTournamnetFixtureDetails();
                        modeTeamTournamnetFixtureDetails.setId(jo.getString("id"));
                        modeTeamTournamnetFixtureDetails.setTeam1Id(jo.getString("team1Id"));
                        modeTeamTournamnetFixtureDetails.setTeam2Id(jo.getString("team2Id"));
                        modeTeamTournamnetFixtureDetails.setTeam1Name(jo.getString("team1Name"));
                        modeTeamTournamnetFixtureDetails.setTeam2Name(jo.getString("team2Name"));
                        modeTeamTournamnetFixtureDetails.setDate(jo1.getString("date"));
                        modeTeamTournamnetFixtureDetails.setMonthName(jo1.getString("monthName"));
                        modeTeamTournamnetFixtureDetails.setYear(jo1.getString("year"));
                        modeTeamTournamnetFixtureDetails.setTime(jo1.getString("time"));
                        modeTeamTournamnetFixtureDetails.setDayName(jo1.getString("dayName"));

                        modeTeamTournamnetFixtureDetails.setRowType(1);

                        arrayList.add(modeTeamTournamnetFixtureDetails);
                    }
                    adapterTeamTournamentFixtureListDetails = new AdapterTeamTournamentFixtureListDetails(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTeamTournamentFixtureListDetails);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Data found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Data  found");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    //   maxlistLength = jObject.getString("total");
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.remove(arrayList.size() - 1);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);
                        JSONObject jo1 = jo.getJSONObject("matchDate");

                        modeTeamTournamnetFixtureDetails = new ModeTeamTournamnetFixtureDetails();
                        modeTeamTournamnetFixtureDetails.setId(jo.getString("id"));
                        modeTeamTournamnetFixtureDetails.setTeam1Name(jo.getString("team1Name"));
                        modeTeamTournamnetFixtureDetails.setTeam2Name(jo.getString("team2Name"));
                        modeTeamTournamnetFixtureDetails.setDate(jo1.getString("date"));
                        modeTeamTournamnetFixtureDetails.setMonthName(jo1.getString("monthName"));
                        modeTeamTournamnetFixtureDetails.setYear(jo1.getString("year"));
                        modeTeamTournamnetFixtureDetails.setTime(jo1.getString("time"));
                        modeTeamTournamnetFixtureDetails.setDayName(jo1.getString("dayName"));

                        arrayList.add(modeTeamTournamnetFixtureDetails);
                    }
                    adapterTeamTournamentFixtureListDetails.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterTeamTournamentFixtureListDetails.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getServicelistRefresh();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
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

