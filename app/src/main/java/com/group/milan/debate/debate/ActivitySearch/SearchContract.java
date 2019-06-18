package com.group.milan.debate.debate.ActivitySearch;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivitySearch.model.SearchResponsePojo;

public interface SearchContract {

    interface Views{
        void displayResponseData(SearchResponsePojo searchResponsePojo);
    }

    interface Presenter{
        void sendDataToApi(JsonObject jsonObject);
        void getDataToApi(SearchResponsePojo searchResponsePojo);
    }

}
