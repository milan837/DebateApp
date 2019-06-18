package com.group.milan.debate.debate.ActivityAuthentication.signup;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.signup.model.SignUpResponse;

public class SignUpPresenter implements SignUpContract.Presenter {

    SignUpRepository signUpRepository;
    SignUpContract.Views signUpViews;


    public SignUpPresenter(SignUpContract.Views views,Context context){
        this.signUpViews=views;
        signUpRepository=new SignUpRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject,ProgressDialog progressDialog) {
        signUpRepository.signUpApi(jsonObject,progressDialog);
    }

    @Override
    public void getDataFromApi(SignUpResponse signUpResponse) {
        signUpViews.displayResponse(signUpResponse);
    }
}
