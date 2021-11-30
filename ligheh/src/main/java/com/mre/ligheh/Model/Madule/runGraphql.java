package com.mre.ligheh.Model.Madule;

import androidx.annotation.NonNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.mre.ligheh.API.APIEvents;

import okhttp3.OkHttpClient;

public class runGraphql extends APIEvents {
    public ApolloClient run(String token) {
        ApolloClient.Builder apolloClientBuilder = ApolloClient.builder()
                .serverUrl("https://devgraph.risloo.ir/graphql");
        if (token != null || !token.equals("")) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new AuthorizationInterceptor(token)).build();
            apolloClientBuilder.okHttpClient(okHttpClient);
        }

        return apolloClientBuilder.build();
    }

    @Override
    public void onOK(com.mre.ligheh.API.Response callback, Object response, Class aClass) {

    }
}
