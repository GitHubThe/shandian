package com.developer.shan;

import android.app.Application;
import android.content.Context;

import com.developer.shan.utils.SharedPreferenceUtils;


/**
 * author: shell
 * date 2017/2/24 下午3:27
 **/
public class BaseApplication extends Application {
    public static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPreferenceUtils.init(this);
    }
}
