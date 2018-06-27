package com.example.movienativeapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import fragments.Fragment_rented_movies;
import fragments.insert_movie_fragment;

/**
 * Created by eli on 6/24/2018.
 */

public class AdminActivity extends AppCompatActivity {
    android.support.v4.app.FragmentManager fm ;
    FragmentTransaction fragmentTransaction ;
    private Fragment currentFragment;
    String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);
        Button b =(Button)findViewById(R.id.B_loadMovie);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("check","insertMovie");
                fragments.insert_movie_fragment rfragment;


                fm = getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                Fragment f= fm.findFragmentByTag("insert_movie");
                if(f==null) {
                    rfragment = new insert_movie_fragment();


                }
                else
                    rfragment = (insert_movie_fragment) f;
                fragmentTransaction.replace(R.id.admin_container, rfragment, "insert_movie").commit();

                currentFragmentTag="insert_movie";
                currentFragment = rfragment;
            }

        });
    }


    public void getLeasedMovies(View view) {
        fragments.Fragment_rented_movies rented_movies_fragment;
        Log.d("here","get leased movies");
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Fragment f= fm.findFragmentByTag("rented_movies");
        if (f==null)
        {
            rented_movies_fragment = new Fragment_rented_movies();
            Log.d("fragment","not exist");
        }
        else {
            rented_movies_fragment = (Fragment_rented_movies) f;
            Log.d("fragment","create new one");
        }

        fragmentTransaction.replace(R.id.admin_container,rented_movies_fragment,"rented_movies").commit();
        currentFragment = rented_movies_fragment;

    }
}
