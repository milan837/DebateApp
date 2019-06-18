package com.group.milan.debate.debate.ActivitySearch;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivitySearch.model.SearchResponsePojo;

public class SearchPresenter implements SearchContract.Presenter {
    SearchContract.Views views;
    Context context;
    SeachRepository repository;

    public SearchPresenter(SearchContract.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.repository=new SeachRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitSearchApi(jsonObject);
    }

    @Override
    public void getDataToApi(SearchResponsePojo searchResponsePojo) {
        views.displayResponseData(searchResponsePojo);
    }
}
