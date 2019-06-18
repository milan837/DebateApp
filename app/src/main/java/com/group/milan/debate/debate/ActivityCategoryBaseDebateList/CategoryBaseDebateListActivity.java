package com.group.milan.debate.debate.ActivityCategoryBaseDebateList;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.CategoryBaseDebateListResponse;
import com.group.milan.debate.debate.ActivityCategoryBaseDebateList.model.Response;
import com.group.milan.debate.debate.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryBaseDebateListActivity extends AppCompatActivity implements CategoryBaseDebateListContract.Views{

    @BindView(R.id.activity_categoryBase_debate_list_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.activity_categoryBase_debate_list_back_button)
    ImageView backBtn;

    @BindView(R.id.fragment_category_base_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.activity_category_base_deabte_title)
    TextView title;

    public static CategoryBaseDebateListActivity instance=null;
    public String keyword=null;
    List<Response> list;

    RecyclerViewAdapterCategoryBaseDebateList adapterCategoryBaseDebateList;
    CategoryBaseDebateListResponse categoryBaseDebateListResponse;
    CategoryBaseDebateListPresenter presenter;

    int getTotalcount,lastVisibleItem;
    boolean loadMoreStatus=true;

    //activity inst for entire lifecycle
    public static CategoryBaseDebateListActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_base_debate_list);
        ButterKnife.bind(this);

        instance=this;
        keyword=getIntent().getStringExtra("keyword");
        title.setText(keyword.substring(0,1).toUpperCase()+keyword.substring(1).toLowerCase());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter=new CategoryBaseDebateListPresenter(this,getApplicationContext());

        initViews();
    }

    private void initViews(){

        list=new ArrayList();

        adapterCategoryBaseDebateList=new RecyclerViewAdapterCategoryBaseDebateList(this,list);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCategoryBaseDebateList);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

        //hiting the api from view
        sendDataToApi("0",keyword);

        //pagination cal for api list
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
                    Response response = (Response) list.get(recyclerView.getAdapter().getItemCount() - 1);
                    if (!loadMoreStatus) {
                        Log.i("milan_last",response.getId());
                        //hiting the api from view
                        sendDataToApi(response.getId(),keyword);
                        loadMoreStatus = true;
                    }
                } else {
                    loadMoreStatus = false;
                }
            }
        });
    }

    public void sendDataToApi(String lastId,String keyword){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("last_id",lastId);
        jsonObject.addProperty("keyword",keyword);
        presenter.sendDataToApi(jsonObject);
    }

    @Override
    public void displayApiResponse(CategoryBaseDebateListResponse categoryBaseDebateListResponse) {
        progressBar.setVisibility(View.GONE);
        this.categoryBaseDebateListResponse=categoryBaseDebateListResponse;
        for(int i=0;i<categoryBaseDebateListResponse.getResponse().size();i++){
            list.add(categoryBaseDebateListResponse.getResponse().get(i));
            adapterCategoryBaseDebateList.notifyDataSetChanged();
        }
    }
}
