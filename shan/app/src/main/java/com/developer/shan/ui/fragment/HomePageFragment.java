package com.developer.shan.ui.fragment;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.developer.shan.BaseApplication;
import com.developer.shan.R;
import com.developer.shan.api.CNodeApi;
import com.developer.shan.common.Constant;
import com.developer.shan.model.EncryptDataModel;
import com.developer.shan.utils.AES.Base64Utils;
import com.developer.shan.utils.AES.RSAUtils;
import com.developer.shan.utils.DevicesUtils;
import com.developer.shan.utils.FragmentUtils;
import com.developer.shan.utils.LogUtils;
import com.developer.shan.utils.NetWorkUtils;
import com.developer.shan.utils.PermissionUtils;
import com.developer.shan.utils.SharedPreferenceUtils;
import com.developer.shan.utils.TimeUtils;
import com.developer.shan.utils.VersionUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.security.PrivateKey;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.R.attr.versionCode;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment  {

    private static final String LOG_TAG = "HomePageFragment";
    public static final String SIV = "siv";
    public static final String KEY = "key";
    private Boolean hasSim;
    private String netWorkType;
    private String timeStr;



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
        initView();
        startApp();
    }

    private void initView() {
        hasSim = DevicesUtils.hasSimCard(BaseApplication.getContext());
        netWorkType = NetWorkUtils.getNetWorkStatus(BaseApplication.getContext());
        timeStr = TimeUtils.getTime();
    }

    private void startApp() {
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EncryptDataModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(EncryptDataModel encryptDataModel) {
                        if (encryptDataModel.getRetCode() == Constant.success) {
                            try {
                                InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
                                PrivateKey privateKey =  RSAUtils.loadPrivateKey(inPrivate);
                                // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
                                byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptDataModel.getEncryptData()), privateKey);
                                String decryptStr = new String(decryptByte);
                                JSONObject jsonObject = new JSONObject(decryptStr);
                                String siv = jsonObject.getString("siv");
                                String key = jsonObject.getString("key");
                                SharedPreferenceUtils.save("siv", siv);
                                SharedPreferenceUtils.save("key", key);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));

    }

    private Observable<EncryptDataModel> getObservable() {
        return CNodeApi.getCNodeService().appStart(BaseApplication.getDeviceId(getActivity()), hasSim, netWorkType, timeStr, BaseApplication.getPlatform(),
                BaseApplication.getVersionName(), BaseApplication.getVersionCode(), BaseApplication.getChanel());
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
