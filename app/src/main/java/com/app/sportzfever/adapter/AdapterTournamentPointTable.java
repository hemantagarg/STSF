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
import com.app.sportzfever.models.ModelAllTournamentPointTables;
import com.app.sportzfever.models.ModelTournamentTeam;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTournamentPointTable extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAllTournamentPointTables> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTournamentPointTable(Context context, OnCustomItemClicListener lis, ArrayList<ModelAllTournamentPointTables> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_pointtable, parent, false);

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

            ModelAllTournamentPointTables m1 = (ModelAllTournamentPointTables) detail.get(i);

          ((CustomViewHolder) holder).pointtable_match.setText(m1.getGroupName());
          ((CustomViewHolder) holder).pointtable_won.setText(m1.getWon());
          ((CustomViewHolder) holder).pointtable_lost.setText(m1.getLost());
          ((CustomViewHolder) holder).pointtable_nrr.setText(m1.getNetRunRate());
          ((CustomViewHolder) holder).pointtable_points.setText(m1.getPoints());
          ((CustomViewHolder) holder).pointtable_teamname.setText(m1.getTeamName());


            if (!m1.getTeamProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeamProfilePicture())

                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).image_avtar);
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
        TextView pointtable_match,pointtable_won,pointtable_lost,pointtable_nrr,pointtable_points,pointtable_teamname;
        ImageView image_avtar;

RelativeLayout relmatchvs;
        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);




            this.pointtable_match = (TextView) view.findViewById(R.id.pointtable_match);
            this.pointtable_won = (TextView) view.findViewById(R.id.pointtable_won);
            this.pointtable_lost = (TextView) view.findViewById(R.id.pointtable_lost);
            this.pointtable_nrr = (TextView) view.findViewById(R.id.pointtable_nrr);
            this.pointtable_points = (TextView) view.findViewById(R.id.pointtable_points);
            this.pointtable_teamname = (TextView) view.findViewById(R.id.pointtable_teamname);




        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelAllTournamentPointTables m1 = (ModelAllTournamentPointTables) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}