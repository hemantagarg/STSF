package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAvtarProfile;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentAvtarBio extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Context context;
    private ModelAvtarProfile modelAvtarProfile;
    private LinearLayoutManager layoutManager;
    private ImageView image_edit;
    private TextView avtar_namelive, avtar_battingheldlive, avtar_jercynumberlive, avtar_bowlinghandlive, avtar_battingstylelive, avtar_bowlingstylelive, avtar_specialitylive, avtar_favouritefieldpositionlive, avtar_aboutmelive;
    public static FragmentAvtarBio fragment_teamJoin_request;
    private final String TAG = FragmentAvtarBio.class.getSimpleName();
    private String avtarid = "";
    private JSONObject jo;

    public static FragmentAvtarBio getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentAvtarBio();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        View view_about = inflater.inflate(R.layout.fragement_avtarbio_new, container, false);
        context = getActivity();
        b = getArguments();

        return view_about;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avtar_namelive = (TextView) view.findViewById(R.id.avtar_namelive);
        avtar_battingheldlive = (TextView) view.findViewById(R.id.avtar_battingheldlive);
        avtar_jercynumberlive = (TextView) view.findViewById(R.id.avtar_jercynumberlive);
        avtar_bowlinghandlive = (TextView) view.findViewById(R.id.avtar_bowlinghandlive);
        avtar_battingstylelive = (TextView) view.findViewById(R.id.avtar_battingstylelive);
        avtar_bowlingstylelive = (TextView) view.findViewById(R.id.avtar_bowlingstylelive);
        avtar_specialitylive = (TextView) view.findViewById(R.id.avtar_specialitylive);
        avtar_favouritefieldpositionlive = (TextView) view.findViewById(R.id.avtar_favouritefieldpositionlive);
        avtar_aboutmelive = (TextView) view.findViewById(R.id.avtar_aboutmelive);
        image_edit = (ImageView) view.findViewById(R.id.image_edit);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBundle();
        setlistener();
        //   getServicelistRefresh();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            avtarid = bundle.getString("avtarid");
            if (AppUtils.getLoginUserAvtarId(context).equalsIgnoreCase(avtarid)) {
                // currently we are hiding edit button later we have to change it
                //  image_edit.setVisibility(View.VISIBLE);
                image_edit.setVisibility(View.GONE);
            } else {
                image_edit.setVisibility(View.GONE);
            }
        }
    }

    private void setlistener() {

        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentAvtarBioEdit tab2 = new FragmentAvtarBioEdit();
                Bundle b = new Bundle();
                b.putString("data", jo.toString());
                tab2.setArguments(b);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, tab2, true);

            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getServicelistRefresh();
    }

    public void getServicelistRefresh() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getNotifications/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_AVTARPROFILEBIO + avtarid + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

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
                    //JSONArray jarray = data.getJSONArray("AvatarDetails");
                    //  data = jObject.getString("total");

                    jo = data.getJSONObject("AvatarDetails");

                    modelAvtarProfile = new ModelAvtarProfile();

                    modelAvtarProfile.setAvatarId(jo.getString("avatarId"));
                    modelAvtarProfile.setName(jo.getString("name"));
                    modelAvtarProfile.setDescription(jo.getString("description"));
                    modelAvtarProfile.setAvatarProfilePicture(jo.getString("avatarProfilePicture"));
                    modelAvtarProfile.setAvatarType(jo.getString("avatarType"));
                    modelAvtarProfile.setBattingHand(jo.getString("battingHand"));
                    modelAvtarProfile.setBattingStyle(jo.getString("battingStyle"));
                    modelAvtarProfile.setBowlingStyle(jo.getString("bowlingStyle"));
                    modelAvtarProfile.setBowlingHand(jo.getString("bowlingHand"));
                    modelAvtarProfile.setSpeciality(jo.getString("speciality"));
                    modelAvtarProfile.setFavouriteFieldPosition(jo.getString("favouriteFieldPosition"));
                    modelAvtarProfile.setJersyNumber(jo.getString("jersyNumber"));
                    modelAvtarProfile.setSportName(jo.getString("sportName"));

                    avtar_namelive.setText(modelAvtarProfile.getName());
                    avtar_battingheldlive.setText(modelAvtarProfile.getBattingHand());
                    avtar_jercynumberlive.setText(modelAvtarProfile.getJersyNumber());
                    avtar_battingstylelive.setText(modelAvtarProfile.getBattingStyle());
                    avtar_bowlingstylelive.setText(modelAvtarProfile.getBowlingStyle());
                    avtar_bowlinghandlive.setText(modelAvtarProfile.getBowlingHand());
                    avtar_specialitylive.setText(modelAvtarProfile.getSpeciality());
                    avtar_favouritefieldpositionlive.setText(modelAvtarProfile.getFavouriteFieldPosition());
                    avtar_aboutmelive.setText(modelAvtarProfile.getDescription());

                    modelAvtarProfile.setRowType(1);

                    FragmentAvtar_Details.getInstance().setUserData(modelAvtarProfile.getAvatarProfilePicture(),
                            modelAvtarProfile.getName(), modelAvtarProfile.getSportName(), jo.getString("isFollower"));

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

