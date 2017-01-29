package com.bsunk.zumperproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//This activity uses the DetailsBottomSheetDialogFragment to display details of the restaurant when in list mode.

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Fragment fragment = new DetailsBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("placeid", getIntent().getStringExtra("placeid"));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
