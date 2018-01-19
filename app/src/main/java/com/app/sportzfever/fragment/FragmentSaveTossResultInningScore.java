package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentSaveTossResultInningScore extends BaseFragment implements ApiResponse {

    private Bundle b;
    private Activity context;
    private JSONObject data;
    public static FragmentSaveTossResultInningScore fragment_teamJoin_request;
    private final String TAG = FragmentSaveTossResultInningScore.class.getSimpleName();
    private String matchId = "";
    private Spinner spinnerWinningTeam, spinnerSelectionType;
    private Button btnSubmit;
    View view_about;
    private TextView text_title;
    private String team2Id = "", eventId = "", team1Id = "", team1Name = "", team2Name = "";
    private ArrayAdapter<String> adapterWinningTeam, adapterSelectionType;
    private ArrayList<String> listTeam = new ArrayList<>();
    private ArrayList<String> listTeamId = new ArrayList<>();
    private ArrayList<String> listSelectonType = new ArrayList<>();
    private String isScorerForTeam1 = "", isScorerForTeam2 = "", overs = "", playersCount = "";
    private EditText runScoredOne, wicketsOne, playedOversOne, runScoredTwo, wicketsTwo, playedOversTwo;

    public static FragmentSaveTossResultInningScore getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentSaveTossResultInningScore();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_save_toss_inningscore, container, false);
        context = getActivity();
        fragment_teamJoin_request = FragmentSaveTossResultInningScore.this;
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
            team1Id = b.getString("team1Id");
            team2Id = b.getString("team2Id");
            eventId = b.getString("eventId");
            matchId = b.getString("matchId");
            team1Name = b.getString("team1Name");
            playersCount = b.getString("playersCount");
            overs = b.getString("overs");
            team2Name = b.getString("team2Name");
            isScorerForTeam1 = b.getString("isScorerForTeam1");
            isScorerForTeam2 = b.getString("isScorerForTeam2");
            text_title.setText(team1Name + " vs " + team2Name);
            setData();
        }
    }

    private void setData() {
        listTeam.add("Select Team");
        listTeamId.add("-1");

        listTeam.add(team1Name);
        listTeam.add(team2Name);
        listTeamId.add(team1Id);
        listTeamId.add(team2Id);

        listSelectonType.add("Choose BATTING/BOWLING");
        listSelectonType.add(AppConstant.BATTING);
        listSelectonType.add(AppConstant.BOWLING);

        adapterWinningTeam = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listTeam);
        spinnerWinningTeam.setAdapter(adapterWinningTeam);
        adapterSelectionType = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listSelectonType);
        spinnerSelectionType.setAdapter(adapterSelectionType);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getBundle();
        setlistener();
    }

    private void init(View view) {
        spinnerWinningTeam = (Spinner) view.findViewById(R.id.spinnerWinningTeam);
        spinnerSelectionType = (Spinner) view.findViewById(R.id.spinnerSelectionType);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        text_title = (TextView) view.findViewById(R.id.text_title);
        runScoredOne = (EditText) view.findViewById(R.id.runScoredOne);
        wicketsOne = (EditText) view.findViewById(R.id.wicketsOne);
        playedOversOne = (EditText) view.findViewById(R.id.playedOversOne);
        runScoredTwo = (EditText) view.findViewById(R.id.runScoredTwo);
        wicketsTwo = (EditText) view.findViewById(R.id.wicketsTwo);
        playedOversTwo = (EditText) view.findViewById(R.id.playedOversTwo);

    }

    private void setlistener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    saveResult();
                }
            }
        });

    }

    private boolean isValidate() {
        boolean isValid = true;
        if (spinnerWinningTeam.getSelectedItemPosition() == 0) {
            isValid = false;
            Toast.makeText(context, "Please select toss winner", Toast.LENGTH_SHORT).show();
        } else if (spinnerSelectionType.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please select toss selection.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (runScoredOne.getText().toString().equalsIgnoreCase("") || runScoredTwo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Run Scored field cannot be empty", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (wicketsTwo.getText().toString().equalsIgnoreCase("") || wicketsOne.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Number of wickets are less then or equal to " + playersCount + ".", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (playedOversOne.getText().toString().equalsIgnoreCase("") || playedOversTwo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Number of overs are less then or equal to " + overs + ".", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (Integer.parseInt(wicketsTwo.getText().toString()) > Integer.parseInt(playersCount) ||
                Integer.parseInt(wicketsOne.getText().toString()) > Integer.parseInt(playersCount)) {
            Toast.makeText(context, "Number of wickets are less then or equal to " + playersCount + ".", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (Float.parseFloat(playedOversOne.getText().toString()) > Float.parseFloat(overs) ||
                Float.parseFloat(playedOversTwo.getText().toString()) > Float.parseFloat(overs)) {
            Toast.makeText(context, "Number of overs are less then or equal to " + overs + ".", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }


    private void saveResult() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.sf.com/saveScoreForMatches

                JSONObject match = makeJsonObject();
                //http://sfscoring.sf.com/saveScoreForMatches
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_SCORERS_FOR_MATCH;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, match, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject makeJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject match = new JSONObject();
            match.put("tossResultId", listTeamId.get(spinnerWinningTeam.getSelectedItemPosition()));
            match.put("action", "");
            match.put("tossSelection", spinnerSelectionType.getSelectedItem().toString());
            match.put("matchId", matchId);

            JSONArray innings = new JSONArray();

            JSONObject inning1 = new JSONObject();
            inning1.put("inningNumber", "1");
            inning1.put("totalRunsScored", runScoredOne.getText().toString());
            inning1.put("wicketsInOver", wicketsOne.getText().toString());
            inning1.put("playedOvers", playedOversOne.getText().toString());
            inning1.put("totalOvers", overs);


            JSONObject inning2 = new JSONObject();
            inning2.put("inningNumber", "2");
            inning2.put("totalRunsScored", runScoredTwo.getText().toString());
            inning2.put("wicketsInOver", wicketsTwo.getText().toString());
            inning2.put("playedOvers", playedOversTwo.getText().toString());
            inning2.put("totalOvers", overs);

            String team1 = "", team2 = "";
            if (listTeamId.get(spinnerWinningTeam.getSelectedItemPosition()).equals(team1Id)) {
                team1 = team1Id;
                team2 = team2Id;
            } else {
                team1 = team2Id;
                team2 = team1Id;
            }
            if (spinnerSelectionType.getSelectedItem().toString().equals(AppConstant.BATTING)) {
                inning1.put("battingTeamId", team1);
                inning1.put("bowlingTeamId", team2);
                inning2.put("battingTeamId", team2);
                inning2.put("bowlingTeamId", team1);
            } else {
                inning1.put("battingTeamId", team2);
                inning1.put("bowlingTeamId", team1);
                inning2.put("battingTeamId", team1);
                inning2.put("bowlingTeamId", team2);

            }

            innings.put(inning1);
            innings.put(inning2);
            match.put("innings", innings);
            jsonObject.put("match", match);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    context.onBackPressed();
                    FragmentUpcomingMatchDetails fragmentupcomingdetals = new FragmentUpcomingMatchDetails();
                    Bundle b = new Bundle();
                    b.putString("eventId", eventId);
                    fragmentupcomingdetals.setArguments(b);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
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


