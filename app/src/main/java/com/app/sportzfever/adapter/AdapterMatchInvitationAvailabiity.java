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
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.ModelMatchInvitation;

import java.util.ArrayList;

/**
 * Created by admin on 26-11-2015.
 */
public class AdapterMatchInvitationAvailabiity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelMatchInvitation> detail;
    Context mContext;
    OnCustomItemClicListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public AdapterMatchInvitationAvailabiity(Context context, OnCustomItemClicListener lis, ArrayList<ModelMatchInvitation> list) {

        this.detail = list;
        this.mContext = context;
        this.listener = lis;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_matchavailavility, parent, false);

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

            ModelMatchInvitation m1 = (ModelMatchInvitation) detail.get(i);

           ((CustomViewHolder) holder).text_name.setText(m1.getTeamName());
           ((CustomViewHolder) holder).text_eventitle.setText(m1.getEventTitle());
           ((CustomViewHolder) holder).text_eventype.setText(m1.getEventType());

            /*((CustomViewHolder) holder).text_mess age.setText(m1.getNotificationText());
            ((CustomViewHolder) holder).text_date.setText(m1.getDatetime());*/
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
        TextView text_name, text_message, text_date,text_eventype,text_eventitle;
        ImageView image_viewers;
Button btn_confirm,btn_reject;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_date = (TextView) view.findViewById(R.id.text_date);
            this.text_eventype = (TextView) view.findViewById(R.id.text_eventype);
            this.text_eventitle = (TextView) view.findViewById(R.id.text_eventitle);
            this.btn_reject = (Button) view.findViewById(R.id.btn_reject);
            this.btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(getPosition(), 1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ModelMatchInvitation m1 = (ModelMatchInvitation) detail.get(position);
        if (detail.get(position).getRowType() == 1) {
            return VIEW_ITEM;
        } else if (detail.get(position).getRowType() == 2) {
            return VIEW_PROG;
        }
        return -1;
    }
}