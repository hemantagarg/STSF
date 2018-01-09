package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.app.sportzfever.adapter.AdapterSearchNewPlayer;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSearchPeoples;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentSearchNewPlayer extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private RecyclerView list_request;
    private Bundle b;
    private Activity context;
    private AdapterSearchNewPlayer adapterSearchOppositeTeamList;
    private ModelSearchPeoples userFriendList;
    private TextView text_nodata;
    private ArrayList<ModelSearchPeoples> arrayList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int skipCount = 0;
    private TextView text_save, text_detai, text_validate;
    public static FragmentSearchNewPlayer fragment_friend_request;
    private final String TAG = FragmentSearchNewPlayer.class.getSimpleName();
    private View mView;
    private EditText edt_search, edt_password;
    private ImageView image_search;
    private boolean isPlayerEmailFound = false;

    public static FragmentSearchNewPlayer getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentSearchNewPlayer();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragment_search_new_player, container, false);
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
        text_validate = (TextView) view.findViewById(R.id.text_validate);
        text_validate.setVisibility(View.GONE);
        text_save.setVisibility(View.GONE);
        text_detai.setVisibility(View.GONE);
        image_search = (ImageView) view.findViewById(R.id.image_search);
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        edt_password = (EditText) view.findViewById(R.id.edt_password);
        edt_password.setVisibility(View.GONE);
        list_request.setLayoutManager(new LinearLayoutManager(context));
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
        HeaderViewManager.getInstance().setHeading(true, "Add New Player");
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
                if (!edt_search.getText().toString().equalsIgnoreCase("")) {
                    getServicelistRefresh();
                } else {
                    Toast.makeText(context, "Please enter emailid to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
        text_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlayerEmailFound) {
                    if (!edt_password.getText().toString().equalsIgnoreCase("")) {
                        validatePlayer();
                    } else {
                        Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!edt_search.getText().toString().equalsIgnoreCase("") && !edt_password.getText().toString().equalsIgnoreCase("") && AppUtils.isEmailValid(edt_search.getText().toString())) {
                        makeJsonRequest();
                    } else {
                        if (edt_search.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(context, "Please enter valid emailid", Toast.LENGTH_SHORT).show();
                        } else if (edt_password.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(context, "Please enter player name", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicelistRefresh();
            }
        });
    }

    private void makeJsonRequest() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("playerName", edt_password.getText().toString());
            jsonObject.put("avatarName", edt_password.getText().toString());
            jsonObject.put("email", edt_search.getText().toString());
            jsonObject.put("avatarId", "");
            jsonObject.put("role", "N/A");
            jsonObject.put("profilePicture", "");
            jsonObject.put("inviteStatus", AppConstant.ACCEPTED);
            jsonObject.put("isInPlayingSquad", "1");
            jsonObject.put("isInBench", "0");
            jsonObject.put("shouldAddInRoster", "YES");

            Intent intent = new Intent(context, FragmentSearchNewPlayer.class);
            intent.putExtra("userData", jsonObject.toString());
            intent.putExtra("isNewPlayer", true);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            context.onBackPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClickListener(int position, int flag) {
        if (flag == 2) {
            changeButton(true);
        }
    }


    private void validatePlayer() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", edt_search.getText().toString());
                jsonObject.put("password", edt_password.getText().toString());
                //  http://sfscoring.sf.com/sflogin
                String url = JsonApiHelper.BASEURL + JsonApiHelper.LOGIN;
                new CommonAsyncTaskHashmap(4, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getServicelistRefresh() {
        if (AppUtils.isEmailValid(edt_search.getText().toString())) {
            AppUtils.onKeyBoardDown(context);
            try {
                if (AppUtils.isNetworkAvailable(context)) {
                    String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_PLAYER_BYEMAIL + edt_search.getText().toString() + "/" + "Cricket";
                    new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, new JSONObject(), Request.Method.GET);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Please enter valid emailid", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        try {
            if (position == 1) {
                AppUtils.onKeyBoardDown(context);
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    arrayList.clear();

                    JSONObject jo = jObject.getJSONObject("data");
                    userFriendList = new ModelSearchPeoples();
                    userFriendList.setId(jo.getString("id"));
                    userFriendList.setAvatarName(jo.getString("avatarName"));
                    userFriendList.setAvatarProfilePicture(jo.getString("avatarProfilePicture"));
                    userFriendList.setAvatar(jo.getString("avatarId"));
                    userFriendList.setProfilePicture(jo.getString("profilePicture"));
                    userFriendList.setFullName(jo.getString("fullName"));
                    userFriendList.setRowType(1);

                    arrayList.add(userFriendList);
                    adapterSearchOppositeTeamList = new AdapterSearchNewPlayer(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterSearchOppositeTeamList);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (arrayList.size() > 0) {
                        text_nodata.setVisibility(View.GONE);
                    } else {
                        changeButton(false);
                    }
                } else {
                    changeButton(false);
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            } else if (position == 4) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    //    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    makeExistingJsonRequest();
                } else {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            if (position == 1) {
                changeButton(false);
                if (adapterSearchOppositeTeamList != null) {
                    adapterSearchOppositeTeamList.notifyDataSetChanged();
                }
            }
            e.printStackTrace();
        }
    }

    private void makeExistingJsonRequest() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("email", edt_search.getText().toString());
            if (arrayList.size() > 0) {
                jsonObject.put("avatarName", arrayList.get(0).getAvatarName());
                jsonObject.put("playerName", arrayList.get(0).getFullName());
                jsonObject.put("userId", arrayList.get(0).getId());
                jsonObject.put("avatarId", arrayList.get(0).getAvatar());
            }
            jsonObject.put("role", "N/A");
            jsonObject.put("profilePicture", "");
            jsonObject.put("inviteStatus", AppConstant.ACCEPTED);
            jsonObject.put("isInPlayingSquad", "1");
            jsonObject.put("isInBench", "0");
            jsonObject.put("shouldAddInRoster", "YES");

            Intent intent = new Intent(context, FragmentSearchNewPlayer.class);
            intent.putExtra("userData", jsonObject.toString());
            intent.putExtra("isNewPlayer", false);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            context.onBackPressed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void changeButton(boolean isPlayerFound) {
        isPlayerEmailFound = isPlayerFound;
        edt_password.setText("");
        if (isPlayerFound) {
            edt_password.setVisibility(View.VISIBLE);
            text_validate.setVisibility(View.VISIBLE);
            text_validate.setText("Validate");
            edt_password.setHint("Password");
        } else {
            text_nodata.setVisibility(View.VISIBLE);
            text_nodata.setText("We didn't find any user with the above email id.\n" +
                    "Click on \"Add player to lineup\" button to add player with this email id.");
            edt_password.setVisibility(View.VISIBLE);
            text_validate.setVisibility(View.VISIBLE);
            text_validate.setText("Add player to Lineup");
            edt_password.setHint("Player Full Name");
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        if (context != null && isAdded())
            Toast.makeText(getActivity(), getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

