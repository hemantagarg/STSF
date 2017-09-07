package com.app.sportzfever.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.app.sportzfever.R;
import com.app.sportzfever.fragment.BaseFragment;
import com.app.sportzfever.fragment.FragmentUpcomingEvent;
import com.app.sportzfever.fragment.Fragment_MatchInvitationAvailability;
import com.app.sportzfever.fragment.Fragment_Notification;
import com.app.sportzfever.fragment.Fragment_Team;
import com.app.sportzfever.fragment.Fragment_UserFeed;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.utils.AppUtils;

import java.util.HashMap;
import java.util.Stack;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    private static final String TAG = Dashboard.class.getSimpleName();
    private FrameLayout feed_container, freinds_container, event_container, notification_container, chat_container;
    private TabLayout tabLayout;
    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA,
    };
    private String mCurrentTab;
    /*
      * Fragment instance
      * */
    private static Dashboard mInstance;
    public static volatile Fragment currentFragment;
    private  HashMap<String, Stack<Fragment>> mStacks;

    /***********************************************
     * Function Name : getInstance
     * Description : This function will return the instance of this activity.
     *
     * @return
     */
    public static Dashboard getInstance() {
        if (mInstance == null)
            mInstance = new Dashboard();
        return mInstance;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void init() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        feed_container = (FrameLayout) findViewById(R.id.feed_container);
        freinds_container = (FrameLayout) findViewById(R.id.freinds_container);
        event_container = (FrameLayout) findViewById(R.id.event_container);
        chat_container = (FrameLayout) findViewById(R.id.chat_container);
        notification_container = (FrameLayout) findViewById(R.id.notification_container);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;
        mInstance = Dashboard.this;
        init();
        mStacks = new HashMap<>();
        mStacks.put(GlobalConstants.TAB_FEED_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_FRIENDS_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_NOTIFCATION_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_CHAT_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_EVENT_BAR, new Stack<Fragment>());

        pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);
        pushFragments(GlobalConstants.TAB_NOTIFCATION_BAR, new Fragment_Notification(), true);
        pushFragments(GlobalConstants.TAB_EVENT_BAR, new FragmentUpcomingEvent(), true);
        pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_MatchInvitationAvailability(), true);
        pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_UserFeed(), true);

        setupTabIcons();
        setListener();
    }

    private void setupTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.newsfeed_sel));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.friends));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.calendar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.bell));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.chat));

    }


    @Override
    public void onResume() {
        super.onResume();
        // activeFeedFragment();
    }

    private void setListener() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.newsfeed_sel);

                        if (mStacks.get(GlobalConstants.TAB_FEED_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed))
                                AppUtils.showErrorLog(TAG, "Feed clicked");
                            activeFeedFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_UserFeed(), true);

                        break;
                    case 1:
                        tab.setIcon(R.drawable.friends_sel);
                        if (mStacks.get(GlobalConstants.TAB_FRIENDS_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Team))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeFreindsFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);


                        break;
                    case 2:
                        tab.setIcon(R.drawable.calendar_sel);
                        if (mStacks.get(GlobalConstants.TAB_EVENT_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof FragmentUpcomingEvent))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeEventFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_EVENT_BAR, new FragmentUpcomingEvent(), true);


                        break;
                    case 3:
                        tab.setIcon(R.drawable.bell_sel);
                        if (mStacks.get(GlobalConstants.TAB_NOTIFCATION_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Notification))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeNotificationFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_NOTIFCATION_BAR, new Fragment_Notification(), true);



                        break;
                    case 4:
                        tab.setIcon(R.drawable.chat_sel);
                        if (mStacks.get(GlobalConstants.TAB_CHAT_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_MatchInvitationAvailability))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeChatFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_MatchInvitationAvailability(), true);


                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.newsfeed);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.friends);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.calendar);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.bell);
                        break;
                    case 4:
                        tab.setIcon(R.drawable.chat);
                        break;

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*********************************************************************************
     * Function Name - activeFeedFragment
     * <p/>
     * Description - active the view of home tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeFeedFragment() {

        mCurrentTab = GlobalConstants.TAB_FEED_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.VISIBLE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);
    }

    /*********************************************************************************
     * Function Name - activeFreindsFragment
     * <p/>
     * Description - active the view of alert tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeFreindsFragment() {

        mCurrentTab = GlobalConstants.TAB_FRIENDS_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.VISIBLE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);


    }

    /*********************************************************************************
     * Function Name - activeNotificationFragment
     * <p/>
     * Description - active the view of wallet tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeNotificationFragment() {

        mCurrentTab = GlobalConstants.TAB_NOTIFCATION_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.VISIBLE);
        event_container.setVisibility(View.GONE);


    }

    /*********************************************************************************
     * Function Name - activeChatFragment
     * <p/>
     * Description - active the view of Profile tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeChatFragment() {

        mCurrentTab = GlobalConstants.TAB_CHAT_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.VISIBLE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);

    }

    /*********************************************************************************
     * Function Name - activeEventFragment
     * <p/>
     * Description - active the view of home tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeEventFragment() {

        mCurrentTab = GlobalConstants.TAB_EVENT_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.VISIBLE);


    }


    /*
         * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
         * show, false when we switch tabs, or adding first fragment to a tab true
         * when we are pushing more fragment into navigation stack. shouldAdd ->
         * Should add to fragment navigation stack (mStacks.get(tag)). false when we
         * are switching tabs (except for the first time) true in all other cases.
         */
    public void pushFragments(String tag, Fragment fragment, boolean ShouldAdd) {
        if (fragment != null && currentFragment != fragment) {
            currentFragment = fragment;
            mCurrentTab = tag;
            if (ShouldAdd)
                mStacks.get(tag).add(fragment);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (tag.equals(GlobalConstants.TAB_FEED_BAR)) {
                ft.add(R.id.feed_container, fragment);
                activeFeedFragment();
            } else if (tag.equals(GlobalConstants.TAB_FRIENDS_BAR)) {
                ft.add(R.id.freinds_container, fragment);
                activeFreindsFragment();
            } else if (tag.equals(GlobalConstants.TAB_EVENT_BAR)) {
                ft.add(R.id.event_container, fragment);
                activeEventFragment();
            } else if (tag.equals(GlobalConstants.TAB_CHAT_BAR)) {
                ft.add(R.id.chat_container, fragment);
                activeChatFragment();
            } else if (tag.equals(GlobalConstants.TAB_NOTIFCATION_BAR)) {
                ft.add(R.id.notification_container, fragment);
                activeNotificationFragment();
            }
            ft.commitAllowingStateLoss();
        }

    }

    /*********************************************************************************
     * Function Name - popFragments
     * <p/>
     * Description - this function is used to remove the top fragment of a
     * specific tab on back press
     ********************************************************************************/
    private void popFragments() {
        /*
         * // * Select the last fragment in current tab's stack.. which will be
		 * shown after the fragment transaction given below
		 */
        Fragment fragment = mStacks.get(mCurrentTab).elementAt(
                mStacks.get(mCurrentTab).size() - 1);

        // Fragment fragment = getLastElement(mStacks.get(mCurrentTab));
        /* pop current fragment from stack.. */
        mStacks.get(mCurrentTab).remove(fragment);
        if (mStacks != null && mStacks.get(mCurrentTab) != null && !mStacks.get(mCurrentTab).isEmpty())
            currentFragment = mStacks.get(mCurrentTab).lastElement();
           /*
         * Remove the top fragment
		 */
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        // ft.add(R.id.realtabcontent, fragment);
        ft.detach(fragment);
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        AppUtils.showLog(TAG, " ((BaseFragment) mStacks.get(mCurrentTab).lastElement()).onBackPressed() : " + ((BaseFragment) mStacks.get(mCurrentTab).lastElement()).onBackPressed());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (mStacks.get(mCurrentTab).size() > 0 &&
                    ((BaseFragment) mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false) {
                AppUtils.showErrorLog(TAG, "onBackPressed");
            /*
             * top fragment in current tab doesn't handles back press, we can do
			 * our thing, which is
			 *
			 * if current tab has only one fragment in stack, ie first fragment
			 * is showing for this tab. finish the activity else pop to previous
			 * fragment in stack for the same tab
			 */
                if (mStacks.get(mCurrentTab).size() == 1) {
                    AppUtils.showLog(TAG, "mStacks.get(mCurrentTab).size() == 1");
                    super.onBackPressed();
                    finish();
                } else {
                    AppUtils.showLog(TAG,
                            "mStacks.get(" + mCurrentTab + ").size() not equal to 1 : "
                                    + mStacks.get(mCurrentTab).size());
                    popFragments();
                    if (mStacks.get(mCurrentTab).hashCode() != 0) {
                        // refresh screens
                        if (mStacks.get(mCurrentTab).size() > 0 && mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed) {
                            AppUtils.showLog(TAG, " Current Fragment is Feed Fragment");
                            //  refreshHomeFragment();
                        }
                        if (mStacks.get(mCurrentTab).size() > 0 && mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Notification) {
                            AppUtils.showLog(TAG, " Current Fragment is Notification Fragment");
                            //  refreshProfileFragment();
                        }
                        // refreshFragments();
                    }
                }
            } else {
                // do nothing.. fragment already handled back button press.

/*
            if (mStacks.get(mCurrentTab).size() > 0 && (mStacks.get(mCurrentTab).lastElement() instanceof HealthFragment ||
                    mStacks.get(mCurrentTab).lastElement() instanceof NetworksFragment ||
                    mStacks.get(mCurrentTab).lastElement() instanceof DevicesFragment ||
                    mStacks.get(mCurrentTab).lastElement() instanceof NotificationsFragment)) {
                AppUtils.showLog(TAG, " Main Dashboards Screens displayed");
            }
*/

            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
