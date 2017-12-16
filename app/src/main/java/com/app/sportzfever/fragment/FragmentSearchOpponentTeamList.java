package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.app.sportzfever.adapter.AdapterSearchUserList;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSearchPeoples;
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
    private AdapterSearchUserList adapterSearchUserList;
    private ModelSearchPeoples userFriendList;
    private TextView text_nodata;
    private ArrayList<ModelSearchPeoples> arrayList = new ArrayList<>();
    private ArrayList<ModelSearchPeoples> arrayListAddedUsers = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private int skipCount = 0;
    private TextView text_save;
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
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        text_nodata = (TextView) view.findViewById(R.id.text_nodata);
        text_save = (TextView) view.findViewById(R.id.text_save);
        image_search = (ImageView) view.findViewById(R.id.image_search);
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();
        getBundle();
        manageHeaderView();
    }

    private void getBundle() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                if (bundle.containsKey("selectedUser")) {
                    String data = bundle.getString("selectedUser");

                    JSONObject jsonObject = new JSONObject(data);

                    JSONArray userList = jsonObject.getJSONArray("userList");

                    for (int i = 0; i < userList.length(); i++) {

                        JSONObject jo = userList.getJSONObject(i);

                        userFriendList = new ModelSearchPeoples();
                        userFriendList.setUserId(jo.getString("id"));
                        userFriendList.setTotalFriend(jo.getString("totalFriend"));
                        userFriendList.setTotalPost(jo.getString("totalPost"));
                        userFriendList.setTotalTeam(jo.getString("totalTeam"));
                        userFriendList.setName(jo.getString("name"));
                        userFriendList.setEmail(jo.getString("email"));
                        userFriendList.setDateOfBirth(jo.getString("dateOfBirth"));
                        userFriendList.setAbout(jo.getString("about"));
                        userFriendList.setHometown(jo.getString("hometown"));
                        userFriendList.setCurrentLocation(jo.getString("currentLocation"));
                        userFriendList.setProfilePicture(jo.getString("profilePicture"));
                        userFriendList.setIschecked(true);
                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterSearchUserList = new AdapterSearchUserList(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterSearchUserList);
                    addedCount = arrayListAddedUsers.size();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        HeaderViewManager.getInstance().setHeading(true, "Match Roles");
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
                if (addedCount > 0) {
                    JSONObject jsonObject = makeJsonRequest();
                    Intent intent = new Intent(context, FragmentSearchOpponentTeamList.class);
                    intent.putExtra("userData", jsonObject.toString());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    context.onBackPressed();
                } else {
                    Toast.makeText(context, "Please select atleast one user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicelistRefresh();
            }
        });

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
                                            adapterSearchUserList.notifyItemInserted(arrayList.size() - 1);
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

    }

    private JSONObject makeJsonRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).ischecked()) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", arrayList.get(i).getUserId());
                    jsonObject1.put("name", arrayList.get(i).getName());
                    jsonObject1.put("totalFriend", arrayList.get(i).getTotalFriend());
                    jsonObject1.put("totalPost", arrayList.get(i).getTotalPost());
                    jsonObject1.put("totalTeam", arrayList.get(i).getTotalTeam());
                    jsonObject1.put("email", arrayList.get(i).getEmail());
                    jsonObject1.put("dateOfBirth", arrayList.get(i).getDateOfBirth());
                    jsonObject1.put("about", arrayList.get(i).getAbout());
                    jsonObject1.put("hometown", arrayList.get(i).getHometown());
                    jsonObject1.put("currentLocation", arrayList.get(i).getCurrentLocation());
                    jsonObject1.put("profilePicture", arrayList.get(i).getProfilePicture());
                    array.put(jsonObject1);
                }
            }
            jsonObject.put("userList", array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 1) {
            if (arrayList.get(position).ischecked()) {
                arrayList.get(position).setIschecked(false);
                addInlist(position, false);
                addedCount--;
            } else {
                if (addedCount < 3) {
                    arrayList.get(position).setIschecked(true);
                    addedCount++;
                    addInlist(position, true);
                } else {
                    Toast.makeText(context, "You can add maximum 3 scorers", Toast.LENGTH_SHORT).show();
                }
            }
            adapterSearchUserList.notifyDataSetChanged();
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
                        + "/" + "PEOPLE/" + edt_search.getText().toString() + "/" + skipCount + "/" + AppUtils.getAuthToken(context);
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
                // http://sfscoring.betasportzfever.com/searchTeams/challenger/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCHTEAM_CHALLENGER + AppUtils.getAuthToken(context);
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
                    JSONObject data = jObject.getJSONObject("data");
                    arrayList.clear();
                    if (arrayListAddedUsers.size() > 0) {
                        arrayList.addAll(arrayListAddedUsers);
                    }
                    JSONArray peoplesArray = data.getJSONArray("peoples");
                    maxlistLength = data.getString("totalPeoples");
                    for (int i = 0; i < peoplesArray.length(); i++) {

                        JSONObject jo = peoplesArray.getJSONObject(i);

                        userFriendList = new ModelSearchPeoples();
                        userFriendList.setUserId(jo.getString("userId"));
                        userFriendList.setTotalFriend(jo.getString("totalFriend"));
                        userFriendList.setTotalPost(jo.getString("totalPost"));
                        userFriendList.setTotalTeam(jo.getString("totalTeam"));
                        userFriendList.setName(jo.getString("name"));
                        userFriendList.setEmail(jo.getString("email"));
                        userFriendList.setIschecked(false);
                        userFriendList.setDateOfBirth(jo.getString("dateOfBirth"));
                        userFriendList.setAbout(jo.getString("about"));
                        userFriendList.setHometown(jo.getString("hometown"));
                        userFriendList.setCurrentLocation(jo.getString("currentLocation"));
                        userFriendList.setProfilePicture(jo.getString("profilePicture"));

                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterSearchUserList = new AdapterSearchUserList(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterSearchUserList);

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

            } else if (position == 4) {

                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    arrayList.remove(arrayList.size() - 1);
                    JSONArray peoplesArray = data.getJSONArray("peoples");
                    maxlistLength = data.getString("totalPeoples");

                    for (int i = 0; i < peoplesArray.length(); i++) {

                        JSONObject jo = peoplesArray.getJSONObject(i);

                        userFriendList = new ModelSearchPeoples();
                        userFriendList.setUserId(jo.getString("userId"));
                        userFriendList.setTotalFriend(jo.getString("totalFriend"));
                        userFriendList.setTotalPost(jo.getString("totalPost"));
                        userFriendList.setTotalTeam(jo.getString("totalTeam"));
                        userFriendList.setName(jo.getString("name"));
                        userFriendList.setEmail(jo.getString("email"));
                        userFriendList.setDateOfBirth(jo.getString("dateOfBirth"));
                        userFriendList.setAbout(jo.getString("about"));
                        userFriendList.setHometown(jo.getString("hometown"));
                        userFriendList.setCurrentLocation(jo.getString("currentLocation"));
                        userFriendList.setProfilePicture(jo.getString("profilePicture"));

                        userFriendList.setRowType(1);

                        arrayList.add(userFriendList);
                    }
                    adapterSearchUserList.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {
                    adapterSearchUserList.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
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

