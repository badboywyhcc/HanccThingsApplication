package com.example.hancc.hanccbleapplication.hanccustomview.HanccBanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import com.example.hancc.hanccbleapplication.R;


public class HanccBannerManager {
    // item点击事件
    public interface BannerItemEvent{
        public void itemEvent(int item);
    }


    private int  viewsitemssize;
    private Context MyContext;
    private ViewPager banner;
    private View mRootView;
    private LayoutInflater inflater;
    BannerItemEvent MyEvent;


    public HanccBannerManager(Context context,View mRootView) {
        MyContext = context;
        this.mRootView = mRootView;
        inflater = LayoutInflater.from(context);
    }

    public void datasourceList(int[] sourceList,BannerItemEvent event) {
        try {
            MyEvent = event;
            viewsitemssize = sourceList.length;
            //设置Banner数据
            initBannerView(sourceList);
            //设置底部信息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initBannerView(int[] sourceList) {
        RelativeLayout bannerLayout = (RelativeLayout) mRootView.findViewById(R.id.banner_layout);
//        bannerLayout.getLayoutParams().height = 500 * 247 / 375;
        //得到ViewPager的数据源
        List<View> items = new ArrayList<>();
        final int size = sourceList.length;
        for (int i = 0; i < size; i++) {
            View view = inflater.inflate(R.layout.hanccbanneritemview, null);
            //图片
            com.example.hancc.hanccbleapplication.hanccustomview.HanccTailorImageCornerView  img = (com.example.hancc.hanccbleapplication.hanccustomview.HanccTailorImageCornerView) view.findViewById(R.id.img);
            img.setSrc(BitmapFactory.decodeResource(MyContext.getResources(),sourceList[i]));
            items.add(view);
        }
        //设置ViewPager的adapter
        HanccBannerPagerAdapter adapter = new HanccBannerPagerAdapter(items);
        banner = (ViewPager) mRootView.findViewById(R.id.banner);
        banner.setAdapter(adapter);
        //当手指在触摸Banner时，暂停轮播
        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                Log.i("Hancc","🍑:️"+i+""+i1);
            }

            @Override
            public void onPageSelected(int position) {
                //更新ViewPager的item位置
//                Log.i("Hancc","🥎:️"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("Hancc","🏖:️"+state);
            }
        });

        // 一下业务是判断哪个item被选中了
        banner.setOnTouchListener(new View.OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;
            ViewConfiguration configuration = ViewConfiguration.get(MyContext);
            int mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        if (xDiff < mTouchSlop && xDiff >= yDiff)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {
                            int currentItem = banner.getCurrentItem();
                            if (MyEvent != null)
                            {
                                MyEvent.itemEvent(currentItem);
//                                Log.i("Hancc", "🍉item" + currentItem + "被点击了");
                            }
                        }
                        break;
                }
                return false;
            }
        });
        //ViewPager初始位置
        banner.setCurrentItem(0);
    }

}
