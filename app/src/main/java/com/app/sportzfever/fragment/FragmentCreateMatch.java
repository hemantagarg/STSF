package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.PickLocation;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentCreateMatch extends BaseFragment implements ApiResponse {

    View mView;
    private Bundle b;
    private Activity context;
    private ImageView image_map;
    private TextView mTvToDate, mTvTime, text_overs, text_matchdesc;
    private String teamid = "";
    public static FragmentCreateMatch fragment_teamJoin_request;
    private final String TAG = FragmentCreateMatch.class.getSimpleName();
    String latitude = "0.0", longitude = "0.0", selectedTeam2Id = "", selectedTeam1Id;
    private EditText mEdtdetails, edt_no_overs, edt_no_players;
    private TextView mEdtlocation, text_selectTeam, text_selectTeam2;
    private Button btn_save;
    private JSONArray teamJson;
    private ArrayList<String> listTeamName = new ArrayList<>();
    private ArrayList<String> listTeamId = new ArrayList<>();
    private Spinner spinnerSelectTeam;
    private ArrayAdapter<String> adapterTeam;

    private String EventType = "";

    public static FragmentCreateMatch getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentCreateMatch();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_create_match, container, false);
        context = getActivity();
        b = getArguments();

        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        getBundle();
        manageHeaderView();
        getAdminTeam();
        setlistener();
    }

    private void init(View view) {
        mTvToDate = (TextView) view.findViewById(R.id.mTvToDate);
        mTvTime = (TextView) view.findViewById(R.id.mTvTime);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        image_map = (ImageView) view.findViewById(R.id.image_map);
        text_overs = (TextView) view.findViewById(R.id.text_overs);
        text_matchdesc = (TextView) view.findViewById(R.id.text_matchdesc);
        mEdtlocation = (TextView) view.findViewById(R.id.mEdtlocation);
        text_selectTeam2 = (TextView) view.findViewById(R.id.text_selectTeam2);
        text_selectTeam = (TextView) view.findViewById(R.id.text_selectTeam);
        mEdtdetails = (EditText) view.findViewById(R.id.mEdtdetails);
        edt_no_overs = (EditText) view.findViewById(R.id.edt_no_overs);
        edt_no_players = (EditText) view.findViewById(R.id.edt_no_players);
        spinnerSelectTeam = (Spinner) view.findViewById(R.id.spinnerSelectTeam);
    }

    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Create Match");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);
        HeaderViewManager.getInstance().setLogoView(false);
        HeaderViewManager.getInstance().setProgressLoader(false, false);

    }

    private HeaderViewClickListener manageHeaderClick() {
        return new HeaderViewClickListener() {
            @Override
            public void onClickOfHeaderLeftView() {
                AppUtils.showLog(TAG, "onClickOfHeaderLeftView");
                getActivity().onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setlistener() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMatch()) {
                    makeMatchJsonRequest();
                }
            }
        });
        mTvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth;
                        mTvToDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();


            }
        });
        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTvTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        spinnerSelectTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position != 0) {
                    if (position == adapterTeam.getCount() - 1) {
                        selectedTeam1Id = "";
                        Intent intent = new Intent(context, Fragment_CreateTeam.class);
                        intent.putExtra("isBack", true);
                        startActivityForResult(intent, 21);
                    } else {
                        selectedTeam1Id = listTeamId.get(position);
                    }
                } else {
                    selectedTeam1Id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        text_selectTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("selectedId", "**" + selectedTeam1Id);
                FragmentSearchTeam fragmentSearchUserList = new FragmentSearchTeam();
                Bundle b = new Bundle();
                b.putString("json", teamJson.toString());
                b.putString("selectedId", selectedTeam1Id);
                fragmentSearchUserList.setArguments(b);
                fragmentSearchUserList.setTargetFragment(FragmentCreateMatch.this, AppConstant.FRAGMENT_CODE2);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSearchUserList, true);
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

        mEdtlocation.setOnClickListener(new View.OnClickListener() {
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


    private void createMatchEvent(JSONObject jsonObject) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATEVENTMATCH;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getAdminTeam() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_ADMIN_TEAM + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, null, Request.Method.GET);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validateMatch() {
        boolean isValid = false;
        String team2name = text_selectTeam2.getText().toString();
        String date = mTvToDate.getText().toString();
        String overs = edt_no_overs.getText().toString();
        String players = edt_no_players.getText().toString();
        String time = mTvTime.getText().toString();
        String location = mEdtlocation.getText().toString();
        String desc = mEdtdetails.getText().toString();
        if (!date.equalsIgnoreCase("") && !selectedTeam1Id.equalsIgnoreCase("") && !selectedTeam2Id.equalsIgnoreCase("") && !overs.equalsIgnoreCase("") && !players.equalsIgnoreCase("")
                && !time.equalsIgnoreCase("") && !desc.equalsIgnoreCase("") && !location.equalsIgnoreCase("")
                ) {
            if (selectedTeam1Id.equals(selectedTeam2Id)) {
                isValid = false;
                Toast.makeText(context, "Please select another opponent team", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else {
            if (selectedTeam1Id.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select first team", Toast.LENGTH_SHORT).show();
            } else if (selectedTeam2Id.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select second team", Toast.LENGTH_SHORT).show();
            } else if (overs.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please enter no of overs", Toast.LENGTH_SHORT).show();
            } else if (players.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please enter no of players", Toast.LENGTH_SHORT).show();
            } else if (date.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select date", Toast.LENGTH_SHORT).show();
            } else if (time.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select time", Toast.LENGTH_SHORT).show();
            } else if (location.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select location", Toast.LENGTH_SHORT).show();
            } else if (desc.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please enter Description", Toast.LENGTH_SHORT).show();
            }
        }

        return isValid;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", +requestCode + "");
        if (requestCode == 511 && resultCode == 512) {
            mEdtlocation.setText(data.getStringExtra("location"));
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
        } else if (resultCode == 23) {
            String id = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            selectedTeam2Id = (id);
            text_selectTeam2.setText(name);

        } else if (requestCode == 21 && resultCode == RESULT_OK) {
            String id = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            selectedTeam1Id = (id);
            listTeamName.add(listTeamName.size() - 1, name);
            listTeamId.add(listTeamId.size() - 1, id);
            adapterTeam.notifyDataSetChanged();
        }
    }

    private JSONObject makeMatchJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("team1Id", selectedTeam1Id);
            jsonObject.put("team2Id", selectedTeam2Id);

            jsonObject.put("startDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            jsonObject.put("endDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            jsonObject.put("lat", latitude);
            jsonObject.put("lng", longitude);
            jsonObject.put("userId", AppUtils.getUserId(context));
            jsonObject.put("noOfPlayers", edt_no_players.getText().toString());
            jsonObject.put("noOfOvers", edt_no_overs.getText().toString());
            jsonObject.put("inviteStatus", AppConstant.PENDING);
            jsonObject.put("description", mEdtdetails.getText().toString());
            jsonObject.put("location", mEdtlocation.getText().toString());
            jsonObject.put("isActive", "0");
            jsonObject.put("eventType", EventType);

            createMatchEvent(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    context.onBackPressed();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    teamJson = jObject.getJSONArray("data");
                    listTeamName.add("Choose your Team");
                    listTeamId.add("-1");
                    for (int i = 0; i < teamJson.length(); i++) {
                        JSONObject jsonObject = teamJson.getJSONObject(i);
                        listTeamName.add(jsonObject.getString("teamName"));
                        listTeamId.add(jsonObject.getString("teamId"));
                    }

                    listTeamName.add("Create Team");
                    listTeamId.add("-99");

                    adapterTeam = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listTeamName);
                    spinnerSelectTeam.setAdapter(adapterTeam);

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


    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        if (view.getTag().equalsIgnoreCase("todate")) {
            mTvToDate.setText(date);
        } else {
            mTvToDate.setText(date);
        }
    }
}

