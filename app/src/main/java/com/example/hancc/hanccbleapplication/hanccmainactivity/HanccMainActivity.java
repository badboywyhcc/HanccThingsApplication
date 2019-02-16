package com.example.hancc.hanccbleapplication.hanccmainactivity;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hancc.hanccbleapplication.R;
import com.example.hancc.hanccbleapplication.hanccbaseactivity.HanccBaseActivity;
import com.example.hancc.hanccbleapplication.hanccmainbusiness.HanccMainBusinessFragment;
import com.example.hancc.hanccbleapplication.hanccminebusiness.HanccMineBusinessFragment;

public class HanccMainActivity extends HanccBaseActivity {

    // 主页-业务
    private HanccMainBusinessFragment MainFragment;
    // 我的-业务
    private HanccMineBusinessFragment MineFragment;
    // fragment管理者
    private android.support.v4.app.FragmentManager MyFragmentManager;


    private long startTime = 0;
    /**
     * // 2s内双击2次直接退出程序
     */
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            Toast.makeText(HanccMainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            startTime = currentTime;
        } else {
            // 完全退出程序
            // finish();
            // 退到后台,但是不退出程序
            moveTaskToBack(true);
        }
    }

    /**
     * @return 返回activity的xml布局文件
     */
    @Override
    public @LayoutRes
    int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化View
     */
    @Override
    public void initSubView() {
        RadioGroup tabRadioGroup = (RadioGroup)findViewById(R.id.rg_tab);
        tabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });

        initFragment();
    }

    /**
     * 初始化fragment
     */
    private void initFragment(){
        MainFragment = new HanccMainBusinessFragment();
        MineFragment = new HanccMineBusinessFragment();
        MyFragmentManager = getSupportFragmentManager();
        MyFragmentManager.beginTransaction().replace(R.id.fragment_container,MainFragment).commit();
    }


    private void switchFragment(@IdRes int id){
        switch (id){
            case R.id.rb_device:{
                showFragment(MainFragment,MineFragment);
                Log.v("Hancc","设备按键被按下");
            }break;
            case R.id.rb_my:{
                showFragment(MineFragment,MainFragment);
                Log.v("Hancc","我的按键被按下");
            }break;
            default:{

            }break;
        }
    }

    private void showFragment(Fragment beShowFragment, Fragment beHideFragment) {
        FragmentTransaction fragmentTransaction = MyFragmentManager.beginTransaction();
        if (beShowFragment.isAdded()) {
            fragmentTransaction.hide(beHideFragment).show(beShowFragment).commitAllowingStateLoss();
        } else {
            fragmentTransaction.hide(beHideFragment).add(R.id.fragment_container, beShowFragment).commitAllowingStateLoss();
        }
    }
}
