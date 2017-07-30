package com.bidjidev.popularmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bidjidev.popularmovie.Adapter.PosterAdapter;
import com.bidjidev.popularmovie.Favorite.FavoriteMain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static public PosterAdapter posterAdapter;
    static public ArrayList<GetMovie> getMovie;
    static public ArrayList<String> images;
    static public String lastSortOrder;
    static GridView gridview;
    public static Toast toast;
    public static int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            getMovie = savedInstanceState.getParcelableArrayList("movies");
            images = savedInstanceState.getStringArrayList("images");
        }else{
            getMovie = new ArrayList<GetMovie>();
            images = new ArrayList<String>();
            posterAdapter = new PosterAdapter(getApplication());
            updateMovies();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.movie_grid);
        int ot = getResources().getConfiguration().orientation;
        gridview.setNumColumns(ot == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        gridview.setAdapter(posterAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("movie_id", getMovie.get(position).getId());
                intent.putExtra("sOverviewIndex", getMovie.get(position).getOverview());
                intent.putExtra("sbackdropIndex", getMovie.get(position).getbackdropPath());
                intent.putExtra("sreleaseIndex", getMovie.get(position).getreleaseDate());
                intent.putExtra("averageIndex", getMovie.get(position).getvoteAverage());
                intent.putExtra("countIndex", getMovie.get(position).getVote_count());
                intent.putExtra("titleIndex", getMovie.get(position).getTitle());
                intent.putExtra("posterIndex", getMovie.get(position).getposterPath());
                intent.putExtra("movie_position", position);
                intent.putExtra("favoritte","0" );

                startActivity(intent);
            }
        });
        TextView txtPage = (TextView)findViewById(R.id.txtPage);
        txtPage.setText(""+MainActivity.page);
        toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);
        FloatingActionButton fab_next = (FloatingActionButton) findViewById(R.id.fab_next);
        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = page +1;
                startActivity(new Intent(getApplicationContext(),Page2.class));
            }
        });
        FloatingActionButton fab_prev = (FloatingActionButton) findViewById(R.id.fab_prev);
        if (MainActivity.page == (1)){
            fab_prev.setVisibility(View.GONE);
        }else {
            fab_prev.setVisibility(View.VISIBLE);

        }if (MainActivity.page == 1000){
            fab_next.setVisibility(View.GONE);
        }else {
            fab_next.setVisibility(View.VISIBLE);
        }

        fab_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    MainActivity.page = MainActivity.page - 1;
                    startActivity(new Intent(getApplicationContext(), Page2.class));
                    overridePendingTransition(R.anim.push_out_right, R.anim.push_out_right);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.setting) {
            startActivity(new Intent(this,Setting.class));
            return true;
        }else if (id == R.id.pJump){
            startActivity(new Intent(this,JumpPage.class));
            return true;
        }else if (id == R.id.Favorite){
            startActivity(new Intent(this,FavoriteMain.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", MainActivity.getMovie);
        outState.putStringArrayList("images", MainActivity.images);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key), getString(R.string.pref_sorting_criteria_default_value));

        if(lastSortOrder!= null && !sortingCriteria.equals(lastSortOrder)){
            getMovie = new ArrayList<GetMovie>();
            images = new ArrayList<String>();
            updateMovies();
        }
        lastSortOrder = sortingCriteria;
        super.onResume();

    }



    public void updateMovies() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key), getString(R.string.pref_sorting_criteria_default_value));
        new MoviesTask().execute(sortingCriteria, null);
    }
    @Override
    public void onBackPressed() {
        if (MainActivity.page == 1){
            moveTaskToBack(true);
        }else {
            MainActivity.page = MainActivity.page - 1;
            startActivity(new Intent(this, Page2.class));
            overridePendingTransition(R.anim.push_out_right,R.anim.push_out_right);
        }
    }
}
