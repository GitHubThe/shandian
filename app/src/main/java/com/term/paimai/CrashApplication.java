package com.term.paimai;

import android.app.Application;

/**
 * Created by 武 on 2015/11/22.
 */

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
