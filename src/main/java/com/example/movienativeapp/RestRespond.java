package com.example.movienativeapp;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eli on 24/07/2015.
 */
public class RestRespond {
    static String _username;
    static String _password;
    public RestRespond() {

    }

    public JSONObject getJsonFromSerever(String command) throws JSONException {
        JSONObject json = null;

        switch (command) {
            case "getData": {

                System.out.println("here in " + command);
                String js = "{\"efjks@gmail.com\":\"this movie is great, good action " +
                        "and lots of effects\",\"sharon@gmail.com\":\"Eli\",\"dorit@gmail.com\":\"comment2\",\"amos@gmail.com\":\"Eli i\",\"hag@gmail.com\":\"comment3\",\"harry@gmail.com\":\"Eli is te best\",\"thegoal@gmail.com\":\"commen32\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getCat": {
                System.out.println("here in " + command);
                String js = "{\"cat0\":\"Horor\",\"cat1\":\"Advanture\",\"cat2\":\"Action\",\"cat3\":\"Drama\",\"cat4\":\"doco\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getFMovies": {
                System.out.println("here in " + command);
                //getting movvies array of maps

                String js = "{\"m0\":\"first movie\",\"m1\":\"2first movie\",\"m2\":" +
                        "\"second movie\",\"m3\":\"3th movie\",\"m4\":\"4th movie\",\"m5\":\"the end\"," +
                        "\"m6\":\"last movie\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getRating": {
                System.out.println("here in " + command);
                String js = "{\"r0\":\"5\",\"r1\":\"3.5\",\"r2\":\"5\",\"r3\":\"3.5\",\"r4\":\"5\",\"r5\":\"4.0\",\"r6\":\"4.5\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getFMoviesPath": {
                System.out.println("here in " + command);
                String js = "{\"p0\":\"http://ia.media-imdb.com/images/M/MV5BMTQ5MTE0MTk3Nl5BMl5BanBnXkFtZTgwMjczMzk2NTE@._V1._SY140_.jpg\",\n" +
                        "\t\t\"p1\":\"http://farm4.staticflickr.com/3777/9049174610_bf51be8a07_s.jpg\",\n" +
                        "\t\t\"p2\":\"http://farm4.staticflickr.com/3810/9046947167_3a51fffa0b_s.jpg\",\n" +
                        "\t\t\"p3\":\"http://farm8.staticflickr.com/7324/9046946887_d96a28376c_s.jpg\",\n" +
                        "\t\t\"p4\":\"http://farm3.staticflickr.com/2828/9046946983_923887b17d_s.jpg\",\n" +
                        "\t\t\"p5\":\"http://farm4.staticflickr.com/3773/9049175264_b0ea30fa75_s.jpg\",\n" +
                        "\t\t\"p6\":\"http://farm4.staticflickr.com/3781/9046945893_f27db35c7e_s.jpg\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

        }

        System.out.println("here in so no json sssssssssssssssss");
        return json;
    }

    public boolean IsReviewed(String username, String movieTitle) {
        return false;
    }

    public void sendReview(String userName, int reviewRating) {
        Log.d("review sends", "review sends by:" + userName);
    }

    public ArrayList connctToServer(String username, String password) {
        _username=username;
        _password=password;
        StringBuilder sb = new StringBuilder();
        URL url;
        ArrayList map = null;
        String auth = username + ":" + password;
        final String basicAuth = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        try {
            url = new URL("http://10.0.0.23:8090/users");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        sb.toString();

        JSONObject json=null;
        try {
            json = new JSONObject(sb.toString());
            JsonToArraylist jParcer= new JsonToArraylist(json);
           map= jParcer.JasonToMap();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("auth",sb.toString()+"here");
        return map;
    }

    public Callback getData(String[] args){ //args parms @methodurl,@parmsourl
        String[] _args = new String[3];


       String username=_username;
       String  password=_password;

        String auth = username + ":" + password;
        final String basicAuth = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        String request_method= args[0];
        _args[1]=args[1];
        if (args[2]!=null)
        {
        _args[2]=args[2];
        }
        URL url;;
        StringBuilder sb = new StringBuilder();

    Callback callback;
    String BaseUrl = "http://10.0.0.23:8090/";
        String RestUrl=BaseUrl+_args[1].toString()+"/"+_args[2];
        Object json;
        ArrayList map = null;
        try {
            url = new URL(RestUrl.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod(request_method);
            BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
                if(sb.charAt(0)=='{')
                {
                    json=new JSONObject(sb.toString());
                }
                else
                {
                    json=new JSONArray(sb.toString());
                }
               // json = new JSONObject(sb.toString());
                JsonToArraylist jParcer= new JsonToArraylist(json);
                map= jParcer.JasonToMap();
                Log.d("here","hwew");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callback = new Callback(args,map);
        return callback;
    }
}