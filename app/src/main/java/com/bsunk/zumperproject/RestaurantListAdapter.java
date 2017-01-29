package com.bsunk.zumperproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bsunk.zumperproject.data.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bharat on 1/28/2017.
 */
//Adapter for displaying list of restaurants
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<Result> results;
    private Context mContext;
    private OnItemClickListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.restaurant_name);
            imageView = (ImageView) itemView.findViewById(R.id.restaurant_image_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.restaurant_rating);
        }

        public void bind(final Result result, final OnItemClickListener listener) {
            name.setText(result.getName());
            if (result.getRating()!=null) {
                ratingBar.setRating(result.getRating().floatValue());
            }

            if(result.getPhotos()!=null) {
                String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key="
                        + BuildConfig.PLACES_API_KEY + "&photoreference=" + result.getPhotos().get(0).getPhotoReference();
                Log.v("TAG", url);
                Picasso.with(mContext).load(url).error(R.drawable.no_pic).placeholder(R.drawable.no_pic).into(imageView);
            }
            else {
                Picasso.with(mContext).load(R.drawable.no_pic).into(imageView);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(result);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Result result);
    }

    RestaurantListAdapter(List<Result> list, Context context, OnItemClickListener listener) {
        results = list;
        mContext = context;
        mItemListener = listener;
    }

    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(results.get(position), mItemListener);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return results.size();
    }
}
