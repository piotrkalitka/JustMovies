package com.piotrkalitka.justMovies.activities.aboutActivity;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.piotrkalitka.justMovies.api.models.AppAboutModel;

import java.util.List;

class InformationAdapter extends RecyclerView.Adapter<InformationViewHolder> {

    private List<AppAboutModel> model;

    InformationAdapter(List<AppAboutModel> model){
        this.model = model;
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InformationViewHolder(InformationViewHolder.getView(parent));
    }

    @Override
    public void onBindViewHolder(InformationViewHolder holder, int position) {
        holder.bind(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

}
