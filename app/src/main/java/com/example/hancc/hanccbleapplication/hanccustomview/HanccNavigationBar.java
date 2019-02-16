package com.example.hancc.hanccbleapplication.hanccustomview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hancc.hanccbleapplication.R;

/**
 * Created by hcc on 2018/6/4.
 */
class CheckException extends RuntimeException{

    public CheckException() {
        super();
    }

    public CheckException(String message){
        super(message);
    }

    public CheckException(String message,Throwable cause) {
        super(message, cause);
    }
}

public class HanccNavigationBar extends RelativeLayout {
    // 2.左右按键事件接口
    public interface navigationBarEvent {
        // 左边按键被按下事件
        public void LeftBtnOnClickEvent(View v);

        // 右边按键被按下事件
        public void RightBtnOnClickEvent(View v);
    }

    /**
     * 自定义组件的构造方法
     */
    public HanccNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.hanccnavigationbar, this);     //加载布局文件
    }



    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context,float dipValue)
    {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    private  Point  getIconWidthHeight(int Icon){

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), Icon, opts);
        opts.inSampleSize = 2;
        opts.inJustDecodeBounds = false;
        Bitmap mBitmap =BitmapFactory.decodeResource(getResources(), Icon, opts);
        int width=opts.outWidth;
        int height=opts.outHeight;
        Log.v("Hancc","图片的"+width+","+height);

        // 这里是把计算的高度宽度放在Point类型变量中
        Point finalSize = new Point(width,height);
        return finalSize;
    }
    /**
     * 设置 view宽高
     * @param view 目标view
     */
    private void setViewWidthHeight(View view,int leftBtnIcon){

        Point finalSize = getIconWidthHeight(leftBtnIcon);

        if (finalSize.y >50){
            throw new CheckException();
        }else{
            LayoutParams lp= (LayoutParams)view.getLayoutParams();
            lp.width = dip2px((Activity)getContext(),finalSize.x);
            lp.height = dip2px((Activity)getContext(),finalSize.y);
            view.setLayoutParams(lp);
        }
    }


    /**
     * 设置导航栏(类型1)
     * @param title                 导航栏标题文字
     * @param leftBtnIcon           设置导航栏左边底图
     * @param rightBtnIcon          设置导航栏右边底图
     * @param navigationBarEvent    设置导航栏事件
     */
    public void setNavigationBarType1(String title,int leftBtnIcon,int rightBtnIcon, final navigationBarEvent navigationBarEvent) {
        TextView titleView = (TextView) findViewById(R.id.hanccnavigationbartitleView);
        titleView.setText(title);
        Button leftBtn = findViewById(R.id.hanccnavigationbarleftBtn);
        Button rightBtn = findViewById(R.id.hanccnavigationbarrightBtn);

        if (leftBtnIcon == 0) {
            leftBtn.setVisibility(View.INVISIBLE);
        } else {

            try {
                // 设置底图的颜色
                setViewWidthHeight(leftBtn,leftBtnIcon);
            } catch (Exception e) {
                Log.v("Hancc","异常:底图高度不能大于50");
                e.printStackTrace();
            }

            leftBtn.setBackgroundResource(leftBtnIcon);
            leftBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                // 获取当前Activity
//                Activity currentActivity = (Activity)getContext();
//                // 返回上一页
//                currentActivity.finish();
                    if (navigationBarEvent != null) navigationBarEvent.LeftBtnOnClickEvent(v);
                }
            });
        }
        if (rightBtnIcon == 0) {
            rightBtn.setVisibility(View.INVISIBLE);
        } else {
            try {
                // 设置底图的颜色
                setViewWidthHeight(leftBtn,leftBtnIcon);
            } catch (Exception e) {
                Log.v("Hancc","异常:底图高度不能大于50");
                e.printStackTrace();
            }
            rightBtn.setBackgroundResource(rightBtnIcon);
            rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (navigationBarEvent != null) navigationBarEvent.RightBtnOnClickEvent(v);
                }
            });
        }
    }
    /**
     * 设置导航栏(类型2)
     * @param title                  导航栏标题文字
     * @param leftBtnTitle           设置导航栏左边文字
     * @param rightBtnTitle          设置导航栏右边文字
     * @param navigationBarEvent     设置导航栏事件
     */
    public void setNavigationBarType2(String title, String leftBtnTitle, String rightBtnTitle, final navigationBarEvent navigationBarEvent) {
        TextView titleView = (TextView) findViewById(R.id.hanccnavigationbartitleView);
        titleView.setText(title);
        titleView.setTextSize(16);
        Button leftBtn = findViewById(R.id.hanccnavigationbarleftBtn);
        Button rightBtn = findViewById(R.id.hanccnavigationbarrightBtn);

        if (TextUtils.isEmpty(leftBtnTitle)) {
            leftBtn.setVisibility(View.INVISIBLE);
        } else {
            // 设置背景为透明色
            leftBtn.setBackgroundColor(0);
            leftBtn.setTextSize(14);
            leftBtn.setText(leftBtnTitle);
            leftBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (navigationBarEvent != null) navigationBarEvent.LeftBtnOnClickEvent(v);
                }
            });
        }
        if (TextUtils.isEmpty(rightBtnTitle)) {
            rightBtn.setVisibility(View.INVISIBLE);
        } else {
            // 设置背景为透明色
            rightBtn.setBackgroundColor(0);
            rightBtn.setTextSize(14);
            rightBtn.setText(rightBtnTitle);
            rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (navigationBarEvent != null) navigationBarEvent.RightBtnOnClickEvent(v);
                }
            });
        }
    }
}