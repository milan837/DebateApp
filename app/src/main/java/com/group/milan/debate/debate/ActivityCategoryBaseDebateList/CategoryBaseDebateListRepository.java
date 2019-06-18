package com.group.milan.debate.debate.ActivityCategoryBaseDebateList;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.CategoryBaseDebateListResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryBaseDebateListRepository {
    Context context;
    CategoryBaseDebateListContract.Presenter presenter;

    public CategoryBaseDebateListRepository(CategoryBaseDebateListContract.Presenter presenter,Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitCategoryBaseApi(JsonObject jsonObject){
        Call<CategoryBaseDebateListResponse> apiInterface= DebateApiInstance.getHomeApiCallInstance().getCategoryDebateListApiData(jsonObject);
        apiInterface.enqueue(new Callback<CategoryBaseDebateListResponse>() {
            @Override
            public void onResponse(Call<CategoryBaseDebateListResponse> call, Response<CategoryBaseDebateListResponse> response) {
                if(response.code() == 200){
                    CategoryBaseDebateListResponse categoryBaseDebateListResponse=response.body();
                    presenter.getDataFromApi(categoryBaseDebateListResponse);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryBaseDebateListResponse> call, Throwable t) {
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
            }
        });
    }
}
