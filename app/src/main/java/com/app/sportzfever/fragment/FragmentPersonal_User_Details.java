package com.app.sportzfever.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.AsyncPostDataFileResponse;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.iclasses.InternalStorageContentProvider;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.ModelAvtarMyTeam;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

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
import java.util.List;

import eu.janmuller.android.simplecropimage.CropImage;


public class FragmentPersonal_User_Details extends BaseFragment implements View.OnClickListener, ApiResponse {


    public static FragmentPersonal_User_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = FragmentPersonal_User_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address;
    private ImageView image_back, imge_user, imge_banner, image_edit;
    private Button btn_follow_team;
    private ViewPager viewPager;
    private ArrayList<ModelAvtarMyTeam> arrayList;
    JSONObject userDetailObject;
    private String id = "", path = "";
    private String isFriend = "";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private File mFileTemp, selectedFilePath;

    public static final int REQUEST_TAKE_GALLERY_VIDEO = 7;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    public static FragmentPersonal_User_Details getInstance() {
        if (vendorProfileFragment == null)
            vendorProfileFragment = new FragmentPersonal_User_Details();
        return vendorProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_details, container, false);
        mActivity = getActivity();
        String states = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(states)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(mActivity.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
        vendorProfileFragment = this;
        initViews();
        getBundle();
        setCollapsingToolbar();
        setListener();
        return view;
    }


    private void setListener() {
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage1();
            }
        });
        btn_follow_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFriend.equalsIgnoreCase("1")) {
                    followUnfollowTeam("UNFRIEND");
                } else {
                    followUnfollowTeam("ADDFRIEND");
                }
            }
        });
    }

    private void getUserDetails() {
        Dashboard.getInstance().setProgressLoader(true);
        try {
            if (AppUtils.isNetworkAvailable(mActivity)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_USERABOUT + id + "/" + AppUtils.getAuthToken(mActivity);
                new CommonAsyncTaskHashmap(2, mActivity, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void followUnfollowTeam(String ADDFRIEND) {

        if (AppUtils.isNetworkAvailable(mActivity)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fromUserId", AppUtils.getUserId(mActivity));
                jsonObject.put("toUserId", id);
                jsonObject.put("type", ADDFRIEND);

                //  https://sfscoring.betasportzfever.com/followUnfollow
                String url = JsonApiHelper.BASEURL + JsonApiHelper.ADDASFRIEND;
                new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, jsonObject, Request.Method.POST);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void setCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appBar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset < 100) {
                    collapsingToolbarLayout.setTitle(text_username.getText().toString());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }


    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }


    private void initViews() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        imge_user = (ImageView) view.findViewById(R.id.imge_user);
        imge_banner = (ImageView) view.findViewById(R.id.imge_banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        image_edit = (ImageView) view.findViewById(R.id.image_edit);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        text_username = (TextView) view.findViewById(R.id.text_username);
        text_address = (TextView) view.findViewById(R.id.text_address);
        btn_follow_team = (Button) view.findViewById(R.id.btn_follow_team);
        btn_follow_team.setVisibility(View.GONE);
        image_edit.setVisibility(View.VISIBLE);
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("About");
        tabLayout.getTabAt(1).setText("Sport Avatar");
        tabLayout.getTabAt(2).setText("Friends");
        tabLayout.getTabAt(3).setText("Following");
        tabLayout.getTabAt(4).setText("Gallery");
        btn_follow_team.setVisibility(View.GONE);
        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentUserProfile tab2 = new FragmentUserProfile();
        Bundle b = new Bundle();
        b.putString("avtarid", id);
        b.putString("data", userDetailObject.toString());
        tab2.setArguments(b);
        adapter.addFrag(tab2, "services");

        Fragment_AvtarNames tab12 = new Fragment_AvtarNames();
        Bundle b111 = new Bundle();
        b111.putString("avtarid", id);
        b111.putString("data", userDetailObject.toString());
        tab12.setArguments(b111);
        adapter.addFrag(tab12, "services");

        Fragment_UserFriend_List tab4 = new Fragment_UserFriend_List();
        Bundle b3 = new Bundle();
        b3.putString("avtarid", id);
        tab4.setArguments(b3);
        adapter.addFrag(tab4, "Reviews");

        Fragment_Following_List fragmentFollowingList = new Fragment_Following_List();
        Bundle b1 = new Bundle();
        b1.putString("avtarid", id);
        fragmentFollowingList.setArguments(b1);
        adapter.addFrag(fragmentFollowingList, "Reviews");

        Fragmentphotos tab3 = new Fragmentphotos();
        Bundle b2 = new Bundle();
        b2.putString("avtarid", id);
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Portfolio");

        viewPager.setAdapter(adapter);
    }

    private void selectImage1() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                mActivity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               /* if (items[item].equals("Take Photo")) {

                    takePicture();

                } else */
                if (items[item].equals("Choose from Library")) {

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

        Intent intent = new Intent(mActivity, CropImage.class);
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
        Log.e("onActivityResult", +requestCode + "");
        switch (requestCode) {

            case REQUEST_CODE_TAKE_PICTURE:

                startCropImage();
                break;

            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = mActivity.getContentResolver().openInputStream(data.getData());
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
                Picasso.with(mActivity).load(selectedFilePath).skipMemoryCache().transform(new CircleTransform()).into(imge_user);
                uploadPhoto();
                //     profile_image.setImageBitmap(bitmap);
                break;
        }
    }

    private void uploadPhoto() {
        Charset encoding = Charset.forName("UTF-8");
        MultipartEntity reqEntity = new MultipartEntity();
        try {
            StringBody userId = new StringBody(AppUtils.getUserId(mActivity), encoding);
            StringBody avatarId = new StringBody("", encoding);
            StringBody type = new StringBody("user", encoding);

            if (!path.equalsIgnoreCase("")) {
                FileBody filebodyimage = new FileBody(selectedFilePath);
                reqEntity.addPart("image", filebodyimage);
            }
            reqEntity.addPart("userId", userId);
            reqEntity.addPart("type", type);
            reqEntity.addPart("avatarId", avatarId);

            if (AppUtils.isNetworkAvailable(mActivity)) {
                //    https://sfscoring.betasportzfever.com/updateProfilePicture
                String url = JsonApiHelper.BASEURL + JsonApiHelper.UPDATE_PROFILE_PICTURE;
                new AsyncPostDataFileResponse(mActivity, this, 3, reqEntity, url);
            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }

    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {
                if (response.getString("result").equalsIgnoreCase("1")) {

                    //  JSONObject data = response.getJSONObject("data");
                    if (isFriend.equalsIgnoreCase("1")) {
                        isFriend = "0";
                        btn_follow_team.setText("FRIEND");
                    } else {
                        isFriend = "1";
                        btn_follow_team.setText("UNFRIEND");
                    }
                } else {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else if (method == 2) {
                Dashboard.getInstance().setProgressLoader(false);
                if (response.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = response.getJSONObject("data");
                    userDetailObject = new JSONObject();
                    userDetailObject = data;

                    String image = data.getString("profilePicture");
                    if (image != null && !image.equalsIgnoreCase("")) {
                        Picasso.with(mActivity).load(image).transform(new CircleTransform()).placeholder(R.drawable.user).into(imge_user);
                        Picasso.with(mActivity).load(image).placeholder(R.drawable.logo).into(imge_banner);
                    }
                    text_username.setText(data.getString("userName"));
                    text_address.setText(data.getString("currentLocation"));

                    isFriend = data.getString("isFriend");
                    if (isFriend.equalsIgnoreCase("1")) {
                        btn_follow_team.setText("UnFriend");
                    } else {
                        btn_follow_team.setText("Add Friend");
                    }
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    setupTabIcons();
                }
            } else if (method == 3) {
                if (response.getString("result").equalsIgnoreCase("1")) {
                    Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDetails();
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        HeaderViewManager.getInstance().InitializeHeaderView(null, view, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, mActivity.getResources().getString(R.string.add));
        HeaderViewManager.getInstance().setLeftSideHeaderView(false, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(false, R.drawable.search);

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
                mActivity.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }


    protected void setFragment(Fragment fragment) {
        Dashboard.getInstance().pushFragments(GlobalConstants.TAB_CHAT_BAR, fragment, true);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


        }

    }

}
