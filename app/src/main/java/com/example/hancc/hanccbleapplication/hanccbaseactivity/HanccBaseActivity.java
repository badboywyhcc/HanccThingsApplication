package com.example.hancc.hanccbleapplication.hanccbaseactivity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.example.hancc.hanccbleapplication.hanccminebusiness.hanccpersonbeans.HanccPersonInfoBean;

public abstract class HanccBaseActivity extends AppCompatActivity {

    // 抽象方法
    public abstract @LayoutRes int getLayoutId();
    public abstract void initSubView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setImmerse();

        setContentView(getLayoutId());
        initSubView();

        HanccPersonInfoBean mine = new HanccPersonInfoBean.Builder()
                .name("fff")
                .age(18)
                .build();
        Log.i("Hancc","个人信息"+mine.name+mine.age);
    }
    /*设置沉浸式,开启全屏模式*/
    public void setImmerse(){
         requestWindowFeature(getWindow().FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}