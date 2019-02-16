package com.example.hancc.hanccbleapplication.hanccustomview.HanccBanner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HanccBannerPagerAdapter extends PagerAdapter {
    private List<View> items;
    public HanccBannerPagerAdapter(List<View> items) {
        this.items = items;
    }
    /**
     * ①将给定位置的view添加到ViewGroup(容器)中,创建并显示出来 ②返回一个代表新增页面的Object(key),通常都是直接返回view本身就可以了,当然你也可以 自定义自己的key,但是key和每个view要一一对应的关系
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(items.get(position));
        return items.get(position);
    }

    /**
     * 获得viewpager中有多少个view
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * 判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是 代表的同一个视图(即它俩是否是对应的，对应的表示同一个View),通常我们直接写 return view == object!
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (View) o;
    }

    /**
     * 移除一个给定位置的页面，适配器有责任从容器中删除这个视图。 这是为了确保在finishUpdate(viewGroup)返回时视图能够被移除。
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(items.get(position));
    }
}
