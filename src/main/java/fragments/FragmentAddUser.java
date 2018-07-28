package fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movienativeapp.R;


public class FragmentAddUser extends Fragment {

    public static FragmentAddUser newInstance( final int layout) {
        Bundle bundle = new Bundle();
        bundle.putInt("layout", layout);

        FragmentAddUser fragment = new FragmentAddUser();
        fragment.setArguments(bundle);

        return fragment;
    }

    private OnFragmentInteractionListener mListener;

    public FragmentAddUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(getArguments()!=null)
            return inflater.inflate(getArguments().getInt("layout"), container, false);
        else

        return inflater.inflate(R.layout.user_add, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
