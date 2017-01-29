package com.bsunk.zumperproject;

/**
 * Created by Bharat on 1/28/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bsunk.zumperproject.data.model.place.Place;
import com.bsunk.zumperproject.data.rest.ApiClient;
import com.bsunk.zumperproject.data.rest.PlacesInterface;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Fragment that displays details of restaurant. Used in BottomSheet in Map Mode and as a Fragment in List Mode.
public class DetailsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    String placeid;
    Place placeDetails;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.imageView) ImageView image;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.review_rv) RecyclerView reviewsRecyclerView;
    @BindView(R.id.details_loading) ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeid = getArguments().getString("placeid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);
        ButterKnife.bind(this, v);
        if(savedInstanceState!=null) {
            placeDetails = savedInstanceState.getParcelable("list");
            setInformation();
        }
        else {
            if (placeid != null) {
                getData();
            }
        }
        return v;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("list", placeDetails);
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        PlacesInterface placesService = ApiClient.getClient().create(PlacesInterface.class);
        Call<Place> placeData = placesService.getDetails(BuildConfig.PLACES_API_KEY, placeid);
        placeData.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                progressBar.setVisibility(View.GONE);
                placeDetails = response.body();
                setInformation();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Log.v("DetailsDialog", "Failed to retrieve data");
                Activity activity = getActivity();
                if(activity!=null && isAdded()) {
                    Toast.makeText(activity, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void setInformation() {
        if(placeDetails.getResult().getName()!=null) {
            name.setText(placeDetails.getResult().getName());
        }
        if(placeDetails.getResult().getPhotos()!=null) {
            String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key="
                    + BuildConfig.PLACES_API_KEY + "&photoreference=" + placeDetails.getResult().getPhotos().get(0).getPhotoReference();
            Picasso.with(getContext()).load(url).error(R.drawable.no_pic).placeholder(R.drawable.no_pic).into(image);
        }
        else {
            Picasso.with(getContext()).load(R.drawable.no_pic).into(image);
        }
        if(placeDetails.getResult().getFormattedPhoneNumber()!=null) {
            phone.setText(placeDetails.getResult().getFormattedPhoneNumber());
        }
        if(placeDetails.getResult().getFormattedAddress()!=null) {
            address.setText(placeDetails.getResult().getFormattedAddress());
        }
        if(placeDetails.getResult().getRating()!=null) {
            ratingBar.setRating(placeDetails.getResult().getRating().floatValue());
        }
        if (placeDetails.getResult().getReviews()!=null) {
            reviewsRecyclerView.setAdapter(new ReviewsAdapter(placeDetails.getResult().getReviews()));
            reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @OnClick(R.id.call_button)
    public void onClickCall() {
        if (placeDetails.getResult().getFormattedPhoneNumber()!=null) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", placeDetails.getResult().getFormattedPhoneNumber(), null)));
        }
    }



}