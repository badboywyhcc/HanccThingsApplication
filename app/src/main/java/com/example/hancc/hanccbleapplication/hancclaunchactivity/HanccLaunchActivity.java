package com.example.hancc.hanccbleapplication.hancclaunchactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.hancc.hanccbleapplication.R;
import com.example.hancc.hanccbleapplication.hanccmainactivity.HanccMainActivity;

public class HanccLaunchActivity extends AppCompatActivity {
    // 主界面
    private static final int MAINACTIVITY = 0X00001;

    private Handler jmHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmerse();
        setContentView(R.layout.activity_launch);

        // 延时跳转到主界面
        jmHandler.postDelayed(MainPage,3000);
    }
    /*设置沉浸式,开启全屏模式*/
    public void setImmerse(){
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    Runnable MainPage = new Runnable() {
        @Override
        public void run() {
            jumpMainPage();
        }
    };

    /**
     *  跳转到主界面
     */
    private void jumpMainPage(){
        Intent intent = new Intent(HanccLaunchActivity.this,HanccMainActivity.class);
        startActivityForResult(intent,MAINACTIVITY);
        // 把该引导页从栈中移除
        finish();
    }
    /**
     * 返回键禁用
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
