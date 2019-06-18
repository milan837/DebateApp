package com.group.milan.debate.debate.ActivityHome;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.group.milan.debate.debate.ActivityHome.model.HomeActivityResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    Context context;
    HomeContract.Presenter presenter;

    public HomeRepository(HomeContract.Presenter presenter,Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitDebateApiList(String id){
        Call<HomeActivityResponse> apiInterface= DebateApiInstance.getHomeApiCallInstance().getHomeApiData(id);
        apiInterface.enqueue(new Callback<HomeActivityResponse>() {
            @Override
            public void onResponse(Call<HomeActivityResponse> call, Response<HomeActivityResponse> response) {
                Log.i("milan_res", String.valueOf(response.code()));
                if(response.code() == 200){
                    HomeActivityResponse homeActivityResponse=response.body();
                    presenter.getDataFromApi(homeActivityResponse);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HomeActivityResponse> call, Throwable t) {
                Toast.makeText(context,"Faield to Hit Api",Toast.LENGTH_LONG).show();
            }
        });

    }


}
