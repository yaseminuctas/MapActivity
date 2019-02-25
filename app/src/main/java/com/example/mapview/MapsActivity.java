package com.example.mapview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    ArrayList<Marker> markers = new ArrayList<>();




    private final static int REQUEST_lOCATION = 90;
    EditText edtLat, edtLng;
    Button button;
    String lat, lng;
    Double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        // for (int i = 0 ; i < markersArray.size() ; i++) {

        // }

        edtLng = (EditText) findViewById( R.id.edt_lng );
        edtLat = (EditText) findViewById( R.id.edt_lat );

        button = (Button) findViewById( R.id.btn_ekle );


        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lat = edtLat.getText().toString();
                latitude = Double.valueOf( lat ).doubleValue();

                lng = edtLng.getText().toString();
                longitude = Double.valueOf( lng ).doubleValue();

                LatLng latLng = new LatLng( latitude, longitude );



                Marker markerOpt1 = mMap.addMarker( new MarkerOptions().position( new LatLng( latitude, longitude ) ) );
                
                mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );





                if (markers.size() > 1) {
                    mMap.addPolyline(
                            new PolylineOptions()
                                    .add( markers.get( 0 ).getPosition(), markers.get( 1 ).getPosition() )
                                    .width( 8f )
                                    .color( Color.RED ) );
                }


            }


        } );


    }

    private boolean ShouldAddMarker(double latA, double lngA, double latB, double lngB) {


        Location locationA = new Location( "point A" );

        locationA.setLatitude( latA );
        locationA.setLongitude( lngA );

        Location locationB = new Location( "point B" );

        locationB.setLatitude( latB );
        locationB.setLongitude( lngB );


        float distance = locationA.distanceTo( locationB );
        return distance > 1000;


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            mMap.setMyLocationEnabled( true );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_lOCATION );
            }


        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );


    }
}
