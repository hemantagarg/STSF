package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentCheckPlayerAvailability extends BaseFragment implements OnCustomItemClicListener, ApiResponse {

    private Bundle b;
    private TextView totalplayer;
    private Activity context;
    View mView;
    private Button btn_create_team;
    private CheckBox checkbox_player_availability;
    public static FragmentCheckPlayerAvailability fragment_friend_request;
    private final String TAG = FragmentCheckPlayerAvailability.class.getSimpleName();
    private String teamId = "", eventId = "", playersCount = "",title="";
    private JSONObject jsonresponse;

    public static FragmentCheckPlayerAvailability getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentCheckPlayerAvailability();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_check_player_availability, container, false);
        context = getActivity();
        b = getArguments();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getBundle();
        setlistener();
        manageHeaderView();
        getTeamLineup();
    }

    private void init() {
        checkbox_player_availability = (CheckBox) mView.findViewById(R.id.checkbox_player_availability);
        btn_create_team = (Button) mView.findViewById(R.id.btn_create_team);
        totalplayer = (TextView) mView.findViewById(R.id.totalplayer);

    }

    private void getBundle() {

        if (b != null) {
            teamId = b.getString("teamId");
            eventId = b.getString("eventId");
            title = b.getString("title");
            playersCount = b.getString("playersCount");
            totalplayer.setText(playersCount);
        }
    }


    private void getTeamLineup() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //   http://sfscoring.betasportzfever.com/getMatchLineup/23/77/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_MATCH_LINEUP + teamId + "/" + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, title);
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

    private void setlistener() {
        btn_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox_player_availability.isChecked()) {
                    FragmentPrepareLineup fragmentPrepareLineup = new FragmentPrepareLineup();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", teamId);
                    bundle.putString("eventId", eventId);
                    bundle.putString("playersCount", playersCount);
                    bundle.putString("title", title);
                    bundle.putString("jsonresponse", jsonresponse.toString());
                    bundle.putString("teamCheckAvailibility", "1");
                    fragmentPrepareLineup.setArguments(bundle);
                    fragmentPrepareLineup.setTargetFragment(FragmentCheckPlayerAvailability.this, AppConstant.FRAGMENT_CODE);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentPrepareLineup, true);
                } else {
                    FragmentPrepareLineupDirect fragmentPrepareLineup = new FragmentPrepareLineupDirect();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", teamId);
                    bundle.putString("title", title);
                    bundle.putString("eventId", eventId);
                    bundle.putString("playersCount", playersCount);
                    bundle.putString("teamCheckAvailibility", "0");
                    if (jsonresponse != null) {
                        bundle.putString("jsonresponse", jsonresponse.toString());
                    } else {
                        bundle.putString("jsonresponse", "");
                    }
                    fragmentPrepareLineup.setArguments(bundle);
                    fragmentPrepareLineup.setTargetFragment(FragmentCheckPlayerAvailability.this, AppConstant.FRAGMENT_CODE);
                    Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentPrepareLineup, true);

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppConstant.RESULTCODE_FINISH) {
            context.onBackPressed();
        }
    }


    @Override
    public void onItemClickListener(int position, int flag) {
   /*     Intent in = new Intent(context, ActivityChat.class);
        if (arrayList.get(position).getUserId().equalsIgnoreCase(AppUtils.getUserIdChat(context))) {
            in.putExtra("reciever_id", arrayList.get(position).getSenderID());
        } else {
            in.putExtra("reciever_id", arrayList.get(position).getUserId());
        }
        in.putExtra("name", arrayList.get(position).getSenderName());
        in.putExtra("image", arrayList.get(position).getReceiverImage());
        in.putExtra("searchID", arrayList.get(position).getSearchId());
        startActivity(in);*/
    }

    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {

                if (response.getString("result").equalsIgnoreCase("1")) {
                    jsonresponse = response;

                    String checkAvailability = response.getString("checkAvailability");
                    if (checkAvailability.equalsIgnoreCase("0")) {
                        checkbox_player_availability.setChecked(false);
                    } else {
                        checkbox_player_availability.setChecked(true);
                    }
                } else {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostFail(int method, String response) {

    }
}

