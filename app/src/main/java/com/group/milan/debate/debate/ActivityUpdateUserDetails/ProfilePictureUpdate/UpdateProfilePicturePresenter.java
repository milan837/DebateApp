package com.group.milan.debate.debate.ActivityUpdateUserDetails.ProfilePictureUpdate;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateProfilePicturePresenter implements UpdateProfilePictureContact.Presenter {
    Context context;
    UpdateProfilePictureContact.Views views;
    UpdateProfilePictureRepository repository;

    public UpdateProfilePicturePresenter(Context context, UpdateProfilePictureContact.Views views) {
        this.context = context;
        this.views = views;
        this.repository=new UpdateProfilePictureRepository(context,this);
    }

    @Override
    public void sendDataToApi(RequestBody userId, RequestBody username, MultipartBody.Part image, ProgressDialog progressDialog) {
        repository.hitUpdateProfilePicApi(userId,username,image,progressDialog);
    }

    @Override
    public void getDataFromApi(SaveInfoResponse saveInfoResponse) {
        views.displayResponseData(saveInfoResponse);
    }
}
