package com.app.kalyanbusiness.baseapp.networking;

import android.util.Log;

import com.app.kalyanbusiness.baseapp.utils.Constants;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServicesHandler {

    private static WebServicesHandler instance;
    private static WebServices webServices;
    private static String token = "";

    private WebServicesHandler() {
        // Private constructor to prevent instantiation
    }

    private static WebServices initInstance(String s,String baseUrl) {
        token = s;

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message ->
                Log.e("Serverresponse :: ", message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(interceptor);

        if (s != null && !s.isEmpty()) {
            httpClientBuilder.addInterceptor(chain -> {
                okhttp3.Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + s)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            });
        }

        String url = "";
        if (baseUrl.equals("1")){
            url = Constants.BaseApis;
        }else{
            url = "https://kalyanmoney.com/codescafe/apis/";
        }


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClientBuilder.build());

        Retrofit retrofit = builder.build();
        webServices = retrofit.create(WebServices.class);
        return webServices;
    }

    public static WebServices getWebServices(String token,String baseUrl) {
        if (webServices == null) {
            return initInstance(token,baseUrl);
        } else {
            return webServices;
        }
    }
}
