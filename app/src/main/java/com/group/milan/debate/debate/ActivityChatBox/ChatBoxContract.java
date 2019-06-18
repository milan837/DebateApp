package com.group.milan.debate.debate.ActivityChatBox;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;

public interface ChatBoxContract {
    interface Views{
        void displayResponseData(ExitChatResponse exitChatResponse);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(ExitChatResponse exitChatResponse);
    }
}
