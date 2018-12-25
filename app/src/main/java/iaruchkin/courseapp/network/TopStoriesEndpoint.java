package iaruchkin.courseapp.network;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TopStoriesEndpoint {

    @GET("topstories/v2/{section}.json")
    Single<TopStoriesResponse> get(@Path("section") @NonNull String section);
}


