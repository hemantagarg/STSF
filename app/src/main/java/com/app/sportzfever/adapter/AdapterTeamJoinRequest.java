package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.TeamJoinRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTeamJoinRequest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<TeamJoinRequest> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTeamJoinRequest(Context context, OnCustomItemClicListener lis, ArrayList<TeamJoinRequest> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_allmatchinvitation, parent, false);

            vh = new CustomViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;

    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
            this.progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof CustomViewHolder) {

            TeamJoinRequest m1 = (TeamJoinRequest) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getTeamName());
            ((CustomViewHolder) holder).text_event_type.setText(m1.getEventType());
            ((CustomViewHolder) holder).text_teamname.setText(m1.getNotificationText());
            ((CustomViewHolder) holder).text_location.setText(m1.getLocation());

            /*((CustomViewHolder) holder).text_mess age.setText(m1.getNotificationText());*/
            //((CustomViewHolder) holder).text_date.setText(m1.getMatchDate());
            if (!m1.getTeamProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeamProfilePicture())

                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).image_team);
            }

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_name, text_event_type, text_date, text_teamname, text_location;
        ImageView image_team;
        Button btn_confirm, btn_reject;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.image_team = (ImageView) view.findViewById(R.id.image_team);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_event_type = (TextView) view.findViewById(R.id.text_event_type);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.text_teamname = (TextView) view.findViewById(R.id.text_teamname);
            this.text_location = (TextView) view.findViewById(R.id.text_location);
            this.btn_reject = (Button) view.findViewById(R.id.btn_reject);
            this.btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        TeamJoinRequest m1 = (TeamJoinRequest) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}