package com.codesnroses.foodo.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesnroses.foodo.Activity.FatsDetail;
import com.codesnroses.foodo.Adapter.ItemAdapter;
import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeedsFragment extends Fragment {

    private ItemAdapter itemAdapter;

    private View rootView;
    private ArrayList<Fats> fList = new ArrayList<Fats>();

    private int AtoZToggle = 0; //0 = No sort, 1 = Sorting A to Z, 2 = Sorting Z to A
    private int CalorieToggle = 0; //0 = No sort, 1 = Sorting lowest to highest, 2 = Sorting highest to lowest

    public SeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_seeds,container,false);

        Log.d("nuts Fragment", "creating nuts fragment now");

        putFatsObjectIntoList();

        catchSort();

        return rootView;
    }

    //Sorting buttons
    public void catchSort(){
        Button AtoZButton = (Button)rootView.findViewById(R.id.AtoZSortButton);
        Button CalorieButton = (Button)rootView.findViewById(R.id.CaloriesSortButton);

        AtoZButton.setOnClickListener(new View.OnClickListener() {
            TextView t = (TextView)rootView.findViewById(R.id.AtoZSortButton);
            @Override
            public void onClick(View v) {
                if(AtoZToggle == 0){
                    Collections.sort(fList, Fats.FatComparatorAtoZ);
                    AtoZToggle = 1;
                    t.setText("Z-A");
                }else if(AtoZToggle == 1){
                    Collections.sort(fList,Fats.FatComparatorZtoA);
                    AtoZToggle = 2;
                    t.setText("A-Z");
                }else if(AtoZToggle == 2){
                    Collections.sort(fList,Fats.FatComparatorAtoZ);
                    AtoZToggle = 1;
                    t.setText("Z-A");
                }

                itemAdapter.notifyDataSetChanged();
            }
        });

        CalorieButton.setOnClickListener(new View.OnClickListener() {
            TextView c = (TextView)rootView.findViewById(R.id.CaloriesSortButton);
            @Override
            public void onClick(View v) {
                if(CalorieToggle == 0){
                    Collections.sort(fList,Fats.FatComparatorCalDown);
                    CalorieToggle = 1;
                    c.setText("Calories (Low to High)");
                }else if(CalorieToggle == 1){
                    Collections.sort(fList,Fats.FatComparatorCalUp);
                    CalorieToggle = 2;
                    c.setText("Calories (High to Low)");
                }else if(CalorieToggle == 2){
                    Collections.sort(fList,Fats.FatComparatorCalDown);
                    CalorieToggle = 1;
                    c.setText("Calories (Low to High)");
                }

                itemAdapter.notifyDataSetChanged();
            }
        });
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
                f.setEnergy_kj(((JSONObject) fatObjects.get(i)).getDouble("energy_kj"));
                f.setProtein_g(((JSONObject) fatObjects.get(i)).getDouble("protein_g"));
                f.setCarbohydrate_g(((JSONObject) fatObjects.get(i)).getDouble("carbohydrate_g"));
                f.setTotal_fat_g(((JSONObject) fatObjects.get(i)).getDouble("total_fat_g"));
                f.setSaturated_fat_g(((JSONObject) fatObjects.get(i)).getDouble("saturated_fat_g"));
                f.setMonounsaturated_fat_g(((JSONObject) fatObjects.get(i)).getDouble("monounsaturated_fat_g"));
                f.setPolyunsaturated_fat_g(((JSONObject) fatObjects.get(i)).getDouble("polyunsaturated_fat_g"));
                f.setCalcium_mg(((JSONObject) fatObjects.get(i)).getDouble("calcium_mg"));
                f.setIron_mg(((JSONObject) fatObjects.get(i)).getDouble("iron_mg"));
                f.setSodium_mg(((JSONObject) fatObjects.get(i)).getDouble("sodium_mg"));
                f.setPotassium_mg(((JSONObject) fatObjects.get(i)).getDouble("potassium_mg"));
                f.setMagnesium_mg(((JSONObject) fatObjects.get(i)).getDouble("magnesium_mg"));
                f.setPhosphorus_mg(((JSONObject) fatObjects.get(i)).getDouble("phosphorus_mg"));
                f.setVitamin_e_mg(((JSONObject) fatObjects.get(i)).getDouble("vitamin_e_mg"));
                fList.add(f);
            }
        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Put all Fats object into the list View
        itemAdapter = new ItemAdapter(getActivity(),fList);

        //Set adapter for the list view
        ListView myItemList = (ListView)rootView.findViewById(R.id.listView_seeds);


        myItemList.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

        myItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fats f = (Fats) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(rootView.getContext(), FatsDetail.class);

                String selectedFatsJson = new Gson().toJson(f);
                intent.putExtra("SelectedItem",selectedFatsJson);
                startActivity(intent);
            }
        });
    }

    /**
     * Reads the nuts.json file and returns the jsonArray object
     * @return
     */
    private JSONArray getFatObjects(){
        String jsonString = null;
        JSONArray jsonArray = null;
        try{
            //open the input stream to the file
            InputStream inputStream = getActivity().getAssets().open("nuts.json");

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
