package com.codesnroses.foodo.Fragment;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.codesnroses.foodo.Activity.GymActivity;
import com.codesnroses.foodo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class GymFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, AdapterView.OnItemSelectedListener {

    private static final LatLng CANADA = new LatLng(56.130366, -106.346771);

    private GoogleMap map;
    private JSONArray clubListArray = null;
    private HashMap<Marker, JSONObject> markerMap = null;

    public GymFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.clubListArray == null) {
            this.clubListArray = parseClubList();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gym, container, false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        android.app.Fragment f = getActivity().getFragmentManager().findFragmentById(R.id.map);
        if (f != null) {
            ft.remove(f);
            ft.commit();
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner_province);
        spinner.setOnItemSelectedListener(this);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_gender);
        spinner.setOnItemSelectedListener(this);

        initMap();
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

    private void addMarkers() {
        addMarkers(null, null);
    }

    private void addMarkers(String provinceFilter, String genderFilter) {
        this.map.clear();
        this.markerMap = new HashMap<Marker, JSONObject>();
        for (int i = 0, l = this.clubListArray.length(); i < l; i++) {
            try {
                JSONObject club = this.clubListArray.getJSONObject(i);
                String title = club.getString("publicName");
                double lat = club.getDouble("latitude");
                double lng = club.getDouble("longitude");

                if (provinceFilter != null) {
                    String province = club.getString("province");
                    if (!provinceFilter.equals(province)) {
                        continue;
                    }
                }
                if (genderFilter != null) {
                    String gender = club.getString("gender");
                    if (!genderFilter.equals(gender)) {
                        continue;
                    }
                }

                Marker marker = this.map.addMarker(new MarkerOptions()
                        .title(title)
                        .snippet(getResources().getString(R.string.gym_details))
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                this.markerMap.put(marker, club);
            } catch (JSONException e) {
                Log.d("JSON Exception", e.toString());
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(this);

        addMarkers();

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

        Bundle extras = new Bundle();
        extras.putString("club", this.markerMap.get(marker).toString());
        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        filterMarkers();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        filterMarkers();
    }

    private void filterMarkers() {
        Spinner provinceView = (Spinner) getActivity().findViewById(R.id.spinner_province);
        Spinner genderView = (Spinner) getActivity().findViewById(R.id.spinner_gender);

        String provinceItem = provinceView.getSelectedItemPosition() == 0 ? null : provinceView.getSelectedItem().toString();
        String genderItem = genderView.getSelectedItemPosition() == 0 ? null : genderView.getSelectedItem().toString();
        addMarkers(provinceItem, genderItem);
    }
}
