package com.app.sportzfever.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.adapter.AdapterAlbumPhotoList;
import com.app.sportzfever.adapter.AdapterPhotoList;
import com.app.sportzfever.aynctask.AsyncPostDataFileResponse;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.Likes;
import com.app.sportzfever.models.ModelGallery;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_PostFeed extends BaseFragment implements ApiResponse, OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    private Likes likes;
    View mView;
    private EditText edt_text_post, edt_albumname;
    ArrayList<String> imagesPath = new ArrayList<>();
    StringBuffer stringBuffer = null;
    private Spinner spinnerShareWith;
    private String selectedimagespath = "";
    private TextView text_post;
    private RelativeLayout rl_photo, rl_album;
    public static Fragment_PostFeed fragment_friend_request;
    private final String TAG = Fragment_PostFeed.class.getSimpleName();
    private String feedId = "";
    private boolean isAlbum = false;
    private ArrayAdapter<String> adapterShare, adapterAlbum;
    private ArrayList<String> listShare = new ArrayList<>();
    private ArrayList<String> listAlbumId = new ArrayList<>();
    private ArrayList<String> listAlbumName = new ArrayList<>();
    private Spinner spinner_album;
    private LinearLayout linear_album;
    private RecyclerView recyclerPhotos, recyclerAlbumPhotos;
    private ArrayList<ModelGallery> arrayListPhotos = new ArrayList<>();
    private ArrayList<ModelGallery> arrayListPhotosAlbum = new ArrayList<>();
    private AdapterPhotoList adapterPhotoList;
    private AdapterAlbumPhotoList adapterAlbumPhotoList;
    private String userid = "";
    private String useridn = "203";

    public static Fragment_PostFeed getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new Fragment_PostFeed();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_postfeed, container, false);
        context = getActivity();
        b = getArguments();

        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        setlistener();
        getBundle();
        manageHeaderView();
        getAlbums();
    }

    private void getBundle() {
        Bundle b = getArguments();
        if (b != null) {
            userid = b.getString("id");
        }

    }

    private void init() {
        edt_text_post = (EditText) mView.findViewById(R.id.edt_text_post);
        edt_albumname = (EditText) mView.findViewById(R.id.edt_albumname);
        linear_album = (LinearLayout) mView.findViewById(R.id.linear_album);
        text_post = (TextView) mView.findViewById(R.id.text_post);
        spinnerShareWith = (Spinner) mView.findViewById(R.id.spinnerShareWith);
        spinner_album = (Spinner) mView.findViewById(R.id.spinner_album);
        rl_photo = (RelativeLayout) mView.findViewById(R.id.rl_photo);
        rl_album = (RelativeLayout) mView.findViewById(R.id.rl_album);

        recyclerPhotos = (RecyclerView) mView.findViewById(R.id.recyclerPhotos);
        recyclerAlbumPhotos = (RecyclerView) mView.findViewById(R.id.recyclerAlbumPhotos);
        recyclerPhotos.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerAlbumPhotos.setLayoutManager(new GridLayoutManager(context, 2));

        adapterAlbumPhotoList = new AdapterAlbumPhotoList(context, this, arrayListPhotosAlbum);
        recyclerAlbumPhotos.setAdapter(adapterAlbumPhotoList);
        adapterPhotoList = new AdapterPhotoList(context, this, arrayListPhotos);
        recyclerPhotos.setAdapter(adapterPhotoList);


        listShare.add(AppConstant.PUBLIC);
        listShare.add(AppConstant.FRIENDS);
        listShare.add(AppConstant.ONLYME);
        adapterShare = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listShare);
        spinnerShareWith.setAdapter(adapterShare);
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Share Post");
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
                getActivity().onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setlistener() {
        text_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_text_post.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    if (isAlbum) {
                        if (spinner_album.getSelectedItemPosition() == 0) {
                            if (!edt_albumname.getText().toString().equalsIgnoreCase("")) {
                                submitPost();
                            } else {
                                edt_albumname.setError("Please enter album name");
                                edt_albumname.requestFocus();
                            }
                        } else {
                            submitPost();
                        }
                    } else {
                        submitPost();
                    }

                }
            }
        });

        rl_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlbum = false;
                linear_album.setVisibility(View.GONE);
                recyclerPhotos.setVisibility(View.VISIBLE);
                recyclerAlbumPhotos.setVisibility(View.GONE);
                rl_photo.setBackgroundColor(ContextCompat.getColor(context, R.color.text_selected_bg));
                rl_album.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                Intent intent = new Intent(context, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });
        rl_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlbum = true;
                recyclerPhotos.setVisibility(View.GONE);
                recyclerAlbumPhotos.setVisibility(View.VISIBLE);
                rl_photo.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                rl_album.setBackgroundColor(ContextCompat.getColor(context, R.color.text_selected_bg));
                Intent intent = new Intent(context, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });
        spinner_album.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    edt_albumname.setVisibility(View.VISIBLE);
                } else {
                    edt_albumname.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images

            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            stringBuffer = new StringBuffer();
            selectedimagespath = "";
            imagesPath = new ArrayList<>();
            for (int i = 0, l = images.size(); i < l; i++) {
                imagesPath.add(images.get(i).path);
                stringBuffer.append(images.get(i).path + ",");
            }

            selectedimagespath = Arrays.deepToString(imagesPath.toArray());
            Log.e("selectedImagesPath", "*" + selectedimagespath);

            if (isAlbum) {
                linear_album.setVisibility(View.VISIBLE);
                for (int i = 0; i < imagesPath.size(); i++) {
                    ModelGallery modelGallery = new ModelGallery();
                    File file = new File(imagesPath.get(i));
                    modelGallery.setFileImage(file);
                    modelGallery.setImage(imagesPath.get(i));
                    arrayListPhotosAlbum.add(modelGallery);
                }
                adapterAlbumPhotoList.notifyDataSetChanged();

            } else {
                for (int i = 0; i < imagesPath.size(); i++) {
                    ModelGallery modelGallery = new ModelGallery();
                    File file = new File(imagesPath.get(i));
                    modelGallery.setFileImage(file);
                    modelGallery.setImage(imagesPath.get(i));
                    modelGallery.setImageDesc("");
                    arrayListPhotos.add(modelGallery);
                }
                adapterPhotoList.notifyDataSetChanged();

                linear_album.setVisibility(View.GONE);
            }
        }
    }

    private void submitPost() {
        Charset encoding = Charset.forName("UTF-8");
        MultipartEntity reqEntity = new MultipartEntity();
        try {
            StringBody userId = new StringBody(userid, encoding);
            StringBody userIdN = new StringBody(useridn, encoding);
            StringBody statusVisiblity = new StringBody(spinnerShareWith.getSelectedItem().toString(), encoding);
            StringBody statusType = new StringBody("TEXT", encoding);
            StringBody description = new StringBody(edt_text_post.getText().toString(), encoding);
            if (isAlbum) {
                if (arrayListPhotosAlbum.size() > 0) {
                    for (int i = 0; i < arrayListPhotosAlbum.size(); i++) {
                        FileBody filebodyimage = new FileBody(new File(arrayListPhotosAlbum.get(i).getImage()));
                        StringBody desc = new StringBody(arrayListPhotosAlbum.get(i).getImageDesc(), encoding);
                        reqEntity.addPart("statusImages[" + i + "]", filebodyimage);
                        reqEntity.addPart("statusImagesDesc[" + i + "]", desc);

                        Log.e("image_desc", "*" + arrayListPhotosAlbum.get(i).getImageDesc());
                    }
                }
                if (spinner_album.getSelectedItemPosition() == 0) {
                    StringBody name = new StringBody(edt_albumname.getText().toString(), encoding);
                    StringBody isNewAlbum = new StringBody("yes", encoding);
                    reqEntity.addPart("album", name);
                    reqEntity.addPart("isNewAlbum", isNewAlbum);
                } else {
                    StringBody name = new StringBody(listAlbumId.get(spinner_album.getSelectedItemPosition()), encoding);
                    reqEntity.addPart("album", name);
                    StringBody isNewAlbum = new StringBody("no", encoding);
                    reqEntity.addPart("isNewAlbum", isNewAlbum);
                }

            } else {
                if (arrayListPhotos.size() > 0) {
                    for (int i = 0; i < arrayListPhotos.size(); i++) {
                        FileBody filebodyimage = new FileBody(new File(arrayListPhotos.get(i).getImage()));
                        reqEntity.addPart("statusImages[" + i + "]", filebodyimage);
                    }
                }
            }

            Log.e("user", userid);
            Log.e("statusVisibility", spinnerShareWith.getSelectedItem().toString());
            Log.e("statusType", "TEXT");
            Log.e("description", edt_text_post.getText().toString());
            Log.e("Content-Type", "undefined");
            Log.e("Authorization", AppUtils.getAuthToken(context));

            //  reqEntity.addPart("avatar", userId);
            reqEntity.addPart("user", userId);
            reqEntity.addPart("statusVisibility", statusVisiblity);
            reqEntity.addPart("statusType", statusType);
            reqEntity.addPart("description", description);

            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.CREATESTATUS;
                new AsyncPostDataFileResponse(context, Fragment_PostFeed.this, 1, reqEntity, url);
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

    private void getAlbums() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {

                JSONObject jsonObject = new JSONObject();
                //     http://sfscoring.betasportzfever.com/getUserAlbums/203/59a5e6bfea3964e9a8e4278d26aec647
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GETUSERALBUMS + userid;
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbject(url, jsonObject, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getServicelistRefresh() {
        HeaderViewManager.getInstance().setProgressLoader(true, false);
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_LIKES + feedId;
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

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
            } else if (position == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    listAlbumId.add("-1");
                    listAlbumName.add("Create new");

                    JSONArray data = jObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        listAlbumId.add(ob.getString("albumId"));
                        listAlbumName.add(ob.getString("albumName"));
                    }
                    adapterAlbum = new ArrayAdapter<String>(context, R.layout.row_spinner, R.id.textview, listAlbumName);
                    spinner_album.setAdapter(adapterAlbum);
                } else {
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

