package com.app.sportzfever.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelCommentry;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTeamCommentry extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelCommentry> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTeamCommentry(Context context, OnCustomItemClicListener lis, ArrayList<ModelCommentry> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_team_commentry_header, parent, false);

            vh = new CustomViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_team_commentry, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;

    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        TextView text_bowlingString, text_out_string, text_over_count, text_over_string;

        public ProgressViewHolder(View view) {
            super(view);
            this.text_over_string = (TextView) view.findViewById(R.id.text_over_string);
            this.text_over_count = (TextView) view.findViewById(R.id.text_over_count);
            this.text_out_string = (TextView) view.findViewById(R.id.text_out_string);
            this.text_bowlingString = (TextView) view.findViewById(R.id.text_bowlingString);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof CustomViewHolder) {

            ModelCommentry m1 = (ModelCommentry) detail.get(i);
            ((CustomViewHolder) holder).text_over.setText(m1.getOver());
            ((CustomViewHolder) holder).text_wicket.setText(m1.getTeam1Name() + ": " + m1.getTotalRun() + "/" + m1.getTotalWicket());
            ((CustomViewHolder) holder).text_run_scored.setText("Runs scored: " + m1.getRunInOver());
            ((CustomViewHolder) holder).text_overstring.setText(m1.getOverString());
            ((CustomViewHolder) holder).text_batsman1.setText(m1.getBatsman1Name() + ": " + m1.getBatsman1Runs() + "(" + m1.getBatsman1balls() + ")");
            ((CustomViewHolder) holder).text_batsman2.setText(m1.getBatsman2Name() + ": " + m1.getBatsman2Runs() + "(" + m1.getBatsman2Balls() + ")");
            ((CustomViewHolder) holder).text_bowler.setText(m1.getBowlerName() + ": " + m1.getBowlerOvers() + "-" + m1.getBowlerMaiden() + "-"
                    + m1.getBowlerRuns() + "-" + m1.getBowlerWicket());

        } else {
            ModelCommentry m1 = (ModelCommentry) detail.get(i);
            ((ProgressViewHolder) holder).text_bowlingString.setText(m1.getBowlingString()+" "+m1.getRunScored());
            if (m1.getIsWicket().equalsIgnoreCase("1")) {
                ((ProgressViewHolder) holder).text_out_string.setText(m1.getOutstring());
                ((ProgressViewHolder) holder).text_out_string.setVisibility(View.VISIBLE);
            }else {
                ((ProgressViewHolder) holder).text_out_string.setVisibility(View.GONE);
            }
            ((ProgressViewHolder) holder).text_over_count.setText(m1.getInningOverCount());
            ((ProgressViewHolder) holder).text_over_string.setText(m1.getOverString());
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_over, text_wicket, text_run_scored, text_overstring, text_batsman1, text_batsman2, text_bowler;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            this.text_over = (TextView) view.findViewById(R.id.text_over);
            this.text_wicket = (TextView) view.findViewById(R.id.text_wicket);
            this.text_run_scored = (TextView) view.findViewById(R.id.text_run_scored);
            this.text_overstring = (TextView) view.findViewById(R.id.text_overstring);
            this.text_batsman1 = (TextView) view.findViewById(R.id.text_batsman1);
            this.text_batsman2 = (TextView) view.findViewById(R.id.text_batsman2);
            this.text_bowler = (TextView) view.findViewById(R.id.text_bowler);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelCommentry m1 = (ModelCommentry) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}