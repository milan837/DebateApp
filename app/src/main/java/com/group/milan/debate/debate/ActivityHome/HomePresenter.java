package com.group.milan.debate.debate.ActivityHome;

import android.content.Context;

import com.group.milan.debate.debate.ActivityHome.model.HomeActivityResponse;

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.Views homeView;
    HomeRepository homeRepository;

    public HomePresenter(HomeContract.Views homeView,Context context) {
        this.homeView = homeView;
        this.homeRepository = new HomeRepository(this,context);
    }

    @Override
    public void sendDataToApi(String id) {
        homeRepository.hitDebateApiList(id);
    }

    @Override
    public void getDataFromApi(HomeActivityResponse homeActivityResponse) {
        homeView.displayApiResponse(homeActivityResponse);
    }
}
