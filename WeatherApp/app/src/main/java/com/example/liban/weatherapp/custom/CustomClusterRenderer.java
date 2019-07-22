package com.example.liban.weatherapp.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liban.weatherapp.MyItems;
import com.example.liban.weatherapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CustomClusterRenderer extends DefaultClusterRenderer<MyItems> {

    private IconGenerator mIconGenerator;
    private Bitmap mBitmap;
    private Context mContext;
    private TextView mTextView;


    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager, Bitmap bitmap) {
        super(context, map, clusterManager);
        mContext = context;
        mBitmap = bitmap;
        mTextView = new TextView(context);
        mIconGenerator = new IconGenerator(context);
    }


    @Override
    protected void onBeforeClusterItemRendered(MyItems item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        mTextView.setBackgroundResource(R.drawable.bg_marker);
        mTextView.setText(item.getTitle());
        mTextView.setLayoutParams(new ViewGroup.LayoutParams(60, 70));
        mTextView.setGravity(Gravity.CENTER | Gravity.TOP);
        mIconGenerator.setContentView(mTextView);
        mIconGenerator.setBackground(null);

        markerOptions.icon(BitmapDescriptorFactory
                .fromBitmap(mIconGenerator.makeIcon()))
                .position(item.getPosition());
    }
}
