package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterSearchOppositeTeamList;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSearchOppositeTeam;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentMyTeamList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterSearchOppositeTeamList adapterSearchOppositeTeamList;
    private ModelSearchOppositeTeam userFriendList;
    private TextView text_nodata;
    private ArrayList<ModelSearchOppositeTeam> arrayList = new ArrayList<>();
    private RelativeLayout rl_top;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int skipCount = 0;
    private TextView text_save, text_detai;
    public static FragmentMyTeamList fragment_friend_request;
    private final String TAG = FragmentMyTeamList.class.getSimpleName();
    private String keyword = "";
    private View mView;
    private EditText edt_search;
    private ImageView image_search;
    private String jsonData = "", selectedId = "";

    public static FragmentMyTeamList getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentMyTeamList();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragment_search_team, container, false);
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        text_save = (TextView) view.findViewById(R.id.text_save);
        text_detai = (TextView) view.findViewById(R.id.text_detai);
        text_save.setVisibility(View.GONE);
        text_detai.setVisibility(View.GONE);
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
        image_search = (ImageView) view.findViewById(R.id.image_search);
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        rl_top.setVisibility(View.GONE);
        list_request.setLayoutManager(new GridLayoutManager(context, 2));
        arrayList = new ArrayList<>();
        list_request.setVisibility(View.VISIBLE);
        getBundle();
        getServicelistRefresh();
        setlistener();
    }

    private void getBundle() {
        if (b != null) {
            jsonData = b.getString("json");
            selectedId = b.getString("selectedId");
            Log.e("selectedId", "*" + selectedId);
        }
    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Find opponent team");
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
    }

    private void setlistener() {
        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_search.getText().toString().equalsIgnoreCase("")) {
                    getServicelistRefresh();
                } else {
                    Toast.makeText(context, "Please enter something to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!edt_search.getText().toString().equalsIgnoreCase("")) {
                        getServicelistRefresh();
                    }
                    return true;
                }
                return false;
            }
        });
        text_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicelistRefresh();
            }
        });

    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 2) {
            if (!arrayList.get(position).getTeamId().equalsIgnoreCase(selectedId)) {
                Intent intent = new Intent();
                intent.putExtra("id", arrayList.get(position).getTeamId());
                intent.putExtra("name", arrayList.get(position).getTeamName());
                getParentFragment().getTargetFragment().onActivityResult(getTargetRequestCode(), 23, intent);
                context.onBackPressed();
            } else {
                Toast.makeText(context, R.string.team_already_selected_message, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getServicelistRefresh() {
        AppUtils.onKeyBoardDown(context);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_ADMIN_TEAM + AppUtils.getAuthToken(context);
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
                AppUtils.onKeyBoardDown(context);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    arrayList.clear();
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        userFriendList = new ModelSearchOppositeTeam();
                        userFriendList.setTeamId(jo.getString("teamId"));
                        userFriendList.setTeamAvatarId(jo.getString("teamAvatarId"));
                        userFriendList.setTeamLocation(jo.getString("teamLocation"));
                        userFriendList.setTeamName(jo.getString("teamName"));
                        userFriendList.setTeamProfilePicture(jo.getString("teamProfile"));
                        userFriendList.setIsActive(jo.getString("isActive"));
                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterSearchOppositeTeamList = new AdapterSearchOppositeTeamList(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterSearchOppositeTeamList);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        text_nodata.setVisibility(View.VISIBLE);
                        text_nodata.setText("No User found");
                    }
                } else {
                    text_nodata.setVisibility(View.VISIBLE);
                    text_nodata.setText("No User found");
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 11) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getServicelistRefresh();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
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

