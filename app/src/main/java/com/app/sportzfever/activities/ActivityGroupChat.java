package com.app.sportzfever.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.adapter.AdapterGroupChatDetail;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelChat;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.app.sportzfever.R.id.edit_message;


public class ActivityGroupChat extends AppCompatActivity implements OnCustomItemClicListener, ApiResponse {

    private Context mActivity;
    private RecyclerView chatList;
    private AdapterGroupChatDetail adapterChatDetail;
    private ArrayList<ModelChat> chatListData;
    private EditText edtMessage;
    private Toolbar toolbar;
    private TextView username;
    private String reciever_id = "";
    private ImageView imgSendMessage, image_user;
    private LinearLayoutManager layoutManager;
    private boolean loading = true, isActivityVisible = true;
    private String maxlistLength = "", serviceId = "";
    private ModelChat chatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail);
        mActivity = this;
        init();
        setListener();
        AppUtils.setIsChatVisible(mActivity, true);
        adapterChatDetail = new AdapterGroupChatDetail(mActivity, this, chatListData);
        chatList.setAdapter(adapterChatDetail);
        SyncDataToServer();

    }

    private void SyncDataToServer() {

        if (AppUtils.isNetworkAvailable(mActivity)) {

            String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_GROUP_CHATBOX_DATA + reciever_id + "/0";
            //   http://sfscoring.betasportzfever.com/getGroupChatboxData/11/0/efc0c68e-8bb5-11e7-8cf8-008cfa5afa52
            new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonNoProgress(url, null, Request.Method.GET);

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        edtMessage = (EditText) findViewById(edit_message);
        imgSendMessage = (ImageView) findViewById(R.id.send_message);
        image_user = (ImageView) findViewById(R.id.image_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        chatList = (RecyclerView) findViewById(R.id.list_request);
        //==============================================
        username = (TextView) findViewById(R.id.username);
        chatListData = new ArrayList<>();
        Intent in = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  getSupportActionBar().setTitle(in.getExtras().getString("name"));
        reciever_id = in.getExtras().getString("reciever_id");
        AppUtils.setChatUserId(mActivity, reciever_id);

        username.setText(in.getExtras().getString("name"));

        if (!in.getExtras().getString("image").equalsIgnoreCase("")) {
            Picasso.with(mActivity)
                    .load(in.getExtras().getString("image"))
                    .placeholder(R.drawable.user)
                    .transform(new CircleTransform())
                    .into(image_user);
        }

        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        chatList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = edtMessage.getText().toString();
                if (!edtMessage.getText().toString().equalsIgnoreCase("")) {
                    try {
                        Calendar calander = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        Date date = calander.getTime();
                        String ISO_FORMAT = "hh:mm";
                        SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
                        long time = System.currentTimeMillis();
                        ModelChat chatData = new ModelChat();
                        chatData.setSenderid(AppUtils.getUserId(mActivity));
                        chatData.setMessage(msg);
                        chatData.setSenderName(AppUtils.getUserName(mActivity));
                        chatData.setSentTime(sdf.format(date));
                        chatListData.add(chatData);
                        chatList.setAdapter(adapterChatDetail);

                        SendDataToServer();
                        edtMessage.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void SendDataToServer() {
        if (AppUtils.isNetworkAvailable(mActivity)) {
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("senderId", AppUtils.getUserId(mActivity));
                jsonObject.put("chatroomId", reciever_id);
                jsonObject.put("message", edtMessage.getText().toString());

                // http://sfscoring.betasportzfever.com/postGroupChat
                String url = JsonApiHelper.BASEURL + JsonApiHelper.POST_GROUP_CHAT;
                new CommonAsyncTaskHashmap(2, mActivity, this).getqueryJsonNoProgress(url, jsonObject, Request.Method.POST);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClickListener(int position, int flag) {

    }

    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (jObject != null) {
                if (method == 1) {

                    if (jObject.getString("result").equalsIgnoreCase("1")) {
                        JSONArray messageList = jObject.getJSONArray("data");

                        Log.e("messageList", "*" + messageList.length());
                        Log.e("chatListData", "*" + chatListData.size());
                        boolean isUpdated = false;
                        for (int i = chatListData.size(); i < messageList.length(); i++) {
                            JSONObject chat = messageList.getJSONObject(i);
                            ModelChat chatData = new ModelChat();
                            //  chatData.setReceiverId(chat.getString("recieverId"));
                            chatData.setSenderid(chat.getString("senderId"));
                            chatData.setRowType(1);
                            //     chatData.setReciever_id(chat.getString("receiverID"));
                            chatData.setMessage(chat.getString("message"));
                            chatData.setSenderName(chat.getString("senderName"));
                            // chatData.setReceiverName(chat.getString("recieverName"));
                            chatData.setSentTime(chat.getString("sentOn") + " " + chat.getString("sentTime"));
                            chatListData.add(chatData);
                            isUpdated = true;
                        }

                        //   Collections.reverse(chatListData);
                        if (isUpdated) {
                            adapterChatDetail.notifyDataSetChanged();
                            chatList.post(new Runnable() {
                                @Override
                                public void run() {
                                    chatList.smoothScrollToPosition(adapterChatDetail.getItemCount());
                                }
                            });
                        }
                    }
                    if (isActivityVisible) {
                        syncData();
                    }

                } else if (method == 2) {


                } else {
                    if (isActivityVisible) {
                        syncData();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isActivityVisible) {
                syncData();
            }

        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }


    private void syncData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SyncDataToServer();

            }
        }, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityVisible = false;
        AppUtils.setIsChatVisible(mActivity, false);
    }
}
