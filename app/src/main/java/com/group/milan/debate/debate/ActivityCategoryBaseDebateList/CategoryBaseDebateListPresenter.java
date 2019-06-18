package com.group.milan.debate.debate.ActivityCategoryBaseDebateList;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.CategoryBaseDebateListResponse;

public class CategoryBaseDebateListPresenter implements CategoryBaseDebateListContract.Presenter {
    CategoryBaseDebateListContract.Views views;
    CategoryBaseDebateListRepository repository;
    Context context;

    public CategoryBaseDebateListPresenter(CategoryBaseDebateListContract.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.repository=new CategoryBaseDebateListRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitCategoryBaseApi(jsonObject);
    }

    @Override
    public void getDataFromApi(CategoryBaseDebateListResponse categoryBaseDebateListResponse) {
        views.displayApiResponse(categoryBaseDebateListResponse);
    }
}
