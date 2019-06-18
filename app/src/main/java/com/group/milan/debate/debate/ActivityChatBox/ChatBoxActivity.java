package com.group.milan.debate.debate.ActivityChatBox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatBoxActivity extends AppCompatActivity implements ChatBoxContract.Views {

    @BindView(R.id.activity_chat_box_debate_name)
    TextView title;

    @BindView(R.id.activity_chat_box_back_btn)
    ImageView backbtn;

    @BindView(R.id.activity_chat_box_send_msg_btn)
    ImageView sendBtn;

    @BindView(R.id.activity_chat_box_image)
    ImageView uploadImageBtn;

    @BindView(R.id.activity_chat_box_message_box)
    EditText messageBox;

    @BindView(R.id.activity_chat_box_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.activity_chat_box_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_to_refresh_chat_box_fragment)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.activity_cancle_image_upload)
    ImageView cancleUploadImage;

    @BindView(R.id.activity_chat_box_sen_msg_image)
    RoundedImageView sendMsgImage;

    @BindView(R.id.activity_chat_box_progress_layout)
    RelativeLayout imageUploadProgressbarLayout;

    @BindView(R.id.activity_chat_box_progress_text)
    TextView imageUploadProgressText;

    @BindView(R.id.activity_chat_box_down_arrow)
    RelativeLayout downArrorwBtn;

    private static final int PICK_PHOTO_FOR_MESSAGE=1;

    List<ChatBoxPojo> list;
    List listKey;

    ProgressDialog progressDialog;

    ChatBoxRecyclerViewAdapter adapter;
    String username,debateTeamId,userId,profilePicture,chatId;
    int firstVisibleItem;
    boolean loadingStatus=false;
    Uri uploadImageUrl=null;
    LinearLayoutManager layoutManager;

    //firebase instance
    DatabaseReference databaseReference,messagesReferences;

    //firebase storage ref
    StorageReference storageReference;
    StorageReference imageStorageRef;
    public static ChatBoxActivity instance=null;
    ChatBoxPresenter chatBoxPresenter;

    boolean messageClickScroll=false;

    //pojo
    ExitChatResponse exitChatResponse;

    public static ChatBoxActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        //firebase storage instance
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        instance=this;

        //butter knife
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences=getSharedPreferences("users",MODE_PRIVATE);
        username=sharedPreferences.getString("username",null);
        userId=sharedPreferences.getString("user_id",null);
        profilePicture=sharedPreferences.getString("profile_picture",null);

        chatBoxPresenter=new ChatBoxPresenter(getApplicationContext(),this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing................");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        list=new ArrayList<>();
        listKey=new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FIREBASE REFERENCES FOR THE DATABASE REFERENCES
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressBar.setVisibility(View.VISIBLE);

        //GET BUNDLE DATA
        chatId=getIntent().getStringExtra("debate_id");
        userId=getIntent().getStringExtra("user_id");
        debateTeamId=getIntent().getStringExtra("debate_team_id");

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chat_box_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);
        switch(menuItem.getItemId()){
            case R.id.chat_box_option_exit:
                sendDataToApi();
                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }

    //VIEWS OF AN ACTIVITY
    public void initViews(){

        title.setText("Debate Title");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        adapter=new ChatBoxRecyclerViewAdapter(getApplicationContext(),list,debateTeamId,storageReference);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        final long timeStamp=new Date().getTime();
        messagesReferences=databaseReference.child("chats").child(chatId);
        Query chatsQuery=messagesReferences.orderByChild("time").endAt(timeStamp).limitToLast(20);

        //loading the first values from firebase chats
        chatsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Log.i("milan_fire",data.getKey());
                    ChatBoxPojo chatBoxPojo=data.getValue(ChatBoxPojo.class);
                    list.add(chatBoxPojo);
                    listKey.add(data.getKey());
                    adapter.notifyDataSetChanged();
                }
                addListners();
                scrollToBottom();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
            }
        });

        // SEND MESSAGE
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String images="0";
                String message=messageBox.getText().toString().trim();

                if(!message.isEmpty() && uploadImageUrl == null){
                    //SEND THE MESSAGE IF THE IMAGE IS SELECTED
                    long timeStamp=new Date().getTime();
                    sendMessage(debateTeamId,images,message,false,timeStamp,userId,profilePicture,username);
                    messageBox.setText("");

                }else if(uploadImageUrl != null){

                    //SEND MESSAGE IF THE IMAGE IS SELECTED
                    final String imagePath="images/"+chatId+"/"+ UUID.randomUUID().toString();
                    long newTimeStamp = new Date().getTime();
                    String pushId=sendMessage(debateTeamId, imagePath, message, false, newTimeStamp, userId, profilePicture, username);

                    //CLEARE THE DATA AFTER THE MESSAGE HAS BEEN SEND
                    messageBox.setText("");
                    cancleUploadImage.setVisibility(View.GONE);
                    sendMsgImage.setVisibility(View.GONE);
                    imageUploadProgressbarLayout.setVisibility(View.VISIBLE);

                    //UPLOAD THE IMAGE IN FOREBASE STORAGE FOR MESSAGE
                    uploadImageTofirebaseStorage(message,imagePath,pushId);
                }
            }
        });

        //SWAPPER FOR LOADMORE MESSAGE
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstVisibleItem=layoutManager.findFirstVisibleItemPosition();
                swipeRefreshLayout.setRefreshing(false);
                if(firstVisibleItem == 0 && loadingStatus== false){
                    loadMoreMessage(list.get(0).getTime()-1);
                }
            }
        });

        //SELECT IMAGE FOR THE MESSAGE
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_MESSAGE);
            }
        });

        //CANCLE SELECTED IMAGE FOR THE MESSAGE
        cancleUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageUrl=null;
                cancleUploadImage.setVisibility(View.GONE);
                sendMsgImage.setVisibility(View.GONE);
            }
        });

        //scrolling for showing scroll down button icon
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy < 0){
                    //configuration for the scroll down button
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    if(lastVisibleItem < list.size()-3) {
                        downArrorwBtn.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //SCROLLDOWN ICON IN CHATBOX
        downArrorwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToBottom();
                downArrorwBtn.setVisibility(View.GONE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_PHOTO_FOR_MESSAGE && resultCode == RESULT_OK){
            if (data == null) {
                //Display an error
                return;
            }
            uploadImageUrl=data.getData();
            sendMsgImage.setImageURI(uploadImageUrl);

            sendMsgImage.setVisibility(View.VISIBLE);
            cancleUploadImage.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    }


    //upload image to firebase store for message
    public void uploadImageTofirebaseStorage(final String message, final String imagePath, final String pushId){
        imageStorageRef=storageReference.child(imagePath);

        if(uploadImageUrl != null) {
            imageStorageRef.putFile(uploadImageUrl)
                    //onsucess listner
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploadImageUrl=null;
                            messagesReferences=databaseReference.child("chats").child(chatId).child(pushId);
                            messagesReferences.child("images").setValue(imagePath);
                            adapter.notifyItemChanged(list.size()+1);
                            addChildChangeListners();
                           // Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_LONG).show();
                        }
                    })

                    //on failed listner
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "upload faield", Toast.LENGTH_LONG).show();
                        }
                    })

                    //ON PROGRESS UPDATE LISTNER
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                            imageUploadProgressText.setText(String.valueOf(progress));
                            if(progress >= 99){
                                imageUploadProgressbarLayout.setVisibility(View.GONE);
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(),"error image null",Toast.LENGTH_LONG).show();
        }

    }

    //load more pagination call
    public void loadMoreMessage(long timeStamp){
        messagesReferences=databaseReference.child("chats").child(chatId);
        Query query=messagesReferences.orderByChild("time").endAt(timeStamp).limitToLast(20);
        final List<ChatBoxPojo> reverseList=new ArrayList<>();
        final List reverseListKey=new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ChatBoxPojo chatBoxPojo=data.getValue(ChatBoxPojo.class);
                    reverseList.add(chatBoxPojo);
                    reverseListKey.add(data.getKey());
                }

                Collections.reverse(reverseList);
                Collections.reverse(reverseListKey);

                for(int i=0;i<reverseList.size();i++){
                    list.add(0,reverseList.get(i));
                    listKey.add(reverseListKey.get(i));
                }

                adapter.notifyItemRangeInserted(0,reverseList.size());
                recyclerView.scrollToPosition(reverseList.size()+2);
                loadingStatus=false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"inter connection erro",Toast.LENGTH_LONG).show();
            }

        });
    }

    //scroll to bottom
    public void scrollToBottom(){
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
        downArrorwBtn.setVisibility(View.GONE);
    }

    //send message
    public String sendMessage(String debate_team_id, String images, String message, boolean private_team_message, long time, String user_id, String user_profile_url, String username){
        messagesReferences=databaseReference.child("chats").child(chatId);
        ChatBoxPojo chatBoxPojo=new ChatBoxPojo(debate_team_id,images,message,private_team_message,time,user_id,profilePicture,username);
        String newPushKey = messagesReferences.push().getKey();
        messagesReferences.child(newPushKey).setValue(chatBoxPojo);
        messageClickScroll=true;
        return newPushKey;
    }

    //add child listner
    public void addListners(){
        messagesReferences=databaseReference.child("chats").child(chatId);
        long currentTime=new Date().getTime()+1;
        Query query=messagesReferences.orderByChild("time").startAt(currentTime);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("milan_fire_aa",dataSnapshot.getKey());
                ChatBoxPojo chatBoxPojo=dataSnapshot.getValue(ChatBoxPojo.class);
                list.add(chatBoxPojo);
                listKey.add(dataSnapshot.getKey());
                adapter.notifyItemChanged(list.size());

                //configuration for the scroll down button
                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                Log.i("milan_mil", String.valueOf(lastVisibleItem)+"->"+list.size());

                if(messageClickScroll == true){
                    scrollToBottom();
                    messageClickScroll=false;
                }else {
                    if (lastVisibleItem < list.size() - 2) {
                        downArrorwBtn.setVisibility(View.VISIBLE);
                    } else if (lastVisibleItem == list.size() - 1 || lastVisibleItem == list.size()) {
                        //SCROLLING tO BOTTOM
                        scrollToBottom();
                    }else{
                        scrollToBottom();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                int position=listKey.indexOf(dataSnapshot.getKey());
//                ChatBoxPojo chatBoxPojo=dataSnapshot.getValue(ChatBoxPojo.class);
//                list.remove(position);
//                list.add(position,chatBoxPojo);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addChildChangeListners(){
        messagesReferences=databaseReference.child("chats").child(chatId);
        long currentTime=new Date().getTime()+1;
        Query query=messagesReferences.orderByChild("time").startAt(currentTime);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.i("milan_fire_aa",dataSnapshot.getKey());
//                ChatBoxPojo chatBoxPojo=dataSnapshot.getValue(ChatBoxPojo.class);
//                list.add(chatBoxPojo);
//                listKey.add(dataSnapshot.getKey());
//                adapter.notifyItemChanged(list.size()+1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int position=listKey.indexOf(dataSnapshot.getKey());
                ChatBoxPojo chatBoxPojo=dataSnapshot.getValue(ChatBoxPojo.class);
                list.remove(position);
                list.add(position,chatBoxPojo);
               // adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();

                //SCROLLING TO THE BOTTTOM DEPENDING UPON THE CONDITION
                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                if(lastVisibleItem < list.size()-4){
                    downArrorwBtn.setVisibility(View.VISIBLE);
                }else if(lastVisibleItem == list.size()-1){
                    //sCROLLING tO BOTTOM
                    scrollToBottom();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //send data to api
    public void sendDataToApi(){
        progressDialog.show();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("debate_id",chatId);
        jsonObject.addProperty("user_id",userId);
        chatBoxPresenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayResponseData(ExitChatResponse exitChatResponse) {
        this.exitChatResponse=exitChatResponse;
        progressDialog.hide();
        progressDialog.dismiss();
        if(exitChatResponse.getResponse().getStatus().equals("ok")){
            finish();
        }else{
            Toast.makeText(ChatBoxActivity.this,"sorry",Toast.LENGTH_LONG).show();
        }
    }
}
