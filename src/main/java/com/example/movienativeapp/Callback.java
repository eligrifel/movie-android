package com.example.movienativeapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eli on 11/11/2017.
 *
 * this is a callback object holding information returned from te server
 */

public class Callback {
    private String[] _data;
    private ArrayList<HashMap<String, String>> _dataList;
    private Movie[] _movies;
    private Object _list;


    public Callback(String[] args, ArrayList<HashMap<String, String>> arraylist) {

        _data = args;
        _dataList = arraylist;
    }

    public String[] get_data() {
        return _data;
    }

    public ArrayList<HashMap<String, String>> get_dataList() {
        return _dataList;
    }

    public Movie[] getMoviesList() {
        Parcer parcer = new Parcer();
        final String[] movie_id = parcer.getFieldArray(_dataList, "id");
        final String[] movie_name = parcer.getFieldArray(_dataList, "movie_name");
        final String[] movie_rate = parcer.getFieldArray(_dataList, "rating");
        final String[] movie_info = parcer.getFieldArray(_dataList, "info");
        final String[] movie_url = parcer.getFieldArray(_dataList, "pic_link");
        final String[] movie_available = parcer.getFieldArray(_dataList, "available");
        final String[] movie_year = parcer.getFieldArray(_dataList, "year");
        final String[] movie_category = parcer.getFieldArray(_dataList, "category");


        int size = movie_id.length;
        _movies = new Movie[size];
        for (int i = 0; i < size; i++) {
            _movies[i] = new Movie(movie_id[i], movie_name[i], movie_url[i], movie_rate[i], movie_info[i], movie_available[i], movie_year[i], movie_category[i]);

        }
        return _movies;
    }

    public Movie[] getRentedMoviesList() {
        Parcer parcer = new Parcer();
        final String[] movie_id = parcer.getFieldArray(_dataList, "id");
        final String[] movie_name = parcer.getFieldArray(_dataList, "movie_name");
        final String[] movie_rate = parcer.getFieldArray(_dataList, "rating");
        final String[] movie_info = parcer.getFieldArray(_dataList, "info");
        final String[] movie_url = parcer.getFieldArray(_dataList, "pic_link");
        final String[] movie_available = parcer.getFieldArray(_dataList, "available");
        final String[] movie_year = parcer.getFieldArray(_dataList, "year");
        final String[] movie_category = parcer.getFieldArray(_dataList, "category");
        int size = movie_id.length;
        _movies = new Movie[size];
        for (int i = 0; i < size; i++) {
            _movies[i] = new Movie(movie_id[i], movie_name[i], movie_url[i], movie_rate[i], movie_info[i], movie_available[i], movie_year[i], movie_category[i]);

        }
        return _movies;
    }

    public String getRespondFromServer() {
        String[] message = null;
        Parcer parcer = new Parcer();
        String[] response = parcer.getFieldArray(_dataList, "result");
        String[] cause = parcer.getFieldArray(_dataList, "cause");
        message = parcer.getFieldArray(_dataList, "message");
        if (message.length > 0)
            return message[0];
        if (((response != null) && (response[0] != null))) {
            if ((response[0].equals("SUCCESS"))) {
                return "1";
            } else
                return cause[0];
        }
        return "0";
    }

    public void ShowResponse(final Context context, String success_message) {
        String response = getRespondFromServer();
        final String message;
        if (response.equals("1")) {

            message = success_message;
        } else
            message = response;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });


    }


    public void setList(Object o) {
        _list = o;

    }

    public Object getList() {
        return _list;
    }
}
