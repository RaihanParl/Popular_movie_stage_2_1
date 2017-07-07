package com.bidjidev.popularmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 Created by You on 6/29/17.
 */
public class PosterAdapter extends BaseAdapter {


    private Context mContext;


    public PosterAdapter(Context c) {
        mContext = c;
    }


    public int getCount() {
        return MainActivity.images.size();
    }


    public Object getItem(int position) {
        return null;
    }


    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgPoster;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imgPoster = (ImageView) inflater.inflate(R.layout.list_movie, parent, false);
        } else {
            imgPoster = (ImageView) convertView;
        }
        Picasso.with(mContext)
                .load(MainActivity.images.get(position))
                .into(imgPoster);
        return imgPoster;
    }

}

