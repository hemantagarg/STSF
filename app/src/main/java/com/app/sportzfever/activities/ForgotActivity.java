package com.app.sportzfever.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotActivity extends AppCompatActivity implements ApiResponse {

    private Activity mActivity;
    private EditText edtEmail, edtPassword;
    private Button btn_login;
    private TextView createAccount, forgotPassword, signup;
    private ImageView image_facebook, image_twitter;
    String latitude = "0.0", longitude = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        mActivity = ForgotActivity.this;
        initViews();
        setListener();

    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtEmail.getText().toString().equalsIgnoreCase("")) {
                    if (AppUtils.isEmailValid(edtEmail.getText().toString())) {
                        forgotpasswordUser();
                    } else {
                        edtEmail.setError(getString(R.string.enter_valid_emailid));
                        edtEmail.requestFocus();
                    }
                } else {
                    if (edtEmail.getText().toString().equalsIgnoreCase("")) {
                        edtEmail.setError(getString(R.string.enter_email));
                        edtEmail.requestFocus();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(mActivity, SignupActivity.class));
            }
        });

    }


    private void forgotpasswordUser() {

        if (AppUtils.isNetworkAvailable(mActivity)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", edtEmail.getText().toString());
                jsonObject.put("action", "email");

                String url = JsonApiHelper.BASEURL + JsonApiHelper.FORGOT;
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
        btn_login = (Button) findViewById(R.id.btn_login);
        createAccount = (TextView) findViewById(R.id.createAccount);
        signup = (TextView) findViewById(R.id.signup);


    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {
                if (response.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(mActivity, response.getString("data"), Toast.LENGTH_SHORT).show();
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
