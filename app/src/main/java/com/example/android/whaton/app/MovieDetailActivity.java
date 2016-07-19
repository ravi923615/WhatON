package com.example.android.whaton.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MovieDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_activity_view,new MovieDetailFragment())
                    .commit();
        }
    }
}
