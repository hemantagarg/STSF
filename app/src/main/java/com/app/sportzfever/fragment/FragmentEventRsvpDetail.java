package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentEventRsvpDetail extends BaseFragment implements ApiResponse {


    private Bundle b;
    private Activity context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;
    View view_about;
    private JSONArray pendingUsers, goingUsers, notGoingUsers;
    public static FragmentEventRsvpDetail fragment_team;
    private final String TAG = FragmentEventRsvpDetail.class.getSimpleName();
    private String jsonData = "";

    public static FragmentEventRsvpDetail getInstance() {
        if (fragment_team == null)
            fragment_team = new FragmentEventRsvpDetail();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_matches_main, container, false);
        context = getActivity();
        b = getArguments();
        manageHeaderView();
        getBundle();
        return view_about;
    }

    private void getBundle() {
        if (b != null) {
            jsonData = b.getString("data");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
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
        HeaderViewManager.getInstance().setHeading(true, "RSVPs");
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


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        checkData();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setListener();
    }

    private void checkData() {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            pendingUsers = jsonObject.getJSONArray("pendingUsers");
            goingUsers = jsonObject.getJSONArray("goingUsers");
            notGoingUsers = jsonObject.getJSONArray("notGoingUsers");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Fragment_GoingUsers tab21 = new Fragment_GoingUsers();
        Bundle b21 = new Bundle();
        b21.putString("data", goingUsers.toString());
        tab21.setArguments(b21);
        adapter.addFrag(tab21);

        Fragment_GoingUsers tab2 = new Fragment_GoingUsers();
        Bundle b = new Bundle();
        b.putString("data", notGoingUsers.toString());
        tab2.setArguments(b);
        adapter.addFrag(tab2);

        Fragment_GoingUsers feed = new Fragment_GoingUsers();
        Bundle b11 = new Bundle();
        b11.putString("data", pendingUsers.toString());
        feed.setArguments(b11);
        adapter.addFrag(feed);

        viewPager.setAdapter(adapter);
    }


    private void setListener() {
    }


    private void setupTabIcons() {
        if (goingUsers != null && goingUsers.length() > 0) {
            tabLayout.getTabAt(0).setText("Going (" + goingUsers.length() + ")");
        } else {
            tabLayout.getTabAt(0).setText("Going");
        }

        if (goingUsers != null && goingUsers.length() > 0) {
            tabLayout.getTabAt(2).setText("Pending (" + pendingUsers.length() + ")");
        } else {
            tabLayout.getTabAt(2).setText("Pending");
        }

        if (goingUsers != null && goingUsers.length() > 0) {
            tabLayout.getTabAt(1).setText("Not Going (" + notGoingUsers.length() + ")");
        } else {
            tabLayout.getTabAt(1).setText("Not Going");
        }


        tabLayout.setTabTextColors(context.getResources().getColor(R.color.textcolordark), context.getResources().getColor(R.color.red));

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

