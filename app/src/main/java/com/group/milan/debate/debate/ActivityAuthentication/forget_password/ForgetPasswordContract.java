package com.group.milan.debate.debate.ActivityAuthentication.forget_password;

public interface ForgetPasswordContract {
    interface Views{
        void displayResponseData();
    }
    interface Presenter{
        void sendDataToApi();
        void getDataFromApi();
    }
}
