package com.group.milan.debate.debate.ActivityChatBoxImageView;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatBoxImageViewActivity extends AppCompatActivity {

    @BindView(R.id.activity_chat_box_image_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.activity_chat_box_image_image_view)
    ImageView imageView;

    @BindView(R.id.activity_chat_image_view_close_button)
    ImageView backBtn;

    String imageUri=null,status;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chat_box_image_view);
        ButterKnife.bind(this);

        //firebase storage instance
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageUri=getIntent().getStringExtra("image_url");
        status=getIntent().getStringExtra("status");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        if(status.equals("server")){

            //loading the image from the server
            Glide.with(this).load(imageUri)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            progressBar.setVisibility(View.GONE);

        }else {
            //loading the image from firebase
            storageReference.child(imageUri)
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.i("download_uri", uri.toString());

                            //IMAGE LOADING
                            Glide.with(getApplicationContext())
                                    .load(uri.toString())
                                    .placeholder(R.drawable.blank_image_drawable)
                                    .fitCenter()
                                    .into(imageView);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    }
}
