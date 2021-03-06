package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelGallery;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterPhotoList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ModelGallery> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public AdapterPhotoList(Context context, OnCustomItemClicListener lis, ArrayList<ModelGallery> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_feed_photos, parent, false);

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
            this.progressBar.getIndeterminateDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof CustomViewHolder) {

            ModelGallery m1 = (ModelGallery) detail.get(position);
            Log.e("image", "  " + m1.getFileImage());
            //  Picasso.with(mContext).load(m1.getFileImage()).into(((CustomViewHolder) holder).image);
            Bitmap bitmap = BitmapFactory.decodeFile(m1.getImage());
            ((CustomViewHolder) holder).image.setImageBitmap(bitmap);
            ((CustomViewHolder) holder).card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(position, 2);
                }
            });

            ((CustomViewHolder) holder).image_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(position, 4);

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

        ImageView image, image_download;
        RelativeLayout card_view;

        public CustomViewHolder(View view) {
            super(view);

            this.image = (ImageView) view.findViewById(R.id.image);
            this.image_download = (ImageView) view.findViewById(R.id.image_download);
            this.card_view = (RelativeLayout) view.findViewById(R.id.card_view);

        }


    }

    public void setFilter(ArrayList<ModelGallery> detailnew) {
        detail = new ArrayList<>();
        detail.addAll(detailnew);
        notifyDataSetChanged();
    }

    public ModelGallery getFilter(int i) {

        return detail.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        ModelGallery m1 = (ModelGallery) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}

