package com.example.hancc.hanccbleapplication.hanccscanbledevicesbusiness;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.hancc.hanccbleapplication.R;
import com.example.hancc.hanccbleapplication.hanccbaseactivity.HanccBaseActivity;
import com.example.hancc.hanccbleapplication.hanccustomview.HanccNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class HanccScanBLEDevicesBusinessActivity extends HanccBaseActivity {
    private static final int LOAD_SUCCESS=0x01;
    private static final int LOAD_FAILURE=0x02;

    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    // 扫描设备刷新列表
    FloatingActionButton refreshBLEDeviceBtn;
    // 搜索的时候进度条转圈
    private ProgressBar scanBLEDeviceProgressBar;

    HanccBLEDevicesAdapter ListViewAdapter;
    ListView devicesListview;
    ArrayList<BleDevice> BLEDevicesData;
    private Object View;


    /**
     * @return 返回activity的xml布局文件
     */
    @Override
    public @LayoutRes
    int getLayoutId() {
        return R.layout.activity_scan_bledevices_business;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"点击悬浮按钮搜索设备",Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化View
     */
    @Override
    public void initSubView() {
        initView();
        initBLEBusiness();
        initActionEvent();

        BLEDevicesData = new ArrayList<>();
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//        overridePendingTransition(R.anim.anim_out,R.anim.anim_in);
//    }
    /**
     * 刷新listView的数据
     */
    public void refreshListData() {
//        HashSet<BleDevice> BLEDevicesSetdata = new HashSet<>(BLEDevicesData);
//        BLEDevicesData.clear();
//        BLEDevicesData = new ArrayList<>(BLEDevicesSetdata);
        ListViewAdapter = new HanccBLEDevicesAdapter(BLEDevicesData, this);
        devicesListview.setAdapter(ListViewAdapter);
        // 刷新数据
        devicesListview.deferNotifyDataSetChanged();
    }
    /**
     * 初始化页面布局
     */
    private void initView(){
        initNavigationbar();
        scanBLEDeviceProgressBar = (ProgressBar)this.findViewById(R.id.scanBLEDeviceProgressBarID);
        devicesListview = (ListView)this.findViewById(R.id.bledevices_listView);
        devicesListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Log.i("Hancc","🍎"+position);
            }
        });
    }
    /**
     * 设置导航栏
     */
    private void initNavigationbar(){
        HanccNavigationBar navigationbar = (HanccNavigationBar)this.findViewById(R.id.hanccnavigationbarmain_id);
//        navigationbar.setNavigationBarType2(getResources().getString(R.string.main_page_title),null,null,null);
        navigationbar.setNavigationBarType1("搜索", R.drawable.hanccnavigationbarlefticon, 0, new HanccNavigationBar.navigationBarEvent() {
            @Override
            public void LeftBtnOnClickEvent(android.view.View v) {
                finish();
            }

            @Override
            public void RightBtnOnClickEvent(android.view.View v) {

            }
        });
    }
    /**
     * 触发事件接口
     */
    private void initActionEvent(){
        refreshBLEDeviceBtn = (FloatingActionButton)this.findViewById(R.id.refreshbledeviceslistview);
        refreshBLEDeviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                checkPermissions();
            }
        });
    }
    /**
     * 初始化BLE业务模块
     */
    private void  initBLEBusiness(){
        BleManager.getInstance().init(this.getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(null)      // 只扫描指定的服务的设备，可选
                .setDeviceName(true, "")   // 只扫描指定广播名的设备，可选
                .setDeviceMac("")                  // 只扫描指定mac的设备，可选
                .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    /**
     * 启动扫描业务
     */
    private void startScanBusiness(){
        scanBLEDeviceProgressBar.setVisibility(android.view.View.VISIBLE);
        BLEDevicesData.clear();
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.i("Hancc","💚扫描开始");
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
//                filterBLEDevices(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.i("Hancc","🦑扫描到指定的设备"+bleDevice.getName()+""+bleDevice.getMac()+"");
//                BLEDevicesData.add(bleDevice);
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                scanBLEDeviceProgressBar.setVisibility(android.view.View.INVISIBLE);
                BLEDevicesData.clear();
                if (false) {
                    ArrayList<BleDevice> BLEDevicesTempData = new ArrayList<>(scanResultList);
                    for(BleDevice obj : BLEDevicesTempData){
                        if (!TextUtils.isEmpty(obj.getName())) {
                            BLEDevicesData.add(obj);
                        }
                    }
                }else{
                    BLEDevicesData.addAll(scanResultList);
                }

                refreshListData();
                Log.i("Hancc","🍉扫描结束"+" "+BLEDevicesData.size());
                refreshBLEDeviceBtn.setEnabled(true);
                devicesListview.setVisibility(android.view.View.VISIBLE);
            }
        });
    }

    /**
     * 过滤设备
     */
    private void filterBLEDevices(BleDevice bleDevice){
        Log.i("Hancc","🐠扫描到指定的设备"+bleDevice.getName()+""+bleDevice.getMac()+"");
        if (!TextUtils.isEmpty(bleDevice.getName())) {
            BLEDevicesData.add(bleDevice);
        }
    }
    /**
     * 动态检查权限
     */
    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "请打开蓝牙设备", Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }
    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("当前手机扫描蓝牙需要打开定位功能")
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton("前往设置",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
                    startScanBusiness();
                    refreshBLEDeviceBtn.setEnabled (false);
                    devicesListview.setVisibility(android.view.View.INVISIBLE);
                    Toast.makeText(this,"正在搜索设备请稍候!!!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }
    @Override
    public final void onRequestPermissionsResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }
}
