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
import com.app.sportzfever.models.ModelPerformance;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterPerformance extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelPerformance> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterPerformance(Context context, OnCustomItemClicListener lis, ArrayList<ModelPerformance> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_performance, parent, false);

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

            ModelPerformance m1 = (ModelPerformance) detail.get(i);

            if (m1.getRunScored() != null && m1.getBallPlayed() != null) {
                if (!m1.getRunScored().equalsIgnoreCase("")
                        && !m1.getBallPlayed().equalsIgnoreCase("")) {
                    ((CustomViewHolder) holder).pointtable_match.setText(m1.getRunScored() + "(" + m1.getBallPlayed() + ")");
                } else {
                    ((CustomViewHolder) holder).pointtable_match.setText("DNB");
                }
            } else {
                ((CustomViewHolder) holder).pointtable_match.setText("DNB");
            }

            if (m1.getWicketTaken() != null && m1.getRunConceded() != null) {
                if (!m1.getWicketTaken().equalsIgnoreCase("")
                        && !m1.getRunConceded().equalsIgnoreCase("")) {
                    ((CustomViewHolder) holder).pointtable_won.setText(m1.getWicketTaken() + "(" + m1.getRunConceded() + ")");
                } else {
                    ((CustomViewHolder) holder).pointtable_won.setText("DNB");

                }
            } else {
                ((CustomViewHolder) holder).pointtable_won.setText("DNB");
            }

            ((CustomViewHolder) holder).pointtable_lost.setText(m1.getMatchDate());


            ((CustomViewHolder) holder).pointtable_teamname.setText(m1.getOppositionTeamName());

            ((CustomViewHolder) holder).pointtable_nrr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 22);
                }
            });

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pointtable_match, pointtable_won, pointtable_lost, pointtable_nrr, pointtable_points, pointtable_teamname;
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
        ModelPerformance m1 = (ModelPerformance) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}