package com.app.sportzfever.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_EvenDetail extends BaseFragment implements ApiResponse, OnMapReadyCallback {

    private Bundle b;
    private Activity context;
    View view_about;
    private ImageView image_team, image_reject, image_accept;
    private TextView textTeamname, textAreyouGoing, text_date, text_time, text_location,
            text_createdby, text_person_going, textDesc, texttitle;
    private RelativeLayout rlAccepReject, teamDetail, rl_map;
    public static Fragment_EvenDetail fragment_team;
    private final String TAG = Fragment_EvenDetail.class.getSimpleName();
    private String eventId = "", teamId = "", currentTab = "";
    private SupportMapFragment supportMapFragment = null;
    private GoogleMap googleMap;
    private JSONObject jsonData;
    private Double mLat = 0.0, mLong = 0.0;

    public static Fragment_EvenDetail getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_EvenDetail();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_event_detail, container, false);
        context = getActivity();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manageHeaderView();
        init(view);
        getBundle();
        getEventDetail();
        setListener();
        AppConstant.ISFEEDNEEDTOREFRESH = false;
    }

    private void init(View view) {

        image_team = (ImageView) view.findViewById(R.id.image_team);
        image_reject = (ImageView) view.findViewById(R.id.image_reject);
        image_accept = (ImageView) view.findViewById(R.id.image_accept);
        rlAccepReject = (RelativeLayout) view.findViewById(R.id.rlAccepReject);
        teamDetail = (RelativeLayout) view.findViewById(R.id.teamDetail);
        rl_map = (RelativeLayout) view.findViewById(R.id.rl_map);
        textTeamname = (TextView) view.findViewById(R.id.textTeamname);
        text_date = (TextView) view.findViewById(R.id.text_date);
        textAreyouGoing = (TextView) view.findViewById(R.id.textAreyouGoing);
        text_time = (TextView) view.findViewById(R.id.text_time);
        textDesc = (TextView) view.findViewById(R.id.textDesc);
        texttitle = (TextView) view.findViewById(R.id.texttitle);
        text_location = (TextView) view.findViewById(R.id.text_location);
        text_createdby = (TextView) view.findViewById(R.id.text_createdby);
        text_person_going = (TextView) view.findViewById(R.id.text_person_going);

    }

    private void getBundle() {
        if (b != null) {
            eventId = b.getString("eventId");
            currentTab = b.getString("currentTab");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
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
        HeaderViewManager.getInstance().setHeading(true, "Matches");
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

    private void acceptRejectInvitation(String type) {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/RespondToMatchAndPractiseInvitation/274/ACCEPTED
                String url = JsonApiHelper.BASEURL + JsonApiHelper.RESPONDTOMATCHINVITATION + eventId + "/" + type;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getEventDetail() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    https://sfscoring.betasportzfever.com/getEventById/152/fc0120111c7dc3d54bb477a7a89542a6
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_EVENTBYID + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setListener() {
        teamDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Team_Details fragmentAvtar_details = new Fragment_Team_Details();
                Bundle bundle = new Bundle();
                bundle.putString("id", teamId);
                fragmentAvtar_details.setArguments(bundle);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);
            }
        });
        image_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRejectInvitation(AppConstant.ACCEPTED);
            }
        });
        image_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRejectInvitation(AppConstant.REJECTED);
            }
        });

        text_person_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentEventRsvpDetail fragmentEventRsvpDetail = new FragmentEventRsvpDetail();
                Bundle bundle = new Bundle();
                bundle.putString("data", jsonData.toString());
                fragmentEventRsvpDetail.setArguments(bundle);
                Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentEventRsvpDetail, true);
            }
        });

    }


    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (method == 1) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    JSONObject data = jObject.getJSONObject("data");
                    jsonData = data;
                    teamId = data.getString("team1");
                    texttitle.setText(data.getString("title"));
                    textDesc.setText(data.getString("description"));
                    text_location.setText(data.getString("location"));
                    text_createdby.setText("Created by " + data.getString("eventCreateBy"));
                    text_person_going.setText(data.getString("going") + " person are going");
                    textTeamname.setText(data.getString("avatarName"));

                    if (!data.getString("avatarProfilePicture").equalsIgnoreCase("")) {
                        Picasso.with(context).load(data.getString("avatarProfilePicture"))
                                .transform(new CircleTransform()).into(image_team);
                    }

                    JSONObject startDate = data.getJSONObject("startDate");
                    JSONObject endDate = data.getJSONObject("endDate");

                    String start = startDate.getString("date") + "-" + startDate.getString("month") + "-" + startDate.getString("year");
                    String end = endDate.getString("date") + "-" + endDate.getString("month") + "-" + endDate.getString("year");

                    if (start.equals(end)) {
                        text_date.setText(startDate.getString("dayName") + ", " + startDate.getString("date") + "" + startDate.getString("monthName"));
                    } else {
                        text_date.setText(startDate.getString("dayName") + ", " + startDate.getString("date")
                                + "" + startDate.getString("monthName") + " - " + endDate.getString("dayName") + ", " + endDate.getString("date")
                                + "" + endDate.getString("monthName"));
                    }

                    text_time.setText(startDate.getString("time") + " - " + endDate.getString("time"));

                    String isUserInvite = data.getString("isUserInvite");
                    String isUserInviteStatus = data.getString("isUserInviteStatus");

                    if (isUserInvite.equals("0")) {
                        text_location.setText("Location only visible to members");
                        rlAccepReject.setVisibility(View.GONE);
                        texttitle.setVisibility(View.GONE);
                        text_createdby.setVisibility(View.GONE);
                        rl_map.setVisibility(View.GONE);
                        textDesc.setText("Details are only visible to members");

                    } else {
                        if (isUserInvite.equals("1") && isUserInviteStatus.equals(AppConstant.GOING)) {
                            rlAccepReject.setVisibility(View.GONE);
                        } else if (isUserInvite.equals("1") && isUserInviteStatus.equals(AppConstant.PENDING)) {
                            rlAccepReject.setVisibility(View.VISIBLE);
                        }
                    }

                    String latitude = data.getString("lattitude");
                    String longitude = data.getString("longitude");
                    mLat = Double.parseDouble(latitude);
                    mLong = Double.parseDouble(longitude);
                    initilizeMap();
                }
            } else if (method == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    context.onBackPressed();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void initilizeMap() {
        try {
            supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPostFail(int method, String response) {

    }

    @Override
    public void onMapReady(GoogleMap Map) {
        googleMap = Map;
        if (googleMap != null) {
            // Changing map type
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);

            Log.e("latitude", "**" + mLat + "**" + mLong);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mLat, mLong)).zoom(13).build();

            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

            MarkerOptions marker = new MarkerOptions().position(new LatLng(
                    mLat, mLong));
            marker.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.blumap));
            googleMap.addMarker(marker);
        }
    }
}

