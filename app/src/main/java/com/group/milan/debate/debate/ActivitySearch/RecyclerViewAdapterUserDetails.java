package com.group.milan.debate.debate.ActivitySearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityDebateProfile.team_a.TeamARepository;
import com.group.milan.debate.debate.ActivitySearch.model.Response;
import com.group.milan.debate.debate.ActivityUserProfile.UserProfileActivity;
import com.group.milan.debate.debate.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterUserDetails extends RecyclerView.Adapter<RecyclerViewAdapterUserDetails.MyviewHolder> {

    Context context;
    List<Response> list;

    public RecyclerViewAdapterUserDetails(Context context, List<Response> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.adapter_recyclerview_user_details,viewGroup,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder myviewHolder, final int i) {
        myviewHolder.itemView.setTag("off");
        myviewHolder.username.setText(list.get(i).getUsername());
        myviewHolder.levels.setText(list.get(i).getLevels());
        myviewHolder.badges.setText(list.get(i).getBadges());
        myviewHolder.totalDebate.setText(list.get(i).getTotalDebates());

        Glide.with(context).load(list.get(i).getProfilePicture())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myviewHolder.image);

        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.newInstance(), UserProfileActivity.class);
                intent.putExtra("user_id",list.get(i).getUserId());
                SearchActivity.newInstance().startActivity(intent);
                SearchActivity.newInstance().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_right);
            }
        });

        if (list.get(i).getStatus()=="0") {
            myviewHolder.button.setText("Add to squard");
            myviewHolder.button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.login_button));
        } else {
            myviewHolder.button.setText("Added");
            myviewHolder.button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.clicked_button));
        }

        myviewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).getStatus()=="0") {
                    myviewHolder.button.setText("Added");
                    myviewHolder.button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.clicked_button));
                    list.get(i).setStatus("1");
                    notifyDataSetChanged();
                } else {
                    myviewHolder.button.setText("Add to squard");
                    myviewHolder.button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.login_button));
                    list.get(i).setStatus("0");
                    notifyDataSetChanged();
                }

                SharedPreferences sharedPreferences=context.getSharedPreferences("users", Context.MODE_PRIVATE);
                String userId=sharedPreferences.getString("user_id",null);
                Log.i("milan_shrestha",userId+" = "+list.get(i).getUserId());

                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("user_id",userId);
                jsonObject.addProperty("squard_user_id",list.get(i).getUserId());

                SeachRepository teamARepository=new SeachRepository(context);
                teamARepository.hitAddToSquardApi(jsonObject);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_recyclerview_user_details_profile_picture)
        CircularImageView image;

        @BindView(R.id.adapter_recyclerview_user_details_username)
        TextView username;

        @BindView(R.id.adapter_recyclerview_user_details_total_debate)
        TextView totalDebate;

        @BindView(R.id.adapter_recyclerview_user_details_badges)
        TextView badges;

        @BindView(R.id.adapter_recyclerview_user_details_levels)
        TextView levels;

        @BindView(R.id.adapter_recyclerview_user_details_button_add)
        Button button;

        @BindView(R.id.adapter_recyclerview_user_details_button_status)
        TextView buttonStatus;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}