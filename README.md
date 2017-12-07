# Bing
Rxjava2.0 +retrofit2 +okhttp3 网络请求封装

#使用方法
在Application初始化
 Bing.init(this)
             .withApiHost("http://114.67.145.163/RestServer/api/")
             .withInterceptor(new LoggerInterceptor())
             .withLoaderDelayed(1000)
             .configure();
