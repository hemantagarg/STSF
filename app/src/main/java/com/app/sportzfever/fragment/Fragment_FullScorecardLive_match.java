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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterTeamBattingMatch;
import com.app.sportzfever.adapter.AdapterTeamBowlingMatch;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.BattingStats;
import com.app.sportzfever.models.BowlingStats;
import com.app.sportzfever.models.ModelLiveInnings;
import com.app.sportzfever.utils.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_FullScorecardLive_match extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_team1batting, list_team1bowling, list_team2batting, list_team2bowling;
    private Bundle b;
    private Activity context;
    private AdapterTeamBattingMatch adapterTeam1BattingMatch, adapterTeam2BattingMatch;
    private AdapterTeamBowlingMatch adapterTeam1BowlingMatch, adapterTeam2BowlingMatch;

    private ArrayList<BattingStats> arrayteam1Batting, arrayteam2Batting;
    private ArrayList<BowlingStats> arrayteam1Bowling, arrayteam2Bowling;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata, text_team1batting, text_team1bowling, text_team2batting, text_team2bowling;
    LinearLayout layout_team2, layout_team1, layout_team1batting, layout_team1bowling, layout_team2batting, layout_team2bowling;
    public static Fragment_FullScorecardLive_match fragment_teamJoin_request;
    private final String TAG = Fragment_FullScorecardLive_match.class.getSimpleName();
    private String avtarid = "";
    private boolean isTeam1BattingVisible = true;
    private boolean isTeam1BowlingVisible = true;
    private boolean isTeam2BattingVisible = true;
    private boolean isTeam2BowlingVisible = true;
    View view;
    JSONObject data;
    private TextView text_extrarun, text_total, text_totalrun, text_extrarunrate, text_extrarun1, text_total1, text_totalrun1, text_extrarunrate1;

    public static Fragment_FullScorecardLive_match getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_FullScorecardLive_match();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_full_scorecard_match, container, false);
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
        arrayteam1Batting = new ArrayList<>();
        arrayteam1Bowling = new ArrayList<>();
        arrayteam2Batting = new ArrayList<>();
        arrayteam2Bowling = new ArrayList<>();
        list_team1batting = (RecyclerView) view.findViewById(R.id.list_team1batting);
        list_team1bowling = (RecyclerView) view.findViewById(R.id.list_team1bowling);
        list_team2batting = (RecyclerView) view.findViewById(R.id.list_team2batting);
        list_team2bowling = (RecyclerView) view.findViewById(R.id.list_team2bowling);

        list_team1batting.setLayoutManager(new LinearLayoutManager(context));
        list_team1bowling.setLayoutManager(new LinearLayoutManager(context));
        list_team2batting.setLayoutManager(new LinearLayoutManager(context));
        list_team2bowling.setLayoutManager(new LinearLayoutManager(context));

        layout_team1 = (LinearLayout) view.findViewById(R.id.layout_team1);
        layout_team2 = (LinearLayout) view.findViewById(R.id.layout_team2);
        layout_team1batting = (LinearLayout) view.findViewById(R.id.layout_team1batting);
        layout_team1bowling = (LinearLayout) view.findViewById(R.id.layout_team1bowling);
        layout_team2batting = (LinearLayout) view.findViewById(R.id.layout_team2batting);
        layout_team2bowling = (LinearLayout) view.findViewById(R.id.layout_team2bowling);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        text_team1batting = (TextView) view.findViewById(R.id.text_team1batting);
        text_team1bowling = (TextView) view.findViewById(R.id.text_team1bowling);
        text_team2bowling = (TextView) view.findViewById(R.id.text_team2bowling);
        text_team2batting = (TextView) view.findViewById(R.id.text_team2batting);
        text_extrarun = (TextView) view.findViewById(R.id.text_extrarun);
        text_total = (TextView) view.findViewById(R.id.text_total);
        text_totalrun = (TextView) view.findViewById(R.id.text_totalrun);
        text_extrarunrate = (TextView) view.findViewById(R.id.text_extrarunrate);
        text_extrarun1 = (TextView) view.findViewById(R.id.text_extrarun1);
        text_total1 = (TextView) view.findViewById(R.id.text_total1);
        text_totalrun1 = (TextView) view.findViewById(R.id.text_totalrun1);
        text_extrarunrate1 = (TextView) view.findViewById(R.id.text_extrarunrate1);

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
                JSONObject team1 = data.getJSONObject("team1");
                JSONObject team2 = data.getJSONObject("team2");

                btn_teamb.setText(team2.getString("name"));
                btn_teama.setText(team1.getString("name"));

                setTeam1Data(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTeam1Data(JSONObject data) {
        try {
            JSONObject match = data.getJSONObject("match");
            String isTeam1ScoringOnSf = match.getString("isTeam1ScoringOnSf");
            JSONArray innings = data.getJSONArray("innings");
            if (innings.length() > 0) {
                Gson gson = new Gson();
                arrayteam1Batting.clear();
                arrayteam1Bowling.clear();
                ModelLiveInnings modelInnings = null;
                try {
                    modelInnings = gson.fromJson(innings.getJSONObject(0).toString(), ModelLiveInnings.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                text_extrarun.setText(modelInnings.getExtras());
                text_total.setText("Total (" + modelInnings.getWickets() + " wickets, " + modelInnings.getPlayedOvers() + " overs)");
                text_totalrun.setText(modelInnings.getTotalRunsScored());
                text_extrarunrate.setText("Run Rt: " + modelInnings.getOverRate());

                if (modelInnings.getBattingStats() != null && modelInnings.getBattingStats().length > 0) {
                    for (int i = 0; i < modelInnings.getBattingStats().length; i++) {
                        arrayteam1Batting.add(modelInnings.getBattingStats()[i]);
                    }

                    for (int i = 0; i < modelInnings.getBowlingStats().length; i++) {
                        arrayteam1Bowling.add(modelInnings.getBowlingStats()[i]);
                    }
                    adapterTeam1BattingMatch = new AdapterTeamBattingMatch(getActivity(), this, arrayteam1Batting);
                    list_team1batting.setAdapter(adapterTeam1BattingMatch);

                    text_team1batting.setText(modelInnings.getBattingTeamName() + " - Batting");
                    text_team1bowling.setText(modelInnings.getBowlingTeamName() + " - Bowling");

                    adapterTeam1BowlingMatch = new AdapterTeamBowlingMatch(getActivity(), this, arrayteam1Bowling);
                    list_team1bowling.setAdapter(adapterTeam1BowlingMatch);
                    text_nodata.setVisibility(View.GONE);
                    layout_team1.setVisibility(View.VISIBLE);

                } else {
                    layout_team1.setVisibility(View.GONE);
                    text_nodata.setVisibility(View.VISIBLE);
                    if (isTeam1ScoringOnSf.equals("1"))
                        text_nodata.setText("Inning not started yet");
                    else
                        text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
                }
            } else {
                layout_team1.setVisibility(View.GONE);
                text_nodata.setVisibility(View.VISIBLE);
                if (isTeam1ScoringOnSf.equals("1"))
                    text_nodata.setText("Inning not started yet");
                else
                    text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTeam2Data(JSONObject data) {
        try {
            JSONObject match = data.getJSONObject("match");
            String isTeam2ScoringOnSf = match.getString("isTeam2ScoringOnSf");
            JSONArray innings = data.getJSONArray("innings");
            if (innings.length() > 0 && innings.length() > 1) {
                Gson gson = new Gson();
                arrayteam2Batting.clear();
                arrayteam2Bowling.clear();
                ModelLiveInnings modelInnings = null;
                try {
                    modelInnings = gson.fromJson(innings.getJSONObject(1).toString(), ModelLiveInnings.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                text_extrarun1.setText(modelInnings.getExtras());
                text_total1.setText("Total (" + modelInnings.getWickets() + " wickets, " + modelInnings.getPlayedOvers() + " overs)");
                text_totalrun1.setText(modelInnings.getTotalRunsScored());
                text_extrarunrate1.setText("Run Rate: " + modelInnings.getOverRate());

                if (modelInnings.getBattingStats() != null && modelInnings.getBattingStats().length > 0) {
                    for (int i = 0; i < modelInnings.getBattingStats().length; i++) {
                        arrayteam2Batting.add(modelInnings.getBattingStats()[i]);
                    }

                    for (int i = 0; i < modelInnings.getBowlingStats().length; i++) {
                        arrayteam2Bowling.add(modelInnings.getBowlingStats()[i]);
                    }
                    adapterTeam2BattingMatch = new AdapterTeamBattingMatch(getActivity(), this, arrayteam2Batting);
                    list_team2batting.setAdapter(adapterTeam2BattingMatch);

                    text_team2batting.setText(modelInnings.getBattingTeamName() + " - Batting");
                    text_team2bowling.setText(modelInnings.getBowlingTeamName() + " - Bowling");

                    adapterTeam2BowlingMatch = new AdapterTeamBowlingMatch(getActivity(), this, arrayteam2Bowling);
                    list_team2bowling.setAdapter(adapterTeam2BowlingMatch);
                    text_nodata.setVisibility(View.GONE);
                    layout_team2.setVisibility(View.VISIBLE);
                } else {
                    layout_team2.setVisibility(View.GONE);
                    text_nodata.setVisibility(View.VISIBLE);
                    if (isTeam2ScoringOnSf.equals("1"))
                        text_nodata.setText("Inning not started yet");
                    else
                        text_nodata.setText(btn_teamb.getText().toString() + "  inning is not scored on Sportzfever.");
                }
            } else {
                layout_team2.setVisibility(View.GONE);
                text_nodata.setVisibility(View.VISIBLE);
                if (isTeam2ScoringOnSf.equals("1"))
                    text_nodata.setText("Inning not started yet");
                else
                    text_nodata.setText(btn_teamb.getText().toString() + "  inning is not scored on Sportzfever.");
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
                layout_team2.setVisibility(View.GONE);
                layout_team1.setVisibility(View.VISIBLE);
                setTeam1Data(data);
            }
        });
        btn_teamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                layout_team2.setVisibility(View.VISIBLE);
                layout_team1.setVisibility(View.GONE);
                setTeam2Data(data);

            }
        });
        text_team1batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeam1BattingVisible) {
                    layout_team1batting.setVisibility(View.GONE);
                    isTeam1BattingVisible = false;
                    text_team1batting.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_white, 0);
                } else {
                    layout_team1batting.setVisibility(View.VISIBLE);
                    text_team1batting.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow_white, 0);
                    isTeam1BattingVisible = true;
                }
            }
        });
        text_team1bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeam1BowlingVisible) {
                    layout_team1bowling.setVisibility(View.GONE);
                    text_team1bowling.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_white, 0);
                    isTeam1BowlingVisible = false;
                } else {
                    layout_team1bowling.setVisibility(View.VISIBLE);
                    text_team1bowling.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow_white, 0);
                    isTeam1BowlingVisible = true;
                }
            }
        });
        text_team2batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeam2BattingVisible) {
                    layout_team2batting.setVisibility(View.GONE);
                    text_team2batting.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_white, 0);
                    isTeam2BattingVisible = false;
                } else {
                    layout_team2batting.setVisibility(View.VISIBLE);
                    text_team2batting.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow_white, 0);
                    isTeam2BattingVisible = true;
                }
            }
        });
        text_team2bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTeam2BowlingVisible) {
                    text_team2bowling.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_white, 0);
                    layout_team2bowling.setVisibility(View.GONE);
                    isTeam2BowlingVisible = false;
                } else {
                    layout_team2bowling.setVisibility(View.VISIBLE);
                    text_team2bowling.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow_white, 0);
                    isTeam2BowlingVisible = true;
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
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);

        } else if (flag == 2) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayteam1Bowling.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);

        }*/
    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
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


