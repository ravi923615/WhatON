package com.example.android.whaton.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MovieListFragment extends android.support.v4.app.Fragment {
    CustomMovieAdapter customMovieAdapter;
    CustomMovie[] customMovies = {new CustomMovie(R.drawable.cupcake),
                                  new CustomMovie(R.drawable.froyo),
                                  new CustomMovie(R.drawable.kitkat),
                                  new CustomMovie(R.drawable.lollipop)};
    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // String[] list = {"Ravi","Kumar","Sharma"};

       // List<String> mMovieList = new ArrayList<String>(Arrays.asList(list));

       // mMovieAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list_movie_text,R.id.list_movie_text_view,mMovieList);

        customMovieAdapter = new CustomMovieAdapter(getActivity(),Arrays.asList(customMovies));

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(customMovieAdapter);

        getPopularMovie movielis = new getPopularMovie();
        movielis.execute();
        return rootView;
    }

    public class getPopularMovie extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



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

                Log.v("sdf",popularMovieJSON);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
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


            return null;
        }
    }
}