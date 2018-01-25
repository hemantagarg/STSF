package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.app.sportzfever.R.id.tablayout;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentSearchTeam extends BaseFragment {


    private Bundle b;
    private Activity context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;
    View view_about;

    public static FragmentSearchTeam fragment_team;
    private final String TAG = FragmentSearchTeam.class.getSimpleName();
    private String jsonData = "", selectedId = "";

    public static FragmentSearchTeam getInstance() {
        if (fragment_team == null)
            fragment_team = new FragmentSearchTeam();
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
        HeaderViewManager.getInstance().setHeading(true, "Select Team");
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
        tabLayout = (TabLayout) view.findViewById(tablayout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        getBundle();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setListener();
    }

    private void getBundle() {
        if (b != null) {
            jsonData = b.getString("json");
            selectedId = b.getString("selectedId");
            Log.e("selectedId", "*" + selectedId);
        }
    }

    private void setListener() {

    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("My Teams");
        tabLayout.getTabAt(1).setText("Search Teams");
        tabLayout.setTabTextColors(context.getResources().getColor(R.color.textcolordark), context.getResources().getColor(R.color.red));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentMyTeamList tab2 = new FragmentMyTeamList();
        Bundle bundle = new Bundle();
        bundle.putString("json", jsonData);
        bundle.putString("selectedId", selectedId);
        tab2.setArguments(bundle);
        adapter.addFrag(tab2);

        FragmentSearchAndCreateTeam tab1 = new FragmentSearchAndCreateTeam();
        Bundle bundle1 = new Bundle();
        bundle1.putString("json", jsonData);
        bundle1.putString("selectedId", selectedId);
        tab1.setArguments(bundle1);
        adapter.addFrag(tab1);

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

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

