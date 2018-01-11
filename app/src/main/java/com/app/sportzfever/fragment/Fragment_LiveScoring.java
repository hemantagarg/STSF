package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterRecentBalls;
import com.app.sportzfever.adapter.AdapterTeamBattingMatch;
import com.app.sportzfever.adapter.AdapterTeamBowlingMatch;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.BattingStats;
import com.app.sportzfever.models.BowlingStats;
import com.app.sportzfever.models.ModelLiveInnings;
import com.app.sportzfever.models.ModelRecentBall;
import com.app.sportzfever.utils.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_LiveScoring extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_team1batting, list_team1bowling, list_team2batting, list_team2bowling, recycler_recent_balls;
    private Bundle b;
    private TextView text_recentball;
    private Activity context;
    private AdapterTeamBattingMatch adapterTeam1BattingMatch, adapterTeam2BattingMatch;
    private AdapterTeamBowlingMatch adapterTeam1BowlingMatch, adapterTeam2BowlingMatch;

    private ArrayList<BattingStats> arrayteam1Batting, arrayteam2Batting;
    private ArrayList<BowlingStats> arrayteam1Bowling, arrayteam2Bowling;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata, text_team1batting, text_team1bowling, text_team2batting, text_team2bowling;
    private LinearLayout layout_team2, layout_team1, layout_team1batting, layout_team1bowling, layout_team2batting, layout_team2bowling;
    public static Fragment_LiveScoring fragment_teamJoin_request;
    private final String TAG = Fragment_LiveScoring.class.getSimpleName();
    private String avtarid = "";
    private TextView text_extras, text_run, textOk, text0, text1, text2, text3, text4, text5, text6, textMoreOptions, textUndo;
    private CheckBox checkbox_no_ball, checkbox_wide_ball, checkbox_bye, checkbox_leg_bye, checkbox_out;
    private boolean isTeam1BattingVisible = true;
    private boolean isTeam1BowlingVisible = true;
    private boolean isTeam2BattingVisible = true;
    private boolean isTeam2BowlingVisible = true;
    View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdapterRecentBalls adapterRecentBalls;
    private ArrayList<ModelRecentBall> recentBallArrayList = new ArrayList<>();
    JSONObject data;

    public static Fragment_LiveScoring getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_LiveScoring();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_live_scoring, container, false);
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
        recycler_recent_balls = (RecyclerView) view.findViewById(R.id.recycler_recent_balls);
        text_recentball = (TextView) view.findViewById(R.id.text_recentball);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        list_team1batting.setLayoutManager(new LinearLayoutManager(context));
        list_team1bowling.setLayoutManager(new LinearLayoutManager(context));
        list_team2batting.setLayoutManager(new LinearLayoutManager(context));
        list_team2bowling.setLayoutManager(new LinearLayoutManager(context));
        recycler_recent_balls.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

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
        btn_teamb = (Button) view.findViewById(R.id.btn_teamb);
        btn_teama = (Button) view.findViewById(R.id.btn_teama);

        text_extras = (TextView) view.findViewById(R.id.text_extras);
        text_run = (TextView) view.findViewById(R.id.text_run);
        textOk = (TextView) view.findViewById(R.id.textOk);
        text0 = (TextView) view.findViewById(R.id.text0);
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        text5 = (TextView) view.findViewById(R.id.text5);
        text6 = (TextView) view.findViewById(R.id.text6);
        textMoreOptions = (TextView) view.findViewById(R.id.textMoreOptions);
        textUndo = (TextView) view.findViewById(R.id.textUndo);
        checkbox_no_ball = (CheckBox) view.findViewById(R.id.checkbox_no_ball);
        checkbox_wide_ball = (CheckBox) view.findViewById(R.id.checkbox_wide_ball);
        checkbox_bye = (CheckBox) view.findViewById(R.id.checkbox_bye);
        checkbox_leg_bye = (CheckBox) view.findViewById(R.id.checkbox_leg_bye);
        checkbox_out = (CheckBox) view.findViewById(R.id.checkbox_out);
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
                getLiveScores();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTeam1Data(JSONObject data) {
        try {
            JSONArray innings = data.getJSONArray("innings");

            if (innings.length() > 0) {
                recentBallArrayList.clear();
                JSONObject jo = innings.getJSONObject(0);
                JSONArray recentBall = jo.getJSONArray("recentBall");
                for (int i = 0; i < recentBall.length(); i++) {

                    JSONObject jsonObject = recentBall.getJSONObject(i);
                    JSONArray overBall = jsonObject.getJSONArray("overBall");

                    for (int j = 0; j < overBall.length(); j++) {
                        JSONObject ob = overBall.getJSONObject(j);

                        ModelRecentBall modelRecentBall = new ModelRecentBall();
                        modelRecentBall.setId(ob.getString("id"));
                        modelRecentBall.setInningOverCount(ob.getString("inningOverCount"));
                        modelRecentBall.setIsWicket(ob.getString("isWicket"));
                        modelRecentBall.setBatsmanId(ob.getString("batsmanId"));
                        modelRecentBall.setBowlerId(ob.getString("bowlerId"));
                        modelRecentBall.setBowlingString(ob.getString("bowlingString"));
                        modelRecentBall.setRunScored(ob.getString("runScored"));
                        modelRecentBall.setOverString(ob.getString("overString"));

                        recentBallArrayList.add(modelRecentBall);
                    }
                    adapterRecentBalls = new AdapterRecentBalls(context, this, recentBallArrayList);
                    recycler_recent_balls.setAdapter(adapterRecentBalls);

                }

                Gson gson = new Gson();
                arrayteam1Batting.clear();
                arrayteam1Bowling.clear();
                ModelLiveInnings modelInnings = null;
                try {
                    modelInnings = gson.fromJson(innings.getJSONObject(0).toString(), ModelLiveInnings.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                    recycler_recent_balls.setVisibility(View.VISIBLE);
                    text_recentball.setVisibility(View.VISIBLE);
                } else {
                    layout_team1.setVisibility(View.GONE);
                    recycler_recent_balls.setVisibility(View.GONE);
                    text_recentball.setVisibility(View.GONE);
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
                }
            } else {
                recycler_recent_balls.setVisibility(View.GONE);
                text_recentball.setVisibility(View.GONE);
                layout_team1.setVisibility(View.GONE);
                text_nodata.setVisibility(View.VISIBLE);
                text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTeam2Data(JSONObject data) {
        try {
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
                    text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
                }
            } else {
                layout_team2.setVisibility(View.GONE);
                text_nodata.setVisibility(View.VISIBLE);
                text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setlistener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveScoresRefresh();
            }
        });

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
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);

        } else if (flag == 2) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayteam1Bowling.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);

        }*/
    }


    private void getLiveScores() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //https://sfscoring.betasportzfever.com/getLiveScore/EVENT/101/479a44a634f82b0394f78352d302ec36
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_LIVE_SCORE + avtarid + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLiveScoresRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //https://sfscoring.betasportzfever.com/getLiveScore/EVENT/101/479a44a634f82b0394f78352d302ec36
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_LIVE_SCORE + avtarid + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbjectNoProgress(url, new JSONObject(), Request.Method.GET);

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

                    data = jObject.getJSONObject("data");
                    setTeam1Data(data);
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
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


