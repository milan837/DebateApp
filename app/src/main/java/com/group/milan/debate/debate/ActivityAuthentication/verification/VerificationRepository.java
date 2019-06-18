package com.group.milan.debate.debate.ActivityAuthentication.verification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.verification.model.VerificationResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationRepository {

    VerificationContract.Presenter verificationPresenter;

    Context context;
    public VerificationRepository(VerificationContract.Presenter presenter,Context context){
        this.context=context;
        this.verificationPresenter=presenter;
    }

    public void verificationApiCall(final JsonObject jsonObject, final ProgressDialog progressDialog){
        Call<VerificationResponse> apiInterface= DebateApiInstance.getApiCallInstance().getVerificationApiData(jsonObject);
        apiInterface.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if(response.code() == 200){
                    VerificationResponse verificationResponse=response.body();
                    verificationPresenter.getDataFromApi(verificationResponse);
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"faield to hit api",Toast.LENGTH_LONG).show();
            }
        });
    }
}
