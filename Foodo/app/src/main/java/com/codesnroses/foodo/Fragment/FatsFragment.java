package com.codesnroses.foodo.Fragment;


import android.app.Activity;
import android.os.Bundle;
//import android.app.Fragment;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.codesnroses.foodo.Activity.VolleyApplication;
import com.codesnroses.foodo.Adapter.ItemAdapter;
import com.codesnroses.foodo.Etc.Utils;
import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FatsFragment extends Fragment {
    //private final String API_KEY = getResources().getString(R.string.API_KEY_NAMARA);
    private final String API_KEY = "303a47359480f5bb640a4dce99383092b983137b8128f86b1633b934a8368252";

    private final String FATS_URL = "http://api.namara.io/v0/data_sets/824b11d2-43d6-405b-b9b4-ffba23334876/data/en-0?api_key=";
    private final String OFFSET = "&offset=0&limit=10";

    private View rootView;
    private ArrayList<Fats> fList = new ArrayList<Fats>();

    public FatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fats,container,false);

        Log.d("Fats Fragment","creating fats fragment now");

        putFatsObjectIntoList();

        return rootView;
    }


    public void putFatsObjectIntoList(){
        JSONArray fatObjects = getFatObjects();

        try{
            //Grab each JSONObject in the JSONArray and parse them into Fats object and
            //add them to a list to be passed into the listView adapter
            for(int i = 0;i<fatObjects.length();i++){
                Fats f = new Fats();
                f.setFood_name(((JSONObject) fatObjects.get(i)).getString("food_name"));
                f.setMeasure(((JSONObject) fatObjects.get(i)).getString("measure"));
                f.setWeight_g(((JSONObject) fatObjects.get(i)).getDouble("weight_g"));
                f.setEnergy_kcal(((JSONObject) fatObjects.get(i)).getDouble("energy_kcal"));
                fList.add(f);
            }
        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        //Put all Fats object into the list View
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(),fList);

        //Set adapter for the list view
        ListView myItemList = (ListView)rootView.findViewById(R.id.listView_fats);

        myItemList.setAdapter(itemAdapter);


        myItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fats f = (Fats) adapterView.getItemAtPosition(i);

                String foodName = f.getFood_name();

                Toast.makeText(getActivity().getApplicationContext(), "You clicked on " + foodName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Reads the fats.json file and returns the jsonArray object
     * @return
     */
    private JSONArray getFatObjects(){
        String jsonString = null;
        JSONArray jsonArray = null;
        try{
            //open the input stream to the file
            InputStream inputStream = getActivity().getAssets().open("fats.json");

            int size = inputStream.available();

            //array that will store the data
            byte[] bytes = new byte[size];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            jsonString = new String(bytes,"UTF-8");
            jsonArray = new JSONArray(jsonString);

        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }catch(JSONException x){
            x.printStackTrace();
            return null;
        }
        return jsonArray;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
