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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private JSONArray arrayListNewPlayers = new JSONArray();
    private JSONArray arrayListExistingPlayers = new JSONArray();
    private int skipCount = 0, intAddedPlayers = 0;
    private View view_about;
    private Button btn_send_invite, btn_next;
    private TextView text_nodata, textSelectAll, text_selected_players, text_create_lineup;
    private ArrayList<String> listAvtarId = new ArrayList<>();
    private ArrayList<String> emailIdlIst = new ArrayList<>();
    private TextView text_name5, text_name1, text_name2, text_name3, text_name4, text_name6, text_name7, text_name8, text_name9, text_name10, text_name11;
    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10, ll11;
    private ImageView image_cross;
    private RelativeLayout rl_preview, rl_previewopen;


    public static FragmentScoringPrepareLineup fragment_teamJoin_request;
    private final String TAG = FragmentScoringPrepareLineup.class.getSimpleName();
    private String team2Id = "", eventId = "", team1Id = "", team1Name = "", team2Name = "";
    private JSONObject jsonLinupArray;
    private String playersCount = "";
    private String teamCheckAvailibility = "", title = "";
    private String matchId = "";
    private boolean isTeam1 = true;

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
        HeaderViewManager.getInstance().setRightSideHeaderView(true, R.drawable.right_arrow_white);
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
                moveNext();
            }
        };
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getBundle();
        manageHeaderView();
        setlistener();
    }

    private void init(View view) {
        text_name5 = (TextView) view.findViewById(R.id.text_name5);
        text_name1 = (TextView) view.findViewById(R.id.text_name1);
        text_name2 = (TextView) view.findViewById(R.id.text_name2);
        text_name3 = (TextView) view.findViewById(R.id.text_name3);
        text_name4 = (TextView) view.findViewById(R.id.text_name4);
        text_name6 = (TextView) view.findViewById(R.id.text_name6);
        text_name7 = (TextView) view.findViewById(R.id.text_name7);
        text_name8 = (TextView) view.findViewById(R.id.text_name8);
        text_name9 = (TextView) view.findViewById(R.id.text_name9);
        text_name10 = (TextView) view.findViewById(R.id.text_name10);
        text_name11 = (TextView) view.findViewById(R.id.text_name11);
        ll1 = (LinearLayout) view.findViewById(R.id.ll1);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2);
        ll3 = (LinearLayout) view.findViewById(R.id.ll3);
        ll4 = (LinearLayout) view.findViewById(R.id.ll4);
        ll5 = (LinearLayout) view.findViewById(R.id.ll5);
        ll6 = (LinearLayout) view.findViewById(R.id.ll6);
        ll7 = (LinearLayout) view.findViewById(R.id.ll7);
        ll8 = (LinearLayout) view.findViewById(R.id.ll8);
        ll9 = (LinearLayout) view.findViewById(R.id.ll9);
        ll10 = (LinearLayout) view.findViewById(R.id.ll10);
        ll11 = (LinearLayout) view.findViewById(R.id.ll11);
        image_cross = (ImageView) view.findViewById(R.id.image_cross);
        rl_preview = (RelativeLayout) view.findViewById(R.id.rl_preview);
        rl_previewopen = (RelativeLayout) view.findViewById(R.id.rl_previewopen);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        list_added_players = (RecyclerView) view.findViewById(R.id.list_added_players);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        textSelectAll = (TextView) view.findViewById(R.id.textSelectAll);
        textSelectAll.setVisibility(View.VISIBLE);
        text_selected_players = (TextView) view.findViewById(R.id.text_selected_players);
        text_create_lineup = (TextView) view.findViewById(R.id.text_create_lineup);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_send_invite = (Button) view.findViewById(R.id.btn_send_invite);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_request.setLayoutManager(layoutManager);
        list_request.setNestedScrollingEnabled(false);
        list_added_players.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
        arrayListaddedPlayers = new ArrayList<>();
        adapterTeamAddedPlayersLineup = new AdapterTeamScoringPlayersLineup(context, this, arrayListaddedPlayers);
        list_added_players.setAdapter(adapterTeamAddedPlayersLineup);
    }

    private void getBundle() {
        try {
            Bundle b = getArguments();
            team1Id = b.getString("team1Id");
            isTeam1 = b.getBoolean("isTeam1");
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
            int restCount = Integer.parseInt(playersCount) - intAddedPlayers;
            text_create_lineup.setText(restCount + " more player(s) required");
        } else {
            text_create_lineup.setText("Lineup Completed");
        }
    }

    private void setlistener() {
        rl_previewopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreviewData();
                rl_preview.setVisibility(View.VISIBLE);
            }
        });
        image_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_preview.setVisibility(View.GONE);
            }
        });
        textSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSearchNewPlayer fragmentPrepareLineup = new FragmentSearchNewPlayer();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("emailList", emailIdlIst);
                fragmentPrepareLineup.setArguments(bundle);
                fragmentPrepareLineup.setTargetFragment(FragmentScoringPrepareLineup.this, AppConstant.SEARCH_FRAGMENT_CODE);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);

            }
        });
        btn_send_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayListaddedPlayers.size() > 0) {
                    //   sentInvite();
                } else {
                    Toast.makeText(context, "Please add atleast one player", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveNext();
            }
        });
    }

    private void moveNext() {
        if (intAddedPlayers > 0) {
            JSONObject jsonObject = makeJsonRequest();
            sentInvite(jsonObject);
            JSONObject playerAvalablity = makeAddedPlayerJsonRequest();
            FragmentScoringMatchRoles fragmentPrepareLineup = new FragmentScoringMatchRoles();
            Bundle bundle = new Bundle();
            bundle.putString("teamId", team1Id);
            bundle.putString("team1Id", team1Id);
            bundle.putString("team2Id", team2Id);
            bundle.putString("team1Name", team1Name);
            bundle.putString("team2Name", team2Name);
            bundle.putString("title", title);
            bundle.putString("eventId", eventId);
            bundle.putString("matchId", matchId);
            bundle.putBoolean("isTeam1", isTeam1);
            bundle.putString("playersCount", playersCount);
            bundle.putString("teamCheckAvailibility", teamCheckAvailibility);
            bundle.putString("linepArray", jsonLinupArray.toString());
            bundle.putString("jsonresponse", playerAvalablity.toString());
            bundle.putString("jsonObject", jsonObject.toString());

            fragmentPrepareLineup.setArguments(bundle);
            fragmentPrepareLineup.setTargetFragment(FragmentScoringPrepareLineup.this, AppConstant.FRAGMENT_CODE);
            Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);
        } else {
            Toast.makeText(context, "Please add players", Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject makeAddedPlayerJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newMatchlineUp = new JSONArray();
            for (int i = 0; i < arrayListaddedPlayers.size(); i++) {
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
            getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());
            context.onBackPressed();
        }
        if (requestCode == AppConstant.SEARCH_FRAGMENT_CODE) {
            if (data != null) {
                String userData = data.getStringExtra("userData");
                setPlayerArray(userData, data.getBooleanExtra("isNewPlayer", false));
            }
        }
    }

    private void setPlayerArray(String userData, boolean isNewPlayer) {
        try {
            if (intAddedPlayers < Integer.parseInt(playersCount)) {
                if (isNewPlayer) {
                    JSONObject jo = new JSONObject(userData);
                    modelSportTeamList = new ModelSportTeamList();
                    modelSportTeamList.setUserId("");
                    modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                    modelSportTeamList.setPlayerName(jo.getString("playerName"));
                    modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                    modelSportTeamList.setSpeciality(jo.getString("role"));
                    modelSportTeamList.setNewPlayer(true);
                    modelSportTeamList.setOrder(arrayListaddedPlayers.size() + 1 + "");
                    modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                    modelSportTeamList.setIsAdded(1);
                    modelSportTeamList.setIsInPlayingBench(jo.getString("isInBench"));
                    modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                    modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                    modelSportTeamList.setRowType(1);
                    modelSportTeamList.setIsInPlayingSquad("1");
                    jo.put("order", modelSportTeamList.getOrder());
                    arrayListaddedPlayers.add(modelSportTeamList);
                    arrayListNewPlayers.put(jo);
                } else {
                    JSONObject jo = new JSONObject(userData);
                    modelSportTeamList = new ModelSportTeamList();

                    modelSportTeamList.setUserId(jo.getString("userId"));
                    modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                    modelSportTeamList.setPlayerName(jo.getString("playerName"));
                    modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                    modelSportTeamList.setSpeciality(jo.getString("role"));
                    modelSportTeamList.setNewPlayer(true);
                    modelSportTeamList.setOrder(arrayListaddedPlayers.size() + 1 + "");
                    modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                    modelSportTeamList.setIsAdded(1);
                    modelSportTeamList.setIsInPlayingBench(jo.getString("isInBench"));
                    modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                    modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                    modelSportTeamList.setRowType(1);
                    modelSportTeamList.setIsInPlayingSquad("1");
                    jo.put("order", modelSportTeamList.getOrder());
                    arrayListaddedPlayers.add(modelSportTeamList);
                    arrayListExistingPlayers.put(jo);

                }
                intAddedPlayers++;
                checkPlayerCount();
                adapterTeamAddedPlayersLineup.notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Team playing lineup is complete. Lets add scorer and captain.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void sentInvite(JSONObject jsonObject) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

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
                if (!arrayListaddedPlayers.get(i).isNewPlayer()) {
                    JSONObject jo = new JSONObject();
                    jsonObject.put("matchId", matchId);
                    jo.put("avatarId", arrayListaddedPlayers.get(i).getAvtarId());
                    jo.put("inviteStatus", AppConstant.ACCEPTED);
                    jo.put("isInBench", arrayListaddedPlayers.get(i).getIsInPlayingBench());
                    jo.put("isInPlayingSquad", arrayListaddedPlayers.get(i).getIsInPlayingSquad());
                    jo.put("isReservedPlayer", arrayListaddedPlayers.get(i).getIsReservedPlayer());
                    jo.put("order", i + 1 + "");
                    jo.put("role", arrayListaddedPlayers.get(i).getSpeciality());
                    newMatchlineUp.put(jo);
                }
            }
            jsonObject.put("teamId", team1Id);
            jsonObject.put("isTeamScoringOnSf", "1");
            jsonObject.put("teamCheckAvailibility", "0");
            jsonObject.put("newMatchlineUp", newMatchlineUp);
            jsonObject.put("existingPlayersToAdd", arrayListExistingPlayers);
            jsonObject.put("newPlayersToAddInTeam", arrayListNewPlayers);
            if (jsonLinupArray.has("captain")) {
                jsonObject.put("captain", jsonLinupArray.getJSONObject("captain"));
            }
            if (jsonLinupArray.has("viceCaptain")) {
                jsonObject.put("viceCaptain", jsonLinupArray.getJSONObject("viceCaptain"));
            }
            if (jsonLinupArray.has("scorers")) {
                jsonObject.put("scorers", jsonLinupArray.getJSONArray("scorers"));
            }
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
                        emailIdlIst.add(jo.getString("email"));
                        modelSportTeamList.setNewPlayer(false);
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setIsInPlayingBench("0");
                        modelSportTeamList.setIsInPlayingSquad("1");
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
                        modelSportTeamList.setNewPlayer(false);
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

    private void setPreviewData() {
        ArrayList<ModelSportTeamList> addedPlayes = new ArrayList<>();
        for (int i = 0; i < arrayListaddedPlayers.size(); i++) {
            addedPlayes.add(arrayListaddedPlayers.get(i));
        }
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        ll4.setVisibility(View.VISIBLE);
        ll5.setVisibility(View.VISIBLE);
        ll6.setVisibility(View.VISIBLE);
        ll7.setVisibility(View.VISIBLE);
        ll8.setVisibility(View.VISIBLE);
        ll9.setVisibility(View.VISIBLE);
        ll10.setVisibility(View.VISIBLE);
        ll11.setVisibility(View.VISIBLE);
        if (addedPlayes.size() > 0) {
            text_name1.setText(addedPlayes.get(0).getPlayerName());
            if (addedPlayes.size() > 1)
                text_name2.setText(addedPlayes.get(1).getPlayerName());
            else
                ll2.setVisibility(View.GONE);
            if (addedPlayes.size() > 2)
                text_name3.setText(addedPlayes.get(2).getPlayerName());
            else
                ll3.setVisibility(View.GONE);
            if (addedPlayes.size() > 3)
                text_name4.setText(addedPlayes.get(3).getPlayerName());
            else
                ll4.setVisibility(View.GONE);
            if (addedPlayes.size() > 4)
                text_name5.setText(addedPlayes.get(4).getPlayerName());
            else
                ll5.setVisibility(View.GONE);
            if (addedPlayes.size() > 5)
                text_name6.setText(addedPlayes.get(5).getPlayerName());
            else
                ll6.setVisibility(View.GONE);
            if (addedPlayes.size() > 6)
                text_name7.setText(addedPlayes.get(6).getPlayerName());
            else
                ll7.setVisibility(View.GONE);
            if (addedPlayes.size() > 7)
                text_name8.setText(addedPlayes.get(7).getPlayerName());
            else
                ll8.setVisibility(View.GONE);
            if (addedPlayes.size() > 8)
                text_name9.setText(addedPlayes.get(8).getPlayerName());
            else
                ll9.setVisibility(View.GONE);
            if (addedPlayes.size() > 9)
                text_name10.setText(addedPlayes.get(9).getPlayerName());
            else
                ll10.setVisibility(View.GONE);
            if (addedPlayes.size() > 10)
                text_name11.setText(addedPlayes.get(10).getPlayerName());
            else
                ll11.setVisibility(View.GONE);


        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
            ll11.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

