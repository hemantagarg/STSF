package com.app.sportzfever.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.ImagesListActivity;
import com.app.sportzfever.adapter.AdapterAvtarFeed;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.Images;
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
public class Fragment_AvtarFeed extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterAvtarFeed adapterFeed;
    private ModelFeed modelFeed;
    private ArrayList<ModelFeed> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";
    private EditText edt_text_post;
    private TextView text_nodata;
    private TextView text_post;
    int feedClickedPosition = 0;
    private FloatingActionButton floating_post;

    public static Fragment_AvtarFeed fragment_userFeed;
    private final String TAG = Fragment_AvtarFeed.class.getSimpleName();
    private String avtarid = "";

    public static Fragment_AvtarFeed getInstance() {
        if (fragment_userFeed == null)
            fragment_userFeed = new Fragment_AvtarFeed();
        return fragment_userFeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_avtar_feed, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();

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
        edt_text_post = (EditText) view.findViewById(R.id.edt_text_post);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        text_post = (TextView) view.findViewById(R.id.text_post);
        floating_post = (FloatingActionButton) view.findViewById(R.id.floating_post);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        list_request.setNestedScrollingEnabled(true);
        arrayList = new ArrayList<>();
        getBundle();
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
        floating_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_PostAvtarFeed fragment_postFeed = new Fragment_PostAvtarFeed();
                Bundle bundle = new Bundle();
                bundle.putString("id", AppUtils.getAvtarId(context));
                fragment_postFeed.setArguments(bundle);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragment_postFeed, true);
            }
        });

        text_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_text_post.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    postFeed();
                }
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
                                    modelFeed = new ModelFeed();
                                    modelFeed.setRowType(2);
                                    arrayList.add(modelFeed);

                                    recyclerView.post(new Runnable() {
                                        public void run() {
                                            adapterFeed.notifyItemInserted(arrayList.size() - 1);
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

            Fragment_Comments fragment_comments = new Fragment_Comments();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            b.putInt("likeCount", arrayList.get(position).getLikeCount());
            fragment_comments.setArguments(b);

            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragment_comments, true);
        } else if (flag == 2) {

            Fragment_Likes fragmentLikes = new Fragment_Likes();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            fragmentLikes.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentLikes, true);
        } else if (flag == 3) {
            shareFeed(arrayList.get(position).getFeedId());

        } else if (flag == 6) {
            feedClickedPosition = position;
            likeFeed(arrayList.get(position).getFeedId());

        } else if (flag == 5) {

            Fragment_Share fragment_share = new Fragment_Share();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            fragment_share.setArguments(b);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragment_share, true);

        } else if (flag == 4) {

            if (arrayList.get(position).getImages() != null && arrayList.get(position).getImages().size() > 0) {
                ArrayList<Images> imagesArrayList = arrayList.get(position).getImages();
                Bundle b = new Bundle();
                b.putSerializable("images", imagesArrayList);

                Intent intent = new Intent(context, ImagesListActivity.class);
                intent.putExtra("bundle", b);
                startActivity(intent);
            }

        } else if (flag == 5) {

            likeFeed(arrayList.get(position).getFeedId());

        }
    }


    private void postFeed() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(context));
                jsonObject.put("statusVisiblity", AppConstant.PUBLIC);
                jsonObject.put("statusType", "TEXT");
                jsonObject.put("description", edt_text_post.getText().toString());

                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATESTATUS;
                new CommonAsyncTaskHashmap(21, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void likeFeed(String id) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(context));
                jsonObject.put("feedId", id);
                //   http://sfscoring.betasportzfever.com/likeAndUnlikePost
                String url = JsonApiHelper.BASEURL + JsonApiHelper.LIKEUNLIKEPOST;
                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareFeed(String id) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(context));
                jsonObject.put("statusId", id);

                String url = JsonApiHelper.BASEURL + JsonApiHelper.SHAREFEED;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateFeed(String id) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("description", edt_text_post.getText().toString());
                jsonObject.put("statusId", id);

                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATESTATUS;
                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonbject(url, jsonObject, Request.Method.PUT);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFeed(String id) {
        try {

            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.DELETESTATUS + id;
                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonbject(url, null, Request.Method.DELETE);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void unlikeFeed(String id) {
        try {

            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.DELETESTATUS + id;
                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonbject(url, null, Request.Method.DELETE);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //  http://sfscoring.betasportzfever.com/getFeeds/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS_BY_AVTAR + AppUtils.getUserId(context) + "/" + avtarid + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
                //   String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS_BY_AVTAR + "1/69/10/479a44a634f82b0394f78352d302ec36";
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbjectNoProgress(url, null, Request.Method.GET);

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
                //  http://sfscoring.betasportzfever.com/getFeedByAvatar/1/69/10/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS_BY_AVTAR + AppUtils.getUserId(context) + "/" + avtarid + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
                // String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FEEDS_BY_AVTAR + "1/69/10/479a44a634f82b0394f78352d302ec36";
                new CommonAsyncTaskHashmap(4, context, this).getqueryNoProgress(url);

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
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    if (data.length() > 0) {
                        maxlistLength = jObject.getString("totalFeeds");
                        arrayList.clear();
                        Log.e("jsonsize", "**" + data.length());
                        for (int i = 0; i < data.length(); i++) {

                            JSONObject jo = data.getJSONObject(i);
                            modelFeed = new ModelFeed();

                            modelFeed.setId(jo.getString("id"));
                            modelFeed.setFeedId(jo.getString("id"));
                            modelFeed.setUser(jo.getString("user"));
                            modelFeed.setAvatarProfilePicture(jo.getString("avatarProfilePicture"));
                            modelFeed.setAvatarName(jo.getString("avatarName"));
                            modelFeed.setOriginalAvatarProfilePicture(jo.getString("originalAvatarProfilePicture"));
                            modelFeed.setOriginalAvatarName(jo.getString("originalAvatarName"));
                            modelFeed.setOriginalAvatarType(jo.getString("originalAvatarType"));
                            modelFeed.setAvatarSportName(jo.getString("avatarSportName"));
                            modelFeed.setAvatarType(jo.getString("avatarType"));
                            modelFeed.setUserName(jo.getString("userName"));
                            modelFeed.setDateString(jo.getString("dateString"));
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
                            modelFeed.setCommentsCount(jo.getInt("comments"));
                            modelFeed.setLikeCount(jo.getInt("likes"));
                            modelFeed.setShareCount(jo.getInt("shares"));

                            modelFeed.setOriginalAvatar(jo.getString("originalAvatar"));
                            modelFeed.setOriginalUser(jo.getString("originalUser"));
                            modelFeed.setOriginalStatusId(jo.getString("originalStatusId"));
                            modelFeed.setIsShared(jo.getString("isShared"));
                            modelFeed.setIsLiked(jo.getString("isUserLiked"));

                            if (jo.getJSONArray("images") != null) {
                                ArrayList<Images> imagesArrayList = new ArrayList<>();
                                JSONArray imagesArray = jo.getJSONArray("images");
                                for (int j = 0; j < imagesArray.length(); j++) {
                                    JSONObject jsonObject = imagesArray.getJSONObject(j);

                                    Images images = new Images();
                                    images.setId(jsonObject.getString("id"));
                                    images.setStatusId(jsonObject.getString("statusId"));
                                    images.setImage(jsonObject.getString("image"));
                                    if (jsonObject.has("album")) {
                                        images.setAlbum(jsonObject.getString("album"));
                                    }
                                    imagesArrayList.add(images);
                                }
                                modelFeed.setImages(imagesArrayList);
                            }

                            modelFeed.setRowType(1);
                            arrayList.add(modelFeed);
                        }

                        Log.e("size", "**" + arrayList.size());
                        adapterFeed = new AdapterAvtarFeed(getActivity(), this, arrayList);
                        list_request.setAdapter(adapterFeed);
                    }
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 21) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getServicelistRefresh();

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 12) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();

                    if (arrayList.get(feedClickedPosition).getIsLiked().equalsIgnoreCase("0")) {
                        arrayList.get(feedClickedPosition).setIsLiked("1");
                    } else {
                        arrayList.get(feedClickedPosition).setIsLiked("0");
                    }
                    adapterFeed.notifyItemChanged(feedClickedPosition);

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    maxlistLength = jObject.getString("totalFeeds");
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.remove(arrayList.size() - 1);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);
                        modelFeed = new ModelFeed();

                        modelFeed.setId(jo.getString("id"));
                        modelFeed.setFeedId(jo.getString("id"));
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
                        modelFeed.setCommentsCount(jo.getInt("comments"));
                        modelFeed.setLikeCount(jo.getInt("likes"));
                        modelFeed.setShareCount(jo.getInt("shares"));

                        modelFeed.setOriginalAvatar(jo.getString("originalAvatar"));
                        modelFeed.setOriginalUser(jo.getString("originalUser"));
                        modelFeed.setOriginalStatusId(jo.getString("originalStatusId"));
                        modelFeed.setIsShared(jo.getString("isShared"));
                        modelFeed.setIsLiked(jo.getString("isUserLiked"));
                        if (jo.getJSONArray("images") != null) {
                            ArrayList<Images> imagesArrayList = new ArrayList<>();
                            JSONArray imagesArray = jo.getJSONArray("images");
                            for (int j = 0; j < imagesArray.length(); j++) {
                                JSONObject jsonObject = imagesArray.getJSONObject(j);

                                Images images = new Images();
                                images.setId(jsonObject.getString("id"));
                                images.setStatusId(jsonObject.getString("statusId"));
                                images.setImage(jsonObject.getString("image"));
                                if (jsonObject.has("album")) {
                                    images.setAlbum(jsonObject.getString("album"));
                                }
                                imagesArrayList.add(images);
                            }
                            modelFeed.setImages(imagesArrayList);
                        }
                        modelFeed.setRowType(1);
                        arrayList.add(modelFeed);
                    }

                    adapterFeed.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    //  Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    adapterFeed.notifyDataSetChanged();
                    if (arrayList.size() > 0) {
                        arrayList.remove(arrayList.size() - 1);
                    }
                    skipCount = skipCount - 10;
                    loading = true;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (position == 4) {
                skipCount = skipCount - 10;
                loading = true;
            }
        }

    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }

}

