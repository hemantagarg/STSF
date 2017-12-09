package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
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
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterTeamAddedPlayersLineupDirect extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSportTeamList> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterTeamAddedPlayersLineupDirect(Context context, OnCustomItemClicListener lis, ArrayList<ModelSportTeamList> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_teamaddedplayers, parent, false);

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

            ((CustomViewHolder) holder).text_avtarteamname.setText(m1.getAvatarName());
            ((CustomViewHolder) holder).text_speciality.setText("(" + m1.getPlayerName() + ")");
            ((CustomViewHolder) holder).text_status.setText(m1.getAddedStatus());

            if (m1.getIsAdded() == 1) {
                ((CustomViewHolder) holder).image_status.setImageResource(R.drawable.cancel);
            } else {
                ((CustomViewHolder) holder).image_status.setImageResource(R.drawable.add_green);
            }
            ((CustomViewHolder) holder).image_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 2);
                }
            });

            if (!m1.getProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getProfilePicture()).transform(new CircleTransform())
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView text_avtarteamname, text_speciality, text_status;
        ImageView image_avtar, image_status;
        RelativeLayout relmatchvs;
        CardView card_view;

        public CustomViewHolder(View view) {
            super(view);

            this.text_avtarteamname = (TextView) view.findViewById(R.id.text_name);
            this.text_speciality = (TextView) view.findViewById(R.id.text_speciality);
            this.text_status = (TextView) view.findViewById(R.id.text_status);
            this.image_avtar = (ImageView) view.findViewById(R.id.image_viewers);
            this.image_status = (ImageView) view.findViewById(R.id.image_status);
            this.card_view = (CardView) view.findViewById(R.id.card_view);
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