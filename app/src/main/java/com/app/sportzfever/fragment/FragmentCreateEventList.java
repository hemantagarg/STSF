package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.PickLocation;
import com.app.sportzfever.adapter.AdapterCreateTeamRoster;
import com.app.sportzfever.adapter.AdapterUpcomingTournamentEvent;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSportTeamList;
import com.app.sportzfever.models.UpcomingEvent;
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
public class FragmentCreateEventList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    View mView;
    private RecyclerView list_request, event_rosterlist;
    private Bundle b;
    private Activity context;
    private AdapterUpcomingTournamentEvent adapterUpcomingEvent;
    private UpcomingEvent upcomingEvent;
    private ArrayList<UpcomingEvent> arrayList;
    private AdapterCreateTeamRoster adapterCreateTeamRoster;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayListRoster;
    private ImageView image_map;
    private TextView mTvToDate, mTvTime, mTvToDateend, mTvTimeend, text_overs, text_matchdesc;
    private String teamid = "", teamavtarid = "";
    public static FragmentCreateEventList fragment_teamJoin_request;
    private final String TAG = FragmentCreateEventList.class.getSimpleName();
    private boolean isTeamOwnerOrCaptain = false;
    private ArrayList<String> listEventType = new ArrayList<>();
    private ArrayList<String> listMatchType = new ArrayList<>();
    private ArrayAdapter<String> adapterShare, adapterMatchType, adapterteam;
    private Spinner spinnerShareWith, spinner_matchtype, spinner_selectteam;
    private CheckBox checkboxend_date;
    private LinearLayout linear_matchPublic, linear_enddatetime;
    private RelativeLayout rl_main;
    String latitude = "0.0", longitude = "0.0", selectedTeamId = "";
    private EditText edt_eventtitle, mEdtdetails, edt_no_overs, edt_no_players;
    private TextView mEdtlocation, text_invite, text_selectTeam;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button btn_save;
    private String EventType = "";

    public static FragmentCreateEventList getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentCreateEventList();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_create_event, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        arrayListRoster = new ArrayList<>();
        b = getArguments();

        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        getBundle();
        manageHeaderView();
        setlistener();
        // getServicelistRefresh();
        getServicelistRoster();
    }

    private void init(View view) {
        list_request = (RecyclerView) view.findViewById(R.id.floating_create_event);
        event_rosterlist = (RecyclerView) view.findViewById(R.id.event_rosterlist);
        event_rosterlist.setLayoutManager(new LinearLayoutManager(context));
        event_rosterlist.setNestedScrollingEnabled(false);
        spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);
        spinner_matchtype = (Spinner) view.findViewById(R.id.spinner_matchtype);
        spinner_selectteam = (Spinner) view.findViewById(R.id.spinner_selectteam);
        checkboxend_date = (CheckBox) view.findViewById(R.id.checkboxend_date);
        mTvToDate = (TextView) view.findViewById(R.id.mTvToDate);
        mTvTime = (TextView) view.findViewById(R.id.mTvTime);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        mTvTimeend = (TextView) view.findViewById(R.id.mTvTimeend);
        image_map = (ImageView) view.findViewById(R.id.image_map);
        mTvToDateend = (TextView) view.findViewById(R.id.mTvToDateend);
        text_overs = (TextView) view.findViewById(R.id.text_overs);
        text_matchdesc = (TextView) view.findViewById(R.id.text_matchdesc);
        linear_matchPublic = (LinearLayout) view.findViewById(R.id.linear_matchPublic);
        linear_enddatetime = (LinearLayout) view.findViewById(R.id.linear_enddatetime);
        edt_eventtitle = (EditText) view.findViewById(R.id.edt_eventtitle);
        mEdtlocation = (TextView) view.findViewById(R.id.mEdtlocation);
        text_invite = (TextView) view.findViewById(R.id.text_invite);
        text_selectTeam = (TextView) view.findViewById(R.id.text_selectTeam);
        mEdtdetails = (EditText) view.findViewById(R.id.mEdtdetails);
        edt_no_overs = (EditText) view.findViewById(R.id.edt_no_overs);
        edt_no_players = (EditText) view.findViewById(R.id.edt_no_players);
        list_request.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
        //  text_matchdesc.setText(Html.fromHtml(context.getResources().getString(R.string.match_desc)));
        arrayListRoster = new ArrayList<>();
        listEventType.add("Select Event Type");
        listEventType.add("Event (Public)");
        listEventType.add("Meet Up (With In Team)");
        listEventType.add("Practise (With In Team)");
        listEventType.add("Match (Public)");
        adapterShare = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listEventType);
        spinnerShareWith.setAdapter(adapterShare);

        listMatchType.add("Limited over");
        listMatchType.add("Unlimited over");
        adapterMatchType = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listMatchType);
        spinner_matchtype.setAdapter(adapterMatchType);

    }

    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Create Event");
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
            teamid = bundle.getString("teamId");
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
                if (spinnerShareWith.getSelectedItemPosition() == 4) {
                    if (validateMatch()) {
                        makeMatchJsonRequest();
                    }
                } else {
                    if (validateEvent()) {
                        makeEventJsonRequest();
                    }
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
        text_selectTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSearchOpponentTeamList fragmentSearchUserList = new FragmentSearchOpponentTeamList();
                fragmentSearchUserList.setTargetFragment(FragmentCreateEventList.this, AppConstant.FRAGMENT_CODE);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSearchUserList, true);
            }
        });

        checkboxend_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (checkboxend_date.isChecked()) {
                    linear_enddatetime.setVisibility(View.VISIBLE);
                } else {
                    linear_enddatetime.setVisibility(View.GONE);
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


        spinnerShareWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {
                    rl_main.setVisibility(View.GONE);
                    text_matchdesc.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.GONE);
                    linear_matchPublic.setVisibility(View.GONE);
                    edt_eventtitle.setVisibility(View.GONE);
                    event_rosterlist.setVisibility(View.GONE);
                    text_invite.setVisibility(View.GONE);
                } else {
                    text_matchdesc.setVisibility(View.GONE);
                    rl_main.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);
                    if (position == 4) {
                        EventType = AppConstant.MATCH;
                        setHeaderTitle("Create match event");
                        linear_matchPublic.setVisibility(View.VISIBLE);
                        edt_eventtitle.setVisibility(View.GONE);
                        event_rosterlist.setVisibility(View.GONE);
                        text_invite.setVisibility(View.GONE);
                    } else {
                        linear_matchPublic.setVisibility(View.GONE);
                        edt_eventtitle.setVisibility(View.VISIBLE);
                        event_rosterlist.setVisibility(View.VISIBLE);
                        text_invite.setVisibility(View.VISIBLE);
                        if (position == 1) {
                            EventType = AppConstant.EVENT;
                            setHeaderTitle("Create event");
                        } else if (position == 2) {
                            EventType = AppConstant.MEET_UP;
                            setHeaderTitle("Create meetup event");

                        } else if (position == 3) {
                            setHeaderTitle("Create practise event");
                            EventType = AppConstant.PRACTISE;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_matchtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    text_overs.setVisibility(View.VISIBLE);
                    edt_no_overs.setVisibility(View.VISIBLE);
                } else {
                    text_overs.setVisibility(View.GONE);
                    edt_no_overs.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mTvToDateend.setOnClickListener(new View.OnClickListener() {
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
                        mTvToDateend.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        mTvTimeend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTvTimeend.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    private void setHeaderTitle(String title) {
        HeaderViewManager.getInstance().setHeading(true, title);
    }

    private JSONObject makeMatchJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("team1Id", teamid);
            jsonObject.put("team2Id", selectedTeamId);

            jsonObject.put("startDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            if (checkboxend_date.isChecked() && !mTvToDateend.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("endDate", mTvToDateend.getText().toString() + " " + mTvTimeend.getText().toString());
            } else {
                jsonObject.put("endDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            }
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

    private JSONObject makeEventJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray usersAndAvatars = new JSONArray();
            for (int i = 0; i < arrayListRoster.size(); i++) {

                JSONObject jo = new JSONObject();
                jo.put("avatarId", arrayListRoster.get(i).getAvtarId());
                jo.put("userId", arrayListRoster.get(i).getUserId());
                usersAndAvatars.put(jo);
            }

            jsonObject.put("startDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            if (checkboxend_date.isChecked() && !mTvToDateend.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("endDate", mTvToDateend.getText().toString() + " " + mTvTimeend.getText().toString());
            } else {
                jsonObject.put("endDate", mTvToDate.getText().toString() + " " + mTvTime.getText().toString());
            }
            jsonObject.put("userId", AppUtils.getUserId(context));
            jsonObject.put("teamId", teamid);
            jsonObject.put("title", edt_eventtitle.getText().toString());
            jsonObject.put("description", mEdtdetails.getText().toString());
            jsonObject.put("location", mEdtlocation.getText().toString());
            jsonObject.put("eventType", EventType);
            jsonObject.put("isActive", "0");
            jsonObject.put("lat", latitude);
            jsonObject.put("lng", longitude);
            jsonObject.put("usersAndAvatars", usersAndAvatars);
            sentInvite(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void sentInvite(JSONObject jsonObject) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATEEVENT;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private boolean validateMatch() {
        boolean isValid = false;
        String title = text_selectTeam.getText().toString();
        String date = mTvToDate.getText().toString();
        String overs = edt_no_overs.getText().toString();
        String players = edt_no_players.getText().toString();
        String time = mTvTime.getText().toString();
        String location = mEdtlocation.getText().toString();
        if (!date.equalsIgnoreCase("") && !title.equalsIgnoreCase("") && !overs.equalsIgnoreCase("") && !players.equalsIgnoreCase("")
                && !time.equalsIgnoreCase("") && !location.equalsIgnoreCase("")) {
            if (teamid.equals(selectedTeamId)) {
                isValid = false;
                Toast.makeText(context, "Please select another opponent team", Toast.LENGTH_SHORT).show();
            } else {
                isValid = true;
            }
        } else {
            if (title.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select opponent team", Toast.LENGTH_SHORT).show();
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
            }
        }

        return isValid;
    }


    private boolean validateEvent() {
        boolean isValid = false;
        String title = edt_eventtitle.getText().toString();
        String date = mTvToDate.getText().toString();
        String time = mTvTime.getText().toString();
        String location = mEdtlocation.getText().toString();
        if (!date.equalsIgnoreCase("") && !title.equalsIgnoreCase("") && !time.equalsIgnoreCase("") && !location.equalsIgnoreCase("")) {
            isValid = true;
        } else {
            if (title.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please enter event title", Toast.LENGTH_SHORT).show();
            } else if (date.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select date", Toast.LENGTH_SHORT).show();
            } else if (time.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select time", Toast.LENGTH_SHORT).show();
            } else if (location.equalsIgnoreCase("")) {
                Toast.makeText(context, "Please select location", Toast.LENGTH_SHORT).show();
            }
        }

        return isValid;
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 2) {
            showConfirmationDialog(position);
        }
    }

    private void showConfirmationDialog(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        alertDialog.setTitle("Remove Player");

        alertDialog.setMessage("Are you sure you want to Remove this Player?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        arrayListRoster.remove(position);
                        adapterCreateTeamRoster.notifyDataSetChanged();
                    }

                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    private void getServicelistRoster() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLSPORTTEAMDETIAL + teamid + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(11, context, this).getqueryJsonbjectNoProgress(url, null, Request.Method.GET);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("color upload 88888", +requestCode + "");
        if (requestCode == 511 && resultCode == 512) {
            mEdtlocation.setText(data.getStringExtra("location"));
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstant.FRAGMENT_CODE) {
                String id = data.getStringExtra("id");
                String name = data.getStringExtra("name");
                selectedTeamId = (id);
                text_selectTeam.setText(name);
            }
        }
    }

    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
                    context.onBackPressed();

                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } else if (position == 11) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    JSONObject data = jObject.getJSONObject("data");
                    JSONArray teamProfile = data.getJSONArray("teamProfile");
                    arrayList.clear();
                    for (int i = 0; i < teamProfile.length(); i++) {

                        JSONObject jo = teamProfile.getJSONObject(i);
                        modelSportTeamList = new ModelSportTeamList();

                        modelSportTeamList.setPlayerName(jo.getString("playerName"));
                        modelSportTeamList.setAvtarId(jo.getString("avatar"));
                        modelSportTeamList.setUserId(jo.getString("userId"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        modelSportTeamList.setRequestStatus(jo.getString("requestStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                        modelSportTeamList.setRowType(1);
                        if (modelSportTeamList.getRequestStatus().equalsIgnoreCase(AppConstant.ACCEPTED)) {
                            arrayListRoster.add(modelSportTeamList);
                        }
                    }
                    adapterCreateTeamRoster = new AdapterCreateTeamRoster(getActivity(), this, arrayListRoster);
                    event_rosterlist.setAdapter(adapterCreateTeamRoster);
                } else {
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

