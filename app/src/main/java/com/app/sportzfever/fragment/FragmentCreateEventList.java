package com.app.sportzfever.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.app.sportzfever.adapter.AdapterTeamRoster;
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

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentCreateEventList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    View mView;

    private RecyclerView list_request, event_rosterlist;
    private Bundle b;
    private Context context;
    private AdapterUpcomingTournamentEvent adapterUpcomingEvent;
    private UpcomingEvent upcomingEvent;
    private ArrayList<UpcomingEvent> arrayList;
    private AdapterTeamRoster adapterSportTeamList;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayListRoster;
    private ImageView image_map;
    private TextView mTvToDate, mTvTime, mTvToDateend, mTvTimeend, text_selectteam, text_overs;
    private String teamid = "", teamavtarid = "";
    public static FragmentCreateEventList fragment_teamJoin_request;
    private final String TAG = FragmentCreateEventList.class.getSimpleName();
    private boolean isTeamOwnerOrCaptain = false;
    private ArrayList<String> listEventType = new ArrayList<>();
    private ArrayList<String> listMatchType = new ArrayList<>();
    private ArrayAdapter<String> adapterShare, adapterMatchType;
    private Spinner spinnerShareWith, spinner_matchtype;
    private CheckBox checkboxend_date;
    private LinearLayout linear_matchPublic, linear_enddatetime;
    private RelativeLayout rl_main;
    String latitude = "0.0", longitude = "0.0";
    private EditText edt_eventtitle, mEdtlocation, mEdtdetails, edt_no_overs, edt_no_players;
    private int mYear, mMonth, mDay, mHour, mMinute;

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
        getServicelistRefresh();
        getServicelistRoster();
    }

    private void init(View view) {
        list_request = (RecyclerView) view.findViewById(R.id.floating_create_event);
        event_rosterlist = (RecyclerView) view.findViewById(R.id.event_rosterlist);
        event_rosterlist.setLayoutManager(new LinearLayoutManager(context));
        spinnerShareWith = (Spinner) view.findViewById(R.id.spinnerShareWith);
        spinner_matchtype = (Spinner) view.findViewById(R.id.spinner_matchtype);
        checkboxend_date = (CheckBox) view.findViewById(R.id.checkboxend_date);
        mTvToDate = (TextView) view.findViewById(R.id.mTvToDate);
        mTvTime = (TextView) view.findViewById(R.id.mTvTime);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        mTvTimeend = (TextView) view.findViewById(R.id.mTvTimeend);
        image_map = (ImageView) view.findViewById(R.id.image_map);
        mTvToDateend = (TextView) view.findViewById(R.id.mTvToDateend);
        text_selectteam = (TextView) view.findViewById(R.id.text_selectteam);
        text_overs = (TextView) view.findViewById(R.id.text_overs);
        linear_matchPublic = (LinearLayout) view.findViewById(R.id.linear_matchPublic);
        linear_enddatetime = (LinearLayout) view.findViewById(R.id.linear_enddatetime);
        edt_eventtitle = (EditText) view.findViewById(R.id.edt_eventtitle);
        mEdtlocation = (EditText) view.findViewById(R.id.mEdtlocation);
        mEdtdetails = (EditText) view.findViewById(R.id.mEdtdetails);
        edt_no_overs = (EditText) view.findViewById(R.id.edt_no_overs);
        edt_no_players = (EditText) view.findViewById(R.id.edt_no_players);
        list_request.setLayoutManager(new LinearLayoutManager(context));
        arrayList = new ArrayList<>();
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
                        String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                                + "-" + String.valueOf(dayOfMonth);
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

        spinnerShareWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {
                    rl_main.setVisibility(View.GONE);
                } else {
                    rl_main.setVisibility(View.VISIBLE);
                    if (position == 4) {
                        linear_matchPublic.setVisibility(View.VISIBLE);
                        edt_eventtitle.setVisibility(View.GONE);
                        event_rosterlist.setVisibility(View.GONE);
                    } else {
                        linear_matchPublic.setVisibility(View.GONE);
                        edt_eventtitle.setVisibility(View.VISIBLE);
                        event_rosterlist.setVisibility(View.VISIBLE);
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
                        String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                                + "-" + String.valueOf(dayOfMonth);
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

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            if (arrayList.get(position).getEventType().equalsIgnoreCase("MATCH")) {
                if (isTeamOwnerOrCaptain) {
                    FragmentCheckPlayerAvailability fragment_postFeed = new FragmentCheckPlayerAvailability();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", teamid);
                    bundle.putString("eventId", arrayList.get(position).getId());
                    bundle.putString("playersCount", arrayList.get(position).getPlayersCount());
                    fragment_postFeed.setArguments(bundle);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragment_postFeed, true);
                } else {
                    if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_ENDED)) {
                        Fragment_PastMatch_Details fragmentupcomingdetals = new Fragment_PastMatch_Details();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                        FragmentUpcomingMatchDetails fragmentupcomingdetals = new FragmentUpcomingMatchDetails();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    } else if (arrayList.get(position).getMatchStatus().equalsIgnoreCase(AppConstant.MATCHSTATUS_STARTED)) {
                        Fragment_LiveMatch_Details fragmentupcomingdetals = new Fragment_LiveMatch_Details();
                        Bundle b = new Bundle();
                        b.putString("eventId", arrayList.get(position).getId());
                        fragmentupcomingdetals.setArguments(b);
                        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentupcomingdetals, true);
                    }
                }
            }
        }
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

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_UPCOMINGEVENTS + AppUtils.getUserId(context) + "/" + teamid + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

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

    }

    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                Dashboard.getInstance().setProgressLoader(false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    //  data = jObject.getString("total");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setId(jo.getString("id"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        upcomingEvent.setLocation(jo.getString("location"));
                        upcomingEvent.setEventType(jo.getString("eventType"));
                        upcomingEvent.setTeam1ProfilePicture(jo.getString("team1ProfilePicture"));
                        upcomingEvent.setTeam2ProfilePicture(jo.getString("team2ProfilePicture"));
                        upcomingEvent.setTeam1Name(jo.getString("team1Name"));
                        upcomingEvent.setPlayersCount(jo.getString("playersCount"));
                        upcomingEvent.setTeam2Name(jo.getString("team2Name"));
                        upcomingEvent.setTitle(jo.getString("title"));
                        if (jo.has("matchStatus")) {
                            upcomingEvent.setMatchStatus(jo.getString("matchStatus"));
                        }
                        JSONObject j1 = jo.getJSONObject("startDate");

                        upcomingEvent.setDayName(j1.getString("dayName"));
                        upcomingEvent.setMonthName(j1.getString("monthName"));
                        upcomingEvent.setDate(j1.getString("date"));
                        upcomingEvent.setTime(j1.getString("time"));
                        upcomingEvent.setRowType(1);
                        arrayList.add(upcomingEvent);
                    }
                    adapterUpcomingEvent = new AdapterUpcomingTournamentEvent(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterUpcomingEvent);

                } else {
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
                        modelSportTeamList.setAvatar(jo.getString("avatar"));
                        modelSportTeamList.setAvatarName(jo.getString("avatarName"));
                        modelSportTeamList.setJerseyNumber(jo.getString("jerseyNumber"));
                        modelSportTeamList.setSpeciality(jo.getString("speciality"));
                        modelSportTeamList.setRequestStatus(jo.getString("requestStatus"));
                        modelSportTeamList.setProfilePicture(jo.getString("profilePicture"));
                        modelSportTeamList.setRowType(1);

                        arrayListRoster.add(modelSportTeamList);
                    }
                    adapterSportTeamList = new AdapterTeamRoster(getActivity(), this, arrayListRoster);
                    event_rosterlist.setAdapter(adapterSportTeamList);
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

