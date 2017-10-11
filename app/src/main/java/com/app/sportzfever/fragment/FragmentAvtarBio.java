package com.app.sportzfever.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.app.sportzfever.activities.ViewMatchScoreCard;
import com.app.sportzfever.adapter.AdapterUpcomingEvent;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAvtarProfile;
import com.app.sportzfever.models.UpcomingEvent;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentAvtarBio extends BaseFragment implements ApiResponse, OnCustomItemClicListener {


    private RecyclerView list_request;
    private Bundle b;
    private Context context;


    private ModelAvtarProfile modelAvtarProfile;
    private ArrayList<ModelAvtarProfile> arrayList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectionDetector cd;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private boolean loading = true;
    private String maxlistLength = "";
    private TextView avtar_namelive,avtar_battingheldlive,avtar_jercynumberlive,avtar_battingstylelive,avtar_bowlingstylelive,avtar_specialitylive,avtar_favouritefieldpositionlive,avtar_aboutmelive;
    public static FragmentAvtarBio fragment_teamJoin_request;
    private final String TAG = FragmentAvtarBio.class.getSimpleName();

    public static FragmentAvtarBio getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentAvtarBio();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragement_avtarbio, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        avtar_namelive = (TextView) view.findViewById(R.id.avtar_namelive);
        avtar_battingheldlive = (TextView) view.findViewById(R.id.avtar_battingheldlive);
        avtar_jercynumberlive = (TextView) view.findViewById(R.id.avtar_jercynumberlive);
        avtar_battingstylelive = (TextView) view.findViewById(R.id.avtar_battingstylelive);
        avtar_bowlingstylelive = (TextView) view.findViewById(R.id.avtar_bowlingstylelive);
        avtar_specialitylive = (TextView) view.findViewById(R.id.avtar_specialitylive);
        avtar_favouritefieldpositionlive = (TextView) view.findViewById(R.id.avtar_favouritefieldpositionlive);
        avtar_aboutmelive = (TextView) view.findViewById(R.id.avtar_aboutmelive);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        setlistener();

        getServicelistRefresh();
    }

    private void setlistener() {




    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {

        }
    }

    private void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_AVTARPROFILEBIO + "173"+"/" +  AppUtils.getAuthToken(context);
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
                    JSONArray jarray = data.getJSONArray("AvatarDetails");
                    //  data = jObject.getString("total");


                    JSONObject jo = jarray.getJSONObject(0);

                    modelAvtarProfile = new ModelAvtarProfile();

                    modelAvtarProfile.setAvatarId(jo.getString("avatarId"));
                    modelAvtarProfile.setName(jo.getString("name"));
                    modelAvtarProfile.setDescription(jo.getString("description"));
                    modelAvtarProfile.setAvatarProfilePicture(jo.getString("avatarProfilePicture"));
                    modelAvtarProfile.setAvatarType(jo.getString("avatarType"));
                    modelAvtarProfile.setBattingHand(jo.getString("battingHand"));
                    modelAvtarProfile.setBattingStyle(jo.getString("battingStyle"));
                    modelAvtarProfile.setSpeciality(jo.getString("speciality"));
                    modelAvtarProfile.setFavouriteFieldPosition(jo.getString("favouriteFieldPosition"));
                    modelAvtarProfile.setJersyNumber(jo.getString("jersyNumber"));

                    avtar_namelive.setText(modelAvtarProfile.getName());
                    avtar_battingheldlive.setText(modelAvtarProfile.getBattingHand());
                    avtar_jercynumberlive.setText(modelAvtarProfile.getJersyNumber());

                    avtar_battingstylelive.setText(modelAvtarProfile.getBattingStyle());

                    avtar_bowlingstylelive.setText(modelAvtarProfile.getBowlingStyle());
                    avtar_specialitylive.setText(modelAvtarProfile.getSpeciality());
                    avtar_favouritefieldpositionlive.setText(modelAvtarProfile.getFavouriteFieldPosition());
                    avtar_aboutmelive.setText(modelAvtarProfile.getDescription());

                    modelAvtarProfile.setRowType(1);


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
