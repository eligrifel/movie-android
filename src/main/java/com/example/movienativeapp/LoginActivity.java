package com.example.movienativeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lionel on 20/10/2017.
 */

public class LoginActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private Context context;
    private TextView password;
    private TextView email;
    private Button login;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;
        password =  (TextView) findViewById(R.id.password);
        email =  (TextView) findViewById(R.id.email);
        login = (Button) findViewById(R.id.loginButton);
        login.setVisibility(View.INVISIBLE);


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().equals("") &&  !email.getText().equals("")){
                    login.setVisibility(View.VISIBLE);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate("a@a.com","a");
            }
        });


    }

    private void authenticate(String username, String password){
        if (username.equals("a@a.com") && password.equals("a")){
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
