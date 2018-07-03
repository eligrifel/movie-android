package view;



/**
 * Created by eli on 5/30/2015.
 */
import com.example.movienativeapp.Callback;
import com.example.movienativeapp.R;
import com.example.movienativeapp.R.layout;
import com.example.movienativeapp.RequestInterface;
import com.example.movienativeapp.UserRequest;

import listAdapters.MoviesListAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tab3 extends Fragment {
    String last_name;
    String first_name;
    HashMap user;
    private View _container;

    private TextView TVuser_name;
    private TextView TVcredits;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.personal_user_layout, container, false);

        _container=container;

        TVuser_name= (TextView) rootView.findViewById(R.id.TVuser_name);
        TVcredits= (TextView) rootView.findViewById(R.id.TV_number_credit_remains);

        UserRequest request = new UserRequest();

        RequestInterface interfaceCallback = new RequestInterface() {
            @Override
            public JSONObject onRecive(Callback callback) {
                ArrayList<HashMap<String, String>> map = callback.get_dataList();
                //Log.d("test",map.toString());
                user = map.get(0);
                String user_id = (String) user.get("id");
                 first_name= (String) user.get("first_name");
                last_name= (String) user.get("last_name");

                String   role =(String) user.get("role");
                getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    TVuser_name.setText(first_name+last_name);

                                                    TVcredits.setText((String) user.get("credits"));
                                                }
                                            }
                );

                return null;
            }
        };

        //request.addListener(interfaceCallback);
        request.getCurrentUser(interfaceCallback);


        return rootView;
    }

}