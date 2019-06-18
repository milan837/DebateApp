package com.group.milan.debate.debate.ActivitySubCategory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryActivity extends AppCompatActivity {

    @BindView(R.id.activity_sub_category_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.activity_sub_category_back_btn)
    ImageView backBtn;

    public static SubCategoryActivity instance=null;

    public static SubCategoryActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        instance=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List list=new ArrayList();
        list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");
        list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");
        list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");
        list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");
        list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");list.add("1");

        RecyclerViewAdapterSubCategory adapterSubCategory=new RecyclerViewAdapterSubCategory(this,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterSubCategory);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });





    }

}
