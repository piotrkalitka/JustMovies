package com.piotrkalitka.justMovies.activities.searchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.activities.aboutActivity.AboutActivity;
import com.piotrkalitka.justMovies.api.ApiProvider;
import com.piotrkalitka.justMovies.api.models.SearchModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements FloatingSearchView.OnSearchListener, FloatingSearchView.OnQueryChangeListener {

    @BindView(R.id.searchView)
    FloatingSearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtNothingFound)
    TextView txtNothingFound;
    @BindView(R.id.txtHint)
    TextView txtHint;

    private boolean fromSuggestion;
    private Subscription request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initToolbar();
        initSearchView();
        initRecyclerView();
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        fromSuggestion = true;
        MovieSuggestion suggestion = (MovieSuggestion) searchSuggestion;
        searchView.setSearchText(suggestion.getBody());
        searchView.clearSuggestions();
        searchView.clearSearchFocus();
    }

    @Override
    public void onSearchAction(String currentQuery) {
        if (TextUtils.isEmpty(currentQuery)) {
            setRecyclerViewData(new ArrayList<>());
            showHint();
        } else {
            searchMovies(currentQuery, searchModel -> setRecyclerViewData(searchModel.getSearch()));
            hideHint();
        }
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (!fromSuggestion) {
            if (!TextUtils.isEmpty(newQuery) && newQuery.length() != oldQuery.length() - 1) {
                searchMovies(newQuery, this::showSuggestions);
            }
            if (TextUtils.isEmpty(newQuery)) {
                searchView.clearSuggestions();
                showHint();
            }
        } else {
            hideHint();
            fromSuggestion = false;
            searchMovies(newQuery, searchModel -> setRecyclerViewData(searchModel.getSearch()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return false;
    }

    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initSearchView() {
        searchView.setOnQueryChangeListener(this);
        searchView.setOnSearchListener(this);
    }


    private void setRecyclerViewData(List<SearchModel.Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        if (movies == null && txtHint.getVisibility() != View.VISIBLE) showNothingFound();
        else hideNothingFound();
    }

    private void searchMovies(String query, OnMoviesFound onMoviesFound) {
        if (request != null) request.unsubscribe();
        searchView.showProgress();
        request = ApiProvider
                .provideApi()
                .search(query, null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchModel -> {
                    searchView.hideProgress();
                    onMoviesFound.onMoviesFound(searchModel);
                }, throwable -> {
                    searchView.hideProgress();
                    showSearchError();
                });
    }

    private void showSearchError() {
        Toast.makeText(this, R.string.movie_loading_error, Toast.LENGTH_LONG).show();
    }

    private void showSuggestions(SearchModel model) {
        if (model.getSearch() != null) {
            List<MovieSuggestion> suggestions = new ArrayList<>();
            for (SearchModel.Movie movie : model.getSearch()) {
                suggestions.add(new MovieSuggestion(movie.getImdbID(), movie.getTitle()));
            }
            searchView.swapSuggestions(suggestions);
        } else {
            searchView.clearSuggestions();
        }
    }

    private void showNothingFound() {
        txtNothingFound.setVisibility(View.VISIBLE);
    }

    private void hideNothingFound() {
        txtNothingFound.setVisibility(View.GONE);
    }

    private void showHint() {
        txtHint.setVisibility(View.VISIBLE);
    }

    private void hideHint() {
        txtHint.setVisibility(View.GONE);
    }


    private interface OnMoviesFound {
        void onMoviesFound(SearchModel searchModel);
    }
}
