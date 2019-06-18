package com.group.milan.debate.debate.ActivityAllCategory;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAllCategory.model.AllCategoryPojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoryRepository {
    AllCategoryPresenter allCategoryPresenter;
    Context context;

    public AllCategoryRepository(AllCategoryPresenter allCategoryPresenter, Context context) {
        this.allCategoryPresenter = allCategoryPresenter;
        this.context = context;
    }

    public void hitApi(JsonObject jsonObject){
        Call<AllCategoryPojo> apiInterface= DebateApiInstance.getCategoryApiCallInstance().getAllCategoryResponseData(jsonObject);
        apiInterface.enqueue(new Callback<AllCategoryPojo>() {
            @Override
            public void onResponse(Call<AllCategoryPojo> call, Response<AllCategoryPojo> response) {
                if(response.code() == 200){
                    AllCategoryPojo allCategoryPojo=response.body();
                    allCategoryPresenter.getDataFromApi(allCategoryPojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_rep", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AllCategoryPojo> call, Throwable t) {
                Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                Log.i("milan_rep", t.getMessage());
            }
        });
    }
}
