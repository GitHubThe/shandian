package com.developer.shan.utils.PostEntry;

import android.util.Log;

import com.developer.shan.model.HomeModel;
import com.developer.shan.ui.fragment.HomePageFragment;
import com.developer.shan.utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String response = responseBody.string();

        String strResult = response.substring(1, response.length() - 1);
        String key = SharedPreferenceUtils.getString(HomePageFragment.KEY, "");
        //String result = AESUtils.decrypt(key,strResult);//解密
       // Log.i("xiaozhang", "解密的服务器数据：" + result);
       // HomeModel pageBean = mGson.fromJson(result, HomeModel.class);
      //  return (T) pageBean;
        return null;


    }

}