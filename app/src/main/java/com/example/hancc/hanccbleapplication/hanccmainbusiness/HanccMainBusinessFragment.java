package com.example.hancc.hanccbleapplication.hanccmainbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.hancc.hanccbleapplication.R;
import com.example.hancc.hanccbleapplication.hanccustomview.HanccBanner.HanccBannerManager;
import com.example.hancc.hanccbleapplication.hanccustomview.HanccNavigationBar;
import com.example.hancc.hanccbleapplication.hanccscanbledevicesbusiness.HanccScanBLEDevicesBusinessActivity;


public class HanccMainBusinessFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_business, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
        initNavigationbar();
        initActionEvent();
    }
    /**
     * 触发事件接口
     */
    private void initActionEvent(){
        // 开始
//        Button startScanBLEDeviceBtn = (Button)getActivity().findViewById(R.id.nextBtn);
//        startScanBLEDeviceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent scanBleDevicesActivityIntent = new Intent(getActivity(),HanccScanBLEDevicesBusinessActivity.class);
//                startActivity(scanBleDevicesActivityIntent);
//                getActivity().overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
//            }
//        });
    }
    /**
     * 设置导航栏
     */
    private void initNavigationbar(){
        HanccNavigationBar navigationbar = (HanccNavigationBar)getActivity().findViewById(R.id.hanccnavigationbarmain_id);
        navigationbar.setNavigationBarType2(getResources().getString(R.string.main_page_title),null,null,null);

        int[] iconList = {R.drawable.ioticon1, R.drawable.ioticon2, R.drawable.ioticon3,R.drawable.ioticon4, R.drawable.ioticon5};
        HanccBannerManager MyBannerManager = new HanccBannerManager(getActivity(),getActivity().getWindow().getDecorView());
        MyBannerManager.datasourceList(iconList, new HanccBannerManager.BannerItemEvent() {
            @Override
            public void itemEvent(int item) {
//                Log.i("Hancc", "🍉item" + item + "被点击了");
                if(item == 4) {
                    Intent scanBleDevicesActivityIntent = new Intent(getActivity(), HanccScanBLEDevicesBusinessActivity.class);
                    startActivity(scanBleDevicesActivityIntent);
                    getActivity().overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                }else{
                    Snackbar.make(getActivity().getWindow().getDecorView(),"提示!",Snackbar.LENGTH_SHORT).setAction("IoT开拓未来", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("Hancc", "snakbar被点击了");
                        }
                    }).show();
                }
            }
        });
    }

}