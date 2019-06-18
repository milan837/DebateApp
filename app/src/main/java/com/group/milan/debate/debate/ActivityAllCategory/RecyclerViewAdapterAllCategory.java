package com.group.milan.debate.debate.ActivityAllCategory;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group.milan.debate.debate.ActivityAllCategory.model.Response;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.CategoryBaseDebateListActivity;
import com.group.milan.debate.debate.ActivityMenu.DrawerMenuActivity;
import com.group.milan.debate.debate.ActivitySubCategory.SubCategoryActivity;
import com.group.milan.debate.debate.ActivityUserProfile.UserProfileActivity;
import com.group.milan.debate.debate.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterAllCategory extends RecyclerView.Adapter<RecyclerViewAdapterAllCategory.MyviewHolder> {
    Context context;
    List<Response> list;

    public RecyclerViewAdapterAllCategory(Context context, List<Response> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_recyclerview_all_category, viewGroup, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, final int i) {
        myviewHolder.title.setText(list.get(i).getName());
        myviewHolder.debateNo.setText(list.get(i).getTotalDebate());
        myviewHolder.hashTages.setText(list.get(i).getImage());

        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllCategoryActivity.newInstance(), CategoryBaseDebateListActivity.class);
                intent.putExtra("keyword",list.get(i).getName());
                AllCategoryActivity.newInstance().startActivity(intent);
                AllCategoryActivity.newInstance().overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_recyclerview_all_category_title)
        TextView title;

        @BindView(R.id.adapter_recyclerview_all_category_hasttag)
        TextView hashTages;

        @BindView(R.id.adapter_recyclerview_all_category_active_debate)
        TextView debateNo;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}