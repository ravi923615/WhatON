package com.example.android.sunshine.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieListFragment extends android.support.v4.app.Fragment {
    ArrayAdapter<String> mMovieAdapter;
    public MovieListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] list = {"Ravi","Kumar","Sharma"};

        List<String> mMovieList = new ArrayList<String>(Arrays.asList(list));

        mMovieAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list_movie_text,R.id.list_movie_text_view,mMovieList);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_item_movie);
        listView.setAdapter(mMovieAdapter);
        return rootView;
    }
}