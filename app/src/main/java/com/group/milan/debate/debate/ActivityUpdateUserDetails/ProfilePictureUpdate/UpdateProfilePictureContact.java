package com.group.milan.debate.debate.ActivityUpdateUserDetails.ProfilePictureUpdate;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UpdateProfilePictureContact {
    interface Views{
        void displayResponseData(SaveInfoResponse saveInfoResponse);
    }

    interface Presenter{
        void sendDataToApi(RequestBody userId, RequestBody username, MultipartBody.Part image, ProgressDialog progressDialog);
        void getDataFromApi(SaveInfoResponse saveInfoResponse);
    }
}
