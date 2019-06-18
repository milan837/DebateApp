package com.group.milan.debate.debate.ActivityChatBox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group.milan.debate.debate.ActivityChatBoxImageView.ChatBoxImageViewActivity;
import com.group.milan.debate.debate.R;
import com.group.milan.debate.debate.Utils.UtilsClass;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatBoxRecyclerViewAdapter extends RecyclerView.Adapter<ChatBoxRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<ChatBoxPojo> list;
    String teamAId,dateTest="0";
    StorageReference storageReference;
    String imageUrl=null;

    public ChatBoxRecyclerViewAdapter(Context context, List<ChatBoxPojo> list,String teamAId,StorageReference storageReference) {
        this.context = context;
        this.list = list;
        this.teamAId=teamAId;
        this.storageReference=storageReference;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.adapter_recyclerview_chat_box_public,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        String[] getFirstString=list.get(i).getUsername().split(" ");
        String username=getFirstString[0];
        String setUsername=username.substring(0,1).toUpperCase()+username.substring(1).toLowerCase();

        myViewHolder.message.setText(list.get(i).getMessage());
        myViewHolder.time.setText(UtilsClass.getConvertTime(list.get(i).getTime()));

        //FOR THE IMAGES UPLOAD
        if(list.get(i).getDebate_team_id().equals(teamAId)){
            myViewHolder.roundedImageView.setVisibility(View.VISIBLE);
            myViewHolder.circularImageView.setVisibility(View.GONE);
            myViewHolder.username.setText("#"+setUsername);
            Glide.with(context).load(list.get(i).getUser_profile_url())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myViewHolder.roundedImageView);
        }else{
            myViewHolder.circularImageView.setVisibility(View.VISIBLE);
            myViewHolder.roundedImageView.setVisibility(View.GONE);
            myViewHolder.username.setText("*"+setUsername);
            Glide.with(context).load(list.get(i).getUser_profile_url())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myViewHolder.circularImageView);
        }

        //upload images show div
        if(list.get(i).getImages().equals("0")){
            myViewHolder.uploadImage.setVisibility(View.GONE);
        }else {
            myViewHolder.uploadImage.setVisibility(View.VISIBLE);
            storageReference.child(list.get(i).getImages())
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("download_uri",uri.toString());

                    Glide.with(context)
                            .load(uri.toString())
                            .fitCenter()
                            .into(myViewHolder.uploadImage);

                }
            });

            myViewHolder.uploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ChatBoxImageViewActivity.class);
                    intent.putExtra("image_url",list.get(i).getImages());
                    intent.putExtra("status"," ");
                    ChatBoxActivity.getInstance().startActivity(intent);
                    ChatBoxActivity.getInstance().overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
                }
            });
        }

        Glide.clear(myViewHolder.uploadImage);

        //DATE AND TIME FOR MESSAGE
        //MESSAGE SENT DATE CONVERTING
        String getMsgDate=UtilsClass.getConvertDate(list.get(i).getTime());
        String[] msgDateArray=getMsgDate.split("-");
        String msgYear=msgDateArray[0].trim();
        String msgMonth=msgDateArray[1].trim();
        String msgDate=msgDateArray[2].trim();

        //CURRENT TIME CONVERTING
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] currentDateArray= currentDateString.split("-");

        String currentMonth=currentDateArray[1];
        String currentDate=currentDateArray[2];
        String currentYear=currentDateArray[0];

        Log.i("milan_date",dateTest+"=>"+msgDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_recyclerview_profile_picture_rect)
        RoundedImageView roundedImageView;

        @BindView(R.id.adapter_recyclerview_profile_picture_upload)
        RoundedImageView uploadImage;

        @BindView(R.id.adapter_recyclerview_profile_picture_oval)
        CircularImageView circularImageView;

        @BindView(R.id.adapter_chat_box_username)
        TextView username;

        @BindView(R.id.adapter_chat_box_msg)
        TextView message;

        @BindView(R.id.adapter_chat_box_time)
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
