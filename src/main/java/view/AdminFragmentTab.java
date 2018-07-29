package view;


/**
 */

import com.example.movienativeapp.R;
import com.example.movienativeapp.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fragments.insert_movie_fragment;

public class AdminFragmentTab extends Fragment {
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.admin_panel, container, false);

        return rootView;
    }


}
