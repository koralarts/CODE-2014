package com.codesnroses.foodo.Activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.provider.Settings.Secure;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codesnroses.foodo.Etc.AppController;
import com.codesnroses.foodo.Fragment.FatsFragment;
import com.codesnroses.foodo.Fragment.FruitsFragment;
import com.codesnroses.foodo.Fragment.GymFragment;
import com.codesnroses.foodo.Fragment.HomeFragment;
import com.codesnroses.foodo.Fragment.MeatFragment;
import com.codesnroses.foodo.Fragment.PlayFragment;
import com.codesnroses.foodo.Fragment.SeafoodFragment;
import com.codesnroses.foodo.Fragment.SeedsFragment;
import com.codesnroses.foodo.Fragment.SweetsFragment;
import com.codesnroses.foodo.Fragment.NavigationDrawerFragment;
import com.codesnroses.foodo.R;
import com.android.volley.Request.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final String SERVER = "http://frodo.karlworks.com/api/devices/";


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Fragment homeFragment = new HomeFragment();
    private Fragment fatsFragment = new FatsFragment();
    private Fragment fruitsFragment = new FruitsFragment();
    private Fragment meatFragment = new MeatFragment();
    private Fragment seafoodFragment = new SeafoodFragment();
    private Fragment seedsFragment = new SeedsFragment();
    private Fragment sweetsFragment = new SweetsFragment();
    private Fragment gymFragment = new GymFragment();
    private Fragment playFragment = new PlayFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = "Home"; //Default

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        postDeviceDataToServer();
    }

    private void postDeviceDataToServer(){
        //Get device unique id
        final String uuid = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, SERVER,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uuid", ""+uuid);

                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = homeFragment;
                mTitle = getResources().getString(R.string.title_home);
                break;
            case 1:
                fragment = fatsFragment;
                mTitle = getResources().getString(R.string.title_fats);
                break;
            case 2:
                fragment = fruitsFragment;
                mTitle = getResources().getString(R.string.title_fruits);
                break;
            case 3:
                fragment = meatFragment;
                mTitle = getResources().getString(R.string.title_meat);
                break;
            case 4:
                fragment = seafoodFragment;
                mTitle = getResources().getString(R.string.title_seafood);
                break;
            case 5:
                fragment = seedsFragment;
                mTitle = getResources().getString(R.string.title_seeds);
                break;
            case 6:
                fragment = sweetsFragment;
                mTitle = getResources().getString(R.string.title_sweets);
                break;
            case 7:
                fragment = gymFragment;
                mTitle = getResources().getString(R.string.title_gym);
                break;
            case 8:
                fragment = playFragment;
                mTitle = getResources().getString(R.string.title_play);
                break;
            default:
                fragment = homeFragment;
                mTitle = getResources().getString(R.string.title_home);
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
