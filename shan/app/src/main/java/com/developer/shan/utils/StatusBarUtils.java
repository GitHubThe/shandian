package com.developer.shan.utils;

import android.os.Build;
import android.view.WindowManager;

import com.developer.shan.ui.activity.BaseActivity;

/**
 * Created by plasway on 2017/6/9.
 */

public class StatusBarUtils {

    public static void initStatusBar(BaseActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

}
