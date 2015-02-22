package com.codesnroses.foodo.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View rootView;
    private String[] allJson = {"fats.json","fruits.json","meat.json","fish.json","nuts.json","sweets.json"};

    private ArrayList<Fats> randomFeaturedItems = new ArrayList<Fats>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home,container,false);


        for(int i =0; i<allJson.length;i++){
            JSONArray array = getFatObjects(allJson[i]);
            int randomInt = randomRange(0, allJson.length);
            Log.d("random feature","adding random item from "+allJson[i]);
            try{
                JSONObject object = (JSONObject)array.get(randomInt);
                Fats f = new Fats();
                f.setFood_name(object.getString("food_name"));
                f.setEnergy_kcal(object.getDouble("energy_kcal"));
                f.setMeasure(object.getString("measure"));

                Log.d("random feature","item is "+f.getFood_name());


                if(i == 0){//Fats
                    TextView fats = (TextView)rootView.findViewById(R.id.featured_fats);
                    fats.setText(f.getFood_name());
                }else if(i == 1){//Fruits
                    TextView fruits = (TextView)rootView.findViewById(R.id.featured_fruits);
                    fruits.setText(f.getFood_name());
                }else if(i == 2){//Meats
                    TextView meat = (TextView)rootView.findViewById(R.id.featured_meat);
                    meat.setText(f.getFood_name());
                }else if(i == 3){//Seafood
                    TextView seafood = (TextView)rootView.findViewById(R.id.featured_seafood);
                    seafood.setText(f.getFood_name());
                }else if(i == 4){//Seeds
                    TextView seeds = (TextView)rootView.findViewById(R.id.featured_seeds);
                    seeds.setText(f.getFood_name());
                }else if(i == 5){//Sweets
                    TextView sweets = (TextView)rootView.findViewById(R.id.featured_sweets);
                    sweets.setText(f.getFood_name());
                }







            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }




        return rootView;
    }

    public int randomRange(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min)) + min;
        return randomNum;
    }


    private JSONArray getFatObjects(String file){
        String jsonString = null;
        JSONArray jsonArray = null;

        try{
            //open the input stream to the file
            InputStream inputStream = getActivity().getAssets().open(file);

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
