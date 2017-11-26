package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelRecentBall;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterRecentBalls extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelRecentBall> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterRecentBalls(Context context, OnCustomItemClicListener lis, ArrayList<ModelRecentBall> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_recent_balls, parent, false);

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

            ModelRecentBall m1 = (ModelRecentBall) detail.get(i);

            ((CustomViewHolder) holder).text_ball.setText(m1.getOverString()    );

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_ball;


        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.text_ball = (TextView) view.findViewById(R.id.text_ball);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelRecentBall m1 = (ModelRecentBall) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}