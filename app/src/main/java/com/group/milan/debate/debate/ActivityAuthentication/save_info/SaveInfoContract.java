package com.group.milan.debate.debate.ActivityAuthentication.save_info;

import android.app.ProgressDialog;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface SaveInfoContract {
    interface Views{
        void displayApiResponse(SaveInfoResponse saveInfoResponse);
    }
    interface Presenter{
        void sendDatToApi(RequestBody userId, RequestBody username, MultipartBody.Part image,ProgressDialog progressDialog);
        void getDataFromApi(SaveInfoResponse saveInfoResponse);
    }
}
