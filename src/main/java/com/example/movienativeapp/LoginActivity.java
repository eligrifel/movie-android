package com.example.movienativeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eli levi on 20/10/2017.
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
    if(callback.get_dataList()==null)
    {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        Toast.makeText(getApplicationContext(),"Wrong user name or password",Toast.LENGTH_LONG).show();
    }
});

        return;
    }
   HashMap<String,String> map = dataLists.get(0);
    String S_username=_args[1];
   // String S_password =_args[2];
    String clientUserName=map.get("user_name");
   // String clientPassowrd = map.get("password");

    if((S_username.equals(clientUserName)))
    {
        Intent intent = new Intent(this,Main.class);
       startActivity(intent);
    }
    else{
        this.password.setText("");
        this.email.setText("");

    }
}
public void RegisterUser(View view)
{
//vars
    final String[] Tmessage = new String[1];
    String user_name = ((EditText) findViewById(R.id.ET_user_name)).getText().toString();
    String first_name= ((EditText) findViewById(R.id.ET_first_name)).getText().toString();
    String last_name= ((EditText) findViewById(R.id.ET_last_name)).getText().toString();
    String password= ((EditText) findViewById(R.id.ET_signin_password)).getText().toString();
    String payment_token= ((EditText) findViewById(R.id.ET_paymentToken)).getText().toString();
    if((user_name.isEmpty()) || (first_name.isEmpty())||last_name.isEmpty()|| password.isEmpty()||payment_token.isEmpty())
    {
        Toast.makeText(this,"please fill all require field",Toast.LENGTH_LONG).show();
    }
else
    {

        UserRequest req = new UserRequest();
        HashMap <String,String> postData = new HashMap<>();
        postData.put("user_name",user_name);
        postData.put("password",password);
        postData.put("first_name",first_name);
        postData.put("last_name",last_name);
        postData.put("payment_token",payment_token);
        req.createUser(postData,new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                //check the response and float success message
            String response =callback.getRespondFromServer();
                if((response.equals("1"))){
                    Tmessage[0] ="user added succesfully you can login";



                }
                else
                if(response!=null)
              Tmessage[0]=response;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,Tmessage[0],Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }
        });
    }

}
}
