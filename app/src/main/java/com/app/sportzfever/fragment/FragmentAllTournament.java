package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterAlltournament;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAllTournament;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentAllTournament extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;

    private AdapterAlltournament adapterAlltournament;
    private ModelAllTournament modelAllTournament;
    private ArrayList<ModelAllTournament> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private TextView text_nodata;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";

    public static FragmentAllTournament fragment_teamJoin_request;
    private final String TAG = FragmentAllTournament.class.getSimpleName();

    public static FragmentAllTournament getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentAllTournament();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragment_matches, container, false);
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
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        list_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public void onItemClickListener(int position, int flag) {

        FragmentTournament_Details tab2 = new FragmentTournament_Details();
        Bundle b = new Bundle();
        b.putString("id", arrayList.get(position).getId());
        b.putString("name", arrayList.get(position).getName());
        b.putString("date", arrayList.get(position).getDate());
        b.putString("image", arrayList.get(position).getProfilePicture());
        tab2.setArguments(b);
        Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, tab2, true);

    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_ALLTOURNAMENT + AppUtils.getAuthToken(context);

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

                    //  data = jObject.getString("total");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelAllTournament = new ModelAllTournament();
                        modelAllTournament.setId(jo.getString("id"));
                        modelAllTournament.setName(jo.getString("name"));
                        modelAllTournament.setSportName(jo.getString("sportName"));
                        modelAllTournament.setProfilePicture(jo.getString("profilePicture"));
                        modelAllTournament.setSportName(jo.getString("sportName"));

                        JSONObject jo1 = jo.getJSONObject("tournamentStartDate");
                        modelAllTournament.setDatetime(jo1.getString("date") + " " + jo1.getString("ShortMonthName") + " " + jo1.getString("year"));
                        modelAllTournament.setdate(jo1.getString("date") + " " + jo1.getString("ShortMonthName") + " Onwards");
                        modelAllTournament.setRowType(1);
                        arrayList.add(modelAllTournament);
                    }

                    adapterAlltournament = new AdapterAlltournament(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterAlltournament);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No Tournament found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No Tournament found");

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("OK")) {
                    JSONArray data = jObject.getJSONArray("data");
                    JSONObject eventtime = jObject.optJSONObject("startDate");
                    //  maxlistLength = jObject.getString("total");


                    arrayList.removeAll(arrayList);
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelAllTournament = new ModelAllTournament();

                        modelAllTournament.setId(jo.getString("id"));

                        modelAllTournament.setName(jo.getString("name"));
                        modelAllTournament.setSportName(jo.getString("sportName"));


                        JSONObject jo1 = jo.getJSONObject("tournamentStartDate");
                        modelAllTournament.setDatetime(jo1.getString("datetime"));

                        modelAllTournament.setRowType(1);

                        arrayList.add(modelAllTournament);
                    }
                    adapterAlltournament.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterAlltournament.notifyDataSetChanged();
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

