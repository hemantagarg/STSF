package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.ModelAvtarMyTeam;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentTournament_Details extends BaseFragment implements View.OnClickListener, ApiResponse {

    public static FragmentTournament_Details vendorProfileFragment;
    private Activity mActivity;
    private View view;
    private final String TAG = FragmentTournament_Details.class.getSimpleName();
    private TabLayout tabLayout;
    private TextView text_username, text_address,text_date;
    private ImageView image_back, imge_user, imge_banner;
    private Button btn_booknow;
    private ViewPager viewPager;
    private ArrayList<ModelAvtarMyTeam> arrayList;
    private String id = "";

    public static FragmentTournament_Details getInstance() {
        if (vendorProfileFragment == null)
            vendorProfileFragment = new FragmentTournament_Details();
        return vendorProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tournamentdetails, container, false);
        mActivity = getActivity();
        vendorProfileFragment = this;
        initViews();
        getBundle();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setCollapsingToolbar();
        return view;
    }


    private void getData() {

        // https://sfscoring.betasportzfever.com/getTournamentDetail/3/59a5e6bfea3964e9a8e4278d26aec647

        if (AppUtils.isNetworkAvailable(mActivity)) {

            String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_TOURNAMENT_DETAIL + id + "/" + AppUtils.getAuthToken(mActivity);

            new CommonAsyncTaskHashmap(1, mActivity, this).getqueryJsonbject(url, null, Request.Method.GET);

        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
        }

    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");

            String image = bundle.getString("image");
            if (image != null && !image.equalsIgnoreCase("")) {
                Picasso.with(mActivity).load(image).transform(new CircleTransform()).placeholder(R.drawable.user).into(imge_user);
                Picasso.with(mActivity).load(image).placeholder(R.drawable.logo).into(imge_banner);
            }
            text_date.setText(bundle.getString("date"));
            getData();
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

    private void initViews() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        image_back = (ImageView) view.findViewById(R.id.image_back);
        imge_user = (ImageView) view.findViewById(R.id.imge_user);
        imge_banner = (ImageView) view.findViewById(R.id.imge_banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        text_username = (TextView) view.findViewById(R.id.text_username);
        text_address = (TextView) view.findViewById(R.id.text_address);
        text_date = (TextView) view.findViewById(R.id.text_date);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Matches");
        tabLayout.getTabAt(1).setText("Fixtures");
        tabLayout.getTabAt(2).setText("Points Table");
        tabLayout.getTabAt(3).setText("Teams");
        tabLayout.getTabAt(4).setText("Gallery");
        tabLayout.getTabAt(5).setText("Comments");
//        tabLayout.getTabAt(5).setText("Tournament Talk");

        tabLayout.setTabTextColors(getResources().getColor(R.color.textcolordark), getResources().getColor(R.color.logocolor));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragmentTournamentAllMatches tab2 = new FragmentTournamentAllMatches();
        Bundle b = new Bundle();
        b.putString("id", id);
        tab2.setArguments(b);
        adapter.addFrag(tab2, "services");

        FragmentTournamentFixture tab4 = new FragmentTournamentFixture();
        Bundle b3 = new Bundle();
        b3.putString("id", id);
        tab4.setArguments(b3);
        adapter.addFrag(tab4, "Reviews");

        FragmentTournamentPoints tab5 = new FragmentTournamentPoints();
        Bundle b4 = new Bundle();
        b4.putString("id", id);
        tab5.setArguments(b4);
        adapter.addFrag(tab5, "Tournament");


        FragmentTournamentTeam tab1 = new FragmentTournamentTeam();
        Bundle b1 = new Bundle();
        b1.putString("id", id);
        tab1.setArguments(b1);
        adapter.addFrag(tab1, "About Us");

        FragmentTournamentAlbums tab3 = new FragmentTournamentAlbums();
        Bundle b2 = new Bundle();
        b2.putString("id", id);
        tab3.setArguments(b2);
        adapter.addFrag(tab3, "Portfolio");


        Fragment_TournamentFeed matchFeed = new Fragment_TournamentFeed();
        Bundle b111 = new Bundle();
        b111.putString("id", id);
        matchFeed.setArguments(b111);
        adapter.addFrag(matchFeed, "feed");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPostSuccess(int method, JSONObject response) {
        try {
            if (method == 1) {
                if (response.getString("result").equalsIgnoreCase("1")) {

                    JSONObject data = response.getJSONObject("data");
                    text_username.setText(data.getString("name"));
                    text_address.setText(data.getString("location"));
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
