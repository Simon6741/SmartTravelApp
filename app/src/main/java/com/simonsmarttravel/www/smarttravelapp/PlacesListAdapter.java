package com.simonsmarttravel.www.smarttravelapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.simonsmarttravel.www.smarttravelapp.Model.Places;

import java.util.ArrayList;
import java.util.List;

public class PlacesListAdapter extends ArrayAdapter<Places> {
    public PlacesListAdapter(@NonNull Context context, @LayoutRes int resource, List<Places> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_row_activity_places, parent, false);

            holder = new ViewHolder();
            holder.txtPlaceName = (TextView) view.findViewById(R.id.txtPlaceName);
            holder.txtPlaceDesc = (TextView) view.findViewById(R.id.txtPlaceDesc);
            holder.rtbRating = (RatingBar) view.findViewById(R.id.rtbRating);
holder.placesId = 0;

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtPlaceName.setText(getItem(position).getPlaceName());
        holder.txtPlaceDesc.setText(getItem(position).getPlaceDesc());
        holder.rtbRating.setRating(getItem(position).getAvgRating());
holder.placesId = getItem(position).getId();

        return view;
    }

    public static class ViewHolder{
        public int placesId;
        public TextView txtPlaceName;
        public TextView txtPlaceDesc;
        public RatingBar rtbRating;
    }
}
