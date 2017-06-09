package com.developer.shan;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;

import com.developer.shan.ui.activity.BaseActivity;
import com.developer.shan.ui.fragment.BaseFragment;
import com.developer.shan.ui.fragment.CategoryFragment;
import com.developer.shan.ui.fragment.HomePageFragment;
import com.developer.shan.ui.fragment.MineFragment;
import com.developer.shan.utils.FragmentUtils;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;

    /*
    @BindView(R.id.contentViewPager)
    NotScrollViewPager mContentViewPager;*/

    private BaseFragment[] mFragments = new BaseFragment[3];
    private int mCurrPosition;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments = new BaseFragment[3];
            mFragments[0] = HomePageFragment.newInstance();
            mFragments[1] = CategoryFragment.newInstance();
            mFragments[2] = MineFragment.newInstance();
            FragmentUtils.addMultiple(getSupportFragmentManager(), R.id.content, mCurrPosition, mFragments);
        } else {
            mCurrPosition = savedInstanceState.getInt("currPosition");
            mFragments[0] = findFragment(HomePageFragment.class);
            mFragments[1] = findFragment(CategoryFragment.class);
            mFragments[2] = findFragment(MineFragment.class);

            if (mCurrPosition != 0) {
                updateNavigationBarState(mCurrPosition);
            }
        }

    }

    private <T extends BaseFragment> T findFragment(Class<T> tClass) {
        return FragmentUtils.findFragment(getSupportFragmentManager(), tClass);
    }

    private void updateNavigationBarState(int actionId) {
        Menu menu = mBottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem menuItem = menu.getItem(i);
            ((MenuItemImpl) menuItem).setExclusiveCheckable(false);
            menuItem.setChecked(menuItem.getItemId() == actionId);
            ((MenuItemImpl) menuItem).setExclusiveCheckable(true);
        }
    }

    @Override
    public void setListeners() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mCurrPosition = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.item_home_page:
                        showHideFragment(0);
                        break;
                    case R.id.item_category:
                        showHideFragment(1);
                        break;
                    case R.id.item_mine:
                        showHideFragment(2);
                        break;
                }
                return true;
            }
        });
    }

    private void showHideFragment(int position) {
        mFragments[position].setUserVisibleHint(true);
        FragmentUtils.showHideFragment(getSupportFragmentManager(), mFragments[position], null,false,false);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currPosition", mCurrPosition);
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }
}
