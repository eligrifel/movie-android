package listAdapters;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.movienativeapp.R;
import com.tl.uic.model.Position;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class MoviesListAdapter implements ListAdapter{
	 
	private Bitmap[] movieImages;
	private Context _context;
	private int counter=-1;
	//private String[] _elements;
	private String[] _movies;
	private String[] _paths;
	private String[] _rating;
	
	public MoviesListAdapter(String [] movies,String [] paths,String [] rating,Context context)
	{
		_rating = rating;
		_context=context;
		_movies=movies;
		_paths=paths;
		movieImages= new Bitmap[movies.length];
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
		return _movies.length;
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
		
		 View itemView = convertView;
		  ViewHolder viewHolder = null;
		  boolean firsttime = false;
		  System.out.println("position is" +position);

		  if(position>=counter)
		  {
			  firsttime=true;
			  counter++;
		  }
		  else
			  firsttime=false;
		  

            // make sure we have view to work with
            if (itemView==null)
            {
            	
            	
            	LayoutInflater inflater = LayoutInflater.from(_context);
                itemView =inflater.inflate(R.layout.single_row_movie_list, parent,false);
                viewHolder = new ViewHolder();
                
                TextView name = (TextView)itemView.findViewById(R.id.movie_name);
                viewHolder.text=name;
                viewHolder.ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar1);
                ImageView image = (ImageView)itemView.findViewById(R.id.movie_graphic);
                viewHolder.image = image;
                itemView.setTag(viewHolder);
               
                
            }
            
            else 
            {
            	viewHolder =(ViewHolder)itemView.getTag();
            }
               
                	
					viewHolder.text.setText(_movies[position].toString());
					viewHolder.ratingBar.setRating(Float.valueOf(_rating[position]));
				if(movieImages[position]!=null)
					viewHolder.image.setImageBitmap(movieImages[position]);
				else
				 new DownloadAsyncTask(position).execute(viewHolder);
				 
					
			
				
                
                	

            

            
		
		
		return itemView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return _movies.length;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}
	
	static class ViewHolder{
		public TextView text;
		public ImageView image;
		public RatingBar ratingBar;
		
	}
	private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
		private Bitmap bit;
		private int _position;
		public DownloadAsyncTask(int position)
		{
			
			super();
			_position = position;
			
		}
		
		@Override
		protected ViewHolder doInBackground(ViewHolder... params) {
			// TODO Auto-generated method stub
			//load image directly
			ViewHolder viewHolder = params[0];
			try {
				System.out.println("positions url is"+_paths[_position]);
				URL imageURL = new URL(_paths[_position]);
				bit = BitmapFactory.decodeStream(imageURL.openStream());
				
			} catch (IOException e) {
				// TODO: handle exception
				Log.e("error", "Downloading Image Failed");
				viewHolder.image = null;
			}
			
			return viewHolder;
		}
		
		@Override
		protected void onPostExecute(ViewHolder result) {
			// TODO Auto-generated method stub
			if (bit == null) {
				//result.image.setImageResource(R.drawable.postthumb_loading);
			} else {
				result.image.setImageBitmap(bit);
				
				movieImages[_position]=(bit);
			}
		}
	}
	
}


