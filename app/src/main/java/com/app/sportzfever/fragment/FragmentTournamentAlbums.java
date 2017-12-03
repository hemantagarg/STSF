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
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelTournamentAlbums;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentTournamentAlbums extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private AdapterTournamentAlbums adapterTournamentAlbums;
    private ModelTournamentAlbums modelTournamentAlbums;
    private ArrayList<ModelTournamentAlbums> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private TextView text_nodata;
    private String maxlistLength = "";

    public static FragmentTournamentAlbums fragment_teamJoin_request;
    private final String TAG = FragmentTournamentAlbums.class.getSimpleName();
    private String id = "";

    public static FragmentTournamentAlbums getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentTournamentAlbums();
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
        text_nodata.setVisibility(View.VISIBLE);
        text_nodata.setText("There are no photos or videos as of now for the tournament.");
        getServicelistRefresh();
        /*getView().findViewById(R.id.progressbar).setVisibility(View.GONE);*/
        setlistener();
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
        FragmentGalleryDetails fragmentGalleryDetails = new FragmentGalleryDetails();
        Bundle b = new Bundle();
        b.putString("galleryid", arrayList.get(position).getAlbumId());
        b.putString("title", arrayList.get(position).getAlbumName());
        fragmentGalleryDetails.setArguments(b);
        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentGalleryDetails, true);
    }


    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //   https://sfscoring.betasportzfever.com/getAlbumMatchTournament/tournament/6/a019834e-8f16-11e7-9931-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ALLTOURNAMNENTIMAGES + id + "/" + AppUtils.getAuthToken(context);
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
                if (context != null && isAdded()) {
                    getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
                }
                Dashboard.getInstance().setProgressLoader(false);

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");

                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        modelTournamentAlbums = new ModelTournamentAlbums();

                        modelTournamentAlbums.setAlbumId(jo.getString("albumId"));
                        modelTournamentAlbums.setAlbumName(jo.getString("albumName"));
                        modelTournamentAlbums.setTotalImage(jo.getString("totalImage"));
                        modelTournamentAlbums.setImage(jo.getString("images"));
                        modelTournamentAlbums.setRowType(1);

                        arrayList.add(modelTournamentAlbums);
                    }

                    adapterTournamentAlbums = new AdapterTournamentAlbums(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterTournamentAlbums);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("There are no photos or videos as of now for the tournament.");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("There are no photos or videos as of now for the tournament.");

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

                        modelTournamentAlbums = new ModelTournamentAlbums();
                        modelTournamentAlbums.setId(jo.getString("id"));
                        modelTournamentAlbums.setRowType(1);
                        // modelTournamentAlbums.setTeamName(jo.getString("teamName"));
                        modelTournamentAlbums.setImage(jo.getString("image"));

                        arrayList.add(modelTournamentAlbums);
                    }

                    adapterTournamentAlbums.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterTournamentAlbums.notifyDataSetChanged();
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

