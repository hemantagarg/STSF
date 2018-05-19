package com.app.sportzfever.iclasses;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.dbmodels.MatchScoreJson;
import com.app.sportzfever.models.dbmodels.TossJson;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.SportzDatabase;

import org.json.JSONObject;

import java.util.List;

public class SyncDataToServer extends IntentService implements ApiResponse {

    private Context context;
    TossJson tossdata = null;
    TossJson secondInningdata = null;
    MatchScoreJson matchScoreJsonToUpdate = null;
    int matchlineuptoupdate = 0;
    private SportzDatabase db;
    private String matchId = "";

    public SyncDataToServer() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        context = this;
        if (intent != null) {
            matchId = intent.getStringExtra("matchId");
        }
        setDatabase();
        if (AppUtils.isNetworkAvailable(context)) {
            Log.e("syncCalled", "from service class");
            syncToss();
        }
    }

    public void setDatabase() {
        db = null;
        try {
            db = new SportzDatabase(context);
            db.open();

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            db.close();
        }
    }

    private boolean syncToss() {
        boolean allTosSyced = true;
        tossdata = null;
        if (db != null) {
            try {
                db.open();

                List<MatchScoreJson> matchlineUpJson = db.fetchMatchLineup_LocalJson();
                for (int i = 0; i < matchlineUpJson.size(); i++) {
                    if (matchlineUpJson.get(i).getSynced() == 0) {
                        allTosSyced = false;
                        JSONObject jsonObject = new JSONObject(matchlineUpJson.get(i).getJsonData());
                        if (AppUtils.isNetworkAvailable(context)) {
                            matchlineuptoupdate = matchlineUpJson.get(i).getId();
                            String url = JsonApiHelper.BASEURL + JsonApiHelper.MANAGE_LINEUP_MATCH;
                            new CommonAsyncTaskHashmap(16, context, this).getqueryJsonbjectNoProgress(url, jsonObject, Request.Method.POST);
                        } else {
                            // Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                if (allTosSyced) {
                    List<TossJson> tossJsons = db.fetchTossDataJson();
                    for (int i = 0; i < tossJsons.size(); i++) {
                        if (tossJsons.get(i).getServerinningId() == 0) {
                            allTosSyced = false;
                            JSONObject jsonObject = new JSONObject(tossJsons.get(i).getJsonData());
                            if (AppUtils.isNetworkAvailable(context)) {
                                tossdata = tossJsons.get(i);
                                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_TOSS;
                                new CommonAsyncTaskHashmap(12, context, this).getqueryJsonbjectNoProgress(url, jsonObject, Request.Method.POST);
                            } else {
                                //   Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
                if (allTosSyced) {
                    List<MatchScoreJson> matchScoreJsons = db.fetchMatchScore_LocalJson();
                    for (int i = 0; i < matchScoreJsons.size(); i++) {
                        if (matchScoreJsons.get(i).getSynced() == 0) {
                            allTosSyced = false;
                            JSONObject jsonObject = new JSONObject(matchScoreJsons.get(i).getJsonData());
                            if (AppUtils.isNetworkAvailable(context)) {
                                matchScoreJsonToUpdate = matchScoreJsons.get(i);
                                String url = JsonApiHelper.BASEURL + JsonApiHelper.SAVE_SCORERS_FOR_MATCH;
                                new CommonAsyncTaskHashmap(15, context, this).getqueryJsonbjectNoProgress(url, jsonObject, Request.Method.POST);
                            } else {
                                //   Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
                if (allTosSyced) {
                    List<TossJson> secondInningJsons = db.fetchSecondInningDataJson();
                    for (int i = 0; i < secondInningJsons.size(); i++) {
                        if (secondInningJsons.get(i).getServerinningId() == 0) {
                            allTosSyced = false;
                            JSONObject jsonObject = new JSONObject(secondInningJsons.get(i).getJsonData());
                            if (AppUtils.isNetworkAvailable(context)) {
                                secondInningdata = secondInningJsons.get(i);
                                String url = JsonApiHelper.BASEURL + JsonApiHelper.STARTSECONDINNING;
                                new CommonAsyncTaskHashmap(13, context, this).getqueryJsonbjectNoProgress(url, jsonObject, Request.Method.POST);
                            } else {
                                //   Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }

            } catch (Exception e) {

            } finally {
                db.close();
            }
        }
        return allTosSyced;
    }


    @Override
    public void onPostSuccess(int position, JSONObject jObject) {
        db.open();
        try {
            if (position == 12) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");

                    db.updateTossServerID(tossdata.getId(), Integer.parseInt(data.getString("inningId")));
                    tossdata.setServerinningId(Integer.parseInt(data.getString("inningId")));
                    db.updateInningIdForMatch(tossdata.getCricket_inning_id(), tossdata.getServerinningId());
                    // syncToss();

                } else {
                    //   Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 13) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");

                    db.updateSecondInningServerID(secondInningdata.getId(), Integer.parseInt(data.getString("inningId")));
                    secondInningdata.setServerinningId(Integer.parseInt(data.getString("inningId")));
                    db.updateInningIdForMatch(secondInningdata.getCricket_inning_id(), secondInningdata.getServerinningId());
                    //    syncToss();

                } else {
                    //  Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 15) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    JSONObject data = jObject.getJSONObject("data");
                    int firstinningId = Integer.parseInt((data.getString("firstInningId") == null) ? "0" : data.getString("firstInningId"));
                    int secondinningId = Integer.parseInt((data.getString("secondInningId") == null) ? "0" : data.getString("secondInningId"));

                    if (db != null) {
                        db.open();
                        db.updateSyncStatusForMatchScore(matchScoreJsonToUpdate.getId());
                        db.updateBothInningIdForMatch(firstinningId, secondinningId, Integer.parseInt(matchId));

                    }
                    //   syncToss();

                } else {
                    //    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (position == 16) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (db != null) {
                        db.open();
                        db.updateSyncStatusForMatchLineup(matchlineuptoupdate);
                        //       syncToss();
                    }

                } else {
                    //    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());
        } finally {
            db.close();
        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }
}
