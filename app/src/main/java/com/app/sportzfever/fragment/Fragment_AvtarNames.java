package com.app.sportzfever.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.activities.Dashboard;
import com.app.sportzfever.interfaces.GlobalConstants;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06-01-2016.
 */
public class Fragment_AvtarNames extends BaseFragment {


    private Bundle b;
    private Context context;
    private ImageView image_avtar;
    private RelativeLayout rl_main;
    private TextView text_avtarteamname, text_speciality;

    public static Fragment_AvtarNames fragment_team;
    private final String TAG = Fragment_AvtarNames.class.getSimpleName();
    private String avtarid = "", user_id = "";

    public static Fragment_AvtarNames getInstance() {
        if (fragment_team == null)
            fragment_team = new Fragment_AvtarNames();
        return fragment_team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_about = inflater.inflate(R.layout.fragmentavtar_name, container, false);
        context = getActivity();
        b = getArguments();

        return view_about;
    }

    @Override
    public void onResume() {
        super.onResume();
        //  Dashboard.getInstance().manageHeaderVisibitlity(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_avtar = (ImageView) view.findViewById(R.id.image_avtar);
        text_avtarteamname = (TextView) view.findViewById(R.id.text_avtarteamname);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        text_speciality = (TextView) view.findViewById(R.id.text_speciality);
        setListener();
        getBundle();

    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            user_id = bundle.getString("avtarid");
            String data = bundle.getString("data");

            try {
                JSONObject mainData = new JSONObject(data);
                JSONObject avatar = mainData.getJSONObject("avatar");

                text_avtarteamname.setText(avatar.getString("avatarName"));
                text_speciality.setText(avatar.getString("sportName"));
                avtarid = avatar.getString("avatarId");

                String avatarProfilePic = avatar.getString("avatarProfilePic");
                if (!avatarProfilePic.equalsIgnoreCase("")) {
                    Picasso.with(context).load(avatarProfilePic).transform(new CircleTransform()).placeholder(R.drawable.user).into(image_avtar);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void setListener() {
        rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentAvtar_Details fragmentUser_details = new FragmentAvtar_Details();
                Bundle b = new Bundle();
                b.putString("id", avtarid);
                fragmentUser_details.setArguments(b);
                Dashboard.getInstance().pushFragments(GlobalConstants.TAB_FEED_BAR, fragmentUser_details, true);

            }
        });
    }

}

