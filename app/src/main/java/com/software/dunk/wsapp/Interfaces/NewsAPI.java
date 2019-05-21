package com.software.dunk.wsapp.Interfaces;

import com.software.dunk.wsapp.Models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("api/get_recent_posts")
    Call<News>getPosts(@Query("count") int count,
                       @Query("page") int page,
                       @Query("custom_fields") String fields,
                       @Query("include") String include);
}
