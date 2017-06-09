package com.developer.shan.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.shan.R;
import com.developer.shan.utils.FragmentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {


    public static HomePageFragment newInstance() {
        Bundle bundle = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {
        if (getChildFragmentManager().findFragmentByTag(TopicListFragment.class.getName()) == null) {
            TopicListFragment fragment = TopicListFragment.instance(null);
            fragment.setUserVisibleHint(true);
            FragmentUtils.replace(getChildFragmentManager(), R.id.home_page_content, fragment, false, TopicListFragment.class.getName());
        } else {
            FragmentUtils.findFragment(getChildFragmentManager(), TopicListFragment.class).setUserVisibleHint(true);
        }
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

}
