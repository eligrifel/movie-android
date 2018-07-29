package com.example.movienativeapp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * interface for handeling callback from rest server response
 */
public interface RequestInterface {
    JSONObject onRecive(Callback callback);
}
