package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.adapter.AdapterTeamJoinRequest;
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
public class Fragment_TeamJoin_Request extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;

    private AdapterTeamJoinRequest adapterTeamJoinRequest;
    private TeamJoinRequest teamJoinRequest;
    private ArrayList<TeamJoinRequest> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static Fragment_TeamJoin_Request fragment_teamJoin_request;
    private final String TAG = Fragment_TeamJoin_Request.class.getSimpleName();

    public static Fragment_TeamJoin_Request getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_TeamJoin_Request();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_notification, container, false);
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

   /*     Intent in = new Intent(context, ActivityChat.class);
        if (arrayList.get(position).getUserId().equalsIgnoreCase(AppUtils.getUserIdChat(context))) {
            in.putExtra("reciever_id", arrayList.get(position).getSenderID());
        } else {
            in.putExtra("reciever_id", arrayList.get(position).getUserId());
        }
        in.putExtra("name", arrayList.get(position).getSenderName());
        in.putExtra("image", arrayList.get(position).getReceiverImage());
        in.putExtra("searchID", arrayList.get(position).getSearchId());
        startActivity(in);*/
    }

    private void getServicelist() {
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.betasportzfever.com/getNotifications/155
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_NOTIFICATION + "155";
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);
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
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_TEAMJOINREQUEST + "155/"+ AppConstant.TOKEN;
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
JSONArray match=data.getJSONArray("matchAndPrctiseInvitation");
                    JSONArray matchchallenged =data.getJSONArray("matchchallengeInvitation");
                    JSONArray scorerInvitation =data.getJSONArray("scorerInvitation");
                    JSONArray invitationFromTeamToJoin =data.getJSONArray("invitationFromTeamToJoin");

                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < match.length(); i++) {

                        JSONObject jo = match.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setInviteStatus(jo.getString("inviteStatus"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setEventType(jo.getString("eventType"));
                        teamJoinRequest.setEventTitle(jo.getString("eventTitle"));



                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }
                    for (int i = 0; i < matchchallenged.length(); i++) {

                        JSONObject jo = matchchallenged.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setMatchDate(jo.getString("matchDate"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                        teamJoinRequest.setLocation(jo.getString("location"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setOppositionTeamName(jo.getString("oppositionTeamName"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    } for (int i = 0; i < scorerInvitation.length(); i++) {

                        JSONObject jo = scorerInvitation.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setMatchDate(jo.getString("matchDate"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                        //teamJoinRequest.setLocation(jo.getString("location"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setEventTitle(jo.getString("eventTitle"));
                        teamJoinRequest.setOppositionTeamName(jo.getString("oppositionTeamName"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }
                    for (int i = 0; i < invitationFromTeamToJoin.length(); i++) {

                        JSONObject jo = invitationFromTeamToJoin.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setRequestSentAt(jo.getString("requestSentAt"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));

                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));

                        teamJoinRequest.setPlayerAvatarName(jo.getString("playerAvatarName"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }
                    adapterTeamJoinRequest = new AdapterTeamJoinRequest(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTeamJoinRequest);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    //  maxlistLength = jObject.getString("total");
                    JSONArray match=data.getJSONArray("matchAndPrctiseInvitation");
                    JSONArray matchchallenged =data.getJSONArray("matchchallengeInvitation");
                    JSONArray scorerInvitation =data.getJSONArray("scorerInvitation");
                    JSONArray invitationFromTeamToJoin =data.getJSONArray("invitationFromTeamToJoin");
                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < match.length(); i++) {

                        JSONObject jo = match.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setInviteStatus(jo.getString("inviteStatus"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                        teamJoinRequest.setEventType(jo.getString("eventType"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setEventTitle(jo.getString("eventTitle"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    } for (int i = 0; i < matchchallenged.length(); i++) {

                        JSONObject jo = matchchallenged.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setMatchDate(jo.getString("matchDate"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                        teamJoinRequest.setOppositionTeamName(jo.getString("oppositionTeamName"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setLocation(jo.getString("location"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }
                    for (int i = 0; i < scorerInvitation.length(); i++) {

                        JSONObject jo = scorerInvitation.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setMatchDate(jo.getString("matchDate"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));
                       // teamJoinRequest.setLocation(jo.getString("location"));
                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        teamJoinRequest.setEventTitle(jo.getString("eventTitle"));
                        teamJoinRequest.setOppositionTeamName(jo.getString("oppositionTeamName"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }for (int i = 0; i < invitationFromTeamToJoin.length(); i++) {

                        JSONObject jo = invitationFromTeamToJoin.getJSONObject(i);

                        teamJoinRequest = new TeamJoinRequest();

                        teamJoinRequest.setId(jo.getString("id"));
                        teamJoinRequest.setRequestSentAt(jo.getString("requestSentAt"));
                        teamJoinRequest.setTeamName(jo.getString("teamName"));

                        teamJoinRequest.setTeamProfilePicture(jo.getString("teamProfilePicture"));

                        teamJoinRequest.setPlayerAvatarName(jo.getString("playerAvatarName"));
                        teamJoinRequest.setRowType(1);

                        arrayList.add(teamJoinRequest);
                    }
                    adapterTeamJoinRequest.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterTeamJoinRequest.notifyDataSetChanged();
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

