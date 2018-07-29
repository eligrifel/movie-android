package view;


/**
 * Created by eli on 5/30/2015.
 */

import com.example.movienativeapp.Callback;
import com.example.movienativeapp.R;
import com.example.movienativeapp.R.layout;
import com.example.movienativeapp.RequestInterface;
import com.example.movienativeapp.UserRequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tab2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.search_movie, container, false);


        return rootView;
    }

}
