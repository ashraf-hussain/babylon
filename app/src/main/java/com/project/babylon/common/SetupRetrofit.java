package com.project.babylon.common;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SetupRetrofit {

    String BaseURL = "http://jsonplaceholder.typicode.com/";

    public  Retrofit getRetrofit(){
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(BaseURL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         return retrofit;
    }

}
