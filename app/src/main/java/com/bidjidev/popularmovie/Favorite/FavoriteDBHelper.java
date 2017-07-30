package com.bidjidev.popularmovie.Favorite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.bidjidev.popularmovie.Favorite.FavoriteContract;

import static com.bidjidev.popularmovie.Favorite.FavoriteContract.FavoriteEnt.TABLE_NAME;

/**
 * Created by You on 7/28/17.
 */

public class FavoriteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";

    private static final int VERSION = 1;

    FavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_ID + " TEXT NOT NULL,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_TITLE + " TEXT NOT NULL,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_OVERVIEW + " TEXT,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_AVERAGE + " REAL,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_COUNT + " INTEGER,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_BACKDROP_PATH + " TEXT,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_POSTER_PATH + " TEXT,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_RELEASE_DATE + " TEXT,"
                + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_FAV + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + FavoriteContract.FavoriteEnt.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
