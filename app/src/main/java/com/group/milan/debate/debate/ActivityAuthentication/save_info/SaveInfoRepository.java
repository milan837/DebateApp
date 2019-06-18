package com.group.milan.debate.debate.ActivityAuthentication.save_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveInfoRepository {
    SaveInfoContract.Presenter saveInfoPresenter;
    Context context;

    public SaveInfoRepository(SaveInfoContract.Presenter presenter,Context context){
        this.context=context;
        saveInfoPresenter=presenter;
    }

    public void hitSaveInfoApi(RequestBody userId, RequestBody username, MultipartBody.Part image, final ProgressDialog progressDialog){

        Call<SaveInfoResponse> apiInterface= DebateApiInstance.getApiCallInstance().getSaveInfoApiData(username,userId,image);
        apiInterface.enqueue(new Callback<SaveInfoResponse>() {
            @Override
            public void onResponse(Call<SaveInfoResponse> call, Response<SaveInfoResponse> response) {
                if(response.code() == 200){
                    SaveInfoResponse saveInfoResponse=response.body();

                    //saving data to shared preferences
                    SharedPreferences sharedPreferences=context.getSharedPreferences("users",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user_id",saveInfoResponse.getResponse().getUserId());
                    editor.putString("username",saveInfoResponse.getResponse().getUsername());
                    editor.putString("profile_picture",saveInfoResponse.getResponse().getImageUrl());
                    editor.commit();

                    saveInfoPresenter.getDataFromApi(saveInfoResponse);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SaveInfoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
            }
        });

    }
}
