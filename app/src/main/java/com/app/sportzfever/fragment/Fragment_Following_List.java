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
import com.app.sportzfever.adapter.AdapterFollowingList;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelFollowing;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Following_List extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterFollowingList adapterFollowingList;
    private ModelFollowing modelFollowing;
    private TextView text_nodata;
    private ArrayList<ModelFollowing> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static Fragment_Following_List fragment_friend_request;
    private final String TAG = Fragment_Following_List.class.getSimpleName();
    private String avtarid = "";

    public static Fragment_Following_List getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_Following_List();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_followes, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();
        getBundle();
        return view_about;
    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            avtarid = bundle.getString("avtarid");
        }
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
            followUnfollowTeam(arrayList.get(position).getAvatar(), "UNFOLLOw");
        }
    }

    private void followUnfollowTeam(String id, String type) {

        if (AppUtils.isNetworkAvailable(context)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fanUserId", AppUtils.getUserId(context));
                jsonObject.put("avatarId", id);
                jsonObject.put("type", type);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.FOLLOW_UNFOLLOW;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
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
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FOLLOWINGLIST + avtarid + "/" + AppUtils.getAuthToken(context);
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
                    JSONObject follower = data.getJSONObject("following");
                    arrayList.clear();
                    JSONArray player = follower.getJSONArray("player");
                    for (int i = 0; i < player.length(); i++) {

                        JSONObject jo = player.getJSONObject(i);
                        modelFollowing = new ModelFollowing();
                        modelFollowing.setAvatar(jo.getString("avatar"));
                        modelFollowing.setStatus(jo.getString("status"));
                        modelFollowing.setProfilePicture(jo.getString("profilePicture"));
                        modelFollowing.setFan_date_time(jo.getString("fan_date_time"));
                        modelFollowing.setFan_date_time(jo.getString("avatarType"));
                        modelFollowing.setName(jo.getString("name"));
                        modelFollowing.setRowType(1);

                        arrayList.add(modelFollowing);
                    }

                    JSONArray teamfollowers = follower.getJSONArray("team");

                    for (int i = 0; i < teamfollowers.length(); i++) {

                        JSONObject jo = teamfollowers.getJSONObject(i);

                        modelFollowing = new ModelFollowing();
                        modelFollowing.setAvatar(jo.getString("avatar"));
                        modelFollowing.setStatus(jo.getString("status"));
                        modelFollowing.setProfilePicture(jo.getString("profilePicture"));
                        modelFollowing.setFan_date_time(jo.getString("fan_date_time"));
                        modelFollowing.setFan_date_time(jo.getString("avatarType"));
                        modelFollowing.setName(jo.getString("name"));

                        modelFollowing.setRowType(1);

                        arrayList.add(modelFollowing);
                    }
                    adapterFollowingList = new AdapterFollowingList(getActivity(), this, arrayList, avtarid);
                    list_request.setAdapter(adapterFollowingList);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("You are currently not following any player or team found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("You are currently not following any player or team  found");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
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

