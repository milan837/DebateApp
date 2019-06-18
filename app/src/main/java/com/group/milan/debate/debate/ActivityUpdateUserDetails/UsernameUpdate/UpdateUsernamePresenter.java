package com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.model.UpdateUsernamePojo;

public class UpdateUsernamePresenter implements UpdateUsernameContact.Presenter {
    UpdateUsernameContact.Views views;
    Context context;
    UpdateUsernameRepository repository;

    public UpdateUsernamePresenter(UpdateUsernameContact.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.repository=new UpdateUsernameRepository(context,this);
    }


    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitUpdateUsernameApi(jsonObject);
    }

    @Override
    public void getDataFromApi(UpdateUsernamePojo updateUsernamePojo) {
        views.displayResponseData(updateUsernamePojo);
    }
}
