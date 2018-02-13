package com.app.sportzfever.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.app.sportzfever.R;
import com.app.sportzfever.adapter.DrawerListAdapter;
import com.app.sportzfever.aynctask.CommonAsyncTaskHashmap;
import com.app.sportzfever.fragment.BaseFragment;
import com.app.sportzfever.fragment.FragmentAvtar_Details;
import com.app.sportzfever.fragment.FragmentGallery;
import com.app.sportzfever.fragment.FragmentMenuTeamList;
import com.app.sportzfever.fragment.FragmentPersonalProfileEdit;
import com.app.sportzfever.fragment.FragmentPersonal_User_Details;
import com.app.sportzfever.fragment.FragmentScoringUpcomingMatches;
import com.app.sportzfever.fragment.FragmentSportsTeamDetailList;
import com.app.sportzfever.fragment.FragmentUpcomingEvent;
import com.app.sportzfever.fragment.FragmentUpcomingMatchDetails;
import com.app.sportzfever.fragment.Fragment_AvtarMyTeam;
import com.app.sportzfever.fragment.Fragment_ChatMain;
import com.app.sportzfever.fragment.Fragment_Home;
import com.app.sportzfever.fragment.Fragment_LiveMatch_Details;
import com.app.sportzfever.fragment.Fragment_MatchFeed;
import com.app.sportzfever.fragment.Fragment_Matches;
import com.app.sportzfever.fragment.Fragment_NotificationDetails;
import com.app.sportzfever.fragment.Fragment_PastMatch_Details;
import com.app.sportzfever.fragment.Fragment_PostFeed;
import com.app.sportzfever.fragment.Fragment_Scoring;
import com.app.sportzfever.fragment.Fragment_Search;
import com.app.sportzfever.fragment.Fragment_Team;
import com.app.sportzfever.fragment.Fragment_Tournaments;
import com.app.sportzfever.fragment.Fragment_UserFeed;
import com.app.sportzfever.interfaces.ApiResponse;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.JsonApiHelper;
import com.app.sportzfever.models.DrawerListModel;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

public class Dashboard extends AppCompatActivity implements ApiResponse {

    private Context context;
    private Toolbar toolbar;
    private View main_view;
    private static final String TAG = Dashboard.class.getSimpleName();
    private FrameLayout feed_container, home_container,
            freinds_container, event_container, notification_container, chat_container;
    private TabLayout tabLayout;
    private AppBarLayout appBar;
    private ExpandableListView expendableView;
    private LinkedHashMap<String, List<DrawerListModel>> alldata;
    private ArrayList<String> groupnamelist;
    private ArrayList<String> groupnamelistId;
    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA,
    };
    private DrawerListAdapter listAdapter;
    DrawerLayout drawer;
    private String mCurrentTab;
    private ProgressBar api_loading_request;
    private ImageView image_search;
    /*
      * Fragment instance
      * */
    private static Dashboard mInstance;
    private TextView text_score, text_gallery, text_logout, text_matches, text_tournament, text_sprtsavtar, text_myprofile;
    public static volatile Fragment currentFragment;
    private HashMap<String, Stack<Fragment>> mStacks;
    private ImageView image_user, image_chat, image_notification, image_event, image_friends, image_feed, image_home;
    private int lastExpandedPosition;
    private JSONArray menucategoriesArrayTeam;
    private RelativeLayout rl_home, rl_feed, rl_friends, rl_events, rl_notification, rl_chat;
    private TextView text_teamInvite_count, text_notification_count;
    private LinearLayout ll_bottom;

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


    public void manageHeaderVisibitlity(boolean isVisible) {
        if (isVisible) {
            appBar.setVisibility(View.VISIBLE);
        } else {
            appBar.setVisibility(View.GONE);
        }
    }

    public void manageFooterVisibitlity(boolean isVisible) {
        if (isVisible) {
            //  tabLayout.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.VISIBLE);
        } else {
            //  tabLayout.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;
        mInstance = Dashboard.this;
        init();
        mStacks = new HashMap<>();
        mStacks.put(GlobalConstants.TAB_HOME_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_FEED_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_FRIENDS_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_NOTIFCATION_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_CHAT_BAR, new Stack<Fragment>());
        mStacks.put(GlobalConstants.TAB_EVENT_BAR, new Stack<Fragment>());

        pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Home(), true);
        setupTabIcons();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("type")) {
                String type = intent.getStringExtra("type");
                if (type.equalsIgnoreCase(AppConstant.PUSHTYPE_FRIEND) || type.equalsIgnoreCase(AppConstant.PUSHTYPE_TEAM)) {
                    tabLayout.getTabAt(2).select();
                    pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);
                } else if (type.equalsIgnoreCase(AppConstant.PUSHTYPE_CHAT)) {
                    tabLayout.getTabAt(5).select();
                    pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_ChatMain(), true);
                }
            }
        }
        getUserDetails();
        getMenuData();
        setListener();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            if (intent.hasExtra("type")) {
                String type = intent.getStringExtra("type");
                if (type.equalsIgnoreCase(AppConstant.PUSHTYPE_FRIEND) || type.equalsIgnoreCase(AppConstant.PUSHTYPE_TEAM)) {
                    tabLayout.getTabAt(2).select();
                    pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);
                } else if (type.equalsIgnoreCase(AppConstant.PUSHTYPE_CHAT)) {
                    tabLayout.getTabAt(5).select();
                    pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_ChatMain(), true);
                }
            }
        }

    }

    private void init() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        alldata = new LinkedHashMap<>();
        groupnamelist = new ArrayList<>();
        groupnamelistId = new ArrayList<>();
        expendableView = (ExpandableListView) findViewById(R.id.expendableView);
        listAdapter = new DrawerListAdapter(this, groupnamelist, alldata);
        expendableView.setAdapter(listAdapter);
        image_search = (ImageView) findViewById(R.id.image_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        main_view = findViewById(R.id.main_view);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        text_logout = (TextView) findViewById(R.id.text_logout);
        text_score = (TextView) findViewById(R.id.text_score);
        text_myprofile = (TextView) findViewById(R.id.text_myprofile);
        text_tournament = (TextView) findViewById(R.id.text_tournament);
        text_matches = (TextView) findViewById(R.id.text_matches);
        text_sprtsavtar = (TextView) findViewById(R.id.text_sprtsavtar);
        text_gallery = (TextView) findViewById(R.id.text_gallery);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        api_loading_request = (ProgressBar) findViewById(R.id.api_loading_request);
        feed_container = (FrameLayout) findViewById(R.id.feed_container);
        home_container = (FrameLayout) findViewById(R.id.home_container);
        freinds_container = (FrameLayout) findViewById(R.id.freinds_container);
        event_container = (FrameLayout) findViewById(R.id.event_container);
        chat_container = (FrameLayout) findViewById(R.id.chat_container);
        notification_container = (FrameLayout) findViewById(R.id.notification_container);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        TextView text_username = (TextView) header.findViewById(R.id.text_name);
        TextView text_email = (TextView) header.findViewById(R.id.text_email);
        text_username.setText(AppUtils.getUserName(context));
        text_email.setText(AppUtils.getUseremail(context));
        image_user = (ImageView) header.findViewById(R.id.image_user);
        image_chat = (ImageView) findViewById(R.id.image_chat);
        image_notification = (ImageView) findViewById(R.id.image_notification);
        image_event = (ImageView) findViewById(R.id.image_event);
        image_friends = (ImageView) findViewById(R.id.image_friends);
        image_home = (ImageView) findViewById(R.id.image_home);
        image_feed = (ImageView) findViewById(R.id.image_feed);

        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        rl_feed = (RelativeLayout) findViewById(R.id.rl_feed);
        rl_friends = (RelativeLayout) findViewById(R.id.rl_friends);
        rl_events = (RelativeLayout) findViewById(R.id.rl_events);
        rl_notification = (RelativeLayout) findViewById(R.id.rl_notification);
        rl_chat = (RelativeLayout) findViewById(R.id.rl_chat);

        text_teamInvite_count = (TextView) findViewById(R.id.text_teamInvite_count);
        text_notification_count = (TextView) findViewById(R.id.text_notification_count);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        if (!AppUtils.getUserImage(context).equalsIgnoreCase("")) {
            Picasso.with(context).load(AppUtils.getUserImage(context)).placeholder(R.drawable.user).transform(new CircleTransform()).into(image_user);
        }
    }


    private void setupTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_orange));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.newsfeed));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.friends));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.calendar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.bell));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.chat));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed ||
                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Team ||
                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Home ||
                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_ChatMain ||
                mStacks.get(mCurrentTab).lastElement() instanceof FragmentUpcomingEvent ||
                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_NotificationDetails) {
            manageHeaderVisibitlity(true);
            manageFooterVisibitlity(true);
        } else {
            manageHeaderVisibitlity(false);
        }
    }

    private void setWhiteColor() {
        text_score.setBackgroundColor(getResources().getColor(R.color.white));
        text_myprofile.setBackgroundColor(getResources().getColor(R.color.white));
        text_logout.setBackgroundColor(getResources().getColor(R.color.white));
        text_tournament.setBackgroundColor(getResources().getColor(R.color.white));
        text_gallery.setBackgroundColor(getResources().getColor(R.color.white));
        text_matches.setBackgroundColor(getResources().getColor(R.color.white));
        text_sprtsavtar.setBackgroundColor(getResources().getColor(R.color.white));

        text_score.setTextColor(getResources().getColor(R.color.textcolordark));
        text_myprofile.setTextColor(getResources().getColor(R.color.textcolordark));
        text_sprtsavtar.setTextColor(getResources().getColor(R.color.textcolordark));
        text_logout.setTextColor(getResources().getColor(R.color.textcolordark));
        text_matches.setTextColor(getResources().getColor(R.color.textcolordark));
        text_gallery.setTextColor(getResources().getColor(R.color.textcolordark));
        text_tournament.setTextColor(getResources().getColor(R.color.textcolordark));
    }

    public void selectTab(int position) {
        try {
            tabLayout.getTabAt(position).select();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_logout.setTextColor(getResources().getColor(R.color.red));
                text_logout.setBackgroundResource(R.drawable.text_bg);
                showLogoutBox();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        expendableView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
                Log.e("group click", "clicked" + position);
               /* if (groupnamelistId.get(position).equalsIgnoreCase("1")) {
                    if (!expendableView.isGroupExpanded(position)) {
                        expendableView.expandGroup(position);
                    } else {
                        expendableView.collapseGroup(position);
                    }
                    //  drawer.closeDrawer(GravityCompat.START);
                } else*/
                if (groupnamelistId.get(position).equalsIgnoreCase("3")) {
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, new FragmentGallery(), true);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (groupnamelistId.get(position).equalsIgnoreCase("4")) {
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, new Fragment_Matches(), true);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (groupnamelistId.get(position).equalsIgnoreCase("5")) {
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, new Fragment_Tournaments(), true);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (groupnamelistId.get(position).equalsIgnoreCase("6")) {
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, new FragmentScoringUpcomingMatches(), true);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (groupnamelistId.get(position).equalsIgnoreCase("7")) {
                    FragmentPersonal_User_Details fragmentUser_details = new FragmentPersonal_User_Details();
                    Bundle b = new Bundle();
                    b.putString("id", AppUtils.getUserId(context));
                    fragmentUser_details.setArguments(b);
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentUser_details, true);

                    drawer.closeDrawer(GravityCompat.START);
                } else if (groupnamelistId.get(position).equalsIgnoreCase("8")) {
                    showLogoutBox();
                    drawer.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pushFragments(AppConstant.CURRENT_SELECTED_TAB, new Fragment_Search(), true);
            }
        });
        expendableView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Nothing here ever fires
                System.err.println("child clicked");
                if (groupnamelistId.get(groupPosition).equalsIgnoreCase("2")) {
                    FragmentMenuTeamList fragmentMenuTeamList = new FragmentMenuTeamList();
                    Bundle bundle = new Bundle();
                    bundle.putString("array", menucategoriesArrayTeam.toString());
                    fragmentMenuTeamList.setArguments(bundle);
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentMenuTeamList, true);
                    parent.collapseGroup(groupPosition);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    List<DrawerListModel> list = alldata.get(groupnamelist.get(groupPosition));
                    Log.e("child clicked", list.get(childPosition).getSubMenu1AvatarId());
                    AppUtils.setSelectedSportId(context, list.get(childPosition).getSubMenu1Id());
                    String avtarid = list.get(childPosition).getSubMenu1AvatarId();
                    FragmentAvtar_Details fragmentAvtar_details = new FragmentAvtar_Details();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", avtarid);
                    fragmentAvtar_details.setArguments(bundle);
                    pushFragments(AppConstant.CURRENT_SELECTED_TAB, fragmentAvtar_details, true);
                    parent.collapseGroup(groupPosition);
                    drawer.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        expendableView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.e("group expand click", "clicked" + groupPosition);
                if (lastExpandedPosition != -1 && groupPosition == 1
                        && groupPosition != lastExpandedPosition) {
                    expendableView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        text_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_score.setTextColor(getResources().getColor(R.color.red));
                text_score.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(context, ScoreActivity.class);
                startActivity(intent);

            }
        });
        text_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_myprofile.setTextColor(getResources().getColor(R.color.red));
                text_myprofile.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(context, ActivityAbout.class);
                startActivity(intent);

            }
        });
        text_sprtsavtar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_sprtsavtar.setTextColor(getResources().getColor(R.color.red));
                text_sprtsavtar.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);
                pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_AvtarMyTeam(), true);

            }
        });
        text_tournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_tournament.setTextColor(getResources().getColor(R.color.red));
                text_tournament.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);

                pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Tournaments(), true);

            }
        });
        text_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_matches.setTextColor(getResources().getColor(R.color.red));
                text_matches.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);

                pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Matches(), true);
            }
        });
        text_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteColor();
                text_gallery.setTextColor(getResources().getColor(R.color.red));
                text_gallery.setBackgroundResource(R.drawable.text_bg);
                drawer.closeDrawer(GravityCompat.START);

                pushFragments(GlobalConstants.TAB_FEED_BAR, new FragmentSportsTeamDetailList(), true);
            }
        });

        rl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_home.setImageResource(R.drawable.home_orange);

                if (mStacks.get(GlobalConstants.TAB_FEED_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Home))
                        AppUtils.showErrorLog(TAG, "Feed clicked");
                    activeHomeFragment();
                } else
                    pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Home(), true);

            }
        });
        rl_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_feed.setImageResource(R.drawable.newsfeed_sel);

                if (mStacks.get(GlobalConstants.TAB_HOME_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed))
                        AppUtils.showErrorLog(TAG, "Feed clicked");
                    activeFeedFragment();
                } else
                    pushFragments(GlobalConstants.TAB_HOME_BAR, new Fragment_UserFeed(), true);

            }
        });
        rl_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_friends.setImageResource(R.drawable.friends_sel);

                if (mStacks.get(GlobalConstants.TAB_FRIENDS_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Team))
                        AppUtils.showErrorLog(TAG, "Friens clicked");
                    activeFreindsFragment();
                } else
                    pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);

            }
        });

        rl_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_event.setImageResource(R.drawable.calendar_sel);

                if (mStacks.get(GlobalConstants.TAB_EVENT_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof FragmentUpcomingEvent))
                        AppUtils.showErrorLog(TAG, "Friens clicked");
                    activeEventFragment();
                } else
                    pushFragments(GlobalConstants.TAB_EVENT_BAR, new FragmentUpcomingEvent(), true);

            }
        });

        rl_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_notification.setImageResource(R.drawable.bell_sel);

                if (mStacks.get(GlobalConstants.TAB_NOTIFCATION_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_NotificationDetails))
                        AppUtils.showErrorLog(TAG, "Notification clicked");
                    activeNotificationFragment();
                } else
                    pushFragments(GlobalConstants.TAB_NOTIFCATION_BAR, new Fragment_NotificationDetails(), true);

            }
        });
        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectImages();
                image_chat.setImageResource(R.drawable.chat_sel);

                if (mStacks.get(GlobalConstants.TAB_CHAT_BAR).size() > 0) {
                    if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_ChatMain))
                        AppUtils.showErrorLog(TAG, "Friens clicked");
                    activeChatFragment();
                } else
                    pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_ChatMain(), true);

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.home_orange);

                        if (mStacks.get(GlobalConstants.TAB_FEED_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Home))
                                AppUtils.showErrorLog(TAG, "Feed clicked");
                            activeHomeFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_FEED_BAR, new Fragment_Home(), true);

                        break;
                    case 1:
                        tab.setIcon(R.drawable.newsfeed_sel);

                        if (mStacks.get(GlobalConstants.TAB_HOME_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed))
                                AppUtils.showErrorLog(TAG, "Feed clicked");
                            activeFeedFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_HOME_BAR, new Fragment_UserFeed(), true);

                        break;
                    case 2:
                        tab.setIcon(R.drawable.friends_sel);
                        if (mStacks.get(GlobalConstants.TAB_FRIENDS_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Team))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeFreindsFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_FRIENDS_BAR, new Fragment_Team(), true);

                        break;
                    case 3:
                        tab.setIcon(R.drawable.calendar_sel);
                        if (mStacks.get(GlobalConstants.TAB_EVENT_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof FragmentUpcomingEvent))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeEventFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_EVENT_BAR, new FragmentUpcomingEvent(), true);


                        break;
                    case 4:
                        tab.setIcon(R.drawable.bell_sel);
                        if (mStacks.get(GlobalConstants.TAB_NOTIFCATION_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_NotificationDetails))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeNotificationFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_NOTIFCATION_BAR, new Fragment_NotificationDetails(), true);

                        break;
                    case 5:
                        tab.setIcon(R.drawable.chat_sel);
                        if (mStacks.get(GlobalConstants.TAB_CHAT_BAR).size() > 0) {
                            if (!(mStacks.get(mCurrentTab).lastElement() instanceof Fragment_ChatMain))
                                AppUtils.showErrorLog(TAG, "Friens clicked");
                            activeChatFragment();
                        } else
                            pushFragments(GlobalConstants.TAB_CHAT_BAR, new Fragment_ChatMain(), true);

                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.home_grey);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.newsfeed);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.friends);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.calendar);
                        break;
                    case 4:
                        tab.setIcon(R.drawable.bell);
                        break;
                    case 5:
                        tab.setIcon(R.drawable.chat);
                        break;

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void unSelectImages() {
        image_chat.setImageResource(R.drawable.chat);
        image_notification.setImageResource(R.drawable.bell);
        image_event.setImageResource(R.drawable.calendar);
        image_home.setImageResource(R.drawable.home_grey);
        image_feed.setImageResource(R.drawable.newsfeed);
        image_friends.setImageResource(R.drawable.friends);
    }

    /*********************************************************************************
     * Function Name - activeFeedFragment
     * <p/>
     * Description - active the view of home tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeFeedFragment() {

        mCurrentTab = GlobalConstants.TAB_HOME_BAR;
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_HOME_BAR;

        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.VISIBLE);
        freinds_container.setVisibility(View.GONE);
        home_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);
    }

    /*********************************************************************************
     * Function Name - activeFeedFragment
     * <p/>
     * Description - active the view of home tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeHomeFragment() {

        mCurrentTab = GlobalConstants.TAB_FEED_BAR;
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_FEED_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        home_container.setVisibility(View.VISIBLE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);
    }


    private void showLogoutBox() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Dashboard.this);

        alertDialog.setTitle("LOG OUT !");

        alertDialog.setMessage("Are you sure you want to Logout?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        AppUtils.setUserId(context, "");
                        AppUtils.setUseremail(context, "");
                        AppUtils.setUserName(context, "");
                        AppUtils.setGroupChatList(context, "");
                        AppUtils.setFriendList(context, "");
                        AppUtils.setChatList(context, "");
                        AppUtils.setAuthToken(context, "");

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


    /*********************************************************************************
     * Function Name - activeFreindsFragment
     * <p/>
     * Description - active the view of alert tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeFreindsFragment() {

        mCurrentTab = GlobalConstants.TAB_FRIENDS_BAR;
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_FRIENDS_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.VISIBLE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        home_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);
    }

    private void getUserDetails() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GET_USERABOUT + AppUtils.getUserId(context) + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(2, context, this).getqueryJsonbjectNoProgress(url, null, Request.Method.GET);
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*********************************************************************************
     * Function Name - activeNotificationFragment
     * <p/>
     * Description - active the view of wallet tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeNotificationFragment() {

        mCurrentTab = GlobalConstants.TAB_NOTIFCATION_BAR;
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_NOTIFCATION_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        home_container.setVisibility(View.GONE);
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
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_CHAT_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.VISIBLE);
        home_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.GONE);
        refreshMessageFragment();

    }

    /*********************************************************************************
     * Function Name - activeEventFragment
     * <p/>
     * Description - active the view of home tab manages the visibility of
     * five frames in this view
     ********************************************************************************/
    private void activeEventFragment() {

        mCurrentTab = GlobalConstants.TAB_EVENT_BAR;
        AppConstant.CURRENT_SELECTED_TAB = GlobalConstants.TAB_EVENT_BAR;
        currentFragment = (BaseFragment) mStacks.get(mCurrentTab).lastElement();
        feed_container.setVisibility(View.GONE);
        freinds_container.setVisibility(View.GONE);
        chat_container.setVisibility(View.GONE);
        notification_container.setVisibility(View.GONE);
        event_container.setVisibility(View.VISIBLE);
        home_container.setVisibility(View.GONE);

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
                ft.add(R.id.home_container, fragment);
                activeHomeFragment();
            } else if (tag.equals(GlobalConstants.TAB_HOME_BAR)) {
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

    private void refreshMessageFragment() {
        if (currentFragment instanceof Fragment_ChatMain) {
            ((Fragment_ChatMain) currentFragment).onResume();
        }
    }

    /**
     * This method is to set the get api loader
     */
    public void setProgressLoader(boolean loaderVisible) {
        if (loaderVisible) {
            api_loading_request.setVisibility(View.VISIBLE);

        } else {
            api_loading_request.setVisibility(View.GONE);
        }
    }

    public void getMenuData() {
        try {
            if (AppUtils.isNetworkAvailable(context)) {
                //  https://sfscoring.betasportzfever.com/getMenu/1/479a44a634f82b0394f78352d302ec36
             /*   HashMap<String, Object> hm = new HashMap<>();*/
                String url = JsonApiHelper.BASEURL + JsonApiHelper.GETMENU + AppUtils.getUserId(context) + "/" + AppUtils.getAuthToken(context);
                new CommonAsyncTaskHashmap(1, context, this).getqueryNoProgress(url);

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.message_network_problem), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        if (mStacks.get(mCurrentTab).size() > 0 && mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Home) {
                            AppUtils.showLog(TAG, " Current Fragment is Feed Fragment");
                            //  refreshHomeFragment();
                        }
                        if (mStacks.get(mCurrentTab).size() > 0 && mStacks.get(mCurrentTab).lastElement() instanceof Fragment_NotificationDetails) {
                            AppUtils.showLog(TAG, " Current Fragment is Notification Fragment");
                            //  refreshProfileFragment();
                        }

                        if (mStacks.get(mCurrentTab).lastElement() instanceof Fragment_UserFeed ||
                                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Team ||
                                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_Home ||
                                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_ChatMain ||
                                mStacks.get(mCurrentTab).lastElement() instanceof FragmentUpcomingEvent ||
                                mStacks.get(mCurrentTab).lastElement() instanceof Fragment_NotificationDetails) {
                            manageHeaderVisibitlity(true);
                            manageFooterVisibitlity(true);
                        } else {
                            manageHeaderVisibitlity(false);
                        }
                        refreshFragments();
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

    private void refreshFragments() {
        if (currentFragment instanceof Fragment_PostFeed) {
            ((Fragment_PostFeed) currentFragment).onResume();
        }
        if (currentFragment instanceof FragmentMenuTeamList) {
            ((FragmentMenuTeamList) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_MatchFeed) {
            ((Fragment_MatchFeed) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_Team) {
            ((Fragment_Team) currentFragment).onResume();
        }
        if (currentFragment instanceof FragmentAvtar_Details) {
            ((FragmentAvtar_Details) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_UserFeed) {
            ((Fragment_UserFeed) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_Home) {
            ((Fragment_Home) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_PastMatch_Details) {
            ((Fragment_PastMatch_Details) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_LiveMatch_Details) {
            ((Fragment_LiveMatch_Details) currentFragment).onResume();
        }
        if (currentFragment instanceof FragmentUpcomingMatchDetails) {
            ((FragmentUpcomingMatchDetails) currentFragment).onResume();
        }
        if (currentFragment instanceof FragmentPersonalProfileEdit) {
            ((FragmentPersonalProfileEdit) currentFragment).onResume();
        }
        if (currentFragment instanceof Fragment_Scoring) {
            ((Fragment_Scoring) currentFragment).onResume();
        }

    }

    public void manageNotificationCount(String unreadTeamNot) {
        if (unreadTeamNot != null && !unreadTeamNot.equals("") && !unreadTeamNot.equals("0")) {
            if (Integer.parseInt(unreadTeamNot) > 9)
                text_notification_count.setText("9+");
            else
                text_notification_count.setText(unreadTeamNot);

            text_notification_count.setVisibility(View.VISIBLE);
        } else {
            text_notification_count.setVisibility(View.GONE);
        }
    }

    public void manageTeamInviteCount(String UnreadTeamInvite) {
        if (UnreadTeamInvite != null && !UnreadTeamInvite.equals("") && !UnreadTeamInvite.equals("0")) {
            if (Integer.parseInt(UnreadTeamInvite) > 9)
                text_teamInvite_count.setText("9+");
            else
                text_teamInvite_count.setText(UnreadTeamInvite);

            text_teamInvite_count.setVisibility(View.VISIBLE);
        } else {
            text_teamInvite_count.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPostSuccess(int method, JSONObject jObject) {
        try {
            if (method == 1) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {

                    if (jObject.has("UnreadTeamNotification")) {
                        manageNotificationCount(jObject.getString("UnreadTeamNotification"));
                    }
                    if (jObject.has("UnreadTeamInvite")) {
                        manageTeamInviteCount(jObject.getString("UnreadTeamInvite"));
                    }

                    JSONObject data = jObject.getJSONObject("data");
                    JSONArray servicearr = data.getJSONArray("Menu");

                    for (int i = 0; i < servicearr.length(); i++) {
                        JSONObject headerobj = servicearr.getJSONObject(i);
                        groupnamelist.add(headerobj.getString("MenuTitle"));
                        groupnamelistId.add(headerobj.getString("MenuId"));

                        ArrayList<DrawerListModel> list = new ArrayList<>();
                        JSONArray menucategoriesArray = headerobj.getJSONArray("Menucategories");
                        Log.e("Menucategories", menucategoriesArray.toString());

                        if (menucategoriesArray.length() > 0) {
                            for (int j = 0; j < menucategoriesArray.length(); j++) {
                                JSONObject obj = menucategoriesArray.getJSONObject(j);

                                DrawerListModel model = new DrawerListModel();
                                model.setSubMenu1Id(obj.getString("SubMenu1Id"));
                                model.setSubMenu1AvatarId(obj.getString("SubMenu1AvatarId"));
                                model.setName(obj.getString("SubMenu1Name"));
                                list.add(model);
                                if (headerobj.getString("MenuId").equalsIgnoreCase("2")) {
                                    menucategoriesArrayTeam = obj.getJSONArray("SubMenu1Teams");
                                    AppUtils.setTeamList(context, menucategoriesArrayTeam.toString());
                                }
                            }
                            alldata.put(groupnamelist.get(i), list);
                        }
                    }
                    listAdapter.notifyDataSetChanged();
                    if (currentFragment instanceof FragmentMenuTeamList) {
                        ((FragmentMenuTeamList) currentFragment).onResume();
                    }
                }
            } else if (method == 2) {
                if (jObject.getString("result").equalsIgnoreCase("1")) {
                    JSONObject data = jObject.getJSONObject("data");

                    JSONObject avatar = data.getJSONObject("avatar");
                    AppUtils.setLoginUserAvtarId(context, avatar.getString("avatarId"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFail(int method, String response) {

    }
}
