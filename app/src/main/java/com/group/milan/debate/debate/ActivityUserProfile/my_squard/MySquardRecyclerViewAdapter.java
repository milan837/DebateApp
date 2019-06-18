package com.group.milan.debate.debate.ActivityUserProfile.my_squard;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityChatBox.pojo.ExitChatResponse;
import com.group.milan.debate.debate.ActivityUserProfile.my_squard.model.Response;
import com.group.milan.debate.debate.R;
import com.group.milan.debate.debate.Retrofit.DebateApiInstance;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MySquardRecyclerViewAdapter extends RecyclerView.Adapter<MySquardRecyclerViewAdapter.MyviewHolder> {

    Context context;
    List<Response> list;
    SharedPreferences sharedPreferences;
    String userId;

    public MySquardRecyclerViewAdapter(Context context, List<Response> list) {
        this.context = context;
        this.list = list;
        this.sharedPreferences=context.getSharedPreferences("users",Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("user_id",null);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_profile_friend_layout, viewGroup, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder myviewHolder, final int i) {

        myviewHolder.username.setText(list.get(i).getUsername());
        Glide.with(context).load(list.get(i).getProfilePicture())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myviewHolder.image);

        myviewHolder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("user_id",userId);
                jsonObject.addProperty("squard_user_id",list.get(i).getUserId());

                Log.i("milan_shrestha",userId+"=>"+list.get(i).getUserId());
                Toast.makeText(context, userId+"=>"+list.get(i).getUserId(), Toast.LENGTH_SHORT).show();

                MySquardRepository mySquardRepository=new MySquardRepository(context);
                mySquardRepository.hitRemoveApi(jsonObject);
                list.remove(i);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_profile_friend_profile_picture)
        CircularImageView image;

        @BindView(R.id.adapter_profile_friend_username)
        TextView username;

        @BindView(R.id.adapter_profile_friend_remove_btn)
        TextView removeBtn;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
