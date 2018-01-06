package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.AsyncPostDataFileResponse;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.iclasses.InternalStorageContentProvider;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import eu.janmuller.android.simplecropimage.CropImage;

import static android.app.Activity.RESULT_OK;


public class Fragment_Team_Details extends BaseFragment implements ApiResponse {


    public static Fragment_Team_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = Fragment_Team_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address, mTvTotalPlayers, mTvTotalFans, mTvTotalMatches;
    private ImageView image_back, imge_user, imge_banner, image_edit, image_menu;
    private Button btn_join_team, btn_follow_team;
    private RelativeLayout rl_banner;
    private ViewPager viewPager;
    private LinearLayout ll_follow;
    String isTeamMember = "", isTeamfollower = "";
    private String id = "";
    private boolean isTeamOwnerOrCaptain = false;
    private String teamAvatarId = "";
    private String ownerId = "";
    private String captainId = "";
    private String userid = "", path = "";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private File mFileTemp, selectedFilePath;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private ArrayList<String> arrayListId = new ArrayList<>();
    private ArrayList<String> arrayListName = new ArrayList<>();
    ArrayAdapter<String> adapterUser;
    private boolean isDeleteTeam = false;

    public static Fragment_Team_Details getInstance() {
        if (vendorProfileFragment == null)
            vendorProfileFragment = new Fragment_Team_Details();
        return vendorProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_details, container, false);
        mActivity = getActivity();
        vendorProfileFragment = this;
        String states = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(states)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(mActivity.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
        initViews();
        getBundle();
        getServicelistRefresh();
        setCollapsingToolbar();
        setListener();
        return view;
    }

    private void setListener() {

        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage1();
            }
        });

        image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamEditOptions();
            }
        });

        btn_join_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeamMember.equalsIgnoreCase("1")) {
                    LeaveTeam("JOIN TEAM");
                } else {
                    JoinTeam("LEAVE TEAM");
                }
            }
        });
        btn_follow_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeamfollower.equalsIgnoreCase("1")) {
                    followUnfollowTeam("UNFOLLOw");
                } else {
                    followUnfollowTeam("FOLLOw");
                }
            }
        });
    }

    public void setUserData(JSONObject data) {
        try {
            String bannerimage = data.getString("profilePicture");
            String captainName = data.getString("captainName");
            String managerName = data.getString("ownerName");
            String teamName = data.getString("teamName");
            teamAvatarId = data.getString("teamAvatarId");
            ownerId = data.getString("ownerId");
            captainId = data.getString("captainId");
            String isActive = data.getString("isActive");
            String totalPlayersInTeam = data.getString("totalPlayersInTeam");
            String fansCount = data.getString("fansCount");
            String matchplayed = data.getString("fansCount");
            isTeamMember = data.getString("isTeamMember");
            String image = data.getString("ownerPic");
            isTeamfollower = data.getString("isTeamfollower");
            setRoasterData(data);

            if (AppUtils.getLoginUserAvtarId(mActivity).equalsIgnoreCase(data.getString("ownerId")) ||
                    AppUtils.getLoginUserAvtarId(mActivity).equalsIgnoreCase(data.getString("captainId"))) {
                btn_join_team.setVisibility(View.GONE);
                image_edit.setVisibility(View.VISIBLE);
                image_menu.setVisibility(View.VISIBLE);
                isTeamOwnerOrCaptain = true;
            } else {
                btn_join_team.setVisibility(View.VISIBLE);
                image_edit.setVisibility(View.GONE);
                image_menu.setVisibility(View.GONE);
                isTeamOwnerOrCaptain = false;
            }

            if (isTeamMember.equalsIgnoreCase("1")) {
                btn_join_team.setText("Leave Team");
                btn_follow_team.setVisibility(View.GONE);
                /* final float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (235 * scale + 0.5f);
                rl_banner.getLayoutParams().height = pixels;*/

            } else {
                btn_join_team.setText("Join Team");
                btn_follow_team.setVisibility(View.VISIBLE);
              /*  final float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (260 * scale + 0.5f);
                rl_banner.getLayoutParams().height = pixels;*/
            }
            if (isTeamfollower.equalsIgnoreCase("1")) {
                btn_follow_team.setText("Following");
            } else {
                btn_follow_team.setText("Follow");
            }
            if (btn_follow_team.getVisibility() == View.GONE && btn_join_team.getVisibility() == View.GONE) {
                final float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (235 * scale + 0.5f);
                rl_banner.getLayoutParams().height = pixels;
            }


            if (image != null && !image.equalsIgnoreCase("")) {
                Picasso.with(mActivity).load(bannerimage).transform(new CircleTransform()).placeholder(R.drawable.user).into(imge_user);
            }
            if (bannerimage != null && !bannerimage.equalsIgnoreCase("")) {
                Picasso.with(mActivity).load(bannerimage).placeholder(R.drawable.logo).into(imge_banner);
            }
            text_username.setText(teamName);
            text_address.setText(captainName + " (Captain)" + "\n" + managerName + " (Manager)");
            mTvTotalPlayers.setText("Players" + "\n" + totalPlayersInTeam);
            mTvTotalFans.setText("Fans" + "\n" + fansCount);
            mTvTotalMatches.setText("Matches Played" + "\n" + matchplayed);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRoasterData(JSONObject data) {
        try {
            JSONArray teamProfile = data.getJSONArray("teamProfile");
            arrayListId.clear();
            arrayListName.clear();
            arrayListId.add("-1");
            arrayListName.add("Choose New Captain");

            for (int i = 0; i < teamProfile.length(); i++) {
                JSONObject jo = teamProfile.getJSONObject(i);
                if (jo.getString("requestStatus").equalsIgnoreCase(AppConstant.ACCEPTED)) {
                    arrayListId.add(jo.getString("avatar"));
                    arrayListName.add(jo.getString("playerName"));
                }
            }
            adapterUser = new ArrayAdapter<String>(mActivity, R.layout.row_spinner, R.id.textview, arrayListName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appBar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < 100) {
                    collapsingToolbarLayout.setTitle(text_username.getText().toString());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }


    private void followUnfollowTeam(String type) {
        if (AppUtils.isNetworkAvailable(mActivity)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fanUserId", AppUtils.getUserId(mActivity));
                jsonObject.put("avatarId", teamAvatarId);
                jsonObject.put("type", type);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.FOLLOW_UNFOLLOW;
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void JoinTeam(String type) {
        if (AppUtils.isNetworkAvailable(mActivity)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(mActivity));
                jsonObject.put("teamId", id);
                jsonObject.put("sportId", 1);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.JOINTEAM;
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void LeaveTeam(String type) {
        if (AppUtils.isNetworkAvailable(mActivity)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", AppUtils.getUserId(mActivity));
                jsonObject.put("teamId", id);
                jsonObject.put("sportId", 1);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.LEAVETEAM;
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
        //   getData();
    }


    private void initViews() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        imge_user = (ImageView) view.findViewById(R.id.imge_user);
        imge_banner = (ImageView) view.findViewById(R.id.imge_banner);
        image_edit = (ImageView) view.findViewById(R.id.image_edit);
        image_menu = (ImageView) view.findViewById(R.id.image_menu);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        text_username = (TextView) view.findViewById(R.id.text_username);
        text_address = (TextView) view.findViewById(R.id.text_address);
        mTvTotalFans = (TextView) view.findViewById(R.id.mTvTotalFans);
        mTvTotalMatches = (TextView) view.findViewById(R.id.mTvTotalMatches);
        mTvTotalPlayers = (TextView) view.findViewById(R.id.mTvTotalPlayers);
        btn_follow_team = (Button) view.findViewById(R.id.btn_follow_team);
        btn_join_team = (Button) view.findViewById(R.id.btn_join_team);
        ll_follow = (LinearLayout) view.findViewById(R.id.ll_follow);
        rl_banner = (RelativeLayout) view.findViewById(R.id.rl_banner);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    private void setupTabIcons() {
        if (!isTeamOwnerOrCaptain) {
            tabLayout.getTabAt(0).setText("Team Profile");
            tabLayout.getTabAt(1).setText("Team Wall");
            tabLayout.getTabAt(2).setText("Gallery");
            tabLayout.getTabAt(3).setText("Event Management");
        } else {
            tabLayout.getTabAt(0).setText("Team Profile");
            tabLayout.getTabAt(1).setText("Team Wall");
            tabLayout.getTabAt(2).setText("Player Management");
            tabLayout.getTabAt(3).setText("Gallery");
            tabLayout.getTabAt(4).setText("Event Management");
            tabLayout.getTabAt(5).setText("Tournament Fixtures");
        }

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));

    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(mActivity)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + id + "/" + AppUtils.getAuthToken(mActivity);
                new CommonAsyncTaskHashmap(2, mActivity, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentSportsTeamDetailList tab2 = new FragmentSportsTeamDetailList();
        Bundle b = new Bundle();
        b.putString("avtarid", id);
        tab2.setArguments(b);
        adapter.addFrag(tab2, "Detail");

        Fragment_TeamFeed feed = new Fragment_TeamFeed();
        Bundle b11 = new Bundle();
        b11.putString("avtarid", id);
        b11.putString("teamAvatarId", teamAvatarId);
        b11.putBoolean("isTeamOwnerOrCaptain", isTeamOwnerOrCaptain);
        feed.setArguments(b11);
        adapter.addFrag(feed, "feed");

        if (isTeamOwnerOrCaptain) {
            FragmentTeamRoster tab4 = new FragmentTeamRoster();
            Bundle b3 = new Bundle();
            b3.putString("teamId", id);
            b3.putString("ownerId", ownerId);
            b3.putString("captainId", captainId);
            b3.putString("teamAvatarId", teamAvatarId);
            b3.putBoolean("isTeamOwnerOrCaptain", isTeamOwnerOrCaptain);
            tab4.setArguments(b3);
            adapter.addFrag(tab4, "Roaster");
        }

        FragmentTeamGallery tab1 = new FragmentTeamGallery();
        Bundle b1 = new Bundle();
        b1.putString("avtarid", id);
        b1.putString("teamavtarid", teamAvatarId);
        tab1.setArguments(b1);
        adapter.addFrag(tab1, "Gallery");

        FragmentTeamEventList tab3 = new FragmentTeamEventList();
        Bundle b2 = new Bundle();
        b2.putString("teamid", id);
        b2.putString("teamavtarid", teamAvatarId);
        b2.putBoolean("isTeamOwnerOrCaptain", isTeamOwnerOrCaptain);
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Event");

        if (isTeamOwnerOrCaptain) {
            Fragment_TeamTournamentFixture_List tab5 = new Fragment_TeamTournamentFixture_List();
            Bundle b5 = new Bundle();
            b5.putString("teamid", id);
            tab5.setArguments(b5);
            adapter.addFrag(tab5, "Tour");
        }
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        HeaderViewManager.getInstance().InitializeHeaderView(null, view, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, mActivity.getResources().getString(R.string.add));
        HeaderViewManager.getInstance().setLeftSideHeaderView(false, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);

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
                mActivity.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }


    protected void setFragment(Fragment fragment) {
        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_CHAT_BAR, fragment, true);
    }

    private void teamEditOptions() {
        final CharSequence[] items = {"Edit Name",
                "Change Captain", "Delete Team", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                mActivity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Edit Name")) {
                    editTeamName();
                } else if (items[item].equals("Change Captain")) {
                    editTeamCaptain();
                } else if (items[item].equals("Delete Team")) {
                    deleteTeamPopup();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void deleteTeamPopup() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                mActivity);

        alertDialog.setTitle("Delete Team?");

        alertDialog.setMessage("Are you sure you want to delete ? The team will be deleted but data will remain safe with us.");

        alertDialog.setPositiveButton("CONFRM",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            isDeleteTeam = true;
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("type", AppConstant.DELETETEAM);
                            jsonObject.put("teamId", id);
                            updateTeamName(jsonObject);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


    /**
     * Open dialog for the edit team name
     */
    private void editTeamName() {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_post_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            final EditText edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);
            name_requirement_txt.setText("Edit Team Name");
            edt_comment.setHint("Enter Team Name");
            edt_comment.setText(text_username.getText().toString());
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            Spinner spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!edt_comment.getText().toString().equalsIgnoreCase("")) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("type", AppConstant.UPDATENAME);
                            jsonObject.put("teamId", id);
                            jsonObject.put("teamName", edt_comment.getText().toString());
                            jsonObject.put("sportId", "1");
                            updateTeamName(jsonObject);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        edt_comment.setError("Please enter team name");
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

    /**
     * Open dialog for the edit team name
     */
    private void editTeamCaptain() {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_tem_captain, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            final EditText edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);

            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final Spinner spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);
            if (adapterUser != null)
                spinnerShareWith.setAdapter(adapterUser);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (spinnerShareWith.getSelectedItemPosition() != 0) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("type", AppConstant.UPDATECAPTAIN);
                            jsonObject.put("teamId", id);
                            jsonObject.put("captainId", arrayListId.get(spinnerShareWith.getSelectedItemPosition()));
                            updateTeamName(jsonObject);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        edt_comment.setError("Please select captain");
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

    private void updateTeamName(JSONObject jsonObject) {
        try {
            if (AppUtils.isNetworkAvailable(mActivity)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATE_TEAM;
                new CommonAsyncTaskHashmap(5, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.PUT);

            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {
                if (response.getString("result").equalsIgnoreCase("1")) {

                    //  JSONObject data = response.getJSONObject("data");
                    if (isTeamfollower.equalsIgnoreCase("1")) {
                        isTeamfollower = "0";
                        btn_follow_team.setText("Follow");
                    } else {
                        isTeamfollower = "1";
                        btn_follow_team.setText("Following");
                    }
                } else {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (method == 2) {
                if (response.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = response.getJSONObject("data");
                    setUserData(data);
                }
            } else if (method == 3) {
                if (response.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (method == 5) {
                if (response.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                    if (isDeleteTeam) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
                        mActivity.onBackPressed();
                    } else {
                        getServicelistRefresh();
                    }
                } else {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }

    private void selectImage1() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                mActivity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               /* if (items[item].equals("Take Photo")) {

                    takePicture();

                } else */
                if (items[item].equals("Choose from Library")) {

                    openGallery();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                    /*
                     * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
		        	 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "cannot take picture", e);
        }
    }

    private void startCropImage() {

        Intent intent = new Intent(mActivity, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 0);
        intent.putExtra(CropImage.ASPECT_Y, 0);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }


    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("color upload 88888", +requestCode + "");
        switch (requestCode) {

            case REQUEST_CODE_TAKE_PICTURE:

                startCropImage();
                break;

            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = mActivity.getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {
                    Log.e(TAG, "Error while creating temp file", e);
                }
                //  upload_image.setText("Image upload successfully");
                break;

            case REQUEST_CODE_CROP_IMAGE:
                try {
                    path = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (path == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selectedFilePath = new File(path);
                Log.e("filepath", "**" + selectedFilePath);
                Picasso.with(mActivity).load(selectedFilePath).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).into(imge_user);
                uploadPhoto();
                //     profile_image.setImageBitmap(bitmap);
                break;


        }
    }

    private void uploadPhoto() {
        Charset encoding = Charset.forName("UTF-8");
        MultipartEntity reqEntity = new MultipartEntity();
        try {
            StringBody userId = new StringBody("", encoding);
            StringBody avatarId = new StringBody(teamAvatarId, encoding);
            StringBody type = new StringBody("avatar", encoding);

            if (!path.equalsIgnoreCase("")) {
                FileBody filebodyimage = new FileBody(selectedFilePath);
                reqEntity.addPart("image", filebodyimage);
            }
            reqEntity.addPart("userId", userId);
            reqEntity.addPart("type", type);
            reqEntity.addPart("avatarId", avatarId);

            if (AppUtils.isNetworkAvailable(mActivity)) {
                //    https://sfscoring.betasportzfever.com/updateProfilePicture
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATE_PROFILE_PICTURE;
                new AsyncPostDataFileResponse(mActivity, this, 3, reqEntity, url);
            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }
}
