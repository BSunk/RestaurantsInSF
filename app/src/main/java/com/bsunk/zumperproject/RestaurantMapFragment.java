package com.bsunk.zumperproject;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bsunk.zumperproject.data.model.Restaurants;
import com.bsunk.zumperproject.data.model.Result;
import com.bsunk.zumperproject.data.rest.ApiClient;
import com.bsunk.zumperproject.data.rest.PlacesInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

//Fragment to display the map. The MapView is limited to only SF. Has methods to retrieve the restaurant data and display the markers.

public class RestaurantMapFragment extends Fragment{

    private final String TAG = "RestaurantMapFragment";
    public static final Double SF_LAT = 37.7434432;
    public static final Double SF_LONG = -122.4279874;
    private ClusterManager<Result> mClusterManager;
    private int CAMERA_MOVE_REASON = -1;
    private CameraPosition cameraPosition;

    @BindView(R.id.mapView) MapView mMapView;
    @BindView(R.id.loading) ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;
    BottomSheetDialogFragment detailsBottomSheet;

    private GoogleMap googleMap;
    private Restaurants restaurantsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, rootView);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        if(savedInstanceState!=null) {
            cameraPosition = savedInstanceState.getParcelable("position");
        }
        else {
            initializeCameraPosition();
            showInfo();
        }

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        setUpMapView();
        return rootView;
    }

    public void onSaveInstanceState(Bundle bundle) {
        if(googleMap!=null) {
            bundle.putParcelable("position", googleMap.getCameraPosition());
        }
    }

    private void showInfo() {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.info), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setUpMapView() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                LatLngBounds SAN_FRANCISCO_BOUNDS = new LatLngBounds(new LatLng(37.642782, -122.514725), new LatLng(37.808428, -122.35611));
                googleMap.setLatLngBoundsForCameraTarget(SAN_FRANCISCO_BOUNDS);

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setMinZoomPreference(11);
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        getRestaurantsCall();
                    }
                });
            }
        });
    }

    private void initializeCameraPosition() {
        LatLng sf = new LatLng(SF_LAT, SF_LONG);
        cameraPosition = new CameraPosition.Builder().target(sf).zoom(11).build();
    }

    private void getRestaurantsCall() {
        //Will fetch new restaurants only if map is zoomed in.
        if(googleMap.getCameraPosition().zoom>=11) {
            progressBar.setVisibility(View.VISIBLE);
            String radius = String.valueOf(2/googleMap.getCameraPosition().zoom *8000);
            PlacesInterface placesService = ApiClient.getClient().create(PlacesInterface.class);
            Call<Restaurants> restaurants = placesService.listRestaurants(BuildConfig.PLACES_API_KEY, getCameraPosition(), radius );
            restaurants.enqueue(new Callback<Restaurants>() {
                @Override
                public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                    restaurantsList = response.body();

                    if(restaurantsList.getStatus().equals("OVER_QUERY_LIMIT")) {
                        showOverLimitError();
                    }

                    else {
                        if (mClusterManager == null) {
                            setUpCluster();
                        } else {
                            mClusterManager.clearItems();
                            addCluster();
                            mClusterManager.cluster();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
                //Something wrong retrieving the data so display a message to user and give an option to retry
                @Override
                public void onFailure(Call<Restaurants> call, Throwable t) {
                    Log.v(TAG, "Failed to get data");
                    Log.v(TAG, t.toString());

                    Activity activity = getActivity();
                    if(activity!=null && isAdded()) {
                        final Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.load_error), Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getRestaurantsCall();
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }); }
    }

    //Method to go through the list of restaurants and add to the cluster manager
    private void addCluster() {
        if(googleMap!=null) {
            for (int i = 0; i < restaurantsList.getResults().size(); i++) {
                Result result = restaurantsList.getResults().get(i);
                mClusterManager.addItem(result);
            }
        }
    }

    private void showOverLimitError() {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.load_over_limit_error), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setUpCluster() {
        //Initialize
        mClusterManager = new ClusterManager<>(getActivity(), googleMap);

        googleMap.setOnMarkerClickListener(mClusterManager);

        //Set up click listener and for info window and pass it to the cluster manager
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                mClusterManager.onInfoWindowClick(marker);
            }
        });

        //Handles info window clicks. Starts dialogfragment
        mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Result>() {
            @Override
            public void onClusterItemInfoWindowClick(Result result) {
                detailsBottomSheet = new DetailsBottomSheetDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("placeid", result.getPlaceId());
                detailsBottomSheet.setArguments(bundle);
                detailsBottomSheet.show(getFragmentManager(), detailsBottomSheet.getTag());
            }
        });

        //Does an API call when only user moves the map
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mClusterManager.onCameraIdle();
                if(CAMERA_MOVE_REASON==GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    getRestaurantsCall();
                    Utility.savePosition(getContext(), getCameraPosition()); //save this position so it can be used in the list tab.
                }
            }
        });

        //Make sure to do an API call when the user moves the map
        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    CAMERA_MOVE_REASON = i;
                }
                else { CAMERA_MOVE_REASON = -1;}
            }
        });

        //Sets renderer to take care of the restaurant title to the info window
        mClusterManager.setRenderer(new CustomRenderer(getContext(), googleMap, mClusterManager));

        // Add cluster items (markers) to the cluster manager.
        addCluster();
    }

    private String getCameraPosition() {
        CameraPosition cameraPosition = googleMap.getCameraPosition();
        LatLng latLng = cameraPosition.target;
        return latLng.latitude +"," + latLng.longitude;
    }



    //Renderer to set up restaurant title
    class CustomRenderer extends DefaultClusterRenderer<Result> {

        public CustomRenderer(Context context, GoogleMap map,
                              ClusterManager<Result> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(Result item, MarkerOptions markerOptions) {
            markerOptions.title(item.getName());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }
}
