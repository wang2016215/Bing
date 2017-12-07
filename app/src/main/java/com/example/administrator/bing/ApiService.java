package com.example.administrator.bing;

import com.example.bin.exception.BaseHttpResult;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @autour: wanbin
 * date: 2017/12/7 0007 13:38
 * version: ${version}
 * des:
 */
public interface ApiService {

    @GET
    Observable<BaseHttpResult<String>> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);
}
