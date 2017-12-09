package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
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
import com.app.sportzfever.adapter.AdapterTeamAddedPlayersLineupDirect;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSportTeamList;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentPrepareLineupDirect extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request, list_added_players;
    private Bundle b;
    private Activity context;
    private AdapterTeamAddedPlayersLineupDirect adapterTeamAddedPlayersLineup;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayList, arrayListaddedPlayers;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private View view_about;
    private Button btn_send_invite, btn_next;
    private int WkCount = 0, Batcount = 0, AKCount = 0, BowlCount = 0;
    private TextView text_nodata, textSelectAll, text_Bowl_count, text_AR_count, text_bat_count, text_Wk_count, text_selected_players;
    public static FragmentPrepareLineupDirect fragment_teamJoin_request;
    private final String TAG = FragmentPrepareLineupDirect.class.getSimpleName();
    private String teamId = "", playersCount = "";
    private ArrayList<String> listAvtarId = new ArrayList<>();
    private int totalCount = 0;
    private String eventId = "";

    public static FragmentPrepareLineupDirect getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentPrepareLineupDirect();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_prepare_lineup_direct, container, false);
        context = getActivity();
        b = getArguments();
        return view_about;
    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {

        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Prepare Lineup");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, false);

    }

    /*****************************************************************************
     * Function name - manageHeaderClick
     * Description - manage the click on the left and right image view of header
     *****************************************************************************/
    private HeaderViewClickListener manageHeaderClick() {
        return new HeaderViewClickListener() {
            @Override
            public void onClickOfHeaderLeftView() {
                AppUtils.showLog(TAG, "onClickOfHeaderLeftView");
                context.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        list_added_players = (RecyclerView) view.findViewById(R.id.list_added_players);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        textSelectAll = (TextView) view.findViewById(R.id.textSelectAll);
        text_AR_count = (TextView) view.findViewById(R.id.text_AR_count);
        text_bat_count = (TextView) view.findViewById(R.id.text_bat_count);
        text_Bowl_count = (TextView) view.findViewById(R.id.text_Bowl_count);
        text_selected_players = (TextView) view.findViewById(R.id.text_selected_players);
        text_Wk_count = (TextView) view.findViewById(R.id.text_Wk_count);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_send_invite = (Button) view.findViewById(R.id.btn_send_invite);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_request.setLayoutManager(layoutManager);
        list_added_players.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
        arrayListaddedPlayers = new ArrayList<>();
        adapterTeamAddedPlayersLineup = new AdapterTeamAddedPlayersLineupDirect(context, this, arrayListaddedPlayers);
        list_added_players.setAdapter(adapterTeamAddedPlayersLineup);
        manageHeaderView();
        getBundle();
        setlistener();
    }

    private void getBundle() {
        try {
            Bundle b = getArguments();
            teamId = b.getString("teamId");
            eventId = b.getString("eventId");
            playersCount = b.getString("playersCount");
            text_selected_players.setText("0/" + playersCount + "\nPlayers");
            String response = b.getString("jsonresponse");
            if (!response.equalsIgnoreCase("")) {
                JSONObject jObject = new JSONObject(response);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray jtaemown = jObject.getJSONArray("lineupPlayers");

                    arrayListaddedPlayers.clear();
                    for (int i = 0; i < jtaemown.length(); i++) {

                        JSONObject jo = jtaemown.getJSONObject(i);
                        modelSportTeamList = new ModelSportTeamList();

                        modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                        listAvtarId.add(jo.getString("avatarId"));
                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
                        modelSportTeamList.setTeamId(jo.getString("teamId"));
                        modelSportTeamList.setMatchId(jo.getString("matchId"));
                        modelSportTeamList.setOrder(jo.getString("order"));
                        modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                        modelSportTeamList.setIsAdded(1);
                        modelSportTeamList.setIsInPlayingBench(jo.getString("isInPlayingBench"));
                        modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("playerProfilePicture"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        if (modelSportTeamList.getSpeciality().equalsIgnoreCase(AppConstant.BATSMAN)) {
                            Batcount++;
                            text_bat_count.setText(Batcount + "");
                        } else if (modelSportTeamList.getSpeciality().equalsIgnoreCase(AppConstant.BOWLER)) {
                            BowlCount++;
                            text_Bowl_count.setText(BowlCount + "");
                        } else if (modelSportTeamList.getSpeciality().equalsIgnoreCase(AppConstant.WICKET_KEEPER)) {
                            WkCount++;
                            text_Wk_count.setText(WkCount + "");
                        } else {
                            AKCount++;
                            text_AR_count.setText(AKCount + "");
                        }
                        modelSportTeamList.setRowType(1);
                        if (modelSportTeamList.getAddedStatus().equalsIgnoreCase(AppConstant.ACCEPTED)) {
                            btn_next.setVisibility(View.VISIBLE);
                        }
                        arrayListaddedPlayers.add(modelSportTeamList);
                    }
                    totalCount = AKCount + Batcount + BowlCount + WkCount;
                    text_selected_players.setText(totalCount + "/" + playersCount + "\nPlayers");
                    adapterTeamAddedPlayersLineup.notifyDataSetChanged();
                }
            }
            getServicelistRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setlistener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = makeJsonRequest();
                FragmentMatchRoles fragmentPrepareLineup = new FragmentMatchRoles();
                Bundle bundle = new Bundle();
                bundle.putString("teamId", teamId);
                bundle.putString("eventId", eventId);
                bundle.putString("playersCount", playersCount);
                bundle.putString("jsonresponse", jsonObject.toString());
                fragmentPrepareLineup.setArguments(bundle);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);

            }
        });
    }

    private JSONObject makeJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newMatchlineUp = new JSONArray();
            for (int i = 0; i < arrayListaddedPlayers.size(); i++) {
                if (arrayListaddedPlayers.get(i).getIsAdded() == 1) {
                    JSONObject jo = new JSONObject();
                    jo.put("avatarId", arrayListaddedPlayers.get(i).getAvtarId());
                    jo.put("inviteStatus", arrayListaddedPlayers.get(i).getAddedStatus());
                    jo.put("isInBench", arrayListaddedPlayers.get(i).getIsInPlayingBench());
                    jo.put("isInPlayingSquad", arrayListaddedPlayers.get(i).getIsInPlayingSquad());
                    jo.put("isReservedPlayer", arrayListaddedPlayers.get(i).getIsReservedPlayer());
                    jo.put("order", i + 1 + "");
                    jo.put("role", arrayListaddedPlayers.get(i).getSpeciality());
                    newMatchlineUp.put(jo);
                }
            }
            jsonObject.put("matchId", eventId);
            jsonObject.put("teamId", teamId);
            jsonObject.put("isTeamScoringOnSf", "1");
            jsonObject.put("newMatchlineUp", newMatchlineUp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 2) {
            if (arrayListaddedPlayers.get(position).getIsAdded() == 1) {
                removeFromList(position);
            } else {
                addDataInList(position);
            }
        }
    }

    private void removeFromList(int position) {
        arrayListaddedPlayers.get(position).setIsAdded(0);
        if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BATSMAN)) {
            Batcount--;
            text_bat_count.setText(Batcount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BOWLER)) {
            BowlCount--;
            text_Bowl_count.setText(BowlCount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.WICKET_KEEPER)) {
            WkCount--;
            text_Wk_count.setText(WkCount + "");
        } else {
            AKCount--;
            text_AR_count.setText(AKCount + "");
        }
        totalCount = AKCount + Batcount + BowlCount + WkCount;
        text_selected_players.setText(totalCount + "/" + playersCount + "\nPlayers");
        adapterTeamAddedPlayersLineup.notifyDataSetChanged();
    }

    private void addDataInList(int position) {
        if (totalCount < Integer.parseInt(playersCount)) {
            arrayListaddedPlayers.get(position).setIsAdded(1);
            if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BATSMAN)) {
                Batcount++;
                text_bat_count.setText(Batcount + "");
            } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BOWLER)) {
                BowlCount++;
                text_Bowl_count.setText(BowlCount + "");
            } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.WICKET_KEEPER)) {
                WkCount++;
                text_Wk_count.setText(WkCount + "");
            } else {
                AKCount++;
                text_AR_count.setText(AKCount + "");
            }
            totalCount = AKCount + Batcount + BowlCount + WkCount;
            text_selected_players.setText(totalCount + "/" + playersCount + "\nPlayers");
            adapterTeamAddedPlayersLineup.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Max " + playersCount + " players can be added", Toast.LENGTH_SHORT).show();
        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + teamId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

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

                    JSONArray teamProfile = data.getJSONArray("teamProfile");
                    for (int i = 0; i < teamProfile.length(); i++) {

                        JSONObject jo = teamProfile.getJSONObject(i);

                        modelSportTeamList = new ModelSportTeamList();

                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
                        modelSportTeamList.setAvtarId(jo.getString("avatar"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
                        modelSportTeamList.setIsInPlayingSquad("1");
                        if (jo.getString("speciality").equalsIgnoreCase("")) {
                            modelSportTeamList.setSpeciality(AppConstant.ALL_ROUNDER);
                        } else {
                            modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        }
                        modelSportTeamList.setIsAdded(0);
                        modelSportTeamList.setAddedStatus(jo.getString("requestStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                        modelSportTeamList.setRowType(1);
                        if (!modelSportTeamList.getAddedStatus().equalsIgnoreCase("PENDING")) {
                            if (!listAvtarId.contains(modelSportTeamList.getAvtarId())) {
                                arrayListaddedPlayers.add(modelSportTeamList);
                            }
                        }
                    }
                    adapterTeamAddedPlayersLineup.notifyDataSetChanged();

                    if (arrayListaddedPlayers.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Roster found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Roster found");
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

