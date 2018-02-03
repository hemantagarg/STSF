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
import com.app.sportzfever.models.ModelPastMatches;
import com.app.sportzfever.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterHomeMatches extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelPastMatches> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterHomeMatches(Context context, OnCustomItemClicListener lis, ArrayList<ModelPastMatches> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_homematches, parent, false);

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

            ModelPastMatches m1 = (ModelPastMatches) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getFirstBattingTeamName());
            ((CustomViewHolder) holder).text_teamname.setText(m1.getSecondBattingTeamName());
            ((CustomViewHolder) holder).text_event_matchdate.setText(m1.getDate() + " " + m1.getShortMonthName() + " " + m1.getYear() + " " + m1.getTime());
            ((CustomViewHolder) holder).text_location.setText(m1.getLocation());
            ((CustomViewHolder) holder).text_event_type.setText(m1.getTournamentName());

            if (m1.getTotalRunsScoredTeam1() != null && !m1.getTotalRunsScoredTeam1().equals("")) {
                ((CustomViewHolder) holder).text_score.setText(m1.getTotalRunsScoredTeam1() + "/" + m1.getFirstBattingWickets() + " " + "(" + m1.getPlayedOversTeam1() + ")");
                ((CustomViewHolder) holder).text_score.setVisibility(View.VISIBLE);
            } else {
                ((CustomViewHolder) holder).text_score.setVisibility(View.INVISIBLE);
            }
            if (m1.getTotalRunsScoredTeam2() != null && !m1.getTotalRunsScoredTeam2().equals("")) {
                ((CustomViewHolder) holder).text_score1.setText(m1.getTotalRunsScoredTeam2() + "/" + m1.getSecondBattingWickets() + " " + "(" + m1.getPlayedOversTeam2() + ")");
                ((CustomViewHolder) holder).text_score1.setVisibility(View.VISIBLE);
            } else {
                ((CustomViewHolder) holder).text_score1.setVisibility(View.INVISIBLE);
            }

            if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                ((CustomViewHolder) holder).text_match_status.setText("UPCOMING");
                ((CustomViewHolder) holder).textwinnerteam.setText("Match start at " + m1.getTime() + " on " + m1.getDate() + " " + m1.getShortMonthName() + " " + m1.getYear());
            } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_STARTED)) {
                ((CustomViewHolder) holder).text_match_status.setText("LIVE");
                ((CustomViewHolder) holder).textwinnerteam.setText("Match started at " + m1.getTime() + " on " + m1.getDate() + " " + m1.getShortMonthName() + " " + m1.getYear());
            } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_ENDED)) {
                ((CustomViewHolder) holder).textwinnerteam.setText(m1.getMatchWinnerTeamName() + " " + m1.getWinString());
                ((CustomViewHolder) holder).text_match_status.setText("PAST");
            }

            if (m1.getTotalRunsScoredTeam1() != null && !m1.getTotalRunsScoredTeam1().equals("") && m1.getTotalRunsScoredTeam2() != null && m1.getTotalRunsScoredTeam2().equals("")) {
                ((CustomViewHolder) holder).text_score1.setText("Bowling");
                ((CustomViewHolder) holder).text_score1.setVisibility(View.VISIBLE);
            }

            if (!m1.getTeam1profilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeam1profilePicture())
                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).teama);
            }
            if (!m1.getTeam2profilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeam2profilePicture())
                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).teamb);
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
        TextView text_name, textwinnerteam, text_score, text_score1, text_event_matchdate,
                text_event_type, text_match_status, text_teamname, text_location, text_title;
        ImageView teama, teamb;

        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.relmatchvs = (RelativeLayout) view.findViewById(R.id.relmatchvs);
            this.text_event_type = (TextView) view.findViewById(R.id.text_event_type);
            this.text_match_status = (TextView) view.findViewById(R.id.text_match_status);
            this.text_teamname = (TextView) view.findViewById(R.id.text_teamname);
            this.text_location = (TextView) view.findViewById(R.id.text_location);
            this.text_title = (TextView) view.findViewById(R.id.text_title);
            this.text_event_matchdate = (TextView) view.findViewById(R.id.text_event_matchdate);
            this.textwinnerteam = (TextView) view.findViewById(R.id.textwinnerteam);
            this.text_score1 = (TextView) view.findViewById(R.id.text_score1);
            this.text_score = (TextView) view.findViewById(R.id.text_score);
            this.teama = (ImageView) view.findViewById(R.id.teama);
            this.teamb = (ImageView) view.findViewById(R.id.teamb);


        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelPastMatches m1 = (ModelPastMatches) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}