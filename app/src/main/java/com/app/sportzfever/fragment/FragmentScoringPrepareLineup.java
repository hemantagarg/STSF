package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.app.sportzfever.adapter.AdapterTeamLineupRoster;
import com.app.sportzfever.adapter.AdapterTeamScoringPlayersLineup;
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
public class FragmentScoringPrepareLineup extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request, list_added_players;
    private Bundle b;
    private Activity context;
    private AdapterTeamLineupRoster adapterSportTeamList;
    private AdapterTeamScoringPlayersLineup adapterTeamAddedPlayersLineup;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayList, arrayListaddedPlayers;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0, intAddedPlayers = 0;
    private View view_about;
    private Button btn_send_invite, btn_next;
    private TextView text_nodata, textSelectAll, text_selected_players, text_create_lineup;
    private ArrayList<String> listAvtarId = new ArrayList<>();

    public static FragmentScoringPrepareLineup fragment_teamJoin_request;
    private final String TAG = FragmentScoringPrepareLineup.class.getSimpleName();
    private String team2Id = "", eventId = "", team1Id = "", team1Name = "", team2Name = "";
    private JSONObject jsonLinupArray;
    private String playersCount = "";
    private String teamCheckAvailibility = "", title = "";
    private String matchId = "";

    public static FragmentScoringPrepareLineup getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentScoringPrepareLineup();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_scoring_prepare_lineup, container, false);
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
        HeaderViewManager.getInstance().setHeading(true, title);
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.refresh);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, true);

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
                getTeamLineup();
                getRoasterList();
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
        textSelectAll.setVisibility(View.GONE);
        text_selected_players = (TextView) view.findViewById(R.id.text_selected_players);
        text_create_lineup = (TextView) view.findViewById(R.id.text_create_lineup);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_send_invite = (Button) view.findViewById(R.id.btn_send_invite);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_request.setLayoutManager(layoutManager);
        list_added_players.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
        arrayListaddedPlayers = new ArrayList<>();
        adapterTeamAddedPlayersLineup = new AdapterTeamScoringPlayersLineup(context, this, arrayListaddedPlayers);
        list_added_players.setAdapter(adapterTeamAddedPlayersLineup);
        getBundle();
        manageHeaderView();
        setlistener();
    }

    private void getBundle() {
        try {
            Bundle b = getArguments();
            team1Id = b.getString("team1Id");
            team2Id = b.getString("team2Id");
            eventId = b.getString("eventId");
            matchId = b.getString("matchId");
            team1Name = b.getString("team1Name");
            team2Name = b.getString("team2Name");
            title = b.getString("title");
            playersCount = b.getString("playersCount");
            text_selected_players.setText("0/" + playersCount + "\nPlayers");
            getTeamLineup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPlayerCount() {
        text_selected_players.setText(intAddedPlayers + "/" + playersCount + "\nPlayers");
        if (intAddedPlayers < Integer.parseInt(playersCount)) {
            text_create_lineup.setText("Add player to lineup");
        } else {
            text_create_lineup.setText("Lineup Completed");
        }
    }

    private void setlistener() {
        textSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAllData();
            }
        });
        btn_send_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayListaddedPlayers.size() > 0) {
                    sentInvite();
                } else {
                    Toast.makeText(context, "Please add atleast one player", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentInvite();
                JSONObject playerAvalablity = makeAddedPlayerJsonRequest();
                FragmentScoringMatchRoles fragmentPrepareLineup = new FragmentScoringMatchRoles();
                Bundle bundle = new Bundle();
                bundle.putString("teamId", team1Id);
                bundle.putString("title", title);
                bundle.putString("eventId", eventId);
                bundle.putString("playersCount", playersCount);
                bundle.putString("teamCheckAvailibility", teamCheckAvailibility);
                bundle.putString("linepArray", jsonLinupArray.toString());
                bundle.putString("jsonresponse", playerAvalablity.toString());

                fragmentPrepareLineup.setArguments(bundle);
                fragmentPrepareLineup.setTargetFragment(FragmentScoringPrepareLineup.this, AppConstant.FRAGMENT_CODE);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);

            }
        });
    }

    private JSONObject makeAddedPlayerJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newMatchlineUp = new JSONArray();
            for (int i = 0; i < arrayListaddedPlayers.size(); i++) {
                if (arrayListaddedPlayers.get(i).getIsAdded() == 1) {
                    JSONObject jo = new JSONObject();
                    jo.put("avatarId", arrayListaddedPlayers.get(i).getAvtarId());
                    jo.put("userId", arrayListaddedPlayers.get(i).getUserId());
                    jo.put("playerName", arrayListaddedPlayers.get(i).getPlayerName());
                    jo.put("teamId", arrayListaddedPlayers.get(i).getTeamId());
                    jo.put("matchId", arrayListaddedPlayers.get(i).getMatchId());
                    jo.put("inviteStatus", AppConstant.ACCEPTED);
                    jo.put("speciality", arrayListaddedPlayers.get(i).getSpeciality());
                    jo.put("playerProfilePicture", arrayListaddedPlayers.get(i).getProfilePicture());
                    jo.put("avatarName", arrayListaddedPlayers.get(i).getAvatarName());
                    jo.put("isInPlayingBench", arrayListaddedPlayers.get(i).getIsInPlayingBench());
                    jo.put("isInPlayingSquad", arrayListaddedPlayers.get(i).getIsInPlayingSquad());
                    //  jo.put("isReservedPlayer", arrayListaddedPlayers.get(i).getIsReservedPlayer());
                    jo.put("order", i + 1 + "");
                    jo.put("role", arrayListaddedPlayers.get(i).getSpeciality());
                    newMatchlineUp.put(jo);
                }
            }
            jsonObject.put("playersAvailability", newMatchlineUp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppConstant.RESULTCODE_FINISH) {
            // getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());
            context.onBackPressed();
        }
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            addDataInList(position);
        } else if (flag == 2) {
            removeFromList(position);
        }
        checkPlayerCount();
    }

    private void removeFromList(int position) {
        ModelSportTeamList modelSportTeamList = arrayListaddedPlayers.get(position);
        arrayList.add(modelSportTeamList);
        arrayListaddedPlayers.remove(position);
        adapterTeamAddedPlayersLineup.notifyDataSetChanged();
        adapterSportTeamList.notifyDataSetChanged();
        intAddedPlayers--;
    }

    private void addDataInList(int position) {
        if (intAddedPlayers < Integer.parseInt(playersCount)) {
            ModelSportTeamList modelSportTeamList = arrayList.get(position);
            arrayListaddedPlayers.add(modelSportTeamList);
            arrayList.remove(position);
            adapterTeamAddedPlayersLineup.notifyDataSetChanged();
            adapterSportTeamList.notifyDataSetChanged();
            intAddedPlayers++;
        } else {
            Toast.makeText(context, "Team playing lineup is complete. Lets add scorer and captain.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addAllData() {
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                ModelSportTeamList modelSportTeamList = arrayList.get(i);
                arrayListaddedPlayers.add(modelSportTeamList);
            }
            arrayList.clear();
            adapterTeamAddedPlayersLineup.notifyDataSetChanged();
            adapterSportTeamList.notifyDataSetChanged();
        }
    }

    private void getRoasterList() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + team1Id + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sentInvite() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                JSONObject jsonObject = makeJsonRequest();

                //   http://sfscoring.betasportzfever.com/getMatchLineup/23/77/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.MANAGE_LINEUP_MATCH;
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

            JSONArray newMatchlineUp = new JSONArray();
            for (int i = 0; i < arrayListaddedPlayers.size(); i++) {
                JSONObject jo = new JSONObject();
                jsonObject.put("matchId", matchId);
                jo.put("avatarId", arrayListaddedPlayers.get(i).getAvtarId());
                if (arrayListaddedPlayers.get(i).getAddedStatus().equalsIgnoreCase("Invitation not sent")) {
                    jo.put("inviteStatus", AppConstant.PENDING);
                } else {
                    jo.put("inviteStatus", arrayListaddedPlayers.get(i).getAddedStatus());
                }
                jo.put("isInBench", arrayListaddedPlayers.get(i).getIsInPlayingBench());
                jo.put("isInPlayingSquad", arrayListaddedPlayers.get(i).getIsInPlayingSquad());
                jo.put("isReservedPlayer", arrayListaddedPlayers.get(i).getIsReservedPlayer());
                jo.put("order", i + 1 + "");
                jo.put("role", arrayListaddedPlayers.get(i).getSpeciality());
                newMatchlineUp.put(jo);
            }
            jsonObject.put("teamId", team1Id);
            jsonObject.put("isTeamScoringOnSf", "1");
            jsonObject.put("teamCheckAvailibility", "0");
            jsonObject.put("newMatchlineUp", newMatchlineUp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void getTeamLineup() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //   http://sfscoring.sf.com/api/matches/getLineUpForMatch/87/4
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_MATCH_LINEUP + team1Id + "/" + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, null, Request.Method.GET);

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
                    arrayList.clear();
                    for (int i = 0; i < teamProfile.length(); i++) {

                        JSONObject jo = teamProfile.getJSONObject(i);

                        modelSportTeamList = new ModelSportTeamList();

                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
                        modelSportTeamList.setAvtarId(jo.getString("avatar"));
                        modelSportTeamList.setUserId(jo.getString("userId"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setIsInPlayingBench("0");
                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        modelSportTeamList.setIsAdded(0);
                        modelSportTeamList.setRequestStatus(jo.getString("requestStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                        modelSportTeamList.setRowType(1);
                        if (!listAvtarId.contains(modelSportTeamList.getAvtarId())) {
                            arrayList.add(modelSportTeamList);
                        }
                    }
                    adapterSportTeamList = new AdapterTeamLineupRoster(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterSportTeamList);

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Roster found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Roster found");

                }

            } else if (position == 2) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    jsonLinupArray = jObject;
                    JSONArray jtaemown = jObject.getJSONArray("lineupPlayers");

                    arrayListaddedPlayers.clear();
                    for (int i = 0; i < jtaemown.length(); i++) {

                        JSONObject jo = jtaemown.getJSONObject(i);
                        modelSportTeamList = new ModelSportTeamList();

                        modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                        listAvtarId.add(jo.getString("avatarId"));
                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setUserId(jo.getString("userId"));
                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        modelSportTeamList.setTeamId(jo.getString("teamId"));
                        modelSportTeamList.setMatchId(jo.getString("matchId"));
                        modelSportTeamList.setOrder(jo.getString("order"));
                        modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                        modelSportTeamList.setIsAdded(1);
                        modelSportTeamList.setIsInPlayingBench(jo.getString("isInPlayingBench"));
                        modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("playerProfilePicture"));
                        modelSportTeamList.setRowType(1);
                        modelSportTeamList.setIsInPlayingSquad("1");
                        arrayListaddedPlayers.add(modelSportTeamList);
                        intAddedPlayers++;
                    }
                    getRoasterList();
                    text_selected_players.setText(intAddedPlayers + "/" + playersCount + "\nPlayers");
                    checkPlayerCount();
                    adapterTeamAddedPlayersLineup.notifyDataSetChanged();
                } else {
                }
            } else if (position == 3) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
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

