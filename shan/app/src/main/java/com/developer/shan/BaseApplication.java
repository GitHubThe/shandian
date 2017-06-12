package com.developer.shan;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.developer.shan.utils.DevicesUtils;
import com.developer.shan.utils.PermissionUtils;
import com.developer.shan.utils.SharedPreferenceUtils;
import com.developer.shan.utils.VersionUtils;


/**
 * author: shell
 * date 2017/2/24 下午3:27
 **/
public class BaseApplication extends Application {

    public static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    public static String getPlatform() { return  "android"; }
    public static int getVersionCode() { return  VersionUtils.getVersionCode(mContext); }
    public static String getVersionName() { return  VersionUtils.getVersionName(mContext); }
    public static String getChanel() { return  "0001"; }
    public static String getPackageName1() { return  getPlatform() + getVersionCode()+ "x"; }
    public static String getDeviceId(Activity context) {
        String iMei = "";
        if (PermissionUtils.checkPermission(BaseApplication.getContext(), Manifest.permission.READ_PHONE_STATE )) {
            iMei =  DevicesUtils.getIMEI(BaseApplication.getContext());
        } else {
            PermissionUtils.showPermissionsDialog(context, "应用程序需要手机电话权限，是否开启？", false);
        }
        return  iMei;
    };



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPreferenceUtils.init(this);
    }
}
