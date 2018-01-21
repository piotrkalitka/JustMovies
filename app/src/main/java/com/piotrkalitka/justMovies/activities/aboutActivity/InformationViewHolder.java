package com.piotrkalitka.justMovies.activities.aboutActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.piotrkalitka.justMovies.R;
import com.piotrkalitka.justMovies.api.models.AppAboutModel;

import butterknife.BindView;
import butterknife.ButterKnife;

class InformationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtLabel)
    TextView txtLabel;
    @BindView(R.id.txtEntry)
    TextView txtEntry;
    @BindView(R.id.imgWww)
    ImageView imgWww;
    @BindView(R.id.rootView)
    ConstraintLayout rootView;

    static View getView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.information_view_holder, parent, false);
    }

    InformationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(AppAboutModel model) {
        txtLabel.setText(model.getLabel());
        txtEntry.setText(model.getEntry());
        if (!TextUtils.isEmpty(model.getOnClickUrl())) imgWww.setVisibility(View.VISIBLE);
        rootView.setOnClickListener(v -> tryOpenUrl(model.getOnClickUrl()));
    }

    private void tryOpenUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            itemView.getContext().startActivity(browserIntent);
        }
    }

}