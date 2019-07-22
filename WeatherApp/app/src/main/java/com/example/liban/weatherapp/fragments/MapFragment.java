package com.example.liban.weatherapp.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.liban.weatherapp.App;
import com.example.liban.weatherapp.MyItems;
import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.custom.CustomClusterRenderer;
import com.example.liban.weatherapp.dto.dtoBank.OfficesData;
import com.example.liban.weatherapp.mvp.mapMVP.MapContract;
import com.example.liban.weatherapp.mvp.mapMVP.MapModel;
import com.example.liban.weatherapp.mvp.mapMVP.MapPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.mapView {


    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 25;

    //    @Inject
//    ApiBank apiBank;
    private FrameLayout mFrameLayout;
    private Bitmap mSmallMarker;
    private static GoogleMap mGoogleMap;
    private MapView mMapView;
    private ClusterManager<MyItems> mClusterManager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Inject
    MapPresenter mMapPresenter;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map_id);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        App.componentApp().ComponentMap().inject(this);
        mFrameLayout = view.findViewById(R.id.frame_map_id);
        mMapPresenter.setView(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setUpMap();

    }


    private void setUpMap() {
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mClusterManager = new ClusterManager(getActivity().getApplicationContext(), mGoogleMap);
        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        setUpLocation();
        mGoogleMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            animCamera(marker.getPosition().latitude, marker.getPosition().longitude, 13f);
            return true;
        });
        mClusterManager.setRenderer(new CustomClusterRenderer(getContext(), mGoogleMap, mClusterManager, mSmallMarker));

//        addItems();
        mMapPresenter.requestData();
    }

    private void checkNetworkMap(String msg) {
        if (!PagerFragment.hasConnection(getActivity().getApplicationContext())) {
            Snackbar.make(mFrameLayout, getActivity().getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", v -> setUpMap()).show();
        } else {
            Snackbar.make(mFrameLayout, "Map: " + msg, Snackbar.LENGTH_LONG).show();
        }
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpLocation();
                }

            }
        }

    }

    private void completeData(OfficesData officesData) {
        for (int i = 0; i < officesData.getItems().size(); i++) {
            double lat = officesData.getItems().get(i).getLatitude();
            double lng = officesData.getItems().get(i).getLongitude();
            String title = String.valueOf(officesData.getItems().get(i).getId());
            String snippet = officesData.getItems().get(i).getAddress()
                    + " "
                    + officesData.getItems().get(i).getWorktime();
//                                            + "|"
//                                    + officesData.getItems().get(i).getPhone();
            mClusterManager.addItem(new MyItems(lat, lng, title, snippet));
        }
        Toast.makeText(getActivity().getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        mClusterManager.cluster();
    }

    public static void animCamera(double lat, double lng, float zoom) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapPresenter.onDestroy();
    }

    @Override
    public void onSuccessData(OfficesData officesData) {
        completeData(officesData);
    }

    @Override
    public void onErrorData(String msg) {
        checkNetworkMap(msg);
    }
}


