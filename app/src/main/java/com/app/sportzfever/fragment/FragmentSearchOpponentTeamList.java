package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentSearchOpponentTeamList extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterSearchOppositeTeamList adapterSearchOppositeTeamList;
    private ModelSearchOppositeTeam userFriendList;
    private TextView text_nodata;
    private ArrayList<ModelSearchOppositeTeam> arrayList = new ArrayList<>();
    private ArrayList<ModelSearchOppositeTeam> arrayListAddedUsers = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private TextView text_save,text_detai;
    private boolean loading = true;
    public static FragmentSearchOpponentTeamList fragment_friend_request;
    private final String TAG = FragmentSearchOpponentTeamList.class.getSimpleName();
    private String keyword = "";
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String maxlistLength = "";
    private View mView;
    private int addedCount = 0;
    private EditText edt_search;
    private ImageView image_search;

    public static FragmentSearchOpponentTeamList getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentSearchOpponentTeamList();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragment_search_userlist, container, false);
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
        image_search = (ImageView) view.findViewById(R.id.image_search);
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        list_request.setLayoutManager(new GridLayoutManager(context, 2));
        arrayList = new ArrayList<>();
        setlistener();
        //getBundle();
        manageHeaderView();
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

/*
        list_request.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ((AppUtils.isNetworkAvailable(context))) {
                    if (!maxlistLength.equalsIgnoreCase(arrayList.size() + "")) {
                        if (dy > 0) //check for scroll down
                        {
                            visibleItemCount = layoutManager.getChildCount();
                            totalItemCount = layoutManager.getItemCount();
                            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loading = false;
                                    userFriendList = new ModelSearchPeoples();
                                    userFriendList.setRowType(2);
                                    arrayList.add(userFriendList);

                                    recyclerView.post(new Runnable() {
                                        public void run() {
                                            adapterSearchOppositeTeamList.notifyItemInserted(arrayList.size() - 1);
                                        }
                                    });

                                    skipCount = skipCount + 10;

                                    try {
                                        if (AppUtils.isNetworkAvailable(context)) {
                                            onLoadMore();
                                        } else {
                                            Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    } else {

                        Log.e("maxlength", "*" + arrayList.size());
                    }
                }

            }

        });
*/

    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 2) {
            Intent intent = new Intent(context, FragmentSearchOpponentTeamList.class);
            intent.putExtra("id", arrayList.get(position).getTeamId());
            intent.putExtra("name", arrayList.get(position).getTeamName());
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            context.onBackPressed();
        }
    }

    private void addInlist(int position, boolean add) {
        if (add) {
            arrayListAddedUsers.add(arrayList.get(position));
        } else {
            arrayListAddedUsers.remove(arrayList.get(position));
        }
        Log.e("arraysize", "**" + arrayListAddedUsers.size());
    }

    private void onLoadMore() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  http://sfscoring.betasportzfever.com/getFeeds/155/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52

                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCH + AppUtils.getUserId(context)
                        + "/" + "TEAM/" + edt_search.getText().toString() + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(4, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getServicelistRefresh() {
        AppUtils.onKeyBoardDown(context);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCHTEAM + edt_search.getText().toString() + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);
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
                    if (arrayListAddedUsers.size() > 0) {
                        arrayList.addAll(arrayListAddedUsers);
                    }
                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        userFriendList = new ModelSearchOppositeTeam();
                        userFriendList.setTeamId(jo.getString("teamId"));
                        userFriendList.setTeamAvatarId(jo.getString("teamAvatarId"));
                        userFriendList.setTeamLocation(jo.getString("teamLocation"));
                        userFriendList.setTeamName(jo.getString("teamName"));
                        userFriendList.setRequestStatus(jo.getString("requestStatus"));
                        userFriendList.setTeamProfilePicture(jo.getString("teamProfilePicture"));
                        userFriendList.setAdded(jo.getBoolean("added"));
                        userFriendList.setIsActive(jo.getString("isActive"));
                        userFriendList.setSportId(jo.getString("sportId"));
                        userFriendList.setSportName(jo.getString("sportName"));
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

