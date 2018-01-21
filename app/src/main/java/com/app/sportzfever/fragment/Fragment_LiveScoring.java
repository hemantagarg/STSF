package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterLiveScoringTeamBattingMatch;
import com.app.sportzfever.adapter.AdapterRecentBalls;
import com.app.sportzfever.adapter.AdapterTeamBowlingMatch;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ChoiceDialogClickListener;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.BattingStats;
import com.app.sportzfever.models.BowlingStats;
import com.app.sportzfever.models.ModelLiveInnings;
import com.app.sportzfever.models.ModelRecentBall;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.sportzfever.R.id.playedOversTwo;
import static com.app.sportzfever.R.id.runScoredTwo;
import static com.app.sportzfever.R.id.wicketsTwo;


public class Fragment_LiveScoring extends BaseFragment implements ApiResponse, OnCustomItemClicListener, View.OnClickListener {

    private RecyclerView list_team1batting, list_team1bowling, list_team2batting, list_team2bowling, recycler_recent_balls;
    private Bundle b;
    private TextView text_recentball, textRunsScored;
    private Activity context;
    private Spinner spinnerouttype, spinneroutbyfieldername, spinneroutplayername;
    private AdapterLiveScoringTeamBattingMatch adapterTeam1BattingMatch;
    private AdapterTeamBowlingMatch adapterTeam1BowlingMatch;
    private ArrayAdapter<String> adapterouttype, adapterFielderList, adapterBatsmanList, adapterSelctNewBatsman, adapterSelectNewBatsman2, adapterSelctNewBowler;
    private ArrayList<String> battingStatsPlayerList = new ArrayList<>();
    private ArrayList<BattingStats> arrayteam1Batting;
    private ArrayList<String> listBatsman = new ArrayList<>();
    private ArrayList<String> listNewBatsmanName = new ArrayList<>();
    private ArrayList<String> listNewBowlerName = new ArrayList<>();
    private ArrayList<String> listNewBowlerId = new ArrayList<>();
    private ArrayList<String> listNewBatsmanId = new ArrayList<>();
    private ArrayList<String> listBatsmanId = new ArrayList<>();
    private ArrayList<String> listFielderId = new ArrayList<>();
    private ArrayList<String> listFielderName = new ArrayList<>();
    private ArrayList<BowlingStats> arrayteam1Bowling;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata, text_team1batting, text_team1bowling, text_team2batting, text_team2bowling;
    private LinearLayout layout_team2, layout_team1, layout_team1batting, layout_team1bowling, layout_team2batting, layout_team2bowling, lin_out_action, lin_out_batsanspinner;
    public static Fragment_LiveScoring fragment_teamJoin_request;
    private final String TAG = Fragment_LiveScoring.class.getSimpleName();
    private String eventId = "", strikerBatsmanId = "", nonStrikerBatsmanId = "", IsScorerForTeam2 = "", bowlerId = "", inningId = "";
    private TextView text_extras, text_run, textOk, text0, text1, text2, text3, text4, text5, text6, textMoreOptions, textUndo;
    private CheckBox checkbox_no_ball, checkbox_wide_ball, checkbox_bye, checkbox_leg_bye, checkbox_out;
    private boolean isTeam1BattingVisible = true;
    private boolean isTeam1BowlingVisible = true;
    private boolean isTeam2BattingVisible = true;
    private boolean isTeam2BowlingVisible = true, isStriker = false;
    View view;
    private AdapterRecentBalls adapterRecentBalls;
    private ArrayList<ModelRecentBall> recentBallArrayList = new ArrayList<>();
    JSONObject data;
    private ArrayList<String> lisOutType = new ArrayList<>();
    private String team1Id = "", team2Id = "", matchId = "", currentOverId = "", blankBowlerId = "";
    private int runScored = -1;
    private String isTeam1ScoringOnSf = "", isTeam2ScoringOnSf = "", numberOfOvers = "", numberOfPlayers = "";

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
        list_team1batting = (RecyclerView) view.findViewById(R.id.list_team1batting);
        list_team1bowling = (RecyclerView) view.findViewById(R.id.list_team1bowling);
        list_team2batting = (RecyclerView) view.findViewById(R.id.list_team2batting);
        list_team2bowling = (RecyclerView) view.findViewById(R.id.list_team2bowling);
        recycler_recent_balls = (RecyclerView) view.findViewById(R.id.recycler_recent_balls);
        text_recentball = (TextView) view.findViewById(R.id.text_recentball);
        textRunsScored = (TextView) view.findViewById(R.id.textRunsScored);
        list_team1batting.setLayoutManager(new LinearLayoutManager(context));
        list_team1bowling.setLayoutManager(new LinearLayoutManager(context));
        list_team2batting.setLayoutManager(new LinearLayoutManager(context));
        list_team2bowling.setLayoutManager(new LinearLayoutManager(context));
        recycler_recent_balls.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        layout_team1 = (LinearLayout) view.findViewById(R.id.layout_team1);
        layout_team2 = (LinearLayout) view.findViewById(R.id.layout_team2);
        layout_team1batting = (LinearLayout) view.findViewById(R.id.layout_team1batting);
        lin_out_action = (LinearLayout) view.findViewById(R.id.lin_out_action);
        lin_out_batsanspinner = (LinearLayout) view.findViewById(R.id.lin_out_batsanspinner);
        layout_team1bowling = (LinearLayout) view.findViewById(R.id.layout_team1bowling);
        layout_team2batting = (LinearLayout) view.findViewById(R.id.layout_team2batting);
        layout_team2bowling = (LinearLayout) view.findViewById(R.id.layout_team2bowling);
//  spinner
        spinnerouttype = (Spinner) view.findViewById(R.id.spinnerouttype);
        spinneroutbyfieldername = (Spinner) view.findViewById(R.id.spinneroutbyfieldername);
        spinneroutplayername = (Spinner) view.findViewById(R.id.spinneroutplayername);

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

        text0.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text6.setOnClickListener(this);
        textUndo.setOnClickListener(this);
        textOk.setOnClickListener(this);
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
                eventId = bundle.getString("eventId");
                IsScorerForTeam2 = bundle.getString("IsScorerForTeam2");
                String maindata = bundle.getString("data");
                JSONObject data = new JSONObject(maindata);
                JSONObject match = data.getJSONObject("match");
                matchId = match.getString("id");
                team1Id = match.getString("team1Id");
                team2Id = match.getString("team2Id");
                isTeam1ScoringOnSf = match.getString("isTeam1ScoringOnSf");
                isTeam2ScoringOnSf = match.getString("isTeam2ScoringOnSf");
                numberOfPlayers = match.getString("numberOfPlayers");
                numberOfOvers = match.getString("numberOfOvers");
                //  getLiveScores();
                JSONArray team1Squad = data.getJSONArray("team1Squad");
                JSONArray team2Squad = data.getJSONArray("team2Squad");
                JSONArray innings = data.getJSONArray("innings");
                checkInning(innings, team1Squad, team2Squad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTeam1Data(JSONObject jo, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            if (jo != null) {
                adapterouttype = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, lisOutType);
                spinnerouttype.setAdapter(adapterouttype);
                recentBallArrayList.clear();
                clearTextSelection();
                checkbox_out.setChecked(false);
                checkbox_leg_bye.setChecked(false);
                checkbox_bye.setChecked(false);
                checkbox_no_ball.setChecked(false);
                checkbox_wide_ball.setChecked(false);
                runScored = -1;
                JSONArray overBall = jo.getJSONArray("overBalls");

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

                Gson gson = new Gson();
                arrayteam1Batting.clear();
                arrayteam1Bowling.clear();
                listBatsmanId.clear();
                listBatsman.clear();
                ModelLiveInnings modelInnings = null;
                try {
                    modelInnings = gson.fromJson(jo.toString(), ModelLiveInnings.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (modelInnings != null) {
                    inningId = modelInnings.getId();
                    if (recentBallArrayList.size() == 0) {
                        currentOverId = "-1";
                    } else {
                        currentOverId = modelInnings.getCurrentOverId();
                    }
                    textRunsScored.setText(modelInnings.getInningScoreString());

                    text_run.setText(modelInnings.getExtras());
                    if (modelInnings.getExtraRuns() != null) {

                        StringBuilder runs = new StringBuilder();
                        if (modelInnings.getExtraRuns().getWd() != null && !modelInnings.getExtraRuns().getWd().equalsIgnoreCase("0")) {
                            runs.append("wd " + modelInnings.getExtraRuns().getWd() + ", ");
                        }
                        if (modelInnings.getExtraRuns().getNb() != null && !modelInnings.getExtraRuns().getNb().equalsIgnoreCase("0")) {
                            runs.append("nb " + modelInnings.getExtraRuns().getNb() + ", ");
                        }
                        if (modelInnings.getExtraRuns().getB() != null && !modelInnings.getExtraRuns().getB().equalsIgnoreCase("0")) {
                            runs.append("b " + modelInnings.getExtraRuns().getB() + ", ");
                        }
                        if (modelInnings.getExtraRuns().getLb() != null && !modelInnings.getExtraRuns().getLb().equalsIgnoreCase("0")) {
                            runs.append("lb " + modelInnings.getExtraRuns().getLb() + ", ");
                        }
                        if (runs.length() > 0) {
                            runs.deleteCharAt(runs.lastIndexOf(","));
                        }
                        text_extras.setText("Extras ( " + runs + " )");
                    }

                    if (modelInnings.getDrinksBreak().equals("1")) {
                        AppUtils.customAlertDialogWithoutTitle(context, getString(R.string.drink_break), "RESUME", new ChoiceDialogClickListener() {
                            @Override
                            public void onClickOfPositive() {
                                drinkBreak(AppConstant.END);
                            }

                            @Override
                            public void onClickOfNegative() {

                            }
                        });
                    }

                }

                setFielderList(modelInnings, team1Squad, team2Squad);
                listBatsmanId.add("-1");
                listBatsman.add("Select Out Batsman");
                if (modelInnings != null && modelInnings.getBattingStats() != null && modelInnings.getBattingStats().length > 0) {
                    for (int i = 0; i < modelInnings.getBattingStats().length; i++) {
                        BattingStats battingStats = modelInnings.getBattingStats()[i];
                        if (battingStats.getBatsmanId().equals(modelInnings.getBatsmanOnStrike())) {
                            battingStats.setBattingStriker(true);
                            strikerBatsmanId = battingStats.getBatsmanId();
                            arrayteam1Batting.add(0, battingStats);
                            listBatsmanId.add(battingStats.getBatsmanId());
                            listBatsman.add(battingStats.getBatsmanAvatarName());
                        }
                        if (battingStats.getBatsmanId().equals(modelInnings.getBatsmanOnNonStrike())) {
                            arrayteam1Batting.add(battingStats);
                            nonStrikerBatsmanId = battingStats.getBatsmanId();
                            listBatsmanId.add(battingStats.getBatsmanId());
                            listBatsman.add(battingStats.getBatsmanAvatarName());
                        }
                        battingStatsPlayerList.add(battingStats.getBatsmanId());
                    }
                }

                adapterBatsmanList = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listBatsman);
                spinneroutplayername.setAdapter(adapterBatsmanList);


                if (modelInnings != null && modelInnings.getBowlingStats() != null && modelInnings.getBowlingStats().length > 0) {
                    for (int i = 0; i < modelInnings.getBowlingStats().length; i++) {
                        BowlingStats bowlingStats = modelInnings.getBowlingStats()[i];
                        if (bowlingStats.getBowlerId().equals(modelInnings.getCurrentBowlerId())) {
                            arrayteam1Bowling.add(bowlingStats);
                            bowlerId = bowlingStats.getBowlerId();
                        }
                    }
                }
                if (modelInnings.getBatsmanOnStrike().equals("-1") && modelInnings.getBatsmanOnNonStrike().equals("-1") && modelInnings.getCurrentBowlerId().equals("-1")) {
                    selectBowlerBatsmanList(modelInnings, team1Squad, team2Squad, true);
                } else if ((modelInnings.getBatsmanOnStrike().equals("-1") || modelInnings.getBatsmanOnNonStrike().equals("-1")) && modelInnings.getCurrentBowlerId().equals("-1")) {
                    selectBowlerBatsmanList(modelInnings, team1Squad, team2Squad, false);
                } else if (modelInnings.getBatsmanOnStrike().equals("-1")) {
                    selectBatsmanList(modelInnings, team1Squad, team2Squad);
                    isStriker = true;
                } else if (modelInnings.getBatsmanOnNonStrike().equals("-1")) {
                    selectBatsmanList(modelInnings, team1Squad, team2Squad);
                    isStriker = false;
                } else if (modelInnings.getCurrentBowlerId().equals("-1")) {
                    setBowlerList(modelInnings, team1Squad, team2Squad);
                }
                adapterTeam1BattingMatch = new AdapterLiveScoringTeamBattingMatch(getActivity(), this, arrayteam1Batting);
                list_team1batting.setAdapter(adapterTeam1BattingMatch);

                text_team1batting.setText("Batting - " + modelInnings.getBattingTeamName());
                text_team1bowling.setText("Bowling - " + modelInnings.getBowlingTeamName());

                adapterTeam1BowlingMatch = new AdapterTeamBowlingMatch(getActivity(), this, arrayteam1Bowling);
                list_team1bowling.setAdapter(adapterTeam1BowlingMatch);
                text_nodata.setVisibility(View.GONE);
                layout_team1.setVisibility(View.VISIBLE);
                recycler_recent_balls.setVisibility(View.VISIBLE);
                text_recentball.setVisibility(View.GONE);
            } else {
                layout_team1.setVisibility(View.GONE);
                recycler_recent_balls.setVisibility(View.GONE);
                text_recentball.setVisibility(View.GONE);
                text_nodata.setVisibility(View.VISIBLE);
                text_nodata.setText(btn_teama.getText().toString() + "  inning is not scored on Sportzfever.");
            }
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    private void selectBowlerBatsmanList(ModelLiveInnings modelInnings, JSONArray team1Squad, JSONArray team2Squad, boolean isAllOut) {
        try {
            listNewBatsmanId.clear();
            listNewBatsmanName.clear();
            if (modelInnings != null) {
                listNewBatsmanId.add("-1");
                listNewBatsmanName.add("Select Batsman");
                if (modelInnings.getBattingTeamId().equals(team1Id)) {
                    if (team1Squad != null && team1Squad.length() > 0) {
                        for (int i = 0; i < team1Squad.length(); i++) {
                            JSONObject jo = team1Squad.getJSONObject(i);
                            if (isAllOut) {
                                listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                listNewBatsmanName.add(jo.getString("name"));
                            } else {
                                if (!battingStatsPlayerList.contains(jo.getString("playerAvatarId"))) {
                                    listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                    listNewBatsmanName.add(jo.getString("name"));
                                }
                            }

                        }
                    }
                } else if (modelInnings.getBattingTeamId().equals(team2Id)) {
                    if (team2Squad != null && team2Squad.length() > 0) {
                        for (int i = 0; i < team2Squad.length(); i++) {
                            JSONObject jo = team2Squad.getJSONObject(i);
                            if (isAllOut) {
                                listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                listNewBatsmanName.add(jo.getString("name"));
                            } else {
                                if (!battingStatsPlayerList.contains(jo.getString("playerAvatarId"))) {
                                    listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                    listNewBatsmanName.add(jo.getString("name"));
                                }
                            }
                        }
                    }
                }
                adapterSelctNewBatsman = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listNewBatsmanName);
                adapterSelectNewBatsman2 = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listNewBatsmanName);

                listNewBowlerId.clear();
                listNewBowlerName.clear();
                if (modelInnings != null) {
                    listNewBowlerId.add("-1");
                    listNewBowlerName.add("Select Bowler");
                    if (modelInnings.getBowlingTeamId().equals(team1Id)) {
                        if (team1Squad != null && team1Squad.length() > 0) {
                            for (int i = 0; i < team1Squad.length(); i++) {
                                JSONObject jo = team1Squad.getJSONObject(i);
                                if (isAllOut) {
                                    if (!jo.getString("name").equalsIgnoreCase("")) {
                                        listNewBowlerName.add(jo.getString("name"));
                                        listNewBowlerId.add(jo.getString("playerAvatarId"));
                                    } else {
                                        if (blankBowlerId.equals("")) {
                                            blankBowlerId = jo.getString("playerAvatarId");
                                        }
                                    }
                                } else {
                                    if (!modelInnings.getPreviousBowlerId().equals(jo.getString("playerAvatarId"))) {
                                        listNewBowlerName.add(jo.getString("name"));
                                        listNewBowlerId.add(jo.getString("playerAvatarId"));
                                    }
                                }
                            }
                        }
                        if (isTeam1ScoringOnSf.equals("0")) {
                            listNewBowlerName.add(AppConstant.ADD_NEW_BOWLER);
                            listNewBowlerId.add("-2");
                        }

                    } else if (modelInnings.getBowlingTeamId().equals(team2Id)) {
                        if (team2Squad != null && team2Squad.length() > 0) {
                            for (int i = 0; i < team2Squad.length(); i++) {
                                JSONObject jo = team2Squad.getJSONObject(i);
                                if (isAllOut) {
                                    if (!jo.getString("name").equalsIgnoreCase("")) {
                                        listNewBowlerName.add(jo.getString("name"));
                                        listNewBowlerId.add(jo.getString("playerAvatarId"));
                                    } else {
                                        if (blankBowlerId.equals("")) {
                                            blankBowlerId = jo.getString("playerAvatarId");
                                        }
                                    }
                                } else {
                                    if (!modelInnings.getPreviousBowlerId().equals(jo.getString("playerAvatarId"))) {
                                        listNewBowlerName.add(jo.getString("name"));
                                        listNewBowlerId.add(jo.getString("playerAvatarId"));
                                    }
                                }
                            }
                        }
                        if (isTeam2ScoringOnSf.equals("0")) {
                            listNewBowlerName.add(AppConstant.ADD_NEW_BOWLER);
                            listNewBowlerId.add("-2");
                        }

                    }
                    adapterSelctNewBowler = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listNewBowlerName);
                    selectNewBatsmanBowlerDialog(isAllOut, modelInnings.getBowlingTeamId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectBatsmanList(ModelLiveInnings modelInnings, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            listNewBatsmanId.clear();
            listNewBatsmanName.clear();
            if (modelInnings != null) {
                listNewBatsmanId.add("-1");
                listNewBatsmanName.add("Select Batsman");
                if (modelInnings.getBattingTeamId().equals(team1Id)) {
                    if (team1Squad != null && team1Squad.length() > 0) {
                        for (int i = 0; i < team1Squad.length(); i++) {
                            JSONObject jo = team1Squad.getJSONObject(i);
                            if (!battingStatsPlayerList.contains(jo.getString("playerAvatarId"))) {
                                listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                listNewBatsmanName.add(jo.getString("name"));
                            }
                        }
                    }
                } else if (modelInnings.getBattingTeamId().equals(team2Id)) {
                    if (team2Squad != null && team2Squad.length() > 0) {
                        for (int i = 0; i < team2Squad.length(); i++) {
                            JSONObject jo = team2Squad.getJSONObject(i);
                            if (!battingStatsPlayerList.contains(jo.getString("playerAvatarId"))) {
                                listNewBatsmanId.add(jo.getString("playerAvatarId"));
                                listNewBatsmanName.add(jo.getString("name"));
                            }
                        }
                    }
                }
                adapterSelctNewBatsman = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listNewBatsmanName);
                selectNewBatsmanDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBowlerList(ModelLiveInnings modelInnings, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            listNewBowlerId.clear();
            listNewBowlerName.clear();
            if (modelInnings != null) {
                ArrayList<String> bowlerOversList = new ArrayList<>();
                ArrayList<String> bowlerStatsList = new ArrayList<>();
                for (int i = 0; i < modelInnings.getBowlingStats().length; i++) {
                    bowlerStatsList.add(modelInnings.getBowlingStats()[i].getBowlerId());
                    bowlerOversList.add(modelInnings.getBowlingStats()[i].getNumberOfOvers());
                }

                listNewBowlerId.add("-1");
                listNewBowlerName.add("Select Bowler");
                if (modelInnings.getBowlingTeamId().equals(team1Id)) {
                    if (team1Squad != null && team1Squad.length() > 0) {
                        for (int i = 0; i < team1Squad.length(); i++) {
                            JSONObject jo = team1Squad.getJSONObject(i);
                            if (!modelInnings.getPreviousBowlerId().equals(jo.getString("playerAvatarId"))) {

                                if (!jo.getString("name").equalsIgnoreCase("")) {
                                    if (bowlerStatsList.contains(jo.getString("playerAvatarId"))) {
                                        String overnew = bowlerOversList.get(bowlerStatsList.indexOf(jo.getString("playerAvatarId")));
                                        listNewBowlerName.add(jo.getString("name") + " (" + overnew + " bowled)");
                                    } else {
                                        listNewBowlerName.add(jo.getString("name") + " (0 bowled)");
                                    }
                                    listNewBowlerId.add(jo.getString("playerAvatarId"));
                                } else {
                                    if (blankBowlerId.equals("")) {
                                        blankBowlerId = jo.getString("playerAvatarId");
                                    }
                                }
                            }

                        }
                    }
                    if (isTeam1ScoringOnSf.equals("0")) {
                        listNewBowlerName.add(AppConstant.ADD_NEW_BOWLER);
                        listNewBowlerId.add("-2");
                    }
                } else if (modelInnings.getBowlingTeamId().equals(team2Id)) {
                    if (team2Squad != null && team2Squad.length() > 0) {
                        for (int i = 0; i < team2Squad.length(); i++) {
                            JSONObject jo = team2Squad.getJSONObject(i);
                            if (!modelInnings.getPreviousBowlerId().equals(jo.getString("playerAvatarId"))) {
                                if (!jo.getString("name").equalsIgnoreCase("")) {
                                    if (bowlerStatsList.contains(jo.getString("playerAvatarId"))) {
                                        String overnew = bowlerOversList.get(bowlerStatsList.indexOf(jo.getString("playerAvatarId")));
                                        listNewBowlerName.add(jo.getString("name") + " (" + overnew + " bowled)");
                                    } else {
                                        listNewBowlerName.add(jo.getString("name") + " (0 bowled)");
                                    }
                                    listNewBowlerId.add(jo.getString("playerAvatarId"));
                                } else {
                                    if (blankBowlerId.equals("")) {
                                        blankBowlerId = jo.getString("playerAvatarId");
                                    }
                                }
                            }
                        }
                    }
                    if (isTeam2ScoringOnSf.equals("0")) {
                        listNewBowlerName.add(AppConstant.ADD_NEW_BOWLER);
                        listNewBowlerId.add("-2");
                    }
                }
                adapterSelctNewBowler = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listNewBowlerName);
                selectNewBowlerDialog(modelInnings.getBowlingTeamId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setFielderList(ModelLiveInnings modelInnings, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            listFielderId.clear();
            listFielderName.clear();
            if (modelInnings != null) {
                listFielderId.add("-1");
                listFielderName.add("Select Fielder");
                if (modelInnings.getBowlingTeamId().equals(team1Id)) {
                    if (team1Squad != null && team1Squad.length() > 0) {
                        for (int i = 0; i < team1Squad.length(); i++) {
                            JSONObject jo = team1Squad.getJSONObject(i);
                            listFielderId.add(jo.getString("playerAvatarId"));
                            listFielderName.add(jo.getString("name"));
                        }
                    }
                } else if (modelInnings.getBowlingTeamId().equals(team2Id)) {
                    if (team2Squad != null && team2Squad.length() > 0) {
                        for (int i = 0; i < team2Squad.length(); i++) {
                            JSONObject jo = team2Squad.getJSONObject(i);
                            listFielderId.add(jo.getString("playerAvatarId"));
                            listFielderName.add(jo.getString("name"));
                        }
                    }
                }
                adapterFielderList = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listFielderName);
                spinneroutbyfieldername.setAdapter(adapterFielderList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void selectNewBatsmanBowlerDialog(final boolean isAllOut, final String bowlingTeamId) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_batsman_bowler_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            TextView text_batsman2 = (TextView) view.findViewById(R.id.text_batsman2);
            TextView text_batsman1 = (TextView) view.findViewById(R.id.text_batsman1);
            cross_img_rel.setVisibility(View.GONE);
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final Spinner spinnerBowler = (Spinner) view.findViewById(R.id.spinnerBowler);
            final Spinner spinnerBatsmanTwo = (Spinner) view.findViewById(R.id.spinnerBatsmanTwo);
            final Spinner spinnerBatsmanOne = (Spinner) view.findViewById(R.id.spinnerBatsmanOne);
            if (!isAllOut) {
                spinnerBatsmanTwo.setVisibility(View.GONE);
                text_batsman2.setVisibility(View.GONE);
                text_batsman1.setText("Select Batsman");
            }

            if (adapterSelctNewBowler != null)
                spinnerBowler.setAdapter(adapterSelctNewBowler);
            spinnerBatsmanOne.setAdapter(adapterSelctNewBatsman);
            spinnerBatsmanTwo.setAdapter(adapterSelectNewBatsman2);

            spinnerBowler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (spinnerBowler.getSelectedItem().toString().equals(AppConstant.ADD_NEW_BOWLER)) {
                        addNewBowlerDialog(bowlingTeamId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isAllOut) {
                        if (spinnerBatsmanTwo.getSelectedItemPosition() != 0 && spinnerBatsmanOne.getSelectedItemPosition() != 0
                                && spinnerBowler.getSelectedItemPosition() != 0) {
                            if (spinnerBatsmanOne.getSelectedItemPosition() != spinnerBatsmanTwo.getSelectedItemPosition()) {
                                addNewBowlerBatsman(spinnerBatsmanOne.getSelectedItemPosition(), spinnerBatsmanTwo.getSelectedItemPosition(), spinnerBowler.getSelectedItemPosition(), isAllOut);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Please select different Batsman", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Please select Bowler and Batsman", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (spinnerBatsmanOne.getSelectedItemPosition() != 0
                                && spinnerBowler.getSelectedItemPosition() != 0) {
                            addNewBowlerBatsman(spinnerBatsmanOne.getSelectedItemPosition(), spinnerBatsmanTwo.getSelectedItemPosition(), spinnerBowler.getSelectedItemPosition(), isAllOut);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Please select Bowler and Batsman", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }

    /**
     * Open dialog for the change batsman
     *
     * @param bowlingTeamId
     */
    private void selectNewBowlerDialog(final String bowlingTeamId) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_tem_captain, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            final EditText edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);
            name_requirement_txt.setText("SELECT NEW BOWLER");
            cross_img_rel.setVisibility(View.GONE);
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final Spinner spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);
            if (adapterSelctNewBowler != null)
                spinnerShareWith.setAdapter(adapterSelctNewBowler);

            spinnerShareWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (spinnerShareWith.getSelectedItem().toString().equals(AppConstant.ADD_NEW_BOWLER)) {
                        addNewBowlerDialog(bowlingTeamId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spinnerShareWith.getSelectedItemPosition() != 0) {
                        try {
                            addNewBowler(spinnerShareWith.getSelectedItemPosition());
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "Please select Bowler", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }

    private void saveInningScoreDialog() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.save_inningscore_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final EditText runScoredOne = (EditText) view.findViewById(runScoredTwo);
            final EditText wicketsOne = (EditText) view.findViewById(wicketsTwo);
            final EditText playedOversOne = (EditText) view.findViewById(playedOversTwo);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (runScoredOne.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, "Run Scored field cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (wicketsOne.getText().toString().equalsIgnoreCase("") || Integer.parseInt(wicketsOne.getText().toString()) > Integer.parseInt(numberOfPlayers)) {
                        Toast.makeText(context, "Number of wickets are less then or equal to " + numberOfPlayers + ".", Toast.LENGTH_SHORT).show();
                    } else if (playedOversOne.getText().toString().equalsIgnoreCase("") || Float.parseFloat(playedOversOne.getText().toString()) > Float.parseFloat(numberOfOvers)) {
                        Toast.makeText(context, "Number of overs are less then or equal to " + numberOfOvers + ".", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            JSONObject match = new JSONObject();
                            match.put("matchId", matchId);
                            JSONObject inning2 = new JSONObject();
                            JSONArray innings = new JSONArray();
                            inning2.put("inningNumber", "2");
                            inning2.put("totalRunsScored", runScoredOne.getText().toString());
                            inning2.put("wicketsInOver", wicketsOne.getText().toString());
                            inning2.put("playedOvers", playedOversOne.getText().toString());
                            inning2.put("totalOvers", numberOfOvers);

                            inning2.put("battingTeamId", team2Id);
                            inning2.put("bowlingTeamId", team1Id);
                            innings.put(inning2);
                            match.put("innings", innings);
                            jsonObject.put("match", match);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        saveResult(jsonObject);
                    }

                }
            });

            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }

    private void saveResult(JSONObject jsonObject) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.sf.com/saveScoreForMatches

                //http://sfscoring.sf.com/saveScoreForMatches
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_SCORERS_FOR_MATCH;
                new CommonAsyncTaskHashmap(8, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addNewBowlerDialog(final String bowlingTeamId) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.add_bowler_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);
            name_requirement_txt.setText("SELECT NEW BOWLER");
            cross_img_rel.setVisibility(View.VISIBLE);
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final EditText text_name1 = (EditText) view.findViewById(R.id.text_name1);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!text_name1.getText().toString().equals("")) {
                        try {
                            saveDummyBowler(text_name1.getText().toString(), bowlingTeamId);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "Please enter Bowler name", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }


    /**
     * Open dialog for the change batsman
     */
    private void selectNewBatsmanDialog() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_tem_captain, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            final EditText edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);
            name_requirement_txt.setText("SELECT NEW BATSMAN");
            cross_img_rel.setVisibility(View.GONE);
            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            final Spinner spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);
            if (adapterSelctNewBatsman != null)
                spinnerShareWith.setAdapter(adapterSelctNewBatsman);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spinnerShareWith.getSelectedItemPosition() != 0) {
                        try {
                            addNewBatsman(spinnerShareWith.getSelectedItemPosition());
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "Please select batsman", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }

    private void addNewBowlerBatsman(int selectedBatsmanPosition, int selectedBatsmanPosition2, int selectedBowler, boolean isAllOut) {

        arrayteam1Bowling.clear();
        BowlingStats bowlingStats = new BowlingStats();
        bowlingStats.setBowlerId(listNewBowlerId.get(selectedBowler));
        bowlingStats.setName(listNewBowlerName.get(selectedBowler));
        bowlingStats.setEconomy("0");
        bowlingStats.setMaiden("0");
        bowlingStats.setNumberOfOvers("0");
        bowlingStats.setRuns("0");
        bowlingStats.setWickets("0");
        bowlingStats.setExtras("0");
        bowlingStats.setTotalDotBall("0");
        bowlingStats.setTotalWideBall("0");
        bowlingStats.setTotalNoBall("0");
        arrayteam1Bowling.add(bowlingStats);
        adapterTeam1BowlingMatch.notifyDataSetChanged();
        bowlerId = bowlingStats.getBowlerId();

        BattingStats battingStats = new BattingStats();
        battingStats.setBatsmanId(listNewBatsmanId.get(selectedBatsmanPosition));
        battingStats.setBatsmanAvatarName(listNewBatsmanName.get(selectedBatsmanPosition));
        battingStats.setStatus("");
        battingStats.setRuns("0");
        battingStats.setDotball("0");
        battingStats.setBalls("0");
        battingStats.setFours("0");
        battingStats.setSixes("0");
        battingStats.setStrikeRate("0");
        if (isAllOut) {
            battingStats.setBattingStriker(true);
        }
        battingStats.setOutString("");
        battingStats.setOnStrike("0");
        battingStats.setPlayOrder("0");
        listBatsmanId.add(battingStats.getBatsmanId());
        listBatsman.add(battingStats.getBatsmanAvatarName());
        arrayteam1Batting.add(battingStats);

        if (isAllOut) {
            BattingStats battingStats1 = new BattingStats();
            battingStats1.setBatsmanId(listNewBatsmanId.get(selectedBatsmanPosition2));
            battingStats1.setBatsmanAvatarName(listNewBatsmanName.get(selectedBatsmanPosition2));
            battingStats1.setStatus("");
            battingStats1.setRuns("0");
            battingStats1.setDotball("0");
            battingStats1.setBalls("0");
            battingStats1.setFours("0");
            battingStats1.setSixes("0");
            battingStats1.setStrikeRate("0");
            battingStats1.setOutString("");
            battingStats1.setOnStrike("0");
            battingStats1.setPlayOrder("0");
            listBatsmanId.add(battingStats1.getBatsmanId());
            listBatsman.add(battingStats1.getBatsmanAvatarName());
            arrayteam1Batting.add(battingStats1);
        }

        adapterTeam1BattingMatch.notifyDataSetChanged();
        selectStriker(true);

    }


    private void addNewBowler(int selectedBatsmanPosition) {

        arrayteam1Bowling.clear();
        BowlingStats bowlingStats = new BowlingStats();
        bowlingStats.setBowlerId(listNewBowlerId.get(selectedBatsmanPosition));
        bowlingStats.setName(listNewBowlerName.get(selectedBatsmanPosition));
        bowlingStats.setEconomy("0");
        bowlingStats.setMaiden("0");
        bowlingStats.setNumberOfOvers("0");
        bowlingStats.setRuns("0");
        bowlingStats.setWickets("0");
        bowlingStats.setExtras("0");
        bowlingStats.setTotalDotBall("0");
        bowlingStats.setTotalWideBall("0");
        bowlingStats.setTotalNoBall("0");
        arrayteam1Bowling.add(bowlingStats);
        adapterTeam1BowlingMatch.notifyDataSetChanged();
        bowlerId = bowlingStats.getBowlerId();
        selectStriker(true);
    }

    private void addNewBatsman(int selectedBatsmanPosition) {

        BattingStats battingStats = new BattingStats();
        battingStats.setBatsmanId(listNewBatsmanId.get(selectedBatsmanPosition));
        battingStats.setBatsmanAvatarName(listNewBatsmanName.get(selectedBatsmanPosition));
        battingStats.setStatus("");
        battingStats.setRuns("0");
        battingStats.setDotball("0");
        battingStats.setBalls("0");
        battingStats.setFours("0");
        battingStats.setSixes("0");
        battingStats.setStrikeRate("0");
        battingStats.setOutString("");
        battingStats.setOnStrike("0");
        battingStats.setPlayOrder("0");
        if (isStriker) {
            battingStats.setBattingStriker(true);
            arrayteam1Batting.add(0, battingStats);
        } else {
            arrayteam1Batting.get(0).setBattingStriker(true);
            arrayteam1Batting.add(battingStats);
        }
        adapterTeam1BattingMatch.notifyDataSetChanged();
        listBatsmanId.add(battingStats.getBatsmanId());
        listBatsman.add(battingStats.getBatsmanAvatarName());
        selectStriker(false);
    }

    private void switchStriker() {
        for (int i = 0; i < arrayteam1Batting.size(); i++) {
            if (arrayteam1Batting.get(i).isBattingStriker()) {
                arrayteam1Batting.get(i).setBattingStriker(false);
                nonStrikerBatsmanId = arrayteam1Batting.get(i).getBatsmanId();
            } else {
                arrayteam1Batting.get(i).setBattingStriker(true);
                strikerBatsmanId = arrayteam1Batting.get(i).getBatsmanId();
            }
        }
        adapterTeam1BattingMatch.notifyDataSetChanged();
    }


    private void selectStriker(boolean isBowler) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // inflate the layout dialog_layout.xml and set it as contentView
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.select_striker_dialog, null, false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout cross_img_rel = (RelativeLayout) view.findViewById(R.id.cross_img_rel);
            TextView name_requirement_txt = (TextView) view.findViewById(R.id.name_requirement_txt);
            final TextView text_name1 = (TextView) view.findViewById(R.id.text_name1);
            final TextView text_name2 = (TextView) view.findViewById(R.id.text_name2);
            cross_img_rel.setVisibility(View.GONE);

            if (listBatsman.size() > 2) {
                text_name1.setText(listBatsman.get(1));
                text_name2.setText(listBatsman.get(2));
            }

            if (isBowler) {
                for (int i = 0; i < arrayteam1Batting.size(); i++) {
                    if (arrayteam1Batting.get(i).isBattingStriker()) {
                        int id = listBatsmanId.indexOf(arrayteam1Batting.get(i).getBatsmanId());
                        if (id == 1) {
                            text_name1.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                            strikerBatsmanId = listBatsmanId.get(1);
                        } else {
                            text_name2.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                            strikerBatsmanId = listBatsmanId.get(2);
                        }
                    }
                }
            } else {
                if (isStriker) {
                    text_name2.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    strikerBatsmanId = listBatsmanId.get(2);
                } else {
                    text_name1.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    strikerBatsmanId = listBatsmanId.get(1);
                }
            }

            text_name1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text_name1.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    text_name2.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_color));
                    strikerBatsmanId = listBatsmanId.get(1);
                    nonStrikerBatsmanId = listBatsmanId.get(2);
                }
            });
            text_name2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text_name2.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    text_name1.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_color));
                    strikerBatsmanId = listBatsmanId.get(2);
                    nonStrikerBatsmanId = listBatsmanId.get(1);
                }
            });

            Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < arrayteam1Batting.size(); i++) {
                        if (arrayteam1Batting.get(i).getBatsmanId().equals(strikerBatsmanId)) {
                            arrayteam1Batting.get(i).setBattingStriker(true);
                            strikerBatsmanId = arrayteam1Batting.get(i).getBatsmanId();
                        } else {
                            arrayteam1Batting.get(i).setBattingStriker(false);
                            nonStrikerBatsmanId = arrayteam1Batting.get(i).getBatsmanId();
                        }
                        dialog.dismiss();
                        adapterTeam1BattingMatch.notifyDataSetChanged();
                    }
                }
            });

            cross_img_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            Log.e(TAG, " Exception error : " + e);
        }
    }


    private void setlistener() {

        textMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file
                dialog.setContentView(R.layout.moreoptiondialog);
                // Set dialog title
                dialog.show();
                TextView text_title = (TextView) dialog.findViewById(R.id.text_title);
                text_title.setText("SELECT YOUR OPTION");
                Button btn_switchstrike = (Button) dialog.findViewById(R.id.btn_switchstrike);
                btn_switchstrike.setText(getContext().getString(R.string.switchstriker));

                btn_switchstrike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switchStriker();
                        dialog.dismiss();
                    }
                });
                Button btn_drinkbreak = (Button) dialog.findViewById(R.id.btn_drinkbreak);
                btn_drinkbreak.setText(getContext().getString(R.string.drinkbreak));
                btn_drinkbreak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkBreak(AppConstant.START);
                        dialog.dismiss();
                    }
                });
                Button btn_endinning = (Button) dialog.findViewById(R.id.btn_endinning);
                btn_endinning.setText(getContext().getString(R.string.endenning));
                btn_endinning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file
                dialog.setContentView(R.layout.moreoptiondialog);
                // Set dialog title
                dialog.show();
                TextView text_title = (TextView) dialog.findViewById(R.id.text_title);
                text_title.setText("SELECT RUNS");
                Button btn_switchstrike = (Button) dialog.findViewById(R.id.btn_switchstrike);
                btn_switchstrike.setText(getContext().getString(R.string.five));
                btn_switchstrike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runScored = 5;
                        dialog.dismiss();
                    }
                });
                Button btn_drinkbreak = (Button) dialog.findViewById(R.id.btn_drinkbreak);
                btn_drinkbreak.setText(getContext().getString(R.string.seven));
                btn_drinkbreak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runScored = 7;
                        dialog.dismiss();
                    }
                });
                Button btn_endinning = (Button) dialog.findViewById(R.id.btn_endinning);
                btn_endinning.setText(getContext().getString(R.string.eight));
                btn_endinning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runScored = 8;
                        dialog.dismiss();
                    }
                });
            }
        });
        spinnerouttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                manageOutTypeSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        checkbox_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeOutTypeArray();
                if (isChecked) {
                    lin_out_action.setVisibility(View.VISIBLE);
                    spinnerouttype.setVisibility(View.VISIBLE);
                    spinneroutbyfieldername.setVisibility(View.GONE);
                } else {
                    lin_out_action.setVisibility(View.GONE);
                    lin_out_batsanspinner.setVisibility(View.GONE);
                }
            }


        });
        checkbox_no_ball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (checkbox_wide_ball.isChecked()) {
                        checkbox_wide_ball.setChecked(false);
                    }
                }
                changeOutTypeArray();
            }
        });
        checkbox_wide_ball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (checkbox_no_ball.isChecked()) {
                        checkbox_no_ball.setChecked(false);
                    }
                    if (checkbox_leg_bye.isChecked()) {
                        checkbox_leg_bye.setChecked(false);
                    }
                    if (checkbox_bye.isChecked()) {
                        checkbox_bye.setChecked(false);
                    }
                }
                changeOutTypeArray();

            }
        });
        checkbox_bye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeOutTypeArray();
                if (isChecked) {
                    if (checkbox_leg_bye.isChecked()) {
                        checkbox_leg_bye.setChecked(false);
                    }
                    if (checkbox_wide_ball.isChecked()) {
                        checkbox_wide_ball.setChecked(false);
                    }
                }
            }
        });
        checkbox_leg_bye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                changeOutTypeArray();
                if (isChecked) {
                    if (checkbox_bye.isChecked()) {
                        checkbox_bye.setChecked(false);
                    }
                    if (checkbox_wide_ball.isChecked()) {
                        checkbox_wide_ball.setChecked(false);
                    }
                }
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
                /*setTeam1Data(data, team1Squad, team2Squad);*/
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
                //setTeam2Data(data);

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

    private void manageTextClick(boolean isEnable) {
        if (!isEnable) {
            text0.setEnabled(false);
            text1.setEnabled(false);
            text2.setEnabled(false);
            text3.setEnabled(false);
            text4.setEnabled(false);
            text5.setEnabled(false);
            text6.setEnabled(false);
            textUndo.setEnabled(false);
            text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            textUndo.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
        } else {
            text0.setEnabled(true);
            text1.setEnabled(true);
            text2.setEnabled(true);
            text3.setEnabled(true);
            text4.setEnabled(true);
            text5.setEnabled(true);
            text6.setEnabled(true);
            textUndo.setEnabled(true);
            text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            text6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
            textUndo.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        }
        if (checkbox_bye.isChecked() || checkbox_leg_bye.isChecked()) {
            text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text0.setEnabled(false);
        }
    }

    private void clearTextSelection() {
        text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        text6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        textUndo.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_selected_bg));
        if (checkbox_bye.isChecked() || checkbox_leg_bye.isChecked()) {
            text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.button_unselected_bg));
            text0.setEnabled(false);
        }
    }

    private void changeOutTypeArray() {
        lisOutType.clear();
        if (checkbox_out.isChecked()) {
            if (checkbox_bye.isChecked() || checkbox_leg_bye.isChecked()) {
                lisOutType.add(AppConstant.SELECT_OUT_TYPE);
                lisOutType.add(AppConstant.RUNOUT);
                lisOutType.add(AppConstant.OBSTRUCTION_TO_FIELD);
            } else if (checkbox_wide_ball.isChecked()) {
                lisOutType.add(AppConstant.SELECT_OUT_TYPE);
                lisOutType.add(AppConstant.RUNOUT);
                lisOutType.add(AppConstant.STUMPED);
                lisOutType.add(AppConstant.HITWICKET);
                lisOutType.add(AppConstant.OBSTRUCTION_TO_FIELD);

            } else if (checkbox_no_ball.isChecked()) {
                lisOutType.add(AppConstant.SELECT_OUT_TYPE);
                lisOutType.add(AppConstant.RUNOUT);
                lisOutType.add(AppConstant.HIT_BALL_TWICE);
                lisOutType.add(AppConstant.HANDLED_BALL);
                lisOutType.add(AppConstant.OBSTRUCTION_TO_FIELD);
            } else {
                lisOutType.add(AppConstant.SELECT_OUT_TYPE);
                lisOutType.add(AppConstant.BOWLED);
                lisOutType.add(AppConstant.CATCH_OUT);
                lisOutType.add(AppConstant.CAUGHT_BEHIND);
                lisOutType.add(AppConstant.STUMPED);
                lisOutType.add(AppConstant.LBW);
                lisOutType.add(AppConstant.RUNOUT);
                lisOutType.add(AppConstant.HITWICKET);
                lisOutType.add(AppConstant.RETIRED_HURT);
                lisOutType.add(AppConstant.HIT_BALL_TWICE);
                lisOutType.add(AppConstant.HANDLED_BALL);
                lisOutType.add(AppConstant.OBSTRUCTION_TO_FIELD);
                lisOutType.add(AppConstant.TIME_OUT);
            }
        }
        manageTextClick(true);
        adapterouttype.notifyDataSetChanged();
    }

    private void manageOutTypeSelection() {
        String selectedType = spinnerouttype.getSelectedItem().toString();
        switch (selectedType) {
            case AppConstant.RUNOUT:
                manageTextClick(true);
                spinneroutbyfieldername.setVisibility(View.VISIBLE);
                lin_out_batsanspinner.setVisibility(View.VISIBLE);
                break;
            case AppConstant.OBSTRUCTION_TO_FIELD:
            case AppConstant.SELECT_OUT_TYPE:
                manageTextClick(true);
                spinneroutbyfieldername.setVisibility(View.GONE);
                lin_out_batsanspinner.setVisibility(View.GONE);
                break;
            case AppConstant.CATCH_OUT:
            case AppConstant.CAUGHT_BEHIND:
            case AppConstant.STUMPED:
                manageTextClick(false);
                spinneroutbyfieldername.setVisibility(View.VISIBLE);
                lin_out_batsanspinner.setVisibility(View.GONE);
                break;
            default:
                manageTextClick(false);
                spinneroutbyfieldername.setVisibility(View.GONE);
                lin_out_batsanspinner.setVisibility(View.GONE);
                break;
        }
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


    private void saveDummyBowler(String name, String teamId) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("matchId", matchId);
                jsonObject.put("teamId", teamId);
                jsonObject.put("avatarId", blankBowlerId);
                jsonObject.put("name", name);

                listNewBowlerName.add(listNewBowlerName.size() - 1, name);
                listNewBowlerId.add(listNewBowlerId.size() - 1, blankBowlerId);
                adapterSelctNewBowler.notifyDataSetChanged();
                blankBowlerId = "";
                //https://sfscoring.betasportzfever.com/drinksBreak/100/START/a019834e-8f16-11e7-9931-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_DUMMY_BOWLER;

                new CommonAsyncTaskHashmap(7, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void drinkBreak(String type) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //https://sfscoring.betasportzfever.com/drinksBreak/100/START/a019834e-8f16-11e7-9931-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.DRINK_BREAK + matchId + "/" + type + "/" + AppUtils.getAuthToken(context);
                int code = 0;
                if (type.equals(AppConstant.START))
                    code = 4;
                else
                    code = 5;

                new CommonAsyncTaskHashmap(code, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLiveScores() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLiveScoresRefresh() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbjectNoProgress(url, new JSONObject(), Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBall() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                JSONObject jsonObject = makeJsonRequest();
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_BALL;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void undoBall() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("matchId", matchId);
                jsonObject.put("inningId", inningId);
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UNDO_BALL;
                new CommonAsyncTaskHashmap(3, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSecondInning() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("matchId", matchId);
                //   http://sfscoring.sf.com/startSecondInning
                String url = JsonApiHelper.BASEURL + JsonApiHelper.STARTSECONDINNING;
                new CommonAsyncTaskHashmap(3, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JSONObject makeJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (runScored == 4)
                jsonObject.put("isFour", true);
            else
                jsonObject.put("isFour", false);

            if (runScored == 6)
                jsonObject.put("isSix", true);
            else
                jsonObject.put("isSix", false);


            jsonObject.put("isBye", false);
            jsonObject.put("runScoredOnBye", "");
            jsonObject.put("isLegBye", false);
            jsonObject.put("runScoredOnLegBye", "");
            jsonObject.put("isNoBall", false);
            jsonObject.put("runScoredOnNoBall", "");
            jsonObject.put("isWideBall", false);
            jsonObject.put("runScoredOnWideBall", "");
            jsonObject.put("isNoBall", false);
            jsonObject.put("runScoredOnNoBall", "");
            jsonObject.put("isWicket", false);
            jsonObject.put("wicketType", "");
            jsonObject.put("stumpedById", "");
            jsonObject.put("caughtById", "");
            jsonObject.put("outBatsmanId", "");
            jsonObject.put("runOutById", "");

            if (!checkbox_leg_bye.isChecked() && !checkbox_bye.isChecked() && !checkbox_wide_ball.isChecked() &&
                    !checkbox_no_ball.isChecked() && !checkbox_out.isChecked()) {
                jsonObject.put("runScored", runScored);
            } else {
                jsonObject.put("runScored", 0);

                if (checkbox_bye.isChecked() || checkbox_leg_bye.isChecked()) {
                    if (checkbox_bye.isChecked()) {
                        jsonObject.put("isBye", true);
                        jsonObject.put("runScoredOnBye", runScored);
                    }
                    if (checkbox_leg_bye.isChecked()) {
                        jsonObject.put("isLegBye", true);
                        jsonObject.put("runScoredOnLegBye", runScored);
                    }
                    if (checkbox_no_ball.isChecked()) {
                        jsonObject.put("isNoBall", true);
                        jsonObject.put("runScoredOnNoBall", "");
                    }
                } else {
                    if (checkbox_no_ball.isChecked()) {
                        jsonObject.put("isNoBall", true);
                        jsonObject.put("runScoredOnNoBall", runScored);
                    }
                    if (checkbox_wide_ball.isChecked()) {
                        jsonObject.put("isWideBall", true);
                        jsonObject.put("runScoredOnWideBall", "0");
                    }
                }

                if (checkbox_out.isChecked()) {
                    jsonObject.put("isWicket", true);
                    jsonObject.put("wicketType", spinnerouttype.getSelectedItem().toString());

                    if (spinnerouttype.getSelectedItem().toString().equals(AppConstant.STUMPED)) {
                        if (spinneroutbyfieldername.getSelectedItemPosition() != 0)
                            jsonObject.put("stumpedById", listFielderId.get(spinneroutbyfieldername.getSelectedItemPosition()));
                    } else if (spinnerouttype.getSelectedItem().toString().equals(AppConstant.CAUGHT_BEHIND) || spinnerouttype.getSelectedItem().toString().equals(AppConstant.CATCH_OUT)) {
                        if (spinneroutbyfieldername.getSelectedItemPosition() != 0)
                            jsonObject.put("caughtById", listFielderId.get(spinneroutbyfieldername.getSelectedItemPosition()));
                    } else if (spinnerouttype.getSelectedItem().toString().equals(AppConstant.RUNOUT)) {
                        if (spinneroutbyfieldername.getSelectedItemPosition() != 0)
                            jsonObject.put("runOutById", listFielderId.get(spinneroutbyfieldername.getSelectedItemPosition()));
                    }
                    if (spinnerouttype.getSelectedItem().toString().equals(AppConstant.RUNOUT) || spinnerouttype.getSelectedItem().toString().equals(AppConstant.OBSTRUCTION_TO_FIELD)) {
                        jsonObject.put("outBatsmanId", listBatsmanId.get(spinneroutplayername.getSelectedItemPosition()));
                    } else {
                        jsonObject.put("outBatsmanId", strikerBatsmanId);
                    }
                }
            }
            jsonObject.put("matchId", matchId);
            jsonObject.put("batsmanId", strikerBatsmanId);
            jsonObject.put("NonStrikerbatsmanId", nonStrikerBatsmanId);
            jsonObject.put("batsmanScorecardId", null);
            jsonObject.put("bowlerId", bowlerId);
            jsonObject.put("inningId", inningId);
            jsonObject.put("overId", currentOverId);
            jsonObject.put("comments", "");
            jsonObject.put("isDeclared", false);
            jsonObject.put("by", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    data = jObject.getJSONObject("data");
                    JSONObject match = data.getJSONObject("match");
                    team1Id = match.getString("team1Id");
                    team2Id = match.getString("team2Id");
                    JSONArray team1Squad = data.getJSONArray("team1Squad");
                    JSONArray team2Squad = data.getJSONArray("team2Squad");
                    JSONArray innings = data.getJSONArray("innings");
                    numberOfPlayers = match.getString("numberOfPlayers");
                    numberOfOvers = match.getString("numberOfOvers");
                    isTeam1ScoringOnSf = match.getString("isTeam1ScoringOnSf");
                    isTeam2ScoringOnSf = match.getString("isTeam2ScoringOnSf");
                    checkInning(innings, team1Squad, team2Squad);
                    // Fragment_Scoring_ScorecardLive_match.getInstance().refreshScorecard(data.toString());

                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    getLiveScoresRefresh();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 3) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    if (isTeam2ScoringOnSf.equals("0") || isTeam1ScoringOnSf.equals("0")) {
                        saveInningScoreDialog();
                    } else {
                        getLiveScoresRefresh();
                    }
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 4) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    AppUtils.customAlertDialogWithoutTitle(context, getString(R.string.drink_break), "RESUME", new ChoiceDialogClickListener() {
                        @Override
                        public void onClickOfPositive() {
                            drinkBreak(AppConstant.END);
                        }

                        @Override
                        public void onClickOfNegative() {

                        }
                    });
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 8) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    context.onBackPressed();
                    Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                    Bundle b = new Bundle();
                    b.putString("eventId", eventId);
                    fragmentupcomingdetals.setArguments(b);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkInning(JSONArray innings, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            if (innings.length() == 1) {
                if (innings.getJSONObject(0).getString("playing").equals("1")) {
                    setTeam1Data(innings.getJSONObject(0), team1Squad, team2Squad);
                } else {
                    if (IsScorerForTeam2.equals(AppConstant.YES)) {
                        AppUtils.customAlertDialogWithoutTitle(context, getString(R.string.start_second_inning), "Start Second Inning", new ChoiceDialogClickListener() {
                            @Override
                            public void onClickOfPositive() {
                                startSecondInning();
                            }

                            @Override
                            public void onClickOfNegative() {

                            }
                        });

                    } else {
                        AppUtils.customAlertDialogWithoutTitle(context, getString(R.string.cannot_start_inning), "OK", new ChoiceDialogClickListener() {
                            @Override
                            public void onClickOfPositive() {
                                context.onBackPressed();
                            }

                            @Override
                            public void onClickOfNegative() {

                            }
                        });
                    }
                }
            } else {
                boolean IsInninEnd = true;
                for (int i = 0; i < innings.length(); i++) {
                    JSONObject jsonObject = innings.getJSONObject(i);
                    if (jsonObject.getString("playing").equals("1")) {
                        IsInninEnd = false;
                        setTeam1Data(innings.getJSONObject(i), team1Squad, team2Squad);
                    }
                }
                if (IsInninEnd) {
                    context.onBackPressed();
                    Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                    Bundle b = new Bundle();
                    b.putString("eventId", eventId);
                    fragmentupcomingdetals.setArguments(b);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text0:
                clearTextSelection();
                text0.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 0;
                break;
            case R.id.text1:
                clearTextSelection();
                text1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 1;
                break;
            case R.id.text2:
                clearTextSelection();
                text2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 2;
                break;
            case R.id.text3:
                clearTextSelection();
                text3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 3;
                break;
            case R.id.text4:
                clearTextSelection();
                text4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 4;
                break;
            case R.id.text6:
                clearTextSelection();
                text6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 6;
                break;
            case R.id.textUndo:
                clearTextSelection();
                undoBall();
                break;
            case R.id.textOk:
                if (isValidate()) {
                    saveBall();
                }
                break;

        }
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (!checkbox_leg_bye.isChecked() && !checkbox_bye.isChecked() && !checkbox_wide_ball.isChecked() &&
                !checkbox_no_ball.isChecked() && !checkbox_out.isChecked() && runScored == -1) {
            Toast.makeText(context, R.string.please_select_run, Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            if (checkbox_bye.isChecked() && runScored == -1) {
                Toast.makeText(context, R.string.please_select_run_on_bye, Toast.LENGTH_SHORT).show();
                isValid = false;
            } else if (checkbox_leg_bye.isChecked() && runScored == -1) {
                Toast.makeText(context, R.string.please_select_run_on_legbye, Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                if (checkbox_out.isChecked()) {
                    if (spinnerouttype.getSelectedItemPosition() == 0) {
                        Toast.makeText(context, R.string.please_select_out_type, Toast.LENGTH_SHORT).show();
                        isValid = false;
                    } else if (spinneroutbyfieldername.getVisibility() == View.VISIBLE &&
                            spinneroutbyfieldername.getSelectedItemPosition() == 0) {
                        Toast.makeText(context, R.string.please_select_fielder, Toast.LENGTH_SHORT).show();
                        isValid = false;
                    } else if (lin_out_batsanspinner.getVisibility() == View.VISIBLE &&
                            spinneroutplayername.getSelectedItemPosition() == 0) {
                        Toast.makeText(context, R.string.please_select_batsman, Toast.LENGTH_SHORT).show();
                        isValid = false;
                    }
                }

            }
        }
        return isValid;
    }
}


