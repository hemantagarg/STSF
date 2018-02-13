package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterShare;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelShare;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Share extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterShare adapterShare;
    private ModelShare modelShare;
    private ArrayList<ModelShare> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static Fragment_Share fragment_friend_request;
    private final String TAG = Fragment_Share.class.getSimpleName();
    private String feedId = "";
    View view_about;

    public static Fragment_Share getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_Share();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragment_share, container, false);
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
        getBundle();
        manageHeaderView();
        setlistener();
        AppConstant.ISFEEDNEEDTOREFRESH = false;
        getServicelistRefresh();
    }


    private void getBundle() {

        if (b != null) {
            feedId = b.getString("FeedId");

        }
    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Share");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);

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

    private void getServicelistRefresh() {
        HeaderViewManager.getInstance().setProgressLoader(true, false);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_SHARE + feedId;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

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
                HeaderViewManager.getInstance().setProgressLoader(false, false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    //  maxlistLength = jObject.getString("total");
                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelShare = new ModelShare();

                        modelShare.setId(jo.getString("id"));
                        modelShare.setShareDateTime(jo.getString("shareDateTime"));
                        modelShare.setUserName(jo.getString("userName"));
                        modelShare.setUserProfilePicture(jo.getString("userProfilePicture"));


                        modelShare.setRowType(1);

                        arrayList.add(modelShare);
                    }
                    adapterShare = new AdapterShare(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterShare);

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
                    //   maxlistLength = jObject.getString("total");
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.remove(arrayList.size() - 1);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelShare = new ModelShare();

                        modelShare.setId(jo.getString("id"));
                        modelShare.setShareDateTime(jo.getString("shareDateTime"));
                        modelShare.setUserName(jo.getString("userName"));
                        modelShare.setUserProfilePicture(jo.getString("userProfilePicture"));


                        modelShare.setRowType(1);

                        arrayList.add(modelShare);
                    }
                    adapterShare.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterShare.notifyDataSetChanged();
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

