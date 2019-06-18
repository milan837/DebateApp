package com.group.milan.debate.debate.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DebateApiInstance {

    public static DebateApiCall getApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/authentication.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getHomeApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/home.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getDebateApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/debate.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getActionApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/actions.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getUserProileApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/profile.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getSearchApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/search.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }

    //-- for home api instance
    public static DebateApiCall getCategoryApiCallInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        DebateApiCall retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.137.1/debate/app/api/public/category.php/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(DebateApiCall.class);
        return retrofit;

    }



}
