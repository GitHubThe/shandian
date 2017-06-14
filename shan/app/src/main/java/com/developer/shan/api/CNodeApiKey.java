package com.developer.shan.api;

import com.developer.shan.api.service.CNodeService;
import com.developer.shan.utils.PostEntry.JsonConverterFactory;
import com.developer.shan.utils.http.HttpClient;

import okhttp3.MediaType;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by HG on 2017/6/13.
 */

public class CNodeApiKey {
    private static CNodeService cnodeInstance;

    public static CNodeService getCNodeService() {
        if (cnodeInstance == null) {
            initCNodeService();
        }
        return cnodeInstance;
    }

    private static void initCNodeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .client(HttpClient.INSTANCE.getOkHttpClient())
                .build();
        cnodeInstance = retrofit.create(CNodeService.class);
    }



}
