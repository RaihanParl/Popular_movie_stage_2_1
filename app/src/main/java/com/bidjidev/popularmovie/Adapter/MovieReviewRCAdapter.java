package com.bidjidev.popularmovie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bidjidev.popularmovie.R;
import com.bidjidev.popularmovie.gson.GsonReviews;

import java.util.List;

/**
 * Created by You on 7/27/17.
 */

public class MovieReviewRCAdapter extends RecyclerView.Adapter<MovieReviewRCAdapter.ViewHolder> {
    private Context context;
    private final List<GsonReviews.Result> mValues;
    public MovieReviewRCAdapter(Context context, List<GsonReviews.Result> items) {
        mValues = items;
        this.context = context;
    }
    @Override
    public MovieReviewRCAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_review, parent, false);
        return new MovieReviewRCAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewRCAdapter.ViewHolder holder, int position) {
        holder.txtUsername.setText(mValues.get(position).author);
        holder.txtReview.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
            return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername,txtReview;

        public ViewHolder(View itemView) {
            super(itemView);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);

        }
    }
}
