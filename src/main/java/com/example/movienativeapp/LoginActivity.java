package com.example.movienativeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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


        String[] arra1=new String[3];
        arra1[0]="login";
        arra1[1]=username;
        arra1[2]=password;

        RequestInterface Serverlistener = new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {


               //JSONObject myJsonObject=json;
              //  Log.d("printArray",a.get("user_name").toString());
                loginCallback(callback);
                return null;
            }
        };
        RequestListenerHolder con_server = new RequestListenerHolder(arra1);
        con_server.addListener(Serverlistener);
        con_server.getLogin(arra1);


    }

public void loginCallback(Callback callback){
    ArrayList<HashMap<String, String>> dataLists = callback.get_dataList();
    String[] _args = callback.get_data();
   HashMap<String,String> map = dataLists.get(0);
    String S_username=_args[1];
    String S_password =_args[2];
    String clientUserName=map.get("user_name");
    String clientPassowrd = map.get("password");

    if((S_username.equals(clientUserName)&&(S_password.equals(clientPassowrd))))
    {
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
