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
import com.app.sportzfever.adapter.AdapterTournamentJoinRequest;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.TeamJoinRequest;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_TournamentJoin_Request extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterTournamentJoinRequest adapterTournamentJoinRequest;
    private TeamJoinRequest teamJoinRequest;
    private ArrayList<TeamJoinRequest> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";

    public static Fragment_TournamentJoin_Request fragment_teamJoin_request;
    private final String TAG = Fragment_TournamentJoin_Request.class.getSimpleName();

    public static Fragment_TournamentJoin_Request getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_TournamentJoin_Request();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_teamjoin, container, false);
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

        if (flag == 1) {
            accepttournamnetrequest(arrayList.get(position).getId(), AppConstant.ACCEPTED);

        } else if (flag == 2) {

            accepttournamnetrequest(arrayList.get(position).getId(), AppConstant.REJECTED);

        }
    }






    private void accepttournamnetrequest(String id, String accept) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/RespondToMatchchallengeInvitation/41/REJECTED
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ACCEPTREJECTTOURNAMENTINVITATION + id + "/" + accept;
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void getServicelistRefresh() {

        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_TEAMJOINREQUEST + AppUtils.getUserId(context) + "/" + AppUtils.getAuthToken(context);
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
                    JSONObject data = jObject.getJSONObject("data");
                    //  maxlistLength = jObject.getString("total");

                    JSONArray match = data.getJSONArray("invitationFromOrganizerToTeamsForTournament");

                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < match.length(); i++) {

                        JSONObject jo = match.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));

                        teamJoinRequest.setTournamentName(jo.getString("tournamentName"));
                        teamJoinRequest.setTournamentProfilePicture(jo.getString("tournamentProfilePicture"));

                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }

                    adapterTournamentJoinRequest = new AdapterTournamentJoinRequest(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTournamentJoinRequest);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Tournament invite found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Tournament invite found");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 11) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    //  maxlistLength = jObject.getString("total");
                    JSONArray match = data.getJSONArray("invitationFromOrganizerToTeamsForTournament");

                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < match.length(); i++) {

                        JSONObject jo = match.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));

                        teamJoinRequest.setTournamentName(jo.getString("tournamentName"));

                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }

                    adapterTournamentJoinRequest.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterTournamentJoinRequest.notifyDataSetChanged();
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

