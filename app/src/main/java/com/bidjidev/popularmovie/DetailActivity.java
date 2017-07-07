package com.bidjidev.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class DetailActivity extends AppCompatActivity {
    public static TextView txtRilis, txtRate, txtOverview;
    public static ImageView imgMoviePoster, imgPosterbig;
    public static GetMovie movie;
    static String movie_poster_url;
    static String movie_posterbig_url;
    public static Intent intent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private LayoutInflater inflater;
    static Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initComponents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(movie.getoriginalTitle());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(movie.overview, movie.title);
            }
        });
        initComponents();
        setValues();
        popupimg();
    }

    public void initComponents() {
        movie = new GetMovie();
        DetailActivity.intent = this.getIntent();
        int movie_id = intent.getIntExtra("movie_id", 0);
        int movie_position = intent.getIntExtra("movie_position", 0);
        movie = MainActivity.getMovie.get(movie_position);
        txtRilis = (TextView) findViewById(R.id.txtYear);
        txtRate = (TextView) findViewById(R.id.txtRate);
        imgMoviePoster = (ImageView) findViewById(R.id.imgPoster);
        imgPosterbig = (ImageView) findViewById(R.id.imgPosterbig);
        txtOverview = (TextView) findViewById(R.id.txtOverView);
    }

    public static void setValues() {
        txtRilis.setText(movie.getreleaseDate());
        txtRate.setText(movie.getvoteAverage() + "/10");
        txtOverview.setText(movie.getOverview());
        if (movie.getposterPath() == Server.IMAGE_NOT_FOUND) {
            movie_poster_url = Server.IMAGE_NOT_FOUND;
            movie_posterbig_url = Server.IMAGE_NOT_FOUND;
        } else {
            movie_poster_url = Server.IMAGE_URL + Server.IMAGE_SIZE_185 + "/" + movie.getposterPath();
            movie_posterbig_url = Server.IMAGE_URL + Server.IMAGE_SIZE_185 + "/" + movie.getbackdropPath();
        }
        Picasso.with(con).load(movie_poster_url).into(imgMoviePoster);
        Picasso.with(con).load(movie_posterbig_url).into(imgPosterbig);
        imgMoviePoster.setVisibility(View.VISIBLE);


    }

    private void shareText(String textToShare, String tittle) {

        String mimeType = "text/plain";

        String title = tittle;
        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
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
}
