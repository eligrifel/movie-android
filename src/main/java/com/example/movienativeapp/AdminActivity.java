package com.example.movienativeapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import fragments.EditMovieFragment;
import fragments.FragmentAddUser;
import fragments.LIstViewFragment;
import fragments.insert_movie_fragment;
import listAdapters.AdminLeaserListAdapter;
import listAdapters.MoviesListAdapterObject;
import listAdapters.UserListAdapter;
import listAdapters.category_list_adapter;

/**
 * Created by eli levi on 6/24/2018.
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
        LIstViewFragment rented_movies_fragment;
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment f= fm.findFragmentByTag("rented_movies");
        if (f==null)
        {
            rented_movies_fragment = new LIstViewFragment();
        }
        else {
            rented_movies_fragment = (LIstViewFragment) f;

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
                HashMap h = (HashMap) callback.getList();
                final AdminLeaserListAdapter movieAdapter = new AdminLeaserListAdapter(h,R.layout.admin_single_raw_rented_movie,_context);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rentedMovies.setAdapter(movieAdapter);

                    }
                });

                return null;
            }
        });

    }

    //go to edit mode
    public void editMovies(View view) {
        final LIstViewFragment rented_movies_fragment;
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment f= fm.findFragmentByTag("EditMovies");
        if (f==null)
        {
            rented_movies_fragment = new LIstViewFragment();
        }
        else {
            rented_movies_fragment = (LIstViewFragment) f;

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

                                public void onAdepterCall(final Object object) {
                                    final Movie movie = (Movie) object;
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



  public void purchaseHistory(View v)
  {
      final LIstViewFragment purcahses_list;
      fm = getSupportFragmentManager();
      fragmentTransaction = fm.beginTransaction();
      Fragment f= fm.findFragmentByTag("purchase_history");
      if (f==null)
      {
          purcahses_list = new LIstViewFragment();
      }
      else {
          purcahses_list = (LIstViewFragment) f;

      }

      fragmentTransaction.replace(R.id.admin_container,purcahses_list,"purchase_history").commit();
      getSupportFragmentManager().executePendingTransactions();
      if (Areq==null)
          Areq = new AdminRequest();
      Areq.getPurchases(new RequestInterface() {
          @Override
          public JSONObject onRecive(Callback callback) {
              String [] purchases = (String[]) callback.getList();
              final ListView list = (ListView) purcahses_list.getView().findViewById(R.id.LV_rentedMovies);
              final category_list_adapter adapter = new category_list_adapter(purchases,purchases,R.layout.single_raw_category,_context);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      list.setAdapter(adapter);
                  }
              });

              return null;
          }
      });


  }
public void adduser(View view)
{
    final FragmentAddUser adduser_fragment;
    fm = getSupportFragmentManager();
    fragmentTransaction = fm.beginTransaction();
    Fragment f= fm.findFragmentByTag("adduser");
    if (f==null)
    {
        adduser_fragment = new FragmentAddUser();
    }
    else {
        adduser_fragment = (FragmentAddUser) f;

    }

    fragmentTransaction.replace(R.id.admin_container,adduser_fragment,"adduser").commit();
    getSupportFragmentManager().executePendingTransactions();
    Button B_insert_user= (Button) adduser_fragment.getView().findViewById(R.id.B_add_user);
    CheckBox CB_is_admin = (CheckBox)adduser_fragment.getView().findViewById(R.id.CB_add_admin) ;
    B_insert_user.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            insertUser(v);
        }
    });
}

    public void insertUser(View v)
    {
        String user_name=((EditText) v.getRootView().findViewById(R.id.ET_user_name)).getText().toString();
        String password=((EditText) v.getRootView().findViewById(R.id.ET_password)).getText().toString();
        String first_name=((EditText) v.getRootView().findViewById(R.id.ET_first_name)).getText().toString();
        String last_name=((EditText) v.getRootView().findViewById(R.id.ET_last_name)).getText().toString();
        String credit_card=((EditText) v.getRootView().findViewById(R.id.ET_credit_card)).getText().toString();
        boolean is_admin = ((CheckBox)v.getRootView().findViewById(R.id.CB_add_admin)).isChecked();
        if(user_name.isEmpty()||password.isEmpty()||first_name.isEmpty()||last_name.isEmpty()||credit_card.isEmpty())
        {
            Toast.makeText(_context,"all filed are requires",Toast.LENGTH_LONG).show();
        }
        else{
            if (Areq!=null)
                Areq= new AdminRequest();
            HashMap <String,String> postData = new HashMap<>();
            String role=(is_admin)?"0":"1";
            postData.put("user_name",user_name);
            postData.put("password",password);
            postData.put("first_name",first_name);
            postData.put("last_name",last_name);
            postData.put("payment_token",credit_card);
            postData.put("role",role);
            postData.put("role",role);
            Areq.AddUser(postData, new RequestInterface() {
                @Override
                public JSONObject onRecive(Callback callback) {
                    callback.ShowResponse(_context,"user successfully added");
                    return null;
                }
            });
        }
    }
public void getUsersList(View view)

{
    Log.d("movies","hee");
    final LIstViewFragment users_list_view_fragment;
    fm = getSupportFragmentManager();
    fragmentTransaction = fm.beginTransaction();
    Fragment f= fm.findFragmentByTag("users");
    if (f==null)
    {
        users_list_view_fragment = new LIstViewFragment();
    }
    else {
        users_list_view_fragment = (LIstViewFragment) f;

    }

    fragmentTransaction.replace(R.id.admin_container,users_list_view_fragment,"users").commit();
    getSupportFragmentManager().executePendingTransactions();
    currentFragment = users_list_view_fragment;
    final ListView userslistview = (ListView) users_list_view_fragment.getView().findViewById(R.id.LV_rentedMovies);
    if(Areq==null) Areq= new AdminRequest();
    Areq.getUsers(new RequestInterface() {
        @Override
        public JSONObject onRecive(Callback callback) {
            User [] users= (User[]) callback.getList();
            final UserListAdapter adapter = new UserListAdapter(users,R.layout.single_user,loadEditUserF,_context);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userslistview.setAdapter(adapter);
                }
            });

            return null;
        }
    });

}
    replaceFragment loadEditUserF = new replaceFragment() {
        @Override
        public void onAdepterCall(Object object) {
            fragments.FragmentAddUser rfragment;
            User user = (User) object;
            fm = getSupportFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            Fragment f= fm.findFragmentByTag("edit_user");
            if(f==null) {
                rfragment = fragments.FragmentAddUser.newInstance(R.layout.user_edit);


            }
            else
                rfragment = (FragmentAddUser) f;
            fragmentTransaction.replace(R.id.admin_container, rfragment, "edit_user").commit();
            getSupportFragmentManager().executePendingTransactions();
            setupEditfield(rfragment.getView(),user);


        }
    };

    private void setupEditfield(View view, final User user) {
        final EditText user_name= (EditText)view.findViewById(R.id.ET_user_name);
        final EditText first_name= (EditText)view.findViewById(R.id.ET_first_name);
        final EditText last_name=   (EditText)view.findViewById(R.id.ET_last_name);
        final EditText password=(EditText)view.findViewById(R.id.ET_password);
        final EditText creditNumber= (EditText)view.findViewById(R.id.ET_credit_card);
         CheckBox CB_is_admin = (CheckBox)view.findViewById(R.id.CB_admin);
        Button b_update= (Button)view.findViewById(R.id.B_update_user);

        user_name.setText(user.getUser_name());
        first_name.setText(user.getFirst_name());
        last_name.setText(user.getLast_name());
        creditNumber.setText(user.getCredit_number());
        final String user_id=user.getUser_id();
        boolean is_admin=false;
       if(user.getRoll().equals("0")) {
           is_admin = true;
           CB_is_admin.setChecked(is_admin);
       }
        final boolean finalIs_admin = is_admin;
        b_update.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                    HashMap<String,String> params=new HashMap<String, String>();
                String new_user_name=user_name.getText().toString();
                String new_first_name=first_name.getText().toString();
                String new_last_name=last_name.getText().toString();
                String new_credit_number=creditNumber.getText().toString();
                params.put("user_id",user_id);
                String new_password = password.getText().toString();
                if(!(new_password).isEmpty())
                    params.put("password",new_password);
                String role= (finalIs_admin)? "0":"1";
               params.put("role",role);
                if(!new_user_name.isEmpty())
                params.put("user_name",new_user_name);

                if(!new_first_name.isEmpty())
                    params.put("first_name",new_first_name);

                if(!new_last_name.isEmpty())
                    params.put("last_name",new_last_name);

                if(!new_credit_number.isEmpty())
                    params.put("payment_token",new_credit_number);

                Areq.editUser(params, new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {
                        callback.ShowResponse(_context,"user updated!");
                        return null;
                    }
                });
            }
        });
    }
}
