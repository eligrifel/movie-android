package com.example.movienativeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eli on 11/11/2017.
 */

public class Callback {
   private String[] _data;
    private ArrayList<HashMap<String,String>> _dataList;
    private Movie[] _movies;


    public Callback(String[] args, ArrayList<HashMap<String,String>> arraylist){

        _data=args;
        _dataList=arraylist;
    }

    public String[] get_data() {
        return _data;
    }

    public ArrayList<HashMap<String, String>> get_dataList() {
        return _dataList;
    }

    public Movie[] getMoviesList()
    {
        JsonToArraylist parcer = new JsonToArraylist();
        final String[] movie_id= parcer.getFieldArray(_dataList,"id");
        final String []  movie_name=parcer.getFieldArray(_dataList,"movie_name");
        final String []  movie_rate=parcer.getFieldArray(_dataList,"rating");
        final String []  movie_info=parcer.getFieldArray(_dataList,"info");
        final String []  movie_url=parcer.getFieldArray(_dataList,"pic_link");
        int size = movie_id.length;
        _movies = new Movie[size];
        for(int i=0;i<size;i++)
        {
            _movies[i]=new Movie(movie_id[i],movie_name[i],movie_url[i],movie_rate[i],movie_info[i]);

        }
return _movies;
    }
    public Movie[] getRentedMoviesList()
    {
        JsonToArraylist parcer = new JsonToArraylist();
        final String[] movie_id= parcer.getFieldArray(_dataList,"id");
        final String []  movie_name=parcer.getFieldArray(_dataList,"movie_name");
        final String []  movie_rate=parcer.getFieldArray(_dataList,"rating");
        final String []  movie_info=parcer.getFieldArray(_dataList,"info");
        final String []  movie_url=parcer.getFieldArray(_dataList,"pic_link");
        int size = movie_id.length;
        _movies = new Movie[size];
        for(int i=0;i<size;i++)
        {
            _movies[i]=new Movie(movie_id[i],movie_name[i],movie_url[i],movie_rate[i],movie_info[i]);

        }
        return _movies;
    }

}
