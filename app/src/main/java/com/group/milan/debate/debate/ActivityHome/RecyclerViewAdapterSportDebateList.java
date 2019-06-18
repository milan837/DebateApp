package com.group.milan.debate.debate.ActivityHome;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.group.milan.debate.debate.ActivityDebateProfile.DebateProfileActivity;
import com.group.milan.debate.debate.ActivityHome.model.Sport;
import com.group.milan.debate.debate.R;
import com.group.milan.debate.debate.Utils.UtilsClass;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterSportDebateList extends RecyclerView.Adapter<RecyclerViewAdapterSportDebateList.MyviewHolder> {
    Context context;
    List<Sport> list;

    public RecyclerViewAdapterSportDebateList(Context context, List<Sport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.adapter_recyclerview_debate_item,viewGroup,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, final int i) {
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.newInstance(), DebateProfileActivity.class);
                intent.putExtra("debateId",list.get(i).getId());
                HomeActivity.newInstance().startActivity(intent);
                HomeActivity.newInstance().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });

        myviewHolder.title.setText(list.get(i).getTitle());
        myviewHolder.time.setText(UtilsClass.getConvertTime(Long.valueOf(list.get(i).getTime())));

        myviewHolder.teamAName.setText(list.get(i).getTeamAName());
        myviewHolder.teamAScore.setText(list.get(i).getTeamAScore());

        myviewHolder.teamBName.setText(list.get(i).getTeamBName());
        myviewHolder.teamBScore.setText(list.get(i).getTeamBScore());

        Glide.with(context).load(list.get(i).getTeamAImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myviewHolder.teamAImage);

        Glide.with(context).load(list.get(i).getTeamBImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myviewHolder.teamBImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_debate_item_team_a_profile_pic)
        CircularImageView teamAImage;

        @BindView(R.id.adapter_debate_item_team_b_profile_pic)
        CircularImageView teamBImage;

        @BindView(R.id.adapter_debate_item_title)
        TextView title;

        @BindView(R.id.adapter_debate_item_team_a_score)
        TextView teamAScore;

        @BindView(R.id.adapter_debate_item_team_b_score)
        TextView teamBScore;

        @BindView(R.id.adapter_debate_item_time)
        TextView time;

        @BindView(R.id.adapter_debate_item_team_a_name)
        TextView teamAName;

        @BindView(R.id.adapter_debate_item_team_b_name)
        TextView teamBName;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}