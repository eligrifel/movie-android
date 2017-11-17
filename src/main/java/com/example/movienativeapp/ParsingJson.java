package com.example.movienativeapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONException;
import org.json.JSONObject;

public class ParsingJson {

    private JSONObject _Json;
    private String[] _Element ;
    private String [] _Property ;
    private String[][]  jsonArray ;


    public ParsingJson(JSONObject json , String key)
    {
        _Json = json;
        _Element = new String [json.length()-1];
        for(int i=0;i<json.length()-1;i++)
        {
            try {
                _Element[i]=json.getString(key+i);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    public String []getElement()
    {
        System.out.println(_Element.toString());
        return _Element;
    }
}