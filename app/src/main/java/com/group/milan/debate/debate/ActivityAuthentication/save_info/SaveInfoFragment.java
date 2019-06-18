package com.group.milan.debate.debate.ActivityAuthentication.save_info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.group.milan.debate.debate.ActivityAuthentication.AuthenticationActivity;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;
import com.group.milan.debate.debate.ActivityHome.HomeActivity;
import com.group.milan.debate.debate.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SaveInfoFragment extends Fragment implements SaveInfoContract.Views {

    SaveInfoPresenter saveInfoPresenter;
    @BindView(R.id.authentication_save_info_profile_picture)
    CircularImageView circularImageView;

    @BindView(R.id.authentication_save_info_edittext_username)
    EditText usernameEditText;

    @BindView(R.id.authentication_save_info_button)
    Button saveBtn;

    @BindView(R.id.activity_update_back_button)
    ImageView backButton;

    Uri image=null;
    String userId,saveProfilePicture,saveUsername;
    InputStream inputStream=null;

    private static final int PICK_PHOTO_FOR_AVATAR=1;
    ProgressDialog progressDialog;

    public static SaveInfoFragment newInstance(){
        return new SaveInfoFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication_save_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        //setting progress dailog
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        saveInfoPresenter=new SaveInfoPresenter(this,getActivity());
        userId=getArguments().getString("user_id");

        if(userId != null){
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
            saveProfilePicture=sharedPreferences.getString("profile_picture",null);
            saveUsername=sharedPreferences.getString("username",null);
        }

        initViews();
    }

    private void initViews(){

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsername=usernameEditText.getText().toString().trim();
                if(getUsername.isEmpty()){
                    Toast.makeText(getActivity(),"please enter username",Toast.LENGTH_LONG).show();
                }else if(image == null){
                    Toast.makeText(getActivity(),"please select image",Toast.LENGTH_LONG).show();
                }else{
                    progressDialog.show();
                    try {
                        sendDataToApi(getBytes(inputStream),userId,getUsername);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                image=data.getData();
                circularImageView.setImageURI(image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public void sendDataToApi(byte[] imageBytes,String userId,String username){

        RequestBody usernameRequset=RequestBody.create(MultipartBody.FORM,username);
        RequestBody userIdRequest=RequestBody.create(MultipartBody.FORM,userId);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);

        saveInfoPresenter.sendDatToApi(userIdRequest,usernameRequset,body,progressDialog);
    }

    @Override
    public void displayApiResponse(SaveInfoResponse saveInfoResponse) {
        progressDialog.dismiss();
        if(saveInfoResponse.getResponse().getStatus().equals("ok")){
            Intent intent=new Intent(AuthenticationActivity.newInstance(), HomeActivity.class);
            AuthenticationActivity.newInstance().startActivity(intent);
            AuthenticationActivity.newInstance().overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_left);
            AuthenticationActivity.newInstance().finish();
        }else{
            Toast.makeText(AuthenticationActivity.newInstance(),saveInfoResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }
    }
}
