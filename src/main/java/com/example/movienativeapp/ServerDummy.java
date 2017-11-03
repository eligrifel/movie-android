package com.example.movienativeapp;

import android.util.Base64;
import android.util.Log;

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

/**
 * Created by eli on 24/07/2015.
 */
public class ServerDummy {

    public ServerDummy() {

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

    public int connctToServer(String username, String password) {
        StringBuilder sb = new StringBuilder();
        URL url;
        String auth = username + ":" + password;
        final String basicAuth = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        try {
            url = new URL("http://192.168.2.6:8080/users");

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
        Log.d("auth",sb.toString()+"here");
        return 0;
    }
}