package iaruchkin.courseapp.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiKeyInterceptor implements Interceptor {


    private static final String TOP_STORIES_API_KEY = "RNk9dG9G7RrDEeKoVBC7UfDActFAD8lW";
    private static final String API_KEY_HEADER_NAME = "api-key";


    public static ApiKeyInterceptor create() {
        return new ApiKeyInterceptor();
    }


    private ApiKeyInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
