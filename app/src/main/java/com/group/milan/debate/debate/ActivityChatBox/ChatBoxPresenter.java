package com.group.milan.debate.debate.ActivityChatBox;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;

public class ChatBoxPresenter implements ChatBoxContract.Presenter {

    Context context;
    ChatBoxContract.Views views;
    ChatBoxRepository repository;

    public ChatBoxPresenter(Context context, ChatBoxContract.Views views) {
        this.context = context;
        this.views = views;
        this.repository=new ChatBoxRepository(context,this);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitExitChatApi(jsonObject);
    }

    @Override
    public void getDataFromApi(ExitChatResponse exitChatResponse) {
        views.displayResponseData(exitChatResponse);
    }
}
