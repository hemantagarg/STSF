package com.app.sportzfever.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sportzfever.R;
import com.app.sportzfever.models.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesListActivity extends AppCompatActivity {

    private Button btn_skip;
    private ViewPager view_pager;
    private Context context;
    private ArrayList<String> mResources;
    private ArrayList<String> urlList;
    private ArrayList<String> nameList;
    private CustomPagerAdapter mCustomPagerAdapter;
    private ImageView[] dots;
    private int dotsCount;
    private LinearLayout pager_indicator;
    private TextView tv_toolbar_title;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);
        context = this;
        init();
        setData();
    }

    private void setUiPageViewController() {

        dotsCount = mCustomPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            //   params.gravity = Gravity.RIGHT;

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    private void setData() {
        mResources = new ArrayList<>();
        nameList = new ArrayList<>();
        urlList = new ArrayList<>();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle.containsKey("position")) {
            position = bundle.getInt("position");
        }
        ArrayList<Images> imagesArrayList = (ArrayList<Images>) bundle.getSerializable("images");
        try {
            for (int i = 0; i < imagesArrayList.size(); i++) {
                mResources.add(imagesArrayList.get(i).getImage());
            }
            mCustomPagerAdapter = new CustomPagerAdapter(this);
            view_pager.setAdapter(mCustomPagerAdapter);
            pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
            setUiPageViewController();
            view_pager.setCurrentItem(position);
            setListener();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setListener() {

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    if (position >= dotsCount) {
                        position = (position % dotsCount);
                    }
                    Log.e("position", "*" + position);

                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                    }

                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item,
                    container, false);

            ImageView imageView = (ImageView) itemView
                    .findViewById(R.id.imageView);
            //  imageView.setImageResource(mResources.get(position));
            Picasso.with(mContext).load(mResources.get(position)).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
