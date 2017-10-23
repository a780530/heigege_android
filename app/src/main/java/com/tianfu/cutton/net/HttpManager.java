package com.tianfu.cutton.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaohei on 2017/3/28.
 */

public class HttpManager {
//    public static String ServerUrl = "http://test.efuren.com/";//测试
//            public static String ServerUrl = "http://yf.xjmcotton.com/";//预发
    public static String ServerUrl = "http://www.xjce.org/";//线上
    private static OkHttpClient mClient;
    private static ServerApi mServerApi;
    private static Retrofit retrofit;

    public static ServerApi getServerApi() {
        return mServerApi;
    }

    // 构造函数私有,就不能new
    private HttpManager() {
    
    }

    public static void init() {
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            /*    .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        if (request.body() instanceof FormBody) {
                            for (int i = 0; i < ((FormBody) request.body()).size(); i++) {
                                hashMap.put((((FormBody) request.body()).encodedName(i)),((FormBody) request.body()).value(i));
                            }
                        }
                        HttpUtilsSign httpUtilsSign = new HttpUtilsSign();
                        String sign = httpUtilsSign.sign(hashMap);
                        Request authorised = request.newBuilder().header("Authorization",sign).build();
                        return chain.proceed(authorised);
                    }
                })*/
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerUrl)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServerApi = retrofit.create(ServerApi.class);
    }

}