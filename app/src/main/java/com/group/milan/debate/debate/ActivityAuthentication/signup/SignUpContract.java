package com.group.milan.debate.debate.ActivityAuthentication.signup;

import android.app.ProgressDialog;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.signup.model.SignUpResponse;

public interface SignUpContract {
    interface Views{
        void displayResponse(SignUpResponse signUpResponse);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject,ProgressDialog progressDialog);
        void getDataFromApi(SignUpResponse signUpResponse);
    }
}
