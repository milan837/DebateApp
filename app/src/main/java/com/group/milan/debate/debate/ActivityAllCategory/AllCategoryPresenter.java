package com.group.milan.debate.debate.ActivityAllCategory;

import android.content.Context;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAllCategory.model.AllCategoryPojo;

public class AllCategoryPresenter implements AllCategoryContract.Presenter{
    AllCategoryContract.Views views;
    Context context;
    AllCategoryRepository repository;

    public AllCategoryPresenter(AllCategoryContract.Views views, Context context) {
        this.views = views;
        this.context = context;
        this.repository=new AllCategoryRepository(this,context);
    }

    @Override
    public void sendDataToApi(JsonObject jsonObject) {
        repository.hitApi(jsonObject);
    }

    @Override
    public void getDataFromApi(AllCategoryPojo allCategoryPojo) {
        views.displayResponse(allCategoryPojo);
    }
}
