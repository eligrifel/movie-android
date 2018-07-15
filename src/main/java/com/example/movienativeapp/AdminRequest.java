package com.example.movienativeapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eli on 6/23/2018.
 */
//this class responsible for admin rest calls
public class AdminRequest {
    private String _args[];


    public  void getCategories(final RequestInterface returninter){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="movies/categories";




        Thread getCategories = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();

                // get only the needed data

                Parcer parcer = new Parcer();
                String[] categories=parcer.getFieldArray(map,"category_name");
                String[] id=parcer.getFieldArray(map,"id");
               ArrayList<String []> list = new ArrayList<>();
                list.add(0,id);
                list.add(1,categories);
                callback.setList(list);


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getCategories.start();
    }
    public  void addMovie(final HashMap postData,final RequestInterface returninter){
        _args = new String[3];
        _args[0]="POST";
        _args[1]="admin/movie";





        Thread createUser = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;


                callback = restRespond.getData(_args,postData);
                map=callback.get_dataList();



                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        createUser.start();
    }
    public  void getMovies(final RequestInterface returninter){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="movies";





        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();
                Movie [] movie_list= callback.getMoviesList();
                callback.setList(movie_list);

                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }

    public  void updateMovie(final HashMap postData,final RequestInterface returninter){
        _args = new String[3];
        _args[0]="PUT";
        _args[1]="admin/movie";





        Thread updateMovie = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;


                callback = restRespond.getData(_args,postData);
                map=callback.get_dataList();



                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        updateMovie.start();
    }

    public  void getAllLeaserMovies(final RequestInterface reternfunc){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="admin/leaser";





        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();
                Parcer parcer = new Parcer();


                String movies_id[]=parcer.getFieldArray(map,"movie_id");
                String names[]=parcer.getFieldArray(map,"user_name");
                String returnD []=parcer.getFieldArray(map,"return_date");
                String rentD[]=parcer.getFieldArray(map,"rented_date");
                String movie_names []=parcer.getFieldArray(map,"movie_name");


                HashMap h = new HashMap();
                h.put("names",names);
                h.put("movie_names",movie_names);
                h.put("returnD",returnD);
                h.put("rentD",rentD);
                h.put("movies_id",movies_id);
                callback.setList(h);





                if (map != null) {

                    reternfunc.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }

    public  void getPurchases(final RequestInterface returninter){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="/admin/purchases";





        Thread getpurchases = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();
                Parcer parcer = new Parcer();

                String [] f_name=parcer.getFieldArray(map,"first_name");
                String [] l_name=parcer.getFieldArray(map,"last_name");
                String [] date=parcer.getFieldArray(map,"date");
                String [] credit=parcer.getFieldArray(map,"amount");
                String [] user_id=parcer.getFieldArray(map,"user_id");
                String [] purchase_raws= new String[f_name.length];
                for(int i=0;i<f_name.length;i++)
                {
                    purchase_raws[i]="user name: "+f_name[i]+" "+l_name[i]+" date:"+date[i]+"\n"+
                    "bought: "+credit[i]+" credits ";
                }
                callback.setList(purchase_raws);
                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getpurchases.start();
    }
}
