package com.example.movienativeapp;





import java.util.ArrayList;
import java.util.List;

import listAdapters.CommentListAdapter;
import listAdapters.MoviesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLProcedureInvocationData;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import view.MyPagerAdapter;
import view.SlidingTabLayout;
import view.Tab1;
import view.Tab1.OncategoryViewListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;




public class Main extends ActionBarActivity implements OncategoryViewListener{
	private String [] category_array;
	private  ViewPager mViewPager;
	private int[]  picArray ={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,}; 
	private boolean connected=false;
	//private WLClient myClient;
	private Toast toast;
	private Context context;
	Handler handler;
	private JSONObject json;
	private  FragmentManager fragmentManager;
	private MyPagerAdapter mypageradapter;
    List<SamplePagerItem> mTabs;
    ActionBar actionBar;
    ///movies arrays 
    private String[] moviesArray;
    private String[] moviesPathArray;
    private String[] TopRatedRating;
	final ListenerHolder holderr = new ListenerHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       

        setContentView(R.layout.main);
//       //test listener and reciver
//
//        holderr.addListener(new JsonListener() {
//
//			@Override
//			public JSONObject onRecive(JSONObject json) {
//				holderr.removeListener();
//				return null;
//			}
//		});
//        holderr.getJson("getData");
        handler = new Handler();
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFF2E2E")));
        
        
        
        
        
        //tabcolorize
        colorizetabsetting();

         mViewPager = (ViewPager) findViewById(R.id.ViewPager);
          mypageradapter  = new MyPagerAdapter(getSupportFragmentManager(), this);
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
//        Intent intent = new Intent(this,Category.class);
//        startActivity(intent);

    }

	@Override
	public void ondetoched() {
		connect();
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
        mTabs.add(new SamplePagerItem("leasedMovies", Color.WHITE,Color.WHITE));
        mTabs.add(new SamplePagerItem("top Rated", Color.WHITE,Color.WHITE));

    }
    
    public void connect() {
		context = this;
		connected=true;
		//testing


		Tab1 category1  =  (Tab1)(mypageradapter.getActiveFragment(mViewPager, 0));
		getCategoryArray();

		getComments();
		preperMovieArrays("getFMovies");

//		myClient = WLClient.createInstance(this);
//		myClient.connect(new WLResponseListener() {
//
//			@Override
//			public void onSuccess(WLResponse arg0) {
////			connected=true;
////			//testing
////
////
////			Tab1 category1  =  (Tab1)(mypageradapter.getActiveFragment(mViewPager, 0));
////			getCategoryArray();
////
////			getComments();
////			preperMovieArrays("getFMovies");
//			//getMovieLIst();
//
//			}
//
//
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
//
//			}
//		});
//
	}
    
    
    
    
    public void getComments()
	{

    	holderr.addListener(new JsonListener() {
			@Override
			public JSONObject onRecive(JSONObject myJsonObject) {
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

						ListView myListView = (ListView) findViewById(R.id.comments_list);

						myListView.setAdapter(new CommentListAdapter(json, context));

					}
				});

				return null;
			}
		});
		holderr.getJson("getData");

//		myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getData"), new WLResponseListener() {
//
//			@Override
//			public void onSuccess(final WLResponse arg0) {
//				System.out.println("in success");
//				//manage json pbject
//				JSONObject myjason = arg0.getResponseJSON();
//				json = myjason;
//				for (int i = 0; i < myjason.length() - 1; i++) {
//					try {
//						System.out.println(" this is the json array " + myjason.names().getString(i).toString() + " " + myjason.get(myjason.names().getString(i)));
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
//						ListView myListView = (ListView) findViewById(R.id.comments_list);
//						myListView.setAdapter(new CommentListAdapter(json, context));
//					}
//				});
//
//			}
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
////
//				System.out.println("in fail");
//			}
//		});
//
//
//
		
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
								System.out.println("herrreeeeeee");
//								Intent movie = new Intent(context , SingleMovieOption.class);
								TextView moviename=(TextView)view.findViewById(R.id.movie_name);
							String name = moviename.getText().toString();
//							movie.putExtra("name", name);
//							context.startActivity(movie);
							
							}
							
						});
						
					}
				});
								 
			}
			
			
		
		
	



	public void getCategoryArray() {

		ServerDummy serverDummy = new ServerDummy();
		JSONObject myjason = null;
//myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getCat"), new WLResponseListener() {
//
//			@Override
//			public void onSuccess(final WLResponse arg0) {
		System.out.println("herrrrrrrrrrrrrrrrr");
//
//			//	Tab1 category1  =  (Tab1)(mypageradapter.getActiveFragment(mViewPager, 0));
//
//
//				//manage json pbject
		try {
			myjason = serverDummy.getJsonFromSerever("getCat");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		category_array = new String[myjason.length() ];
		for (int i = 0; i < myjason.length() ; i++) {
			try {
				//System.out.println(" this is the json array "+myjason.names().getString(i).toString()+" "+ myjason.get(myjason.names().getString(i)));
				category_array[i] = "" + myjason.getString("cat" + i);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ListView categoryLIst = (ListView) findViewById(R.id.catagory_list);

				categoryLIst.setAdapter(new ArrayAdapter<String>(context, R.layout.single_raw_category, R.id.category_names, category_array));

				categoryLIst.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						System.out.println("pressss " + category_array[position]);
						Intent category = new Intent(context, MovieActivity.class);
						category.putExtra("category", category_array[position]);
						context.startActivity(category);

					}
				});

			}
//				});

//			}
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
////
//				System.out.println("GET CATEGORY FAILE fail faillllllllll");
//			}
//		});


		});
	}


	public void preperMovieArrays(String method) {
		
		
		final String [] movies;
		ServerDummy serverDummy = new ServerDummy();
		
//		myClient.invokeProcedure(new WLProcedureInvocationData("adapter", method), new WLResponseListener(){
//
//			@Override
//			public void onFailure(WLFailResponse arg0) {
//
//			}
//
//			@Override
//			public void onSuccess(WLResponse arg0) {
				//JSONObject json = arg0.getResponseJSON();
		try {

			 json = serverDummy.getJsonFromSerever(method);
		} catch (JSONException e) {
			e.printStackTrace();
		}
			//	final String [] movies;
				moviesArray=new ParsingJson(json, "m").getElement();
//
//
//				myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getFMoviesPath"), new WLResponseListener(){
//
//					@Override
//					public void onFailure(WLFailResponse arg0) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void onSuccess(WLResponse arg0) {
//						JSONObject json1 = arg0.getResponseJSON();
		JSONObject json1=null;
		try {
			 json1 = serverDummy.getJsonFromSerever("getFMoviesPath");
		} catch (JSONException e) {
			e.printStackTrace();
		}
//
						moviesPathArray=new ParsingJson(json1, "p").getElement();
//						myClient.invokeProcedure(new WLProcedureInvocationData("adapter", "getRating"), new WLResponseListener()
//						{
//
//							@Override
//							public void onFailure(WLFailResponse arg0) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onSuccess(WLResponse arg0) {
//								JSONObject json1 = arg0.getResponseJSON();
		JSONObject json2=null;
		try {
			 json2 = serverDummy.getJsonFromSerever("getRating");
		} catch (JSONException e) {
			e.printStackTrace();
		}
								TopRatedRating=new ParsingJson(json2, "r").getElement();
								getMovieLIst(moviesArray,moviesPathArray,TopRatedRating,R.id.movies_listview_list);
//							}
//
//						});
//
//
//					}
//
//				});
//			}
//
//		});
		
		
		
	}


}
