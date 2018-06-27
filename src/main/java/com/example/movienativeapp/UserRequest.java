package com.example.movienativeapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eli on 6/23/2018.
 */
//this classs responsible for all user request
public class UserRequest {
    private String _args[];
    private List<RequestInterface> listeners = new ArrayList<RequestInterface>();
    //constructor
    public UserRequest() {

    }

    public void addListener(RequestInterface toAdd) {
        listeners.add(toAdd);
    }
    public void removeListener()
    {
        listeners.clear();
    }




    public  void getCurrentUser(final RequestInterface returninter){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="users";
        final String controller_path= _args[1];


        Thread getCat = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();



                if (map != null) {
                   returninter.onRecive(callback);
                }

            }
        });

        getCat.start();
    }
    public void getReviewsByMovieId(int movie_id,final RequestInterface returninter)
    {
        _args = new String[3];
        _args[0]="GET";
        _args[1]="reviews/"+movie_id;
        Thread getCat = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();



                if (map != null) {
                    returninter.onRecive(callback);
                }

            }
        });

        getCat.start();

    }

    public  void getMoviesForCategory(String movie_id,final RequestInterface returninter){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="movies/categories";
        _args[2]=movie_id;




        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();



                if (map != null) {

                        returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }


}
