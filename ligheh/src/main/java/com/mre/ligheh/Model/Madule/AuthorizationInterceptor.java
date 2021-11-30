package com.mre.ligheh.Model.Madule;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private String token;

    public AuthorizationInterceptor(String token) {
    this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!token.equals("")) {
            Request request = chain.request().newBuilder().addHeader("authorization", token).build();
            return chain.proceed(request);
        }else{
            return null;
        }
    }
}
