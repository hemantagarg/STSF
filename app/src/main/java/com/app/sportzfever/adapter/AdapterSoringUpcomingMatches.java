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
import com.app.sportzfever.models.ModelUpcomingMatches;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterSoringUpcomingMatches extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelUpcomingMatches> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterSoringUpcomingMatches(Context context, OnCustomItemClicListener lis, ArrayList<ModelUpcomingMatches> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_scoring_upcomingmatches, parent, false);

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

            ModelUpcomingMatches m1 = (ModelUpcomingMatches) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getTeam1Name());
            ((CustomViewHolder) holder).text_teamname.setText(m1.getTeam2Name());
            ((CustomViewHolder) holder).text_location.setText(m1.getLocation());
            ((CustomViewHolder) holder).text_match_status.setText(m1.getMatchStatus());
            ((CustomViewHolder) holder).textdateofmatch.setText(" Match Started At " + m1.getTime() + " On " + m1.getDate() + "  " + m1.getShortMonthName() + "  " + m1.getYear());

            ((CustomViewHolder) holder).text_scorer1.setText("Scorer for " + m1.getTeam1Name() + ":");
            ((CustomViewHolder) holder).text_scorer2.setText("Scorer for " + m1.getTeam2Name() + ":");
            ((CustomViewHolder) holder).text_scorer1name.setText(m1.getTeam1ScorerName());
            ((CustomViewHolder) holder).text_scorer2name.setText(m1.getTeam2ScorerName());

            if (!m1.getTeam1profilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeam1profilePicture())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).teama);
            }
            if (!m1.getTeam2profilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getTeam2profilePicture()).transform(new CircleTransform())

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
        TextView text_name, text_match_status, text_date, text_teamname, text_location, text_day,
                text_month, text_time, text_title, textdateofmatch, text_scorer2name, text_scorer2, text_scorer1name, text_scorer1;
        ImageView teama, teamb;

        RelativeLayout relmatchvs;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_scorer2 = (TextView) view.findViewById(R.id.text_scorer2);
            this.text_scorer1name = (TextView) view.findViewById(R.id.text_scorer1name);
            this.textdateofmatch = (TextView) view.findViewById(R.id.textdateofmatch);
            this.text_scorer1 = (TextView) view.findViewById(R.id.text_scorer1);
            this.text_scorer2name = (TextView) view.findViewById(R.id.text_scorer2name);
            this.relmatchvs = (RelativeLayout) view.findViewById(R.id.relmatchvs);
            this.text_match_status = (TextView) view.findViewById(R.id.text_match_status);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.text_teamname = (TextView) view.findViewById(R.id.text_teamname);
            this.text_location = (TextView) view.findViewById(R.id.text_location);
            this.text_title = (TextView) view.findViewById(R.id.text_title);
            this.text_day = (TextView) view.findViewById(R.id.text_day);
            this.text_month = (TextView) view.findViewById(R.id.text_month);
            this.text_time = (TextView) view.findViewById(R.id.text_time);
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
        ModelUpcomingMatches m1 = (ModelUpcomingMatches) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}