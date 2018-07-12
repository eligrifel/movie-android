package com.example.movienativeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by eli on 11/11/2017.
 */

public class Parcer {

    JSONArray json_a;

public Parcer()
{

}

    public Parcer(Object jsonobject) {

        if (jsonobject instanceof JSONObject) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonobject);
            json_a = jsonArray;
        }
        else if(jsonobject instanceof JSONArray){
            json_a = (JSONArray) jsonobject;

        }

    }


    public ArrayList JasonToMap() {
        ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        if(json_a!=null) {
            int size = json_a.length();
            for (int i = 0; i < size; i++) {
                try {
                    JSONObject jObject = json_a.getJSONObject(i);

                    Iterator<?> keys = jObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String value = jObject.getString(key);
                        if(!isJson(value))
                        {

                            map.put(key, value);
                        }
                        else
                        {
                            JSONObject obj = new JSONObject(value);
                           Merge(mapList, jsonToMap((JSONObject) obj));
                        }




                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mapList.add(map);
                map = new HashMap<String, String>();
            }
        }
        return mapList;
    }

  ArrayList<HashMap> jsonToMap(JSONObject Jobj) {
      ArrayList<HashMap> arrayHash = new ArrayList<>();
      HashMap<String, String> map = new HashMap();
      Iterator<?> keys = Jobj.keys();
      while (keys.hasNext()) {
          String key = (String) keys.next();
          Object obj = null;
          try {
              obj = Jobj.getString(key);
              if (obj instanceof String) {
                  String value = Jobj.getString(key);
                  map.put(key, value);
              } else//is jobject
              {
                  arrayHash=Merge(arrayHash,jsonToMap((JSONObject) obj));
              }

          } catch (JSONException e) {
              e.printStackTrace();
          }

      }
      arrayHash.add(map);
      return arrayHash;
  }

  private ArrayList Merge(ArrayList source,ArrayList toadd)
  {
      ArrayList _source=source;
      ArrayList _toadd=toadd;
      {
          for (int i=0;i<_toadd.size();i++){
           _source.add(_toadd.get(i));
          }
      }
      return _source;

  }
            //retrive the list as String Array  form given arraylist o map
    //
    public String[] getListValue(ArrayList<HashMap<String, String>> dataList, Boolean bool) {
        int counter=0;
        HashMap<String, String> map;
        map = dataList.get(0);
        String [] keys = new String[map.size()];
        String [] values = new String [map.size()];

        for (String key : map.keySet()) {
            String mapkey = key;
            String mapValue = map.get(key);
            keys[counter]=key;
            values[counter]=mapValue;
            counter++;
        }
        if(bool)
        {
          return values;
        }
        else
        {
return  keys;
        }

    }
//get list of field params
public String[] getFieldArray (ArrayList<HashMap<String,String>> hash_list,String field ){
    ArrayList<HashMap<String,String>> newhash_list;
   newhash_list= reduceSizeByField(hash_list,field);
    int size = newhash_list.size();
    String key=field;
    String [] result = new String[size];
    HashMap<String,String> map ;
    for (int i=0;i<size;i++)
    {
     map=newhash_list.get(i);
                if(map.get(field)!=null)
            result[i]=map.get(field);

    }


    return result;
}

    private ArrayList<HashMap<String,String>> reduceSizeByField(ArrayList<HashMap<String, String>> hash_list, String field) {
        int size = hash_list.size();
        ArrayList tempHash= new ArrayList(hash_list);
        int location=0;
        for( int i=0;i<size;i++)
        {
            HashMap temp = (HashMap) tempHash.get(location);
            if (temp.size()==0||temp.get(field)==null)
            {
                tempHash.remove(location);
            }
            else{
                location ++;
            }
        }

       return tempHash;
    }

    private boolean isJson(String s)
    {
        try {
            JSONObject json = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
}