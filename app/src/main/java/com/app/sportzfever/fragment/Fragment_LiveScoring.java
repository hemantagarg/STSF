package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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


public class Fragment_LiveScoring extends BaseFragment implements ApiResponse, OnCustomItemClicListener, View.OnClickListener {

    private RecyclerView list_team1batting, list_team1bowling, list_team2batting, list_team2bowling, recycler_recent_balls;
    private Bundle b;
    private TextView text_recentball;
    private Activity context;
    private Spinner spinnerouttype, spinneroutbyfieldername, spinneroutplayername;
    private AdapterLiveScoringTeamBattingMatch adapterTeam1BattingMatch;
    private AdapterTeamBowlingMatch adapterTeam1BowlingMatch;
    private ArrayAdapter<String> adapterouttype, adapterFielderList, adapterBatsmanList;

    private ArrayList<BattingStats> arrayteam1Batting;
    private ArrayList<String> listBatsman = new ArrayList<>();
    private ArrayList<String> listBatsmanId = new ArrayList<>();
    private ArrayList<String> listFielderId = new ArrayList<>();
    private ArrayList<String> listFielderName = new ArrayList<>();
    private ArrayList<BowlingStats> arrayteam1Bowling;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata, text_team1batting, text_team1bowling, text_team2batting, text_team2bowling;
    private LinearLayout layout_team2, layout_team1, layout_team1batting, layout_team1bowling, layout_team2batting, layout_team2bowling, lin_out_action, lin_out_batsanspinner;
    public static Fragment_LiveScoring fragment_teamJoin_request;
    private final String TAG = Fragment_LiveScoring.class.getSimpleName();
    private String avtarid = "", strikerBatsmanId = "", nonStrikerBatsmanId = "", bowlerId = "", inningId = "";
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
    private ArrayList<String> lisOutType = new ArrayList<>();
    private String team1Id = "", team2Id = "", matchId = "", currentOverId = "";
    private int runScored = -1;

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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
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

        adapterouttype = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, lisOutType);
        spinnerouttype.setAdapter(adapterouttype);

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
                avtarid = bundle.getString("eventId");
                String maindata = bundle.getString("data");
                JSONObject data = new JSONObject(maindata);
                JSONObject match = data.getJSONObject("match");
                matchId = match.getString("id");
                team1Id = match.getString("team1Id");
                team2Id = match.getString("team2Id");
                JSONArray team1Squad = data.getJSONArray("team1Squad");
                JSONArray team2Squad = data.getJSONArray("team2Squad");
                JSONArray innings = data.getJSONArray("innings");
                for (int i = 0; i < innings.length(); i++) {
                    JSONObject jo = innings.getJSONObject(i);
                    if (jo.getString("playing").equals("1")) {
                        setTeam1Data(jo, team1Squad, team2Squad);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTeam1Data(JSONObject jo, JSONArray team1Squad, JSONArray team2Squad) {
        try {
            if (jo != null) {
                recentBallArrayList.clear();
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
                adapterTeam1BattingMatch = new AdapterLiveScoringTeamBattingMatch(getActivity(), this, arrayteam1Batting);
                list_team1batting.setAdapter(adapterTeam1BattingMatch);

                text_team1batting.setText(modelInnings.getBattingTeamName() + " - Batting");
                text_team1bowling.setText(modelInnings.getBowlingTeamName() + " - Bowling");

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
                Button btn_drinkbreak = (Button) dialog.findViewById(R.id.btn_drinkbreak);
                btn_drinkbreak.setText(getContext().getString(R.string.drinkbreak));

                Button btn_endinning = (Button) dialog.findViewById(R.id.btn_endinning);
                btn_endinning.setText(getContext().getString(R.string.endenning));

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
                if (isChecked) {
                    lin_out_action.setVisibility(View.VISIBLE);
                } else {
                    lin_out_action.setVisibility(View.GONE);
                    lin_out_batsanspinner.setVisibility(View.GONE);
                }
                changeOutTypeArray();
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
                if (isChecked) {
                    if (checkbox_leg_bye.isChecked()) {
                        checkbox_leg_bye.setChecked(false);
                    }
                    if (checkbox_wide_ball.isChecked()) {
                        checkbox_wide_ball.setChecked(false);
                    }
                }
                changeOutTypeArray();
            }
        });
        checkbox_leg_bye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (checkbox_bye.isChecked()) {
                        checkbox_bye.setChecked(false);
                    }
                    if (checkbox_wide_ball.isChecked()) {
                        checkbox_wide_ball.setChecked(false);
                    }
                }
                changeOutTypeArray();
            }
        });

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
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + avtarid + "/" + AppUtils.getAuthToken(context);
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
                        jsonObject.put("runScoredOnWideBall", runScored);
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
                    for (int i = 0; i < innings.length(); i++) {
                        JSONObject jo = innings.getJSONObject(i);
                        if (jo.getString("playing").equals("1")) {
                            setTeam1Data(jo, team1Squad, team2Squad);
                        }
                    }

                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            } else if (position == 2) {
                //    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                context.onBackPressed();
            } else {
                Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                runScored = 4;
                break;
            case R.id.text4:
                clearTextSelection();
                text4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 5;
                break;
            case R.id.text6:
                clearTextSelection();
                text6.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                runScored = 6;
                break;
            case R.id.textUndo:
                clearTextSelection();
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


