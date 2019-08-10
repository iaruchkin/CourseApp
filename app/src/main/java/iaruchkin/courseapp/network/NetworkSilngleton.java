package iaruchkin.courseapp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkSilngleton {

    private static NetworkSilngleton networkSilngleton;
    private static final String URL = "https://api.nytimes.com/svc/";
    private TopStoriesEndpoint topStoriesEndpoint;

    public static synchronized NetworkSilngleton getInstance(){
        if (networkSilngleton == null){
            networkSilngleton = new NetworkSilngleton();
        }
        return networkSilngleton;
    }

    private NetworkSilngleton(){
        final OkHttpClient client = builtClient();
        final Retrofit retrofit = builtRertofit(client);
        final Retrofit retrofitJson = builtRertofitJson(client);

        Call call = client.newCall(requestBuilder());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String gsonResponse = response.body().string();
                Log.e("JSON", gsonResponse);
            }
        });

//        topStoriesEndpoint = retrofit.create(TopStoriesEndpoint.class);
        topStoriesEndpoint = retrofitJson.create(TopStoriesEndpoint.class);
    }

    private Retrofit builtRertofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Retrofit builtRertofitJson(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient builtClient(){

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(ApiKeyInterceptor.create())
                .build();
    }

    private Request requestBuilder() {
        return new Request.Builder()
                .get()
                .url(URL + "topstories/v2/home.json")
                .build();
    }

    @NonNull
    public TopStoriesEndpoint topStories() {
        return topStoriesEndpoint;
    }
}
