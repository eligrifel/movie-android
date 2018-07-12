package fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movienativeapp.Callback;
import com.example.movienativeapp.R;
import com.example.movienativeapp.RequestInterface;
import com.example.movienativeapp.UserRequest;

import org.json.JSONObject;

import listAdapters.category_list_adapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_get_credit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Fragment_get_credit extends Fragment implements  View.OnClickListener {
    private  View mView;
    private OnFragmentInteractionListener mListener;
    private UserRequest request;
    public Fragment_get_credit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mView= inflater.inflate(R.layout.layout_fragment_get_credit, container, false);
        Button buyCredit = (Button) mView.findViewById(R.id.B_getCredit);
        Button showPurchases = (Button) mView.findViewById(R.id.B_show_purchase_history);
        showPurchases.setOnClickListener(this);
        buyCredit.setOnClickListener(this);
return mView;

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

    @Override
    public void onClick(View v) {
        final String[] Tmessage = new String[1];
        if (request==null) request = new UserRequest();
        switch ((v.getId())){
            case  R.id.B_getCredit :
               final String numberOfCredits=((Spinner)(v.getRootView().findViewById(R.id.spinner_credit))).getSelectedItem().toString();
                request.buyCredit(numberOfCredits, new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {

                        String response =callback.getRespondFromServer();
                        if((response.equals("1"))){
                            Tmessage[0] ="credit was added successfully";



                        }
                        else
                        if(response!=null)
                            Tmessage[0]=response;

                       getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),Tmessage[0],Toast.LENGTH_LONG).show();
                            }
                        });
                        return null;
                    }
                });
break;
            case R.id.B_show_purchase_history:
                final ListView purchaaseList = (ListView) v.getRootView().findViewById(R.id.LV_purchase_history);
                if (request==null)
                    request= new UserRequest();
                request.getPurchaseHistory(new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {
                        final String [] purchase_history = (String[])callback.getList();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                purchaaseList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.single_raw_category, R.id.category_names, purchase_history));
                            }
                        });


                        return null;
                    }
                });
// once json is ready continue poplate the list view for purcheses items

                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
