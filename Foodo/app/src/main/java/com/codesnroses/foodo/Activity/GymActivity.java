package com.codesnroses.foodo.Activity;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesnroses.foodo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class GymActivity extends ActionBarActivity implements OnMapReadyCallback {

    private static final String COED = "Coed Club";
    private static final String WOMEN = "Women's Club";
    private static final String BOTH = "Coed/Women's Club";

    private String name;
    private double latitude;
    private double longitude;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postal;
    private String phone;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        try {
            JSONObject club = new JSONObject(getIntent().getStringExtra("club"));
            this.name = club.getString("publicName");
            this.latitude = club.getDouble("latitude");
            this.longitude = club.getDouble("longitude");
            this.address1 = club.getString("address1");
            this.address2 = club.getString("address2");
            this.city = club.getString("city");
            this.province = club.getString("province");
            this.postal = club.getString("postal");
            this.phone = club.getString("phone");
            this.gender = club.getString("gender");
        } catch (JSONException e) {
            Log.d("JSON Exception", e.toString());
        }

        initMap();
        initDetails();
    }

    private void initMap() {
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync(this);
    }

    private void initDetails() {
        TextView name = (TextView) findViewById(R.id.gym_name);
        name.setText(this.name);

        TextView address = (TextView) findViewById(R.id.gym_address);
        String addressText = this.address1 + "\n";
        if (!this.address2.isEmpty()) {
            addressText += this.address2 + "\n";
        }
        addressText += this.city + ", " + this.province + "\n" + this.postal;
        address.setText(addressText);

        TextView phone = (TextView) findViewById(R.id.gym_phone);
        phone.setText(this.phone);

        ImageView coed = (ImageView) findViewById(R.id.gym_gender_coed);
        ImageView women = (ImageView) findViewById(R.id.gym_gender_women);
        switch (this.gender) {
            case COED:
                coed.setVisibility(android.view.View.VISIBLE);
                break;
            case WOMEN:
                women.setVisibility(android.view.View.VISIBLE);
                break;
            case BOTH:
                coed.setVisibility(android.view.View.VISIBLE);
                women.setVisibility(android.view.View.VISIBLE);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Marker marker = map.addMarker(new MarkerOptions()
                .title(this.name)
                .position(new LatLng(this.latitude, this.longitude)));

        marker.showInfoWindow();

        LatLng myLocation = new LatLng(this.latitude, this.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));
    }
}
