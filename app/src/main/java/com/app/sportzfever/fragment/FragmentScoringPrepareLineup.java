package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.app.sportzfever.utils.CircleTransform;
import com.app.sportzfever.utils.SportzDatabase;
import com.squareup.picasso.Picasso;

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
    private Button btn_send_invite, btn_next, btn_proceed;
    private TextView text_nodata, textSelectAll, text_selected_players, text_create_lineup;
    private ArrayList<String> listAvtarId = new ArrayList<>();
    private ArrayList<String> emailIdlIst = new ArrayList<>();
    private TextView text_name5, text_name1, text_name2, text_name3, text_name4, text_name6, text_name7,
            text_name8, text_name9, text_name10, text_name11;
    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10, ll11;
    private ImageView image_cross, image_player1, image_player2, image_player3, image_player4, image_player5,
            image_player6, image_player7, image_player8, image_player9, image_player10, image_player11;
    private RelativeLayout rl_preview, rl_previewopen;
    public static FragmentScoringPrepareLineup fragment_teamJoin_request;
    private final String TAG = FragmentScoringPrepareLineup.class.getSimpleName();
    private String team2Id = "", eventId = "", team1Id = "", team1Name = "", team2Name = "";
    private JSONObject jsonLinupArray;
    private String playersCount = "";
    private CheckBox checkbox_scoring;
    private String teamCheckAvailibility = "", title = "", overs = "", team1ScorerName = "", team2ScorerName = "";
    private String matchId = "";
    private boolean isTeam1 = true;
    private RelativeLayout rl_main;
    private String isScorerForTeam1 = "", isScorerForTeam2 = "";
    private SportzDatabase db;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setTargetFragment(null, -1);
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
        setDatabase();
        init(view);
        setlistener();
        getBundle();
        manageHeaderView();
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
        image_player1 = (ImageView) view.findViewById(R.id.image_player1);
        image_player2 = (ImageView) view.findViewById(R.id.image_player2);
        image_player3 = (ImageView) view.findViewById(R.id.image_player3);
        image_player4 = (ImageView) view.findViewById(R.id.image_player4);
        image_player5 = (ImageView) view.findViewById(R.id.image_player5);
        image_player6 = (ImageView) view.findViewById(R.id.image_player6);
        image_player7 = (ImageView) view.findViewById(R.id.image_player7);
        image_player8 = (ImageView) view.findViewById(R.id.image_player8);
        image_player9 = (ImageView) view.findViewById(R.id.image_player9);
        image_player10 = (ImageView) view.findViewById(R.id.image_player10);
        image_player11 = (ImageView) view.findViewById(R.id.image_player11);

        rl_preview = (RelativeLayout) view.findViewById(R.id.rl_preview);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        rl_previewopen = (RelativeLayout) view.findViewById(R.id.rl_previewopen);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        list_added_players = (RecyclerView) view.findViewById(R.id.list_added_players);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        textSelectAll = (TextView) view.findViewById(R.id.textSelectAll);
        textSelectAll.setVisibility(View.VISIBLE);
        text_selected_players = (TextView) view.findViewById(R.id.text_selected_players);
        text_create_lineup = (TextView) view.findViewById(R.id.text_create_lineup);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_proceed = (Button) view.findViewById(R.id.btn_proceed);
        btn_send_invite = (Button) view.findViewById(R.id.btn_send_invite);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list_request.setLayoutManager(layoutManager);
        list_request.setNestedScrollingEnabled(false);
        checkbox_scoring = (CheckBox) view.findViewById(R.id.checkbox_scoring);
        list_added_players.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
        arrayListaddedPlayers = new ArrayList<>();
        adapterTeamAddedPlayersLineup = new AdapterTeamScoringPlayersLineup(context, this, arrayListaddedPlayers);
        list_added_players.setAdapter(adapterTeamAddedPlayersLineup);
    }

    private void getBundle() {
        try {
            b = getArguments();
            team1Id = b.getString("team1Id");
            isTeam1 = b.getBoolean("isTeam1");
            team2Id = b.getString("team2Id");
            eventId = b.getString("eventId");
            matchId = b.getString("matchId");
            team1ScorerName = b.getString("team1ScorerName");
            team2ScorerName = b.getString("team2ScorerName");
            overs = b.getString("overs");
            Log.e("overs", "**" + overs);
            team1Name = b.getString("team1Name");
            team2Name = b.getString("team2Name");
            isScorerForTeam1 = b.getString("isScorerForTeam1");
            isScorerForTeam2 = b.getString("isScorerForTeam2");
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
        checkbox_scoring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    rl_main.setVisibility(View.VISIBLE);
                    btn_proceed.setVisibility(View.GONE);
                    text_nodata.setVisibility(View.GONE);
                    HeaderViewManager.getInstance().setRightSideHeaderView(true, R.drawable.right_arrow_white);
                } else {
                    rl_main.setVisibility(View.GONE);
                    btn_proceed.setVisibility(View.VISIBLE);
                    text_nodata.setVisibility(View.VISIBLE);
                    HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.right_arrow_white);
                    text_nodata.setText(R.string.final_score_message);
                }
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jo = makeBlankJsonRequest();
                sentInvite(jo);
                if (isTeam1) {
                    FragmentScoringPrepareLineup fragmentupcomingdetals = new FragmentScoringPrepareLineup();
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", eventId);
                    bundle.putString("matchId", matchId);
                    bundle.putString("team1Id", team2Id);
                    bundle.putString("team2Id", team1Id);
                    bundle.putString("isScorerForTeam1", isScorerForTeam1);
                    bundle.putString("isScorerForTeam2", isScorerForTeam2);
                    bundle.putString("team1ScorerName", b.getString("team1ScorerName"));
                    bundle.putString("team2ScorerName", b.getString("team2ScorerName"));
                    bundle.putString("overs", overs);
                    bundle.putBoolean("isTeam1", false);
                    bundle.putString("playersCount", playersCount);
                    bundle.putString("title", team2Name);
                    bundle.putString("team1Name", team2Name);
                    bundle.putString("team2Name", team1Name);
                    fragmentupcomingdetals.setArguments(bundle);
                    fragmentupcomingdetals.setTargetFragment(FragmentScoringPrepareLineup.this, AppConstant.FRAGMENT_CODE);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
                } else {
                    checkLineupComplete();
                }
            }
        });

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
                if (intAddedPlayers < Integer.parseInt(playersCount)) {
                    FragmentSearchNewPlayer fragmentPrepareLineup = new FragmentSearchNewPlayer();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("emailList", emailIdlIst);
                    fragmentPrepareLineup.setArguments(bundle);
                    fragmentPrepareLineup.setTargetFragment(FragmentScoringPrepareLineup.this, AppConstant.SEARCH_FRAGMENT_CODE);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentPrepareLineup, true);

                } else {
                    Toast.makeText(context, "Team playing lineup is complete. Lets add scorer and captain.", Toast.LENGTH_SHORT).show();
                }
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

    private void checkLineupComplete() {

        try {
            if (db != null) {
                db.open();
                JSONObject jObject = db.checkForLineUpComplete(Integer.parseInt(matchId));
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    checkLineupCompleteAndMove(data, jObject);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       /* try {
            if (AppUtils.isNetworkAvailable(context)) {
                // http://sfscoring.sf.com/CheckForLineUpComplete/87
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CHECK_FOR_LNEUP_COMPLETE + matchId;
                new CommonAsyncTaskHashmap(5, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    private void moveNext() {
        if (intAddedPlayers > 0) {
            JSONObject jsonObject = makeJsonRequest();
            JSONObject jsonObject1 = makeJsonRequest1();
            sentInvite(jsonObject1);
            JSONObject playerAvalablity = makeAddedPlayerJsonRequest();
            FragmentScoringMatchRoles fragmentPrepareLineup = new FragmentScoringMatchRoles();
            Bundle bundle = new Bundle();
            bundle.putString("teamId", team1Id);
            bundle.putString("team1Id", team1Id);
            bundle.putString("team2Id", team2Id);
            bundle.putString("overs", overs);
            bundle.putString("isScorerForTeam1", isScorerForTeam1);
            bundle.putString("isScorerForTeam2", isScorerForTeam2);
            bundle.putString("team1ScorerName", b.getString("team1ScorerName"));
            bundle.putString("team2ScorerName", b.getString("team2ScorerName"));
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
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentPrepareLineup, true);
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
            if (getTargetFragment() != null)
                getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());

            context.onBackPressed();
        }
        if (resultCode == AppConstant.RESULTCODE_NEW) {
            if (getTargetFragment() != null)
                getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_NEW, new Intent());

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

    private void getRoasterList()
    {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if(db !=null) {
                JSONObject jObject=  db.getTeamProfile(Integer.parseInt( team1Id),Integer.parseInt( AppUtils.getUserId(context)));
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
                }
            }
//            if (AppUtils.isNetworkAvailable(context)) {
//                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + team1Id + "/" + AppUtils.getAuthToken(context);
//                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);
//            } else {
//                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sentInvite(JSONObject jsonObject) {
        try
        {
            if(db!= null)
            {
                db.open();
                db.manageLineUpForMatch(jsonObject);
            }
            /*if (AppUtils.isNetworkAvailable(context)) {

                //   http://sfscoring.betasportzfever.com/getMatchLineup/23/77/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.MANAGE_LINEUP_MATCH;
                new CommonAsyncTaskHashmap(3, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDatabase() {
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

    private JSONObject makeBlankJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("teamId", team1Id);
            jsonObject.put("matchId", matchId);
            jsonObject.put("isTeamScoringOnSf", "0");
            jsonObject.put("teamCheckAvailibility", "0");
            jsonObject.put("newMatchlineUp", new JSONArray());
            jsonObject.put("existingPlayersToAdd", new JSONArray());
            jsonObject.put("newPlayersToAddInTeam", new JSONArray());

            JSONObject user = new JSONObject();
            user.put("user", "");
            user.put("avatar", "");
            user.put("email", "");
            jsonObject.put("captain", user);
            jsonObject.put("viceCaptain", user);
            jsonObject.put("wicketKeeper", user);

            JSONArray scorers = new JSONArray();
            for (int i = 0; i < 3; i++) {
                JSONObject jo = new JSONObject();
                jo.put("order", i + 1);
                jo.put("userId", "-1");
                scorers.put(jo);
            }
            jsonObject.put("scorers", scorers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
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
    private JSONObject makeJsonRequest1() {
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
            if (jsonLinupArray.has("scorers"))
            {
                JSONArray dd= jsonLinupArray.getJSONArray("scorers");
                int jlength=dd.length();
                JSONArray dd1=new JSONArray();
                  for (int i =0;i<dd.length();i++ )
                  {
                      JSONObject jsonObject1 = new JSONObject();
                      jsonObject1.put("order", i + 1);
                      jsonObject1.put("userId", dd.getJSONObject(i).getString("scorerId"));
                      dd1.put(jsonObject1);
                  }
                  if(jlength==0)
                  {
                      JSONObject jsonObject1 = new JSONObject();
                      jsonObject1.put("order", "1");
                      jsonObject1.put("userId", "-1");
                      dd1.put(jsonObject1);
                      jlength++;
                  }
                if(jlength == 1)
                {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("order", "2");
                    jsonObject1.put("userId", "-1");
                    dd1.put(jsonObject1);
                    jlength++;
                }
                if(jlength == 2)
                {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("order", "3");
                    jsonObject1.put("userId", "-1");
                    dd1.put(jsonObject1);

                }

                jsonObject.put("scorers", dd1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void getTeamLineup() {
        try
        {
            if(db!=null)
            {
                db.open();
                JSONObject jObject=  db.getMatchLineUp(Integer.parseInt( eventId),Integer.parseInt(team1Id));

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
                    if (jObject.getString("isTeamScoringOnSf").equals("0")) {
                        checkbox_scoring.setChecked(false);
                    }

                }
            }
//            if (AppUtils.isNetworkAvailable(context)) {
//                //   http://sfscoring.sf.com/api/matches/getLineUpForMatch/87/4
//                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_MATCH_LINEUP + team1Id + "/" + eventId + "/" + AppUtils.getAuthToken(context);
//                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, null, Request.Method.GET);
//
//            } else {
//                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);

//                if (jObject.getString("result").equalsIgnoreCase("1")) {
//
//                    JSONObject data = jObject.getJSONObject("data");
//
//                    JSONArray teamProfile = data.getJSONArray("teamProfile");
//                    arrayList.clear();
//                    for (int i = 0; i < teamProfile.length(); i++) {
//
//                        JSONObject jo = teamProfile.getJSONObject(i);
//
//                        modelSportTeamList = new ModelSportTeamList();
//
//                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
//                        modelSportTeamList.setAvtarId(jo.getString("avatar"));
//                        modelSportTeamList.setUserId(jo.getString("userId"));
//                        emailIdlIst.add(jo.getString("email"));
//                        modelSportTeamList.setNewPlayer(false);
//                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
//                        modelSportTeamList.setIsInPlayingBench("0");
//                        modelSportTeamList.setIsInPlayingSquad("1");
//                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
//                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
//                        modelSportTeamList.setIsAdded(0);
//                        modelSportTeamList.setRequestStatus(jo.getString("requestStatus"));
//                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
//                        modelSportTeamList.setRowType(1);
//                        if (!listAvtarId.contains(modelSportTeamList.getAvtarId())) {
//                            arrayList.add(modelSportTeamList);
//                        }
//                    }
//                    adapterSportTeamList = new AdapterTeamLineupRoster(getActivity(), this, arrayList);
//                    list_request.setAdapter(adapterSportTeamList);
//                }
            } else if (position == 2) {

//                if (jObject.getString("result").equalsIgnoreCase("1")) {
//                    jsonLinupArray = jObject;
//                    JSONArray jtaemown = jObject.getJSONArray("lineupPlayers");
//
//                    arrayListaddedPlayers.clear();
//                    for (int i = 0; i < jtaemown.length(); i++) {
//
//                        JSONObject jo = jtaemown.getJSONObject(i);
//                        modelSportTeamList = new ModelSportTeamList();
//
//                        modelSportTeamList.setAvtarId(jo.getString("avatarId"));
//                        listAvtarId.add(jo.getString("avatarId"));
//                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
//                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
//                        modelSportTeamList.setUserId(jo.getString("userId"));
//                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
//                        modelSportTeamList.setTeamId(jo.getString("teamId"));
//                        modelSportTeamList.setNewPlayer(false);
//                        modelSportTeamList.setMatchId(jo.getString("matchId"));
//                        modelSportTeamList.setOrder(jo.getString("order"));
//                        modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
//                        modelSportTeamList.setIsAdded(1);
//                        modelSportTeamList.setIsInPlayingBench(jo.getString("isInPlayingBench"));
//                        modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
//                        modelSportTeamList.setProfilePicture(jo.getString("playerProfilePicture"));
//                        modelSportTeamList.setRowType(1);
//                        modelSportTeamList.setIsInPlayingSquad("1");
//                        arrayListaddedPlayers.add(modelSportTeamList);
//                        intAddedPlayers++;
//                    }
//                    getRoasterList();
//                    text_selected_players.setText(intAddedPlayers + "/" + playersCount + "\nPlayers");
//                    checkPlayerCount();
//                    adapterTeamAddedPlayersLineup.notifyDataSetChanged();
//                    if (jObject.getString("isTeamScoringOnSf").equals("0")) {
//                        checkbox_scoring.setChecked(false);
//                    }
//
//                } else {
//                }
            } else if (position == 3) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 5) {
               /* if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    checkLineupCompleteAndMove(data, jObject);
                }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void checkLineupCompleteAndMove(JSONObject data, JSONObject jObject) {
        try {
            if (data.getString("isLineUpCompleteForBothTeams").equals("1")) {
                if (data.getString("isTeam1ScoringOnSf").equals("0") && data.getString("isTeam2ScoringOnSf").equals("0")) {
                    if (getTargetFragment() != null)
                        getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());

                    context.onBackPressed();
                    FragmentSaveTossResultInningScore fragmentupcomingdetals = new FragmentSaveTossResultInningScore();
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", eventId);
                    bundle.putString("matchId", matchId);
                    bundle.putString("isTeam1ScoringOnSf", data.getString("isTeam1ScoringOnSf"));
                    bundle.putString("isTeam2ScoringOnSf", data.getString("isTeam2ScoringOnSf"));
                    bundle.putString("isScorerForTeam1", data.getString("isScorerForTeam1"));
                    bundle.putString("isScorerForTeam2", data.getString("isScorerForTeam2"));
                    bundle.putString("playersCount", playersCount);
                    bundle.putString("overs", overs);
                    bundle.putString("team1Id", team2Id);
                    bundle.putString("team2Id", team1Id);
                    bundle.putString("title", "");
                    bundle.putString("team1Name", team2Name);
                    bundle.putString("team2Name", team1Name);
                    fragmentupcomingdetals.setArguments(bundle);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
                } else {
                    JSONObject scoringData = jObject.getJSONObject("scoringData");
                    if (scoringData.getString("isAllowedToScore").equalsIgnoreCase("1") && scoringData.getString("isActiveScorerForAnotherMatch").equalsIgnoreCase("0")) {
                        if (getTargetFragment() != null)
                            getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());

                        context.onBackPressed();

                        FragmentSaveTossResultInningScore fragmentupcomingdetals = new FragmentSaveTossResultInningScore();
                        Bundle bundle = new Bundle();
                        bundle.putString("eventId", eventId);
                        bundle.putString("matchId", matchId);
                        bundle.putString("isTeam1ScoringOnSf", data.getString("isTeam1ScoringOnSf"));
                        bundle.putString("isTeam2ScoringOnSf", data.getString("isTeam2ScoringOnSf"));
                        bundle.putString("isScorerForTeam1", data.getString("isScorerForTeam1"));
                        bundle.putString("isScorerForTeam2", data.getString("isScorerForTeam2"));
                        bundle.putString("playersCount", playersCount);
                        bundle.putString("overs", overs);
                        bundle.putString("team1Id", team2Id);
                        bundle.putString("team2Id", team1Id);
                        bundle.putString("title", "");
                        bundle.putString("team1Name", team2Name);
                        bundle.putString("team2Name", team1Name);
                        fragmentupcomingdetals.setArguments(bundle);
                        Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentupcomingdetals, true);
                    } else {
                        if (scoringData.getString("isAllowedToScore").equalsIgnoreCase("0")) {
                            String message = "You are not the designated scorer for this match" + "\n\n" + "Scorer for " + team2Name + ":" + "\n" + team1ScorerName
                                    + "\n" + "Scorer for " + team1Name + ":" + "\n" + team2ScorerName + "\n\n" + "Please ask your captain to make you match scorer if you want to do scoring.";
                            AppUtils.showDialogMessage(context, message.replace("\n", "<br />"));
                        } else {
                            JSONObject otherMatchDetails = scoringData.getJSONObject("otherMatchDetails");
                            showMessage(otherMatchDetails);
                        }
                    }
                }
            } else {
                Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray NotCompleteData = data.getJSONArray("NotCompleteData");
                for (int i = 0; i < NotCompleteData.length(); i++) {
                    JSONObject jsonObject = NotCompleteData.getJSONObject(i);
                    if (jsonObject.getString("isLineupCompleteTeam").equals("0")) {
                        String teamIdNotCompleted = jsonObject.getString("teamId");
                        if (teamIdNotCompleted.equals(team2Id)) {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_NEW, new Intent());
                            context.onBackPressed();
                            return;
                        } else {
                            context.onBackPressed();
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessage(JSONObject otherMatchDetails) {
        try {

            JSONObject team1 = otherMatchDetails.getJSONObject("team1");
            JSONObject team2 = otherMatchDetails.getJSONObject("team2");
            JSONObject dateTime = otherMatchDetails.getJSONObject("dateTime");
            String time = dateTime.getString("date") + " " + dateTime.getString("ShortMonthName") + " " + dateTime.getString("year");

            String message = "You are active score for a match on " + "\n" + time + "\nbetween\n" +
                    team1.getString("name") + "\nvs\n" + team2.getString("name") + "\n" + "Please complete that match then you can start this match scoring";
            AppUtils.showDialogMessage(context, message.replace("\n", "<br />"));

        } catch (Exception e) {
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

            if (addedPlayes.get(0).getProfilePicture() != null && !addedPlayes.get(0).getProfilePicture().equalsIgnoreCase(""))
                Picasso.with(context).load(addedPlayes.get(0).getProfilePicture()).transform(new CircleTransform()).into(image_player1);

            if (addedPlayes.size() > 1) {
                text_name2.setText(addedPlayes.get(1).getPlayerName());
                if (addedPlayes.get(1).getProfilePicture() != null && !addedPlayes.get(1).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(1).getProfilePicture()).transform(new CircleTransform()).into(image_player2);
            } else
                ll2.setVisibility(View.GONE);
            if (addedPlayes.size() > 2) {
                text_name3.setText(addedPlayes.get(2).getPlayerName());
                if (addedPlayes.get(2).getProfilePicture() != null && !addedPlayes.get(2).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(2).getProfilePicture()).transform(new CircleTransform()).into(image_player3);
            } else
                ll3.setVisibility(View.GONE);
            if (addedPlayes.size() > 3) {
                text_name4.setText(addedPlayes.get(3).getPlayerName());
                if (addedPlayes.get(3).getProfilePicture() != null && !addedPlayes.get(3).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(3).getProfilePicture()).transform(new CircleTransform()).into(image_player4);
            } else
                ll4.setVisibility(View.GONE);
            if (addedPlayes.size() > 4) {
                text_name5.setText(addedPlayes.get(4).getPlayerName());
                if (addedPlayes.get(4).getProfilePicture() != null && !addedPlayes.get(4).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(4).getProfilePicture()).transform(new CircleTransform()).into(image_player5);
            } else
                ll5.setVisibility(View.GONE);
            if (addedPlayes.size() > 5) {
                text_name6.setText(addedPlayes.get(5).getPlayerName());
                if (addedPlayes.get(5).getProfilePicture() != null && !addedPlayes.get(5).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(5).getProfilePicture()).transform(new CircleTransform()).into(image_player6);
            } else
                ll6.setVisibility(View.GONE);
            if (addedPlayes.size() > 6) {
                text_name7.setText(addedPlayes.get(6).getPlayerName());
                if (addedPlayes.get(6).getProfilePicture() != null && !addedPlayes.get(6).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(6).getProfilePicture()).transform(new CircleTransform()).into(image_player7);
            } else
                ll7.setVisibility(View.GONE);
            if (addedPlayes.size() > 7) {
                text_name8.setText(addedPlayes.get(7).getPlayerName());
                if (addedPlayes.get(7).getProfilePicture() != null && !addedPlayes.get(7).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(7).getProfilePicture()).transform(new CircleTransform()).into(image_player8);
            } else
                ll8.setVisibility(View.GONE);
            if (addedPlayes.size() > 8) {
                text_name9.setText(addedPlayes.get(8).getPlayerName());
                if (addedPlayes.get(8).getProfilePicture() != null && !addedPlayes.get(8).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(8).getProfilePicture()).transform(new CircleTransform()).into(image_player9);
            } else
                ll9.setVisibility(View.GONE);
            if (addedPlayes.size() > 9) {
                text_name10.setText(addedPlayes.get(9).getPlayerName());
                if (addedPlayes.get(9).getProfilePicture() != null && !addedPlayes.get(9).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(9).getProfilePicture()).transform(new CircleTransform()).into(image_player10);
            } else
                ll10.setVisibility(View.GONE);
            if (addedPlayes.size() > 10) {
                text_name11.setText(addedPlayes.get(10).getPlayerName());
                if (addedPlayes.get(10).getProfilePicture() != null && !addedPlayes.get(10).getProfilePicture().equalsIgnoreCase(""))
                    Picasso.with(context).load(addedPlayes.get(10).getProfilePicture()).transform(new CircleTransform()).into(image_player11);
            } else
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

