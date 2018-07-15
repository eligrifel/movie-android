package com.example.movienativeapp;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eli on 24/07/2015.
 */
public class RestRespond {
    private String BaseUrl="http://192.168.2.8:8080/";
    static String _username;
    static String _password;
    public RestRespond() {

    }

    public JSONObject getJsonFromSerever(String command) throws JSONException {
        JSONObject json = null;

        switch (command) {
            case "getData": {

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

                String js = "{\"cat0\":\"Horor\",\"cat1\":\"Advanture\",\"cat2\":\"Action\",\"cat3\":\"Drama\",\"cat4\":\"doco\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getFMovies": {

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

                String js = "{\"r0\":\"5\",\"r1\":\"3.5\",\"r2\":\"5\",\"r3\":\"3.5\",\"r4\":\"5\",\"r5\":\"4.0\",\"r6\":\"4.5\"}";
                try {
                    json = new JSONObject(js);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;

            case "getFMoviesPath": {
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

        return json;
    }

    public boolean IsReviewed(String username, String movieTitle) {
        return false;
    }

    public void sendReview(String userName, int reviewRating) {
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
            url = new URL(BaseUrl+"users");

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
            Parcer jParcer= new Parcer(json);
           map= jParcer.JasonToMap();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    public Callback getData(String[] args){ //args parms @methodurl,@url,@parameters

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
        String RestUrl= (_args[2]==null)?BaseUrl+_args[1].toString():BaseUrl+_args[1].toString()+"/"+_args[2];
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



            }
            if(sb.charAt(0)=='{')
            {
                json=new JSONObject(sb.toString());
            }
            else
            {
                json=new JSONArray(sb.toString());
            }
            // json = new JSONObject(sb.toString());
            Parcer jParcer= new Parcer(json);
            map= jParcer.JasonToMap();
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
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    public Callback getData(String[] args,HashMap<String, String> postDataParams){ //args parms @methodurl,@url,@parameters ,Post Params
        StringBuilder sb = new StringBuilder();
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

        Callback callback;
        String RestUrl= (_args[2]==null)?BaseUrl+_args[1].toString():BaseUrl+_args[1].toString()+"/"+_args[2];
        Object json = null;
        ArrayList map = null;
        try {
            url = new URL(RestUrl.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod(request_method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);



                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            BufferedReader br;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
            } else {
     /* error from server */
                br = new BufferedReader(new InputStreamReader((urlConnection.getErrorStream())));
            }



            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);



            }



        } catch (Exception e) {
            e.printStackTrace();
        }



       
if(sb!=null) {

    if (sb.charAt(0) == '{') {
        try {
            json = new JSONObject(sb.toString());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    } else {
        try {
            json = new JSONArray(sb.toString());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    Parcer jParcer = new Parcer(json);
    map = jParcer.JasonToMap();

}
        callback = new Callback(args,map);
        return callback;
    }
}