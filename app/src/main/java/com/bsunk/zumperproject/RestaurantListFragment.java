package com.bsunk.zumperproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bsunk.zumperproject.data.model.Restaurants;
import com.bsunk.zumperproject.data.model.Result;
import com.bsunk.zumperproject.data.rest.ApiClient;
import com.bsunk.zumperproject.data.rest.PlacesInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bsunk.zumperproject.R.id.coordinatorLayout;

/**
 * A simple {@link Fragment} subclass.
 */
//Displays list of restaurants
public class RestaurantListFragment extends Fragment implements RestaurantListAdapter.OnItemClickListener {

    @BindView(R.id.recyclerViewList) RecyclerView recyclerView;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout refreshLayout;
    CoordinatorLayout coordinatorLayout;

    private Restaurants restaurantsList;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        ButterKnife.bind(this, rootView);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        //check if restoring fragment
        if(savedInstanceState!=null) {
            restaurantsList = savedInstanceState.getParcelable("list");
            setData();
        }
        else {
            getRestaurantsCall();
        }

        //Called when refreshing data
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRestaurantsCall();
            }
        });

        return rootView;
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("list", restaurantsList);
    }

    private void getRestaurantsCall() {
        //Will fetch new restaurants only if map is zoomed in.
        String position = Utility.getPosition(getContext());
        refreshLayout.setRefreshing(true);
            PlacesInterface placesService = ApiClient.getClient().create(PlacesInterface.class);

            Call<Restaurants> restaurants = placesService.listRestaurantsByDistance(BuildConfig.PLACES_API_KEY, position);
            restaurants.enqueue(new Callback<Restaurants>() {
                @Override
                public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                    restaurantsList = response.body();
                    if(restaurantsList.getStatus().equals("OVER_QUERY_LIMIT")) {
                        showOverLimitError();
                    }
                    else {
                        setData();
                    }
                    refreshLayout.setRefreshing(false);
                }
                //Something wrong retrieving the data so display a message to user and give an option to retry
                @Override
                public void onFailure(Call<Restaurants> call, Throwable t) {
                    final Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.load_error), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getRestaurantsCall();
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    refreshLayout.setRefreshing(false);
                }
            });
    }

    private void showOverLimitError() {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.load_over_limit_error), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setData() {
        recyclerView.setAdapter(new RestaurantListAdapter(restaurantsList.getResults(), getContext(), this ));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onItemClick(Result result) {
        Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
        intent.putExtra("placeid", result.getPlaceId());
        startActivity(intent);
    }

}
