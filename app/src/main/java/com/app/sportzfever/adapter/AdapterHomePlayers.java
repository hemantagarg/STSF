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
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterHomePlayers extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSportTeamList> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterHomePlayers(Context context, OnCustomItemClicListener lis, ArrayList<ModelSportTeamList> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_home_players, parent, false);

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

            ((CustomViewHolder) holder).text_name.setText(m1.getBastsmanAvatarName());
            if (m1.getSpeciality().equals(AppConstant.BATSMAN)) {
                ((CustomViewHolder) holder).text_matches.setText("Matches: " + m1.getTotalMatch());
                ((CustomViewHolder) holder).text_innings.setText("Innings: " + m1.getTotalInning());
                ((CustomViewHolder) holder).text_average.setText("Average: " + m1.getBattingAvg());
                ((CustomViewHolder) holder).text_fours.setText("Fours: " + m1.getFours());
                ((CustomViewHolder) holder).text_sixes.setText("Sixes: " + m1.getSixes());
                ((CustomViewHolder) holder).text_strike_rate.setText("Strike Rate: " + m1.getStrikeRate());
                ((CustomViewHolder) holder).text_highest_score.setText("Highest Score: " + m1.getHighestScore());
                ((CustomViewHolder) holder).text_runs.setText("Runs: " + m1.getRuns());
                ((CustomViewHolder) holder).text_highest_score.setVisibility(View.VISIBLE);
            } else {
                ((CustomViewHolder) holder).text_matches.setText("Matches: " + m1.getTotalMatch());
                ((CustomViewHolder) holder).text_innings.setText("Overs: " + m1.getTotalOvers());
                ((CustomViewHolder) holder).text_average.setText("Average: " + m1.getBowlingAvg());
                ((CustomViewHolder) holder).text_fours.setText("Wickes: " + m1.getWickets());
                ((CustomViewHolder) holder).text_sixes.setText("Economy: " + m1.getEconomy());
                ((CustomViewHolder) holder).text_strike_rate.setText("Strike Rate: " + m1.getStrikeRate());
                ((CustomViewHolder) holder).text_runs.setText("Best: " + m1.getBest());
                ((CustomViewHolder) holder).text_highest_score.setVisibility(View.INVISIBLE);

            }
            if (!m1.getBatsmanAvatarPic().equalsIgnoreCase("")) {
                Picasso.with(mContext).load(m1.getBatsmanAvatarPic()).transform(new CircleTransform()).into(((CustomViewHolder) holder).image_logo);
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
        TextView text_name, text_matches, text_innings, text_runs, text_average, text_fours, text_sixes, text_strike_rate, text_highest_score;
        ImageView image_logo;
        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.image_logo = (ImageView) view.findViewById(R.id.image_logo);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_innings = (TextView) view.findViewById(R.id.text_innings);
            this.text_runs = (TextView) view.findViewById(R.id.text_runs);
            this.text_fours = (TextView) view.findViewById(R.id.text_fours);
            this.text_sixes = (TextView) view.findViewById(R.id.text_sixes);
            this.text_highest_score = (TextView) view.findViewById(R.id.text_highest_score);
            this.text_average = (TextView) view.findViewById(R.id.text_average);
            this.text_strike_rate = (TextView) view.findViewById(R.id.text_strike_rate);
            this.text_matches = (TextView) view.findViewById(R.id.text_matches);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 3);
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
