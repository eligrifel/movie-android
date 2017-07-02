package view;



/**
 * Created by eli on 5/30/2015.
 */
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.example.movienativeapp.R;
import com.example.movienativeapp.R.layout;

import listAdapters.MoviesListAdapter;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Tab1 extends Fragment {
	
	OncategoryViewListener mCallback;

	    // Container Activity must implement this interface
	    public interface OncategoryViewListener {
	        public void ondetoched();
	    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.category, container, false);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
        mCallback.ondetoched();
    	 
    	
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    	try {
            mCallback = (OncategoryViewListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }


    }


}
