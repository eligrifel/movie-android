package listAdapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.movienativeapp.Parcer;
import com.example.movienativeapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentListAdapter implements ListAdapter {
    private Context _context;
    Parcer jToArray;
    private HashMap<String, String> _map;
    String[] users;
    String[] comments;

    public CommentListAdapter(ArrayList<String[]> user_comment_array, Context context) {
        _context = context;
        users = user_comment_array.get(0);
        comments = user_comment_array.get(1);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public int getCount() {

        return users.length;

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
    public View getView(int position, View convertView, ViewGroup parent) {


        View itemView = convertView;
        ViewHolder viewHolder = null;
        boolean firsttime = false;

        if (itemView == null) {


            LayoutInflater inflater = LayoutInflater.from(_context);
            itemView = inflater.inflate(R.layout.singlerowcomments, parent, false);
            viewHolder = new ViewHolder();

            TextView name = (TextView) itemView.findViewById(R.id.name);
            viewHolder.text = name;
            TextView comment = (TextView) itemView.findViewById(R.id.comment);

            viewHolder.comment = comment;

            itemView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }
        viewHolder.text.setText(users[position]);

        viewHolder.comment.setText(comments[position]);


        return itemView;

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public int getViewTypeCount() {

        return users.length;
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
        public TextView text;
        public TextView comment;

    }
}

