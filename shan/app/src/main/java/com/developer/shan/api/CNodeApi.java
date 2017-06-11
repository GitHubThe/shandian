package com.developer.shan.api;

import com.developer.shan.api.service.CNodeService;
import com.developer.shan.utils.http.HttpClient;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HG on 2017/6/10.
 */

public class CNodeApi {

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
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .serializeNulls()
                        .create()))
                .client(HttpClient.INSTANCE.getOkHttpClient())
                .build();
        cnodeInstance = retrofit.create(CNodeService.class);
    }

}
