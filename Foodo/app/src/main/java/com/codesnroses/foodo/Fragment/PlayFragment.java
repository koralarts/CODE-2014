package com.codesnroses.foodo.Fragment;


import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codesnroses.foodo.Activity.Game;
import com.codesnroses.foodo.Adapter.AchievementAdapter;
import com.codesnroses.foodo.R;
import com.codesnroses.foodo.Model.Achievement;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment {


    private View rootView;
    private ArrayList<Achievement> achievementArrayList = new ArrayList<Achievement>();
    private ArrayList<Achievement> achievementArrayListForDevice = new ArrayList<Achievement>();

    private AchievementAdapter achievementAdapter;

    private final String SERVER = "http://frodo.karlworks.com/api/achievements/";
    private String SERVER_DEVICE = "http://frodo.karlworks.com/api/devices/a7008339ec948c41/achievements/";

    private String uuid;

    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_play, container, false);

        getAchievementsFromServer();
        getAchievementsFromServerDevice();
        uuid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
Log.d("UUID","device uuid is "+uuid);
        //SERVER_DEVICE = "http://frodo.karlworks.com/api/devices/"+uuid+"/achievements/";

        checkPlayButton();

        return rootView;
    }

    private void checkPlayButton(){
        Button playButton = (Button)rootView.findViewById(R.id.button_start_game);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), Game.class);
                startActivity(intent);
            }
        });
    }

    private void getAchievementsFromServer() {
        final String jsonResult;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, SERVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        setOfflineMessage(true);
                        check(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error Response", error.getMessage());
                        setOfflineMessage(false);
                    }
                }
        );
        queue.add(postRequest);
    }

    private void getAchievementsFromServerDevice() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.GET, SERVER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response device", response);
                        store(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error Response", SERVER_DEVICE+"-"+error.getMessage());

                    }
                }
        );
        queue.add(postRequest);
    }


    private void store(String res){
        try {
            JSONArray jsonArray = new JSONArray(res);

            for (int i =0;i<jsonArray.length();i++) {
                Achievement a = new Achievement();
                a.setTitle(((JSONObject) jsonArray.get(i)).getString("name"));

                achievementArrayListForDevice.add(a);
            }
        } catch (JSONException x) {
            x.printStackTrace();
        }
    }


    private void setOfflineMessage(Boolean bool){
        TextView offlineMsg = (TextView)rootView.findViewById(R.id.offline_or_not);
        if(bool){
            offlineMsg.setVisibility(View.GONE);
        }else{
            offlineMsg.setVisibility(View.VISIBLE);
        }

    }

    private void check(String res) {

        try {
            JSONArray jsonArray = new JSONArray(res);

            for (int i =0;i<jsonArray.length();i++) {
                Achievement a = new Achievement();
                String name = ((JSONObject) jsonArray.get(i)).getString("name");
                a.setTitle(((JSONObject) jsonArray.get(i)).getString("name"));
                a.setDescription(((JSONObject) jsonArray.get(i)).getString("description"));
                a.setIsAchieved(false);



                for(int k =0 ;k<achievementArrayListForDevice.size();k++){
                    if(achievementArrayListForDevice.get(k).getTitle() == name){

                       a.setIsAchieved(true);
                        break;
                    }
                }


                achievementArrayList.add(a);
            }
        } catch (JSONException x) {
            x.printStackTrace();
        }







        //Put all Achievement object into the list View
        achievementAdapter = new AchievementAdapter(getActivity(),achievementArrayList);

        //Set adapter for the list view
        ListView myAchievementList = (ListView)rootView.findViewById(R.id.listView_achievements);


        myAchievementList.setAdapter(achievementAdapter);
        achievementAdapter.notifyDataSetChanged();




    }

}
