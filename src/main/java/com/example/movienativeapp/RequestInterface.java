package com.example.movienativeapp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eli on 19/07/2015.
 *
 * interface for handeling callback from rest server response
 */
public interface RequestInterface {
    JSONObject onRecive(Callback callback);
}
