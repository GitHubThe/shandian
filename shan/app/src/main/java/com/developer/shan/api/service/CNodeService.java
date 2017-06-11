package com.developer.shan.api.service;

import com.developer.shan.model.EncryptDataModel;
import com.developer.shan.model.PmModel;
import com.developer.shan.model.TabModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HG on 2017/6/10.
 */

public interface CNodeService {


    //测试接口
    /**
     * 根据tab名字获取tab文章列表
     * 可用tab: ask share job good
     *
     * @return
     */
    @GET("PlasApp")
    Observable<PmModel> getSearchList(@Query("type") String type, @Query("page") Integer pageIndex, @Query("limit") Integer limit,
                                      @Query("genre") String genre, @Query("key") String key);

    /**
     * APP启动获取
     *
     * @return
     */
    @GET(" main/startup")
    Observable<EncryptDataModel> appStart(@Query("device_id") String device_id, @Query("sim") Boolean sim, @Query("network") String network,
                                               @Query("t") String t, @Query("app") String app,
                                               @Query("v") String v, @Query("in") Integer in, @Query("c") String c);

    /**
     * 获取首页文章列表
     *
     * @param pageIndex
     * @param limit
     * @param mdrender
     * @return
     */
    @GET("topics")
    Observable<TabModel> getTopicPage(@Query("page") Integer pageIndex, @Query("limit") Integer limit, @Query("mdrender") Boolean mdrender);

    /**
     * 根据tab名字获取tab文章列表
     * 可用tab: ask share job good
     *
     * @param tab
     * @param pageIndex
     * @param limit
     * @param mdrender
     * @return
     */
    @GET("topics")
    Observable<TabModel> getTabByName(@Query("tab") String tab, @Query("page") Integer pageIndex, @Query("limit") Integer limit, @Query("mdrender") Boolean mdrender);
}
