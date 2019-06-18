package com.group.milan.debate.debate.ActivityAuthentication.verification;

import android.app.ProgressDialog;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.verification.model.VerificationResponse;

public interface VerificationContract {
    interface Views{
        void displayVerificationResponse(VerificationResponse verificationResponse);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject,ProgressDialog progressDialog);
        void getDataFromApi(VerificationResponse verificationResponse);
    }
}
