package com.piotrkalitka.justMovies.api;

import android.support.annotation.Nullable;

import com.piotrkalitka.justMovies.api.models.MovieModel;
import com.piotrkalitka.justMovies.api.models.SearchModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    @GET("/")
    Observable<SearchModel> search(@Query("s") String title, @Nullable @Query("page") String page, @Nullable @Query("type") String type, @Nullable @Query("y") String year);

    @GET("/")
    Observable<MovieModel> getFilm(@Query("i") String id, @Nullable @Query("type") String type, @Nullable @Query("y") String year, @Nullable @Query("plot") String plot);

    //type - movie, series, episode
    //plot - short, full

}
