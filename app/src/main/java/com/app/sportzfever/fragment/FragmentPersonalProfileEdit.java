package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAvtarProfile;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentPersonalProfileEdit extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Button btn_submit;
    private Bundle b;
    private Activity context;
    private ModelAvtarProfile modelAvtarProfile;
    private EditText avtar_name, avtar_dob, avtar_gender, avtar_hometown,
            avtar_currentlocation, avtar_aboutme;
    public static FragmentPersonalProfileEdit fragment_teamJoin_request;
    private final String TAG = FragmentPersonalProfileEdit.class.getSimpleName();
    private String avtarId = "";
    View view_about;
    String latitude = "0.0", longitude = "0.0";

    public static FragmentPersonalProfileEdit getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentPersonalProfileEdit();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragement_personalprofile_edit, container, false);
        context = getActivity();
        b = getArguments();
        manageHeaderView();
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
        HeaderViewManager.getInstance().setHeading(true, "Edit Profile");
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

        avtar_name = (EditText) view.findViewById(R.id.avtar_name);
        avtar_dob = (EditText) view.findViewById(R.id.avtar_dob);
        avtar_gender = (EditText) view.findViewById(R.id.avtar_gender);
        avtar_hometown = (EditText) view.findViewById(R.id.avtar_hometown);
        avtar_currentlocation = (EditText) view.findViewById(R.id.avtar_currentlocation);
        avtar_aboutme = (EditText) view.findViewById(R.id.avtar_aboutme);
        //  avtar_aboutme = (EditText) view.findViewById(R.id.avtar_aboutme);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        getBundle();
        GPSTracker gps = new GPSTracker(context);
        if (gps.isGPSEnabled) {
            latitude = gps.getLatitude() + "";
            longitude = gps.getLongitude() + "";
        } else {
        }
        setlistener();
    }

    private void getBundle() {

        try {

            Bundle bundle = getArguments();
            if (bundle != null) {
                String data = bundle.getString("data");
                JSONObject jo = new JSONObject(data);

                JSONObject dateOfBirth = jo.getJSONObject("dateOfBirth");

                avtarId = jo.getString("userId");
                avtar_name.setText(jo.getString("userName"));
//                avtar_aboutme.setText(jo.getString("description"));
                avtar_dob.setText(dateOfBirth.getString("datetime"));
                avtar_hometown.setText(jo.getString("hometown"));
                avtar_currentlocation.setText(jo.getString("currentLocation"));
                avtar_aboutme.setText(jo.getString("about"));
                avtar_gender.setText(jo.getString("gender"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setlistener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });

    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {

        }
    }

    private void submitPost() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", avtarId);
                jsonObject.put("gender", avtar_gender.getText().toString());
                jsonObject.put("homeTown", avtar_hometown.getText().toString());
                jsonObject.put("currentLocation", avtar_currentlocation.getText().toString());
                jsonObject.put("lat", latitude);
                jsonObject.put("lng", longitude);
                jsonObject.put("height", "");
                jsonObject.put("phoneNo", "");
                jsonObject.put("weight", "");
                jsonObject.put("dob", avtar_dob.getText().toString());
                jsonObject.put("about", avtar_aboutme.getText().toString());
                //   http://sfscoring.betasportzfever.com/updateAvatarDetails
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATE_PROFILEDETAILS;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

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
              //  Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    //JSONObject data = jObject.getJSONObject("data");
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    context.onBackPressed();
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

