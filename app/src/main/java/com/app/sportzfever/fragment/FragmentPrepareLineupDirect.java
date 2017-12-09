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
    private String teamId = "";

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
        getServicelistRefresh();
    }

    private void getBundle() {
        Bundle b = getArguments();
        teamId = b.getString("teamId");
    }

    private void setlistener() {
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
        adapterTeamAddedPlayersLineup.notifyDataSetChanged();
        if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.ALL_ROUNDER)) {
            AKCount--;
            text_AR_count.setText(AKCount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BATSMAN)) {
            Batcount--;
            text_bat_count.setText(Batcount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BOWLER)) {
            BowlCount--;
            text_Bowl_count.setText(BowlCount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.WICKET_KEEPER)) {
            WkCount--;
            text_Wk_count.setText(WkCount + "");
        }
        int totalCount = AKCount + Batcount + Batcount + WkCount;
        text_selected_players.setText(totalCount + "/11"+"\nPlayers");
    }

    private void addDataInList(int position) {
        arrayListaddedPlayers.get(position).setIsAdded(1);
        adapterTeamAddedPlayersLineup.notifyDataSetChanged();
        if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.ALL_ROUNDER)) {
            AKCount++;
            text_AR_count.setText(AKCount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BATSMAN)) {
            Batcount++;
            text_bat_count.setText(Batcount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.BOWLER)) {
            BowlCount++;
            text_Bowl_count.setText(BowlCount + "");
        } else if (arrayListaddedPlayers.get(position).getSpeciality().equalsIgnoreCase(AppConstant.WICKET_KEEPER)) {
            WkCount++;
            text_Wk_count.setText(WkCount + "");
        }
        int totalCount = AKCount + Batcount + Batcount + WkCount;
        text_selected_players.setText(totalCount + "/11"+"\nPlayers");
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + teamId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbjectNoProgress(url, null, Request.Method.GET);

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
                        modelSportTeamList.setAvatar(jo.getString("avatar"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
                        if (jo.getString("speciality").equalsIgnoreCase("")) {
                            modelSportTeamList.setSpeciality(AppConstant.ALL_ROUNDER);
                        } else {
                            modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        }
                        modelSportTeamList.setIsAdded(0);
                        modelSportTeamList.setRequestStatus(jo.getString("requestStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                        modelSportTeamList.setRowType(1);
                        if (!modelSportTeamList.getRequestStatus().equalsIgnoreCase("PENDING")) {
                            arrayListaddedPlayers.add(modelSportTeamList);
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

