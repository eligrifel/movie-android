package com.example.movienativeapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eli on 19/07/2015.
 */
public class ListenerHolder {
    private String _args[];
    public ListenerHolder()
    {

    }
public ListenerHolder(String args[]){
    _args=args;
}
    private List<JsonListener> listeners = new ArrayList<JsonListener>();

    public void addListener(JsonListener toAdd) {
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
                ServerDummy serverDummy = new ServerDummy();
                JSONObject json = null;
                try {

                    json = serverDummy.getJsonFromSerever(_command);
                    Log.d("dataTest",_command);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Notify everybody that may be interested.
                if(json==null)
                {
                    Log.d("dataTest","jason is null");
                }
                for (JsonListener hl : listeners)
                    hl.onRecive(json);
            }
        });

test.start();
    }

    public void getJson(String[] args) {
        final String _command= _args[0];
        final String _username=_args[1];
        final String _password=_args[2];
Log.d("listeer",_args[1].toString()+_args[2].toString());
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerDummy serverDummy = new ServerDummy();
                JSONObject json = null;

                json = serverDummy.connctToServer(_username,_password);


                // Notify everybody that may be interested.
                if(json==null)
                {
                    Log.d("dataTest","jason is null");
                }
                for (JsonListener hl : listeners)
                    hl.onRecive(json);
            }
        });

        test.start();
    }
    public String[] get_args(){

        return _args;
    }
}

