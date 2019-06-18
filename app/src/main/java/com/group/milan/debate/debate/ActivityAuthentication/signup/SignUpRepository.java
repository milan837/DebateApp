package com.group.milan.debate.debate.ActivityAuthentication.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.signup.model.SignUpResponse;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    SignUpContract.Presenter signUpPresenter;
    Context context;

    public SignUpRepository(SignUpContract.Presenter presenter,Context context){
        this.context=context;
        this.signUpPresenter=presenter;
    }

    public void signUpApi(JsonObject jsonObject, final ProgressDialog progressDialog){
        Call<SignUpResponse> apiInterface= DebateApiInstance.getApiCallInstance().getSignUpApiData(jsonObject);
        apiInterface.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.code() == 200){
                    SignUpResponse signUpResponse=response.body();
                    signUpPresenter.getDataFromApi(signUpResponse);
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context,"error code",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"failed to hit api",Toast.LENGTH_LONG).show();
            }
        });
    }
}
