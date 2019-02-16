package com.example.hancc.hanccbleapplication.hanccscanbledevicesbusiness;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clj.fastble.data.BleDevice;
import com.example.hancc.hanccbleapplication.R;

import java.util.ArrayList;

public class HanccBLEDevicesAdapter extends BaseAdapter {
    ArrayList<BleDevice> BLEDevicesData = null;
    LayoutInflater BLEDevicesInflater = null;
    Context BLEDevicesContext = null;

    public HanccBLEDevicesAdapter(ArrayList<BleDevice>datas, Context context) {
        BLEDevicesData = datas;
        BLEDevicesInflater = LayoutInflater.from(context);
        BLEDevicesContext =context;
    }

    @Override
    public int getCount() {
        return BLEDevicesData!=null?BLEDevicesData.size():0;
    }

    @Override
    public Object getItem(int position) {
        return BLEDevicesData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        if (convertView == null){

            convertView = BLEDevicesInflater.inflate(R.layout.bledeviceslistcell,null);
            holder = new viewHolder();
            holder.BLEDevices_icon = (ImageView)convertView.findViewById(R.id.news_icon);
            holder.BLEDevices_title = (TextView)convertView.findViewById(R.id.news_title);
            holder.BLEDevices_resitle = (TextView)convertView.findViewById(R.id.news_resitle);

            convertView.setTag(holder);
            Log.i("Hancc",Integer.valueOf(position).toString());
        }
        else
        {
            holder = (viewHolder)convertView.getTag();
        }

        BleDevice m = BLEDevicesData.get(position);
        setDatasToView(holder,m);

        return convertView;
    }

    public void setDatasToView(viewHolder holder,BleDevice m){
        // 加载启动页图片
//        Glide.with(newsContext).load(m.getImg()).into(holder.news_icon);
        Glide.with(BLEDevicesContext).load(R.drawable.hanccbledevicecion).into(holder.BLEDevices_icon);

        if (!TextUtils.isEmpty(m.getName())) {
            holder.BLEDevices_title.setText(m.getName());
        }else {
            holder.BLEDevices_title.setText(R.string.unknow_name);
        }
        if (!TextUtils.isEmpty(m.getMac())){
            holder.BLEDevices_resitle.setText(m.getMac());
        }else {
            holder.BLEDevices_title.setText(R.string.unknow_MAC);
        }
    }
    class viewHolder{
        ImageView BLEDevices_icon;
        TextView  BLEDevices_title;
        TextView  BLEDevices_resitle;
    }
}
