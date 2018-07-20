package com.example.movienativeapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import listAdapters.CommentListAdapter;
import listAdapters.MoviesListAdapter;
import listAdapters.MoviesListAdapterObject;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.movienativeapp.Main.SamplePagerItem;
//import com.worklight.wlclient.api.WLClient;

public class MovieActivity extends AppCompatActivity {

	
	private boolean connected=false;
//	private WLClient myClient;
	private RestRespond restRespond;
	private Context context;
	Handler handler;
	String method;
    String role;
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
   
///
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;
    private String[] movieId;
	final RequestListenerHolder _serverRec = new RequestListenerHolder();
	private  RequestInterface _listener;
    private String category_id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleCallBack();
        restRespond = new RestRespond();
       
        handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));
        Bundle bundle = getIntent().getExtras();
        
        actionBar.setTitle(bundle.getString("category"));
        role=bundle.getString("role");
        category_id = bundle.getString("category_id");
        
        
        
        
       
        

        
        connect();
        




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        if(id==R.id.go_to_admin_panel&&role.equals("0"))
        {
            Intent intent = new Intent(this,AdminActivity.class);
            startActivity(intent);
            return false;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"permition denied, you are not Admin",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void act2(View view) {
        actionBar.hide();
//        Intent intent = new Intent(this,Category.class);
//        startActivity(intent);

    }
    private void connect() {
		context = this;

			//preperMovieArrays("getFMovies");
      // _serverRec.getAllMovies();
        //testing get category movies
        UserRequest req = new UserRequest();
        req.getMoviesForCategory(category_id, _listener);

	}






public void getMovieLIst(final Movie[] movielist,final int list)
{

    if(movielist!=null&&movielist.length>0){
			handler.post(new Runnable() {
				
				@Override
				public void run() {						
					ListView myListView1 = (ListView)findViewById(list);
					
					myListView1.setAdapter(new MoviesListAdapterObject(movielist,R.layout.single_row_movie_list,context));
					
					myListView1.setOnItemClickListener(new OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent,
                                                        View view, int position, long id) {

                                    Intent movie = new Intent(context , SingleMovieOption.class);
                                    Movie single_movie =movielist[position];
                                    movie.putExtra("name", single_movie.get_name());
                                    movie.putExtra("path", single_movie.getUrl());
                                    movie.putExtra("movie_id",single_movie.getId());
                                    movie.putExtra("info",single_movie.getInfo());

                                    float movie_rating=Float.parseFloat(movielist[position].getRating()) ;

                                    movie.putExtra("rating",movie_rating);
						context.startActivity(movie);
						
						}
						
					});
					
				}
			});}
							 
		}

    private void handleCallBack() {
        final ArrayList<HashMap<String,String>> mapList;


        _listener= new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                final ArrayList<HashMap<String,String>> mapList;

                method = callback.get_data()[1];
                mapList = callback.get_dataList();

                switch(method)
                {

                    case "movies":
                    {

                        getMovieLIst(callback.getMoviesList(),R.id.general_list_view);


                    }
                    break;
                    case "movies/categories":
                    {
//

                        getMovieLIst(callback.getMoviesList(),R.id.general_list_view);
                    }
                    break;
                }


                return null;
            }
        };

        _serverRec.addListener(_listener);
    }



}
