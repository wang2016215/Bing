package com.example.administrator.bing;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bin.base.BingBaseAcitivity;
import com.example.bin.callback.IError;
import com.example.bin.callback.IFailure;
import com.example.bin.callback.ISuccess;
import com.example.bin.exception.BaseHttpResult;
import com.example.bin.observer.CommonObserver;
import com.example.bin.retrofit.RestClient;
import com.example.bin.retrofit.RestCreator;
import com.example.bin.rx.RxSchedulers;

import java.util.WeakHashMap;

import io.reactivex.annotations.NonNull;

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
