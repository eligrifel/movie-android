package com.example.movienativeapp;

import org.json.JSONObject;

/**
 * Created by eli on 19/07/2015.
 */
public interface JsonListener {
    JSONObject onRecive(JSONObject json);
}
