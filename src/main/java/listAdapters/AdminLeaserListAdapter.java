

package listAdapters;

        import android.content.Context;
        import android.database.DataSetObserver;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.TextView;

        import com.example.movienativeapp.Callback;
        import com.example.movienativeapp.R;
        import com.example.movienativeapp.RequestInterface;
        import com.example.movienativeapp.UserRequest;

        import org.json.JSONObject;

        import java.util.HashMap;

/**
 * Created by eli on 6/25/2018.
 */

public class AdminLeaserListAdapter implements ListAdapter {
    private Context _context;
    String[] _movieId;
    String[] _movieName;
    String[] _userName;
    String[] _rentedDay;
    String[] _returnDay;
    int _resource;



    public AdminLeaserListAdapter(HashMap<String,String[]> data, final int resource, Context context) {
        _resource=resource;
        _context = context;
        this._movieId = data.get("movies_id");
        this._movieName = data.get("movie_names");
        this._userName = data.get("names");
        this._rentedDay = data.get("rentD");
        this._returnDay = data.get("returnD");

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _movieId.length;

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View itemView = convertView;
        AdminLeaserListAdapter.ViewHolder viewHolder = null;


        if (itemView == null) {


            LayoutInflater inflater = LayoutInflater.from(_context);
            itemView = inflater.inflate(_resource, parent, false);
            viewHolder = new AdminLeaserListAdapter.ViewHolder();

            viewHolder.user_name  = (TextView)itemView.findViewById(R.id.TV_user_name);
            viewHolder.movie_name = (TextView)itemView.findViewById(R.id.TV_movie_name);
            viewHolder.rented_day = (TextView)itemView.findViewById(R.id.TV_rented_day);
            viewHolder.returned_day = (TextView)itemView.findViewById(R.id.TV_return_day);
            viewHolder.B_returnMovie=(Button)itemView.findViewById(R.id.B_admin_return_movie);


            itemView.setTag(viewHolder);


        } else {
            viewHolder = (AdminLeaserListAdapter.ViewHolder) itemView.getTag();
        }

        viewHolder.user_name.setText(_userName[position]);
        viewHolder.movie_name.setText(_movieName[position]);
        viewHolder.rented_day.setText(_rentedDay[position]);
        viewHolder.returned_day.setText(_returnDay[position]);
        viewHolder.B_returnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRequest req = new UserRequest();
                req.returnMovie(_movieId[position], new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {
                        callback.ShowResponse(_context,"movie successfully returned");
                        return null;
                    }
                });
            }
        });

        return itemView;

    }

    @Override
    public int getItemViewType ( int position){
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getViewTypeCount () {
        // TODO Auto-generated method stub
        return _movieId.length;
    }

    @Override
    public boolean isEmpty () {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled () {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled ( int position){
        // TODO Auto-generated method stub
        return true;
    }

    static class ViewHolder {
        public int movie_id;
        public TextView movie_name;
        public TextView user_name;
        public TextView rented_day;
        public TextView returned_day;
        public Button B_returnMovie;

    }
}



