package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Tournament_Matches extends BaseFragment implements ApiResponse {


    private Bundle b;
    private Activity context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;
    View view_about;

    public static Fragment_Tournament_Matches fragment_team;
    private final String TAG = Fragment_Tournament_Matches.class.getSimpleName();

    public static Fragment_Tournament_Matches getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_Tournament_Matches();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_matches_main, container, false);
        context = getActivity();
        b = getArguments();
        manageHeaderView();
        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {

        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Matches");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, false);

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


    private void getFreindList() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getFriendsList/1/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_PASTMATCHES + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);

        setupTabIcons();
        setListener();
        setFragment(new FragmentPastMatches());

    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(new FragmentTournamentAllMatches());
                        break;
                    case 1:
                        setFragment(new FragmentLiveMatches());
                        break;



                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    private void setupTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Final"));
       // tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));

      /*  tabLayout.getTabAt(0).setText("Chat");
        tabLayout.getTabAt(1).setText("Contacts");
        tabLayout.getTabAt(2).setText("Groups");*/
        tabLayout.setTabTextColors(context.getResources().getColor(R.color.textcolordark), context.getResources().getColor(R.color.red));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.addFrag(new Fragment_Chat());
        adapter.addFrag(new Fragment_FriendList());
        adapter.addFrag(new Fragment_Chat());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {


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

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

}

