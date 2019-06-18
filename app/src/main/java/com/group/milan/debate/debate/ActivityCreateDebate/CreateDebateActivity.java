package com.group.milan.debate.debate.ActivityCreateDebate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateDebateActivity extends AppCompatActivity {

    @BindView(R.id.activity_create_debate_back_button)
    ImageView backBtn;


    public static CreateDebateActivity instance=null;

    public static CreateDebateActivity newInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_debate);
        ButterKnife.bind(this);
        instance=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }

}
