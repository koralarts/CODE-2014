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

import com.codesnroses.foodo.Fragment.FatsFragment;
import com.codesnroses.foodo.Fragment.FruitsFragment;
import com.codesnroses.foodo.Fragment.GymFragment;
import com.codesnroses.foodo.Fragment.HomeFragment;
import com.codesnroses.foodo.Fragment.MeatFragment;
import com.codesnroses.foodo.Fragment.SeafoodFragment;
import com.codesnroses.foodo.Fragment.SeedsFragment;
import com.codesnroses.foodo.Fragment.SweetsFragment;
import com.codesnroses.foodo.Fragment.NavigationDrawerFragment;
import com.codesnroses.foodo.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
        String id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);


        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://frodo.karlworks.com/api/devices/");
        httppost.addHeader("Content-Type","application/json");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("uuid", id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            Log.d("post","post successfully?");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = homeFragment;
                mTitle = "Home";
                break;
            case 1:
                fragment = fatsFragment;
                mTitle = "Fats";
                break;
            case 2:
                fragment = fruitsFragment;
                mTitle = "Fruits";
                break;
            case 3:
                fragment = meatFragment;
                mTitle = "Meats";
                break;
            case 4:
                fragment = seafoodFragment;
                mTitle = "Seafood";
                break;
            case 5:
                fragment = seedsFragment;
                mTitle = "Seeds";
                break;
            case 6:
                fragment = sweetsFragment;
                mTitle = "Sweets";
                break;
            case 7:
                fragment = gymFragment;
                mTitle = getResources().getString(R.string.title_gym);
                break;
            default:
                fragment = homeFragment;
                mTitle = "Home";
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
