package listAdapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.movienativeapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CommentListAdapter implements ListAdapter {
	private Context _context;

	private JSONObject _json;

	public CommentListAdapter(JSONObject json, Context context) {
		_context = context;
		_json = json;

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
		return _json.length();
		//return _json.length();
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
	public View getView(int position, View convertView, ViewGroup parent) {

//			 View itemView = convertView;
//
//	            // make sure we have view to work with
//	            if (itemView==null)
//	            {
//	            	LayoutInflater inflater = LayoutInflater.from(_context);
//	                itemView =inflater.inflate(R.layout.singlerowcomments, parent,false);
//	                TextView name = (TextView)itemView.findViewById(R.id.name);
//	                TextView comment = (TextView)itemView.findViewById(R.id.comment);
//	                try {
//						name.setText(_json.names().getString(position).toString());
//
//						comment.setText(" "+(_json.get(_json.names().getString(position))));
//
//					} catch (JSONException e) {
//
//						e.printStackTrace();
//					}
//
//
//
//	            }


		//return itemView;

		/////
		View itemView = convertView;
		ViewHolder viewHolder = null;
		boolean firsttime = false;
		System.out.println("position is" + position);

		if (itemView == null) {


			LayoutInflater inflater = LayoutInflater.from(_context);
			itemView = inflater.inflate(R.layout.singlerowcomments, parent, false);
			viewHolder = new ViewHolder();

			TextView name = (TextView)itemView.findViewById(R.id.name);
			viewHolder.text = name;
			TextView comment = (TextView)itemView.findViewById(R.id.comment);

			viewHolder.comment=comment;

			itemView.setTag(viewHolder);


		} else {
			viewHolder = (ViewHolder) itemView.getTag();
		}
		try {
			viewHolder.text.setText(_json.names().getString(position).toString());

			viewHolder.comment.setText(" "+(_json.get(_json.names().getString(position))));

		} catch (JSONException e) {

			e.printStackTrace();
		}
//
//			viewHolder.text.setText(_movies[position].toString());
//			viewHolder.comment.setText(Float.valueOf(_rating[position]));
//			if (movieImages[position] != null)
//				viewHolder.image.setImageBitmap(movieImages[position]);
//			else
//				new DownloadAsyncTask(position).execute(viewHolder);

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
			return _json.length();
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
			public TextView text;
			public TextView comment;

		}
	}

