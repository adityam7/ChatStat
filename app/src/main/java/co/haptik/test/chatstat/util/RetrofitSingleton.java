package co.haptik.test.chatstat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class RetrofitSingleton {

    private static Retrofit singleton;
    private static final String BASE_URL = "http://haptik.co/";

    private static Retrofit setUpRetrofit(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit getInstance() {
        if(singleton == null) {
            singleton = setUpRetrofit(BASE_URL);
        }
        return singleton;
    }
}
