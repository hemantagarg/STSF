package com.app.sportzfever.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity implements ApiResponse {

    String type = "";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        context = this;
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("type")) {
                type = intent.getStringExtra("type");
            }
        }
        Log.e("gcm", AppUtils.getGcmRegistrationKey(getApplicationContext()));
        checkVersion();
    }

    public void checkVersion() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  https://sfscoring.betasportzfever.com/getSettings
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GETSETTINGS;
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {
                if (response.getString("status").equalsIgnoreCase("1")) {
                    JSONObject data = response.getJSONObject("data");
                    int currentVersion = data.getInt("anroid_app_version");
                    PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    int appVersionNumber = pinfo.versionCode;
                    if (appVersionNumber < currentVersion) {
                        showUpdateDialog();
                    } else {
                        openNextScreen();
                    }
                } else {
                    openNextScreen();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Splash.this);

        alertDialog.setTitle("Update Info !");

        alertDialog.setMessage("Please update to latest version for more amazing features!");

        alertDialog.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }

                });

        alertDialog.setNegativeButton("Later",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        openNextScreen();
                    }
                });

        alertDialog.show();

    }

    private void openNextScreen() {
        if (AppUtils.getUserId(getApplicationContext()).equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            intent.putExtra("type", type);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        openNextScreen();
    }

}
