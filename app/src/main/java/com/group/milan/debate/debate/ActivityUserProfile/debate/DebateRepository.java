package com.group.milan.debate.debate.ActivityUserProfile.debate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserProfilePojo;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DebateRepository {
    Context context;
    DebateContract.Presenter presenter;

    public DebateRepository( DebateContract.Presenter presenter,Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    public void hitUserProfileApi(JsonObject jsonObject){
        Call<UserProfilePojo> apiInterface= DebateApiInstance.getUserProileApiCallInstance().getUserProfileData(jsonObject);
        apiInterface.enqueue(new Callback<UserProfilePojo>() {
            @Override
            public void onResponse(Call<UserProfilePojo> call, Response<UserProfilePojo> response) {
                Log.i("milan_repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    UserProfilePojo userProfilePojo=response.body();
                    presenter.getDataFromApi(userProfilePojo);
                }else{
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                    Log.i("milan_repo", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<UserProfilePojo> call, Throwable t) {
                Toast.makeText(context,"field to hist api",Toast.LENGTH_LONG).show();
                Log.i("milan_repo", String.valueOf(t.getMessage()));
            }
        });
    }
}
