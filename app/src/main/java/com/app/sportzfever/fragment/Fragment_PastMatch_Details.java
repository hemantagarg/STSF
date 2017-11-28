package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingTeamName;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_PastMatch_Details extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    private ModelUpcomingTeamName modelUpcomingTeamName;
    private ArrayList<ModelUpcomingTeamName> arrayteama, arrayListBowling;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private ImageView teamb, teama, image_back;
    private TextView text_username, text_startdate, text_teamname, textmatchtype, text_maxover, text_scorerfora, text_scorerforb, text_location;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private JSONObject data;
    public static Fragment_PastMatch_Details fragment_teamJoin_request;
    private final String TAG = FragmentStats.class.getSimpleName();
    private String avtarid = "";

    public static Fragment_PastMatch_Details getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_PastMatch_Details();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_pastmatchdetail, container, false);
        context = getActivity();
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

    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            avtarid = bundle.getString("eventId");
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teama = (ImageView) view.findViewById(R.id.teama);
        teamb = (ImageView) view.findViewById(R.id.teamb);
        text_username = (TextView) view.findViewById(R.id.text_name);
        text_teamname = (TextView) view.findViewById(R.id.text_teamname);
        text_startdate = (TextView) view.findViewById(R.id.text_startdate);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        textmatchtype = (TextView) view.findViewById(R.id.textmatchtype);
        text_maxover = (TextView) view.findViewById(R.id.text_maxover);
        text_scorerfora = (TextView) view.findViewById(R.id.text_scorerfora);
        text_scorerforb = (TextView) view.findViewById(R.id.text_scorerforb);
        text_location = (TextView) view.findViewById(R.id.text_location);
        // ll_performance = (LinearLayout) view.findViewById(R.id.ll_performance);
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

    }


    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Full Scorecard");
        tabLayout.getTabAt(1).setText("Match Info");
        tabLayout.getTabAt(2).setText("Team");
        tabLayout.getTabAt(3).setText("Commentary");

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment_FullScorecard_match tab2 = new Fragment_FullScorecard_match();
        Bundle b = new Bundle();
        b.putString("avtarid", avtarid);
        b.putString("data", data.toString());
        tab2.setArguments(b);
        adapter.addFrag(tab2, "services");

        Fragment_PastMatch_Info feed = new Fragment_PastMatch_Info();
        Bundle b11 = new Bundle();
        b11.putString("avtarid", avtarid);
        b11.putString("data", data.toString());
        feed.setArguments(b11);
        adapter.addFrag(feed, "feed");

        Fragment_Match_TeamDetail fragmentMatchTeamDetail = new Fragment_Match_TeamDetail();
        Bundle b112 = new Bundle();
        b112.putString("avtarid", avtarid);
        b112.putString("data", data.toString());
        fragmentMatchTeamDetail.setArguments(b112);
        adapter.addFrag(fragmentMatchTeamDetail, "Team");

        Fragment_PastMatch_Info tab4 = new Fragment_PastMatch_Info();
        Bundle b3 = new Bundle();
        b3.putString("avtarid", avtarid);
        b3.putString("data", data.toString());
        tab4.setArguments(b3);
        adapter.addFrag(tab4, "Reviews");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position, int flag) {


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


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
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

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setId(jbatsman.getString("id"));
                    modelUpcomingTeamName.setLocation(jbatsman.getString("location"));
                    modelUpcomingTeamName.setMatchDate(jbatsman.getString("matchDate"));
                    modelUpcomingTeamName.setMatchType(jbatsman.getString("matchType"));
                    modelUpcomingTeamName.setNumberOfOvers(jbatsman.getString("numberOfOvers"));

                    text_username.setText(team1.getString("name"));

                    text_teamname.setText(team2.getString("name"));
                    Picasso.with(context).load(team1.getString("profilePicture")).transform(new CircleTransform()).placeholder(R.drawable.user).into(teama);
                    Picasso.with(context).load(team2.getString("profilePicture")).placeholder(R.drawable.logo).into(teamb);
                    textmatchtype.setText(jbatsman.getString("wonString"));
                    text_maxover.setText(jbatsman.getString("matchScheduleString"));
                    text_scorerfora.setText(jbatsman.getString("team1ScoreString"));
                    text_scorerforb.setText(jbatsman.getString("team2ScoreString"));
                    //  text_startdate.setText("Match scheduled to begin at" + " : " + jbatsman.getString("matchDate"));
                    text_location.setText(jbatsman.getString("location"));

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam1AvatarId(team1.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team1.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team1.getString("profilePicture"));

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam2AvatarId(team2.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team2.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team2.getString("profilePicture"));

                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    setupTabIcons();
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


