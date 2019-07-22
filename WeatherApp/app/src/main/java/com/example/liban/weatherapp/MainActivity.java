package com.example.liban.weatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.liban.weatherapp.fragments.PagerFragment;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new PagerFragment();
        fragmentTransaction.add(R.id.container_fragment_id, fragment);
        fragmentTransaction.commit();


    }


}
