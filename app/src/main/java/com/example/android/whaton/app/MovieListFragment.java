package com.example.android.whaton.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends android.support.v4.app.Fragment {
    CustomMovieAdapter customMovieAdapter;
  //  CustomMovie[] customMovies = {new CustomMovie(R.drawable.cupcake),
    //                              new CustomMovie(R.drawable.froyo),
      //                            new CustomMovie(R.drawable.kitkat),
        //                          new CustomMovie(R.drawable.lollipop)};

    @Override
    public void onStart() {
        super.onStart();
        getMovieDetailList();
    }

    public MovieListFragment() {
    }

    public void getMovieDetailList(){
        getPopularMovie popularMovie = new getPopularMovie();
        popularMovie.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // String[] list = {"Ravi","Kumar","Sharma"};

       // List<String> mMovieList = new ArrayList<String>(Arrays.asList(list));

       // mMovieAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list_movie_text,R.id.list_movie_text_view,mMovieList);


        customMovieAdapter = new CustomMovieAdapter(getActivity(),new ArrayList<CustomMovie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(customMovieAdapter);


        return rootView;
    }

    public class getPopularMovie extends AsyncTask<Void, Void, List<CustomMovie>> {

        private Bitmap getDrawableFromURL(String url) throws IOException, MalformedURLException {
            Bitmap img;

            HttpURLConnection getURLConnection = (HttpURLConnection) new URL(url).openConnection();
            getURLConnection.setRequestProperty("User-agent","Mozilla/4.0");

            getURLConnection.connect();

            InputStream inputStream = getURLConnection.getInputStream();

            img = BitmapFactory.decodeStream(inputStream);

            if(getURLConnection!=null){
                getURLConnection.disconnect();
            }

            return img;
        }

        private String[] getPopularMovieDataFromJSON(String popularMovieJSONStr)
                throws JSONException {

            JSONObject popularMovieJSON = new JSONObject(popularMovieJSONStr);
            JSONArray PopularmovieArray = popularMovieJSON.getJSONArray("results");
            String[] resultimages = new String[20];

            for(int i=0;i<PopularmovieArray.length();i++){
                String image;

                JSONObject results = PopularmovieArray.getJSONObject(i);
                image = results.getString("poster_path");

                resultimages[i] = image;

            }
            for(String s:resultimages) {
                Log.v("Get out", s);
            }
            return resultimages;

        }

        @Override
        protected List<CustomMovie> doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            List<CustomMovie> customMovies = null;



            try {
                final String PopularMovie = "https://api.themoviedb.org/3/discover/movie?";
                final String Query = "sort_by";
                final String APIKey = "api_key";


                String popularMovieJSON;
                String[] getJSONMovieStr = new String[0];

                Uri builtUri = Uri.parse(PopularMovie).buildUpon()
                        .appendQueryParameter(Query,"popularity.desc")
                        .appendQueryParameter(APIKey,"c5d20681e625f796b7fd405a697a2e91")
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if(inputStream==null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=reader.readLine())!=null){
                    stringBuffer.append(line+'\n');
                }
                if(stringBuffer==null)
                    return null;
                popularMovieJSON = stringBuffer.toString();
                getJSONMovieStr = getPopularMovieDataFromJSON(popularMovieJSON);
                for(String ur:getJSONMovieStr){
                    String fullurl = "https://image.tmdb.org/t/p/w396"+ur;
                    Log.v("Check",fullurl);
                  Bitmap getDrawImage = getDrawableFromURL(fullurl);
                    customMovies = new ArrayList<CustomMovie>();
                    customMovies.add(new CustomMovie(getDrawImage));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return customMovies;
        }

        @Override
        protected void onPostExecute(List<CustomMovie> customMovies) {
            if(customMovies!=null){
                customMovieAdapter.clear();
                for(CustomMovie lst:customMovies){
                    customMovieAdapter.add(lst);
                }
            }
        }
    }

}