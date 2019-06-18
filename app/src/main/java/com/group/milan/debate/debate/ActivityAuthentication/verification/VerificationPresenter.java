package com.group.milan.debate.debate.ActivityAuthentication.verification;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.verification.model.VerificationResponse;

public class VerificationPresenter implements VerificationContract.Presenter{

    VerificationRepository verificationRepository;
    VerificationContract.Views verificationViews;

    Context context;
    public VerificationPresenter(VerificationContract.Views views,Context context){
        this.verificationViews=views;
        verificationRepository=new VerificationRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject,ProgressDialog progressDialog) {
        verificationRepository.verificationApiCall(jsonObject,progressDialog);
    }

    @Override
    public void getDataFromApi(VerificationResponse verificationResponse) {
        verificationViews.displayVerificationResponse(verificationResponse);
    }
}
