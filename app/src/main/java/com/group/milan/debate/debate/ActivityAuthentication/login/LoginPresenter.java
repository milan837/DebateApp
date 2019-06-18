package com.group.milan.debate.debate.ActivityAuthentication.login;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.login.model.LoginResponse;

public class LoginPresenter implements LoginContract.Presenter{

    LoginRepository loginRepository;
    LoginContract.Views loginView;

    public LoginPresenter(LoginContract.Views view,Context context){
       loginRepository=new LoginRepository(this,context);
       this.loginView=view;
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject, ProgressDialog progressDialog) {
        loginRepository.loginApiCall(jsonObject,progressDialog);
    }

    @Override
    public void getDataFromAPi(String data) {
        loginView.displayResponseData(data);
    }

    @Override
    public void sendFbDataToApi(JsonObject jsonObject, ProgressDialog progressDialog) {
        loginRepository.loginWithFbApiCall(jsonObject,progressDialog);
    }

    @Override
    public void getFbDataFromApi(LoginResponse loginResponse) {
        loginView.displayFbResponseData(loginResponse);
    }

}
