package com.example.movienativeapp;

import listAdapters.CommentListAdapter;
import listAdapters.MoviesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.tl.uic.model.JsonBase;
//import com.worklight.wlclient.api.WLClient;
//import com.worklight.wlclient.api.WLFailResponse;
//import com.worklight.wlclient.api.WLProcedureInvocationData;
//import com.worklight.wlclient.api.WLResponse;
//import com.worklight.wlclient.api.WLResponseListener;

import fragments.CommentFrag;
import fragments.MovieFrag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MovieListActivity extends FragmentActivity {
	//private int[]  picArray ={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,};
	private boolean connected=false;
	//	private WLClient myClient;
	private Toast toast;
	private Context context;
	Handler handler;
	private JSONObject json;
	private android.support.v4.app.FragmentManager fragmentManager;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//create action bar tabs
		setContentView(R.layout.activity_main);
		handler = new Handler(getMainLooper());
		connect();

	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		Looper.prepare();

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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void connect() {
		context = this;
//		myClient = WLClient.createInstance(this);
//		myClient.connect(new WLResponseListener() {
//
//			@Override
//			public void onSuccess(WLResponse arg0) {
//				// TODO Auto-generated method stub
//			connected=true;
//
//			}
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

	}



//	public void loadF1(View view) {
//		fragmentManager = getSupportFragmentManager();
//		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		CommentFrag fragment = new CommentFrag();
//		fragmentTransaction.replace(R.id.fragment, fragment);
//
//		fragmentTransaction.addToBackStack("tag1");
//		fragmentTransaction.commit();
//		if(connected)
//			getComments();
//		else
//		{
//			toast = Toast.makeText(context, "try later you are offline " ,Toast.LENGTH_SHORT );
//			toast.show();
//		}
//
//
//	}

	public void loadF2(View view) {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
		MovieFrag fragment = new MovieFrag();
		fragmentTransaction.replace(R.id.fragment, fragment);

		fragmentTransaction.addToBackStack("tag2");
		fragmentTransaction.commit();

		if(connected)
			getMovieLIst();
		else
		{
			toast = Toast.makeText(context, "try later you are offline " ,Toast.LENGTH_SHORT );
			toast.show();
		}

	}

	public void getComments()
	{
//		myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getData"), new WLResponseListener() {
//
//			@Override
//			public void onSuccess(final WLResponse arg0) {
//				System.out.println("in success");
//				//manage json pbject
//				JSONObject myjason = arg0.getResponseJSON();
//				json=myjason;
//				for(int i=0;i<myjason.length()-1;i++)
//				{
//					try {
//						System.out.println(" this is the json array "+myjason.names().getString(i).toString()+" "+ myjason.get(myjason.names().getString(i)));
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//				handler.post(new Runnable() {
//
//					@Override
//					public void run() {
//						ListView myListView = (ListView)findViewById(R.id.comments_list);
//						//myListView.setAdapter(new CommentListAdapter(json,context));
//					}
//				});
//
//			}
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
////
//
//			}
//		});

	}

	public void getMovieLIst()
	{
//myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getData"), new WLResponseListener() {
//
//			@Override
//			public void onSuccess(final WLResponse arg0) {
//				System.out.println("in success");
//				//manage json pbject
//				JSONObject myjason = arg0.getResponseJSON();
//				json=myjason;
//				for(int i=0;i<myjason.length()-1;i++)
//				{
//					try {
//						System.out.println(" this is the json array "+myjason.names().getString(i).toString()+" "+ myjason.get(myjason.names().getString(i)));
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//				handler.post(new Runnable() {
//
//					@Override
//					public void run() {
//						ListView myListView1 = (ListView)findViewById(R.id.movies_listview_list);
////						myListView1.setAdapter(new MoviesListAdapter(json,context));
//					}
//				});
//
//			}
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
//
//
//			}
//		});

	}

}