package com.piotrkalitka.justMovies.activities.movieActivity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.api.models.MovieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

class RatingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtValue)
    TextView txtValue;

    RatingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    static View getView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_view_holder, parent, false);
    }

    void bind(MovieModel.Rating model) {
        txtTitle.setText(model.getSource());
        txtValue.setText(model.getValue());
    }

    void bindBg(){
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
    }

}
