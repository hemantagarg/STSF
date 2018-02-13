package com.app.sportzfever.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
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
import com.app.sportzfever.interfaces.OnCustomItemClicListener;
import com.app.sportzfever.models.Images;
import com.app.sportzfever.models.ModelFeed;
import com.app.sportzfever.utils.AppConstant;
import com.app.sportzfever.utils.AppUtils;
import com.app.sportzfever.utils.CircleTransform;
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
                    R.layout.row_user_feed, parent, false);

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
            this.progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof CustomViewHolder) {
            try {
                ModelFeed m1 = (ModelFeed) detail.get(i);

                if (m1.getIsShared().equalsIgnoreCase("0")) {

                    if (m1.getAvatarType().equalsIgnoreCase("TEAM")) {
                        ((CustomViewHolder) holder).text_name.setText(m1.getAvatarName());

                        if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(((CustomViewHolder) holder).image_viewers);
                        }
                    } else if (m1.getAvatarType().equalsIgnoreCase("PLAYER")) {

                        if (!m1.getOriginalAvatarName().equalsIgnoreCase("")) {
                            ((CustomViewHolder) holder).text_name.setText(m1.getOriginalAvatarName());
                            if (!m1.getOriginalAvatarProfilePicture().equalsIgnoreCase("")) {
                                Picasso.with(mContext)
                                        .load(m1.getOriginalAvatarProfilePicture() + "&w=100&h=100")
                                        .transform(new CircleTransform())
                                        .placeholder(R.drawable.logo_sportz)
                                        .into(((CustomViewHolder) holder).image_viewers);
                            }
                        } else {
                            ((CustomViewHolder) holder).text_name.setText(m1.getAvatarName());
                            if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                                Picasso.with(mContext)
                                        .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                        .transform(new CircleTransform())
                                        .placeholder(R.drawable.logo_sportz)
                                        .into(((CustomViewHolder) holder).image_viewers);
                            }
                        }
                    } else {
                        ((CustomViewHolder) holder).text_name.setText(m1.getUserName());
                        if (!m1.getUserProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(m1.getUserProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(((CustomViewHolder) holder).image_viewers);
                        }
                    }
                    ((CustomViewHolder) holder).text_sharePost.setVisibility(View.GONE);

                } else {
                    ((CustomViewHolder) holder).text_sharePost.setVisibility(View.VISIBLE);
                    if (AppUtils.getUserId(mContext).equalsIgnoreCase(m1.getUser())) {
                        ((CustomViewHolder) holder).text_sharePost.setText("You shared this post");

                    } else {
                        ((CustomViewHolder) holder).text_sharePost.setText(m1.getUserName() + " shared this post");
                    }

                    if (m1.getAvatarType().equalsIgnoreCase("TEAM")) {
                        ((CustomViewHolder) holder).text_name.setText(m1.getAvatarName());

                        if (!m1.getAvatarProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(m1.getAvatarProfilePicture() + "&w=100&h=100")
                                    .transform(new CircleTransform())
                                    .placeholder(R.drawable.logo_sportz)
                                    .into(((CustomViewHolder) holder).image_viewers);
                        }
                    } else {

                        if (!m1.getOriginalAvatarName().equalsIgnoreCase("")) {
                            ((CustomViewHolder) holder).text_name.setText(m1.getOriginalAvatarName());
                            if (!m1.getOriginalAvatarProfilePicture().equalsIgnoreCase("")) {
                                Picasso.with(mContext)
                                        .load(m1.getOriginalAvatarProfilePicture() + "&w=100&h=100")
                                        .transform(new CircleTransform())
                                        .placeholder(R.drawable.logo_sportz)
                                        .into(((CustomViewHolder) holder).image_viewers);
                            }

                        } else {
                            ((CustomViewHolder) holder).text_name.setText(m1.getOriginalUserName());
                            if (!m1.getOriginalUserProfilePicture().equalsIgnoreCase("")) {
                                Picasso.with(mContext)
                                        .load(m1.getOriginalUserProfilePicture() + "&w=100&h=100")
                                        .transform(new CircleTransform())
                                        .placeholder(R.drawable.logo_sportz)
                                        .into(((CustomViewHolder) holder).image_viewers);
                            }
                        }
                    }
                }


                ((CustomViewHolder) holder).text_message.setText(Html.fromHtml(m1.getDescription()));
                ((CustomViewHolder) holder).text_time.setText(m1.getDateString());
                if (m1.getStatusType().equals(AppConstant.EVENT)) {
                    ((CustomViewHolder) holder).text_message.setText(m1.getEventTitle());
                    ((CustomViewHolder) holder).text_location_time.setText(m1.getDateTime());
                    ((CustomViewHolder) holder).text_location_time.setVisibility(View.VISIBLE);
                    if (m1.getEventType().equals(AppConstant.MATCH)) {

                        ((CustomViewHolder) holder).text_location_time.setText(m1.getDateTime());
                        ((CustomViewHolder) holder).text_teamname1.setText(m1.getTeam1Name());
                        ((CustomViewHolder) holder).text_teamname2.setText(m1.getTeam2Name());
                        ((CustomViewHolder) holder).text_message.setText(m1.getLocation());

                        ((CustomViewHolder) holder).text_location_time.setVisibility(View.VISIBLE);
                        ((CustomViewHolder) holder).rl_top.setVisibility(View.GONE);
                        ((CustomViewHolder) holder).rl_match.setVisibility(View.VISIBLE);
                        ((CustomViewHolder) holder).image_viewers.setVisibility(View.GONE);

                        if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_NOTSTARTED)) {
                            ((CustomViewHolder) holder).text_match_status.setText("UPCOMING");
                        } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_STARTED)) {
                            ((CustomViewHolder) holder).text_match_status.setText("LIVE");
                        } else if (m1.getMatchStatus().equals(AppConstant.MATCHSTATUS_ENDED)) {
                            ((CustomViewHolder) holder).text_match_status.setText("PAST");
                        }

                        if (!m1.getTeam1ProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(m1.getTeam1ProfilePicture() + "&w=100&h=100")
                                    .into(((CustomViewHolder) holder).teama);

                        }
                        if (!m1.getTeam2ProfilePicture().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(m1.getTeam2ProfilePicture() + "&w=100&h=100")
                                    .into(((CustomViewHolder) holder).teamb);

                        }

                    } else {
                        ((CustomViewHolder) holder).rl_top.setVisibility(View.VISIBLE);
                        ((CustomViewHolder) holder).rl_match.setVisibility(View.GONE);
                        ((CustomViewHolder) holder).image_viewers.setVisibility(View.VISIBLE);
                        ((CustomViewHolder) holder).text_location_time.setVisibility(View.GONE);
                    }

                } else {
                    ((CustomViewHolder) holder).rl_top.setVisibility(View.VISIBLE);
                    ((CustomViewHolder) holder).rl_match.setVisibility(View.GONE);
                    ((CustomViewHolder) holder).image_viewers.setVisibility(View.VISIBLE);
                    ((CustomViewHolder) holder).text_location_time.setVisibility(View.GONE);
                }
                if (m1.getIsLiked().equalsIgnoreCase("0")) {
                    ((CustomViewHolder) holder).text_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_grey, 0, 0, 0);
                    ((CustomViewHolder) holder).text_like.setTextColor(ContextCompat.getColor(mContext, R.color.text_hint_color));
                } else {
                    ((CustomViewHolder) holder).text_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_red, 0, 0, 0);
                    ((CustomViewHolder) holder).text_like.setTextColor(ContextCompat.getColor(mContext, R.color.red_select));
                }

                if (m1.getIsShared().equalsIgnoreCase("0")) {
                    ((CustomViewHolder) holder).text_share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_grey, 0, 0, 0);
                    ((CustomViewHolder) holder).text_share.setTextColor(ContextCompat.getColor(mContext, R.color.text_hint_color));
                } else {
                    ((CustomViewHolder) holder).text_share.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_red, 0, 0, 0);
                    ((CustomViewHolder) holder).text_share.setTextColor(ContextCompat.getColor(mContext, R.color.red_select));
                }

                if (m1.getCommentsCount() > 0) {
                    ((CustomViewHolder) holder).text_comment.setText(m1.getCommentsCount() + "");
                } else {
                    ((CustomViewHolder) holder).text_comment.setText("0");
                }

                if (m1.getLikeCount() > 0) {
                    ((CustomViewHolder) holder).text_like.setText(m1.getLikeCount() + "");
                } else {
                    ((CustomViewHolder) holder).text_like.setText("0");
                }

                if (m1.getShareCount() > 0) {
                    ((CustomViewHolder) holder).text_share.setText(m1.getShareCount() + "");
                } else {
                    ((CustomViewHolder) holder).text_share.setText("0");
                }

                if (m1.getUser().equalsIgnoreCase(AppUtils.getUserId(mContext))) {
                    ((CustomViewHolder) holder).image_menu.setVisibility(View.VISIBLE);
                } else {
                    ((CustomViewHolder) holder).image_menu.setVisibility(View.GONE);
                }


                ((CustomViewHolder) holder).text_comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 1);
                    }
                });
                ((CustomViewHolder) holder).text_like_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 2);
                    }
                });
                ((CustomViewHolder) holder).text_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 3);
                    }
                });
                ((CustomViewHolder) holder).text_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 1);
                    }
                });
                ((CustomViewHolder) holder).text_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 6);
                    }
                });
                ((CustomViewHolder) holder).text_share_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 5);
                    }
                });
                ((CustomViewHolder) holder).text_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 11);
                    }
                });
                ((CustomViewHolder) holder).rl_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 13);
                    }
                });
                ((CustomViewHolder) holder).ll_feed_images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 4);
                    }
                });

                ((CustomViewHolder) holder).image_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 9);
                    }
                });
                ((CustomViewHolder) holder).card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(i, 21);
                    }
                });

                ArrayList<Images> imagesArrayList = m1.getImages();
                if (imagesArrayList != null && imagesArrayList.size() > 0) {

                    if (imagesArrayList.size() == 1) {
                        ((CustomViewHolder) holder).image_feed1.setVisibility(View.VISIBLE);
                        ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.GONE);

                        if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(imagesArrayList.get(0).getImage() + "&h=400")
                                    .into(((CustomViewHolder) holder).image_feed1);

                        }
                    } else if (imagesArrayList.size() > 1) {
                        ((CustomViewHolder) holder).image_feed1.setVisibility(View.GONE);
                        ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.VISIBLE);

                        if (!imagesArrayList.get(0).getImage().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(imagesArrayList.get(0).getImage() + "&w=400&h=400")
                                    .into(((CustomViewHolder) holder).image_feed2);
                        }

                        if (!imagesArrayList.get(1).getImage().equalsIgnoreCase("")) {
                            Picasso.with(mContext)
                                    .load(imagesArrayList.get(1).getImage() + "&w=400&h=400")
                                    .into(((CustomViewHolder) holder).image_feed3);

                        }

                        if (imagesArrayList.size() > 2) {
                            ((CustomViewHolder) holder).rl_moreimages.setVisibility(View.VISIBLE);
                            ((CustomViewHolder) holder).text_imag_count.setText(imagesArrayList.size() - 2 + "");
                        } else {
                            ((CustomViewHolder) holder).rl_moreimages.setVisibility(View.GONE);
                        }


                    } else {
                        ((CustomViewHolder) holder).image_feed1.setVisibility(View.GONE);
                        ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.GONE);

                    }
                } else {
                    ((CustomViewHolder) holder).image_feed1.setVisibility(View.GONE);
                    ((CustomViewHolder) holder).ll_multiple_images.setVisibility(View.GONE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else

        {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }


    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView text_name, text_message, text_time, text_like, text_comment, text_share,
                text_imag_count, text_sharePost, text_like_count, text_comment_count, text_match_status,
                text_share_count, text_location_time, text_teamname1, text_teamname2, text_match_location;
        ImageView image_viewers, image_feed1, image_feed2, image_feed3, image_menu, teama, teamb;
        LinearLayout ll_feed_images, ll_multiple_images;
        RelativeLayout rl_moreimages, card_view, rl_match, rl_top;
        View view2;

        public CustomViewHolder(View view) {
            super(view);

            this.image_viewers = (ImageView) view.findViewById(R.id.image_viewers);
            this.view2 = view.findViewById(R.id.view2);
            this.image_feed1 = (ImageView) view.findViewById(R.id.image_feed1);
            this.image_feed2 = (ImageView) view.findViewById(R.id.image_feed2);
            this.teama = (ImageView) view.findViewById(R.id.teama);
            this.teamb = (ImageView) view.findViewById(R.id.teamb);
            this.image_menu = (ImageView) view.findViewById(R.id.image_menu);
            this.image_feed3 = (ImageView) view.findViewById(R.id.image_feed3);
            this.ll_feed_images = (LinearLayout) view.findViewById(R.id.ll_feed_images);
            this.rl_moreimages = (RelativeLayout) view.findViewById(R.id.rl_moreimages);
            this.rl_match = (RelativeLayout) view.findViewById(R.id.rl_match);
            this.rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
            this.card_view = (RelativeLayout) view.findViewById(R.id.card_view);
            this.ll_multiple_images = (LinearLayout) view.findViewById(R.id.ll_multiple_images);
            this.text_name = (TextView) view.findViewById(R.id.text_name);
            this.text_match_location = (TextView) view.findViewById(R.id.text_match_location);
            this.text_sharePost = (TextView) view.findViewById(R.id.text_sharePost);
            this.text_imag_count = (TextView) view.findViewById(R.id.text_imag_count);
            this.text_match_status = (TextView) view.findViewById(R.id.text_match_status);
            this.text_like_count = (TextView) view.findViewById(R.id.text_like_count);
            this.text_comment_count = (TextView) view.findViewById(R.id.text_comment_count);
            this.text_share_count = (TextView) view.findViewById(R.id.text_share_count);
            this.text_message = (TextView) view.findViewById(R.id.text_message);
            this.text_like = (TextView) view.findViewById(R.id.text_like);
            this.text_time = (TextView) view.findViewById(R.id.text_time);
            this.text_teamname2 = (TextView) view.findViewById(R.id.text_teamname2);
            this.text_teamname1 = (TextView) view.findViewById(R.id.text_teamname1);
            this.text_location_time = (TextView) view.findViewById(R.id.text_location_time);
            this.text_share = (TextView) view.findViewById(R.id.text_share);
            this.text_comment = (TextView) view.findViewById(R.id.text_comment);

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