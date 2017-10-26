package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.PickLocation;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.GPSTracker;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_CreateTeam extends AppCompatActivity implements ApiResponse, OnCustomItemClicListener {

    private Activity context;
    private EditText edt_about_team, edt_team_name, edt_location;
    private Button btn_browse, btn_create_team;
    TextView edt_team_logo;
    private String selectedimagespath = "";
    public static Fragment_CreateTeam fragment_friend_request;
    private final String TAG = Fragment_CreateTeam.class.getSimpleName();
    private String feedId = "";
    String latitude = "0.0", longitude = "0.0";
    private ArrayAdapter<String> adapterSports;
    private ArrayList<String> listSports = new ArrayList<>();
    private ArrayList<String> listSportsId = new ArrayList<>();
    private Spinner spinner_sports;
    private String userid = "";
    private ImageView image_map;

    public static Fragment_CreateTeam getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_CreateTeam();
        return fragment_friend_request;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_create_team);
        context = this;
        init();
        GPSTracker gps = new GPSTracker(context);
        if (gps.isGPSEnabled) {
            latitude = gps.getLatitude() + "";
            longitude = gps.getLongitude() + "";
        } else {
            gps.showSettingsAlert();
        }
        setlistener();
        manageHeaderView();
    }

    private void init() {
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_about_team = (EditText) findViewById(R.id.edt_about_team);
        edt_team_name = (EditText) findViewById(R.id.edt_team_name);
        edt_team_logo = (TextView) findViewById(R.id.edt_team_logo);
        btn_browse = (Button) findViewById(R.id.btn_browse);
        btn_create_team = (Button) findViewById(R.id.btn_create_team);
        spinner_sports = (Spinner) findViewById(R.id.spinner_sports);
        image_map = (ImageView) findViewById(R.id.image_map);
        listSports.add(AppConstant.CRICKET);
        listSportsId.add("1");
        adapterSports = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listSports);
        spinner_sports.setAdapter(adapterSports);
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(context, null, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Create Team");
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
                onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setlistener() {

        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });
        btn_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_team_name.getText().toString().equalsIgnoreCase("")
                        && !edt_about_team.getText().toString().equalsIgnoreCase("")
                        && !edt_location.getText().toString().equalsIgnoreCase("")) {
                    createTeamRequest();

                } else {
                    if (edt_team_name.getText().toString().equalsIgnoreCase("")) {
                        edt_team_name.setError("Enter your team name");
                        edt_team_name.requestFocus();
                    } else if (edt_about_team.getText().toString().equalsIgnoreCase("")) {
                        edt_about_team.setError("Say something about your team");
                        edt_about_team.requestFocus();
                    } else if (edt_location.getText().toString().equalsIgnoreCase("")) {
                        edt_location.setError("Enter team Location");
                        edt_location.requestFocus();
                    }
                }
            }
        });

        image_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps = new GPSTracker(context);
                if (gps.isGPSEnabled) {
                    Intent i = new Intent(context, PickLocation.class);
                    i.putExtra("lat", latitude);
                    i.putExtra("lng", longitude);
                    startActivityForResult(i, 511);

                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 511 && resultCode == 512) {
            edt_location.setText(data.getStringExtra("location"));
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
        }
    }

/*
    private void submitPost() {
        Charset encoding = Charset.forName("UTF-8");
        MultipartEntity reqEntity = new MultipartEntity();
        try {
            StringBody userId = new StringBody(userid, encoding);
            StringBody statusVisiblity = new StringBody(spinner_sports.getSelectedItem().toString(), encoding);
            StringBody statusType = new StringBody("TEXT", encoding);
            StringBody description = new StringBody(edt_team_logo.getText().toString(), encoding);

          */
/*  FileBody filebodyimage = new FileBody(new File(arrayListPhotos.get(i).getImage()));
            reqEntity.addPart("statusImages[" + i + "]", filebodyimage);
*//*


            Log.e("user", userid);
            Log.e("statusVisibility", spinner_sports.getSelectedItem().toString());
            Log.e("statusType", "TEXT");
            Log.e("description", edt_team_logo.getText().toString());
            Log.e("Content-Type", "undefined");
            Log.e("Authorization", AppUtils.getAuthToken(context));

            reqEntity.addPart("user", userId);
            reqEntity.addPart("statusVisibility", statusVisiblity);
            reqEntity.addPart("statusType", statusType);
            reqEntity.addPart("description", description);

            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATESTATUS;
                new AsyncPostDataFileResponse(context, Fragment_CreateTeam.this, 1, reqEntity, url);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }
*/


    @Override
    public void onItemClickListener(int position, int flag) {

    }


    private void createTeamRequest() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userId", AppUtils.getAvtarId(context));
                jsonObject.put("sport", listSportsId.get(spinner_sports.getSelectedItemPosition()));
                jsonObject.put("name", edt_team_name.getText().toString());
                jsonObject.put("description", edt_about_team.getText().toString());
                jsonObject.put("location", edt_location.getText().toString());
                jsonObject.put("players", "113");
                jsonObject.put("lat", latitude);
                jsonObject.put("lng", longitude);
                //http://sfscoring.betasportzfever.com/createTeamAvatar
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATE_TEAM_AVTAR;
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
                HeaderViewManager.getInstance().setProgressLoader(false, false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
//                    JSONArray data = jObject.getJSONArray("data");
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    context.onBackPressed();

                } else {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        Toast.makeText(context, getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

