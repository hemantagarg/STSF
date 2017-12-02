package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterTeamCommentry;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelCommentry;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_MatchCommentry extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView recyclerViewCommentry, recyclerViewCommentry2;
    private Bundle b;
    private Activity context;
    private ArrayList<ModelCommentry> arrayteam1Commentry, arrayteam2Commentry;
    ModelCommentry modelCommentry;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata;
    public static Fragment_MatchCommentry fragment_teamJoin_request;
    private final String TAG = FragmentStats.class.getSimpleName();
    private String avtarid = "";
    View view;
    JSONObject data;
    private String team1InningId = "", team2InningId = "";
    private AdapterTeamCommentry adapterTeamCommentry, adapterTeamCommentry1;

    public static Fragment_MatchCommentry getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_MatchCommentry();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_match_commentry, container, false);
        context = getActivity();

        b = getArguments();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        getBundle();
        setlistener();
    }

    private void init() {
        arrayteam1Commentry = new ArrayList<>();
        arrayteam2Commentry = new ArrayList<>();

        recyclerViewCommentry = (RecyclerView) view.findViewById(R.id.recyclerViewCommentry);
        recyclerViewCommentry2 = (RecyclerView) view.findViewById(R.id.recyclerViewCommentry2);
        recyclerViewCommentry.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewCommentry2.setLayoutManager(new LinearLayoutManager(context));

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        btn_teamb = (Button) view.findViewById(R.id.btn_teamb);
        btn_teama = (Button) view.findViewById(R.id.btn_teama);

    }


    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
    }

    private void getBundle() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                avtarid = bundle.getString("eventId");
                String maindata = bundle.getString("data");

                data = new JSONObject(maindata);
                JSONObject match = data.getJSONObject("match");

                btn_teamb.setText(match.getString("team2Name"));
                btn_teama.setText(match.getString("team1Name"));
                team1InningId = match.getString("team1InningId");
                team2InningId = match.getString("team2InningId");
                getTeam1Data();
                getTeam2Data();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setlistener() {
        btn_teama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                recyclerViewCommentry2.setVisibility(View.GONE);
                recyclerViewCommentry.setVisibility(View.VISIBLE);
                if (arrayteam1Commentry.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText(btn_teama.getText().toString() + " commentry not available");
                }
            }
        });
        btn_teamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                recyclerViewCommentry2.setVisibility(View.VISIBLE);
                recyclerViewCommentry.setVisibility(View.GONE);
                if (arrayteam2Commentry.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText(btn_teamb.getText().toString() + " commentry not available");
                }
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
/*
        if (flag == 1) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayteam1Batting.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);

        } else if (flag == 2) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayteam1Bowling.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);

        }*/
    }


    private void getTeam1Data() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/commentary/85/50/50/59a5e6bfea3964e9a8e4278d26aec647
                String url = JsonApiHelper.BASEURL + JsonApiHelper.COMMENTARY + team1InningId + "/50/50/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTeam2Data() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/commentary/85/50/50/59a5e6bfea3964e9a8e4278d26aec647
                String url = JsonApiHelper.BASEURL + JsonApiHelper.COMMENTARY + team2InningId + "/50/50/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbjectNoProgress(url, new JSONObject(), Request.Method.GET);

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
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jsonObject = data.getJSONObject(i);
                        ModelCommentry modelCommentry = new ModelCommentry();
                        modelCommentry.setRowType(1);
                        modelCommentry.setOver(jsonObject.getString("over"));
                        modelCommentry.setTeam1Name(btn_teama.getText().toString());
                        modelCommentry.setOverString(jsonObject.getString("overStrings"));

                        JSONObject endOver = jsonObject.getJSONObject("endOver");
                        modelCommentry.setRunInOver(endOver.getString("runInOver"));
                        modelCommentry.setWicketInOver(endOver.getString("wicketInOver"));
                        modelCommentry.setTotalRun(endOver.getString("totalRun"));
                        modelCommentry.setTotalWicket(endOver.getString("totalWicket"));
                        JSONObject Batsman1 = endOver.getJSONObject("Batsman1");
                        modelCommentry.setBatsman1Id(Batsman1.getString("batsmanId"));
                        modelCommentry.setBatsman1Name(Batsman1.getString("name"));
                        modelCommentry.setBatsman1Runs(Batsman1.getString("runs"));
                        modelCommentry.setBatsman1balls(Batsman1.getString("balls"));

                        JSONObject Batsman2 = endOver.getJSONObject("Batsman2");
                        modelCommentry.setBatsman2Id(Batsman2.getString("batsmanId"));
                        modelCommentry.setBatsman2Name(Batsman2.getString("name"));
                        modelCommentry.setBatsman2Runs(Batsman2.getString("runs"));
                        modelCommentry.setBatsman2Balls(Batsman2.getString("balls"));

                        JSONObject Bowler1 = endOver.getJSONObject("Bowler1");
                        modelCommentry.setBowlerId(Bowler1.getString("bowlerId"));
                        modelCommentry.setBowlerName(Bowler1.getString("bowlerName"));
                        modelCommentry.setBowlerOvers(Bowler1.getString("overs"));
                        modelCommentry.setBowlerRuns(Bowler1.getString("runs"));
                        modelCommentry.setBowlerMaiden(Bowler1.getString("maiden"));
                        modelCommentry.setBowlerWicket(Bowler1.getString("wickets"));

                        arrayteam1Commentry.add(modelCommentry);

                        JSONArray overBall = jsonObject.getJSONArray("overBall");
                        for (int j = 0; j < overBall.length(); j++) {

                            JSONObject jsonObject1 = overBall.getJSONObject(j);
                            ModelCommentry modelCommentry1 = new ModelCommentry();
                            modelCommentry1.setRowType(2);
                            modelCommentry1.setId(jsonObject1.getString("id"));
                            modelCommentry1.setInningOverCount(jsonObject1.getString("inningOverCount"));
                            modelCommentry1.setIsWicket(jsonObject1.getString("isWicket"));
                            modelCommentry1.setBowlingString(jsonObject1.getString("bowlingString"));
                            modelCommentry1.setOutstring(jsonObject1.getString("outstring"));
                            modelCommentry1.setRunScored(jsonObject1.getString("runScored"));
                            modelCommentry1.setOverString(jsonObject1.getString("overString"));

                            arrayteam1Commentry.add(modelCommentry1);
                        }
                    }
                    adapterTeamCommentry = new AdapterTeamCommentry(context, this, arrayteam1Commentry);
                    recyclerViewCommentry.setAdapter(adapterTeamCommentry);

                    if (arrayteam1Commentry.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText(btn_teama.getText().toString() + " commentry not available");
                    }
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jsonObject = data.getJSONObject(i);
                        ModelCommentry modelCommentry = new ModelCommentry();
                        modelCommentry.setRowType(1);
                        modelCommentry.setOver(jsonObject.getString("over"));
                        modelCommentry.setTeam1Name(btn_teamb.getText().toString());
                        modelCommentry.setOverString(jsonObject.getString("overStrings"));

                        JSONObject endOver = jsonObject.getJSONObject("endOver");
                        modelCommentry.setRunInOver(endOver.getString("runInOver"));
                        modelCommentry.setWicketInOver(endOver.getString("wicketInOver"));
                        modelCommentry.setTotalRun(endOver.getString("totalRun"));
                        modelCommentry.setTotalWicket(endOver.getString("totalWicket"));
                        JSONObject Batsman1 = endOver.getJSONObject("Batsman1");
                        modelCommentry.setBatsman1Id(Batsman1.getString("batsmanId"));
                        modelCommentry.setBatsman1Name(Batsman1.getString("name"));
                        modelCommentry.setBatsman1Runs(Batsman1.getString("runs"));
                        modelCommentry.setBatsman1balls(Batsman1.getString("balls"));

                        JSONObject Batsman2 = endOver.getJSONObject("Batsman2");
                        modelCommentry.setBatsman2Id(Batsman2.getString("batsmanId"));
                        modelCommentry.setBatsman2Name(Batsman2.getString("name"));
                        modelCommentry.setBatsman2Runs(Batsman2.getString("runs"));
                        modelCommentry.setBatsman2Balls(Batsman2.getString("balls"));

                        JSONObject Bowler1 = endOver.getJSONObject("Bowler1");
                        modelCommentry.setBowlerId(Bowler1.getString("bowlerId"));
                        modelCommentry.setBowlerName(Bowler1.getString("bowlerName"));
                        modelCommentry.setBowlerOvers(Bowler1.getString("overs"));
                        modelCommentry.setBowlerRuns(Bowler1.getString("runs"));
                        modelCommentry.setBowlerMaiden(Bowler1.getString("maiden"));
                        modelCommentry.setBowlerWicket(Bowler1.getString("wickets"));

                        arrayteam2Commentry.add(modelCommentry);

                        JSONArray overBall = jsonObject.getJSONArray("overBall");
                        for (int j = 0; j < overBall.length(); j++) {

                            JSONObject jsonObject1 = overBall.getJSONObject(j);
                            ModelCommentry modelCommentry1 = new ModelCommentry();
                            modelCommentry1.setRowType(2);
                            modelCommentry1.setId(jsonObject1.getString("id"));
                            modelCommentry1.setInningOverCount(jsonObject1.getString("inningOverCount"));
                            modelCommentry1.setIsWicket(jsonObject1.getString("isWicket"));
                            modelCommentry1.setBowlingString(jsonObject1.getString("bowlingString"));
                            modelCommentry1.setOutstring(jsonObject1.getString("outstring"));
                            modelCommentry1.setRunScored(jsonObject1.getString("runScored"));
                            modelCommentry1.setOverString(jsonObject1.getString("overString"));

                            arrayteam2Commentry.add(modelCommentry1);
                        }
                    }
                    adapterTeamCommentry1 = new AdapterTeamCommentry(context, this, arrayteam2Commentry);
                    recyclerViewCommentry2.setAdapter(adapterTeamCommentry1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}


