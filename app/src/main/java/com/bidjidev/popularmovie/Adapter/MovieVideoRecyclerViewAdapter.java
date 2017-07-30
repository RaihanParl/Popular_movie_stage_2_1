package com.bidjidev.popularmovie.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bidjidev.popularmovie.R;
import com.bidjidev.popularmovie.gson.GsonVideo;

import java.util.List;

public class MovieVideoRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieVideoRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<GsonVideo.Result> mValues;

    public MovieVideoRecyclerViewAdapter(Context context, List<GsonVideo.Result> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mVideoName.setText(mValues.get(position).name);
        holder.mVideoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(mValues.get(position).key);
            }
        });
    }

    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mVideoName;
        public ViewHolder(View itemView) {
            super(itemView);
            mVideoName = (TextView) itemView.findViewById(R.id.txtTrailer);
        }
    }
    ;
}
