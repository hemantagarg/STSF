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
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_ChatMain extends BaseFragment implements ApiResponse {


    private Bundle b;
    private Context context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;

    public static Fragment_ChatMain fragment_team;
    private final String TAG = Fragment_ChatMain.class.getSimpleName();

    public static Fragment_ChatMain getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_ChatMain();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_chat_main, container, false);
        context = getActivity();
        b = getArguments();
        getRecentChatList();
        getFreindList();
        getGroupChat();
        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();

        Dashboard.getInstance().manageHeaderVisibitlity(true);


    }

    private void getFreindList() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getFriendsList/1/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_FRIENDLIST + AppUtils.getUserId(context);
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRecentChatList() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getRecentChat/1/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_RECENTCHAT + AppUtils.getUserId(context) + "/" +  AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getGroupChat() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getRecentChat/1/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52'
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_JOINEDGROUPCHAT + AppUtils.getUserId(context) + "/" +  AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

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
        setFragment(new Fragment_Chat());

    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(new Fragment_Chat());
                        break;
                    case 1:
                        setFragment(new Fragment_FriendList());
                        break;
                    case 2:
                        setFragment(new Fragment_Group_Chat());
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

        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));

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
            if (method == 11) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    AppUtils.setFriendList(context, data.toString());
                }

            } else if (method == 12) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    AppUtils.setGroupChatList(context, data.toString());
                }

            } else if (method == 1) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    AppUtils.setChatList(context, data.toString());
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

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

}

