package com.developer.shan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.shan.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Created by plasway on 2017/6/9.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        StatusBarUtils.initStatusBar(this);
        ButterKnife.bind(this);
        initView(savedInstanceState);
        setListeners();
        bind();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }

    //返回content layout id  return

    public abstract int getContentView();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void setListeners();

    public abstract void bind();

    public abstract void unBind();


}
