package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelAllTournament;
import com.app.sportzfever.models.ModelPastMatches;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterAlltournament extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAllTournament> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterAlltournament(Context context, OnCustomItemClicListener lis, ArrayList<ModelAllTournament> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_alltournaments, parent, false);

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

            ModelAllTournament m1 = (ModelAllTournament) detail.get(i);





            ((CustomViewHolder) holder).text_name.setText(detail.get(i).getName());
            ((CustomViewHolder) holder).text_date.setText(detail.get(i).getDatetime());
            ((CustomViewHolder) holder).text_sportname.setText("Sport"+" : "+detail.get(i).getSportName());
            ((CustomViewHolder) holder).text_status.setText("Status"+" : "+detail.get(i).getTournamentState());
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_name,text_sportname,text_status,text_score1, text_event_matchdate,text_event_type, text_date,text_teamname,text_location,text_day,text_month,text_time,text_title;
        Button btn_view;

RelativeLayout relmatchvs;
        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.text_sportname = (TextView) view.findViewById(R.id.text_sportname);
            this.text_status = (TextView) view.findViewById(R.id.text_status);
            this.btn_view = (Button) view.findViewById(R.id.btn_view);



        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelAllTournament m1 = (ModelAllTournament) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}