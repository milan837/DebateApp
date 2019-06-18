package com.group.milan.debate.debate.ActivityUpdateUserDetails.ProfilePictureUpdate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.group.milan.debate.debate.ActivityAuthentication.save_info.model.SaveInfoResponse;
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

import static android.content.Context.MODE_PRIVATE;

public class UpdateProfilePictureFragment extends Fragment implements UpdateProfilePictureContact.Views {

    @BindView(R.id.fragment_update_back_button)
    ImageView backButton;

    @BindView(R.id.fragment_update_profile_picture)
    CircularImageView uploadImageView;

    @BindView(R.id.fragment_upload_profile_picture_button)
    Button uploadButton;

    UpdateProfilePicturePresenter presenter;
    ProgressDialog progressDialog;


    Uri image=null;
    String userId,saveProfilePicture,saveUsername,username;
    InputStream inputStream=null;

    private static final int PICK_PHOTO_FOR_AVATAR=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile_picture_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users", MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);
        username=sharedPreferences.getString("username",null);
        saveProfilePicture=sharedPreferences.getString("profile_picture",null);

        this.presenter=new UpdateProfilePicturePresenter(getActivity(),this);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        initViews();

    }

    private void initViews(){

        Glide.with(getActivity()).load(saveProfilePicture)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.blank_image_drawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(uploadImageView);

        //select image to upload
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            }
        });

        //upload an image
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image == null){
                    Toast.makeText(getActivity(),"image not selected",Toast.LENGTH_LONG).show();
                }else{
                    progressDialog.show();
                    try {
                        sendDataToApi(getBytes(inputStream),userId,username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                image=data.getData();
                uploadImageView.setImageURI(image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    public void sendDataToApi(byte[] imageBytes,String userId,String username){

        RequestBody usernameRequset=RequestBody.create(MultipartBody.FORM,username);
        RequestBody userIdRequest=RequestBody.create(MultipartBody.FORM,userId);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        presenter.sendDataToApi(userIdRequest,usernameRequset,body,progressDialog);

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


    @Override
    public void displayResponseData(SaveInfoResponse saveInfoResponse) {
        progressDialog.hide();
        progressDialog.dismiss();
        if(saveInfoResponse.getResponse().getStatus().equals("ok")){

            //saving data to shared preferences;
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("users",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("profile_picture",saveInfoResponse.getResponse().getImageUrl());
            editor.commit();

            Toast.makeText(getActivity(),"upload sucesssfull",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),saveInfoResponse.getResponse().getStatus(),Toast.LENGTH_LONG).show();
        }

    }
}
