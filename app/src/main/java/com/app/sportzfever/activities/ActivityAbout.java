package com.app.sportzfever.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.MyFirebaseMessagingService;
import com.app.sportzfever.R;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAboutMe;
import com.app.sportzfever.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityAbout extends AppCompatActivity implements OnCustomItemClicListener, ApiResponse {

    private Context mActivity;
    private RecyclerView chatList;
    private ArrayList<ModelAboutMe> chatListData;
    ImageView img_profilepic;
    private Toolbar toolbar;
    private TextView avtar_namelive, avtar_battingheldlive, avtar_weightlive, avtar_heigtlive, avtar_jercynumberlive, avtar_battingstylelive, avtar_bowlingstylelive, avtar_bowlinghandlive, avtar_specialitylive;
    private LinearLayoutManager layoutManager;
    private boolean loading = true, isActivityVisible = true;
    private String maxlistLength = "", serviceId = "";
    private ModelAboutMe modelAboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_about);
        mActivity = this;
        init();
        setListener();
        getServicelistRefresh();

        MyFirebaseMessagingService.value = 0;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MyFirebaseMessagingService.PushNotificationId);
    }


    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avtar_namelive = (TextView) findViewById(R.id.avtar_namelive);
        avtar_battingheldlive = (TextView) findViewById(R.id.avtar_battingheldlive);
        avtar_jercynumberlive = (TextView) findViewById(R.id.avtar_jercynumberlive);
        avtar_battingstylelive = (TextView) findViewById(R.id.avtar_battingstylelive);
        avtar_bowlingstylelive = (TextView) findViewById(R.id.avtar_bowlingstylelive);
        avtar_bowlinghandlive = (TextView) findViewById(R.id.avtar_bowlinghandlive);
        avtar_specialitylive = (TextView) findViewById(R.id.avtar_specialitylive);
        avtar_heigtlive = (TextView) findViewById(R.id.avtar_heigtlive);
        avtar_weightlive = (TextView) findViewById(R.id.avtar_weightlive);
        img_profilepic = (ImageView) findViewById(R.id.img_profilepic);

        Intent in = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Profile");


        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityVisible = false;
        AppUtils.setIsChatVisible(mActivity, false);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onItemClickListener(int position, int flag) {

    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {

            if (AppUtils.isNetworkAvailable(mActivity)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_USERABOUT + AppUtils.getUserId(mActivity) + "/" + AppUtils.getAuthToken(mActivity);
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (jObject != null) {
                if (method == 1) {

                    if (jObject.getString("result").equalsIgnoreCase("1")) {
                        JSONObject data = jObject.getJSONObject("data");
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
                        avtar_battingstylelive.setText(AppUtils.getUseremail(mActivity));
                        avtar_bowlingstylelive.setText(modelAboutMe.getHometown());
                        avtar_bowlinghandlive.setText(modelAboutMe.getCurrentLocation());
                        avtar_specialitylive.setText(modelAboutMe.getAbout());
                        avtar_heigtlive.setText(modelAboutMe.getHeight() + " " + modelAboutMe.getHeightUnit());
                        avtar_weightlive.setText(modelAboutMe.getWeight());
                        avtar_battingheldlive.setText(jdob.getString("date") + " " + jdob.getString("monthName") + " " + jdob.getString("year"));
                        if (!modelAboutMe.getProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mActivity).load(modelAboutMe.getProfilePicture()).placeholder(R.drawable.ic_launcher).into(img_profilepic);
                        }
                        modelAboutMe.setRowType(1);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isActivityVisible) {

            }

        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        isActivityVisible = false;

    }


}
