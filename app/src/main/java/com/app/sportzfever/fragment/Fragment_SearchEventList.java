package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterSearchEventList;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSearchPeoples;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_SearchEventList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterSearchEventList adapterUserFriendList;
    private ModelSearchPeoples userFriendList;
    private TextView text_nodata;
    private ArrayList<ModelSearchPeoples> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    public static Fragment_SearchEventList fragment_friend_request;
    private final String TAG = Fragment_SearchEventList.class.getSimpleName();
    private String keyword = "";
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String maxlistLength = "";

    public static Fragment_SearchEventList getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_SearchEventList();
        return fragment_friend_request;
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
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();
        getBundle();
    }

    private void getBundle() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                String data = bundle.getString("data");
                keyword = bundle.getString("keyword");
                JSONObject jsonObject = new JSONObject(data);
                JSONObject peoples = jsonObject.getJSONObject("events");

                JSONArray peoplesArray = peoples.getJSONArray("events");
                maxlistLength = peoples.getString("totalEvent");
                arrayList.clear();
                for (int i = 0; i < peoplesArray.length(); i++) {

                    JSONObject jo = peoplesArray.getJSONObject(i);

                    userFriendList = new ModelSearchPeoples();
                    userFriendList.setUserId(jo.getString("userId"));
                    userFriendList.setId(jo.getString("id"));
                    userFriendList.setGoing(jo.getString("going"));
                    userFriendList.setNotGoing(jo.getString("notGoing"));
                    userFriendList.setInterested(jo.getString("interested"));
                    userFriendList.setInvite(jo.getString("invite"));
                    userFriendList.setStatus(jo.getString("status"));
                    userFriendList.setTitle(jo.getString("title"));
                    userFriendList.setDescription(jo.getString("description"));
                    userFriendList.setLocation(jo.getString("location"));
                    userFriendList.setEventImage(jo.getString("eventImage"));
                    userFriendList.setEventType(jo.getString("eventType"));
                    userFriendList.setLocationUrl(jo.getString("locationUrl"));
                    userFriendList.setIsActive(jo.getString("isActive"));
                    userFriendList.setIsDeleted(jo.getString("isDeleted"));


                    JSONObject dateTime = jo.getJSONObject("startDate");
                    userFriendList.setStartDate(dateTime.getString("date") + " " + dateTime.getString("ShortMonthName")
                            + " " + dateTime.getString("year") + " " + dateTime.getString("time"));

                    JSONObject endDate = jo.getJSONObject("endDate");
                    userFriendList.setEndDate(endDate.getString("date") + " " + endDate.getString("ShortMonthName")
                            + " " + endDate.getString("year") + " " + endDate.getString("time"));

                    userFriendList.setRowType(1);

                    arrayList.add(userFriendList);
                }
                adapterUserFriendList = new AdapterSearchEventList(getActivity(), this, arrayList);
                list_request.setAdapter(adapterUserFriendList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicelistRefresh();
            }
        });

        list_request.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
                                    userFriendList = new ModelSearchPeoples();
                                    userFriendList.setRowType(2);
                                    arrayList.add(userFriendList);

                                    recyclerView.post(new Runnable() {
                                        public void run() {
                                            adapterUserFriendList.notifyItemInserted(arrayList.size() - 1);
                                        }
                                    });

                                    skipCount = skipCount + 10;

                                    try {
                                        if (AppUtils.isNetworkAvailable(context)) {
                                            onLoadMore();
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

    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            if (arrayList.get(position).getUserId().equalsIgnoreCase("FRIENDS")) {
                acceptTeamrequest(arrayList.get(position).getUserId(), AppConstant.UNFRIEND);
            } else {
                acceptTeamrequest(arrayList.get(position).getUserId(), AppConstant.ADDFRIEND);
            }


        } else if (flag == 2) {
            FragmentUser_Details fragmentUser_details = new FragmentUser_Details();
            Bundle b = new Bundle();
            b.putString("id", arrayList.get(position).getUserId());
            fragmentUser_details.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentUser_details, true);
        }
    }


    private void acceptTeamrequest(String id, String ADDFRIEND) {
        try {
            AppUtils.onKeyBoardDown(context);
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("toUserId", id);
                jsonObject.put("fromUserId", AppUtils.getUserId(context));
                jsonObject.put("type", ADDFRIEND);

                // http://sfscoring.betasportzfever.com/getNotifications/155
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ADDASFRIEND;
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onLoadMore() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  http://sfscoring.betasportzfever.com/getFeeds/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52

                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCH + AppUtils.getUserId(context)
                        + "/" + "ALL/" + keyword + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(4, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getServicelistRefresh() {
        AppUtils.onKeyBoardDown(context);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCH + AppUtils.getUserId(context)
                        + "/" + "ALL/" + keyword + "/0/" + AppUtils.getAuthToken(context);
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
                AppUtils.onKeyBoardDown(context);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    JSONObject peoples = data.getJSONObject("events");

                    JSONArray peoplesArray = peoples.getJSONArray("events");
                    maxlistLength = peoples.getString("totalEvent");
                    arrayList.clear();
                    for (int i = 0; i < peoplesArray.length(); i++) {

                        JSONObject jo = peoplesArray.getJSONObject(i);

                        userFriendList = new ModelSearchPeoples();
                        userFriendList.setUserId(jo.getString("userId"));
                        userFriendList.setId(jo.getString("id"));
                        userFriendList.setGoing(jo.getString("going"));
                        userFriendList.setNotGoing(jo.getString("notGoing"));
                        userFriendList.setInterested(jo.getString("interested"));
                        userFriendList.setInvite(jo.getString("invite"));
                        userFriendList.setStatus(jo.getString("status"));
                        userFriendList.setTitle(jo.getString("title"));
                        userFriendList.setDescription(jo.getString("description"));
                        userFriendList.setLocation(jo.getString("location"));
                        userFriendList.setEventImage(jo.getString("eventImage"));
                        userFriendList.setEventType(jo.getString("eventType"));
                        userFriendList.setLocationUrl(jo.getString("locationUrl"));
                        userFriendList.setIsActive(jo.getString("isActive"));
                        userFriendList.setIsDeleted(jo.getString("isDeleted"));

                        JSONObject dateTime = jo.getJSONObject("startDate");
                        userFriendList.setStartDate(dateTime.getString("date") + " " + dateTime.getString("ShortMonthName")
                                + " " + dateTime.getString("year") + " " + dateTime.getString("time"));

                        JSONObject endDate = jo.getJSONObject("endDate");
                        userFriendList.setEndDate(endDate.getString("date") + " " + endDate.getString("ShortMonthName")
                                + " " + endDate.getString("year") + " " + endDate.getString("time"));

                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterUserFriendList = new AdapterSearchEventList(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterUserFriendList);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Friend found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Friend  found");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    JSONObject peoples = data.getJSONObject("events");
                    arrayList.remove(arrayList.size() - 1);
                    JSONArray peoplesArray = peoples.getJSONArray("events");
                    maxlistLength = peoples.getString("totalEvent");
                    for (int i = 0; i < peoplesArray.length(); i++) {

                        JSONObject jo = peoplesArray.getJSONObject(i);

                        userFriendList = new ModelSearchPeoples();
                        userFriendList.setUserId(jo.getString("userId"));
                        userFriendList.setId(jo.getString("id"));
                        userFriendList.setGoing(jo.getString("going"));
                        userFriendList.setNotGoing(jo.getString("notGoing"));
                        userFriendList.setInterested(jo.getString("interested"));
                        userFriendList.setInvite(jo.getString("invite"));
                        userFriendList.setStatus(jo.getString("status"));
                        userFriendList.setTitle(jo.getString("title"));
                        userFriendList.setDescription(jo.getString("description"));
                        userFriendList.setLocation(jo.getString("location"));
                        userFriendList.setEventImage(jo.getString("eventImage"));
                        userFriendList.setEventType(jo.getString("eventType"));
                        userFriendList.setLocationUrl(jo.getString("locationUrl"));
                        userFriendList.setIsActive(jo.getString("isActive"));
                        userFriendList.setIsDeleted(jo.getString("isDeleted"));

                        JSONObject dateTime = jo.getJSONObject("startDate");
                        userFriendList.setStartDate(dateTime.getString("date") + " " + dateTime.getString("ShortMonthName")
                                + " " + dateTime.getString("year") + " " + dateTime.getString("time"));

                        JSONObject endDate = jo.getJSONObject("endDate");
                        userFriendList.setEndDate(endDate.getString("date") + " " + endDate.getString("ShortMonthName")
                                + " " + endDate.getString("year") + " " + endDate.getString("time"));

                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterUserFriendList.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterUserFriendList.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
                }
            } else if (position == 11) {
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

