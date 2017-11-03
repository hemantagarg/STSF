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
import com.app.sportzfever.models.ModelSearchPeoples;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterSearchTournamentListList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSearchPeoples> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public AdapterSearchTournamentListList(Context context, OnCustomItemClicListener lis, ArrayList<ModelSearchPeoples> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_search_peoplelist, parent, false);

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
            ModelSearchPeoples m1 = (ModelSearchPeoples) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getName());
            ((CustomViewHolder) holder).text_post.setText(m1.getTotalMatch() + " Matches");
            ((CustomViewHolder) holder).text_team.setText(m1.getNoOfTeam() + " Teams");
            ((CustomViewHolder) holder).text_about.setText(m1.getDescription());

            if (!m1.getProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getProfilePicture())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).image_viewers);
            }

            ((AdapterSearchTournamentListList.CustomViewHolder) holder).btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 1);

                }
            });
            ((CustomViewHolder) holder).rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 2);

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

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView text_name, text_message, text_date, text_team, text_post, text_friends, text_about;
        ImageView image_viewers;
        RelativeLayout rl_main;
        Button btn_confirm;

        public CustomViewHolder(View view) {
            super(view);
            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_friends = (TextView) view.findViewById(R.id.text_friends);
            this.text_about = (TextView) view.findViewById(R.id.text_about);
            this.text_post = (TextView) view.findViewById(R.id.text_post);
            this.text_team = (TextView) view.findViewById(R.id.text_team);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
            this.rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelSearchPeoples m1 = (ModelSearchPeoples) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}