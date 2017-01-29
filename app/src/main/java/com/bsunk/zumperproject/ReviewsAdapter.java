package com.bsunk.zumperproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bsunk.zumperproject.data.model.place.Review;

import java.util.List;

/**
 * Created by Bharat on 1/28/2017.
 */
//Adapter for displaying reviews
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> reviews;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView text;
        RatingBar ratingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.review_author);
            text = (TextView) itemView.findViewById(R.id.review_text);
            ratingBar = (RatingBar) itemView.findViewById(R.id.review_rating);
        }
    }

    ReviewsAdapter(List<Review> list) {
        reviews = list;
    }


    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View rootView = inflater.inflate(R.layout.review_item, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder viewHolder, int position) {
        Review singleReview = reviews.get(position);
        viewHolder.author.setText(singleReview.getAuthorName());
        viewHolder.text.setText(singleReview.getText());
        if (singleReview.getRating()!=null) {
            viewHolder.ratingBar.setRating(singleReview.getRating().floatValue());
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
