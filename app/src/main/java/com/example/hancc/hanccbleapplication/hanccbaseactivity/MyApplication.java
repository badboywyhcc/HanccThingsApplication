package com.example.hancc.hanccbleapplication.hanccbaseactivity;

import android.app.Application;
import android.content.Context;

public class MyApplication  extends Application {
    private static Context myContext;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = getApplicationContext();
        instance = this;
    }
    public static Context getMyContext(){
        return myContext;
    }

}
