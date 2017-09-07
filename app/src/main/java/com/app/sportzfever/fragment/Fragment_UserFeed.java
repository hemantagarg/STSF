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
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterFeed;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelFeed;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_UserFeed extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterFeed adapterFeed;
    private ModelFeed modelFeed;
    private ArrayList<ModelFeed> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static Fragment_UserFeed fragment_userFeed;
    private final String TAG = Fragment_UserFeed.class.getSimpleName();

    public static Fragment_UserFeed getInstance() {
        if (fragment_userFeed == null)
            fragment_userFeed = new Fragment_UserFeed();
        return fragment_userFeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_user_feed, container, false);
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
        list_request.setNestedScrollingEnabled(false);
        arrayList = new ArrayList<>();
        setlistener();

    /*    modelFeed = new ModelFeed();
        modelFeed.setRowType(1);
        arrayList.add(modelFeed);
        arrayList.add(modelFeed);
        arrayList.add(modelFeed);
        arrayList.add(modelFeed);
        arrayList.add(modelFeed);
        arrayList.add(modelFeed);

        adapterFeed = new AdapterFeed(getActivity(), this, arrayList);
        list_request.setAdapter(adapterFeed);*/
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
                                    modelFeed = new ModelFeed();
                                    modelFeed.setRowType(2);
                                    arrayList.add(modelFeed);
                                    adapterFeed.notifyDataSetChanged();

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
        if (flag == 1) {
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Comments(), true);
        } else if (flag == 2) {
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Likes(), true);
        } else if (flag == 3) {
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Share(), true);
        }
    }

    private void getServicelist() {
        try {
            skipCount = 0;

            if (AppUtils.isNetworkAvailable(context)) {
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS + "155";
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
                //  http://sfscoring.betasportzfever.com/getFeeds/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                //         /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS + "155/"+ AppConstant.TOKEN;
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
                    arrayList.clear();

                    Log.e("jsonsize","**"+data.length());
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);
                        modelFeed = new ModelFeed();

                        modelFeed.setId(jo.getString("id"));
                        modelFeed.setUser(jo.getString("user"));
                        modelFeed.setAvatarProfilePicture(jo.getString("avatarProfilePicture"));
                        modelFeed.setAvatarName(jo.getString("avatarName"));
                        modelFeed.setOriginalAvatarProfilePicture(jo.getString("originalAvatarProfilePicture"));
                        modelFeed.setOriginalAvatarName(jo.getString("originalAvatarName"));
                        modelFeed.setOriginalAvatarType(jo.getString("originalAvatarType"));
                        modelFeed.setAvatarSportName(jo.getString("avatarSportName"));
                        modelFeed.setAvatarType(jo.getString("avatarType"));
                        modelFeed.setUserName(jo.getString("userName"));
                        modelFeed.setUserProfilePicture(jo.getString("userProfilePicture"));

                        modelFeed.setStatusVisiblity(jo.getString("statusVisiblity"));
                        modelFeed.setStatusType(jo.getString("statusType"));
                        modelFeed.setDateTimeUpdated(jo.getString("dateTimeUpdated"));
                        modelFeed.setDateTime(jo.getString("dateTime"));
                        modelFeed.setDescription(jo.getString("description"));
                        modelFeed.setOriginalAvatarSportName(jo.getString("originalAvatarSportName"));
                        modelFeed.setAvatar(jo.getString("avatar"));
                        modelFeed.setOriginalUserProfilePicture(jo.getString("originalUserProfilePicture"));
                        modelFeed.setOriginalUserName(jo.getString("originalUserName"));

                        modelFeed.setEvent(jo.getString("event"));
                        modelFeed.setShares(jo.getString("shares"));
                        modelFeed.setOriginalAvatar(jo.getString("originalAvatar"));
                        modelFeed.setOriginalUser(jo.getString("originalUser"));
                        modelFeed.setOriginalStatusId(jo.getString("originalStatusId"));
                        modelFeed.setIsShared(jo.getString("isShared"));

/*
                        if (jo.getJSONArray("images")!=null) {
                            ArrayList<Images> imagesArrayList = new ArrayList<>();
                            JSONArray imagesArray = jo.getJSONArray("images");
                            for (int j = 0; j < imagesArray.length(); j++) {
                                JSONObject jsonObject = imagesArray.getJSONObject(j);

                                Images images = new Images();
                                images.setId(jsonObject.getString("id"));
                                images.setStatusId(jsonObject.getString("statusId"));
                                images.setImage(jsonObject.getString("image"));
                                images.setAlbum(jsonObject.getString("album"));
                                imagesArrayList.add(images);
                            }
                            modelFeed.setImages(imagesArrayList);
                        }
*/

                       /* if (jo.getJSONArray("likes")!=null) {
                            ArrayList<Likes> likesList = new ArrayList<>();
                            JSONArray likesArray = jo.getJSONArray("likes");
                            for (int j = 0; j < likesArray.length(); j++) {
                                JSONObject jsonObject = likesArray.getJSONObject(j);
                                Likes likes = new Likes();
                                likes.setId(jsonObject.getString("id"));
                                likes.setStatus(jsonObject.getString("status"));
                                likes.setUser(jsonObject.getString("user"));
                                likes.setAvatar(jsonObject.getString("avatar"));
                                likes.setLikeDateTime(jsonObject.getString("likeDateTime"));
                                likes.setUserName(jsonObject.getString("userName"));
                                likes.setUserProfilePicture(jsonObject.getString("userProfilePicture"));
                                likesList.add(likes);
                            }
                            modelFeed.setLikes(likesList);
                        }

                        if (jo.getJSONArray("comments")!=null) {
                            ArrayList<Comments> commentsArrayList = new ArrayList<>();
                            JSONArray commentsArray = jo.getJSONArray("comments");

                            for (int j = 0; j < commentsArray.length(); j++) {

                                JSONObject commentObj = commentsArray.getJSONObject(i);
                                Comments comments = new Comments();

                                comments.setId(commentObj.getString("id"));
                                comments.setStatus(commentObj.getString("status"));
                                comments.setUser(commentObj.getString("user"));
                                comments.setAvatar(commentObj.getString("avatar"));
                                comments.setComment(commentObj.getString("comment"));
                                comments.setCommentDateTime(commentObj.getString("commentDateTime"));
                                comments.setUserName(commentObj.getString("userName"));
                                comments.setUserProfilePicture(commentObj.getString("userProfilePicture"));
                                commentsArrayList.add(comments);
                            }
                            modelFeed.setComments(commentsArrayList);
                        }*/

                        modelFeed.setRowType(1);
                        arrayList.add(modelFeed);
                    }

                    Log.e("size","**"+arrayList.size());
                    adapterFeed = new AdapterFeed(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterFeed);

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
                    // maxlistLength = jObject.getString("total");
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.remove(arrayList.size() - 1);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelFeed = new ModelFeed();
                        modelFeed.setMessage(jo.getString("message"));
                        modelFeed.setRowType(1);
                        modelFeed.setDate(jo.getString("message_date"));
                        //   modelFeed.setUserImage(getResources().getString(R.string.img_url) + jo.getString("userImage"));
                        arrayList.add(modelFeed);
                    }
                    adapterFeed.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterFeed.notifyDataSetChanged();
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
    /*@Override
    public void onItemClickListener(int position, int flag) {

        if (flag == 1) {

            Fragment_Comments vendorListFragment = new Fragment_Comments();
            *//*Bundle b = new Bundle();
            b.putString(AppConstant.SERVICEID, arrayList.get(position).getServiceId());
            b.putString(AppConstant.CATEGORYNAME, arrayList.get(position).getServiceName());*//*

           *//* vendorListFragment.setArguments(b);
            setFragment(vendorListFragment);*//*
        }

    }*/
}

