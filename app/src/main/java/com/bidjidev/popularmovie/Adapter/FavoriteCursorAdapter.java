package com.bidjidev.popularmovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bidjidev.popularmovie.DetailActivity;
import com.bidjidev.popularmovie.Favorite.FavoriteContract;
import com.bidjidev.popularmovie.MainActivity;
import com.bidjidev.popularmovie.R;
import com.bidjidev.popularmovie.Server;
import com.squareup.picasso.Picasso;

import static com.bidjidev.popularmovie.MainActivity.getMovie;

public class FavoriteCursorAdapter extends RecyclerView.Adapter<FavoriteCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FavoriteCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_favorite, parent, false);

        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        int idIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt._ID);
        int idIndexMovie = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_ID);
         int titleIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_TITLE);
        int OverviewIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_OVERVIEW);
         int averageIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_AVERAGE);
         int countIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_COUNT);
        int backdropIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_BACKDROP_PATH);
        int posterIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_POSTER_PATH);
        int releaseIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_RELEASE_DATE);
        int favIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_FAV);
        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        final int idMovie = mCursor.getInt(idIndexMovie);
        String stitleIndex = mCursor.getString(titleIndex);
        final String sOverviewIndex = mCursor.getString(OverviewIndex);
        final String sbackdropIndex = mCursor.getString(backdropIndex);
        final String sposterIndex = mCursor.getString(posterIndex);
        final String sreleaseIndex = mCursor.getString(releaseIndex);
        final String sfavIndex = mCursor.getString(favIndex);
        final String SaverageIndex = mCursor.getString(averageIndex);
        final String ScountIndex = mCursor.getString(countIndex);
        final String StitleIndex = mCursor.getString(titleIndex);
        holder.itemView.setTag(id);
        holder.txtTitle.setText(stitleIndex);
        holder.cvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(), DetailActivity.class);
                a.putExtra("movie_id", idMovie);
                a.putExtra("movie_position",id );
                a.putExtra("sOverviewIndex",sOverviewIndex );
                a.putExtra("sbackdropIndex",sbackdropIndex );
                a.putExtra("sreleaseIndex",sreleaseIndex );
                a.putExtra("sfavIndex",sfavIndex );
                a.putExtra("averageIndex",SaverageIndex );
                a.putExtra("countIndex",ScountIndex );
                a.putExtra("titleIndex",StitleIndex );
                a.putExtra("posterIndex",sposterIndex );
                a.putExtra("posterIndex",sposterIndex );
                a.putExtra("favoritte","1" );
                v.getContext().startActivity(a);
            }
        });
        String movie_poster_url = Server.IMAGE_URL + Server.IMAGE_SIZE_185 + "/" + sposterIndex;
        Picasso.with(mContext)
                .load(movie_poster_url)
                .into(holder.imgFav);


    }
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;


        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle;
        ImageView imgFav;
        CardView cvFavorite;
        public TaskViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgFav = (ImageView) itemView.findViewById(R.id.imgFav);
            cvFavorite = (CardView) itemView.findViewById(R.id.cvFavorite);

        }
    }
}