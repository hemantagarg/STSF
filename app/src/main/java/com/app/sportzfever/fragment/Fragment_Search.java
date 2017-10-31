package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.ConnectionDetector;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.utils.AppUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.sportzfever.R.drawable.search;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_Search extends BaseFragment implements ApiResponse {

    private Bundle b;
    private Activity context;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private ViewPager viewPager;
    private ConnectionDetector cd;
    JSONObject allDataJson;
    private EditText edt_search;
    View view_about;
    private ImageView headerRightImage, headerLeftImage;

    public static Fragment_Search fragment_team;
    private final String TAG = Fragment_Search.class.getSimpleName();

    public static Fragment_Search getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_Search();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view_about = inflater.inflate(R.layout.fragment_search, container, false);
        context = getActivity();
        b = getArguments();
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);
        //  manageHeaderView();
        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {

        Dashboard.getInstance().manageHeaderVisibitlity(false);
        Dashboard.getInstance().manageFooterVisibitlity(false);

        HeaderViewManager.getInstance().InitializeHeaderView(null, view_about, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "");
        HeaderViewManager.getInstance().setLeftSideHeaderView(true, R.drawable.left_arrow);
        HeaderViewManager.getInstance().setRightSideHeaderView(true, search);
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
                context.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                if (!edt_search.getText().toString().equalsIgnoreCase("")) {

                    searchText();
                } else {
                    Toast.makeText(context, "Please enter something to search", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    private void searchText() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
             /*   1=userId
                ALL=type (ALL,PEOPLE,TEAM,TOURNAMENT,POSTS,EVENTS)
                a=search keyword
                0=index*/
                //  https://sfscoring.betasportzfever.com/search/1/ALL/a/0/479a44a634f82b0394f78352d302ec36
                String url = JsonApiHelper.BASEURL + JsonApiHelper.SEARCH + AppUtils.getUserId(context)
                        + "/" + "ALL/" + edt_search.getText().toString() + "/0/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryJsonbject(url, null, Request.Method.GET);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        headerRightImage = (ImageView) view.findViewById(R.id.headerRightImage);
        headerLeftImage = (ImageView) view.findViewById(R.id.headerLeftImage);
        setListener();
    }

    private void setListener() {
        headerRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_search.getText().toString().equalsIgnoreCase("")) {
                    searchText();
                } else {
                    Toast.makeText(context, "Please enter something to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
        headerLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    private void setupTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setText("Peoples"));
        tabLayout.addTab(tabLayout.newTab().setText("Team"));
        tabLayout.addTab(tabLayout.newTab().setText("Tournament"));
        tabLayout.addTab(tabLayout.newTab().setText("Post"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));

        tabLayout.setTabTextColors(context.getResources().getColor(R.color.textcolordark), context.getResources().getColor(R.color.red));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Fragment_SearchPeopleList fragmentSearchPeopleList = new Fragment_SearchPeopleList();
                        Bundle b11 = new Bundle();
                        b11.putString("data", allDataJson.toString());
                        b11.putString("keyword", edt_search.getText().toString());
                        fragmentSearchPeopleList.setArguments(b11);
                        setFragment(fragmentSearchPeopleList);

                        break;
                    case 1:
                        Fragment_SearchPeopleList fragmentSearchPeopleList1 = new Fragment_SearchPeopleList();
                        Bundle b1 = new Bundle();
                        b1.putString("data", allDataJson.toString());
                        b1.putString("keyword", edt_search.getText().toString());
                        fragmentSearchPeopleList1.setArguments(b1);
                        setFragment(fragmentSearchPeopleList1);
                        break;
                    case 2:
                        Fragment_SearchPeopleList fragmentSearchPeopleList2 = new Fragment_SearchPeopleList();
                        Bundle b12 = new Bundle();
                        b12.putString("data", allDataJson.toString());
                        b12.putString("keyword", edt_search.getText().toString());
                        fragmentSearchPeopleList2.setArguments(b12);
                        setFragment(fragmentSearchPeopleList2);
                        break;
                    case 3:
                        Fragment_SearchPeopleList fragmentSearchPeopleList3 = new Fragment_SearchPeopleList();
                        Bundle b13 = new Bundle();
                        b13.putString("data", allDataJson.toString());
                        b13.putString("keyword", edt_search.getText().toString());
                        fragmentSearchPeopleList3.setArguments(b13);
                        setFragment(fragmentSearchPeopleList3);
                        break;
                    case 4:
                        Fragment_SearchPeopleList fragmentSearchPeopleList4 = new Fragment_SearchPeopleList();
                        Bundle b14 = new Bundle();
                        b14.putString("data", allDataJson.toString());
                        b14.putString("keyword", edt_search.getText().toString());
                        fragmentSearchPeopleList4.setArguments(b14);
                        setFragment(fragmentSearchPeopleList4);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (method == 1) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");
                    allDataJson = data;
                    setupTabIcons();
                    Fragment_SearchPeopleList fragmentSearchPeopleList = new Fragment_SearchPeopleList();
                    Bundle b11 = new Bundle();
                    b11.putString("data", allDataJson.toString());
                    b11.putString("keyword", edt_search.getText().toString());
                    fragmentSearchPeopleList.setArguments(b11);
                    setFragment(fragmentSearchPeopleList);

                } else {

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

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

}

