package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.TeamDrawerListAdapter;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.DrawerListModel;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentMenuTeamList extends BaseFragment implements OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    private ExpandableListView expendableView;
    private LinkedHashMap<String, List<DrawerListModel>> alldata;
    private ArrayList<String> groupnamelist;
    private ArrayList<String> groupnamelistId = new ArrayList<>();
    private TextView text_nodata;
    View view_about;
    private int lastExpandedPosition;
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
        // Dashboard.getInstance().manageHeaderVisibitlity(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        alldata = new LinkedHashMap<>();
        groupnamelist = new ArrayList<>();
        expendableView = (ExpandableListView) view.findViewById(R.id.expendableView);
        listAdapter = new TeamDrawerListAdapter(context, groupnamelist, alldata);
        expendableView.setAdapter(listAdapter);
        setData();
        setlistener();
    }

    private void setData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String array = bundle.getString("array");

            try {
                JSONArray teams = new JSONArray(array);

                for (int i = 0; i < teams.length(); i++) {
                    JSONObject headerobj = teams.getJSONObject(i);
                    groupnamelist.add(headerobj.getString("SubMenu1Name"));
                    groupnamelistId.add(headerobj.getString("SubMenu1AvatarId"));

                    ArrayList<DrawerListModel> list = new ArrayList<>();
                    JSONArray menucategoriesArray = headerobj.getJSONArray("SubMenu1Teams");
                    Log.e("Menucategories", menucategoriesArray.toString());

                    if (menucategoriesArray.length() > 0) {
                        for (int j = 0; j < menucategoriesArray.length(); j++) {
                            JSONObject obj = menucategoriesArray.getJSONObject(j);

                            DrawerListModel model = new DrawerListModel();
                            model.setSubMenu1Id(obj.getString("SubMenu2Id"));
                            model.setSubMenu1AvatarId(obj.getString("SubMenu2AvatarId"));
                            model.setName(obj.getString("SubMenu2Name"));
                            if (obj.has("teamId")) {
                                model.setTeamId(obj.getString("teamId"));
                            }
                            list.add(model);
                        }
                        alldata.put(groupnamelist.get(i), list);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setlistener() {
        expendableView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Nothing here ever fires
                System.err.println("child clicked");
                List<DrawerListModel> list = alldata.get(groupnamelist.get(groupPosition));
                Log.e("getTeamId", "**" + list.get(childPosition).getTeamId());
                String avtarid = list.get(childPosition).getTeamId();
                FragmentSportsTeamDetailList fragmentAvtar_details = new FragmentSportsTeamDetailList();
                Bundle bundle = new Bundle();
                bundle.putString("id", avtarid);
                fragmentAvtar_details.setArguments(bundle);

                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentAvtar_details, true);
                return true;
            }
        });
        expendableView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.e("group expand click", "clicked" + groupPosition);
                if (lastExpandedPosition != -1 && groupPosition == 1
                        && groupPosition != lastExpandedPosition) {
                    expendableView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {


    }
}

