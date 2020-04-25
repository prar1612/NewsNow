package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.squareup.picasso.Picasso;

public class NewsDetails extends AppCompatActivity {
    TextView tvTitle,tvSource,tvTime,tvDesc;
    ImageView imageView;
    WebView webView;
    ProgressBar loader;
    Button btShare, btBrowse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        tvTitle = findViewById(R.id.tvTitle);
        tvSource = findViewById(R.id.tvSource);
        tvTime = findViewById(R.id.tvDate);
        tvDesc = findViewById(R.id.tvDesc);
        webView = findViewById(R.id.webView);
        loader = findViewById(R.id.loader);
        imageView = findViewById(R.id.imageView);
        btShare = (Button)findViewById(R.id.share);
        btBrowse = (Button)findViewById(R.id.webBrowse);
        loader.setVisibility(View.VISIBLE);
        //get all data from adapter using intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageURL = intent.getStringExtra("imageURL");
        String url = intent.getStringExtra("url");

        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText(time);
        tvDesc.setText(desc);

        btShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = getIntent();
                String url = intent.getStringExtra("url");
                String source = intent.getStringExtra("source");
                String stitle = intent.getStringExtra("title");

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, source);
                String body = stitle + "\n" + url + "\n" + "Shared from the NewsNow App" + "\n";
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Share with:"));
            }
        });

        btBrowse.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                 Intent intent = getIntent();
                 String url = intent.getStringExtra("url");
                 String source = intent.getStringExtra("source");
                 String stitle = intent.getStringExtra("title");

                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(url));
                 startActivity(i);
             }
         }
        );



        Picasso.with(NewsDetails.this).load(imageURL).into(imageView);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(ImageView.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if(webView.isShown()){
            loader.setVisibility(View.INVISIBLE);
        }



    }


}
