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
import com.app.sportzfever.models.BattingStats;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTeamBattingMatch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BattingStats> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTeamBattingMatch(Context context, OnCustomItemClicListener lis, ArrayList<BattingStats> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_team_batting_match, parent, false);

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

            BattingStats m1 = (BattingStats) detail.get(i);

            if (m1.getBatsmanAvatarName() != null) {
                ((CustomViewHolder) holder).text_name.setText(m1.getBatsmanAvatarName());
            } else {
                ((CustomViewHolder) holder).text_name.setText("");
            }

            if (m1.getRuns() != null) {
                ((CustomViewHolder) holder).text_r.setText(m1.getRuns());
            } else {
                ((CustomViewHolder) holder).text_r.setText("");
            }

            if (m1.getBalls() != null) {
                ((CustomViewHolder) holder).text_b.setText(m1.getBalls());
            } else {
                ((CustomViewHolder) holder).text_b.setText("");
            }
            if (m1.getStrikeRate() != null) {
                ((CustomViewHolder) holder).text_sr.setText(m1.getStrikeRate());
            } else {
                ((CustomViewHolder) holder).text_sr.setText("");
            }
            if (m1.getStatus() != null) {
                ((CustomViewHolder) holder).text_status.setText(m1.getOutString());
            } else {
                ((CustomViewHolder) holder).text_status.setText("");
            }
            if (m1.getFours() != null) {
                ((CustomViewHolder) holder).text_runs.setText("4*" + m1.getFours() + " | 6*" + m1.getSixes() + " | Dots*" + m1.getDotball());
            } else {
                ((CustomViewHolder) holder).text_runs.setText("");
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
        TextView text_name, text_status, text_r, text_b, text_sr, text_runs;
        ImageView image_avtar;

        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);


            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_status = (TextView) view.findViewById(R.id.text_status);
            this.text_r = (TextView) view.findViewById(R.id.text_r);
            this.text_b = (TextView) view.findViewById(R.id.text_b);
            this.text_sr = (TextView) view.findViewById(R.id.text_sr);
            this.text_runs = (TextView) view.findViewById(R.id.text_runs);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        BattingStats m1 = (BattingStats) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}