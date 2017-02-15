package pawan.t4a.com.onlinetracing.Admin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import pawan.t4a.com.onlinetracing.R;

/**
 * Created by ARUN on 1/30/2017.
 */

public class RouteMap extends AppCompatActivity implements /*OnMapReadyCallback,*/ View.OnClickListener, OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private TextView tvTitleCityInfo, tvSnippetInfo, tvLatInfo, tvLongInfo, tvUserInfo;
    private Toolbar toolbar;
    private ImageView imgUser;
    Marker currLocationMarker;
    LatLng latLng;
    String cityName;
    private Button btnRoute;
    ArrayList<LatLng> points;
    PolylineOptions lineOptions = null;
    Polyline line;
    double[] latarr;
    double[] longarr;

    //double[] latarr = {26.8466937, 28.6814284, 28.338333, 26.7732476};
    //double[] longarr = {80.946166, 77.2226866, 77.6077865, 82.1441643};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnRoute = (Button) findViewById(R.id.btnRoute);
        btnRoute.setOnClickListener(this);

        Intent intent = getIntent();
        latarr = intent.getDoubleArrayExtra("u_lat");
        longarr = intent.getDoubleArrayExtra("u_long");
        //Log.e("hagf", String.valueOf(latarr[1]));
        //Log.e("hagf", String.valueOf(longarr[1]));
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        supportMapFragment.getMapAsync(this);
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        */


    }


    @Override
    public void onClick(View v) {
        SupportMapFragment supportMapFragment;
        switch (v.getId()) {
            case R.id.btnRoute:

                // Traversing through all the routes
                supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
                // Getting reference to google map
                supportMapFragment.getMapAsync(this);
                if ((ActivityCompat.checkSelfPermission(RouteMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(RouteMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
                if (currLocationMarker != null) {
                    currLocationMarker.remove();
                }
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                for (int i = 0; i <= latarr.length - 1; i++) {
                    latLng = new LatLng(latarr[i], longarr[i]);
                    Geocoder geocoder = new Geocoder(RouteMap.this, Locale.getDefault());
                    try {
                        cityName = geocoder.getFromLocation(latarr[i], longarr[i], 1).get(0).getAddressLine(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    points.add(latLng);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                    MarkerOptions markerOptions = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))//we are also place drawable image
                            //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.arun_gps))//
                            .position(latLng)
                            .title(cityName)
                            .draggable(true)
                            .snippet("Any Msg Which U want" + "\n" + "Latitude:" + latarr[i] + ",Longitude:" + latLng.longitude);
                    mGoogleMap.addMarker(markerOptions);//it removes previous marker assign new marker to marker object


                }
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

                if (lineOptions != null) {
                    mGoogleMap.addPolyline(lineOptions);
                }
                break;


        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirmation");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure to close GPSApp?");
        // Setting Icon to Dialog
        // Setting OK Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RouteMap.this.finish();
                Toast.makeText(RouteMap.this, "Thanks", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (mGoogleMap != null) {
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.info_window, null);
                    tvTitleCityInfo = (TextView) view.findViewById(R.id.tvTitleCityInfo);
                    tvSnippetInfo = (TextView) view.findViewById(R.id.tvSnippetInfo);
                    tvLatInfo = (TextView) view.findViewById(R.id.tvLatInfo);
                    tvLongInfo = (TextView) view.findViewById(R.id.tvLongInfo);
                    tvUserInfo = (TextView) view.findViewById(R.id.tvUserInfo);
                    imgUser = (ImageView) view.findViewById(R.id.imgUser);

                    latLng = marker.getPosition();
                    tvTitleCityInfo.setText(marker.getTitle());
                    tvSnippetInfo.setText(marker.getSnippet());
                    tvLatInfo.setText(" " + latLng.latitude);
                    tvLongInfo.setText(" " + latLng.longitude);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));
                    return view;
                }
            });

        }
    }
}
