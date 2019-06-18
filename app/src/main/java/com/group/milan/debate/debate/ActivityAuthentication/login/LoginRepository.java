package com.group.milan.debate.debate.ActivityAuthentication.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.login.model.LoginResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    LoginContract.Presenter loginPresenter;
    Context context;

    public LoginRepository(LoginContract.Presenter presenter,Context context){
        this.context=context;
        this.loginPresenter=presenter;
    }

    public void loginApiCall(JsonObject jsonObject, final ProgressDialog progressDialog){
        Call<LoginResponse> apiInterface= DebateApiInstance.getApiCallInstance().getLoginApiData(jsonObject);
        apiInterface.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200){
                    LoginResponse loginResponse=response.body();
                    if(loginResponse.getResponse().getStatus().equals("ok")){
                        SharedPreferences sharedPreferences=context.getSharedPreferences("users",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("user_id",loginResponse.getResponse().getUserId());
                        editor.putString("username",loginResponse.getResponse().getUsername());
                        editor.commit();
                        loginPresenter.getDataFromAPi("ok");
                        Toast.makeText(context,loginResponse.getResponse().getUserId()+"=>"+loginResponse.getResponse().getUsername(),Toast.LENGTH_LONG).show();
                    }else{
                        loginPresenter.getDataFromAPi(loginResponse.getResponse().getStatus());
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"api cant hit",Toast.LENGTH_LONG).show();
            }
        });
    }

    //api call for login with facebook
    public void loginWithFbApiCall(JsonObject jsonObject, final ProgressDialog progressDialog){
        Call<LoginResponse> apiInterface=DebateApiInstance.getApiCallInstance().getLoginWithFbApiData(jsonObject);
        apiInterface.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 200){
                    LoginResponse loginResponse=response.body();
                    if(loginResponse.getResponse().getStatus().equals("ok")){
                        SharedPreferences sharedPreferences=context.getSharedPreferences("users",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("user_id",loginResponse.getResponse().getUserId());
                        editor.putString("username",loginResponse.getResponse().getUsername());
                        editor.commit();
                        loginPresenter.getFbDataFromApi(loginResponse);
                    }else{
                        loginPresenter.getFbDataFromApi(loginResponse);
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
            }
        });
    }

}
