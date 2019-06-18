package com.group.milan.debate.debate.ActivityUserProfile.debate;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserProfilePojo;

public class DebatePresenter implements DebateContract.Presenter {
    DebateContract.Views views;
    Context context;
    DebateRepository debateRepository;

    public DebatePresenter(DebateContract.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.debateRepository=new DebateRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        debateRepository.hitUserProfileApi(jsonObject);
    }

    @Override
    public void getDataFromApi(UserProfilePojo userProfilePojo) {
        views.displayResponse(userProfilePojo);
    }
}
