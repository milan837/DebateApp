package com.group.milan.debate.debate.ActivityHome;

import com.group.milan.debate.debate.ActivityHome.model.HomeActivityResponse;

public interface HomeContract {
    interface Views{
        void displayApiResponse(HomeActivityResponse homeActivityResponse);
    }
    interface Presenter{
        void sendDataToApi(String id);
        void getDataFromApi(HomeActivityResponse homeActivityResponse);
    }
}
