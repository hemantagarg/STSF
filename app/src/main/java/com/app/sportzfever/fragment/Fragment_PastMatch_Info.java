package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;

import org.json.JSONObject;


public class Fragment_PastMatch_Info extends BaseFragment implements OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    private TextView text_toss, text_match_status, text_match_type, text_match_overs, text_venue, textscoreteam1, textscoreteam2;

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

        View view_about = inflater.inflate(R.layout.fragment_match_detail, container, false);
        context = getActivity();
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
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                avtarid = bundle.getString("eventId");
                String maindata = bundle.getString("data");

                JSONObject data = new JSONObject(maindata);
                JSONObject match = data.getJSONObject("match");
                text_match_type.setText("Match Type : " + match.getString("matchType"));
                text_match_overs.setText("Max Overs : " + match.getString("numberOfOvers"));
                text_venue.setText("Venue : " + match.getString("location"));
                if (match.getString("wonString").equalsIgnoreCase("")) {
                    text_match_status.setText("Match Status : " + match.getString("matchScheduleString"));
                } else {
                    text_match_status.setText("Match Status : " + match.getString("wonString"));
                }
                text_toss.setText("Toss : " + match.getString("tossString"));
                textscoreteam1.setText("Scorer for " + match.getString("team1Name") + " : " + match.getString("team1Scorer"));
                textscoreteam2.setText("Scorer for " + match.getString("team2Name") + " : " + match.getString("team2Scorer"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_toss = (TextView) view.findViewById(R.id.text_toss);
        text_match_status = (TextView) view.findViewById(R.id.text_match_status);
        text_match_type = (TextView) view.findViewById(R.id.text_match_type);
        text_match_overs = (TextView) view.findViewById(R.id.text_match_overs);
        text_venue = (TextView) view.findViewById(R.id.text_venue);
        textscoreteam1 = (TextView) view.findViewById(R.id.textscoreteam1);
        textscoreteam2 = (TextView) view.findViewById(R.id.textscoreteam2);

        getBundle();
        setlistener();

    }

    private void setlistener() {

    }

    @Override
    public void onItemClickListener(int position, int flag) {


    }

}


