package com.example.chenlong.mvppractice.http.api;

import com.example.chenlong.mvppractice.demo2.mvp.bean.MeiZiBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ChenLong on 2017/2/5.
 */

public interface MeiZiApiService
{
    String baseUrl = "http://gank.io/api/";

    @GET("data/福利/{pageSize}/{page}")
    Flowable<MeiZiBean> getMeiZiInfos(@Path("pageSize") int pageSize, @Path("page") int page);
}
