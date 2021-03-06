package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelSportTeamList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterHomeTeams extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSportTeamList> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterHomeTeams(Context context, OnCustomItemClicListener lis, ArrayList<ModelSportTeamList> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_home_team, parent, false);

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

            ModelSportTeamList m1 = (ModelSportTeamList) detail.get(i);

            ((CustomViewHolder) holder).lblListItem.setText(m1.getTeamName());
            ((CustomViewHolder) holder).text_location.setText("Location: "+m1.getLocation());
            ((CustomViewHolder) holder).text_captain_name.setText("Captain: "+m1.getCaptainName());

            if (!m1.getProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext).load(m1.getProfilePicture()).into(((CustomViewHolder) holder).image_logo);
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
        TextView lblListItem, text_location, text_captain_name;
        ImageView image_logo;
        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
            this.lblListItem = (TextView) view.findViewById(R.id.lblListItem);
            this.text_captain_name = (TextView) view.findViewById(R.id.text_captain_name);
            this.text_location = (TextView) view.findViewById(R.id.text_location);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 2);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelSportTeamList m1 = (ModelSportTeamList) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;

    }

}
