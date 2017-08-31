package com.app.sportzfever.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ApiResponse {

    private Activity mActivity;
    private EditText edtEmail, edtPassword;
    private Button btn_login;
    private TextView createAccount, forgotPassword;
    private ImageView image_facebook, image_twitter;
    String latitude = "0.0", longitude = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mActivity = LoginActivity.this;
        initViews();
        setListener();

    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtEmail.getText().toString().equalsIgnoreCase("") && !edtPassword.getText().toString().equalsIgnoreCase("")) {

                    if (AppUtils.isEmailValid(edtEmail.getText().toString())) {

                        loginUser();
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
                    }
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(mActivity, SignupActivity.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   startActivity(new Intent(mActivity, ForgotPassword.class));
            }
        });
    }


    private void loginUser() {

        if (AppUtils.isNetworkAvailable(mActivity)) {

            //http://onlineworkpro.com/trendi/api/login.php?mobile=1234567890&password=123456&gcm=fsfsdfsdfdsfsfsdf&device_type=1&device_id=
            String url = JsonApiHelper.BASEURL + JsonApiHelper.LOGIN + "mobile=" + edtEmail.getText().toString() + "&password=" + edtPassword.getText().toString()
                    + "&latitude=" + latitude + "&longitude=" + longitude + "&gcm=" + AppUtils.getGcmRegistrationKey(mActivity) + "&device_type=" + AppConstant.DEVICE_TYPE + "&device_id=" + "";
            new CommonAsyncTaskHashmap(1, mActivity, this).getquery(url);

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        createAccount = (TextView) findViewById(R.id.createAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        image_facebook = (ImageView) findViewById(R.id.image_facebook);
        image_twitter = (ImageView) findViewById(R.id.image_twitter);

    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {

        try {
            if (method == 1) {

                JSONObject commandResult = response.getJSONObject("commandResult");

                if (commandResult.getString("success").equalsIgnoreCase("1")) {

                    JSONObject data = commandResult.getJSONObject("data");

                    AppUtils.setUserId(mActivity, data.getString("UserId"));
                    AppUtils.setUserRole(mActivity, data.getString("UserType"));
                    AppUtils.setUserName(mActivity, data.getString("Name"));
                    AppUtils.setUseremail(mActivity, data.getString("Email"));
                    AppUtils.setUserMobile(mActivity, data.getString("Mobile"));
                    AppUtils.setUserImage(mActivity, data.getString("ProfilePic"));
                    AppUtils.setCategories(mActivity, data.getJSONArray("services").toString());

                   /* startActivity(new Intent(mActivity, VendorDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();*/
                } else {

                    Toast.makeText(mActivity, commandResult.getString("message"), Toast.LENGTH_SHORT).show();
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
