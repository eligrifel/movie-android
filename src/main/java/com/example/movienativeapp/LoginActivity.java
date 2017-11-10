package com.example.movienativeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lionel on 20/10/2017.
 */

public class LoginActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private Context context;
    private TextView password;
    private TextView email;
    private Button login;

    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;
        password = (TextView) findViewById(R.id.password);
        email = (TextView) findViewById(R.id.email);
        login = (Button) findViewById(R.id.loginButton);
        login.setVisibility(View.INVISIBLE);


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().equals("") && !email.getText().equals("")) {
                    login.setVisibility(View.VISIBLE);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authenticate(email.getText().toString(), password.getText().toString());
            }
        });


    }

    private void authenticate(String username, String password) {
        class login implements Runnable {
            String _username;
            String _password;

            login(String username, String password) {
                _username = username;
                _password = password;
            }


            public void run() {
                ServerDummy testserver = new ServerDummy();
                testserver.connctToServer(_username,_password);
            }
        }

        Thread t = new Thread(new login(username,password));
       // t.start();

        String[] arra1=new String[3];
        arra1[0]="test";
        arra1[1]=username;
        arra1[2]=password;

        JsonListener listener = new JsonListener() {
            @Override
            public JSONObject onRecive(JSONObject json) {

               JSONObject myJsonObject=json;

                for(int i=0;i<myJsonObject.length()-1;i++)
                {
                    try {
                        System.out.println(" this is the json array"+myJsonObject.names().getString(i).toString()+" "+ myJsonObject.get(myJsonObject.names().getString(i)));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                return null;
            }
        };
        ListenerHolder con_server = new ListenerHolder(arra1);
        con_server.addListener(listener);
        con_server.getJson(arra1);










        if (username.equals("a") && password.equals("a")){
            Intent intent = new Intent(this,Main.class);
            //String message = "something";
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        else{
            this.password.setText("");
            this.email.setText("");
        }


    }



}
