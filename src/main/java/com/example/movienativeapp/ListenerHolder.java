package com.example.movienativeapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eli on 19/07/2015.
 */
public class ListenerHolder {

    private List<JsonListener> listeners = new ArrayList<JsonListener>();

    public void addListener(JsonListener toAdd) {
        listeners.add(toAdd);
    }
    public void removeListener()
    {
        listeners.clear();
    }

    public void getJson(String command) {
        JSONObject json = null;
        System.out.println("getting json from server!!");
       ServerDummy serverDummy = new ServerDummy();
        try {
            json = serverDummy.getJsonFromSerever(command);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Notify everybody that may be interested.
        for (JsonListener hl : listeners)
            hl.onRecive(json);

    }
}

