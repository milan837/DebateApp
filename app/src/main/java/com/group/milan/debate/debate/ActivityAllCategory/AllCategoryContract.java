package com.group.milan.debate.debate.ActivityAllCategory;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAllCategory.model.AllCategoryPojo;

public interface AllCategoryContract {
    interface Views{
        void displayResponse(AllCategoryPojo allCategoryPojo);
    }

    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataFromApi(AllCategoryPojo allCategoryPojo);
    }
}
