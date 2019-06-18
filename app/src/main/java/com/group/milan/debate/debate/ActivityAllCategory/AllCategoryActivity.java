package com.group.milan.debate.debate.ActivityAllCategory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityAllCategory.model.AllCategoryPojo;
import com.group.milan.debate.debate.ActivityAllCategory.model.Response;
import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCategoryActivity extends AppCompatActivity implements AllCategoryContract.Views {

    @BindView(R.id.activity_all_category_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.activity_all_category_back_button)
    ImageView backBtn;

    @BindView(R.id.activity_all_category_progressbar)
    ProgressBar progressBar;

    List<Response> list;
    RecyclerViewAdapterAllCategory adapterAllCategory;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    AllCategoryPresenter presenter;

    public static AllCategoryActivity instance=null;

    public static AllCategoryActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        ButterKnife.bind(this);
        instance=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=new ArrayList<>();
        this.presenter=new AllCategoryPresenter(this,getApplicationContext());
        initViews();

    }

    private void initViews(){
        sendDataToApi("0");

        adapterAllCategory=new RecyclerViewAdapterAllCategory(this,list);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterAllCategory);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getTotalcount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (getTotalcount - 1 == lastVisibleItem) {
                    Response response= (Response) list.get(recyclerView.getAdapter().getItemCount() - 1);
                    if (!loadMoreStatus) {
                        Log.i("milan_last",response.getCategoryId());
                        //hiting the api from view
                        sendDataToApi(response.getCategoryId());
                        loadMoreStatus = true;
                    }
                } else {
                    loadMoreStatus = false;
                }
            }
        });
    }

    public void sendDataToApi(String paginationId){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("pagination_id",paginationId);
        presenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayResponse(AllCategoryPojo allCategoryPojo) {
        progressBar.setVisibility(View.GONE);
        for(int i=0;i<allCategoryPojo.getResponse().size();i++){
            list.add(allCategoryPojo.getResponse().get(i));
            adapterAllCategory.notifyDataSetChanged();
        }
    }

}
