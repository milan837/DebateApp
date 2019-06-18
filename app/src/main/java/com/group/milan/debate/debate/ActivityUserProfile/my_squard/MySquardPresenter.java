package com.group.milan.debate.debate.ActivityUserProfile.my_squard;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.MySquardPojo;

public class MySquardPresenter implements MySquardContract.Presenter {
    Context context;
    MySquardContract.Views views;
    MySquardRepository repository;

    public MySquardPresenter(MySquardContract.Views views,Context context) {
        this.context = context;
        this.views = views;
        this.repository=new MySquardRepository(this,context);
    }


    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitMySquardApi(jsonObject);
    }

    @Override
    public void getDataFromApi(MySquardPojo mySquardPojo) {
        views.displayResponseData(mySquardPojo);
    }

}
