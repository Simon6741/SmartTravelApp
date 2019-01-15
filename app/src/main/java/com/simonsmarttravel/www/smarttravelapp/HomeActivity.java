package com.simonsmarttravel.www.smarttravelapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simonsmarttravel.www.smarttravelapp.Model.UserProfile;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    TextView txtDemo;
    protected LocationManager locationManager;
    private double latitude;
    private double longtitude;
    private String areaName;
    private int userId;

    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtDemo = (TextView) findViewById(R.id.txtDemo);
        geocoder = new Geocoder(this, Locale.getDefault());
        areaName = "";

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            userId = bundle.getInt("UserId");

            DBHandler db = new DBHandler(this, null, null, 1);
            UserProfile userProfile = db.selectUserProfileById(userId);
            Toast.makeText(this, "Welcome, " + userProfile.getUserName() + "!", Toast.LENGTH_SHORT).show();
        }


        ActivityCompat.requestPermissions(this, new String[]
                {
                        ACCESS_FINE_LOCATION
                }, 1);

        ActivityCompat.requestPermissions(this, new String[]
                {
                        ACCESS_COARSE_LOCATION
                }, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            latitude = location.getLatitude();
            longtitude = location.getLongitude();

            addresses = geocoder.getFromLocation(latitude, longtitude, 1);

            areaName = addresses.get(0).getSubLocality();

//            txtDemo.setText("Latitude:" + latitude + ", Longitude:"
//                    + longtitude + ", Area Name:" + addresses.get(0).getSubLocality());
            txtDemo.setText("Your current area : " + areaName);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }

    public void onClickTransport(View view) {
        if (!areaName.equals("")) {
            Intent intent = new Intent(this, AreaAroundMeActivity.class);
            intent.putExtra("LocationType", "Transport");
            intent.putExtra("AreaName", areaName);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent,1);//
        } else {
            Toast.makeText(this, R.string.activity_home_location_missing_toast, Toast.LENGTH_SHORT).show();
        }

//        String uriString = "geo:" + latitude + "," + longtitude +
//                "?q=" + latitude + "," + longtitude;
//        Uri uri = Uri.parse(uriString);
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                userId= Integer.parseInt( data.getStringExtra("UserId"));
            }
        }
    }

    public void onClickLandmark(View view) {
        try {
            if (!areaName.equals("")) {
                Intent intent = new Intent(this, AreaAroundMeActivity.class);
                intent.putExtra("LocationType", "Landmark");
                intent.putExtra("AreaName", areaName);
                intent.putExtra("UserId", userId);
                startActivityForResult(intent,1);
            } else {
                Toast.makeText(this, R.string.activity_home_location_missing_toast, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    public void onClickFood(View view) {
        if (!areaName.equals("")) {
            Intent intent = new Intent(this, AreaAroundMeActivity.class);
            intent.putExtra("LocationType", "Food");
            intent.putExtra("AreaName", areaName);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(this, R.string.activity_home_location_missing_toast, Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickHotel(View view) {
        if (!areaName.equals("")) {
            Intent intent = new Intent(this, AreaAroundMeActivity.class);
            intent.putExtra("LocationType", "Hotel");
            intent.putExtra("AreaName", areaName);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(this, R.string.activity_home_location_missing_toast, Toast.LENGTH_SHORT).show();
        }

    }
}
