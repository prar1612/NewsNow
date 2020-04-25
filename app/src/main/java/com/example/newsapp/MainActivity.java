package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText searchText;
    Button btnSearch;
    TextView headlines;
    TextView business_news;
    TextView politics_news;
    TextView health_news;
    TextView entertainment_news;
    final String API_KEY = "e788f0b90d7d4325a9869b2277aab9f6";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        searchText = findViewById(R.id.searchText);
        btnSearch = findViewById(R.id.btnSearch);
        headlines = findViewById(R.id.headlines);
        business_news = findViewById(R.id.business_news);
        politics_news = findViewById(R.id.politics_news);
        health_news = findViewById(R.id.health_news);
        entertainment_news = findViewById(R.id.entertainment_news);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountry();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("","",country,API_KEY);
            }
        });
        retrieveJson("","",country,API_KEY);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchText.getText().toString().equals("")) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(searchText.getText().toString(), "",country,API_KEY);
                        }
                    });


                    retrieveJson(searchText.getText().toString(),"",country,API_KEY);
                }
                else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("","",country,API_KEY);
                        }
                    });
                    retrieveJson("", "",country,API_KEY);
                }
            }
        });

        headlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    public void onRefresh() {
                        retrieveJson("", "",country,API_KEY);
                    }
                });
                retrieveJson("", "",country,API_KEY);
            }
        });

        business_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        public void onRefresh() {
                            retrieveJson("", "business",country,API_KEY);
                        }
                    });
                    retrieveJson("", "business",country,API_KEY);
            }
        });

        politics_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    public void onRefresh() {
                        retrieveJson("", "politics",country,API_KEY);
                    }
                });
                retrieveJson("","politics", country,API_KEY);
            }
        });

        health_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    public void onRefresh() {
                        retrieveJson("", "health",country,API_KEY);
                    }
                });
                retrieveJson("","health", country,API_KEY);
            }
        });

        entertainment_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    public void onRefresh() {
                        retrieveJson("", "entertainment",country,API_KEY);
                    }
                });
                retrieveJson("","entertainment", country,API_KEY);
            }
        });


    }

    public void retrieveJson(String query,String category, String country,String apiKey){
        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;
        if(!query.equals("")){
            call = ApClient.getInstance().getApi().getSpecificData(query,apiKey);
        }else if (!category.equals("")){
            call = ApClient.getInstance().getApi().getHeadlinesForCategory(category,country,apiKey);
        }
        else{
            call = ApClient.getInstance().getApi().getHeadlines(country,apiKey);
        }

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.US;
        String country = locale.getCountry();
        return country.toLowerCase();
    }




}
