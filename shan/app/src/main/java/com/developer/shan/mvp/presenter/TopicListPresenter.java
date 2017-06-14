package com.developer.shan.mvp.presenter;

import android.os.Build;

import com.developer.shan.BaseApplication;
import com.developer.shan.utils.AES.AES;
import com.developer.shan.api.CNodeApiKey;
import com.developer.shan.api.Urls;
import com.developer.shan.model.HomeModel;
import com.developer.shan.ui.fragment.HomePageFragment;
import com.developer.shan.ui.fragment.TopicListFragment;
import com.developer.shan.utils.LogUtils;
import com.developer.shan.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;

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



    Runnable network = new Runnable() {
        @Override
        public void run() {
            //传递的json数据
            JSONObject object = new JSONObject();
            try {
                object.put("package", BaseApplication.getPackageName1());
                object.put("channel", BaseApplication.getChanel());
                object.put("version", BaseApplication.getVersionName());
                object.put("app_version", BaseApplication.getVersionCode()+"");
                object.put("platform", BaseApplication.getPlatform());
                object.put("device_id", BaseApplication.getDeviceId(getView().getActivity()));
                object.put("sim",BaseApplication.hasSim());
                object.put("network",BaseApplication.getNetWorkType());
                object.put("t",BaseApplication.getTimeStr());
                object.put("device_model", Build.MODEL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String str= object.toString();
            OkHttpClient client = new OkHttpClient();
            LogUtils.debug(LOG_TAG, str);
            MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
            // 加密方法
            String decry = "";
            try {
               decry =  AES.getInstance().encrypt(str);
                LogUtils.debug(LOG_TAG, decry);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Request request = new Request.Builder()
                    .url(Urls.BASE_URL + "main/index")
                    .post(RequestBody.create(MEDIA_TYPE_TEXT, decry))
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("服务器端错误: " + response);
                }
                String rsp =  response.body().string();
                try {
                    LogUtils.debug(LOG_TAG, "rsp = "  + rsp);
                    String dec =  AES.getInstance().decrypt(rsp);
                    LogUtils.debug(LOG_TAG, "dec = "  + dec);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    private void getHomePage(final int pageIndex, final boolean refresh) {
        if (mLoading) {
            return;
        }
        mLoading = true;
        key = SharedPreferenceUtils.getString(HomePageFragment.KEY, "");
        if (key.isEmpty()) {
            return;
        }
        new Thread(network).start();
    }



    private Observable<HomeModel> getObservable() {
        String decryptData = "";
        return CNodeApiKey.getCNodeService().getHome( decryptData);
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



/*
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/plain; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            RequestBody requestBody = new RequestBody() {
                @Override public MediaType contentType() {
                    return MEDIA_TYPE_MARKDOWN;
                }

                @Override public void writeTo(BufferedSink sink) throws IOException {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("package", BaseApplication.getPackageName1());
                        object.put("channel", BaseApplication.getChanel());
                        object.put("version", BaseApplication.getVersionName());
                        object.put("app_version", BaseApplication.getVersionCode()+"");
                        object.put("platform", BaseApplication.getPlatform());
                        object.put("device_id", SharedPreferenceUtils.getString(BaseApplication.iMei, ""));
                        object.put("sim",BaseApplication.hasSim());
                        object.put("network",BaseApplication.getNetWorkType());
                        object.put("t",BaseApplication.getTimeStr());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String str = object.toString();
                    LogUtils.debug(LOG_TAG, str);
                    String decryptData  =  AESUtils.encrypt(key, str);
                    LogUtils.debug(LOG_TAG, decryptData);
                    sink.writeUtf8(decryptData);
                }
            };

            Request request = new Request.Builder()
                    .url(Urls.BASE_URL + "main/index")
                    .post(requestBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                LogUtils.debug(LOG_TAG, response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };*/


        /*
        mCompositeSubscription.add(getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeModel>() {
                    @Override
                    public void onCompleted() {
                        mLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onFailed(e);
                    }

                    @Override
                    public void onNext(HomeModel homeModel) {
                        // mPageIndex++;
                        //  mHasNextPage = tabModel.getData().size() >= PAGE_LIMIT;
                        // getView().notifySuccess(tabModel, refresh);
/*)
                        if (tabModel.isSuccess()) {
                            getView().notifySuccess(tabModel, refresh);
                        } else {
                            getView().onFailed(new Throwable("获取列表失败"));
                        }*/
    //        }
    //  }));
}

