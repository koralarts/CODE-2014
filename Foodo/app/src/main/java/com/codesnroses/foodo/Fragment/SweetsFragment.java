package com.codesnroses.foodo.Fragment;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.codesnroses.foodo.Model.Fats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SweetsFragment extends BaseFragment implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    public SweetsFragment() {
        this.jsonFile = "sweets.json";
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
                f.setCholesterol_mg(((JSONObject) fatObjects.get(i)).getDouble("cholesterol_mg"));
                f.setCalcium_mg(((JSONObject) fatObjects.get(i)).getDouble("calcium_mg"));
                f.setIron_mg(((JSONObject) fatObjects.get(i)).getDouble("iron_mg"));
                f.setSodium_mg(((JSONObject) fatObjects.get(i)).getDouble("sodium_mg"));
                f.setPotassium_mg(((JSONObject) fatObjects.get(i)).getDouble("potassium_mg"));
                f.setMagnesium_mg(((JSONObject) fatObjects.get(i)).getDouble("magnesium_mg"));
                f.setPhosphorus_mg(((JSONObject) fatObjects.get(i)).getDouble("phosphorus_mg"));
                adapterList.add(f);
                fullList.add(f);
            }
        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
