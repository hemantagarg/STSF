package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.fragment.Fragment_Comments;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.Images;
import com.app.sportzfever.models.ModelFeed;
import com.app.sportzfever.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterFeed extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelFeed> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterFeed(Context context, OnCustomItemClicListener lis, ArrayList<ModelFeed> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_feed, parent, false);

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

            ModelFeed m1 = (ModelFeed) detail.get(i);

            ((CustomViewHolder) holder).text_name.setText(m1.getUserName());
            ((CustomViewHolder) holder).text_message.setText(Html.fromHtml(m1.getDescription()));
            ((CustomViewHolder) holder).text_time.setText(AppUtils.getTimeFromDateString(m1.getDateTime()));
            ((CustomViewHolder) holder).text_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ArrayList<Images> imagesArrayList = m1.getImages();
            if (imagesArrayList != null && imagesArrayList.size() > 0) {

                if (imagesArrayList.size() == 1) {
                    ((CustomViewHolder) holder).ll_feed_images.setVisibility(View.VISIBLE);
                    ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.GONE);

                    if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                        Picasso.with(mContext)
                                .load(imagesArrayList.get(0).getImage())
                                .into(((CustomViewHolder) holder).image_feed1);

                    }
                } else if (imagesArrayList.size() > 1) {
                    ((CustomViewHolder) holder).ll_feed_images.setVisibility(View.GONE);
                    ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.VISIBLE);

                    if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                        Picasso.with(mContext)
                                .load(imagesArrayList.get(0).getImage())
                                .into(((CustomViewHolder) holder).image_feed2);
                    }

                    if (!imagesArrayList.get(1).getImage().equalsIgnoreCase("")) {
                        Picasso.with(mContext)
                                .load(imagesArrayList.get(1).getImage())
                                .into(((CustomViewHolder) holder).image_feed3);

                    }

                }
            }

          /*  if (!m1.getUserImage().equalsIgnoreCase("")) {
                Picasso.with(mContext)
                        .load(m1.getReceiverImage())
                        .transform(new CircleTransform())
                        .placeholder(R.drawable.user)
                        .into(((CustomViewHolder) holder).image_viewers);
            }
*/
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_name, text_message, text_time, text_like, text_comment, text_share, text_imag_count;
        ImageView image_viewers, image_feed1, image_feed2, image_feed3;
        LinearLayout ll_feed_images, ll_multiple_images;
        RelativeLayout rl_moreimages;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.image_feed1 = (ImageView) view.findViewById(R.id.image_feed1);
            this.image_feed2 = (ImageView) view.findViewById(R.id.image_feed2);
            this.image_feed3 = (ImageView) view.findViewById(R.id.image_feed3);
            this.ll_feed_images = (LinearLayout) view.findViewById(R.id.ll_feed_images);
            this.rl_moreimages = (RelativeLayout) view.findViewById(R.id.rl_moreimages);
            this.ll_multiple_images = (LinearLayout) view.findViewById(R.id.ll_multiple_images);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_imag_count = (TextView) view.findViewById(R.id.text_imag_count);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_like = (TextView) view.findViewById(R.id.text_like);
            this.text_time = (TextView) view.findViewById(R.id.text_time);
            this.text_share = (TextView) view.findViewById(R.id.text_share);
            this.text_comment = (TextView) view.findViewById(R.id.text_comment);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelFeed m1 = (ModelFeed) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}