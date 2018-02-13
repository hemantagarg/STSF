package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.TeamDrawerListAdapter;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.DrawerListModel;
import com.app.sportzfever.models.ModelSportTeamList;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentMenuTeamList extends BaseFragment implements OnCustomItemClicListener, ApiResponse {

    private Bundle b;
    private Activity context;
    private RecyclerView list_request;
    ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> teamlist = new ArrayList<>();
    private TextView text_nodata;
    View view_about;
    private TeamDrawerListAdapter listAdapter;

    public static FragmentMenuTeamList fragment_teamJoin_request;
    private final String TAG = FragmentMenuTeamList.class.getSimpleName();

    public static FragmentMenuTeamList getInstance() {
        if (fragment_teamJoin_request == null)
            fragment_teamJoin_request = new FragmentMenuTeamList();
        return fragment_teamJoin_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view_about = inflater.inflate(R.layout.fragment_menu_teamlist, container, false);
        context = getActivity();
        b = getArguments();
        manageHeaderView();
        return view_about;
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
        HeaderViewManager.getInstance().setHeading(true, "Team");
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

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        setData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        list_request.setLayoutManager(new GridLayoutManager(context, 2));
        listAdapter = new TeamDrawerListAdapter(context, this, teamlist);
        list_request.setAdapter(listAdapter);
    }


    public void setData() {
        try {
            teamlist.clear();
            String array = AppUtils.getTeamList(context);
            JSONArray teams = new JSONArray(array);

            for (int i = 0; i < teams.length(); i++) {
                JSONObject headerobj = teams.getJSONObject(i);
                modelSportTeamList = new ModelSportTeamList();
                if (headerobj.has("teamId")) {
                    modelSportTeamList.setTeamId(headerobj.getString("teamId"));
                }
                modelSportTeamList.setRowType(1);
                modelSportTeamList.setTeamName(headerobj.getString("SubMenu2Name"));
                if (headerobj.has("teamProfilePicture"))
                    modelSportTeamList.setProfilePicture(headerobj.getString("teamProfilePicture"));

                teamlist.add(modelSportTeamList);
                listAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickListener(int position, int flag) {
        String avtarid = teamlist.get(position).getTeamId();
        if (avtarid.equalsIgnoreCase("")) {
            Intent intent = new Intent(context, Fragment_CreateTeam.class);
            startActivity(intent);
        } else {
            Fragment_Team_Details fragmentAvtar_details = new Fragment_Team_Details();
            Bundle bundle = new Bundle();
            bundle.putString("id", avtarid);
            fragmentAvtar_details.setArguments(bundle);
            fragmentAvtar_details.setTargetFragment(FragmentMenuTeamList.this, AppConstant.FRAGMENT_CODE);
            Dashboard.getInstance().pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);
        }

    }

    public void getMenuData() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  https://sfscoring.betasportzfever.com/getMenu/1/479a44a634f82b0394f78352d302ec36
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GETMENU + AppUtils.getUserId(context) + "/" + AppUtils.getAuthToken(context);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstant.FRAGMENT_CODE) {
                getMenuData();
            }
        }
    }

    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (method == 1) {
                if (method == 1) {
                    if (jObject.getString("result").equalsIgnoreCase("1")) {
                        JSONArray menucategoriesArrayTeam;
                        JSONObject data = jObject.getJSONObject("data");
                        JSONArray servicearr = data.getJSONArray("Menu");

                        for (int i = 0; i < servicearr.length(); i++) {
                            JSONObject headerobj = servicearr.getJSONObject(i);
                            ArrayList<DrawerListModel> list = new ArrayList<>();
                            JSONArray menucategoriesArray = headerobj.getJSONArray("Menucategories");
                            Log.e("Menucategories", menucategoriesArray.toString());

                            if (menucategoriesArray.length() > 0) {
                                for (int j = 0; j < menucategoriesArray.length(); j++) {
                                    JSONObject obj = menucategoriesArray.getJSONObject(j);

                                    DrawerListModel model = new DrawerListModel();
                                    model.setSubMenu1Id(obj.getString("SubMenu1Id"));
                                    model.setSubMenu1AvatarId(obj.getString("SubMenu1AvatarId"));
                                    model.setName(obj.getString("SubMenu1Name"));
                                    list.add(model);
                                    if (headerobj.getString("MenuId").equalsIgnoreCase("2")) {
                                        menucategoriesArrayTeam = obj.getJSONArray("SubMenu1Teams");
                                        AppUtils.setTeamList(context, menucategoriesArrayTeam.toString());
                                        setData();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostFail(int method, String response) {

    }
}