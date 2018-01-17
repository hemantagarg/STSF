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
import com.app.sportzfever.models.BowlingStats;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTeamBowlingMatch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BowlingStats> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTeamBowlingMatch(Context context, OnCustomItemClicListener lis, ArrayList<BowlingStats> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_team_bowling_match, parent, false);

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

            BowlingStats m1 = (BowlingStats) detail.get(i);

            if (m1.getName() != null) {
                if (m1.getName().contains("(")) {
                    String name = m1.getName();
                    String[] names = name.split("\\(");
                    ((CustomViewHolder) holder).text_name.setText(names[0]);
                } else
                    ((CustomViewHolder) holder).text_name.setText(m1.getName());

            } else {
                ((CustomViewHolder) holder).text_name.setText("");
            }

            if (m1.getRuns() != null) {
                ((CustomViewHolder) holder).text_r.setText(m1.getRuns());
            } else {
                ((CustomViewHolder) holder).text_r.setText("");
            }

            if (m1.getNumberOfOvers() != null) {
                ((CustomViewHolder) holder).text_o.setText(m1.getNumberOfOvers());
            } else {
                ((CustomViewHolder) holder).text_o.setText("");
            }
            if (m1.getWickets() != null) {
                ((CustomViewHolder) holder).text_w.setText(m1.getWickets());
            } else {
                ((CustomViewHolder) holder).text_w.setText("");
            }

            if (m1.getEconomy() != null) {
                ((CustomViewHolder) holder).text_econ.setText(m1.getEconomy());
            } else {
                ((CustomViewHolder) holder).text_econ.setText("");
            }
          /*  if (m1.getStatus() != null) {
                ((CustomViewHolder) holder).text_status.setText(m1.getStatus());
            } else {
                ((CustomViewHolder) holder).text_status.setText("");
            }*/
            ((CustomViewHolder) holder).text_runs.setText("M: " + m1.getMaiden() + " | NB: " + m1.getTotalNoBall() + " | WD: " + m1.getTotalWideBall() + " | Dots: " + m1.getTotalDotBall());

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_name, text_status, text_r, text_o, text_w, text_runs, text_econ;
        ImageView image_avtar;

        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);


            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_status = (TextView) view.findViewById(R.id.text_status);
            this.text_r = (TextView) view.findViewById(R.id.text_r);
            this.text_o = (TextView) view.findViewById(R.id.text_o);
            this.text_econ = (TextView) view.findViewById(R.id.text_econ);
            this.text_w = (TextView) view.findViewById(R.id.text_w);
            this.text_runs = (TextView) view.findViewById(R.id.text_runs);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        BowlingStats m1 = (BowlingStats) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}