package com.example.chenlong.mvppractice.http;

import com.example.chenlong.mvppractice.BaseApplication;
import com.example.chenlong.mvppractice.http.api.MeiZiApiService;
import com.example.chenlong.mvppractice.util.NetWorkUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChenLong on 2017/1/30.
 */

public class RetrofitHelper
{
    private static OkHttpClient mOkHttpClient;

    static
    {
        initOkHttp();
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志
     */
    private static void initOkHttp()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkHttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(BaseApplication.newInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .cookieJar(new CookieJar()
                            {
                                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
                                @Override
                                public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
                                {
                                    cookieStore.put(url.host(),cookies);
                                }

                                @Override
                                public List<Cookie> loadForRequest(HttpUrl url)
                                {
                                    List<Cookie> cookies=cookieStore.get(url.host());
                                    return cookies!=null?cookies: new ArrayList<>();
                                }
                            })
//                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {

            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetWorkUtil.isNetworkConnected())
            {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else
            {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected())
            {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else
            {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     *
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static MeiZiApiService getMeiziApi()
    {
        return createApi(MeiZiApiService.class, MeiZiApiService.baseUrl);
    }


}
