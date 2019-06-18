package com.group.milan.debate.debate.ActivitySearch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivitySearch.model.SearchResponsePojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachRepository {
    SearchContract.Presenter presenter;
    Context context;

    public SeachRepository(SearchContract.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public SeachRepository(Context context){
        this.context=context;
    }

    public void hitSearchApi(JsonObject jsonObject){
        Call<SearchResponsePojo> apiInterface= DebateApiInstance.getSearchApiCallInstance().getSearchResponseData(jsonObject);
        apiInterface.enqueue(new Callback<SearchResponsePojo>() {
            @Override
            public void onResponse(Call<SearchResponsePojo> call, Response<SearchResponsePojo> response) {
                if(response.code() == 200){
                    SearchResponsePojo searchResponsePojo=response.body();
                    presenter.getDataToApi(searchResponsePojo);
                }else{
                    Toast.makeText(context,"erro code",Toast.LENGTH_LONG).show();
                    Log.i("milan_rep", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<SearchResponsePojo> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_rep", t.getMessage());
            }
        });
    }

    //user action addto squard api
    public void hitAddToSquardApi(JsonObject jsonObject){
        Call<AddToSquardPojo> apiInterface=DebateApiInstance.getActionApiCallInstance().getAddToSquardBtnData(jsonObject);
        apiInterface.enqueue(new Callback<AddToSquardPojo>() {
            @Override
            public void onResponse(Call<AddToSquardPojo> call, Response<AddToSquardPojo> response) {
                if(response.code() == 200){
                    AddToSquardPojo addToSquardPojo=response.body();
                    Toast.makeText(context,addToSquardPojo.getStatus(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AddToSquardPojo> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
                Log.i("milan_faield",t.getMessage());
            }

        });
    }

}
