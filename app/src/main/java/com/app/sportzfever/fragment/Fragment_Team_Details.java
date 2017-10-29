package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Team_Details extends BaseFragment implements ApiResponse {


    public static Fragment_Team_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = Fragment_Team_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address, mTvTotalPlayers, mTvTotalFans, mTvTotalMatches;
    private ImageView image_back, imge_user, imge_banner;
    private Button btn_join_team, btn_follow_team;
    private RelativeLayout rl_banner;
    private ViewPager viewPager;
    private LinearLayout ll_follow;
    String isTeamMember = "", isTeamfollower = "";
    private String id = "";
    private String teamAvatarId = "";

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
        initViews();
        getBundle();
        getServicelistRefresh();
        setCollapsingToolbar();
        setListener();
        return view;
    }

    private void setListener() {

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
            String isActive = data.getString("isActive");
            String totalPlayersInTeam = data.getString("totalPlayersInTeam");
            String fansCount = data.getString("fansCount");
            String matchplayed = data.getString("fansCount");
            isTeamMember = data.getString("isTeamMember");
            String image = data.getString("ownerPic");
            isTeamfollower = data.getString("isTeamfollower");

            if (isTeamMember.equalsIgnoreCase("1")) {
                btn_join_team.setText("Leave Team");
                btn_follow_team.setText("Leave Team");
                /* final float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (235 * scale + 0.5f);
                rl_banner.getLayoutParams().height = pixels;*/

            } else {
                btn_join_team.setText("Join Team");
              /*  final float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (260 * scale + 0.5f);
                rl_banner.getLayoutParams().height = pixels;*/
            }
            if (isTeamfollower.equalsIgnoreCase("1")) {
                btn_follow_team.setText("Following");
            } else {
                btn_follow_team.setText("Follow");
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

        tabLayout.getTabAt(0).setText("Team Profile");
        tabLayout.getTabAt(1).setText("Team Wall");
        tabLayout.getTabAt(2).setText("Roster");
        tabLayout.getTabAt(3).setText("Gallery");
        tabLayout.getTabAt(4).setText("Events");

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
        adapter.addFrag(tab2, "services");

        Fragment_TeamFeed feed = new Fragment_TeamFeed();
        Bundle b11 = new Bundle();
        b11.putString("avtarid", id);
        b11.putString("teamAvatarId", teamAvatarId);
        feed.setArguments(b11);
        adapter.addFrag(feed, "feed");

        Fragment_AvtarMyTeam tab4 = new Fragment_AvtarMyTeam();
        Bundle b3 = new Bundle();
        b3.putString("avtarid", id);
        tab4.setArguments(b3);
        adapter.addFrag(tab4, "Reviews");

        FragmentTeamGallery tab1 = new FragmentTeamGallery();
        Bundle b1 = new Bundle();
        b1.putString("avtarid", id);
        b1.putString("teamavtarid", teamAvatarId);
        tab1.setArguments(b1);
        adapter.addFrag(tab1, "About Us");

        FragmentTeamGallery tab3 = new FragmentTeamGallery();
        Bundle b2 = new Bundle();
        b2.putString("avtarid", id);
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Portfolio");

        viewPager.setAdapter(adapter);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {

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


}
