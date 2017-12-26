package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAboutMe;
import com.app.sportzfever.models.ModelAvtarProfile;
import com.app.sportzfever.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentUserProfile extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Bundle b;
    private Context context;
    private ArrayList<ModelAvtarProfile> arrayList;
    public static FragmentUserProfile fragment_teamJoin_request;
    private final String TAG = FragmentUserProfile.class.getSimpleName();
    private String avtarid = "";
    private TextView avtar_namelive, avtar_battingheldlive, avtar_weightlive, avtar_heigtlive, avtar_jercynumberlive, avtar_battingstylelive, avtar_bowlingstylelive, avtar_bowlinghandlive, avtar_specialitylive;
    private ModelAboutMe modelAboutMe;
    ImageView img_profilepic, image_edit;
    JSONObject data;

    public static FragmentUserProfile getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentUserProfile();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragement_user_profile, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avtar_namelive = (TextView) view.findViewById(R.id.avtar_namelive);
        avtar_battingheldlive = (TextView) view.findViewById(R.id.avtar_battingheldlive);
        avtar_jercynumberlive = (TextView) view.findViewById(R.id.avtar_jercynumberlive);
        avtar_battingstylelive = (TextView) view.findViewById(R.id.avtar_battingstylelive);
        avtar_bowlingstylelive = (TextView) view.findViewById(R.id.avtar_bowlingstylelive);
        avtar_bowlinghandlive = (TextView) view.findViewById(R.id.avtar_bowlinghandlive);
        avtar_specialitylive = (TextView) view.findViewById(R.id.avtar_specialitylive);
        avtar_heigtlive = (TextView) view.findViewById(R.id.avtar_heigtlive);
        avtar_weightlive = (TextView) view.findViewById(R.id.avtar_weightlive);
        img_profilepic = (ImageView) view.findViewById(R.id.img_profilepic);
        image_edit = (ImageView) view.findViewById(R.id.image_edit);

        setlistener();
    }

    @Override
    public void onResume() {
        super.onResume();
        getBundle();
    }

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            avtarid = bundle.getString("avtarid");
            if (avtarid.equalsIgnoreCase(AppUtils.getUserId(context))) {
                image_edit.setVisibility(View.VISIBLE);
            } else {
                image_edit.setVisibility(View.GONE);
            }
            String data = bundle.getString("data");
            setData(data);
        }
    }

    private void setData(String maindata) {
        try {
            data = new JSONObject(maindata);

            modelAboutMe = new ModelAboutMe();

            modelAboutMe.setUserId(data.getString("userId"));

            modelAboutMe.setUserName(data.getString("userName"));
            modelAboutMe.setGender(data.getString("gender"));
            modelAboutMe.setHometown(data.getString("hometown"));
            modelAboutMe.setCurrentLocation(data.getString("currentLocation"));
            modelAboutMe.setProfilePicture(data.getString("profilePicture"));
            modelAboutMe.setHeight(data.getString("height"));
            modelAboutMe.setAbout(data.getString("about"));
            modelAboutMe.setWeight(data.getString("weight"));

            avtar_namelive.setText(modelAboutMe.getUserName());
            avtar_jercynumberlive.setText(modelAboutMe.getGender());
            avtar_battingstylelive.setText(AppUtils.getUseremail(context));
            avtar_bowlingstylelive.setText(modelAboutMe.getHometown());
            avtar_bowlinghandlive.setText(modelAboutMe.getCurrentLocation());
            avtar_specialitylive.setText(modelAboutMe.getAbout());
            avtar_heigtlive.setText(modelAboutMe.getHeight() + " " + modelAboutMe.getHeightUnit());
            avtar_weightlive.setText(modelAboutMe.getWeight());
            if (!modelAboutMe.getProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(context).load(modelAboutMe.getProfilePicture()).placeholder(R.drawable.ic_launcher).into(img_profilepic);
            }
            JSONObject jdob = data.getJSONObject("dateOfBirth");
            avtar_battingheldlive.setText(jdob.getString("date") + " " + jdob.getString("monthName") + " " + jdob.getString("year"));

            modelAboutMe.setRowType(1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setlistener() {
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPersonalProfileEdit tab2 = new FragmentPersonalProfileEdit();
                Bundle b = new Bundle();
                b.putString("data", data.toString());
                tab2.setArguments(b);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, tab2, true);
            }
        });

    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {

        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_USERABOUT + avtarid + "/" + AppUtils.getAuthToken(context);
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
                    data = jObject.getJSONObject("data");
                    JSONObject jdob = data.getJSONObject("dateOfBirth");

                    modelAboutMe = new ModelAboutMe();

                    modelAboutMe.setUserId(data.getString("userId"));

                    modelAboutMe.setUserName(data.getString("userName"));
                    modelAboutMe.setGender(data.getString("gender"));
                    modelAboutMe.setHometown(data.getString("hometown"));
                    modelAboutMe.setCurrentLocation(data.getString("currentLocation"));
                    modelAboutMe.setProfilePicture(data.getString("profilePicture"));
                    modelAboutMe.setHeight(data.getString("height"));
                    modelAboutMe.setAbout(data.getString("about"));
                    modelAboutMe.setWeight(data.getString("weight"));

                    avtar_namelive.setText(modelAboutMe.getUserName());
                    avtar_jercynumberlive.setText(modelAboutMe.getGender());
                    avtar_battingstylelive.setText(AppUtils.getUseremail(context));
                    avtar_bowlingstylelive.setText(modelAboutMe.getHometown());
                    avtar_bowlinghandlive.setText(modelAboutMe.getCurrentLocation());
                    avtar_specialitylive.setText(modelAboutMe.getAbout());
                    avtar_heigtlive.setText(modelAboutMe.getHeight() + " " + modelAboutMe.getHeightUnit());
                    avtar_weightlive.setText(modelAboutMe.getWeight());
                    avtar_battingheldlive.setText(jdob.getString("date") + " " + jdob.getString("monthName") + " " + jdob.getString("year"));
                    if (!modelAboutMe.getProfilePicture().equalsIgnoreCase("")) {
                        Picasso.with(context).load(modelAboutMe.getProfilePicture()).placeholder(R.drawable.ic_launcher).into(img_profilepic);
                    }
                    modelAboutMe.setRowType(1);
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

