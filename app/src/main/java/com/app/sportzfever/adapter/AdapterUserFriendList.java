package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelUserFriendList;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterUserFriendList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelUserFriendList> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private String currentUserId = "";

    public AdapterUserFriendList(Context context, OnCustomItemClicListener lis, ArrayList<ModelUserFriendList> list, String currentUserId) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;
        this.currentUserId = currentUserId;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_userfriendlist, parent, false);

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

            ModelUserFriendList m1 = (ModelUserFriendList) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getFriendName());

            if (!m1.getFriendProfilePic().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getFriendProfilePic())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.newsfeed)
                        .into(((CustomViewHolder) holder).image_viewers);
            }
            if (m1.getRequestStatus().equalsIgnoreCase("FRIENDS")) {
                ((CustomViewHolder) holder).btn_confirm.setText("UNFRIEND");
            }
            if (m1.getRequestStatus().equalsIgnoreCase("PENDING")) {
                ((CustomViewHolder) holder).btn_confirm.setText("RESEND");
            }
            Log.e("currentUserId", currentUserId + " * " + AppUtils.getUserId(mContext));
            if (currentUserId.equalsIgnoreCase(AppUtils.getUserId(mContext))) {
                ((CustomViewHolder) holder).btn_confirm.setVisibility(View.VISIBLE);
            } else {
                ((CustomViewHolder) holder).btn_confirm.setVisibility(View.GONE);
            }

            ((AdapterUserFriendList.CustomViewHolder) holder).btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 1);

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
        TextView text_name, text_message, text_date;
        ImageView image_viewers;

        Button btn_confirm, btn_reject;

        public CustomViewHolder(View view) {
            super(view);
            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelUserFriendList m1 = (ModelUserFriendList) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}