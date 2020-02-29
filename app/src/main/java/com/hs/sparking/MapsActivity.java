package com.hs.sparking;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng sydney = new LatLng(-34, 151);
    LatLng hostel = new LatLng(28.476113,77.489236);
    LatLng jagat = new LatLng(28.481010,77.499650);
    LatLng parichowk = new LatLng(28.465210,77.510963);
    LatLng expomart = new LatLng(28.4591,77.4995);
    LatLng shardaHos = new LatLng(28.4753,77.4824);
    LatLng kailashHos = new LatLng(28.4707,77.5053);
    LatLng fortisHos = new LatLng(28.6184,77.3736);
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();

    ArrayList<String> title = new ArrayList<String>();



    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        arrayList.add(hostel);
        arrayList.add(jagat);
        arrayList.add(expomart);
        arrayList.add(parichowk);
        arrayList.add(shardaHos);
        arrayList.add(kailashHos);
        arrayList.add(fortisHos);

        title.add("Satvik Hostel");
        title.add("Jagat Farm");
        title.add("Expo Mart");
        title.add("Pari Chowk");
        title.add("Sharda Hospital");
        title.add("Kailash Hospital");
        title.add("Fortis Hospital");


        requestPermission();







    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    ///code
                    LatLng myloc = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myloc).title("Your Location").icon(bitmapLocDescriptorFromVector(getApplicationContext(),R.drawable.ic_location_on_red_24dp)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
                }
                else{
                    Toast. makeText(MapsActivity.this,"Location Error",Toast. LENGTH_SHORT).show();
                }
            }
        });

        // Add a marker in Sydney and move the camera

        for(int i = 0;i<arrayList.size();i++){

                mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))).icon(bitmapDescriptorFromVector(getApplicationContext(),
                        R.drawable.ic_local_parking_black_24dp)));


            mMap.moveCamera(CameraUpdateFactory.newLatLng( arrayList.get(i)));
        }


        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)));
        //mMap.addMarker(new MarkerOptions().position(hostel).title("Marker in Hostel").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(hostel));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String markertitle = marker.getTitle();
                Intent i = new Intent(MapsActivity.this, DetailsActivity.class);
                i.putExtra("title",markertitle);
                startActivity(i);

                return false;
            }
        });

    }




    private BitmapDescriptor bitmapDescriptorFromVector(Context context,int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    private BitmapDescriptor bitmapLocDescriptorFromVector(Context context,int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }
}
