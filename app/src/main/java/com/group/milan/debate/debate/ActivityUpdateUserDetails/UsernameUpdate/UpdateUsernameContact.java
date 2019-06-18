package com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.model.UpdateUsernamePojo;

public interface UpdateUsernameContact {
    interface Views{
        void displayResponseData(UpdateUsernamePojo updateUsernamePojo);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(UpdateUsernamePojo updateUsernamePojo);
    }
}
