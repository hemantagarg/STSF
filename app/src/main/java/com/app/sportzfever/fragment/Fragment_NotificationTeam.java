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
import com.app.sportzfever.adapter.AdapterNotificationTeam;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelNotification;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_NotificationTeam extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterNotificationTeam adapterNotificationTeam;
    private ModelNotification modelNotification;
    private TextView text_nodata, text_markRead;
    private ArrayList<ModelNotification> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;

    public static Fragment_NotificationTeam fragment_notification;
    private int clickedPosition = 0;

    public static Fragment_NotificationTeam getInstance() {
        if (fragment_notification == null)
            fragment_notification = new Fragment_NotificationTeam();
        return fragment_notification;
    }

    @Override
    public void onResume() {
        super.onResume();
        getServicelistRefresh();
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
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        text_markRead = (TextView) view.findViewById(R.id.text_markRead);
        text_markRead.setVisibility(View.VISIBLE);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();
    }

    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getServicelistRefresh();
            }
        });

        text_markRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markNotificationRead(true);
            }
        });

    }

    @Override
    public void onItemClickListener(int position, int flag) {

        if (flag == 1) {
            clickedPosition = position;
            if (arrayList.get(position).getReadStatus().equals("0")) {
                markNotificationRead(false);
            }
            if (arrayList.get(position).getCategory().equals(AppConstant.TEAM)) {
                Fragment_Team_Details fragmentUser_details = new Fragment_Team_Details();
                Bundle b = new Bundle();
                b.putString("id", arrayList.get(position).getActionId());
                fragmentUser_details.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);
            } else if (arrayList.get(position).getCategory().equals(AppConstant.MATCH_PAST)) {
                Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getActionId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getCategory().equalsIgnoreCase(AppConstant.MATCH_UP)) {
                FragmentUpcomingMatchDetails fragmentupcomingdetals = new FragmentUpcomingMatchDetails();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getActionId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getCategory().equalsIgnoreCase(AppConstant.MATCH_LIVE)) {
                Fragment_LiveMatch_Details fragmentupcomingdetals = new Fragment_LiveMatch_Details();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getActionId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getCategory().equals(AppConstant.USER_TYPE)) {
                FragmentUser_Details fragmentUser_details = new FragmentUser_Details();
                Bundle b = new Bundle();
                b.putString("id", arrayList.get(position).getActionId());
                b.putString("currentTab", GlobalConstants.TAB_NOTIFCATION_BAR);
                fragmentUser_details.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);
            } else if (arrayList.get(position).getCategory().equals(AppConstant.EVENT)) {
                Fragment_EvenDetail fragmentupcomingdetals = new Fragment_EvenDetail();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getActionId());
                b.putString("currentTab", AppConstant.CURRENT_SELECTED_TAB);
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getCategory().equals(AppConstant.STATUS)) {
                FragmentFeedDetails fragmentUser_details = new FragmentFeedDetails();
                Bundle b = new Bundle();
                b.putString("id", arrayList.get(position).getActionId());
                fragmentUser_details.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);
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
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_NOTIFICATIONTEAM + AppUtils.getUserId(context) + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markNotificationRead(boolean isAllRead) {
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                int type = 2;
                if (isAllRead) {
                    jsonObject.put("isRead", "ALLREAD");
                } else {
                    type = 3;
                    jsonObject.put("isRead", "READ");
                    jsonObject.put("id", arrayList.get(clickedPosition).getId());
                }

                String url = JsonApiHelper.BASEURL + JsonApiHelper.NOTIFICATION_READ;
                new CommonAsyncTaskHashmap(type, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

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
                /*    "UnreadNotification":0,
                            "totalTeamNotification":35,*/
                    if (jObject.has("UnreadNotification")) {
                        Dashboard.getInstance().manageNotificationCount(jObject.getString("UnreadNotification"));
                    }
                    arrayList.clear();

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelNotification = new ModelNotification();
                        modelNotification.setId(jo.getString("id"));
                        modelNotification.setNotificationText(jo.getString("notificationText"));

                        modelNotification.setDatetime(jo.getString("datetime"));
                        modelNotification.setReadStatus(jo.getString("readStatus"));
                        modelNotification.setIsTeamNotification(jo.getString("isTeamNotification"));
                        modelNotification.setUserPorfile(jo.getString("userPorfile"));
                        modelNotification.setReadStatus(jo.getString("readStatus"));
                        modelNotification.setCategory(jo.getString("category"));
                        modelNotification.setActionId(jo.getString("actionId"));

                        modelNotification.setRowType(1);

                        arrayList.add(modelNotification);
                    }
                    adapterNotificationTeam = new AdapterNotificationTeam(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterNotificationTeam);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Notification found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Notification found");

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

            } else if (position == 3) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();
                }
            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    //   maxlistLength = jObject.getString("total");
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.remove(arrayList.size() - 1);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelNotification = new ModelNotification();
                        modelNotification.setId(jo.getString("id"));
                        modelNotification.setNotificationText(jo.getString("notificationText"));

                        modelNotification.setDatetime(jo.getString("datetime"));
                        modelNotification.setReadStatus(jo.getString("readStatus"));
                        modelNotification.setIsTeamNotification(jo.getString("isTeamNotification"));
                        modelNotification.setUserPorfile(jo.getString("userPorfile"));

                        modelNotification.setRowType(1);

                        arrayList.add(modelNotification);
                    }
                    adapterNotificationTeam.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterNotificationTeam.notifyDataSetChanged();
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

