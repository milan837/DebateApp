package com.group.milan.debate.debate.ActivityChangePassword;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChangePassword.pojo.ChangePasswordResponse;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter{
    Context context;
    ChangePasswordContract.Views views;
    ChangePasswordRepository repository;

    public ChangePasswordPresenter(Context context, ChangePasswordContract.Views views) {
        this.context = context;
        this.views = views;
        this.repository=new ChangePasswordRepository(context,this);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitChangePasswordApi(jsonObject);
    }

    @Override
    public void getDataFromApi(ChangePasswordResponse changePasswordResponse) {
        views.displayResponseData(changePasswordResponse);
    }
}
