package listAdapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienativeapp.Callback;
import com.example.movienativeapp.Movie;
import com.example.movienativeapp.R;
import com.example.movienativeapp.RequestInterface;
import com.example.movienativeapp.UserRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by eli on 6/28/2018.
 */

public class MoviesListAdapterObject implements ListAdapter {
    private Bitmap[] movieImages;
    private Context _context;
    private int counter=-1;
    //private String[] _elements;
    private Movie[] _movies;
    final int _layout;


    public MoviesListAdapterObject(Movie[] movies, int layout,Context context)
    {
        _movies = movies;
        _context=context;
        _layout=layout;

        movieImages= new Bitmap[_movies.length];
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
        MoviesListAdapterObject.ViewHolder viewHolder = null;
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
            itemView =inflater.inflate(_layout, parent,false);
            viewHolder = new MoviesListAdapterObject.ViewHolder();
            viewHolder.B_return = (Button) itemView.findViewById(R.id.B_return_movie);

            TextView name = (TextView)itemView.findViewById(R.id.movie_name);
            viewHolder.text=name;
            viewHolder.ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar1);
            ImageView image = (ImageView)itemView.findViewById(R.id.movie_graphic);
            viewHolder.image = image;

            itemView.setTag(viewHolder);


        }

        else
        {
            viewHolder =(MoviesListAdapterObject.ViewHolder)itemView.getTag();
        }

final Movie single_movie = _movies[position];
        viewHolder.text.setText(single_movie.get_name());
        float temp=Float.parseFloat(single_movie.getRating());
        viewHolder.ratingBar.setRating(temp);
        if(movieImages[position]!=null)
            viewHolder.image.setImageBitmap(movieImages[position]);
        else
            new MoviesListAdapterObject.DownloadAsyncTask(position).execute(viewHolder);
        if(viewHolder.B_return!=null) {
            viewHolder.B_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String movie_id = single_movie.getId();
                    UserRequest req = new UserRequest();
                    req.returnMovie(movie_id, new RequestInterface() {
                        @Override
                        public JSONObject onRecive(Callback callback) {
                            Toast.makeText(_context,"you succesfully return the movie",Toast.LENGTH_LONG).show();
                            return null;
                        }
                    });
                }
            });

        }









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
        public Button B_return;
        public String Movie_id;

    }
    private class DownloadAsyncTask extends AsyncTask<MoviesListAdapterObject.ViewHolder, Void, MoviesListAdapterObject.ViewHolder> {
        private Bitmap bit;
        private int _position;
        public DownloadAsyncTask(int position)
        {

            super();
            _position = position;

        }

        @Override
        protected MoviesListAdapterObject.ViewHolder doInBackground(MoviesListAdapterObject.ViewHolder... params) {
            // TODO Auto-generated method stub
            //load image directly
            MoviesListAdapterObject.ViewHolder viewHolder = params[0];
            try {

                URL imageURL = new URL(_movies[_position].getUrl());
                bit = BitmapFactory.decodeStream(imageURL.openStream());

            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                viewHolder.image = null;
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(MoviesListAdapterObject.ViewHolder result) {
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


