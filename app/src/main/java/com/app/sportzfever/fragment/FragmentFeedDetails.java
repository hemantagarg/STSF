package com.app.sportzfever.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.ImagesListActivity;
import com.app.sportzfever.aynctask.AsyncPostDataFileResponse;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.Images;
import com.app.sportzfever.models.ModelFeed;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentFeedDetails extends BaseFragment implements ApiResponse {

    private Bundle b;
    private Context context;
    private ModelFeed modelFeed;
    private ArrayList<ModelFeed> arrayList;
    public static FragmentFeedDetails fragment_userFeed;
    private final String TAG = FragmentFeedDetails.class.getSimpleName();
    View view_about;
    private String statusId = "";
    private TextView text_name, text_message, text_time, text_like, text_comment, text_share,
            text_imag_count, text_sharePost, text_like_count, text_comment_count,
            text_share_count, text_location_time, text_teamname1, text_teamname2, text_match_status;
    private ImageView image_viewers, image_feed1, image_feed2, image_feed3, image_menu, teama, teamb;
    private LinearLayout ll_feed_images, ll_multiple_images;
    private RelativeLayout rl_moreimages, card_view, rl_match, rl_top;

    public static FragmentFeedDetails getInstance() {
        if (fragment_userFeed == null)
            fragment_userFeed = new FragmentFeedDetails();
        return fragment_userFeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragment_feed_detail, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        getServicelistRefresh();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayList = new ArrayList<>();
        init(view);
        setlistener();
        AppConstant.ISFEEDNEEDTOREFRESH = false;
        getBundle();
        manageHeaderView();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init(View view) {
        image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
        image_feed1 = (ImageView) view.findViewById(R.id.image_feed1);
        image_feed2 = (ImageView) view.findViewById(R.id.image_feed2);
        image_menu = (ImageView) view.findViewById(R.id.image_menu);
        teama = (ImageView) view.findViewById(R.id.teama);
        teamb = (ImageView) view.findViewById(R.id.teamb);
        text_teamname2 = (TextView) view.findViewById(R.id.text_teamname2);
        text_match_status = (TextView) view.findViewById(R.id.text_match_status);
        text_teamname1 = (TextView) view.findViewById(R.id.text_teamname1);
        rl_match = (RelativeLayout) view.findViewById(R.id.rl_match);
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
        card_view = (RelativeLayout) view.findViewById(R.id.card_view);

        image_feed3 = (ImageView) view.findViewById(R.id.image_feed3);
        ll_feed_images = (LinearLayout) view.findViewById(R.id.ll_feed_images);
        rl_moreimages = (RelativeLayout) view.findViewById(R.id.rl_moreimages);
        ll_multiple_images = (LinearLayout) view.findViewById(R.id.ll_multiple_images);
        text_name = (TextView) view.findViewById(R.id.text_name);
        text_sharePost = (TextView) view.findViewById(R.id.text_sharePost);
        text_imag_count = (TextView) view.findViewById(R.id.text_imag_count);
        text_like_count = (TextView) view.findViewById(R.id.text_like_count);
        text_comment_count = (TextView) view.findViewById(R.id.text_comment_count);
        text_share_count = (TextView) view.findViewById(R.id.text_share_count);
        text_message = (TextView) view.findViewById(R.id.text_message);
        text_like = (TextView) view.findViewById(R.id.text_like);
        text_time = (TextView) view.findViewById(R.id.text_time);
        text_location_time = (TextView) view.findViewById(R.id.text_location_time);
        text_share = (TextView) view.findViewById(R.id.text_share);
        text_comment = (TextView) view.findViewById(R.id.text_comment);

    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            statusId = bundle.getString("id");
        }
    }

    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Status Detail");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, false);

    }

    private HeaderViewClickListener manageHeaderClick() {
        return new HeaderViewClickListener() {
            @Override
            public void onClickOfHeaderLeftView() {
                AppUtils.showLog(TAG, "onClickOfHeaderLeftView");
                getActivity().onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setlistener() {

    }

    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {

            Fragment_Comments fragment_comments = new Fragment_Comments();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            b.putInt("likeCount", arrayList.get(position).getLikeCount());
            fragment_comments.setArguments(b);

            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragment_comments, true);
        } else if (flag == 2) {

            Fragment_Likes fragmentLikes = new Fragment_Likes();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            fragmentLikes.setArguments(b);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentLikes, true);
        } else if (flag == 3) {
            shareFeed(arrayList.get(position).getFeedId());

        } else if (flag == 6) {
            likeFeed(arrayList.get(position).getFeedId());

        } else if (flag == 5) {

            Fragment_Share fragment_share = new Fragment_Share();
            Bundle b = new Bundle();
            b.putString("FeedId", arrayList.get(position).getFeedId());
            fragment_share.setArguments(b);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragment_share, true);

        } else if (flag == 4) {
            if (arrayList.get(position).getImages() != null && arrayList.get(position).getImages().size() > 0) {
                ArrayList<Images> imagesArrayList = arrayList.get(position).getImages();
                Bundle b = new Bundle();
                b.putSerializable("images", imagesArrayList);
                Intent intent = new Intent(context, ImagesListActivity.class);
                intent.putExtra("bundle", b);
                startActivity(intent);
            }
        } else if (flag == 9) {
            showMenuDialog(position);
        } else if (flag == 11) {
            if (arrayList.get(position).getStatusType().equals(AppConstant.EVENT)) {
                Fragment_EvenDetail fragmentUser_details = new Fragment_EvenDetail();
                Bundle b = new Bundle();
                b.putString("id", arrayList.get(position).getEventId());
                fragmentUser_details.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);

            } else {
                if (arrayList.get(position).getAvatarType().equalsIgnoreCase("TEAM")) {
                    Fragment_Team_Details fragmentUser_details = new Fragment_Team_Details();
                    Bundle b = new Bundle();
                    b.putString("id", arrayList.get(position).getTeamId());
                    fragmentUser_details.setArguments(b);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);

                } else if (arrayList.get(position).getAvatarType().equalsIgnoreCase("PLAYER")) {
                    FragmentAvtar_Details fragmentUser_details = new FragmentAvtar_Details();
                    Bundle b = new Bundle();
                    if (!arrayList.get(position).getOriginalAvatarName().equalsIgnoreCase("")) {
                        b.putString("id", arrayList.get(position).getOriginalAvatar());
                    } else {
                        b.putString("id", arrayList.get(position).getAvatar());
                    }
                    fragmentUser_details.setArguments(b);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);
                } else {
                    FragmentUser_Details fragmentUser_details = new FragmentUser_Details();
                    Bundle b = new Bundle();
                    b.putString("id", arrayList.get(position).getUser());
                    fragmentUser_details.setArguments(b);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);
                }
            }
        } else if (flag == 13) {
            if (arrayList.get(position).getMatchStatus().equals(AppConstant.MATCHSTATUS_ENDED)) {
                Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getEventId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                FragmentUpcomingMatchDetails fragmentupcomingdetals = new FragmentUpcomingMatchDetails();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getEventId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_STARTED)) {
                Fragment_LiveMatch_Details fragmentupcomingdetals = new Fragment_LiveMatch_Details();
                Bundle b = new Bundle();
                b.putString("eventId", arrayList.get(position).getEventId());
                fragmentupcomingdetals.setArguments(b);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
            }
        }

    }


    private void showMenuDialog(final int position) {

        if (arrayList.get(position).getIsShared().equalsIgnoreCase("1")) {
            final CharSequence[] items = {"Delete Post"
            };
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Delete Post")) {
                        deleteFeed(arrayList.get(position).getFeedId());

                    }
                }
            });

            alertDialog.show();

        } else {
            final CharSequence[] items = {"Edit Post", "Delete Post"
            };
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Edit Post")) {
                        dialog.dismiss();
                        openEditRequirements(arrayList.get(position).getFeedId());

                    } else if (items[item].equals("Delete Post")) {
                        deleteFeed(arrayList.get(position).getFeedId());
                    }
                }
            });
            alertDialog.show();
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
            View view = inflater.inflate(R.layout.edit_post_dialog, null, false);
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
                        updateFeed(edt_comment.getText().toString(), id);
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


    private void updateFeed(String text, String id) {
        try {
            Charset encoding = Charset.forName("UTF-8");
            MultipartEntity reqEntity = new MultipartEntity();
            StringBody statusid = new StringBody("id", encoding);
            StringBody description = new StringBody(text, encoding);

            reqEntity.addPart("statusId", statusid);
            reqEntity.addPart("description", description);

            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATESTATUS + "/" + id;
                new AsyncPostDataFileResponse(context, FragmentFeedDetails.this, 10, reqEntity, url);
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
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonbject(url, null, Request.Method.DELETE);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getServicelistRefresh() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                // https://sfscoring.betasportzfever.com/singleStatusDetails/724
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SINGLE_SATUS_DETAILS + statusId;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);

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
                    JSONObject jo = jObject.getJSONObject("data");
                    arrayList.clear();
                    modelFeed = new ModelFeed();

                    modelFeed.setId(jo.getString("id"));
                    modelFeed.setFeedId(jo.getString("id"));
                    modelFeed.setTeamId(jo.getString("teamId"));
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
                    if (jo.getString("statusType").equals(AppConstant.EVENT)) {
                        JSONObject event = jo.getJSONObject("event");
                        if (event.has("title")) {
                            modelFeed.setEventTitle(event.getString("title"));
                            JSONObject startDate = event.getJSONObject("startDate");
                            modelFeed.setDateTime(startDate.getString("monthName") + " " + startDate.getString("month") + " " + event.getString("location"));
                        }
                        modelFeed.setEventType(event.getString("eventType"));
                        modelFeed.setEventId(event.getString("id"));
                        if (event.getString("eventType").equals(AppConstant.MATCH)) {
                            modelFeed.setMatchStatus(event.getString("matchStatus"));
                            modelFeed.setTeam1Name(event.getString("team1Name"));
                            modelFeed.setTeam2Name(event.getString("team2Name"));
                            modelFeed.setTeam1ProfilePicture(event.getString("team1ProfilePicture"));
                            modelFeed.setTeam2ProfilePicture(event.getString("team2ProfilePicture"));
                            modelFeed.setLocation(event.getString("location"));
                            JSONObject startDate = event.getJSONObject("startDate");
                            if (modelFeed.getMatchStatus().equals(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                                modelFeed.setDateTime(" Match starts At " + startDate.getString("time") + " On " + startDate.getString("date") + "  " + startDate.getString("ShortMonthName") + "  " + startDate.getString("year"));
                            } else {
                                modelFeed.setDateTime(" Match started At " + startDate.getString("time") + " On " + startDate.getString("date") + "  " + startDate.getString("ShortMonthName") + "  " + startDate.getString("year"));
                            }
                        }
                    }
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
                    setData(modelFeed);
                    Log.e("size", "**" + arrayList.size());
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 10) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getServicelistRefresh();
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 11) {
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

                    getServicelistRefresh();

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setData(ModelFeed modelFeed) {
        ModelFeed m1 = modelFeed;
        try {
            if (m1.getIsShared().equalsIgnoreCase("0")) {

                if (m1.getAvatarType().equalsIgnoreCase("TEAM")) {
                    text_name.setText(m1.getAvatarName());

                    if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.logo_sportz)
                                .into(image_viewers);
                    }
                } else if (m1.getAvatarType().equalsIgnoreCase("PLAYER")) {

                    if (!m1.getOriginalAvatarName().equalsIgnoreCase("")) {
                        text_name.setText(m1.getOriginalAvatarName());
                        if (!m1.getOriginalAvatarProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(context)
                                    .load(m1.getOriginalAvatarProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(image_viewers);
                        }
                    } else {
                        text_name.setText(m1.getAvatarName());
                        if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(context)
                                    .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(image_viewers);
                        }
                    }
                } else {
                    text_name.setText(m1.getUserName());
                    if (!m1.getUserProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(m1.getUserProfilePicture() + "&w=100&h=100")
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.logo_sportz)
                                .into(image_viewers);
                    }
                }
                text_sharePost.setVisibility(View.GONE);

            } else {
                text_sharePost.setVisibility(View.VISIBLE);
                if (AppUtils.getUserId(context).equalsIgnoreCase(m1.getUser())) {
                    text_sharePost.setText("You shared this post");

                } else {
                    text_sharePost.setText(m1.getUserName() + " shared this post");
                }

                if (m1.getAvatarType().equalsIgnoreCase("TEAM")) {
                    text_name.setText(m1.getAvatarName());

                    if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                .transform(new CircleTransform())
                                .placeholder(R.drawable.logo_sportz)
                                .into(image_viewers);
                    }
                } else {

                    if (!m1.getOriginalAvatarName().equalsIgnoreCase("")) {
                        text_name.setText(m1.getOriginalAvatarName());
                        if (!m1.getOriginalAvatarProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(context)
                                    .load(m1.getOriginalAvatarProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(image_viewers);
                        }

                    } else {
                        text_name.setText(m1.getOriginalUserName());
                        if (!m1.getOriginalUserProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(context)
                                    .load(m1.getOriginalUserProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(image_viewers);
                        }
                    }
                }
            }


            text_message.setText(Html.fromHtml(m1.getDescription()));
            text_time.setText(m1.getDateString());
            if (m1.getStatusType().equals(AppConstant.EVENT)) {
                text_message.setText(m1.getEventTitle());
                text_location_time.setText(m1.getDateTime());
                text_location_time.setVisibility(View.VISIBLE);
                if (m1.getEventType().equals(AppConstant.MATCH)) {

                    text_location_time.setText(m1.getDateTime());
                    text_teamname1.setText(m1.getTeam1Name());
                    text_teamname2.setText(m1.getTeam2Name());
                    text_message.setText(m1.getLocation());

                    text_location_time.setVisibility(View.VISIBLE);
                    rl_top.setVisibility(View.GONE);
                    rl_match.setVisibility(View.VISIBLE);
                    image_viewers.setVisibility(View.GONE);

                    if (!m1.getTeam1ProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(m1.getTeam1ProfilePicture() + "&w=100&h=100")
                                .into(teama);

                    }
                    if (!m1.getTeam2ProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(m1.getTeam2ProfilePicture() + "&w=100&h=100")
                                .into(teamb);

                    }
                    if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                        text_match_status.setText("UPCOMING");
                    } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_STARTED)) {
                        text_match_status.setText("LIVE");
                    } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_ENDED)) {
                        text_match_status.setText("PAST");
                    }
                } else {
                    rl_top.setVisibility(View.VISIBLE);
                    rl_match.setVisibility(View.GONE);
                    image_viewers.setVisibility(View.VISIBLE);
                    text_location_time.setVisibility(View.GONE);
                }

            } else {
                text_location_time.setVisibility(View.GONE);
                rl_top.setVisibility(View.VISIBLE);
                rl_match.setVisibility(View.GONE);
                image_viewers.setVisibility(View.VISIBLE);
            }
            if (m1.getIsLiked().equalsIgnoreCase("0")) {
                text_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_grey, 0, 0, 0);
                text_like.setTextColor(ContextCompat.getColor(context, R.color.text_hint_color));
            } else {
                text_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_red, 0, 0, 0);
                text_like.setTextColor(ContextCompat.getColor(context, R.color.red_select));
            }

            if (m1.getIsShared().equalsIgnoreCase("0")) {
                text_share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_grey, 0, 0, 0);
                text_share.setTextColor(ContextCompat.getColor(context, R.color.text_hint_color));
            } else {
                text_share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_red, 0, 0, 0);
                text_share.setTextColor(ContextCompat.getColor(context, R.color.red_select));
            }

            if (m1.getCommentsCount() > 0) {
                text_comment.setText(m1.getCommentsCount() + "");
            } else {
                text_comment.setText("0");
            }

            if (m1.getLikeCount() > 0) {
                text_like.setText(m1.getLikeCount() + "");
            } else {
                text_like.setText("0");
            }

            if (m1.getShareCount() > 0) {
                text_share.setText(m1.getShareCount() + "");
            } else {
                text_share.setText("0");
            }

            if (m1.getUser().equalsIgnoreCase(AppUtils.getUserId(context))) {
                image_menu.setVisibility(View.VISIBLE);
            } else {
                image_menu.setVisibility(View.GONE);
            }


           /*     if (m1.getShareCount() > 0 || m1.getLikeCount() > 0 || m1.getCommentsCount() > 0) {
                    ((CustomViewHolder) holder).view2.setVisibility(View.VISIBLE);
                } else {
                    ((CustomViewHolder) holder).view2.setVisibility(View.GONE);
                }*/

            text_comment_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 1);
                }
            });
            text_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 2);
                }
            });
            text_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 3);
                }
            });
            text_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 1);
                }
            });
            text_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 6);
                }
            });
            text_share_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 5);
                }
            });
            rl_match.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 13);
                }
            });
            text_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 11);
                }
            });
            ll_feed_images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 4);
                }
            });

            image_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener(0, 9);
                }
            });

            ArrayList<Images> imagesArrayList = m1.getImages();
            if (imagesArrayList != null && imagesArrayList.size() > 0) {

                if (imagesArrayList.size() == 1) {
                    image_feed1.setVisibility(View.VISIBLE);
                    ll_multiple_images.setVisibility(View.GONE);

                    if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(imagesArrayList.get(0).getImage() + "&h=400")
                                .into(image_feed1);

                    }
                } else if (imagesArrayList.size() > 1) {
                    image_feed1.setVisibility(View.GONE);
                    ll_multiple_images.setVisibility(View.VISIBLE);

                    if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(imagesArrayList.get(0).getImage() + "&w=400&h=400")
                                .into(image_feed2);
                    }

                    if (!imagesArrayList.get(1).getImage().equalsIgnoreCase("")) {
                        Picasso.with(context)
                                .load(imagesArrayList.get(1).getImage() + "&w=400&h=400")
                                .into(image_feed3);

                    }

                    if (imagesArrayList.size() > 2) {
                        rl_moreimages.setVisibility(View.VISIBLE);
                        text_imag_count.setText(imagesArrayList.size() - 2 + "");
                    } else {
                        rl_moreimages.setVisibility(View.GONE);
                    }


                } else {
                    image_feed1.setVisibility(View.GONE);
                    ll_multiple_images.setVisibility(View.GONE);

                }
            } else {
                image_feed1.setVisibility(View.GONE);
                ll_multiple_images.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }

}

