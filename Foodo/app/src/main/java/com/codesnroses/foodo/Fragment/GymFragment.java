package com.codesnroses.foodo.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesnroses.foodo.Activity.GymActivity;
import com.codesnroses.foodo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class GymFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final LatLng CANADA = new LatLng(56.130366, -106.346771);

    private JSONArray clubListArray = null;

    public GymFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.clubListArray = parseClubList();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gym, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMap();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private JSONArray parseClubList() {
        String jsonStr = null;
        try {
            InputStream is = getActivity().getApplicationContext().getAssets().open("clublist.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();

            jsonStr = sb.toString();
        } catch(IOException e) {
            Log.d("Buffer Error", e.toString());
        }

        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(jsonStr);
        } catch (JSONException e) {
            Log.d("JSON Exception", e.toString());
        }

        return jsonArray;
    }

    private void initMap() {
        MapFragment fm = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(this);

        for (int i = 0, l = this.clubListArray.length(); i < l; i++) {
            try {
                JSONObject club = this.clubListArray.getJSONObject(i);
                String title = club.getString("publicName");
                double lat = club.getDouble("latitude");
                double lng = club.getDouble("longitude");
                map.addMarker(new MarkerOptions()
                        .title(title)
                        .position(new LatLng(lat, lng)));
            } catch (JSONException e) {
                Log.d("JSON Exception", e.toString());
            }
        }

        Location location = map.getMyLocation();
        if (location != null) {
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));
        } else {
            LatLng defaultLocation = CANADA;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 3));
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(), GymActivity.class);
        startActivity(intent);
    }
}
