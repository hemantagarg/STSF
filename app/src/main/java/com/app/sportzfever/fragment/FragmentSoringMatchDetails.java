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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingTeamName;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentSoringMatchDetails extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    private ModelUpcomingTeamName modelUpcomingTeamName;
    private ArrayList<ModelUpcomingTeamName> arrayteama, arrayListBowling;
    private ImageView image_back, image_refresh;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private JSONObject data;
    public static FragmentSoringMatchDetails fragment_teamJoin_request;
    private final String TAG = FragmentSoringMatchDetails.class.getSimpleName();
    private String avtarid = "";
    View view_about;
    private String IsScorerForTeam2 = "";
    private boolean isMatchEnd = false;

    public static FragmentSoringMatchDetails getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentSoringMatchDetails();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_scoring_matchdetail, container, false);
        context = getActivity();
        fragment_teamJoin_request = FragmentSoringMatchDetails.this;
        arrayteama = new ArrayList<>();
        arrayListBowling = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
      /*  if (Fragment_MatchFeed.getInstance().isAdded() && Fragment_MatchFeed.getInstance().getUserVisibleHint()) {
            Fragment_MatchFeed.getInstance().onResume();
        }*/
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            avtarid = bundle.getString("eventId");
            IsScorerForTeam2 = bundle.getString("IsScorerForTeam2");
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image_refresh = (ImageView) view.findViewById(R.id.image_refresh);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        arrayteama = new ArrayList<>();
        getBundle();
        setlistener();
        getServicelistRefresh();
    }

    private void setlistener() {
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });
        image_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getServicelistRefresh();
            }
        });

/*
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        if (isMatchEnd) {
                            Toast.makeText(context, "Match Ended", Toast.LENGTH_SHORT).show();
                        } else {
                            Fragment_LiveScoring tab21 = new Fragment_LiveScoring();
                            Bundle b21 = new Bundle();
                            b21.putString("eventId", avtarid);
                            b21.putString("IsScorerForTeam2", IsScorerForTeam2);
                            b21.putString("data", data.toString());
                            tab21.setArguments(b21);
                            setFragment(tab21);
                        }
                        break;
                    case 1:
                        Fragment_Scoring_ScorecardLive_match tab2 = new Fragment_Scoring_ScorecardLive_match();
                        Bundle b = new Bundle();
                        b.putString("eventId", avtarid);
                        b.putString("data", data.toString());
                        tab2.setArguments(b);
                        setFragment(tab2);
                        break;
                    case 2:
                        Fragment_Match_TeamDetail fragmentMatchTeamDetail = new Fragment_Match_TeamDetail();
                        Bundle b112 = new Bundle();
                        b112.putString("avtarid", avtarid);
                        b112.putString("data", data.toString());
                        fragmentMatchTeamDetail.setArguments(b112);
                        setFragment(fragmentMatchTeamDetail);
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
*/
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Live");
        tabLayout.getTabAt(1).setText("Scorecard");
        tabLayout.getTabAt(2).setText("Teams");

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Fragment_LiveScoring tab21 = new Fragment_LiveScoring();
        Bundle b21 = new Bundle();
        b21.putString("eventId", avtarid);
        b21.putString("data", data.toString());
        b21.putString("IsScorerForTeam2", IsScorerForTeam2);
        tab21.setArguments(b21);
        adapter.addFrag(tab21, "services");


        Fragment_Scoring_ScorecardLive_match tab2 = new Fragment_Scoring_ScorecardLive_match();
        Bundle b = new Bundle();
        b.putString("eventId", avtarid);
        b.putString("data", data.toString());
        tab2.setArguments(b);
        adapter.addFrag(tab2, "services");

        Fragment_Match_TeamDetail fragmentMatchTeamDetail = new Fragment_Match_TeamDetail();
        Bundle b112 = new Bundle();
        b112.putString("avtarid", avtarid);
        b112.putString("data", data.toString());
        fragmentMatchTeamDetail.setArguments(b112);
        adapter.addFrag(fragmentMatchTeamDetail, "Team");

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
    public void moveFragment() {
        Fragment_Scoring_ScorecardLive_match tab2 = new Fragment_Scoring_ScorecardLive_match();
        Bundle b = new Bundle();
        b.putString("eventId", avtarid);
        b.putString("data", data.toString());
        tab2.setArguments(b);
        setFragment(tab2);
        isMatchEnd = true;
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

 /*   private void setupTabIcons() {
        tabLayout.addTab(tabLayout.newTab().setText("Live"));
        tabLayout.addTab(tabLayout.newTab().setText("Scorecard"));
        tabLayout.addTab(tabLayout.newTab().setText("Teams"));

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));
    }
*/
    @Override
    public void onItemClickListener(int position, int flag) {


    }


    public void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + avtarid + "/" + AppUtils.getAuthToken(context);
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
                Dashboard.getInstance().setProgressLoader(false);

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    data = jObject.getJSONObject("data");

                    //  maxlistLength = jObject.getString("total");
                    JSONObject jbatsman = data.getJSONObject("match");
                    JSONObject team1 = data.getJSONObject("team1");
                    JSONObject team2 = data.getJSONObject("team2");
                    //  tournamentId = jbatsman.getString("tournamentId");
                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setId(jbatsman.getString("id"));
                    modelUpcomingTeamName.setLocation(jbatsman.getString("location"));
                    modelUpcomingTeamName.setMatchDate(jbatsman.getString("matchDate"));
                    modelUpcomingTeamName.setMatchType(jbatsman.getString("matchType"));
                    modelUpcomingTeamName.setNumberOfOvers(jbatsman.getString("numberOfOvers"));

                    //  matchTitle = jbatsman.getString("matchTile");
                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam1AvatarId(team1.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team1.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team1.getString("profilePicture"));

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam2AvatarId(team2.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team2.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team2.getString("profilePicture"));

                 /*   setupTabIcons();
                    setlistener();
                    Fragment_LiveScoring tab21 = new Fragment_LiveScoring();
                    Bundle b21 = new Bundle();
                    b21.putString("eventId", avtarid);
                    b21.putString("IsScorerForTeam2", IsScorerForTeam2);
                    b21.putString("data", data.toString());
                    tab21.setArguments(b21);
                    setFragment(tab21);*/

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


