package com.app.sportzfever.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity implements ApiResponse {

    private Activity mActivity;
    private EditText edtEmail, edtPassword, edtName, edtmobile;
    private Button btn_login;
    private TextView createAccount, forgotPassword, signin;
    private ImageView image_facebook, image_twitter;
    String latitude = "0.0", longitude = "0.0";
    RelativeLayout rel_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_new);
        mActivity = SignupActivity.this;
        initViews();
        setListener();

    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtEmail.getText().toString().equalsIgnoreCase("") && !edtPassword.getText().toString().equalsIgnoreCase("") && !edtName.getText().toString().equalsIgnoreCase("") && !edtmobile.getText().toString().equalsIgnoreCase("")) {

                    if (AppUtils.isEmailValid(edtEmail.getText().toString())) {

                        signupUser();
                    } else {
                        edtEmail.setError(getString(R.string.enter_valid_emailid));
                        edtEmail.requestFocus();
                    }
                } else {
                    if (edtEmail.getText().toString().equalsIgnoreCase("")) {
                        edtEmail.setError(getString(R.string.enter_email));
                        edtEmail.requestFocus();
                    } else if (edtPassword.getText().toString().equalsIgnoreCase("")) {
                        edtPassword.setError(getString(R.string.enter_password));
                        edtPassword.requestFocus();
                    } else if (edtmobile.getText().toString().equalsIgnoreCase("")) {
                        edtmobile.setError(getString(R.string.enter_mb));
                        edtmobile.requestFocus();
                    } else if (edtName.getText().toString().equalsIgnoreCase("")) {
                        edtName.setError(getString(R.string.enter_name));
                        edtName.requestFocus();
                    }
                }
            }
        });

        rel_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });

    }


    private void signupUser() {

        if (AppUtils.isNetworkAvailable(mActivity)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", edtEmail.getText().toString());
                jsonObject.put("password", edtPassword.getText().toString());
                jsonObject.put("name", edtName.getText().toString());
                jsonObject.put("phoneNumber", edtmobile.getText().toString());
                jsonObject.put("deviceid", "dg");
                jsonObject.put("devicetoken", AppUtils.getGcmRegistrationKey(mActivity));
                jsonObject.put("devicetype", AppConstant.DEVICE_TYPE);


                String url = JsonApiHelper.BASEURL + JsonApiHelper.SIGNUP;
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();

            }

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtmobile = (EditText) findViewById(R.id.edtmobile);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        createAccount = (TextView) findViewById(R.id.createAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        image_facebook = (ImageView) findViewById(R.id.image_facebook);
        image_twitter = (ImageView) findViewById(R.id.image_twitter);
        rel_signin = (RelativeLayout) findViewById(R.id.rel_signin);

    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {

        try {
            if (method == 1) {

                if (response.getString("result").equalsIgnoreCase("1")) {

                    JSONObject data = response.getJSONObject("data");

                    AppUtils.setUserId(mActivity, data.getString("SF_USER_ID"));
                    AppUtils.setUserName(mActivity, data.getString("Name"));
                    AppUtils.setUseremail(mActivity, data.getString("Email"));
                    AppUtils.setUserImage(mActivity, data.getString("ProfilePicture"));
                    startActivity(new Intent(mActivity, Dashboard.class));
                    finish();
                } else {

                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }
}
