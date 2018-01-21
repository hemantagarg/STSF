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
    private String teamId = "", eventId = "", playersCount = "", title = "";
    private JSONObject jsonresponse;
    private TextView text_captain, textViceCaptain, text_wicket_keeper, text_select_scorer,
            text_first_scorer, text_second_scorer, text_third_scorer, text_userscorer;
    private Spinner spinner_captain, spinner_vice_captain, spinner_wicket_keeper, spinner_select_scorer,
            spinner_first_scorer, spinner_second_scorer, spinner_third_scorer;
    private ModelSportTeamList modelSportTeamList;
    private ArrayList<ModelSportTeamList> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListNames = new ArrayList<>();
    private ArrayList<String> arrayListCaptain = new ArrayList<>();
    private ArrayList<String> arrayListAvtarId = new ArrayList<>();
    private ArrayList<String> arrayListUserId = new ArrayList<>();
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
    private String teamCheckAvailibility = "", selectedFirstScorer = "", selectedSecondScorer = "", selectedThirdScorer = "";
    private JSONObject jsonObject;
    private JSONObject selectedUserList;
    private String matchId = "";

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
            title = b.getString("title");
            playersCount = b.getString("playersCount");
            teamCheckAvailibility = b.getString("teamCheckAvailibility");
            String response = b.getString("jsonresponse");
            String linepArray = b.getString("linepArray");
            Log.e("respnse", response);
            setData(response, linepArray);
        }

    }

    private void setData(String response, String linepArray) {
        try {
            jsonObject = new JSONObject(response);
            JSONObject lineuparray = new JSONObject(linepArray);
            matchId = lineuparray.getString("matchId");
            JSONArray newMatchlineUp = jsonObject.getJSONArray("newMatchlineUp");
            for (int i = 0; i < newMatchlineUp.length(); i++) {
                JSONObject jo = newMatchlineUp.getJSONObject(i);
                modelSportTeamList = new ModelSportTeamList();
                modelSportTeamList.setAvtarId(jo.getString("avatarId"));
                modelSportTeamList.setUserId(jo.getString("userId"));
                modelSportTeamList.setAddedStatus(jo.getString("inviteStatus"));
                modelSportTeamList.setPlayerName(jo.getString("playerName"));
                modelSportTeamList.setIsInPlayingBench(jo.getString("isInBench"));
                modelSportTeamList.setIsInPlayingSquad(jo.getString("isInPlayingSquad"));
                //  modelSportTeamList.setIsReservedPlayer(jo.getString("isReservedPlayer"));
                modelSportTeamList.setOrder(jo.getString("order"));
                modelSportTeamList.setSpeciality(jo.getString("role"));
                arrayList.add(modelSportTeamList);
            }
            setSpinnerData(linepArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSelectedItems(String response) {
        try {
            if (!response.equalsIgnoreCase("")) {
                JSONObject jObject = new JSONObject(response);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject matchRoles = jObject.getJSONObject("matchRoles");
                    JSONObject captain = matchRoles.getJSONObject("captain");
                    if (captain.has("avatarId")) {
                        if (arrayListAvtarId.contains(captain.getString("avatarId"))) {
                            int pos = arrayListAvtarId.indexOf(captain.getString("avatarId"));
                            spinner_captain.setSelection(pos + 1);
                        }
                    }
                    JSONObject viceCaptain = matchRoles.getJSONObject("viceCaptain");
                    if (viceCaptain.has("avatarId")) {
                        if (arrayListAvtarId.contains(viceCaptain.getString("avatarId"))) {
                            int po1 = arrayListAvtarId.indexOf(viceCaptain.getString("avatarId"));
                            spinner_vice_captain.setSelection(po1 + 1);
                        }
                    }
                    JSONObject wicketKeeper = matchRoles.getJSONObject("wicketKeeper");
                    if (viceCaptain.has("avatarId")) {
                        if (arrayListAvtarId.contains(wicketKeeper.getString("avatarId"))) {
                            int po1 = arrayListAvtarId.indexOf(wicketKeeper.getString("avatarId"));
                            spinner_wicket_keeper.setSelection(po1 + 1);
                        }
                    }
                    JSONArray scorers = jObject.getJSONArray("scorers");
                    int size = scorers.length();
                    if (size > 0) {
                        JSONObject jo = scorers.getJSONObject(0);
                        if (jo.has("scorerId")) {
                            if (arrayListUserId.contains(jo.getString("scorerId"))) {
                                int po1 = arrayListUserId.indexOf(jo.getString("scorerId"));
                                spinner_first_scorer.setSelection(po1 + 1);
                            } else {
                                arrayListUserId.add(jo.getString("scorerId"));
                                arrayListFirstScorer.add(jo.getString("scorerName"));
                                arrayListNames.add(jo.getString("scorerName"));
                                adapterFirstScorer.notifyDataSetChanged();
                                spinner_first_scorer.setSelection(adapterFirstScorer.getCount());
                            }
                        }
                        if (size > 1) {
                            JSONObject jo1 = scorers.getJSONObject(1);
                            if (jo1.has("scorerId")) {
                                if (arrayListUserId.contains(jo1.getString("scorerId"))) {
                                    int po1 = arrayListUserId.indexOf(jo1.getString("scorerId"));
                                    spinner_second_scorer.setSelection(po1 + 1);
                                } else {
                                    arrayListUserId.add(jo1.getString("scorerId"));
                                    arrayListSecondScorer.add(jo1.getString("scorerName"));
                                    arrayListNames.add(jo1.getString("scorerName"));
                                    adapterSecondScorer.notifyDataSetChanged();
                                    spinner_second_scorer.setSelection(adapterSecondScorer.getCount());
                                }
                            }
                        }
                        if (size > 2) {
                            JSONObject jo2 = scorers.getJSONObject(2);
                            if (jo2.has("scorerId")) {
                                if (arrayListUserId.contains(jo2.getString("scorerId"))) {
                                    int po1 = arrayListUserId.indexOf(jo2.getString("scorerId"));
                                    spinner_third_scorer.setSelection(po1 + 1);
                                } else {
                                    arrayListUserId.add(jo2.getString("scorerId"));
                                    arrayListThirdScorer.add(jo2.getString("scorerName"));
                                    arrayListNames.add(jo2.getString("scorerName"));
                                    adapterThirdScorer.notifyDataSetChanged();
                                    spinner_third_scorer.setSelection(adapterThirdScorer.getCount());

                                }
                            }
                        }

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerData(String linepArray) {

        arrayListScorerType.add("User");
        arrayListScorerType.add("Team Lineup");
        adapterScorerType = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, arrayListScorerType);
        spinner_select_scorer.setAdapter(adapterScorerType);
        spinner_select_scorer.setSelection(1);

        arrayListCaptain.add("Select Captain");
        arrayListWicketKeeper.add("Select Wicket Keeper");
        arrayListViceCaptain.add("Select Vice Captain");
        arrayListFirstScorer.add("Select First scorer");
        arrayListSecondScorer.add("Select Second scorer");
        arrayListThirdScorer.add("Select Third scorer");

        for (int i = 0; i < arrayList.size(); i++) {
            arrayListAvtarId.add(arrayList.get(i).getAvtarId());
            arrayListUserId.add(arrayList.get(i).getUserId());
            arrayListNames.add(arrayList.get(i).getPlayerName());
            arrayListCaptain.add(arrayList.get(i).getPlayerName());
            arrayListViceCaptain.add(arrayList.get(i).getPlayerName());
            arrayListWicketKeeper.add(arrayList.get(i).getPlayerName());
            arrayListFirstScorer.add(arrayList.get(i).getPlayerName());
            arrayListSecondScorer.add(arrayList.get(i).getPlayerName());
            arrayListThirdScorer.add(arrayList.get(i).getPlayerName());
        }

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
        setSelectedItems(linepArray);
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
        HeaderViewManager.getInstance().setHeading(true, title);
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
                if (spinner_captain.getSelectedItem().toString() != null && spinner_captain.getSelectedItemPosition() != 0) {
                    if (spinner_select_scorer.getSelectedItemPosition() == 0 && selectedUserList != null) {
                        makeJsonRequest();
                    } else {
                        if (spinner_select_scorer.getSelectedItemPosition() == 0) {
                            Toast.makeText(context, "Please select atleast one scorer", Toast.LENGTH_SHORT).show();
                        } else if (spinner_first_scorer.getSelectedItemPosition() == 0 && spinner_second_scorer.getSelectedItemPosition() == 0
                                && spinner_third_scorer.getSelectedItemPosition() == 0) {
                            Toast.makeText(context, "Please select atleast one scorer", Toast.LENGTH_SHORT).show();
                        } else {
                            if (spinner_first_scorer.getSelectedItem().toString().equalsIgnoreCase(spinner_second_scorer.getSelectedItem().toString())) {
                                Toast.makeText(context, "Please select different scorers", Toast.LENGTH_SHORT).show();
                            } else if (spinner_first_scorer.getSelectedItem().toString().equalsIgnoreCase(spinner_third_scorer.getSelectedItem().toString())) {
                                Toast.makeText(context, "Please select different scorers", Toast.LENGTH_SHORT).show();
                            } else if (spinner_second_scorer.getSelectedItem().toString().equalsIgnoreCase(spinner_third_scorer.getSelectedItem().toString())) {
                                Toast.makeText(context, "Please select different scorers", Toast.LENGTH_SHORT).show();
                            } else {
                                makeJsonRequest();
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Please select captain", Toast.LENGTH_SHORT).show();
                }
            }
        });
        text_userscorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_SearchUserList fragmentSearchUserList = new Fragment_SearchUserList();
                if (selectedUserList != null) {
                    Bundle b = new Bundle();
                    b.putString("selectedUser", selectedUserList.toString());
                    fragmentSearchUserList.setArguments(b);
                }
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
        spinner_captain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                text_captain.setText(spinner_captain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_vice_captain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                textViceCaptain.setText(spinner_vice_captain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_wicket_keeper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                text_wicket_keeper.setText(spinner_wicket_keeper.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_select_scorer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    text_userscorer.setVisibility(View.VISIBLE);
                    linear_teamlineup.setVisibility(View.GONE);
                } else {
                    text_userscorer.setVisibility(View.GONE);
                    linear_teamlineup.setVisibility(View.VISIBLE);
                }
                text_select_scorer.setText(spinner_select_scorer.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_first_scorer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /*if (selectedFirstScorer.equalsIgnoreCase("")) {
                    removeItemFromArrayList(spinner_first_scorer.getSelectedItem().toString(), "1");
                } else {
                    addItemInArrayList(selectedFirstScorer, "1");
                    removeItemFromArrayList(spinner_first_scorer.getSelectedItem().toString(), "1");
                }*/
                selectedFirstScorer = spinner_first_scorer.getSelectedItem().toString();
                text_first_scorer.setText(spinner_first_scorer.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_second_scorer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /*if (selectedSecondScorer.equalsIgnoreCase("")) {
                    removeItemFromArrayList(spinner_second_scorer.getSelectedItem().toString(), "2");
                } else {
                    addItemInArrayList(selectedSecondScorer, "2");
                    removeItemFromArrayList(spinner_second_scorer.getSelectedItem().toString(), "2");
                }*/
                selectedSecondScorer = spinner_second_scorer.getSelectedItem().toString();
                text_second_scorer.setText(spinner_second_scorer.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_third_scorer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               /* if (selectedThirdScorer.equalsIgnoreCase("")) {
                    removeItemFromArrayList(spinner_third_scorer.getSelectedItem().toString(), "3");
                } else {
                    addItemInArrayList(selectedThirdScorer, "3");
                    removeItemFromArrayList(spinner_third_scorer.getSelectedItem().toString(), "3");
                }*/
                selectedThirdScorer = spinner_third_scorer.getSelectedItem().toString();
                text_third_scorer.setText(spinner_third_scorer.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void removeItemFromArrayList(String name, String id) {
        if (id.equalsIgnoreCase("1")) {
            arrayListSecondScorer.remove(name);
            arrayListThirdScorer.remove(name);
        } else if (id.equalsIgnoreCase("2")) {
            arrayListFirstScorer.remove(name);
            arrayListThirdScorer.remove(name);
        } else if (id.equalsIgnoreCase("3")) {
            arrayListFirstScorer.remove(name);
            arrayListSecondScorer.remove(name);
        }
    }

    private void addItemInArrayList(String name, String id) {
        if (id.equalsIgnoreCase("1")) {
            if (!name.equalsIgnoreCase("Select First scorer")) {
                arrayListSecondScorer.add(name);
                arrayListThirdScorer.add(name);
            }
        } else if (id.equalsIgnoreCase("2")) {
            if (!name.equalsIgnoreCase("Select Second scorer")) {
                arrayListFirstScorer.add(name);
                arrayListThirdScorer.add(name);
            }
        } else if (id.equalsIgnoreCase("3")) {
            if (!name.equalsIgnoreCase("Select Third scorer")) {
                arrayListFirstScorer.add(name);
                arrayListSecondScorer.add(name);
            }
        }
    }

    private JSONObject makeJsonRequest() {
        try {
            jsonObject.put("teamCheckAvailibility", teamCheckAvailibility);
            JSONArray scorers = new JSONArray();
            if (spinner_select_scorer.getSelectedItemPosition() == 0) {
                if (selectedUserList != null) {
                    JSONArray userList = selectedUserList.getJSONArray("userList");
                    for (int i = 0; i < userList.length(); i++) {
                        JSONObject jo1 = userList.getJSONObject(i);
                        JSONObject userId = new JSONObject();
                        userId.put("userId", jo1.getString("id"));
                        userId.put("order", i + 1 + "");
                        scorers.put(userId);
                    }
                }
            } else {
                if (spinner_first_scorer.getSelectedItem() != null && spinner_first_scorer.getSelectedItemPosition() != 0) {
                    String id = gteUserIdFromList(spinner_first_scorer.getSelectedItem().toString());
                    JSONObject userId = new JSONObject();
                    userId.put("userId", id);
                    userId.put("order", "1");
                    scorers.put(userId);
                }
                if (spinner_second_scorer.getSelectedItem() != null && spinner_second_scorer.getSelectedItemPosition() != 0) {
                    String id = gteUserIdFromList(spinner_second_scorer.getSelectedItem().toString());
                    JSONObject userId = new JSONObject();
                    userId.put("userId", id);
                    userId.put("order", "2");
                    scorers.put(userId);
                }
                if (spinner_third_scorer.getSelectedItem() != null && spinner_third_scorer.getSelectedItemPosition() != 0) {
                    String id = gteUserIdFromList(spinner_third_scorer.getSelectedItem().toString());
                    JSONObject userId = new JSONObject();
                    userId.put("userId", id);
                    userId.put("order", "3");
                    scorers.put(userId);
                }
            }
            if (spinner_captain.getSelectedItem() != null && spinner_captain.getSelectedItemPosition() != 0) {
                String id = gteAvtarIdFromList(spinner_captain.getSelectedItem().toString());
                JSONObject captain = new JSONObject();
                captain.put("avatar", id);
                jsonObject.put("captain", captain);
            }
            if (spinner_vice_captain.getSelectedItem() != null && spinner_vice_captain.getSelectedItemPosition() != 0) {
                String id = gteAvtarIdFromList(spinner_vice_captain.getSelectedItem().toString());
                JSONObject captain = new JSONObject();
                captain.put("avatar", id);
                jsonObject.put("viceCaptain", captain);
            }
            if (spinner_wicket_keeper.getSelectedItem() != null && spinner_wicket_keeper.getSelectedItemPosition() != 0) {
                String id = gteAvtarIdFromList(spinner_wicket_keeper.getSelectedItem().toString());
                JSONObject captain = new JSONObject();
                captain.put("avatar", id);
                jsonObject.put("wicketKeeper", captain);
            }
            jsonObject.put("scorers", scorers);
            jsonObject.put("matchId", matchId);
            Log.e("jsonbjet", jsonObject.toString());
            sentInvite();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void sentInvite() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                //   http://sfscoring.betasportzfever.com/getMatchLineup/23/77/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.MANAGE_LINEUP_MATCH;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String gteAvtarIdFromList(String name) {
        String id = "";
        for (int i = 0; i < arrayList.size(); i++) {
            int pos = arrayListNames.indexOf(name);
            id = arrayListAvtarId.get(pos);
        }

        return id;
    }

    private String gteUserIdFromList(String name) {
        String id = "";
        for (int i = 0; i < arrayList.size(); i++) {
            int pos = arrayListNames.indexOf(name);
            id = arrayListUserId.get(pos);
        }

        return id;
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
            selectedUserList = new JSONObject(userData);
            String names = "";
            JSONArray userList = selectedUserList.getJSONArray("userList");
            Log.e("userList", "*" + userList.toString());
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
   /*   Intent in = new Intent(context, ActivityChat.class);
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
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    getTargetFragment().onActivityResult(getTargetRequestCode(), AppConstant.RESULTCODE_FINISH, new Intent());
                    context.onBackPressed();
                } else {
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
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

