package com.bidjidev.popularmovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bidjidev.popularmovie.Adapter.MovieReviewRCAdapter;
import com.bidjidev.popularmovie.Adapter.MovieVideoRecyclerViewAdapter;
import com.bidjidev.popularmovie.Favorite.FavoriteContract;
import com.bidjidev.popularmovie.gson.GsonReviews;
import com.bidjidev.popularmovie.gson.GsonVideo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class DetailActivity extends AppCompatActivity {
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private static final String TAG = "tag";
    public static TextView txtRilis, txtRate, txtOverview;
    public static ImageView imgMoviePoster, imgPosterbig;
//    public static GetMovie movie;
    static String movie_poster_url;
    static String movie_posterbig_url;
    public static Intent intent;
    public static Intent i;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String sOverviewIndex;
    String sbackdropIndex;
    String sreleaseIndex;
    String sfavIndex;
    String sRate;
    String sTitleIndex;
    String posterIndex;
    String countIndex;
    String sFavorite = "0";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.btnFavo)
    Button btnFavo;
    private LayoutInflater inflater;
    static Context con;
    private ShareActionProvider mShareActionProvider;
    MovieVideoRecyclerViewAdapter adapter;
    RecyclerView rcReview;
    GsonVideo gsonVideo;
    GsonReviews gsonReviews;
    int movie_id;
    RecyclerView rcMovie;
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rcReview = (RecyclerView) findViewById(R.id.rcReview);
        rcMovie = (RecyclerView) findViewById(R.id.rcMovie);
        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        LinearLayoutManager linearmanager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager apake = new LinearLayoutManager(getApplicationContext());
        rcMovie.setLayoutManager(linearmanager);
        rcReview.setLayoutManager(apake);
        initComponents();
        getReview();
        setValues();
        getTrailer();
        popupimg();
        setSupportActionBar(toolbar);
        initComponents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(sTitleIndex);
        if (sFavorite.equals("1")){
            btnFavo.setVisibility(View.GONE);
        }else {
            btnFavo.setVisibility(View.VISIBLE);

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(sOverviewIndex,sTitleIndex);
            }
        });

    }

    public void initComponents() {
        DetailActivity.intent = this.getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        int movie_position = intent.getIntExtra("movie_position", 0);
        sOverviewIndex = intent.getStringExtra("sOverviewIndex");
        sbackdropIndex = intent.getStringExtra("sbackdropIndex");
        sreleaseIndex = intent.getStringExtra("sreleaseIndex");
        sfavIndex = intent.getStringExtra("sfavIndex");
        sRate = intent.getStringExtra("averageIndex");
        sfavIndex = intent.getStringExtra("sfavIndex");
        sTitleIndex = intent.getStringExtra("titleIndex");
        countIndex = intent.getStringExtra("countIndex");
        posterIndex = intent.getStringExtra("posterIndex");
        sFavorite = intent.getStringExtra("favoritte");

        txtRilis = (TextView) findViewById(R.id.txtYear);
        txtRate = (TextView) findViewById(R.id.txtRate);
        imgMoviePoster = (ImageView) findViewById(R.id.imgPoster);
        imgPosterbig = (ImageView) findViewById(R.id.imgPosterbig);
        txtOverview = (TextView) findViewById(R.id.txtOverView);
    }

    public void setValues() {
        txtRilis.setText(sreleaseIndex);
        txtRate.setText(sRate + "/10");
        txtOverview.setText(sOverviewIndex);
        if (posterIndex == Server.IMAGE_NOT_FOUND) {
            movie_poster_url = Server.IMAGE_NOT_FOUND;
            movie_posterbig_url = Server.IMAGE_NOT_FOUND;
        } else {
            movie_poster_url = Server.IMAGE_URL + Server.IMAGE_SIZE_185 + "/" + posterIndex;
            movie_posterbig_url = Server.IMAGE_URL + Server.IMAGE_SIZE_185 + "/" + sbackdropIndex;
        }
        Picasso.with(con).load(movie_poster_url).into(imgMoviePoster);
        Picasso.with(con).load(movie_posterbig_url).into(imgPosterbig);
        imgMoviePoster.setVisibility(View.VISIBLE);


    }

    private void shareText(String textToShare, String tittle) {

        String mimeType = "text/plain";

        String title = tittle;
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("share to:")
                .setText("Tittle:" + title + "\n" + "Over View:" + textToShare)
                .startChooser();
    }

    public void popupimg() {
        imgMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = DetailActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.popimg, null);
                PhotoView pv;
                pv = (PhotoView) content.findViewById(R.id.pvcomment);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                Picasso.with(con).load(movie_poster_url).into(pv);
                builder.setView(content);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        imgPosterbig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = DetailActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.popimg, null);
                PhotoView pv;
                pv = (PhotoView) content.findViewById(R.id.pvcomment);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

                Picasso.with(con).load(movie_posterbig_url).into(pv);
                builder.setView(content);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }


    private void getTrailer() {
        DetailActivity.intent = this.getIntent();
        int movie_idt = intent.getIntExtra("movie_id", 0);
        String sMovie_id = movie_idt + "";
        String URL = Server.BASE_URL + sMovie_id + "/videos?api_key=" + Server.API_KEY;
        Log.i(TAG, "getTrailer: " + URL);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                gsonVideo = gson.fromJson(response, GsonVideo.class);
                MovieVideoRecyclerViewAdapter adapter = new MovieVideoRecyclerViewAdapter(getApplicationContext(), gsonVideo.results);
                rcMovie.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramuserlogin = new HashMap<>();
                paramuserlogin.put("api_key=", Server.API_KEY);
                return paramuserlogin;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void getData() {
        DetailActivity.intent = this.getIntent();
        int movie_position = intent.getIntExtra("movie_position", 0);

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_ID, movie_id);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_TITLE, sTitleIndex);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_OVERVIEW, sOverviewIndex);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_AVERAGE,sRate);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_VOTE_COUNT,countIndex );
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_BACKDROP_PATH, sbackdropIndex);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_POSTER_PATH, posterIndex);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_RELEASE_DATE, sreleaseIndex);
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_FAV, "1");
        contentValues.put(FavoriteContract.FavoriteEnt.COLUMN_MOVIE_ID, movie_id);
        Uri uri = getContentResolver().insert(FavoriteContract.FavoriteEnt.CONTENT_URI, contentValues);
        if (uri != null) {
//            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }


    }

    private void getReview() {
        DetailActivity.intent = this.getIntent();
        int movie_idt = intent.getIntExtra("movie_id", 0);
        String sMovie_id = movie_idt + "";
        String URL = Server.BASE_URL + sMovie_id + "/reviews?api_key=" + Server.API_KEY;
        Log.i(TAG, "getTrailer: " + URL);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                gsonReviews = gson.fromJson(response, GsonReviews.class);
                MovieReviewRCAdapter adapter = new MovieReviewRCAdapter(getApplicationContext(), gsonReviews.resultsRev);
                rcReview.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramuserlogin = new HashMap<>();
                paramuserlogin.put("api_key", Server.API_KEY);
                return paramuserlogin;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void btnFav(View v) {
        getData();
        btnFavo.setEnabled(false);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ mScrollView.getScrollX(), mScrollView.getScrollY()});
    }
//
//    Restore them on onRestoreInstanceState
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        if(position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });
    }

}
