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
import com.developer.shan.utils.TimeUtils;
import com.developer.shan.utils.VersionUtils;

import java.io.InputStream;
import java.security.PrivateKey;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment  {

    private static final String LOG_TAG = "HomePageFragment";
    public static String app = "android";
    private String iMei;
    private Boolean hasSim;
    private int versionCode;
    private String netWorkType;
    private String timeStr;
    private String versionName;
    private String chanel;


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

        if (PermissionUtils.checkPermission(BaseApplication.getContext(), Manifest.permission.READ_PHONE_STATE )) {
            iMei =  DevicesUtils.getIMEI(BaseApplication.getContext());
        } else {
            PermissionUtils.showPermissionsDialog(getActivity(), "应用程序需要手机电话权限，是否开启？", false);
        }
        hasSim = DevicesUtils.hasSimCard(BaseApplication.getContext());
        netWorkType = NetWorkUtils.getNetWorkStatus(BaseApplication.getContext());
        timeStr = TimeUtils.getTime();
        versionName = VersionUtils.getVersionName(BaseApplication.getContext());
        versionCode = VersionUtils.getVersionCode(BaseApplication.getContext());
        chanel = "0001";
    }

    public static String PRIVATE_KEY =
            "MIIJKAIBAAKCAgEAtJ2KZ2t3hnwzUgrvomABhP/qJsgtHak+ufCOrLqsXNtB9Ib7" + "\r"  +
                    "juo+HpUAirYs5NdSGItL9V2mJB9QkcZG5N9JDuqIP/SOmzK6ygqydxlV8yy2Gw60" + "\r"  +
                    "yVVLErL+1dceI1wKAdIUzoykNMJSGwpqV/Yoeynxesg+MvVq7IfAamdr29eITep7" + "\r"  +
                    "wL0kFvR+aZIFG8vH2IpzebsKOsEVoRYnmGHsk60iiQNHJRjCzPnpK2vM7qsSJ5nJ" + "\r"  +
                    "odiFmJZkNoEaOhuWIBYcneoqiX8Ax7KiakGTpkT59T3ta57D+hWHEW8i8i2GqurE" +"\r"  +
                    "38QjlAzvt9P0ErggiTqMyyVIEL4Om0iZCdS4NradidwAeRrzQwFsYZ6TVDhYGKik" +"\r"  +
                    "cjmBw4IgRWWRblMQ9wlB3os8ssnLP8ltpC5OY6l7fONN4/K6lDrk11A3TDmqgQKH" +"\r"  +
                    "tohRPgxCRMGlNEpiQWrnCULx9rNiiGenbEcO0cNPIc1doFEEnzdkpPcq1Yawswjc" +"\r"  +
                    "5aadkKf+J+SZULqKAiBDQk1C2k8OiNyQNTAiS5a80YYX/D3JL03ibDBQQnopCf2l" +"\r"  +
                    "XblzmwINW3gzDSk7oDI2tpuxip868dN8yuyEuDFpNHGGImxhndllGRjwOoxxX0Qr" +"\r"  +
                    "qa04TYI5aqc7vPGMEDNhNGI/aCeZk6ro1WiyVNfGNAmEnmmbXvzi48ez3+cCAwEA" +"\r"  +
                    "AQKCAgAGTu5TmGRPVS0xs2IVSMckzw9tlVZGD/A57FX/YHG+uIXANaq5niLXTJr9" +"\r"  +
                    "PUmqIH8kjlDz2/Z+kn1s6gD143qYw8ZIfZExiH8PqXn8QO1IlgoyPbxAv/LkT04p" +"\r"  +
                    "R98smv1afOzvHehqU1Ms0JtQQS++xYNCp5XMj9fRNRiaXsxcnFWNayAxZinJh3aW" +"\r"  +
                    "11vIUcDEJmnMGaVM0gugWba+UsVBjy2CnZOxjwDWye/D7Xr15xG2Ql/RgE+COdZ8" +"\r"  +
                    "NNwmpWoD6uHK/i/IJ6BuWSXlEi9HAme8khIlzSDJLlFOYZRV4xMuCw8fRal8dUs2" +"\r"  +
                    "H3gJ41Z3lIzwV3gB5bF66Pk3Z6+bYmhP+AbAr/9Gb38N46XUeS2UOCNFQQdsLxxr" +"\r"  +
                    "whs4KjFdpTX9zNdZGcDMtp9f7622J4Vcb9eDBcOvYfbv+sWu9anbH5d+e9wVkx5E" +"\r"  +
                    "qITdKAWGUVUiougCvEBrQBVQIRJJBhoAwvtRe9sN9vMaFerHlr3k/WrxcfFaILvo" +"\r"  +
                    "f2mTsmMQe+NLRO19byN7hcRGn6R+w/76ZYntSXTxiU9eByDL7qfcnRGAM1XQvcq8" +"\r"  +
                    "mQu9xTASEBehVeLF2vCOiSpDfbPdtXGUFRVjI4HWcMFWmnu1A2DD9+Qpp1iqzYjY" +"\r"  +
                    "SREOTA6bHwW1ooVwpTO5cpUdDZzJ10tFImxVTkW2Cj1S/SALeQKCAQEAuEyOMEBq" +"\r"  +
                    "qRKR0qg58gEZx8P26EgZAv0cgSf7lE6BYEDNhpWESOOPEmesqwkzgpvm7Iyz2uzh" +"\r"  +
                    "RLAmGdFrWZGdnLhLVnYsjWrXajJCdblyZu1k5M/8ssBaP2/WIg8OKPlTwh4Fop4D" +"\r"  +
                    "MUEZCsrjAv2ufwSVADHIDgD8Iw2yOSOJtCu+Oa0GJ4OST9liyUI+2cmHBgj0Ei/J" +"\r"  +
                    "FDKCiSVjruB03K0NhfShjXxsEGWDpeoxasWiFMIIk1aCVi5l7d03Ja4ae1hZ4K5h" +"\r"  +
                    "bY3t5yQMVj2ka85iEY9B6/s/pkrMNGlGgEAf8CA3L4Yy/HxZTC8HgDgXky2wEYYb" +"\r"  +
                    "eqk5xpdX5rty0wKCAQEA+uIbuxp0W7za7J1Nu5bM1h9wZ/P1azsJz1uBHm7ZG1n3" +"\r"  +
                    "V4s/NnoG2bwZY/JNNjGOKLAo99UdYOeFGG2upvGKALozuIaDrXGXmJLiyB2sij6U" +"\r"  +
                    "2c6Uqr06AUuqDSpLSRiAT11NRCCGvJ0u8Gx1dCKx4EA488MQhhGq/UY25GC9TISj" +"\r"  +
                    "+dkY5K8GKkZo7Wng349pXICZSOyxYtMRnoALBcMd3Xq50bVKVnZypo7b+9nSlMM+" +"\r"  +
                    "2g0Rn6l164Fdg8lU7UVnGVjRxK8FAWbfoqY5Z4OO9dO2VHvMk68dC/NWGTNi+Ewk" +"\r"  +
                    "a97VIkZUhf+EmNHHVxqti5nr5tSaINME5WUFLJHqHQKCAQEAgSidqXErjsvFyFU/" +"\r"  +
                    "LrvYvmg2nHkSlkaeeGF17WzLPJKNttkH+TEJLoPbvY3YPJtXzcWZsj66bLHqMcPT" +"\r"  +
                    "MNVdGN0LZ0+wT3dl+jW439oUM9ABGl4soXUuijCtZ/cXHF4Hdg4mMt38LMOLckXX" +"\r"  +
                    "rFvus3OZnKZGppHIIiTSFzsxoUPar37zxjNuEWmu0o8LjwOuDeuuaSR2DiKL2uOK" +"\r"  +
                    "JZ+GJrVcudlEcuyUqzUJevhSo5pXyaZl7iP9ebpQks+qe0y1oGW9rzlmbCnAZXGc" +"\r"  +
                    "97+BgFAF6Hp2X5QX+kmHLN/umajW+SkKo9Kv3sSirEJ1RkWJOAsXzmAm1+LK68Na" +"\r"  +
                    "xgfuIwKCAQAgb4U4TnZGugbv2nKHUzrOYCgvOGo9X9en2GO8JIarM5l31wfoKiSi" +"\r"  +
                    "l+AMfPIglI2Vkxjo2cO/mI8rllqjOM0nxOsU60cw8GOyFSN+zd9VzDuAYWX1IRvT" +"\r"  +
                    "uQ2WQCaVn7ZPDHgF29Kvpf7AlLUmu6mVnZf/Y/PJ8ZTSw/Yq1psCvo9YqQm/3yUD" +"\r"  +
                    "6DB+qVaqcKclj3bB5+ATL8iyXkmOiChfTxOhGjgvL4cpI4UrbYD9PLTuigwCQW1+" +"\r"  +
                    "ci498JSrTHLE4yfTrvENulgldioryOV9wM5s48iSO2yc8+vovC0KfwWicJ/Lm54n" +"\r"  +
                    "6N4ix9Rm3YscZl0KbegzYDTc14+wsHi9AoIBAB8bgtJ0aoIRmhLaPEwDQVoKTYYK" +"\r"  +
                    "VPGk/PI9rSmpDk9L8FOhT90tX5P6iuzOx+EHAZhvrl2txhpcIV8Btj4wr48tGiP1" +"\r"  +
                    "zCi5awBDe8l5HqWTrFw7EPB9X41WZIrVyjqCtWcQ4Z7S+o1bYXIkZSxkOgvxhAuK" +"\r"  +
                    "ROTuLKqRvVcQ1oZfO2GlVluThF35dAps3zlSRVpI/GF8dAMHxtHXvbvGhnk3GAoK" +"\r"  +
                    "0P3gkvhAVj3R07DSqqZzufsGYK/g8oYnUSBevofX7xVKfx97y0Z3EKJHu1EFcs2x" +"\r"  +
                    "9Vn3eR6HvWJI0mxk504VdOHjGNab1JUKPsdeF2tDI8FVhantD3bVcmpbRpI=" +"\r" ;

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

                        if (encryptDataModel.getRetCode() == Constant.success){
                            try
                            {
                                // 从字符串中得到私钥
                                PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
                                // 从文件中得到私钥
                                //InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
                                //PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
                                // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
                                byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptDataModel.getEncryptData()), privateKey);
                                String decryptStr = new String(decryptByte);
                                LogUtils.debug(LOG_TAG, decryptStr);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                                LogUtils.debug(LOG_TAG, e.toString());
                            }
                        }

                    }
                }));

    }

    private Observable<EncryptDataModel> getObservable() {
        return CNodeApi.getCNodeService().appStart(iMei, hasSim, netWorkType, timeStr, app, versionName, versionCode, chanel);
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
