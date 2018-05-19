package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterUpcomingTeamoneMatch;
import com.app.sportzfever.adapter.AdapterUpcomingTeamtwoMatch;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUpcomingTeamName;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.SportzDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_Match_TeamDetail extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_teama, list_teamb;
    private Bundle b;
    private Activity context;
    private AdapterUpcomingTeamoneMatch adapterUpcomingTeamoneMatch;
    private AdapterUpcomingTeamtwoMatch adapterUpcomingTeamtwoMatch;

    private ArrayList<ModelUpcomingTeamName> arrayteama, arrayListBowling;
    private ConnectionDetector cd;
    private LinearLayoutManager layoutManager;
    private Button btn_teama, btn_teamb;
    private TextView text_nodata;

    public static Fragment_Match_TeamDetail fragment_teamJoin_request;
    private final String TAG = Fragment_Match_TeamDetail.class.getSimpleName();
    private String eventId = "";
    private SportzDatabase db;

    public static Fragment_Match_TeamDetail getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_Match_TeamDetail();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_past_match_info, container, false);
        context = getActivity();
        arrayteama = new ArrayList<>();
        arrayListBowling = new ArrayList<>();
        b = getArguments();
        setDatabase();
        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
    }

    private void setDatabase() {
        db = null;
        try {
            db = new SportzDatabase(context);
            db.open();

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            db.close();
        }
    }


    private void getBundle() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                eventId = bundle.getString("eventId");
                db.open();
                String str = db.getMatchStatisticsDetails(Integer.parseInt(eventId));

                JSONObject data1 = new JSONObject(str);
                JSONObject data = data1.getJSONObject("data");

                JSONObject team1 = data.getJSONObject("team1");
                JSONObject team2 = data.getJSONObject("team2");

                btn_teamb.setText(team2.getString("name"));
                btn_teama.setText(team1.getString("name"));
                arrayteama.clear();
                JSONArray team1squasd = data.getJSONArray("team1Squad");
                for (int i = 0; i < team1squasd.length(); i++) {
                    JSONObject jsonObject = team1squasd.getJSONObject(i);
                    ModelUpcomingTeamName modelteama = new ModelUpcomingTeamName();
                    if (jsonObject.has("playerRole")) {
                        modelteama.setPlayerRole(jsonObject.getString("playerRole"));
                    }
                    modelteama.setName(jsonObject.getString("name"));
                    modelteama.setProfilePicture(jsonObject.getString("profilePicture"));
                    modelteama.setPlayerAvatarId(jsonObject.getString("playerAvatarId"));

                    modelteama.setRowType(1);

                    if (!jsonObject.getString("name").equals("")) {
                        arrayteama.add(modelteama);
                    }

                }
                adapterUpcomingTeamoneMatch = new AdapterUpcomingTeamoneMatch(getActivity(), this, arrayteama);
                list_teama.setAdapter(adapterUpcomingTeamoneMatch);


                if (arrayteama.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText(btn_teama.getText().toString() + " lineup is not yet published.");
                }

                JSONArray team2squasd = data.getJSONArray("team2Squad");

                arrayListBowling.clear();

                for (int i = 0; i < team2squasd.length(); i++) {
                    JSONObject jsonObject1 = team2squasd.getJSONObject(i);
                    ModelUpcomingTeamName modelteamb = new ModelUpcomingTeamName();
                    modelteamb.setPlayerRole(jsonObject1.getString("playerRole"));
                    modelteamb.setName(jsonObject1.getString("name"));
                    modelteamb.setProfilePicture(jsonObject1.getString("profilePicture"));
                    modelteamb.setPlayerAvatarId(jsonObject1.getString("playerAvatarId"));

                    modelteamb.setRowType(1);
                    if (!jsonObject1.getString("name").equals("")) {
                        arrayListBowling.add(modelteamb);
                    }
                }

                adapterUpcomingTeamtwoMatch = new AdapterUpcomingTeamtwoMatch(getActivity(), this, arrayListBowling);
                list_teamb.setAdapter(adapterUpcomingTeamtwoMatch);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_teama = (RecyclerView) view.findViewById(R.id.list_teama);
        list_teamb = (RecyclerView) view.findViewById(R.id.list_teamb);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager = new GridLayoutManager(context, 2);

        list_teama.setLayoutManager(new GridLayoutManager(context, 2));
        list_teamb.setLayoutManager(new GridLayoutManager(context, 2));
        btn_teamb = (Button) view.findViewById(R.id.btn_teamb);
        btn_teama = (Button) view.findViewById(R.id.btn_teama);

        arrayteama = new ArrayList<>();
        getBundle();
        setlistener();

        //  getServicelistRefresh();
    }

    private void setlistener() {
        btn_teama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_teama.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_teamb.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_teama.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_teamb.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                list_teamb.setVisibility(View.GONE);
                if (arrayteama.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText(btn_teama.getText().toString() + " lineup is not yet published.");
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
                    text_nodata.setText(btn_teamb.getText().toString() + " lineup is not yet published.");
                }
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {

        if (flag == 1) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayteama.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);

        } else if (flag == 2) {
            FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayListBowling.get(position).getPlayerAvatarId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);

        }
    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + eventId + "/" + AppUtils.getAuthToken(context);
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

                    arrayteama.clear();
                    JSONArray team1squasd = data.getJSONArray("team1Squad");
                    for (int i = 0; i < team1squasd.length(); i++) {
                        JSONObject jsonObject = team1squasd.getJSONObject(i);
                        ModelUpcomingTeamName modelteama = new ModelUpcomingTeamName();
                        modelteama.setPlayerRole(jsonObject.getString("playerRole"));
                        modelteama.setName(jsonObject.getString("name"));
                        modelteama.setProfilePicture(jsonObject.getString("profilePicture"));
                        modelteama.setPlayerAvatarId(jsonObject.getString("playerAvatarId"));
                        modelteama.setPlayerTeamId(jsonObject.getString("playerTeamId"));
                        modelteama.setPlaySquadId(jsonObject.getString("playSquadId"));

                        modelteama.setRowType(1);
                        arrayteama.add(modelteama);

                    }
                    adapterUpcomingTeamoneMatch = new AdapterUpcomingTeamoneMatch(getActivity(), this, arrayteama);
                    list_teama.setAdapter(adapterUpcomingTeamoneMatch);


                    if (arrayteama.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText(btn_teama.getText().toString() + " lineup is not yet published.");
                    }

                    JSONArray team2squasd = data.getJSONArray("team2Squad");

                    arrayListBowling.clear();

                    for (int i = 0; i < team2squasd.length(); i++) {
                        JSONObject jsonObject1 = team2squasd.getJSONObject(i);
                        ModelUpcomingTeamName modelteamb = new ModelUpcomingTeamName();
                        modelteamb.setPlayerRole(jsonObject1.getString("playerRole"));
                        modelteamb.setName(jsonObject1.getString("name"));
                        modelteamb.setProfilePicture(jsonObject1.getString("profilePicture"));
                        modelteamb.setPlayerAvatarId(jsonObject1.getString("playerAvatarId"));
                        modelteamb.setPlayerTeamId(jsonObject1.getString("playerTeamId"));
                        modelteamb.setPlaySquadId(jsonObject1.getString("playSquadId"));

                        modelteamb.setRowType(1);
                        arrayListBowling.add(modelteamb);
                    }

                    adapterUpcomingTeamtwoMatch = new AdapterUpcomingTeamtwoMatch(getActivity(), this, arrayListBowling);
                    list_teamb.setAdapter(adapterUpcomingTeamtwoMatch);
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


