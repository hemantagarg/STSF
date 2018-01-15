package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentSaveTossResult extends BaseFragment implements ApiResponse {

    private Bundle b;
    private Activity context;
    private JSONObject data;
    public static FragmentSaveTossResult fragment_teamJoin_request;
    private final String TAG = FragmentSaveTossResult.class.getSimpleName();
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
    private String isScorerForTeam1 = "", isScorerForTeam2 = "";

    public static FragmentSaveTossResult getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentSaveTossResult();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_save_toss, container, false);
        context = getActivity();
        fragment_teamJoin_request = FragmentSaveTossResult.this;
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
        listSelectonType.add("BATTING");
        listSelectonType.add("BOWLING");

        adapterWinningTeam = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listTeam);
        spinnerWinningTeam.setAdapter(adapterWinningTeam);
        adapterSelectionType = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listSelectonType);
        spinnerSelectionType.setAdapter(adapterSelectionType);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerWinningTeam = (Spinner) view.findViewById(R.id.spinnerWinningTeam);
        spinnerSelectionType = (Spinner) view.findViewById(R.id.spinnerSelectionType);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        text_title = (TextView) view.findViewById(R.id.text_title);
        getBundle();
        setlistener();
    }

    private void setlistener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerWinningTeam.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Please Select winning team", Toast.LENGTH_SHORT).show();
                } else if (spinnerSelectionType.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Please Select Selection", Toast.LENGTH_SHORT).show();
                } else {
                    saveResult();
                }
            }
        });
    }


    private void saveResult() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.sf.com/saveScoreForMatches

                JSONObject match = new JSONObject();

                match.put("tossWinnerTeamId", listTeamId.get(spinnerWinningTeam.getSelectedItemPosition()));
                match.put("tossSelection", spinnerSelectionType.getSelectedItem().toString());
                match.put("matchId", matchId);

                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_TOSS;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, match, Request.Method.POST);

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
                    if (isScorerForTeam1.equalsIgnoreCase("Yes") && isScorerForTeam2.equalsIgnoreCase("Yes")) {
                        FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                        Bundle b = new Bundle();
                        b.putString("eventId", eventId);
                        fragmentSoringMatchDetails.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSoringMatchDetails, true);
                        context.onBackPressed();
                    } else if (isScorerForTeam1.equalsIgnoreCase("Yes")) {
                        if (listTeamId.get(spinnerWinningTeam.getSelectedItemPosition()).equals(team1Id) &&
                                spinnerSelectionType.getSelectedItem().toString().equalsIgnoreCase("Batting")) {
                            FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                            Bundle b = new Bundle();
                            b.putString("eventId", eventId);
                            fragmentSoringMatchDetails.setArguments(b);
                            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSoringMatchDetails, true);
                            context.onBackPressed();
                        } else {
                            Toast.makeText(context, R.string.cannot_scorer_message, Toast.LENGTH_SHORT).show();
                        }
                    } else if (isScorerForTeam2.equalsIgnoreCase("Yes")) {
                        if (listTeamId.get(spinnerWinningTeam.getSelectedItemPosition()).equals(team2Id) &&
                                spinnerSelectionType.getSelectedItem().toString().equalsIgnoreCase("Batting")) {
                            FragmentSoringMatchDetails fragmentSoringMatchDetails = new FragmentSoringMatchDetails();
                            Bundle b = new Bundle();
                            b.putString("eventId", eventId);
                            fragmentSoringMatchDetails.setArguments(b);
                            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSoringMatchDetails, true);
                            context.onBackPressed();
                        } else {
                            Toast.makeText(context, R.string.cannot_scorer_message, Toast.LENGTH_SHORT).show();
                        }
                    }
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


