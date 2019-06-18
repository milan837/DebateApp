package com.group.milan.debate.debate.Retrofit;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAllCategory.model.AllCategoryPojo;
import com.group.milan.debate.debate.ActivityAuthentication.login.model.LoginResponse;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;
import com.group.milan.debate.debate.ActivityAuthentication.signup.model.SignUpResponse;
import com.group.milan.debate.debate.ActivityAuthentication.verification.model.VerificationResponse;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.CategoryBaseDebateListResponse;
import com.group.milan.debate.debate.ActivityChangePassword.pojo.ChangePasswordResponse;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.model.AddToSquardPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamAUserPojo;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.TeamUser;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.joinBtn.JoinButtonResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.model.selectTeam.SelectTeamResponse;
import com.group.milan.debate.debate.ActivityDebateProfile.team_b.model.TeamUserPojo;
import com.group.milan.debate.debate.ActivityHome.model.HomeActivityResponse;
import com.group.milan.debate.debate.ActivitySearch.model.SearchResponsePojo;
import com.group.milan.debate.debate.ActivityUpdateUserDetails.UsernameUpdate.model.UpdateUsernamePojo;
import com.group.milan.debate.debate.ActivityUserProfile.debate.model.UserProfilePojo;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.MySquardPojo;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.StatusPojo.RemoveUserPojo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DebateApiCall {

    //--------------------------------------- Login api call -------------------------------------//
    @POST("auth/login/email")
    Call<LoginResponse> getLoginApiData(@Body JsonObject jsonObject);

    //--------------------------------------- Login api call -------------------------------------//
    @POST("auth/login/fb")
    Call<LoginResponse> getLoginWithFbApiData(@Body JsonObject jsonObject);

    //-------------------------------------- Signup api call -------------------------------------//
    @POST("auth/signup/email")
    Call<SignUpResponse> getSignUpApiData(@Body JsonObject jsonObject);

    //-------------------------------------- Signup api call -------------------------------------//
    @POST("auth/verification/email")
    Call<VerificationResponse> getVerificationApiData(@Body JsonObject jsonObject);

    //-------------------------------------- change password -------------------------------------//
    @POST("auth/changepassword")
    Call<ChangePasswordResponse> getChangePasswordApi(@Body JsonObject jsonObject);

    //-------------------------------------- save info api call ----------------------------------//
    @Multipart
    @POST("auth/save_info")
    Call<SaveInfoResponse> getSaveInfoApiData(
            @Part("username") RequestBody username,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part image);

    //-------------------------------------- Home Activity ----------------------------------//
    @GET("home/main/{userId}")
    Call<HomeActivityResponse> getHomeApiData(@Path("userId") String userId);

    //-------------------------------------- Home Activity ----------------------------------//
    @POST("home/category/debate/list")
    Call<CategoryBaseDebateListResponse> getCategoryDebateListApiData(@Body JsonObject jsonObject);



    //************************************** Debate activity Activity ****************************//

    //-------------------------------------- debate first hit api---------------------------------//
    @POST("debate/profile")
    Call<TeamAUserPojo> getTeamAUsersData(@Body JsonObject jsonObject);

    //-------------------------------------- debate first hit api---------------------------------//
    @POST("debate/profile/team/list")
    Call<TeamUserPojo> getTeamBUserData(@Body JsonObject jsonObject);

    //----------------------------- debate add user to squard hit api-----------------------------//
    @POST("action/squard/add")
    Call<AddToSquardPojo> getAddToSquardBtnData(@Body JsonObject jsonObject);

    //------------------------------------- debate join btn api ----------------------------------//
    @POST("debate/join/button")
    Call<JoinButtonResponse> getJoinButtonData(@Body JsonObject jsonObject);

    //------------------------------------- debate select team api--------------------------------//
    @POST("debate/join/select/team")
    Call<SelectTeamResponse> getSelectTeamData(@Body JsonObject jsonObject);

    //------------------------------------- debate chat exit api----------------------------------//
    @POST("debate/chat/exit")
    Call<ExitChatResponse> getExitChatData(@Body JsonObject jsonObject);


    //************************************** User Profile Activity *******************************//

    //-------------------------------------- User Profile Activity -------------------------------//
    @POST("user/profile")
    Call<UserProfilePojo> getUserProfileData(@Body JsonObject jsonObject);

    //-------------------------------------- My squard Activity ----------------------------------//
    @POST("user/profile/my_squard")
    Call<MySquardPojo> getMySquardListData(@Body JsonObject jsonObject);

    //-------------------------------------- Search Activity -------------------------------------//
    @POST("search/auto")
    Call<SearchResponsePojo> getSearchResponseData(@Body JsonObject jsonObject);

    //--------------------------------------- category activity ----------------------------------//
    @POST("category/list")
    Call<AllCategoryPojo> getAllCategoryResponseData(@Body JsonObject jsonObject);

    //---------------------------------------remove user form squard -----------------------------//
    @POST("squard/remove/user")
    Call<RemoveUserPojo> getRemoveUserFromSquardData(@Body JsonObject jsonObject);

    //--------------------------------------- update users ---------------------------------------//
    @POST("auth/update/username")
    Call<UpdateUsernamePojo> getUpdateUsernameData(@Body JsonObject jsonObject);

}
