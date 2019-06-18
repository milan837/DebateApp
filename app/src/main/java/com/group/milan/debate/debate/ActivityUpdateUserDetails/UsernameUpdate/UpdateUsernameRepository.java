package com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.model.UpdateUsernamePojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUsernameRepository {
    Context context;
    UpdateUsernameContact.Presenter presenter;

    public UpdateUsernameRepository(Context context, UpdateUsernameContact.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitUpdateUsernameApi(final JsonObject jsonObject){
        Call<UpdateUsernamePojo> apiInterface= DebateApiInstance.getApiCallInstance().getUpdateUsernameData(jsonObject);
        apiInterface.enqueue(new Callback<UpdateUsernamePojo>() {
            @Override
            public void onResponse(Call<UpdateUsernamePojo> call, Response<UpdateUsernamePojo> response) {
                Log.i("milan_error_code", String.valueOf(response.code()));
                if(response.code() == 200){
                    UpdateUsernamePojo updateUsernamePojo=response.body();
                    presenter.getDataFromApi(updateUsernamePojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_error_code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<UpdateUsernamePojo> call, Throwable t) {
                Toast.makeText(context,"field to hit api",Toast.LENGTH_LONG).show();
            }
        });
    }
}