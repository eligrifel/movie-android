

package listAdapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.movienativeapp.AdminRequest;
import com.example.movienativeapp.Callback;
import com.example.movienativeapp.R;
import com.example.movienativeapp.RequestInterface;
import com.example.movienativeapp.User;
import com.example.movienativeapp.UserRequest;
import com.example.movienativeapp.replaceFragment;

import org.json.JSONObject;

import java.util.HashMap;

/**
 */

public class UserListAdapter implements ListAdapter {
    private Context _context;
    private User[] _users;
    int _resource;
    ListAdapter _adapter;
    private replaceFragment _fragment_replace;

    public UserListAdapter(User[] users, final int resource, replaceFragment fragment_replace, Context context) {
        _resource = resource;
        _context = context;
        this._users = users;
        _adapter = this;
        _fragment_replace = fragment_replace;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public int getCount() {

        return _users.length;

    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        UserListAdapter.ViewHolder viewHolder = null;


        if (itemView == null) {


            LayoutInflater inflater = LayoutInflater.from(_context);
            itemView = inflater.inflate(_resource, parent, false);
            viewHolder = new UserListAdapter.ViewHolder();

            viewHolder.user_name = (TextView) itemView.findViewById(R.id.TV_user_name);

            viewHolder.B_edit_user = (Button) itemView.findViewById(R.id.B_edit_user);
            viewHolder.B_delete_user = (Button) itemView.findViewById(R.id.B_delete_user);


            itemView.setTag(viewHolder);


        } else {
            viewHolder = (UserListAdapter.ViewHolder) itemView.getTag();
        }

        viewHolder.user_name.setText(_users[position].getUser_name());

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.B_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fragment_replace.onAdepterCall(_users[position]);
            }
        });
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.B_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminRequest req = new AdminRequest();
                req.deleteUser(_users[position].getUser_id(), new RequestInterface() {
                    @Override
                    public JSONObject onRecive(Callback callback) {
                        callback.ShowResponse(_context, "user successfully deleted");

                        return null;
                    }
                });
            }
        });

        return itemView;

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public int getViewTypeCount() {

        return _users.length;
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {


        return true;
    }

    @Override
    public boolean isEnabled(int position) {

        return true;
    }

    static class ViewHolder {

        public TextView user_name;
        public Button B_delete_user;
        public Button B_edit_user;

    }
}



