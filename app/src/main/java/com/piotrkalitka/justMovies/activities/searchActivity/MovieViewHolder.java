package com.piotrkalitka.justMovies.activities.searchActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.piotrkalitka.justMovies.Constants;
import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.activities.movieActivity.movieActivity;
import com.piotrkalitka.justMovies.api.models.SearchModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.txtPlot)
    TextView txtPlot;
    @BindView(R.id.cardView)
    CardView cardView;

    MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    static View getView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view_holder, parent, false);
    }

    void bind(Context context, SearchModel.Movie model) {
        txtTitle.setText(model.getTitle());
        txtYear.setText(model.getYear());
        txtPlot.setText(context.getString(R.string.lipsum));
        Picasso
                .with(context)
                .load(model.getPoster())
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder)
                .into(imgPoster);

        cardView.setOnClickListener(view -> startMovieActivity(context, model.getImdbID()));
    }

    private void startMovieActivity(Context context, String id){
        Intent intent = new Intent(context, movieActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_ID, id);
        context.startActivity(intent);
    }

}
