package com.example.administrator.bing;

import android.app.Application;

import com.example.bin.app.Bing;
import com.example.bin.interceptors.LoggerInterceptor;

/**
 * @autour: wanbin
 * date: 2017/12/7 0007 11:39
 * version: ${version}
 * des:
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bing.init(this)
             .withApiHost("http://114.67.145.163/RestServer/api/")
             .withInterceptor(new LoggerInterceptor())
             .withLoaderDelayed(1000)
             .configure();
    }
}
