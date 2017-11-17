package com.example.movienativeapp;

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
}
