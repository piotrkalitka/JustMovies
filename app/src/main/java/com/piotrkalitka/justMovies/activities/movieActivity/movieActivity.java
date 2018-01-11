package com.piotrkalitka.justMovies.activities.movieActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.piotrkalitka.justMovies.Constants;
import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.api.ApiProvider;
import com.piotrkalitka.justMovies.api.models.MovieModel;
import com.piotrkalitka.justMovies.api.models.SearchModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class movieActivity extends AppCompatActivity {

    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtReleaseDate)
    TextView txtReleaseDate;
    @BindView(R.id.txtRuntime)
    TextView txtRuntime;
    @BindView(R.id.txtGenre)
    TextView txtGenre;
    @BindView(R.id.txtDirector)
    TextView txtDirector;
    @BindView(R.id.txtProduction)
    TextView txtProduction;
    @BindView(R.id.txtPlot)
    TextView txtPlot;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.paddingWrapper)
    ConstraintLayout paddingWrapper;
    @BindView(R.id.txtError)
    TextView txtError;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        initProgressDialog();
        showProgressDialog();
        initRecyclerView();
        initErrorListener();
        getData();
    }

    private void initErrorListener(){
        txtError.setOnClickListener(v -> getData());
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.message_loading));
    }

    private void showProgressDialog(){
        progressDialog.show();
    }

    private void hideProgressDialog(){
        progressDialog.dismiss();
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRecyclerViewData(List<MovieModel.Rating> ratings){
        RatingsAdapter adapter = new RatingsAdapter(ratings);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        assert getIntent().getExtras() != null;
        String id = getIntent().getExtras().getString(Constants.BUNDLE_KEY_ID);
        getMovie(id, this::bindView);
    }

    private void getMovie(String id, OnMovieFound onMovieFound) {
        ApiProvider
                .provideApi()
                .getFilm(id, null, null, SearchModel.PLOT_FULL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieModel -> {
                    hideProgressDialog();
                    onMovieFound.onMovieFound(movieModel);
                    hideError();
                }, throwable -> {
                    hideProgressDialog();
                    showError();
                });
    }

    private void showError(){
        txtError.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtError.setVisibility(View.GONE);
    }

    private void bindView(MovieModel model) {

        Picasso
                .with(this)
                .load(model.getPoster())
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder)
                .into(imgPoster);

        txtTitle.setText(model.getTitle());
        txtReleaseDate.setText(Html.fromHtml(getString(R.string.movie_release_date, model.getReleased())));
        txtRuntime.setText(Html.fromHtml(getString(R.string.movie_runtime, model.getRuntime())));
        txtGenre.setText(Html.fromHtml(getString(R.string.movie_genre, model.getGenre())));
        txtDirector.setText(Html.fromHtml(getString(R.string.movie_director, model.getDirector())));
        txtProduction.setText(Html.fromHtml(getString(R.string.movie_production, model.getProduction())));
        txtPlot.setText(model.getPlot());
        setRecyclerViewData(model.getRatings());

    }


    private interface OnMovieFound {
        void onMovieFound(MovieModel model);
    }
}
