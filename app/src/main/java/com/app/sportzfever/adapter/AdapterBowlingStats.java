package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelStats;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterBowlingStats extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelStats> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterBowlingStats(Context context, OnCustomItemClicListener lis, ArrayList<ModelStats> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_bowling_stats, parent, false);

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

            ModelStats m1 = (ModelStats) detail.get(i);

            ((CustomViewHolder) holder).text_match.setText(m1.getTotalMatch());
            ((CustomViewHolder) holder).text_inns.setText(m1.getTotalInnings());
            ((CustomViewHolder) holder).text_Runs.setText(m1.getTotalOvers());
            ((CustomViewHolder) holder).text_hs.setText(m1.getTotalRuns());
            ((CustomViewHolder) holder).text_SR.setText(m1.getTotalWickets());
            ((CustomViewHolder) holder).text_hundred.setText(m1.getBest());
            ((CustomViewHolder) holder).text_fifty.setText(m1.getFiveWickets());
            ((CustomViewHolder) holder).text_sixfour.setText(m1.getAvg());
            ((CustomViewHolder) holder).text_Ct.setText(m1.getStrikeRate());
            // ((CustomViewHolder) holder).text_St.setText(m1.getTotalStumping());
            ((CustomViewHolder) holder).text_six.setText(m1.getEconomyRate());

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_match, text_inns, text_six, text_Runs, text_hs, text_SR, text_hundred, text_fifty, text_sixfour, text_Ct, text_St;


        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);


            this.text_match = (TextView) view.findViewById(R.id.text_match);

            this.text_inns = (TextView) view.findViewById(R.id.text_inns);
            this.text_inns = (TextView) view.findViewById(R.id.text_inns);
            this.text_Runs = (TextView) view.findViewById(R.id.text_Runs);
            this.text_hs = (TextView) view.findViewById(R.id.text_hs);
            this.text_SR = (TextView) view.findViewById(R.id.text_SR);
            this.text_hundred = (TextView) view.findViewById(R.id.text_hundred);
            this.text_fifty = (TextView) view.findViewById(R.id.text_fifty);
            this.text_sixfour = (TextView) view.findViewById(R.id.text_sixfour);
            this.text_Ct = (TextView) view.findViewById(R.id.text_Ct);
            this.text_St = (TextView) view.findViewById(R.id.text_St);
            this.text_six = (TextView) view.findViewById(R.id.text_six);


        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelStats m1 = (ModelStats) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}