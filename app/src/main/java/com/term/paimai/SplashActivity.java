package com.term.paimai;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

//public class SplashActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//    }
//}

public class SplashActivity extends Activity implements ServiceConnection {

    boolean isFirstIn = false;
//    private RegionChargeService regionChargeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(isFirstIn){
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    preferences.edit().putBoolean("isFirstIn", true).apply();
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent bindIntent = new Intent(this, RegionChargeService.class);
//        bindService(bindIntent, this, BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//        regionChargeService = ((RegionChargeService.LocalBinder)iBinder).getService();
//        regionChargeService.doLongRunningWork();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
//        regionChargeService = null;
    }
}
