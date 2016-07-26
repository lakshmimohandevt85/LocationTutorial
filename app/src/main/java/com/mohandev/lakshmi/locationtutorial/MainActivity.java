package com.mohandev.lakshmi.locationtutorial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Double latitude;
    private Double longitude;
    TextView _latitude;
    TextView _longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildGoogleApiClient();
         _latitude = (TextView)findViewById(R.id.textView_latitude);
         _longitude = (TextView)findViewById(R.id.textView_longitude);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);


    }

    protected synchronized void buildGoogleApiClient() {
        Log.d("Building", "Building");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

           Log.d("Latitude", String.valueOf(mLastLocation.getLatitude()));
           Log.d("Longitude", String.valueOf(mLastLocation.getLongitude()));

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
           Log.d("Latitude", String.valueOf(latitude));
            Log.d("Longitude", String.valueOf(longitude));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
    _latitude.setText(String.valueOf(mLastLocation.getLatitude()));
     _longitude.setText(String.valueOf(mLastLocation.getLongitude()));
    }
}
