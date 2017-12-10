package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
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
public class FragmentMatchRoles extends BaseFragment implements OnCustomItemClicListener, ApiResponse {

    private Bundle b;
    private Activity context;
    View mView;
    private Button btn_create_team;
    public static FragmentMatchRoles fragment_friend_request;
    private final String TAG = FragmentMatchRoles.class.getSimpleName();
    private String teamId = "", eventId = "", playersCount = "";
    private JSONObject jsonresponse;
    private TextView text_captain, textViceCaptain, text_wicket_keeper, text_select_scorer,
            text_first_scorer, text_second_scorer, text_third_scorer, text_userscorer;
    private Spinner spinner_captain, spinner_vice_captain, spinner_wicket_keeper, spinner_select_scorer,
            spinner_first_scorer, spinner_second_scorer, spinner_third_scorer;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListCaptain = new ArrayList<>();
    private ArrayList<String> arrayListViceCaptain = new ArrayList<>();
    private ArrayList<String> arrayListWicketKeeper = new ArrayList<>();
    private ArrayList<String> arrayListFirstScorer = new ArrayList<>();
    private ArrayList<String> arrayListSecondScorer = new ArrayList<>();
    private ArrayList<String> arrayListThirdScorer = new ArrayList<>();
    private ArrayList<String> arrayListScorerType = new ArrayList<>();
    private ArrayAdapter<String> adapterCaptain, adapterViceCaptain, adapterWicketKeeper, adapterScorerType, adapterFirstScorer,
            adapterThirdScorer, adapterSecondScorer;
    private LinearLayout linear_teamlineup;
    private boolean isFirstTime = true;

    public static FragmentMatchRoles getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentMatchRoles();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_match_roles, container, false);
        context = getActivity();
        b = getArguments();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getBundle();
        setlistener();
        manageHeaderView();
    }


    private void init() {
        btn_create_team = (Button) mView.findViewById(R.id.btn_create_team);
        text_captain = (TextView) mView.findViewById(R.id.text_captain);
        textViceCaptain = (TextView) mView.findViewById(R.id.textViceCaptain);
        text_wicket_keeper = (TextView) mView.findViewById(R.id.text_wicket_keeper);
        text_select_scorer = (TextView) mView.findViewById(R.id.text_select_scorer);
        text_first_scorer = (TextView) mView.findViewById(R.id.text_first_scorer);
        text_second_scorer = (TextView) mView.findViewById(R.id.text_second_scorer);
        text_third_scorer = (TextView) mView.findViewById(R.id.text_third_scorer);
        text_userscorer = (TextView) mView.findViewById(R.id.text_userscorer);
        spinner_captain = (Spinner) mView.findViewById(R.id.spinner_captain);
        spinner_vice_captain = (Spinner) mView.findViewById(R.id.spinner_vice_captain);
        spinner_wicket_keeper = (Spinner) mView.findViewById(R.id.spinner_wicket_keeper);
        spinner_select_scorer = (Spinner) mView.findViewById(R.id.spinner_select_scorer);
        spinner_first_scorer = (Spinner) mView.findViewById(R.id.spinner_first_scorer);
        spinner_second_scorer = (Spinner) mView.findViewById(R.id.spinner_second_scorer);
        spinner_third_scorer = (Spinner) mView.findViewById(R.id.spinner_third_scorer);
        linear_teamlineup = (LinearLayout) mView.findViewById(R.id.linear_teamlineup);
    }

    private void getBundle() {

        if (b != null) {
            teamId = b.getString("teamId");
            eventId = b.getString("eventId");
            playersCount = b.getString("playersCount");
            String response = b.getString("jsonresponse");
            Log.e("respnse", response);
            setData(response);
        }

    }

    private void setData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray newMatchlineUp = jsonObject.getJSONArray("newMatchlineUp");
            for (int i = 0; i < newMatchlineUp.length(); i++) {
                JSONObject jo = newMatchlineUp.getJSONObject(i);
                modelSportTeamList = new ModelSportTeamList();
                modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                modelSportTeamList.setIsInPlayingBench(jo.getString("isInBench"));
                modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                //  modelSportTeamList.setIsReservedPlayer(jo.getString("isReservedPlayer"));
                modelSportTeamList.setOrder(jo.getString("order"));
                modelSportTeamList.setSpeciality(jo.getString("role"));
                arrayList.add(modelSportTeamList);
            }
            setSpinnerData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerData() {

        arrayListScorerType.add("User");
        arrayListScorerType.add("Team Lineup");
        adapterScorerType = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListScorerType);
        spinner_select_scorer.setAdapter(adapterScorerType);

        adapterCaptain = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListCaptain);
        spinner_captain.setAdapter(adapterCaptain);

        adapterViceCaptain = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListViceCaptain);
        spinner_vice_captain.setAdapter(adapterViceCaptain);

        adapterWicketKeeper = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListWicketKeeper);
        spinner_wicket_keeper.setAdapter(adapterWicketKeeper);

        adapterFirstScorer = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListFirstScorer);
        spinner_first_scorer.setAdapter(adapterFirstScorer);

        adapterSecondScorer = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListSecondScorer);
        spinner_second_scorer.setAdapter(adapterSecondScorer);

        adapterThirdScorer = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListThirdScorer);
        spinner_third_scorer.setAdapter(adapterThirdScorer);
    }

    private void getTeamLineup() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //   http://sfscoring.betasportzfever.com/getMatchLineup/23/77/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_MATCH_LINEUP + teamId + "/" + eventId + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
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

    private void setlistener() {
        btn_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        text_userscorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_SearchUserList fragmentSearchUserList = new Fragment_SearchUserList();
                fragmentSearchUserList.setTargetFragment(FragmentMatchRoles.this, AppConstant.FRAGMENT_CODE);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentSearchUserList, true);
            }
        });
        text_captain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_captain.performClick();
            }
        });

        textViceCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_vice_captain.performClick();
            }
        });
        text_wicket_keeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_wicket_keeper.performClick();
            }
        });

        text_select_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_select_scorer.performClick();
            }
        });
        text_first_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_first_scorer.performClick();
            }
        });
        text_second_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_second_scorer.performClick();
            }
        });
        text_third_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_third_scorer.performClick();
            }
        });
        spinner_select_scorer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (!isFirstTime) {
                    if (position == 0) {
                        text_userscorer.setVisibility(View.VISIBLE);
                        linear_teamlineup.setVisibility(View.GONE);
                    } else {
                        text_userscorer.setVisibility(View.GONE);
                        linear_teamlineup.setVisibility(View.VISIBLE);
                    }
                    text_select_scorer.setText(spinner_select_scorer.getSelectedItem().toString());
                }
                isFirstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_captain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (!isFirstTime) {
                    text_captain.setText(spinner_captain.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_vice_captain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (!isFirstTime) {
                    textViceCaptain.setText(spinner_captain.getSelectedItem().toString());
                }
                isFirstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstant.FRAGMENT_CODE) {
                String userData = data.getStringExtra("userData");
                Log.e("userData", userData);
                setUserData(userData);
            }
        }
    }

    private void setUserData(String userData) {
        try {
            JSONObject jsonObject = new JSONObject(userData);
            String names = "";
            JSONArray userList = jsonObject.getJSONArray("userList");
            for (int i = 0; i < userList.length(); i++) {
                JSONObject jo = userList.getJSONObject(i);
                if (names.equalsIgnoreCase("")) {
                    names = jo.getString("name");
                } else {
                    names = names + "," + jo.getString("name");
                }
            }
            text_userscorer.setText(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickListener(int position, int flag) {
   /*     Intent in = new Intent(context, ActivityChat.class);
        if (arrayList.get(position).getUserId().equalsIgnoreCase(AppUtils.getUserIdChat(context))) {
            in.putExtra("reciever_id", arrayList.get(position).getSenderID());
        } else {
            in.putExtra("reciever_id", arrayList.get(position).getUserId());
        }
        in.putExtra("name", arrayList.get(position).getSenderName());
        in.putExtra("image", arrayList.get(position).getReceiverImage());
        in.putExtra("searchID", arrayList.get(position).getSearchId());
        startActivity(in);*/
    }

    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {

                if (response.getString("result").equalsIgnoreCase("1")) {
                    jsonresponse = response;

                } else {
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

