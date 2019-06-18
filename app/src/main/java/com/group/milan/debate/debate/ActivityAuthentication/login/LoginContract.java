package com.group.milan.debate.debate.ActivityAuthentication.login;

import android.app.ProgressDialog;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.login.model.LoginResponse;

public interface LoginContract {
    interface Views{
        void displayResponseData(String response);
        void displayFbResponseData(LoginResponse loginResponse);
    }

    interface Presenter{
        void sendDataToApi(JsonObject jsonObject, ProgressDialog progressDialog);
        void getDataFromAPi(String data);
        void sendFbDataToApi(JsonObject jsonObject,ProgressDialog progressDialog);
        void getFbDataFromApi(LoginResponse loginResponse);
    }
}
