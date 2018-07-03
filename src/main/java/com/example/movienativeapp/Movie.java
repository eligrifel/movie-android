package com.example.movienativeapp;

/**
 * Created by eli on 6/28/2018.
 */

public class Movie {
    private String _id;
    private String _name;
    private String _url;
    private String _rating;
    private String _info;



    public Movie(String id,String name, String url, String rating,String info) {

        this._rating = rating;
        this._url = url;
        this._name = name;
        this._id=id;
        this._info=info;

    }


    public String get_name() {
        return _name;
    }

    public String getUrl() {
        return _url;
    }

    public String getRating() {
        float rate=Float.parseFloat(_rating);
        rate = rate/2;
        return  Float.toString(rate);
    }

    public String getInfo() {
        return _info;
    }
    public String getId() {
        return _id;
    }

@Override
    public String toString()
    {
      return  "movie details is"+getId()+get_name()+getInfo()+getUrl()+getRating();
    }
}
