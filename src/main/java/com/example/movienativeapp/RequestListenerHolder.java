package com.example.movienativeapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eli on 19/07/2015.
 */
public class RequestListenerHolder {

    private String _args[];
    private Callback callback;
    public RequestListenerHolder()
    {

    }
public RequestListenerHolder(String args[]){
    _args=args;

}
    private List<RequestInterface> listeners = new ArrayList<RequestInterface>();

    public void addListener(RequestInterface toAdd) {
        listeners.add(toAdd);
    }
    public void removeListener()
    {
        listeners.clear();
    }

    public void getJson(String command) {
final String _command= command;
        System.out.println("getting json from server!!");
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                JSONObject json = null;
                try {

                    json = restRespond.getJsonFromSerever(_command);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Notify everybody that may be interested.
            Parcer jToArray = new Parcer(json);
               ArrayList dataArrayList= jToArray.JasonToMap();
                //create callback object
                callback= new Callback(_args,dataArrayList);
                for (RequestInterface hl : listeners)
                    hl.onRecive( callback);
            }
        });

test.start();
    }

    public void getLogin(String[] args) {
        final String _command= _args[0];
        final String _username=_args[1];
       final String _password=_args[2];

        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> json = null;

                json = restRespond.connctToServer(_username, _password);


                // Notify everybody that may be interested.
//                if(json==null)
//                {
//                    Log.d("dataTest","jason is null");
//                }


                    Callback callback = new Callback(_args, json);
                    for (RequestInterface hl : listeners)
                        hl.onRecive(callback);



            }
        });

        test.start();
    }
    public String[] get_args(){

        return _args;
    }
    public void severRequest(String command) {
        final String _command= command;
        System.out.println("getting json from server!!");
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                JSONObject json = null;
                try {

                    json = restRespond.getJsonFromSerever(_command);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Notify everybody that may be interested.
                Parcer jToArray = new Parcer(json);
                ArrayList dataArraylist= jToArray.JasonToMap();
                Callback callback= new Callback(_args,dataArraylist);
                for (RequestInterface hl : listeners)
                    hl.onRecive(callback);
            }
        });

        test.start();
    }

    public void getComments(final String[] args) {
        final String _command= _args[0];
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(args);
                map=callback.get_dataList();



                if (map != null) {
                    for (RequestInterface hl : listeners)
                        hl.onRecive(callback);
                }
            }
        });

        test.start();
    }

    public void getReviewByMovieId(final String[] args) {

        final String controller_path= args[1];
        final String controller_parameter= args[2];

        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(args);
                map=callback.get_dataList();



                if (map != null) {
                    for (RequestInterface hl : listeners)
                        hl.onRecive(callback);
                }
            }
        });

        test.start();
    }
public  void getCategoryList(){
    _args = new String[3];
    _args[0]="GET";
    _args[1]="movies/categories";
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
                for (RequestInterface hl : listeners)
                    hl.onRecive(callback);
            }
        }
    });

    getCat.start();
}

    public  void getAllMovies(){
        _args = new String[3];
        _args[0]="GET";
        _args[1]="movies";
        final String controller_path= _args[1];


        Thread queryData = new Thread(new Runnable() {
            @Override
            public void run() {
                RestRespond restRespond = new RestRespond();
                ArrayList<HashMap<String, String>> map = null;
                Callback callback;
                callback = restRespond.getData(_args);
                map=callback.get_dataList();



                if (map != null) {
                    for (RequestInterface hl : listeners)
                        hl.onRecive(callback);
                }
            }
        });

        queryData.start();
    }

}

