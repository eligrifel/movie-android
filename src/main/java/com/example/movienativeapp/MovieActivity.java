package com.example.movienativeapp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

import listAdapters.CommentListAdapter;
import listAdapters.MoviesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import view.MyPagerAdapter;
import view.SlidingTabLayout;
import view.Tab1;
import view.Tab1.OncategoryViewListener;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.movienativeapp.Main.SamplePagerItem;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

public class MovieActivity extends ActionBarActivity{

	
	private boolean connected=false;
	private WLClient myClient;
	private ServerDummy serverDummy;
	private Context context;
	Handler handler;
	
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
   
///
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       

        setContentView(R.layout.activity_main);
        serverDummy = new ServerDummy();
       
        handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));
        Bundle bundle = getIntent().getExtras();
        
        actionBar.setTitle(bundle.getString("category"));
        
        
        
        
        
       
        

        
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

        return super.onOptionsItemSelected(item);
    }

    public void act2(View view) {
        actionBar.hide();


    }

   
    
   
    private void connect() {
		context = this;

			preperMovieArrays("getFMovies");


	}





private void preperMovieArrays(String method) {
		
		
		final String [] movies;
		

	JSONObject json=null;
	try {
		 json = serverDummy.getJsonFromSerever(method);
	} catch (JSONException e) {
		e.printStackTrace();
	}

				moviesArray=new ParsingJson(json, "m").getElement();

	JSONObject json1=null;
	try {
		 json1 = serverDummy.getJsonFromSerever("getFMoviesPath");
	} catch (JSONException e) {
		e.printStackTrace();
	}
//
						moviesPathArray=new ParsingJson(json1, "p").getElement();

	JSONObject json2=null;
	try {
		 json2 = serverDummy.getJsonFromSerever("getRating");
	} catch (JSONException e) {
		e.printStackTrace();
	}
								TopRatedRating=new ParsingJson(json2, "r").getElement();
								getMovieLIst(moviesArray,moviesPathArray,TopRatedRating,R.id.general_list_view);

		
		
		
	}
public void getMovieLIst(final String[] movies,final String[] path,final String[] toprating ,final int list)
{


			handler.post(new Runnable() {
				
				@Override
				public void run() {						
					ListView myListView1 = (ListView)findViewById(list);
					
					myListView1.setAdapter(new MoviesListAdapter(movies,path,toprating,context));
					
					myListView1.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							
							Intent movie = new Intent(context , SingleMovieOption.class);
							TextView moviename=(TextView)view.findViewById(R.id.movie_name);
						String name = moviename.getText().toString();
						movie.putExtra("name", name);
						movie.putExtra("path", path[position]);
						context.startActivity(movie);
						
						}
						
					});
					
				}
			});
							 
		}




}
