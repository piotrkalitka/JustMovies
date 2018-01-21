package com.piotrkalitka.justMovies.activities.aboutActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.api.ApiProvider;
import com.piotrkalitka.justMovies.api.models.AppAboutModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    private AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initLoadingDialog();
        showLoading();
        initErrorDialog();
        initRecyclerView();
        getData();

    }

    private void initErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_loading_error_title);
        builder.setMessage(getString(R.string.about_loading_error_message));
        builder.setCancelable(false);
        errorDialog = builder.create();
    }

    private void initLoadingDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.about_loading_title);
        progressDialog.setMessage(getString(R.string.about_loading_message));
        progressDialog.setCancelable(false);
    }

    private void showLoading(){
        progressDialog.show();
    }

    private void hideLoading(){
        progressDialog.dismiss();
    }

    private void showLoadingError(){
        errorDialog.show();
    }

    private void hideLoadingError(){
        errorDialog.dismiss();
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void updateRecyclerView(List<AppAboutModel> model){
        InformationAdapter adapter = new InformationAdapter(model);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        ApiProvider
                .provideApi()
                .getAppAbout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(appAboutModel -> {
                    hideLoading();
                    updateRecyclerView(appAboutModel);
                }, throwable -> {
                    hideLoading();
                    showErrorDialog();
                });
    }

    private void showErrorDialog(){

    }
}
