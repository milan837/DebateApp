package com.group.milan.debate.debate.ActivityChangePassword;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChangePassword.pojo.ChangePasswordResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {
    Context context;
    ChangePasswordContract.Presenter presenter;

    public ChangePasswordRepository(Context context, ChangePasswordContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitChangePasswordApi(JsonObject jsonObject){
        Call<ChangePasswordResponse> apiInterface= DebateApiInstance.getApiCallInstance().getChangePasswordApi(jsonObject);
        apiInterface.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                Log.i("milan_res", String.valueOf(response.code()));
                if(response.code() == 200){
                    ChangePasswordResponse changePasswordResponse=response.body();
                    presenter.getDataFromApi(changePasswordResponse);
                }else{
                    Log.i("milan_error_code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(context,"faield api called",Toast.LENGTH_LONG).show();
            }
        });
    }
}
