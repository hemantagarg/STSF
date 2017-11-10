package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.app.sportzfever.activities.ViewMatchScoreCard;
import com.app.sportzfever.adapter.AdapterBattingStats;
import com.app.sportzfever.adapter.AdapterBowlingStats;
import com.app.sportzfever.adapter.AdapterPerformance;
import com.app.sportzfever.adapter.AdapterUpcomingTeamoneMatch;
import com.app.sportzfever.adapter.AdapterUpcomingTeamtwoMatch;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelPerformance;
import com.app.sportzfever.models.ModelStats;
import com.app.sportzfever.models.ModelUpcomingTeamName;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_UpcomingMatch_Details extends BaseFragment implements ApiResponse,OnCustomItemClicListener {


    private RecyclerView list_teama, list_teamb;
    private Bundle b;
    private Context context;
    private AdapterUpcomingTeamoneMatch adapterUpcomingTeamoneMatch;
    private AdapterUpcomingTeamtwoMatch adapterUpcomingTeamtwoMatch;

    private ModelUpcomingTeamName modelUpcomingTeamName;
    private ArrayList<ModelUpcomingTeamName> arrayteama, arrayListBowling;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata;
    private String maxlistLength = "";
   // private LinearLayout ll_performance;
    private ImageView teamb,teama;
    private TextView text_username,text_startdate,text_teamname,textmatchtype,text_maxover,text_scorerfora,text_scorerforb,text_location;

    public static FragmentStats fragment_teamJoin_request;
    private final String TAG = FragmentStats.class.getSimpleName();
    private String avtarid = "";

    public static FragmentStats getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentStats();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_upcomingmatchdetail, container, false);
        context = getActivity();
        arrayteama = new ArrayList<>();
        arrayListBowling = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        //  Dashboard.getInstance().manageHeaderVisibitlity(true);
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

       list_teama = (RecyclerView) view.findViewById(R.id.list_teama);
        list_teamb = (RecyclerView) view.findViewById(R.id.list_teamb);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager =  new GridLayoutManager(context, 2);

        list_teama.setLayoutManager(layoutManager);
        list_teamb.setLayoutManager(new LinearLayoutManager(context));
        btn_teamb = (Button) view.findViewById(R.id.btn_teamb);
        btn_teama = (Button) view.findViewById(R.id.btn_teama);
        teama = (ImageView) view.findViewById(R.id.teama);
        teamb = (ImageView) view.findViewById(R.id.teamb);
        text_username = (TextView) view.findViewById(R.id.text_name);
        text_teamname = (TextView) view.findViewById(R.id.text_teamname);
        text_startdate = (TextView) view.findViewById(R.id.text_startdate);


        textmatchtype= (TextView) view.findViewById(R.id.textmatchtype);
        text_maxover= (TextView) view.findViewById(R.id.text_maxover);
        text_scorerfora= (TextView) view.findViewById(R.id.text_scorerfora);
        text_scorerforb= (TextView) view.findViewById(R.id.text_scorerforb);
        text_location= (TextView) view.findViewById(R.id.text_location);
       // ll_performance = (LinearLayout) view.findViewById(R.id.ll_performance);
        arrayteama = new ArrayList<>();
        getBundle();
        setlistener();

        getServicelistRefresh();
    }

    private void setlistener() {

        btn_teama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                list_teama.setVisibility(View.GONE);
                if (arrayteama.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Data found");
                }

                list_teama.setVisibility(View.VISIBLE);
            }
        });
        btn_teamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                list_teamb.setVisibility(View.VISIBLE);
                list_teama.setVisibility(View.GONE);
                if (arrayListBowling.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Data found");
                }

            }
        });


    }

    @Override
    public void onItemClickListener(int position, int flag) {

        if (flag == 22) {
        /*    Intent inte = new Intent(context, ViewMatchScoreCard.class);
            inte.putExtra("eventId", arrayListPerformance.get(position).getEventID());
            startActivity(inte);*/
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
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

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
                    JSONObject data = jObject.getJSONObject("data");

                    //  maxlistLength = jObject.getString("total");
                    JSONObject jbatsman = data.getJSONObject("match");
                    JSONObject team1 = data.getJSONObject("team1");
                    JSONObject team2 = data.getJSONObject("team2");

                    arrayteama.clear();

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setId(jbatsman.getString("id"));
                    modelUpcomingTeamName.setLocation(jbatsman.getString("location"));
                    modelUpcomingTeamName.setMatchDate(jbatsman.getString("matchDate"));
                    modelUpcomingTeamName.setMatchType(jbatsman.getString("matchType"));
                    modelUpcomingTeamName.setNumberOfOvers(jbatsman.getString("numberOfOvers"));

                    text_username.setText(team1.getString("name"));
                    btn_teamb.setText(team2.getString("name"));
                    btn_teama.setText(team1.getString("name"));
                    text_teamname.setText(team2.getString("name"));
                    Picasso.with(context).load(team1.getString("profilePicture")).transform(new CircleTransform()).placeholder(R.drawable.user).into(teama);
                    Picasso.with(context).load(team2.getString("profilePicture")).placeholder(R.drawable.logo).into(teamb);
                    textmatchtype.setText("Match Type"+" : "+jbatsman.getString("matchType"));
                    text_maxover.setText("Max Overs"+" : "+jbatsman.getString("numberOfOvers"));
                   /* text_scorerfora.setText(team2.getString("name"));
                    text_scorerforb.setText(team2.getString("name"));*/
                    text_startdate.setText("Match scheduled to begin at"+" : "+jbatsman.getString("matchDate"));
                    text_location.setText("Venue"+" : "+jbatsman.getString("location"));

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam1AvatarId(team1.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team1.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team1.getString("profilePicture"));

                    modelUpcomingTeamName = new ModelUpcomingTeamName();

                    modelUpcomingTeamName.setTeam2AvatarId(team2.getString("teamAvatarId"));
                    modelUpcomingTeamName.setName(team2.getString("name"));
                    modelUpcomingTeamName.setProfilePicture(team2.getString("profilePicture"));



                    modelUpcomingTeamName.setRowType(1);
                    JSONArray team1squasd = data.getJSONArray("team1Squad");
                 for (int i = 0; i < team1squasd.length(); i++) {
                        JSONObject jsonObject = team1squasd.getJSONObject(i);
                     ModelUpcomingTeamName modelteama = new ModelUpcomingTeamName();
                     modelteama.setPlayerRole(jsonObject.getString("playerRole"));
                     modelteama.setName(jsonObject.getString("name"));
                     modelteama.setProfilePicture(jsonObject.getString("profilePicture"));

                     modelteama.setRowType(1);
                     arrayteama.add(modelteama);

                 }
                    adapterUpcomingTeamoneMatch = new AdapterUpcomingTeamoneMatch(getActivity(), this, arrayteama);
                    list_teama.setAdapter(adapterUpcomingTeamoneMatch);


                    if (arrayteama.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Data found");
                    }

                    JSONArray team2squasd = data.getJSONArray("team2Squad");

                   // arrayListBowling.clear();

                    for (int i = 0; i < team2squasd.length(); i++) {
                        JSONObject jsonObject1 = team2squasd.getJSONObject(i);
                        ModelUpcomingTeamName modelteamb = new ModelUpcomingTeamName();
                        modelteamb.setPlayerRole(jsonObject1.getString("playerRole"));
                        modelteamb.setName(jsonObject1.getString("name"));
                        modelteamb.setProfilePicture(jsonObject1.getString("profilePicture"));

                        modelteamb.setRowType(1);
                        arrayListBowling.add(modelteamb);
                    }

                    adapterUpcomingTeamtwoMatch = new AdapterUpcomingTeamtwoMatch(getActivity(), this, arrayListBowling);
                    list_teamb.setAdapter(adapterUpcomingTeamtwoMatch);

                    if (arrayListBowling.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Data found");
                    }
                    arrayListBowling.clear();


                    if (arrayListBowling.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                       // text_nodata.setText("Yet to play any match on Sportzfever");
                    }

                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Data found");

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


