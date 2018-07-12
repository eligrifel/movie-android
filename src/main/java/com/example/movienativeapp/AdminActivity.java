package com.example.movienativeapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fragments.EditMovieFragment;
import fragments.Fragment_rented_movies;
import fragments.insert_movie_fragment;
import listAdapters.MoviesListAdapter;
import listAdapters.MoviesListAdapterObject;

/**
 * Created by eli on 6/24/2018.
 */

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{
    Context _context;
    android.support.v4.app.FragmentManager fm ;
    FragmentTransaction fragmentTransaction ;
    private Fragment currentFragment;
    String currentFragmentTag;
    AdminRequest Areq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);
        _context=this;
        Areq = new AdminRequest();
        Button b =(Button)findViewById(R.id.B_loadMovie);
        b.setOnClickListener(new View.OnClickListener() {
         fragments.insert_movie_fragment rfragment;
            @Override
            public void onClick(View v) {




                fm = getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                Fragment f= fm.findFragmentByTag("insert_movie");
                if(f==null) {
                    rfragment = new insert_movie_fragment();


                }
                else
                    rfragment = (insert_movie_fragment) f;
                fragmentTransaction.replace(R.id.admin_container, rfragment, "insert_movie").commit();
                getSupportFragmentManager().executePendingTransactions();
                currentFragmentTag="insert_movie";
                currentFragment = rfragment;
                final Spinner categories_spinner = (Spinner) rfragment.getView().findViewById(R.id.Spinner_category);
                Button B_addmovie=(Button)rfragment.getView().findViewById(R.id.B_addmovie);
                //add listener to add new movie in admin panel
                B_addmovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int spinner_position=categories_spinner.getSelectedItemPosition();
                        String movie_categorie=categories_spinner.getSelectedItem().toString();
                        String ET_movie_name = ((EditText) v.getRootView().findViewById(R.id.ET_movie_name)).getText().toString();
                        String ET_movie_year = ((EditText) v.getRootView().findViewById(R.id.ET_movie_year)).getText().toString();
                        String ET_movie_info = ((EditText) v.getRootView().findViewById(R.id.ET_movie_info)).getText().toString();
                        String ET_img_url=((EditText) v.getRootView().findViewById(R.id.ET_movie_url)).getText().toString();
                        String ET_numberofmovies=((EditText) v.getRootView().findViewById(R.id.ET_numberofmovies)).getText().toString();
                        Log.d("movies","params are "+movie_categorie+" "+ET_movie_name+" "+ET_movie_year+" "+ET_movie_info+" "+ET_img_url+" "+ET_numberofmovies);
                        HashMap <String,String> movieParams = new HashMap<String, String>();
                        movieParams.put("movie_name",ET_movie_name);
                        movieParams.put("pick_link",ET_img_url);
                        movieParams.put("year",ET_movie_year);
                        movieParams.put("category",Integer.toString(spinner_position));
                        movieParams.put("info",ET_movie_info);
                        movieParams.put("available",ET_numberofmovies);
                        if(Areq==null)
                        Areq = new AdminRequest();
                        Areq.addMovie(movieParams, new RequestInterface() {
                            @Override
                            public JSONObject onRecive(Callback callback) {

                              callback.ShowResponse(getApplication(),"movie inserted successfully");
                                return null;
                            }
                        });
                    }
                });

                Areq.getCategories(new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {
                        ArrayList<String[]> categoriesArraylist = (ArrayList<String[]>) callback.getList();
                        String [] id =  categoriesArraylist.get(0);
                        String [] categories =categoriesArraylist.get(1);
                        final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                                getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,categories);
                       runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             categories_spinner.setAdapter(spinner_adapter);
                                         }
                                     }
                       );

                        return null;
                    }
                });
            }

        });

    }


    public void getLeasedMovies(View view) {
        fragments.Fragment_rented_movies rented_movies_fragment;
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment f= fm.findFragmentByTag("rented_movies");
        if (f==null)
        {
            rented_movies_fragment = new Fragment_rented_movies();
        }
        else {
            rented_movies_fragment = (Fragment_rented_movies) f;

        }

        fragmentTransaction.replace(R.id.admin_container,rented_movies_fragment,"rented_movies").commit();
        currentFragment = rented_movies_fragment;
        getSupportFragmentManager().executePendingTransactions();
        final ListView rentedMovies= (ListView) rented_movies_fragment.getView().findViewById(R.id.LV_rentedMovies);
        if (Areq==null)
        Areq=new AdminRequest();
        Areq.getAllLeaserMovies(new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                Movie[] movie_list = (Movie[]) callback.getList();
                MoviesListAdapterObject movieAdapter = new MoviesListAdapterObject(movie_list,R.layout.admin_single_raw_rented_movie,_context);
                rentedMovies.setAdapter(movieAdapter);
                return null;
            }
        });

    }

    //go to edit mode
    public void editMovies(View view) {
        final fragments.Fragment_rented_movies rented_movies_fragment;
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment f= fm.findFragmentByTag("EditMovies");
        if (f==null)
        {
            rented_movies_fragment = new Fragment_rented_movies();
        }
        else {
            rented_movies_fragment = (Fragment_rented_movies) f;

        }

        fragmentTransaction.replace(R.id.admin_container,rented_movies_fragment,"EditMovies").commit();
        getSupportFragmentManager().executePendingTransactions();
        currentFragment = rented_movies_fragment;

        if(Areq==null)
            Areq = new AdminRequest();
            Areq.getMovies(new RequestInterface() {
                @Override
                public JSONObject onRecive(Callback callback) {

                    Movie movies[]= (Movie[]) callback.getList();

                    final MoviesListAdapterObject listAdepter = new MoviesListAdapterObject(movies, R.layout.single_raw_edit_movie, getApplicationContext(),
                            new replaceFragment() {
                                fragments.EditMovieFragment rfragment;
                                @Override

                                public void onAdepterCall(final Movie movie) {
                                    fm = getSupportFragmentManager();
                                    fragmentTransaction = fm.beginTransaction();
                                    Fragment f= fm.findFragmentByTag("edit_movie");
                                    if(f==null) {
                                        rfragment = new EditMovieFragment();


                                    }
                                    else
                                        rfragment = (EditMovieFragment) f;
                                    fragmentTransaction.replace(R.id.admin_container, rfragment, "edit_movie").commit();
                                    getSupportFragmentManager().executePendingTransactions();
                                     ((EditText) rfragment.getView().findViewById(R.id.ET_movie_name)).setText(movie.get_name());
                                     ((EditText) rfragment.getView().findViewById(R.id.ET_movie_year)).setText(movie.getYear());
                                     ((EditText) rfragment.getView().findViewById(R.id.ET_movie_info)).setText(movie.getInfo());
                                     ((EditText) rfragment.getView().findViewById(R.id.ET_movie_url)).setText(movie.getUrl());
                                     ((EditText) rfragment.getView().findViewById(R.id.ET_numberofmovies)).setText(movie.getAvailable());
                                    final String movieCategoryId=movie.get_category();

                                    final Spinner category_spinner = (Spinner) rfragment.getView().findViewById(R.id.Spinner_category);
                                    Areq.getCategories(new RequestInterface() {
                                        @Override
                                        public JSONObject onRecive(Callback callback) {
                                            final int position;
                                            ArrayList<String[]> categoriesArraylist = (ArrayList<String[]>) callback.getList();
                                            final String [] id =  categoriesArraylist.get(0);
                                            String [] categories =categoriesArraylist.get(1);
                                            final ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                                                    getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,categories);
                                            int i=0;
                                            for (i=0;i<id.length;i++)
                                            {
                                               if(id[i].equals(movieCategoryId))
                                                   break;

                                            }
                                            position=i;
                                            runOnUiThread(new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  category_spinner.setAdapter(spinner_adapter);
                                                                  category_spinner.setSelection(position);
                                                              }
                                                          }
                                            );
                                            Button updateMovie=(Button)rfragment.getView().findViewById(R.id.B_update_movie);
                                            updateMovie.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //updating movie request to server
                                                    int position=category_spinner.getSelectedItemPosition();
                                                    String category_id=id[position];
                                                    String movie_name=((EditText) rfragment.getView().findViewById(R.id.ET_movie_name)).getText().toString();
                                                    String movie_year= ((EditText) rfragment.getView().findViewById(R.id.ET_movie_year)).getText().toString();
                                                    String movie_info=((EditText) rfragment.getView().findViewById(R.id.ET_movie_info)).getText().toString();
                                                    String movie_url=((EditText) rfragment.getView().findViewById(R.id.ET_movie_url)).getText().toString();
                                                    String movie_available = ((EditText) rfragment.getView().findViewById(R.id.ET_numberofmovies)).getText().toString();
                                                    HashMap<String,String> movieParm= new HashMap<String, String>();
                                                    movieParm.put("movie_id",movie.getId());
                                                    movieParm.put("movie_name",movie_name);
                                                    movieParm.put("pick_link",movie_url);
                                                    movieParm.put("year",movie_year);
                                                    movieParm.put("category",category_id);
                                                    movieParm.put("info",movie_info);
                                                    movieParm.put("available",movie_available);
                                                    Areq.updateMovie(movieParm, new RequestInterface() {
                                                        @Override
                                                        public JSONObject onRecive(Callback callback) {
                                                            callback.ShowResponse(_context,"movie successfully edited");
                                                            return null;
                                                        }
                                                    });


                                                }
                                            });
                                            return null;
                                        }
                                    });

                                }

                            });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             ListView movieList= (ListView) rented_movies_fragment.getView().findViewById(R.id.LV_rentedMovies);
                              movieList.setAdapter(listAdepter);
                        }
                    });

                    return null;
                }
            });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.B_update_movie:


                break;
        }

    }



    {

    }

}
