package com.software.dunk.wsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.software.dunk.wsapp.Adapters.MainNewAdapter;
import com.software.dunk.wsapp.Interfaces.NewsAPI;
import com.software.dunk.wsapp.Models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //=====WIDGETS
    private RecyclerView mRecycler;
    private MainNewAdapter mAdapter;
    //======VARS
    private static final String BASE_URL = "https://www.wallstreetitalia.com/";
    private final String CUSTOM_FIELDS = "PostThumb";
    private final String INCLUDE_FIELDS = "title,excerpt,date,thumbnail,url";
    private List<News.Posts> postsList = new ArrayList<>();
    private int COUNT_PAGE = 1;
    private LinearLayoutManager mManager;
    private ProgressBar mProgress;
    private boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("News");

        mProgress = findViewById(R.id.main_progress);
        mRecycler = findViewById(R.id.main_rv);
        setUpRecycler();
    }

    private void setUpRecycler(){
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        getNews(COUNT_PAGE);
        mAdapter = new MainNewAdapter(postsList, MainActivity.this);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mManager.getChildCount();
                totalItems = mManager.getItemCount();
                scrollOutItems = mManager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    COUNT_PAGE++;
                    mProgress.setVisibility(View.VISIBLE);
                    getNews(COUNT_PAGE);
                }
            }
        });
    }

    private void getNews(int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        Call<News> newsCall = newsAPI.getPosts(20,page,CUSTOM_FIELDS, INCLUDE_FIELDS);

        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()){
                    News news = response.body();
                    mAdapter.addPosts(news.getPosts());
                    mProgress.setVisibility(View.GONE);
                }else Toast.makeText(MainActivity.this, "ERRORE: Errore codice: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERRORE: Errore codice: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
