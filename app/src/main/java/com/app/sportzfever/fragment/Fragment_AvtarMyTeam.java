package com.app.sportzfever.fragment;

import android.content.Context;
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

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.interfaces.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_AvtarMyTeam extends BaseFragment {


    private Bundle b;
    private Context context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;

    public static Fragment_AvtarMyTeam fragment_team;
    private final String TAG = Fragment_AvtarMyTeam.class.getSimpleName();

    public static Fragment_AvtarMyTeam getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_AvtarMyTeam();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_chat_main, container, false);
        context = getActivity();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageHeaderVisibitlity(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);

        setupTabIcons();
        setListener();
        setFragment(new FragmentAvtarIAdminTeam());

    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(new FragmentAvtarIAdminTeam());
                        break;
                    case 1:
                        setFragment(new FragmentAvtarIPlanOn());
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

        tabLayout.addTab(tabLayout.newTab().setText("Teams That I Admin"));
        tabLayout.addTab(tabLayout.newTab().setText("Teams That I Play On"));
        //  tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));

      /*  tabLayout.getTabAt(0).setText("Chat");
        tabLayout.getTabAt(1).setText("Contacts");
        tabLayout.getTabAt(2).setText("Groups");*/
        tabLayout.setTabTextColors(context.getResources().getColor(R.color.textcolordark), context.getResources().getColor(R.color.red));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        adapter.addFrag(new FragmentAvtarIAdminTeam());
        adapter.addFrag(new FragmentAvtarIAdminTeam());

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

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

}

