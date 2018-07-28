package com.example.movienativeapp;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fragments.Fragment_get_credit;
import fragments.MovieFrag;
import fragments.insert_movie_fragment;
import listAdapters.MoviesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import listAdapters.MoviesListAdapterObject;
import listAdapters.category_list_adapter;
import view.MyPagerAdapter;
import view.SlidingTabLayout;
import view.Tab1.OncategoryViewListener;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;




public class Main extends AppCompatActivity implements OncategoryViewListener{
	//fragment property
    Movie [] movielist;
    private  UserRequest request;
	android.support.v4.app.FragmentManager fm ;
	FragmentTransaction fragmentTransaction ;
    ListView categoryLIst;
	Button B_returnMovie;
	private String [] category_array;
	private  ViewPager mViewPager;
	private boolean connected=false;
	//private WLClient myClient;
	private Toast toast;
	private Context context;
	Handler handler;
	//private JSONObject json;
	private  FragmentManager fragmentManager;
	private MyPagerAdapter mypageradapter;
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
    ///movies arrays 
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;
	final RequestListenerHolder serverRec = new RequestListenerHolder();
	private  RequestInterface callback;
	String method ;
	ArrayList <HashMap<String ,String>> _ServerArrayList;
	//private user data
	private String user_id;
	private String user_name; TextView TVuser_name;
	private  String credit  ; TextView TVcredits;
	private String role;
	private String NumberRatedMovies; TextView TVnumber_rated_movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.d("movies","on create method!");

       handleCallBack();
        serverRec.addListener(callback);
        setContentView(R.layout.main);
		//text view of user personal info
		TVuser_name= (TextView) findViewById(R.id.TVuser_name);
		TVcredits = (TextView) findViewById(R.id.TV_number_credit_remains);
		TVnumber_rated_movies=(TextView) findViewById(R.id.TV_number_rented_movies);

        handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));


		testmethod();

        
        //tabcolorize
        colorizetabsetting();

         mViewPager = (ViewPager) findViewById(R.id.ViewPager);
          mypageradapter  = new MyPagerAdapter(getSupportFragmentManager(), this,false);//true if admin pannel
        mViewPager.setAdapter(mypageradapter);
       mViewPager.setOffscreenPageLimit(3);
       

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        
        mSlidingTabLayout.setCustomTabView(R.layout.tab_custom, R.id.textView1);
        mSlidingTabLayout.setBackgroundColor(Color.parseColor("#ffFF2E2E"));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.BLUE);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        


        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

													@Override
													public int getIndicatorColor(int position) {
														return mTabs.get(position).mIndicatorColor;
													}


												}
		);

        

    }



	private void testmethod() {

		 request = new UserRequest();

		RequestInterface interfaceCallback = new RequestInterface() {
			@Override
			public JSONObject onRecive(Callback callback) {
				ArrayList<HashMap<String, String>> map = callback.get_dataList();
				//Log.d("test",map.toString());
				HashMap user = map.get(0);
				user_id = (String) user.get("id");
				String first_name= (String) user.get("first_name");
				String last_name= (String) user.get("last_name");

				role =(String) user.get("role");

				return null;
			}
		};


		request.getCurrentUser(interfaceCallback);
	}
		//setting fragment for user panel
		public void getCredit(View view){

			fragments.Fragment_get_credit rfragment;


			fm = getSupportFragmentManager();
			fragmentTransaction = fm.beginTransaction();
			Fragment f= fm.findFragmentByTag("getCredit");
			if(f==null) {
				rfragment = new Fragment_get_credit();


			}
			else
				rfragment = (Fragment_get_credit) f;

			fragmentTransaction.replace(R.id.layout_personal_page_fragment_container, rfragment, "getCredit").commit();
			getSupportFragmentManager().executePendingTransactions();
			Spinner credit_spinner = (Spinner) rfragment.getView().findViewById(R.id.spinner_credit);

			int min=1;
			int max=10;
			String []credit_spinner_value= new String[max+1-min];
			for (int i=1;i<max+1;i++)
			{
			credit_spinner_value[i-1]=Integer.toString(i);
			}
			ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,credit_spinner_value);
			credit_spinner.setAdapter(spinner_adapter);



	}

	public void getCurrentRentedMovies(View view){

		fragments.MovieFrag rfragment;


		fm = getSupportFragmentManager();
		fragmentTransaction = fm.beginTransaction();
		Fragment f= fm.findFragmentByTag("insert_movie");
		if(f==null) {
			rfragment = new MovieFrag();


		}
		else
			rfragment = (MovieFrag) f;
		fragmentTransaction.replace(R.id.layout_personal_page_fragment_container, rfragment, "insert_movie").commit();
		getSupportFragmentManager().executePendingTransactions();

		final ListView LV_current_rented_movies = (ListView) rfragment.getView().findViewById(R.id.movies_listview_list);
		//put list of rented movies to the adapter
		request.getRentedMovies(new RequestInterface() {
			@Override
			public JSONObject onRecive(Callback callback) {
				movielist=callback.getMoviesList();

				if(movielist!=null&&movielist.length>0){
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						LV_current_rented_movies.setAdapter(new MoviesListAdapterObject(movielist, R.layout.single_row_rented_movie, getApplicationContext()));
						LV_current_rented_movies.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Intent movie = new Intent(context, SingleMovieOption.class);
								Movie single_movie = movielist[position];
								movie.putExtra("name", single_movie.get_name());
								movie.putExtra("path", single_movie.getUrl());
								movie.putExtra("movie_id", single_movie.getId());
								movie.putExtra("info", single_movie.getInfo());

								float movie_rating = Float.parseFloat(movielist[position].getRating());

								movie.putExtra("rating", movie_rating);
								context.startActivity(movie);
							}
						});

					}
				});}

				return null;
			}
		});



	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

    }

	@Override
	public void ondetoched() {
		connect();
	}


	public void insert_movie(View view) {
		Log.d("check","insertMovie");
		fragments.insert_movie_fragment rfragment = new insert_movie_fragment();
		fm = getSupportFragmentManager();
		Log.d("login",fm.getFragments().toString());
		fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.add(R.id.admin_container, rfragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());


	}




	static class SamplePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }
    }
    private void colorizetabsetting() {
         mTabs = new ArrayList<SamplePagerItem>();
        mTabs.add(new SamplePagerItem("Category", Color.WHITE,Color.WHITE));
        mTabs.add(new SamplePagerItem("search", Color.WHITE,Color.WHITE));
        mTabs.add(new SamplePagerItem("dashboard", Color.WHITE,Color.WHITE));
        mTabs.add(new SamplePagerItem("admin panel", Color.WHITE,Color.WHITE));


    }
    
    public void connect() {
		context = this;
		connected=true;
		//testing



        serverRec.getCategoryList();
		}
    
    
    
    
    public void getComments(int movieId)
	{
        String[] args = new String[3];
        args[0]="GET";
        args[1]= "reviews";
        args[2]= String.valueOf(movieId);

		serverRec.getReviewByMovieId(args);


		
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

								TextView moviename=(TextView)view.findViewById(R.id.movie_name);
							String name = moviename.getText().toString();


							
							}
							
						});
						
					}
				});
								 
			}
			
			
		
		
	



	

    private void handleCallBack() {

        callback= new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                final ArrayList<HashMap<String,String>> mapList;

                method = callback.get_data()[1];
                mapList = callback.get_dataList();
                Log.d("callback","here in callback return on main"+ method);

                switch(method)
                {
					case "movies/categories":
					{
						Parcer parcer = new Parcer();
						final String[] category_array= parcer.getFieldArray(mapList,"category_name");
						final String []  category_id_array=parcer.getFieldArray(mapList,"id");

						 categoryLIst = (ListView) findViewById(R.id.catagory_list);
runOnUiThread(new Runnable() {
	@Override
	public void run() {

			categoryLIst.setAdapter(new category_list_adapter( category_array,category_id_array,R.layout.single_raw_category,context));

		categoryLIst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent category = new Intent(context, MovieActivity.class);
				category.putExtra("category", category_array[position]);
				category.putExtra("role", role);
				category.putExtra("category_id",category_id_array[position]);
				context.startActivity(category);

			}
		});

	}
});



					}
					break;
                }


                return null;
            }
        };
    }

    public void search(View view){
		SearchView searchView = (SearchView) view.getRootView().findViewById(R.id.search_box);
		String search_input=searchView.getQuery().toString();

		if(request==null)
			request = new UserRequest();
		final ListView movie_list= (ListView) view.getRootView().findViewById(R.id.LV_search);
		request.getMoviesSearchResults(search_input, new RequestInterface() {
			@Override
			public JSONObject onRecive(final Callback callback) {
			final Movie [] movies = (Movie[]) callback.getList();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(movies.length>0)
						{
						movie_list.setAdapter(new MoviesListAdapterObject(movies, R.layout.single_row_movie_list, context));
						movie_list.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Intent movie = new Intent(context, SingleMovieOption.class);
								Movie single_movie = movies[position];
								movie.putExtra("name", single_movie.get_name());
								movie.putExtra("path", single_movie.getUrl());
								movie.putExtra("movie_id", single_movie.getId());
								movie.putExtra("info", single_movie.getInfo());

								float movie_rating = Float.parseFloat(movies[position].getRating());

								movie.putExtra("rating", movie_rating);
								context.startActivity(movie);
							}
						});
					}
						else
							Toast.makeText(context,"no search result",Toast.LENGTH_LONG).show();
					}
				});


				return null;
			}
		});
	}
}
