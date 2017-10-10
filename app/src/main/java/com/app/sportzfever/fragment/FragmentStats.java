package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterAvtarIAdmin;
import com.app.sportzfever.adapter.AdapterStats;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAvtarMyTeam;
import com.app.sportzfever.models.ModelStats;
import com.app.sportzfever.utils.AppUtils;
import com.google.android.gms.maps.model.Dash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentStats extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterStats adapterStats;
    private ModelStats modelStats;
    private ArrayList<ModelStats> arrayList;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private Button btn_batting, btn_bowling, btn_fielding;
    private TextView text_nodata;
    private String maxlistLength = "";

    public static FragmentStats fragment_teamJoin_request;
    private final String TAG = FragmentStats.class.getSimpleName();

    public static FragmentStats getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentStats();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragment_stats, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  Dashboard.getInstance().manageHeaderVisibitlity(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        btn_fielding = (Button) view.findViewById(R.id.btn_fielding);
        btn_bowling = (Button) view.findViewById(R.id.btn_bowling);
        btn_batting = (Button) view.findViewById(R.id.btn_batting);
        arrayList = new ArrayList<>();
        setlistener();

        getServicelistRefresh();
    }

    private void setlistener() {

        btn_batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
            }
        });
        btn_bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
            }
        });
        btn_fielding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_fielding.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_selected));
                btn_bowling.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_batting.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_bg_unselected));
                btn_fielding.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn_batting.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));
                btn_bowling.setTextColor(ContextCompat.getColor(context, R.color.button_bg_color));

            }
        });

    }

    @Override
    public void onItemClickListener(int position, int flag) {


    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.STATS + AppUtils.getUserId(context) + "/" + AppUtils.getAuthToken(context);
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
                Dashboard.getInstance().setProgressLoader(false);

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");

                    //  maxlistLength = jObject.getString("total");
                    JSONObject jbatsman = data.getJSONObject("batsman");

                    arrayList.clear();

                    modelStats = new ModelStats();

                    modelStats.setTotalMatch(data.getString("totalMatch"));
                    modelStats.setTotalStumping(data.getString("totalStumping"));
                    modelStats.setTotalCatches(data.getString("totalCatches"));
                    modelStats.setTotalInnings(jbatsman.getString("totalInnings"));
                    modelStats.setTotalRuns(jbatsman.getString("totalRuns"));
                    modelStats.setHighestScore(jbatsman.getString("highestScore"));
                    modelStats.setStrikeRate(jbatsman.getString("strikeRate"));
                    modelStats.setTotal100(jbatsman.getString("total100"));
                    modelStats.setTotal50(jbatsman.getString("total50"));
                    modelStats.setTotalFours(jbatsman.getString("totalFours"));
                    modelStats.setTotalSixes(jbatsman.getString("totalSixes"));

                    modelStats.setRowType(1);

                    arrayList.add(modelStats);

                    adapterStats = new AdapterStats(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterStats);

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Team found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Team found");

                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    JSONArray jtaemown = data.getJSONArray("teamThatIOwner");
                    //  maxlistLength = jObject.getString("total");


                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = jtaemown.getJSONObject(i);

                        modelStats = new ModelStats();

                      /*  modelAvtarMyTeam.setTeamId(jo.getString("teamId"));
                        modelAvtarMyTeam.setOwner(jo.getString("owner"));
                        modelAvtarMyTeam.setCaptain(jo.getString("captain"));
                        modelAvtarMyTeam.setLocation(jo.getString("location"));*/
                        modelStats.setRowType(1);

                 /*       JSONObject j1 = jo.getJSONObject("matchDate");

                        modelPastMatches.setTime(j1.getString("time"));
                        modelPastMatches.setDate(j1.getString("date"));
                        modelPastMatches.setYear(j1.getString("year"));
                        modelPastMatches.setMonthName(j1.getString("monthName"));
                        modelPastMatches.setShortMonthName(j1.getString("ShortMonthName"));
                        modelPastMatches.setRowType(1);*/

                        arrayList.add(modelStats);
                    }/* for (int i = 0; i < eventtime.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        upcomingEvent = new UpcomingEvent();

                        upcomingEvent.setShortDayName(jo.getString("shortDayName"));





                        upcomingEvent.setRowType(1);

                        arrayList.add(upcomingEvent);
                    }*/

                    adapterStats.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterStats.notifyDataSetChanged();
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

