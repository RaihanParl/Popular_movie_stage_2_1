package com.bidjidev.popularmovie.Favorite;

import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.bidjidev.popularmovie.Adapter.FavoriteCursorAdapter;
import com.bidjidev.popularmovie.R;

public class FavoriteMain extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = FavoriteMain.class.getSimpleName();
    private static final int FAVORITE_LOADER_ID = 0;

    private FavoriteCursorAdapter favAdapter;
    RecyclerView rcFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_main);

        rcFavorite = (RecyclerView) findViewById(R.id.rcFav);

        rcFavorite.setLayoutManager(new LinearLayoutManager(this));

        favAdapter = new FavoriteCursorAdapter(this);
        rcFavorite.setAdapter(favAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int id = (int) viewHolder.itemView.getTag();

                String stringId = Integer.toString(id);
                Uri uri = FavoriteContract.FavoriteEnt.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                getContentResolver().delete(uri, null, null);

                getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, FavoriteMain.this);

            }
        }).attachToRecyclerView(rcFavorite);


        getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            Cursor favoriteData = null;

            @Override
            protected void onStartLoading() {
                if (favoriteData != null) {
                    deliverResult(favoriteData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(FavoriteContract.FavoriteEnt.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                favoriteData = data;
                super.deliverResult(data);
            }
        };

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favAdapter.swapCursor(null);
    }

}


