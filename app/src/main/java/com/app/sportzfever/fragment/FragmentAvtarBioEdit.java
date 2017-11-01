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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentAvtarBioEdit extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Button btn_submit;
    private Bundle b;
    private Activity context;
    private ModelAvtarProfile modelAvtarProfile;
    private EditText avtar_name, avtar_battinghand, avtar_jersery_number, avtar_battingstyle,
            avtar_bowlingstyle, avtar_bowlinghand, avtar_speciality, avtar_favouritefieldposition, avtar_aboutme;
    public static FragmentAvtarBioEdit fragment_teamJoin_request;
    private final String TAG = FragmentAvtarBioEdit.class.getSimpleName();
    private String avtarId = "";
    View view_about;

    public static FragmentAvtarBioEdit getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentAvtarBioEdit();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragement_avtarbio_edit, container, false);
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
        HeaderViewManager.getInstance().setHeading(true, "Edit Bio");
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
        avtar_battinghand = (EditText) view.findViewById(R.id.avtar_battinghand);
        avtar_jersery_number = (EditText) view.findViewById(R.id.avtar_jersery_number);
        avtar_battingstyle = (EditText) view.findViewById(R.id.avtar_battingstyle);
        avtar_bowlingstyle = (EditText) view.findViewById(R.id.avtar_bowlingstyle);
        avtar_bowlinghand = (EditText) view.findViewById(R.id.avtar_bowlinghand);
        avtar_speciality = (EditText) view.findViewById(R.id.avtar_speciality);
        avtar_favouritefieldposition = (EditText) view.findViewById(R.id.avtar_favouritefieldposition);
        avtar_aboutme = (EditText) view.findViewById(R.id.avtar_aboutme);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        getBundle();
        setlistener();
    }

    private void getBundle() {
        try {

            Bundle bundle = getArguments();
            if (bundle != null) {
                String data = bundle.getString("data");
                JSONObject jo = new JSONObject(data);

                avtarId = jo.getString("avatarId");
                avtar_name.setText(jo.getString("name"));
                avtar_aboutme.setText(jo.getString("description"));
                avtar_battinghand.setText(jo.getString("battingHand"));
                avtar_battingstyle.setText(jo.getString("battingStyle"));
                avtar_bowlingstyle.setText(jo.getString("bowlingStyle"));
                avtar_bowlinghand.setText(jo.getString("bowlingHand"));
                avtar_speciality.setText(jo.getString("speciality"));
                avtar_favouritefieldposition.setText(jo.getString("favouriteFieldPosition"));
                avtar_jersery_number.setText(jo.getString("jersyNumber"));

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
                jsonObject.put("sportName", "Cricket");
                jsonObject.put("avatarId", avtarId);
                jsonObject.put("name", avtar_name.getText().toString());
                jsonObject.put("battingHand", avtar_battinghand.getText().toString());
                jsonObject.put("battingStyle", avtar_battingstyle.getText().toString());
                jsonObject.put("bowlingHand", avtar_bowlinghand.getText().toString());
                jsonObject.put("bowlingStyle", avtar_bowlingstyle.getText().toString());
                jsonObject.put("speciality", avtar_speciality.getText().toString());
                jsonObject.put("favouriteFieldPosition", avtar_favouritefieldposition.getText().toString());
                jsonObject.put("jersyNumber", avtar_jersery_number.getText().toString());
                jsonObject.put("description", avtar_aboutme.getText().toString());

                JSONObject avatarDetails = new JSONObject();
                avatarDetails.put("AvatarDetails", jsonObject);

                //   http://sfscoring.betasportzfever.com/updateAvatarDetails
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATE_AVTARDETAILS;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, avatarDetails, Request.Method.PUT);

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

