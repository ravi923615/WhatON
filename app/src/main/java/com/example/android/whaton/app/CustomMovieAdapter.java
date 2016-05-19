package com.example.android.whaton.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by kailash on 5/15/2016.
 */
public class CustomMovieAdapter extends ArrayAdapter<CustomMovie> {
    private static final String LOG_TAG = CustomMovieAdapter.class.getSimpleName();

    /**
     * This is my custom constructor
     * The context is used to inflate the layout file, and the List is data we want
     * to populate into lists
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param CustomMovie A List of CustomMovie Objects to display in a list.
     */

    public CustomMovieAdapter(Activity context, List<CustomMovie> customMovies){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        super(context,0,customMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the CustomMovie Object from ArrayAdapter at the appropriate position
        CustomMovie customMovie = getItem(position);


        //Adapters recycle views to AdapterViews.
        //If this is a new View Object we're getting, then inflate the layout.
        //If not, this view already has layout inflated from a previous call to getView
        // and we modify the View widget as usual.

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_movie_image,parent,false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
        iconView.setImageBitmap(customMovie.image);

        return convertView;
    }
}
