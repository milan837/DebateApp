package com.group.milan.debate.debate.ActivityAuthentication.save_info;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SaveInfoPresenter implements SaveInfoContract.Presenter {

    SaveInfoContract.Views saveInfoView;
    SaveInfoRepository saveInfoRepository;

    public SaveInfoPresenter(SaveInfoContract.Views views,Context context){
        this.saveInfoView=views;
        saveInfoRepository=new SaveInfoRepository(this,context);
    }

    @Override
    public void sendDatToApi(RequestBody userId, RequestBody username, MultipartBody.Part image,ProgressDialog progressDialog) {
        saveInfoRepository.hitSaveInfoApi(userId,username,image,progressDialog);
    }

    @Override
    public void getDataFromApi(SaveInfoResponse saveInfoResponse) {
        saveInfoView.displayApiResponse(saveInfoResponse);
    }
}
