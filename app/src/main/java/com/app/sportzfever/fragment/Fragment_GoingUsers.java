package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterGoingUser;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelRSVPUsers;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_GoingUsers extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private ArrayList<ModelRSVPUsers> listUsers;
    private ConnectionDetector cd;
    private TextView text_nodata;

    public static Fragment_GoingUsers fragment_teamJoin_request;
    private final String TAG = Fragment_GoingUsers.class.getSimpleName();
    private String avtarid = "";

    public static Fragment_GoingUsers getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new Fragment_GoingUsers();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_going_users, container, false);
        context = getActivity();
        listUsers = new ArrayList<>();
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
                String data = bundle.getString("data");
                JSONArray array = new JSONArray(data);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);

                    ModelRSVPUsers modelRSVPUsers = new ModelRSVPUsers();
                    modelRSVPUsers.setEventId(jsonObject.getString("eventId"));
                    modelRSVPUsers.setUserId(jsonObject.getString("userId"));
                    modelRSVPUsers.setRequestStatus(jsonObject.getString("requestStatus"));
                    modelRSVPUsers.setInviteFromAvatar(jsonObject.getString("inviteFromAvatar"));
                    modelRSVPUsers.setInviteFromAvatarName(jsonObject.getString("inviteFromAvatarName"));
                    modelRSVPUsers.setInviteFromAvatarProfilePicture(jsonObject.getString("inviteFromAvatarProfilePicture"));
                    modelRSVPUsers.setInviteToUser(jsonObject.getString("inviteToUser"));
                    modelRSVPUsers.setInviteToUserName(jsonObject.getString("inviteToUserName"));
                    modelRSVPUsers.setInviteToUserProfilePicture(jsonObject.getString("inviteToUserProfilePicture"));
                    modelRSVPUsers.setInviteToAvatar(jsonObject.getString("inviteToAvatar"));
                    modelRSVPUsers.setInviteToAvatarName(jsonObject.getString("inviteToAvatarName"));
                    modelRSVPUsers.setInviteToAvatarProfilePicture(jsonObject.getString("inviteToAvatarProfilePicture"));
                    modelRSVPUsers.setRowType(1);
                    listUsers.add(modelRSVPUsers);

                }

                AdapterGoingUser adapterGoingUser = new AdapterGoingUser(context, this, listUsers);
                list_request.setAdapter(adapterGoingUser);

                if (listUsers.size() > 0) {
                    text_nodata.setVisibility(View.GONE);
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No User found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        list_request.setLayoutManager(new LinearLayoutManager(context));
        getBundle();
        setlistener();

    }

    private void setlistener() {

    }

    @Override
    public void onItemClickListener(int position, int flag) {

    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPCOMINGMATCHDETAILS + avtarid + "/" + AppUtils.getAuthToken(context);
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}


