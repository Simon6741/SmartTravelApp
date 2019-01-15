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

import com.simonsmarttravel.www.smarttravelapp.Model.CommentsCustom;

import java.util.List;

public class CommentListAdapter extends ArrayAdapter<CommentsCustom> {
    public CommentListAdapter(@NonNull Context context, @LayoutRes int resource, List<CommentsCustom> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        CommentListAdapter.ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custom_row_activity_comment, parent, false);

            holder = new CommentListAdapter.ViewHolder();
            holder.txtCommentMsg = (TextView) view.findViewById(R.id.txtCommentMsg);
            holder.txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            holder.rtbRate = (RatingBar) view.findViewById(R.id.rtbRate);

            view.setTag(holder);
        } else {
            holder = (CommentListAdapter.ViewHolder) view.getTag();
        }

        holder.txtCommentMsg.setText(getItem(position).getCommentMsg());
        holder.txtUserName.setText("Commented by " + getItem(position).getUserName());
        holder.rtbRate.setRating(getItem(position).getRate());

        return view;
    }

    public static class ViewHolder{
        public TextView txtCommentMsg;
        public TextView txtUserName;
        public RatingBar rtbRate;
    }
}
