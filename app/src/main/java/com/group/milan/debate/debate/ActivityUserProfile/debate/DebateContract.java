package com.group.milan.debate.debate.ActivityUserProfile.debate;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserProfilePojo;

public interface DebateContract {
    interface Views{
        void displayResponse(UserProfilePojo userProfilePojo);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(UserProfilePojo userProfilePojo);
    }
}
