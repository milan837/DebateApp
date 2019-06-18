package com.group.milan.debate.debate.ActivityDebateProfile.team_b;


import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;

public interface TeamBContact {

    interface Views{
        void displayResponseData(TeamUserPojo teamUserPojo);
    }

    interface  Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(TeamUserPojo teamUserPojo);
    }
}
