package com.app.sportzfever.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.JsonApiHelper;


public class ViewMatchScoreCard extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    ProgressBar progressbar;
    WebView webview;
    private BroadcastReceiver broadcastReceiver;
    private String eventId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        context = this;
        init();
        Intent intent = getIntent();
        if (intent.hasExtra("eventId")) {
            eventId = intent.getStringExtra("eventId");
        }
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = JsonApiHelper.WEBVIEWBASEURL + JsonApiHelper.VIEWALLMATCHESSCORE + eventId;
        webview.loadUrl(url);
        Log.e("urltest", url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.reload();
            }
        }, 30000);
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void init() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Score Card");
        webview = (WebView) findViewById(R.id.webview);
    }

}
