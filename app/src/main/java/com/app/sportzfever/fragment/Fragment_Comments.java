package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterComments;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.Comments;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Comments extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private EditText edit_message;
    private ImageView send_message;
    private Bundle b;
    private Activity context;
    private AdapterComments adapterComments;
    private Comments comments;
    private ArrayList<Comments> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private RelativeLayout rel_likes;
    private TextView text_likes;
    private boolean loading = true;
    private String maxlistLength = "", feedId = "";
    View view_about;
    public static Fragment_Comments fragment_comments;
    private final String TAG = Fragment_Comments.class.getSimpleName();

    public static Fragment_Comments getInstance() {
        if (fragment_comments == null)
            fragment_comments = new Fragment_Comments();
        return fragment_comments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragement_comments, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    private void getBundle() {

        if (b != null) {
            feedId = b.getString("FeedId");

            int likes = b.getInt("likeCount");
            if (likes > 0) {
                if (likes > 1)
                    text_likes.setText(likes + "likes");
                else
                    text_likes.setText(likes + "like");
            } else {
                rel_likes.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        send_message = (ImageView) view.findViewById(R.id.send_message);
        edit_message = (EditText) view.findViewById(R.id.edit_message);
        text_likes = (TextView) view.findViewById(R.id.text_likes);
        rel_likes = (RelativeLayout) view.findViewById(R.id.rel_likes);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();


        getBundle();
        manageHeaderView();
        setlistener();
        getServicelistRefresh();
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {

        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Comments");
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

        rel_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Likes fragmentLikes = new Fragment_Likes();
                Bundle b = new Bundle();
                b.putString("FeedId", feedId);
                fragmentLikes.setArguments(b);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentLikes, true);
            }
        });
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postcomment();
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

        if (flag == 1) {
            openEditRequirements(arrayList.get(position).getId());
        } else if (flag == 2) {
            showDeleteComment(arrayList.get(position).getId());
        }
    }


    private void showDeleteComment(final String id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        alertDialog.setTitle("Delete Comment!");
        alertDialog.setMessage("Are you sure you want to Delete Comment?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteComment(id);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();


    }


    private void postcomment() {
        try {
            AppUtils.onKeyBoardDown(context);
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(context));
                jsonObject.put("statusId", feedId);
                jsonObject.put("comment", edit_message.getText().toString());

                // http://sfscoring.betasportzfever.com/getNotifications/155
                String url = JsonApiHelper.BASEURL + JsonApiHelper.POSTCOMMENT;
                new CommonAsyncTaskHashmap(4, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getServicelistRefresh() {
        HeaderViewManager.getInstance().setProgressLoader(false, false);
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_COMMENTS + feedId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Open dialog for the edit comment
     */
    private void openEditRequirements(final String id) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_comment_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            final EditText edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!edt_comment.getText().toString().equalsIgnoreCase("")) {
                        editComment(edt_comment.getText().toString(), id);
                        dialog.dismiss();
                    } else {
                        edt_comment.setError("Please enter comment");
                        edt_comment.requestFocus();
                    }

                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }


    private void editComment(String comment, String id) {
        try {
            AppUtils.onKeyBoardDown(context);
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userId", AppUtils.getUserId(context));
                jsonObject.put("statusId", feedId);
                jsonObject.put("comment", comment);
                jsonObject.put("commentId", id);

                // http://sfscoring.betasportzfever.com/getNotifications/155
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATECOMMENT;
                new CommonAsyncTaskHashmap(5, context, this).getqueryJsonbject(url, jsonObject, Request.Method.PUT);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteComment(String id) {
        try {

            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.DELETECOMMENT + id;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, null, Request.Method.DELETE);

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
                    HeaderViewManager.getInstance().setProgressLoader(false, false);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        comments = new Comments();
                        comments.setComment(jo.getString("comment"));
                        // comments.setCommentDateTime(jo.getString("commentDateTime"));
                        comments.setId(jo.getString("id"));
                        comments.setUserName(jo.getString("userName"));
                        comments.setUserProfilePicture(jo.getString("userProfilePicture "));
                        comments.setRowType(1);

                        arrayList.add(comments);
                    }
                    adapterComments = new AdapterComments(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterComments);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 5) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 4) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    //   maxlistLength = jObject.getString("total");
                    JSONArray data = jObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        comments = new Comments();
                        comments.setComment(jo.getString("comment"));
                        // comments.setCommentDateTime(jo.getString("commentDateTime"));
                        comments.setId(jo.getString("id"));
                        comments.setUserName(jo.getString("userName"));
                        comments.setUserProfilePicture(jo.getString("userProfilePicture "));
                        comments.setRowType(1);
                        arrayList.add(comments);
                    }
                    adapterComments.notifyDataSetChanged();
                    edit_message.setText("");

                    list_request.scrollToPosition(arrayList.size() - 1);

                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterComments.notifyDataSetChanged();
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

