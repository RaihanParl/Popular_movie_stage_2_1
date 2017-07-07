package com.bidjidev.popularmovie;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.bidjidev.popularmovie.DetailActivity;
import com.bidjidev.popularmovie.GetMovie;
import com.bidjidev.popularmovie.PosterAdapter;
import com.bidjidev.popularmovie.R;
import com.bidjidev.popularmovie.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Page2 extends AppCompatActivity {
    static public PosterAdapter posterAdapter;
    static public String lastSortOrder;
    static GridView gridview;
    public static int page = 1;
    public static Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            MainActivity.getMovie = savedInstanceState.getParcelableArrayList("movies");
            MainActivity.images = savedInstanceState.getStringArrayList("images");
        }else{
            MainActivity.getMovie = new ArrayList<GetMovie>();
            MainActivity.images = new ArrayList<String>();
            posterAdapter = new PosterAdapter(getApplication());
            updateMovies();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.movie_grid);
        TextView txtPage = (TextView)findViewById(R.id.txtPage);
        txtPage.setText(""+MainActivity.page);
        int ot = getResources().getConfiguration().orientation;
        gridview.setNumColumns(ot == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        gridview.setAdapter(posterAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("movie_id", MainActivity.getMovie.get(position).getId());
                intent.putExtra("movie_position", position);
                startActivity(intent);
            }
        });
        toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);
        FloatingActionButton fab_next = (FloatingActionButton) findViewById(R.id.fab_next);
        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.page = MainActivity.page + 1;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        FloatingActionButton fab_prev = (FloatingActionButton) findViewById(R.id.fab_prev);
        if (MainActivity.page == 1){
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
            MainActivity.getMovie = new ArrayList<GetMovie>();
            MainActivity.images = new ArrayList<String>();
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
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.push_out_right,R.anim.push_out_right);
        }

    }
}
