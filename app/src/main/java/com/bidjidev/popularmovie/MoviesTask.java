package com.bidjidev.popularmovie;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by You on 6/29/17.
 */

public class MoviesTask extends AsyncTask<String, Void, String> {
    public static String sortingCriteria;
    String url;
    @Override
    public String doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        sortingCriteria = params[0];
        Log.d(TAG, "doInBackground: "+sortingCriteria);;
        if (sortingCriteria.equals("popular")){
            url = "http://api.themoviedb.org/3/movie/"+"popular"+"?";
        }else{
            url = "http://api.themoviedb.org/3/movie/"+"top_rated"+"?";

        }
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key", Server.API_KEY)
                .appendQueryParameter("page", String.valueOf(MainActivity.page))
                .build();
        String response;
        try {
            response = getJSON(builtUri);
            return response;
        } catch (Exception e) {
            MainActivity.toast.setText("No Internet Conection");
            MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
            MainActivity.toast.show();
            return null;
        }


    }

    @Override
    protected void onPostExecute(String response) {

        if (response != null) {
            loadInfo(response);
        } else {
            MainActivity.toast.setText("No Internet Conection");
            MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
            MainActivity.toast.show();
        }

    }


    public static void loadInfo(String jsonString) {
        MainActivity.images.clear();
        MainActivity.getMovie.clear();

        try {
            if (jsonString != null) {
                JSONObject moviesObject = new JSONObject(jsonString);
                JSONArray moviesArray = moviesObject.getJSONArray("results");


                for (int i = 0; i <= moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    GetMovie movieItem = new GetMovie();
                    movieItem.setTitle(movie.getString("title"));
                    movieItem.setId(movie.getInt("id"));
                    movieItem.setbackdropPath(movie.getString("backdrop_path"));
                    movieItem.setoriginalTitle(movie.getString("original_title"));
                    movieItem.setOriginal_language(movie.getString("original_language"));
                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setreleaseDate("Unknown Release Date");
                    } else {
                        movieItem.setreleaseDate(movie.getString("release_date"));
                    }
                    movieItem.setPopularity(movie.getString("popularity"));
                    movieItem.setvoteAverage(movie.getString("vote_average"));
                    movieItem.setposterPath(movie.getString("poster_path"));
                    if (movie.getString("poster_path") == "null") {
                        MainActivity.images.add(Server.IMAGE_NOT_FOUND);
                        movieItem.setposterPath(Server.IMAGE_NOT_FOUND);
                    } else {
                        MainActivity.images.add(Server.IMAGE_URL + Server.IMAGE_SIZE_185 + movie.getString("poster_path"));
                    }
                    MainActivity.getMovie.add(movieItem);
                    MainActivity.posterAdapter.notifyDataSetChanged();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String getJSON(Uri builtUri)
    {
        InputStream inputStream;
        StringBuffer buffer;
        HttpURLConnection urlConnection = null;
        BufferedReader bufReader = null;
        String jsonMovie = null;

        try {
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            bufReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonMovie = buffer.toString();
        } catch (IOException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (final IOException e) {

                }
            }
        }

        return jsonMovie;
    }
}


