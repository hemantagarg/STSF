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
import com.app.sportzfever.adapter.AdapterTournamentAlbums;
import com.app.sportzfever.adapter.AdapterTournamentPointTable;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAllTournamentPointTables;
import com.app.sportzfever.models.ModelTournamentAlbums;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentTournamentPoints extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;

    private AdapterTournamentPointTable adapterTournamentPointTable;
    private ModelAllTournamentPointTables modelAllTournamentPointTables;
    private ArrayList<ModelAllTournamentPointTables> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";

    public static FragmentTournamentPoints fragment_teamJoin_request;
    private final String TAG = FragmentTournamentPoints.class.getSimpleName();

    public static FragmentTournamentPoints getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentTournamentPoints();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view_about = inflater.inflate(R.layout.fragment_iadmin, container, false);
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
        setlistener();

        getServicelistRefresh();
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


    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLTOURNAMNENTPOINTTABLES + 3 + "/" +AppUtils.getAuthToken(context);
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

                    JSONArray jobj=jObject.getJSONArray("data");
                    JSONArray data = jObject.getJSONArray("data");


                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelAllTournamentPointTables = new ModelAllTournamentPointTables();

                        modelAllTournamentPointTables.setId(jo.getString("id"));

                        modelAllTournamentPointTables.setTeamName(jo.getString("teamName"));
                        modelAllTournamentPointTables.setMatches(jo.getString("matches"));
                        modelAllTournamentPointTables.setWon(jo.getString("won"));
                        modelAllTournamentPointTables.setLost(jo.getString("lost"));
                        modelAllTournamentPointTables.setPoints(jo.getString("points"));
                        modelAllTournamentPointTables.setNetRunRate(jo.getString("netRunRate"));
                        modelAllTournamentPointTables.setRowType(1);



                        arrayList.add(modelAllTournamentPointTables);
                    }


                    adapterTournamentPointTable = new AdapterTournamentPointTable(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTournamentPointTable);

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

