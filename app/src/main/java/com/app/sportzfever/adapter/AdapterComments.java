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
import com.app.sportzfever.models.Comments;
import com.app.sportzfever.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterComments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Comments> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterComments(Context context, OnCustomItemClicListener lis, ArrayList<Comments> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_comment, parent, false);

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

            Comments m1 = (Comments) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getUserName());
            ((CustomViewHolder) holder).text_message.setText(m1.getComment());
            //((CustomViewHolder) holder).text_date.setText(m1.getCommentDateTime());
            if (!m1.getUserProfilePicture().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getUserProfilePicture())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.logo_sportz)
                        .into(((CustomViewHolder) holder).image_viewers);
            }

            ((CustomViewHolder) holder).img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(i, 1);
                }
            });

            ((CustomViewHolder) holder).card_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemClickListener(i, 2);

                    return false;
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
        ImageView image_viewers, img_edit;
        RelativeLayout card_view;

        public CustomViewHolder(View view) {
            super(view);
            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.img_edit = (ImageView) view.findViewById(R.id.img_edit);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.card_view = (RelativeLayout) view.findViewById(R.id.card_view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        Comments m1 = (Comments) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}