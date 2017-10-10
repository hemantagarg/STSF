package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
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

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.ModelAvtarMyTeam;

import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class FragmentAvtar_Details extends BaseFragment implements View.OnClickListener, ApiResponse {


    public static FragmentAvtar_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = FragmentAvtar_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address;
    private ImageView image_back, imge_user, imge_banner;
    private Button btn_booknow;
    private ViewPager viewPager;
    private ArrayList<ModelAvtarMyTeam> arrayList;
    private String vendorId = "";

    public static FragmentAvtar_Details getInstance() {
        if (vendorProfileFragment == null)
            vendorProfileFragment = new FragmentAvtar_Details();
        return vendorProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profileavtardetails, container, false);
        mActivity = getActivity();
        vendorProfileFragment = this;
        initViews();
        getBundle();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return view;
    }


/*
    private void getData() {

        //  http://dev.stackmindz.com/trendi/api/getvendordetail.php?freelancer_id=200

        if (AppUtils.isNetworkAvailable(mActivity)) {

            // http://dev.stackmindz.com/trendi/api/change-password.php?user_id=199&current_pwd=admin&new_pwd=123456&confirm_pwd=123456
            String url = JsonApiHelper.BASEURL + JsonApiHelper.GETVENDORDETAIL + "freelancer_id=" + vendorId;

            new CommonAsyncTaskHashmap(1, mActivity, this).getquery(url);

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }*/

    private void getBundle() {

        Bundle bundle = getArguments();
        if (bundle != null) {

            vendorId = bundle.getString(AppConstant.VENDORID);

        }
    }


    private void initViews() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        imge_user = (ImageView) view.findViewById(R.id.imge_user);
        imge_banner = (ImageView) view.findViewById(R.id.imge_banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        text_username = (TextView) view.findViewById(R.id.text_username);
        text_address = (TextView) view.findViewById(R.id.text_address);


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Bio");
        tabLayout.getTabAt(1).setText("My Teams");
        tabLayout.getTabAt(2).setText("Stats");
//        tabLayout.getTabAt(3).setText("Stats");

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentAvtarBio tab2 = new FragmentAvtarBio();
       /* Bundle b = new Bundle();
        b.putString("services", arrayList.get(0).getServies());
        tab2.setArguments(b);*/
        adapter.addFrag(tab2, "services");


        Fragment_AvtarMyTeam tab4 = new Fragment_AvtarMyTeam();
     /*   Bundle b3 = new Bundle();
        b3.putString("reviews", arrayList.get(0).getRatingArray());
        tab4.setArguments(b3);*/
        adapter.addFrag(tab4, "Reviews");


        FragmentStats tab1 = new FragmentStats();
      /*  Bundle b1 = new Bundle();
        b1.putString("business", arrayList.get(0).getBussinessDetailsArray());

        tab1.setArguments(b1);*/
        adapter.addFrag(tab1, "About Us");

   /*     FragmentGallery tab3 = new FragmentGallery();
        Bundle b2 = new Bundle();
        b2.putString("gallery", arrayList.get(0).getPortfolioDetailsArray());
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Portfolio");*/

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {

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
