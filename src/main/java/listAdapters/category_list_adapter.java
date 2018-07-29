package listAdapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.movienativeapp.R;

/**
 * Created by eli on 6/25/2018.
 */

public class category_list_adapter implements ListAdapter {
    private Context _context;
    String[] _categories;
    String[] _categories_id;
    int _resource;

    public category_list_adapter(String[] categories, String[] category_id, final int resource, Context context) {
        _resource = resource;
        _context = context;
        _categories = categories;
        _categories_id = category_id;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {


    }

    @Override
    public int getCount() {

        return _categories.length;

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
        category_list_adapter.ViewHolder viewHolder = null;
        boolean firsttime = false;

        if (itemView == null) {


            LayoutInflater inflater = LayoutInflater.from(_context);
            itemView = inflater.inflate(_resource, parent, false);
            viewHolder = new category_list_adapter.ViewHolder();

            TextView category_name = (TextView) itemView.findViewById(R.id.category_names);
            viewHolder.category_name = category_name;


            itemView.setTag(viewHolder);


        } else {
            viewHolder = (category_list_adapter.ViewHolder) itemView.getTag();
        }
        viewHolder.category_name.setText(_categories[position]);


        return itemView;

    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public int getViewTypeCount() {

        return _categories.length;
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
        public TextView category_name;
        public TextView category_id;

    }
}



