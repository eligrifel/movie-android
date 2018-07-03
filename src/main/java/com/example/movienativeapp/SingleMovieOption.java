package com.example.movienativeapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import org.json.JSONObject;

import com.example.movienativeapp.Main.SamplePagerItem;
//import com.worklight.wlclient.api.WLClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import fragments.write_review;
import listAdapters.CommentListAdapter;

public class SingleMovieOption extends ActionBarActivity {
    private Callback callback;
	private boolean connected=false;
//	private WLClient myClient;
Bundle moviebundle ;
	String movie_id;
	TextView _movieName;
	TextView _movieInfo;
	String _imagePath;
	RatingBar _movieRating;
	private Context context;
	Handler handler;
	final RequestListenerHolder serverRec = new RequestListenerHolder();
	private JSONObject json;
	private RequestInterface Interface;
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
	UserRequest req;
   
///
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;

	//fragment property

	FragmentManager fm ;
	FragmentTransaction fragmentTransaction ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_movie_option);
        handleCallBack();
		_movieName=(TextView) findViewById(R.id.TVmovieName);
		_movieInfo=(TextView) findViewById(R.id.TV_infoTextBox);
		_movieRating=(RatingBar) findViewById(R.id.ratingBar1);
		handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));
        Bundle bundle = getIntent().getExtras();
        ImageView _movieImage = (ImageView) findViewById(R.id.movie_pic);
        actionBar.hide();

        moviebundle = getIntent().getExtras();
        final String moviename = moviebundle.getString("name");
		movie_id=moviebundle.getString("movie_id");
        final String pathString = moviebundle.getString("path");
		final float rating= moviebundle.getFloat("rating");
        System.out.println("movie choosen is "+ moviename);
        System.out.println("psth of movie is....."+pathString);
        _imagePath = pathString;
  		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_movieInfo.setText(moviebundle.getString("info"));
				_movieName.setText(moviename);
				_movieRating.setRating(rating);
			}
		});

        new DownloadAsyncTask().execute(_movieImage);
        connect();
		setListeners();
	}

	private void setListeners() {
		findViewById(R.id.give_review);

	}
	public void onGiveRevieClick(View view) {

		fragments.write_review rfragment;


		fm = getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		Fragment f= fm.findFragmentByTag("review");
		if(f==null) {
			rfragment = new write_review();


		}
		else
			rfragment = (write_review) f;
		fragmentTransaction.replace(R.id.test, rfragment, "review").commit();
		getSupportFragmentManager().executePendingTransactions();

		ScrollView sv = (ScrollView) rfragment.getView().getRootView().findViewById(R.id.SV_singleMovie);
		sv.fullScroll(View.FOCUS_DOWN);

	}

	public void updateReview(View view) {
//remove fragment
		fragments.write_review fragment = (fragments.write_review)fm.findFragmentByTag("review");
		FragmentTransaction transaction= fm.beginTransaction();
		if(fragment!=null)
		{
transaction.remove(fragment);

		transaction.commit();
            Toast.makeText(getApplication(),"your review has updated",Toast.LENGTH_LONG).show();

		}
	}

public void rentTheMovie(View view)
{
	req = new UserRequest();
	req.rentMovie(movie_id, new RequestInterface() {
		@Override
		public JSONObject onRecive(Callback callback) {
			Toast.makeText(getApplication(),"check return for succesful rent",Toast.LENGTH_LONG).show();
			return null;
		}
	});
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
			serverRec.addListener(new RequestInterface() {
				@Override
				public JSONObject onRecive(final Callback myJsonObject) {

					handler.post(new Runnable() {

						@Override
						public void run() {

							ListView myListView = (ListView) findViewById(R.id.LV_comments);

							//myListView.setAdapter(new CommentListAdapter(myJsonObject, context));

						}
					});

					return null;
				}
			});
			serverRec.getJson("getData");
	    	
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
				
				
				
				
				//getComments();
               getComments(Integer.parseInt(movie_id));


				


		}
	public void getComments(int movieId)
	{
		String[] args = new String[3];
		args[0]="GET";
		args[1]= "reviews";
		args[2]= String.valueOf(movieId);
		serverRec.addListener(Interface);
		serverRec.getReviewByMovieId(args);



	}
    private void handleCallBack() {

        Interface= new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                final ArrayList<HashMap<String,String>> mapList;

               String method = callback.get_data()[1];
                mapList = callback.get_dataList();
                Log.d("callback","here in callback return on main");

                switch(method)
                {
                    case "reviews":
                    {
                        JsonToArraylist parcer = new JsonToArraylist();
						for(int i=0;i<mapList.size();i++)
						{

							Log.d("movies",mapList.get(i).toString());
						}
                        final String[] users= parcer.getFieldArray(mapList,"user_name");System.out.println(Arrays.toString(users));
                        String[] comments= parcer.getFieldArray(mapList,"comment");System.out.println(Arrays.toString(comments));
                        final ArrayList<String[]> pairs = new ArrayList<>();
                        pairs.add(users);
                        pairs.add(comments);
                        Log.d("callback","reviews callback method");


                                final ListView myListView = (ListView) findViewById(R.id.LV_comments);
runOnUiThread(new Runnable() {
				  @Override
				  public void run() {
					  if(users!=null && users.length>0)
					  myListView.setAdapter(new CommentListAdapter(pairs, context));
				  }
			  }
);




                    }
                    break;
                }


              return  null;
            }
        };
    }
}
