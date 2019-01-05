package com.ljh.newgank_kotlin.http;

import com.ljh.newgank_kotlin.ui.bean.GankResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mr.Hao on 2018/10/16.
 */
public interface GankApi {
    @GET("data/{type}/{count}/{pageNum}")
    Observable<GankResult> getGankResult(
            @Path("type") String type,
            @Path("count") Integer count,
            @Path("pageNum") Integer pageNum);
}
