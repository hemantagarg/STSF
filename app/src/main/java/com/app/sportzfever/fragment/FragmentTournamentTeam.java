package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterTournamentTeam;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelTournamentTeam;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentTournamentTeam extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterTournamentTeam adapterTournamentTeam;
    private ModelTournamentTeam modelTournamentTeam;
    private ArrayList<ModelTournamentTeam> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";

    public static FragmentTournamentTeam fragment_teamJoin_request;
    private final String TAG = FragmentTournamentTeam.class.getSimpleName();
    private String id = "";

    public static FragmentTournamentTeam getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentTournamentTeam();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_tornament_team, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager = new GridLayoutManager(context, 2);

        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        getBundle();
        setlistener();

        getServicelistRefresh();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }


    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getServicelistRefresh();
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            Fragment_Team_Details fragmentAvtar_details = new Fragment_Team_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", arrayList.get(position).getTeamId());
            fragmentAvtar_details.setArguments(bundle);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);
        }

    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLTOURNAMNENTTEAM + id + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

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
                if (context!=null && isAdded()) {
                    getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
                }
                Dashboard.getInstance().setProgressLoader(false);

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    //  maxlistLength = jObject.getString("total");


                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelTournamentTeam = new ModelTournamentTeam();

                        modelTournamentTeam.setTeamId(jo.getString("teamId"));

                        modelTournamentTeam.setTeamName(jo.getString("teamName"));
                        modelTournamentTeam.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        modelTournamentTeam.setRowType(1);


                        arrayList.add(modelTournamentTeam);
                    }


                    adapterTournamentTeam = new AdapterTournamentTeam(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTournamentTeam);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Team found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Team found");

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    JSONArray jtaemown = data.getJSONArray("teamThatIOwner");
                    //  maxlistLength = jObject.getString("total");


                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = jtaemown.getJSONObject(i);

                        modelTournamentTeam = new ModelTournamentTeam();


                        modelTournamentTeam.setTeamId(jo.getString("teamId"));

                        modelTournamentTeam.setTeamName(jo.getString("teamName"));
                        modelTournamentTeam.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        modelTournamentTeam.setRowType(1);

                 /*       JSONObject j1 = jo.getJSONObject("matchDate");

                        modelPastMatches.setTime(j1.getString("time"));
                        modelPastMatches.setDate(j1.getString("date"));
                        modelPastMatches.setYear(j1.getString("year"));
                        modelPastMatches.setMonthName(j1.getString("monthName"));
                        modelPastMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelPastMatches.setRowType(1);*/

                        arrayList.add(modelTournamentTeam);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

                    adapterTournamentTeam.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterTournamentTeam.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
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

