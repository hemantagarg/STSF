package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.activities.PickLocation;
import com.app.sportzfever.aynctask.AsyncPostDataFileResponse;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.iclasses.InternalStorageContentProvider;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.GPSTracker;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import eu.janmuller.android.simplecropimage.CropImage;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_CreateTeam extends AppCompatActivity implements ApiResponse, OnCustomItemClicListener {

    private Activity context;
    private EditText edt_about_team, edt_team_name, edt_location;
    private Button btn_browse, btn_create_team;
    TextView edt_team_logo;
    private String selectedimagespath = "";
    public static Fragment_CreateTeam fragment_friend_request;
    private final String TAG = Fragment_CreateTeam.class.getSimpleName();
    private String feedId = "";
    String latitude = "0.0", longitude = "0.0";
    private ArrayAdapter<String> adapterSports;
    private ArrayList<String> listSports = new ArrayList<>();
    private ArrayList<String> listSportsId = new ArrayList<>();
    private Spinner spinner_sports;
    private String userid = "", path = "";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private File mFileTemp, selectedFilePath;

    public static final int REQUEST_TAKE_GALLERY_VIDEO = 7;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private ImageView image_map;

    public static Fragment_CreateTeam getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_CreateTeam();
        return fragment_friend_request;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_create_team);
        context = this;
        String states = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(states)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(context.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
        init();
        GPSTracker gps = new GPSTracker(context);
        if (gps.isGPSEnabled) {
            latitude = gps.getLatitude() + "";
            longitude = gps.getLongitude() + "";
        } else {
            gps.showSettingsAlert();
        }
        setlistener();
        manageHeaderView();
    }

    private void init() {
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_about_team = (EditText) findViewById(R.id.edt_about_team);
        edt_team_name = (EditText) findViewById(R.id.edt_team_name);
        edt_team_logo = (TextView) findViewById(R.id.edt_team_logo);
        btn_browse = (Button) findViewById(R.id.btn_browse);
        btn_create_team = (Button) findViewById(R.id.btn_create_team);
        spinner_sports = (Spinner) findViewById(R.id.spinner_sports);
        image_map = (ImageView) findViewById(R.id.image_map);
        listSports.add(AppConstant.CRICKET);
        listSportsId.add("1");
        adapterSports = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listSports);
        spinner_sports.setAdapter(adapterSports);
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(context, null, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Create Team");
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
                onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setlistener() {

        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage1();
            }
        });
        btn_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_team_name.getText().toString().equalsIgnoreCase("")
                        && !edt_about_team.getText().toString().equalsIgnoreCase("")
                        && !edt_location.getText().toString().equalsIgnoreCase("")) {
                    submitPost();

                } else {
                    if (edt_team_name.getText().toString().equalsIgnoreCase("")) {
                        edt_team_name.setError("Enter your team name");
                        edt_team_name.requestFocus();
                    } else if (edt_about_team.getText().toString().equalsIgnoreCase("")) {
                        edt_about_team.setError("Say something about your team");
                        edt_about_team.requestFocus();
                    } else if (edt_location.getText().toString().equalsIgnoreCase("")) {
                        edt_location.setError("Enter team Location");
                        edt_location.requestFocus();
                    }
                }
            }
        });

        image_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps = new GPSTracker(context);
                if (gps.isGPSEnabled) {
                    Intent i = new Intent(context, PickLocation.class);
                    i.putExtra("lat", latitude);
                    i.putExtra("lng", longitude);
                    startActivityForResult(i, 511);

                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    private void selectImage1() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    takePicture();

                } else if (items[item].equals("Choose from Library")) {

                    openGallery();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                    /*
                     * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
		        	 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {

            Log.d(TAG, "cannot take picture", e);
        }
    }

    private void startCropImage() {

        Intent intent = new Intent(context, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 0);
        intent.putExtra(CropImage.ASPECT_Y, 0);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }


    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("color upload 88888", +requestCode + "");
        if (requestCode == 511 && resultCode == 512) {
            edt_location.setText(data.getStringExtra("location"));
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
        }

        switch (requestCode) {

            case REQUEST_CODE_TAKE_PICTURE:

                startCropImage();
                break;

            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {
                    Log.e(TAG, "Error while creating temp file", e);
                }
                //  upload_image.setText("Image upload successfully");
                break;

            case REQUEST_CODE_CROP_IMAGE:
                try {
                    path = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (path == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selectedFilePath = new File(path);
                Log.e("filepath", "**" + selectedFilePath);
                edt_team_logo.setText("Image Selected Successfully");
                //    uploadPhoto();
                //     profile_image.setImageBitmap(bitmap);
                break;


        }
    }

    private void submitPost() {
        Charset encoding = Charset.forName("UTF-8");
        MultipartEntity reqEntity = new MultipartEntity();
        try {
            StringBody userId = new StringBody(AppUtils.getUserId(context), encoding);
            StringBody sport = new StringBody(listSportsId.get(spinner_sports.getSelectedItemPosition()), encoding);
            StringBody name = new StringBody(edt_team_name.getText().toString(), encoding);
            StringBody description = new StringBody(edt_about_team.getText().toString(), encoding);
            StringBody location = new StringBody(edt_location.getText().toString(), encoding);
            StringBody latitude1 = new StringBody(latitude + "", encoding);
            StringBody longitude1 = new StringBody(longitude + "", encoding);

            if (!path.equalsIgnoreCase("")) {
                FileBody filebodyimage = new FileBody(selectedFilePath);
                reqEntity.addPart("profilePicture", filebodyimage);
            }
            reqEntity.addPart("userId", userId);
            reqEntity.addPart("sport", sport);
            reqEntity.addPart("name", name);
            reqEntity.addPart("description", description);
            reqEntity.addPart("location", location);
            reqEntity.addPart("lat", latitude1);
            reqEntity.addPart("lng", longitude1);

            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATE_TEAM_AVTAR;
                new AsyncPostDataFileResponse(context, Fragment_CreateTeam.this, 1, reqEntity, url);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }


    @Override
    public void onItemClickListener(int position, int flag) {

    }


    private void createTeamRequest() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userId", AppUtils.getAvtarId(context));
                jsonObject.put("sport", listSportsId.get(spinner_sports.getSelectedItemPosition()));
                jsonObject.put("name", edt_team_name.getText().toString());
                jsonObject.put("description", edt_about_team.getText().toString());
                jsonObject.put("location", edt_location.getText().toString());
                // jsonObject.put("players", "113");
                jsonObject.put("lat", latitude);
                jsonObject.put("lng", longitude);
                //http://sfscoring.betasportzfever.com/createTeamAvatar
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATE_TEAM_AVTAR;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);

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
                HeaderViewManager.getInstance().setProgressLoader(false, false);
                if (jObject.getString("result").equalsIgnoreCase("1")) {
//                    JSONArray data = jObject.getJSONArray("data");
                    Toast.makeText(context, jObject.getString("message"), Toast.LENGTH_SHORT).show();
                    context.onBackPressed();

                } else {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {
        Toast.makeText(context, getResources().getString(R.string.problem_server), Toast.LENGTH_SHORT).show();
    }
}

