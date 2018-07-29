package com.example.movienativeapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * this class holds user request method from the server
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

    public void removeListener() {
        listeners.clear();
    }


    public void getCurrentUser(final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "users";
        final String controller_path = _args[1];


        Thread getCat = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {
                    returninter.onRecive(callback);
                }

            }
        });

        getCat.start();
    }

    public void getReviewsByMovieId(int movie_id, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "leaser/" + movie_id;
        Thread getCat = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {
                    returninter.onRecive(callback);
                }

            }
        });

        getCat.start();

    }

    public void getReviewsByMovieId(final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "leaser";
        Thread getCat = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {
                    returninter.onRecive(callback);
                }

            }
        });

        getCat.start();

    }


    public void getMoviesForCategory(String movie_id, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "movies/categories";
        _args[2] = movie_id;
        if (movie_id.equals("0")) {
            _args[2] = "";
            _args[1] = "movies";
        }


        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }

    public void getRentedMoviesByUser(String user_id, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "movies/categories";
        _args[2] = user_id;


        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }

    //get current user rented movies
    public void getRentedMovies(final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "leaser";


        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }

    public void createUser(final HashMap postData, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "POST";
        _args[1] = "newuser";


        Thread createUser = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;


                callback = restRespond.getData(_args, postData);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        createUser.start();
    }

    //return current user rented movie
    public void returnMovie(String movie_id, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "DELETE";
        _args[1] = "leaser";
        _args[2] = movie_id;


        Thread returnMovie = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        returnMovie.start();
    }

    //rent a movie
    public void rentMovie(String movie_id, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "PUT";
        _args[1] = "leaser";
        _args[2] = movie_id;


        Thread returnMovie = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        returnMovie.start();
    }

    public void updateRating(String movie_id, String comment, String newRating, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "POST";
        _args[1] = "reviews";
        _args[2] = movie_id;
        final HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("comment", comment);
        postData.put("rating", newRating);


        Thread createUser = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;


                callback = restRespond.getData(_args, postData);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        createUser.start();
    }

    public void buyCredit(String NumberOfCredits, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "POST";
        _args[1] = "credits";

        final HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("amount", NumberOfCredits);


        Thread buyCredit = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;


                callback = restRespond.getData(_args, postData);
                map = callback.get_dataList();


                if (map != null) {

                    returninter.onRecive(callback);
                }
            }
        });

        buyCredit.start();
    }

    public void getPurchaseHistory(final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "credits";


        Thread getHistory = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();
                Parcer parcer = new Parcer();
                String[] amounts = parcer.getFieldArray(map, "amount");
                String[] date = parcer.getFieldArray(map, "date");
                String[] PurchaseRows = new String[amounts.length];
                for (int i = 0; i < amounts.length; i++) {
                    PurchaseRows[i] = "date: " + date[i] + " " + amounts[i] + " credits";
                }
                callback.setList(PurchaseRows);
                if (map != null) {
                    returninter.onRecive(callback);
                }

            }
        });

        getHistory.start();
    }

    public void getMoviesSearchResults(String search_patern, final RequestInterface returninter) {
        _args = new String[3];
        _args[0] = "GET";
        _args[1] = "search";
        _args[2] = "";
        String[] expres = search_patern.split("\\s+");
        for (int i = 0; i < expres.length; i++) {
            if (i == 0)
                _args[2] += expres[i];
            else
                _args[2] += "," + expres[i];
        }


        Thread getmovies = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map = callback.get_dataList();

                if (map != null) {
                    Movie movies[] = callback.getMoviesList();
                    callback.setList(movies);
                    returninter.onRecive(callback);
                }
            }
        });

        getmovies.start();
    }
}
