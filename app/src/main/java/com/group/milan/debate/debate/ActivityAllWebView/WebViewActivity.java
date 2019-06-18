package com.group.milan.debate.debate.ActivityAllWebView;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.activity_web_view)
    WebView webView;

    @BindView(R.id.activity_web_view_title)
    TextView title;

    @BindView(R.id.activity_webview_button)
    ImageView backBtn;

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        status=getIntent().getStringExtra("status");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(status.equals("bio")){
            title.setText("Developers details");
            webView.loadUrl("http://192.168.137.1/debate/website/aboutUs.php");
        }else if(status.equals("message")){
            title.setText("Message Us");
            webView.loadUrl("http://192.168.137.1/debate/website/aboutUs.php");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
            }
        });

    }

}
