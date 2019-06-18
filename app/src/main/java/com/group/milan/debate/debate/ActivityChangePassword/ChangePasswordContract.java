package com.group.milan.debate.debate.ActivityChangePassword;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChangePassword.pojo.ChangePasswordResponse;

public interface ChangePasswordContract {

    interface Views{
        void displayResponseData(ChangePasswordResponse changePasswordResponse);
    }

    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(ChangePasswordResponse changePasswordResponse);
    }
}
