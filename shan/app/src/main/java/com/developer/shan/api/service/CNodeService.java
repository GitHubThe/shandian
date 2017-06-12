package com.developer.shan.api.service;

import com.developer.shan.model.EncryptDataModel;
import com.developer.shan.model.PmModel;
import com.developer.shan.model.TabModel;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HG on 2017/6/10.
 */

public interface CNodeService {


    @POST("/main/index")
    Observable<PmModel> getHome(@);
    //测试接口
    /**
     * 根据tab名字获取tab文章列表
     * 可用tab: ask share job good
     *
     * @return
     */
    @POST("/main/index")
    Observable<PmModel> getHomePage(@Query("package") String pack, @Query("channel") String channel, @Query("version") String version,
                                      @Query("app_version") Integer app_version, @Query("platform") String platform);

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
