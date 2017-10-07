package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.ActivityChat;
import com.app.sportzfever.activities.ActivityGroupChat;
import com.app.sportzfever.adapter.AdapterChats;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelChat;
import com.app.sportzfever.utils.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Chat extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    RecyclerView list_request;
    Bundle b;
    Activity context;
    AdapterChats adapterChats;
    public static Fragment_Chat fragment_chat;
    ModelChat modelChat;
    ArrayList<ModelChat> arrayList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ConnectionDetector cd;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager layoutManager;
    int skipCount = 0;
    private boolean loading = true;
    String maxlistLength = "";
    View view;

    public static Fragment_Chat getInstance() {
        if (fragment_chat == null)
            fragment_chat = new Fragment_Chat();
        return fragment_chat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        fragment_chat = this;
        context = getActivity();
        arrayList = new ArrayList<>();
        b = getArguments();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        list_request = (RecyclerView) view.findViewById(R.id.list_request);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_request.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        setlistener();
        getRecentChatList();
       /* if (AppUtils.getChatList(context).equalsIgnoreCase("")) {

        } else {
            setData();
        }*/

    }

    private void setData() {
        try {
            String data1 = AppUtils.getChatList(context);
            JSONArray data = new JSONArray(data1);
            arrayList.clear();
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {

                ModelChat modelChat = null;
                try {
                    modelChat = gson.fromJson(data.getJSONObject(i).toString(), ModelChat.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.add(modelChat);
            }
            adapterChats = new AdapterChats(getActivity(), this, arrayList);
            list_request.setAdapter(adapterChats);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setlistener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecentChatList();
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {

        if (arrayList.get(position).getTYPE().equalsIgnoreCase("USER")) {
            Intent in = new Intent(context, ActivityChat.class);
            in.putExtra("reciever_id", arrayList.get(position).getID());
            in.putExtra("name", arrayList.get(position).getName());
            in.putExtra("image", arrayList.get(position).getProfilePic());
            startActivity(in);

        } else if (arrayList.get(position).getTYPE().equalsIgnoreCase("GROUP")) {
            Intent in = new Intent(context, ActivityGroupChat.class);

            in.putExtra("reciever_id", arrayList.get(position).getID());
            in.putExtra("name", arrayList.get(position).getName());
            in.putExtra("image", arrayList.get(position).getProfilePic());
            startActivity(in);
        }
    }

    private void getRecentChatList() {
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            skipCount = 0;
            if (AppUtils.isNetworkAvailable(context)) {
                //    http://sfscoring.betasportzfever.com/getRecentChat/1/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_RECENTCHAT + AppUtils.getUserId(context) + "/" +  AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

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
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONArray data = jObject.getJSONArray("data");
                    AppUtils.setChatList(context, data.toString());
                    arrayList.clear();
                    Gson gson = new Gson();
                    for (int i = 0; i < data.length(); i++) {

                        ModelChat modelChat = null;
                        try {
                            modelChat = gson.fromJson(data.getJSONObject(i).toString(), ModelChat.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayList.add(modelChat);
                    }
                    adapterChats = new AdapterChats(getActivity(), this, arrayList);
                    list_request.setAdapter(adapterChats);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                } else {

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                }
            } else if (position == 4) {

                JSONObject commandResult = jObject
                        .getJSONObject("commandResult");
                if (commandResult.getString("success").equalsIgnoreCase("1")) {
                    maxlistLength = commandResult.getString("total");
                    JSONArray data = commandResult.getJSONArray("data");
                    arrayList.remove(arrayList.size() - 1);
                    Gson gson = new Gson();
                    for (int i = 0; i < data.length(); i++) {

                        ModelChat modelChat = null;
                        try {
                            modelChat = gson.fromJson(data.getJSONObject(i).toString(), ModelChat.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayList.add(modelChat);
                    }
                    adapterChats.notifyDataSetChanged();
                    loading = true;
                    if (data.length() == 0) {
                        skipCount = skipCount - 10;
                        //  return;
                    }
                } else {

                    adapterChats.notifyDataSetChanged();
                    skipCount = skipCount - 10;
                    loading = true;
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

