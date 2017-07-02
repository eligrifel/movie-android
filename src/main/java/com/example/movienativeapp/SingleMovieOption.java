package com.example.movienativeapp;

import java.io.IOException;
import java.net.URL;
import java.util.List;






import listAdapters.CommentListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import view.Tab1;

import com.example.movienativeapp.Main.SamplePagerItem;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SingleMovieOption extends ActionBarActivity {
	private boolean connected=false;
	private WLClient myClient;
	TextView _movieName;
	String _imagePath;
	private Context context;
	Handler handler;
	final ListenerHolder holderr = new ListenerHolder();
	private JSONObject json;
	
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
   
///
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_movie_option);
		_movieName=(TextView) findViewById(R.id.TVmovieName);
		handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));
        Bundle bundle = getIntent().getExtras();
        ImageView _movieImage = (ImageView) findViewById(R.id.movie_pic);
        actionBar.hide();
        Bundle moviebundle ;
        moviebundle = getIntent().getExtras();
        String moviename = moviebundle.getString("name");
        String pathString = moviebundle.getString("path");
        System.out.println("movie choosen is "+ moviename);
        _movieName.setText(moviename.toString());
        System.out.println("psth of movie is....."+pathString);
        _imagePath = pathString;
        new DownloadAsyncTask().execute(_movieImage);
        connect();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_movie_option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class DownloadAsyncTask extends AsyncTask<ImageView, Void, ImageView> {
		private Bitmap bit;
		
		public DownloadAsyncTask()
		{
			
			super();
			
			
		}
		
		@Override
		protected ImageView doInBackground(ImageView... params) {
			// TODO Auto-generated method stub
			//load image directly
			ImageView viewHolder = params[0];
			try {
				
				URL imageURL = new URL(_imagePath);
				bit = BitmapFactory.decodeStream(imageURL.openStream());
				
			} catch (IOException e) {
				// TODO: handle exception
				Log.e("error", "Downloading Image Failed");
				
			}
			
			return viewHolder;
		}
		
		@Override
		protected void onPostExecute(ImageView result) {
			// TODO Auto-generated method stub
			if (bit == null) {
				//result.image.setImageResource(R.drawable.postthumb_loading);
			} else {
				result.setImageBitmap(bit);
				
				
			}
		}
	}
	
	 public void getComments()
		{
			System.out.println("comment has being pressed");
			holderr.addListener(new JsonListener() {
				@Override
				public JSONObject onRecive(final JSONObject myJsonObject) {
					json=myJsonObject;
					for(int i=0;i<myJsonObject.length()-1;i++)
					{
						try {
							System.out.println(" this is the json array "+myJsonObject.names().getString(i).toString()+" "+ myJsonObject.get(myJsonObject.names().getString(i)));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					handler.post(new Runnable() {

						@Override
						public void run() {

							ListView myListView = (ListView) findViewById(R.id.LV_comments);

							myListView.setAdapter(new CommentListAdapter(myJsonObject, context));

						}
					});

					return null;
				}
			});
			holderr.getJson("getData");
	    	
//			myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getData"), new WLResponseListener() {
//
//				@Override
//				public void onSuccess(final WLResponse arg0) {
//					System.out.println("in success");
//					//manage json pbject
//					final JSONObject myjason = arg0.getResponseJSON();
//
//					for(int i=0;i<myjason.length()-1;i++)
//					{
//						try {
//							System.out.println(" this is the json array "+myjason.names().getString(i).toString()+" "+ myjason.get(myjason.names().getString(i)));
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//					handler.post(new Runnable() {
//
//						@Override
//						public void run() {
//							ListView myListView = (ListView)findViewById(R.id.LV_comments);
//							myListView.setAdapter(new CommentListAdapter(myjason,context));
//						}
//					});
//
//				}
//
//				@Override
//				public void onFailure(WLFailResponse arg0) {
////
//					System.out.println("in fail");
//				}
//			});
			
			
			
			
		}
	  private void connect() {
			context = this;
			//myClient = WLClient.createInstance(this);
			//myClient.connect(new WLResponseListener() {

			//	@Override
		//		public void onSuccess(WLResponse arg0) {
				connected=true;
				//testing 
				
				
				
				
				getComments();
				
				
				


				


		}
	
}
