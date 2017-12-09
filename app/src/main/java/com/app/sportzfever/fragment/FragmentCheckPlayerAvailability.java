package com.app.sportzfever.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.iclasses.HeaderViewManager;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.interfaces.HeaderViewClickListener;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.utils.AppUtils;

/**
 * Created by admin on 06-01-2016.
 */
public class FragmentCheckPlayerAvailability extends BaseFragment implements OnCustomItemClicListener {

    private Bundle b;
    private Activity context;
    View mView;
    private Button btn_create_team;
    private CheckBox checkbox_player_availability;
    public static FragmentCheckPlayerAvailability fragment_friend_request;
    private final String TAG = FragmentCheckPlayerAvailability.class.getSimpleName();
    private String teamId = "";

    public static FragmentCheckPlayerAvailability getInstance() {
        if (fragment_friend_request == null)
            fragment_friend_request = new FragmentCheckPlayerAvailability();
        return fragment_friend_request;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.app.justclap.fragment

        mView = inflater.inflate(R.layout.fragement_check_player_availability, container, false);
        context = getActivity();
        b = getArguments();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.getInstance().manageFooterVisibitlity(false);
        Dashboard.getInstance().manageHeaderVisibitlity(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getBundle();
        setlistener();
        manageHeaderView();
    }

    private void init() {
        checkbox_player_availability = (CheckBox) mView.findViewById(R.id.checkbox_player_availability);
        checkbox_player_availability.setChecked(true);
        btn_create_team = (Button) mView.findViewById(R.id.btn_create_team);
    }

    private void getBundle() {

        if (b != null) {
            teamId = b.getString("teamId");
        }
    }


    /*******************************************************************
     * Function name - manageHeaderView
     * Description - manage the initialization, visibility and click
     * listener of view fields on Header view
     *******************************************************************/
    private void manageHeaderView() {
        Dashboard.getInstance().manageHeaderVisibitlity(false);
        HeaderViewManager.getInstance().InitializeHeaderView(null, mView, manageHeaderClick());
        HeaderViewManager.getInstance().setHeading(true, "Create Team Lineup");
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
                context.onBackPressed();
            }

            @Override
            public void onClickOfHeaderRightView() {
                //   Toast.makeText(mActivity, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setlistener() {
        btn_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox_player_availability.isChecked()) {
                    FragmentPrepareLineup fragmentPrepareLineup = new FragmentPrepareLineup();
                    Bundle bundle = new Bundle();
                     bundle.putString("teamId", teamId);
                    fragmentPrepareLineup.setArguments(bundle);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);
                } else {
                    FragmentPrepareLineupDirect fragmentPrepareLineup = new FragmentPrepareLineupDirect();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamId", teamId);
                    fragmentPrepareLineup.setArguments(bundle);
                    Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentPrepareLineup, true);

                }
            }
        });
    }

    @Override
    public void onItemClickListener(int position, int flag) {
   /*     Intent in = new Intent(context, ActivityChat.class);
        if (arrayList.get(position).getUserId().equalsIgnoreCase(AppUtils.getUserIdChat(context))) {
            in.putExtra("reciever_id", arrayList.get(position).getSenderID());
        } else {
            in.putExtra("reciever_id", arrayList.get(position).getUserId());
        }
        in.putExtra("name", arrayList.get(position).getSenderName());
        in.putExtra("image", arrayList.get(position).getReceiverImage());
        in.putExtra("searchID", arrayList.get(position).getSearchId());
        startActivity(in);*/
    }
}

