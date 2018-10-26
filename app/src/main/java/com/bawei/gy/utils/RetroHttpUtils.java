package com.bawei.gy.utils;

import android.util.Log;

import com.bawei.gy.model.http.Contrac;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * _ヽ陌路离殇ゞ on 2018/10/22
 */
public class RetroHttpUtils {

    private final OkHttpClient build;
    private static RetroHttpUtils retroHttpUtils;

    private RetroHttpUtils(){
        build = new OkHttpClient.Builder()
                .addInterceptor(new LogginInstance())
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    class LogginInstance implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            //请求
            Request request = chain.request();
            //获取地址
            String method = request.method();
            //获取路径
            HttpUrl url = request.url();
            Log.d("LogginInstance", method + "===" + url);
            Response proceed = chain.proceed(request);
            return proceed;
        }
    }

    public <T> T CreatApi(Class<T> cla){
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Contrac.base_url)
                .client(this.build)
                .build();
        return build.create(cla);

    }

    public static RetroHttpUtils getInstan(){
        if(retroHttpUtils==null){
            synchronized (RetroHttpUtils.class){
                if(retroHttpUtils==null){
                    retroHttpUtils=new RetroHttpUtils();
                }
            }
        }
        return retroHttpUtils;
    }
}
