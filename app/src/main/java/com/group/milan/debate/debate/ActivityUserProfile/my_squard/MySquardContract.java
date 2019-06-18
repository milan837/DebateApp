package com.group.milan.debate.debate.ActivityUserProfile.my_squard;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.MySquardPojo;

public interface MySquardContract {

    interface Views{
        void displayResponseData(MySquardPojo mySquardPojo);
    }

    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(MySquardPojo mySquardPojo);

    }

}
