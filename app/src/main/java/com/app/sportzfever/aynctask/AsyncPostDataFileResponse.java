package com.app.sportzfever.aynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.utils.AppUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class AsyncPostDataFileResponse extends AsyncTask<String, Void, String> {
    String url;
    int method;
    Context context;
    ProgressDialog dialog;
    MultipartEntity requestData;
    ApiResponse listener;

    public AsyncPostDataFileResponse(Context context, ApiResponse listenerPostData, int method,
                                     MultipartEntity requestData, String url) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.method = method;
        this.requestData = requestData;
        this.url = url;
        this.listener = listenerPostData;
        this.execute(new String[]{});
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "", "Processing...");
        if (requestData == null) {
            requestData = new MultipartEntity();
        }
        Log.e("requestData", url + "***" + requestData.toString());
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String responce = "";
        HttpClient httpclient = new DefaultHttpClient();
        /*HttpConnectionParams
                .setConnectionTimeout(httpclient.getParams(), 11000); // Timeout
*/
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "undefined");
        httppost.addHeader("token", AppUtils.getAuthToken(context));
        try {
            httppost.setEntity(requestData);
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                responce = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        httpclient.getConnectionManager().shutdown();

        return responce;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        try {
            Log.e("Response", "***" + result);
            if (result != null) {
                JSONObject jObject = new JSONObject(result);
                Log.e("method", "***" + method);
                if (listener != null)
                    listener.onPostSuccess(method, jObject);
            } else {
                if (listener != null)
                    listener.onPostFail(method, "Problem to connect server, please try later.");
                Toast.makeText(context,
                        context.getString(R.string.message_problem),
                        Toast.LENGTH_LONG).show();
            }
            if (dialog != null)
                dialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onPostFail(method, context.getString(R.string.message_problem));

            //listener.onPostRequestFailed(method, "Problem to connect server, please try later.");
            if (dialog != null)
                dialog.cancel();
        }
    }
}
