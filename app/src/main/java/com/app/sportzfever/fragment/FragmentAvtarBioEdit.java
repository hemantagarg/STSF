package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentAvtarBioEdit extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Button btn_submit;
    private Bundle b;
    private Activity context;
    private ModelAvtarProfile modelAvtarProfile;
    private EditText avtar_name, avtar_jersery_number,
            avtar_aboutme;
    public static FragmentAvtarBioEdit fragment_teamJoin_request;
    private final String TAG = FragmentAvtarBioEdit.class.getSimpleName();
    private String avtarId = "";
    private Spinner spinner_battinghand, spinner_battingstyle, spinner_bowlingstyle,
            spinner_bowlinghand, spinner_speciality, spinner_field_position;
    ArrayList<String> listBattinghand = new ArrayList<>();
    ArrayList<String> listBattingStyle = new ArrayList<>();
    ArrayList<String> listBowlingStyle = new ArrayList<>();
    ArrayList<String> listBowlinghand = new ArrayList<>();
    ArrayList<String> listSpeciality = new ArrayList<>();
    ArrayList<String> listFieldPosition = new ArrayList<>();
    ArrayAdapter<String> adapterBattinghand, adapterBattingStyle, adapterBowlingStyle,
            adapterBowlinghand, adapterSpeciality, adapterFieldPosition;
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
        avtar_jersery_number = (EditText) view.findViewById(R.id.avtar_jersery_number);
        avtar_aboutme = (EditText) view.findViewById(R.id.avtar_aboutme);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        spinner_battinghand = (Spinner) view.findViewById(R.id.spinner_battinghand);
        spinner_battingstyle = (Spinner) view.findViewById(R.id.spinner_battingstyle);
        spinner_bowlingstyle = (Spinner) view.findViewById(R.id.spinner_bowlingstyle);
        spinner_bowlinghand = (Spinner) view.findViewById(R.id.spinner_bowlinghand);
        spinner_speciality = (Spinner) view.findViewById(R.id.spinner_speciality);
        spinner_field_position = (Spinner) view.findViewById(R.id.spinner_field_position);
        setSpinnerdata();
        setlistener();
    }

    private void setSpinnerdata() {

        listBattinghand.add(AppConstant.NA);
        listBattinghand.add(AppConstant.RIGHTHAND);
        listBattinghand.add(AppConstant.LEFTHAND);
        adapterBattinghand = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listBattinghand);
        spinner_battinghand.setAdapter(adapterBattinghand);

        listBowlinghand.add(AppConstant.NA);
        listBowlinghand.add(AppConstant.RIGHTHAND);
        listBowlinghand.add(AppConstant.LEFTHAND);
        adapterBowlinghand = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listBowlinghand);
        spinner_bowlinghand.setAdapter(adapterBowlinghand);

        listBattingStyle.add(AppConstant.NA);
        listBattingStyle.add(AppConstant.DEFENSIVE);
        listBattingStyle.add(AppConstant.AGGRESIVE);
        listBattingStyle.add(AppConstant.MODERATE);
        adapterBattingStyle = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listBattingStyle);
        spinner_battingstyle.setAdapter(adapterBattingStyle);

        listBowlingStyle.add(AppConstant.NA);
        listBowlingStyle.add(AppConstant.FAST);
        listBowlingStyle.add(AppConstant.MEDIUM);
        listBowlingStyle.add(AppConstant.SLOW);
        listBowlingStyle.add(AppConstant.OFF_BREAK_SPIN);
        listBowlingStyle.add(AppConstant.LEG_BREAK_SPIN);
        adapterBowlingStyle = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listBowlingStyle);
        spinner_bowlingstyle.setAdapter(adapterBowlingStyle);


        listSpeciality.add(AppConstant.NA);
        listSpeciality.add(AppConstant.BATSMAN);
        listSpeciality.add(AppConstant.BOWLER);
        listSpeciality.add(AppConstant.WICKET_KEEPER);
        listSpeciality.add(AppConstant.ALL_ROUNDER);
        adapterSpeciality = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listSpeciality);
        spinner_speciality.setAdapter(adapterSpeciality);

        listFieldPosition.add(AppConstant.NA);
        listFieldPosition.add(AppConstant.SLIP);
        listFieldPosition.add(AppConstant.SHORT_LEG);
        listFieldPosition.add(AppConstant.WICKET_KEEPER);
        listFieldPosition.add(AppConstant.MID_WICKET);
        listFieldPosition.add(AppConstant.DEEP_MID_WICKET);
        listFieldPosition.add(AppConstant.FINE_LEG);
        listFieldPosition.add(AppConstant.SILLY_POINT);
        listFieldPosition.add(AppConstant.LONG_ON);
        listFieldPosition.add(AppConstant.LONG_OFF);
        listFieldPosition.add(AppConstant.MID_ON);
        listFieldPosition.add(AppConstant.SQUARE_LEG);
        listFieldPosition.add(AppConstant.DEEP_SQUARE_LEG);
        listFieldPosition.add(AppConstant.POINT);
        listFieldPosition.add(AppConstant.DEEP_COVER);
        listFieldPosition.add(AppConstant.THIRD_MAN);
        adapterFieldPosition = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listFieldPosition);
        spinner_field_position.setAdapter(adapterFieldPosition);
        getBundle();
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
                if (jo.getString("battingHand") != null && !jo.getString("battingHand").equalsIgnoreCase("")) {
                    if (listBattinghand.contains(jo.getString("battingHand"))) {
                        spinner_battinghand.setSelection(listBattinghand.indexOf(jo.getString("battingHand")));
                    }
                }

                if (jo.getString("battingStyle") != null && !jo.getString("battingStyle").equalsIgnoreCase("")) {
                    if (listBattingStyle.contains(jo.getString("battingStyle"))) {
                        spinner_battingstyle.setSelection(listBattingStyle.indexOf(jo.getString("battingStyle")));
                    }
                }
                if (jo.getString("bowlingStyle") != null && !jo.getString("bowlingStyle").equalsIgnoreCase("")) {
                    if (listBowlingStyle.contains(jo.getString("bowlingStyle"))) {
                        spinner_bowlingstyle.setSelection(listBowlingStyle.indexOf(jo.getString("bowlingStyle")));
                    }
                }
                if (jo.getString("bowlingHand") != null && !jo.getString("bowlingHand").equalsIgnoreCase("")) {
                    if (listBowlinghand.contains(jo.getString("bowlingHand"))) {
                        spinner_bowlinghand.setSelection(listBowlinghand.indexOf(jo.getString("bowlingHand")));
                    }
                }
                if (jo.getString("speciality") != null && !jo.getString("speciality").equalsIgnoreCase("")) {
                    if (listSpeciality.contains(jo.getString("speciality"))) {
                        spinner_speciality.setSelection(listSpeciality.indexOf(jo.getString("speciality")));
                    }
                }
                if (jo.getString("favouriteFieldPosition") != null && !jo.getString("favouriteFieldPosition").equalsIgnoreCase("")) {
                    if (listFieldPosition.contains(jo.getString("favouriteFieldPosition"))) {
                        spinner_field_position.setSelection(listFieldPosition.indexOf(jo.getString("favouriteFieldPosition")));
                    }
                }
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
                jsonObject.put("battingHand", spinner_battinghand.getSelectedItem().toString());
                jsonObject.put("battingStyle", spinner_battingstyle.getSelectedItem().toString());
                jsonObject.put("bowlingHand", spinner_bowlinghand.getSelectedItem().toString());
                jsonObject.put("bowlingStyle", spinner_bowlingstyle.getSelectedItem().toString());
                jsonObject.put("speciality", spinner_speciality.getSelectedItem().toString());
                jsonObject.put("favouriteFieldPosition", spinner_field_position.getSelectedItem().toString());
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

