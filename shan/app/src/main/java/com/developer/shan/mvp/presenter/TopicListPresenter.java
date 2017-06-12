package com.developer.shan.mvp.presenter;

import com.developer.shan.BaseApplication;
import com.developer.shan.api.CNodeApi;
import com.developer.shan.model.PmModel;
import com.developer.shan.ui.fragment.HomePageFragment;
import com.developer.shan.ui.fragment.TopicListFragment;
import com.developer.shan.utils.AES.AESUtils;
import com.developer.shan.utils.LogUtils;
import com.developer.shan.utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopicListPresenter extends BasePresenter<TopicListFragment> {

    private static final String LOG_TAG = "TopicListPresenter";

    private static final int PAGE_LIMIT = 50;
    private int mPageIndex = 1;
    private String mTab;
    private boolean mHasNextPage = true;
    private boolean mLoading = false;
    private String key;


    public void refresh() {
        mPageIndex = 1;
        getHomePage(mPageIndex, true);
    }

    public void loadNextPage() {
        getHomePage(mPageIndex, false);
    }


    private void getHomePage(final int pageIndex, final boolean refresh) {
        if (mLoading) {
            return;
        }
        mLoading = true;
        mCompositeSubscription.add(getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PmModel>() {
                    @Override
                    public void onCompleted() {
                        mLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onFailed(e);
                    }

                    @Override
                    public void onNext(PmModel tabModel) {
                        mPageIndex++;
                        mHasNextPage = tabModel.getData().size() >= PAGE_LIMIT;
                        getView().notifySuccess(tabModel, refresh);
/*)
                        if (tabModel.isSuccess()) {
                            getView().notifySuccess(tabModel, refresh);
                        } else {
                            getView().onFailed(new Throwable("获取列表失败"));
                        }*/
                    }
                }));

    }

    private Observable<PmModel> getObservable() {
        HashMap<String, String> map = new HashMap<>();
        map.put("package", BaseApplication.getPackageName1());
        map.put("channel", BaseApplication.getChanel());
        map.put("version", BaseApplication.getVersionName());
        map.put("app_version", BaseApplication.getVersionCode()+"");
        map.put("platform", BaseApplication.getPlatform());
        key = SharedPreferenceUtils.getString(HomePageFragment.KEY, "");
        String str = new Gson().toJson(map);
        LogUtils.debug(LOG_TAG, str);
        String decryptData  =  AESUtils.encrypt(key, str);
        LogUtils.debug(LOG_TAG, decryptData);
       // return null;
      return CNodeApi.getCNodeService().getHome(decryptData);
    }

    public void setTab(String tab) {
        mTab = tab;
    }

    public boolean isLoading() {
        return mLoading;
    }

    public boolean hasNextPage() {
        return mHasNextPage;
    }

    public int getPageIndex() {
        return mPageIndex;
    }

    public void setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
    }
}

