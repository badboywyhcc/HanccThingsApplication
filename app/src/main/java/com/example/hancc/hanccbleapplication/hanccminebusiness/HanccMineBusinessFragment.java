package com.example.hancc.hanccbleapplication.hanccminebusiness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hancc.hanccbleapplication.R;
import com.example.hancc.hanccbleapplication.hanccminebusiness.hanccpersonbeans.HanccPersonInfoBean;
import com.example.hancc.hanccbleapplication.hanccustomview.HanccNavigationBar;

public class HanccMineBusinessFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_business, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNavigationbar();
    }
    /**
     * 设置导航栏
     */
    private void initNavigationbar(){
        HanccNavigationBar navigationbar = (HanccNavigationBar)getActivity().findViewById(R.id.hanccnavigationbarmine_id);

        navigationbar.setNavigationBarType2(getResources().getString(R.string.mine_page_title),null,null,null);
//        navigationbar.setNavigationBarType2("我的",null,null,null);
    }
}
