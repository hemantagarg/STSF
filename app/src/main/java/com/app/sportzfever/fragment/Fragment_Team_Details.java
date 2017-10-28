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
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.models.ModelAvtarMyTeam;
import com.app.sportzfever.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Team_Details extends BaseFragment implements View.OnClickListener, ApiResponse {


    public static Fragment_Team_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = Fragment_Team_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address, mTvTotalPlayers, mTvTotalFans, mTvTotalMatches;
    private ImageView image_back, imge_user, imge_banner;
    private Button btn_join_team, btn_follow_team;
    private ViewPager viewPager;
    private LinearLayout ll_follow;
    private ArrayList<ModelAvtarMyTeam> arrayList;
    private String id = "";

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
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setCollapsingToolbar();
        return view;
    }

    public void setUserData(JSONObject data) {
        try {
            String bannerimage = data.getString("profilePicture");
            String captainName = data.getString("captainName");
            String managerName = data.getString("ownerName");
            String teamName = data.getString("teamName");
            String isActive = data.getString("isActive");
            String totalPlayersInTeam = data.getString("totalPlayersInTeam");
            String fansCount = data.getString("fansCount");
            String matchplayed = data.getString("fansCount");
            String image = data.getString("ownerPic");

            if (isActive.equalsIgnoreCase("1")) {
                ll_follow.setVisibility(View.GONE);
            } else {
                ll_follow.setVisibility(View.VISIBLE);
            }

            if (image != null && !image.equalsIgnoreCase("")) {
                Picasso.with(mActivity).load(image).placeholder(R.drawable.user).into(imge_user);
            }
            if (bannerimage != null && !bannerimage.equalsIgnoreCase("")) {
                Picasso.with(mActivity).load(bannerimage).placeholder(R.drawable.logo).into(imge_banner);
            }
            text_username.setText(teamName);
            text_address.setText(captainName + " (Captain)" + "\n" + managerName + " (Manager)");
            mTvTotalPlayers.setText("Players" + "\n" + totalPlayersInTeam);
            mTvTotalFans.setText("Fans" + "\n" + fansCount);
            mTvTotalMatches.setText("Matches Played" + "\n" + matchplayed);

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


/*
    private void getData() {

        //  http://dev.stackmindz.com/trendi/api/getvendordetail.php?freelancer_id=200

        if (AppUtils.isNetworkAvailable(mActivity)) {

            // http://dev.stackmindz.com/trendi/api/change-password.php?user_id=199&current_pwd=admin&new_pwd=123456&confirm_pwd=123456
            String url = JsonApiHelper.BASEURL + JsonApiHelper.GETVENDORDETAIL + "freelancer_id=" + vendorId;

            new CommonAsyncTaskHashmap(1, mActivity, this).getquery(url);

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }*/

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            AppUtils.setAvtarId(mActivity, id);
        }
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentSportsTeamDetailList tab2 = new FragmentSportsTeamDetailList();
        Bundle b = new Bundle();
        b.putString("avtarid", id);
        tab2.setArguments(b);
        adapter.addFrag(tab2, "services");

        Fragment_AvtarFeed feed = new Fragment_AvtarFeed();
        Bundle b11 = new Bundle();
        b11.putString("avtarid", id);
        feed.setArguments(b11);
        adapter.addFrag(feed, "feed");

        Fragment_AvtarMyTeam tab4 = new Fragment_AvtarMyTeam();
        Bundle b3 = new Bundle();
        b3.putString("avtarid", id);
        tab4.setArguments(b3);
        adapter.addFrag(tab4, "Reviews");

        FragmentGallery tab1 = new FragmentGallery();
        Bundle b1 = new Bundle();
        b1.putString("avtarid", id);
        tab1.setArguments(b1);
        adapter.addFrag(tab1, "About Us");

        FragmentSportAvtarAlbums tab3 = new FragmentSportAvtarAlbums();
        Bundle b2 = new Bundle();
        b2.putString("avtarid", id);
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Portfolio");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {

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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


        }

    }

}
