# Bing
Rxjava2.0 +retrofit2 +okhttp3 网络请求封装

#使用方法
#step: 1 初始化
		//在baseApplication 初始化
	   @Override
	    public void onCreate() {
	        super.onCreate();
	        Bing.init(this)
	             .withApiHost("http://114.67.145.163/RestServer/api/")//设置host
	             .withInterceptor(new LoggerInterceptor())//设置拦截器可设置多个
	             .withLoaderDelayed(1000)//设置超时时间
	             .configure();
	    }

#step:2 测试
	public class MainActivity extends BingBaseAcitivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {


        //test1();
        //testRx1();
        testRx2();

    }

    public void test1(){

        final String url = "about.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();

    }

    public void testRx1(){
        final String url = "about.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        RestCreator.getRxRestService().get(url,params)
                .compose(this.<String>bindToLifecycle())
                .compose(RxSchedulers.<String>compose())
                .subscribe(new CommonObserver<String>(this) {
                    @Override
                    public void onNext(@NonNull String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });
    }

	//自定义 apiservice 	
    public void testRx2(){
        final String url = "about.php";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        RestCreator.getRetrofit().create(ApiService.class)
                .get(url,params)
                .compose(this.<BaseHttpResult<String>>bindToLifecycle())
                .compose(RxSchedulers.<BaseHttpResult<String>>compose())
                .subscribe(new CommonObserver<BaseHttpResult<String>>(MainActivity.this) {
                    @Override
                    public void onNext(@NonNull BaseHttpResult<String> BaseHttpResult) {
                        Toast.makeText(MainActivity.this, BaseHttpResult.getData(), Toast.LENGTH_LONG).show();
                    }
                });
    }
	}



继承BingBaseAcitivity主要是为了使用rxjava 生命周期管理也可不继承


#step:3 添加权限

   	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
			