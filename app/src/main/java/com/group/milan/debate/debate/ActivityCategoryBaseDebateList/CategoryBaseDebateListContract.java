package com.group.milan.debate.debate.ActivityCategoryBaseDebateList;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.CategoryBaseDebateListResponse;

public interface CategoryBaseDebateListContract {
    interface Views{
        void displayApiResponse(CategoryBaseDebateListResponse categoryBaseDebateListResponse);
    }
    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(CategoryBaseDebateListResponse categoryBaseDebateListResponse);
    }
}
