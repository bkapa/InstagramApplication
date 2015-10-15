package com.walmartlabs.bkapa.instagramapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bkapa on 10/14/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>{
    public InstagramPhotosAdapter(Context context , List<InstagramPhoto> objects) {

        super(context,android.R.layout.simple_list_item_1,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       InstagramPhoto photo = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
            TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            tvUserName.setText(photo.getUsername());
            tvLikes.setText(new StringBuilder().append(photo.getLikesCount()).append("likes").toString());
            tvCaption.setText(photo.getCaption());
            ivPhoto.setImageResource(0);
            Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);



        return convertView;

    }
}
