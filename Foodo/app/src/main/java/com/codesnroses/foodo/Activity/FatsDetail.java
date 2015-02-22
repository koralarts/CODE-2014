package com.codesnroses.foodo.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;
import com.google.gson.Gson;

public class FatsDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fats_detail);

        //Get the fat object
        String selectedFatsJson = getIntent().getStringExtra("SelectedItem");

        Fats selectedFatsObject = new Gson().fromJson(selectedFatsJson, Fats.class);

        //Fill out details
        fillOutDetails(selectedFatsObject);
    }

    private void fillOutDetails(Fats f){
        TextView name = (TextView)findViewById(R.id.name_detail);
        name.setText(f.getFood_name());

        TextView measure = (TextView)findViewById(R.id.measure_detail);
        measure.setText("Measure : "+f.getMeasure().toLowerCase());

        TextView calorie = (TextView)findViewById(R.id.calories_detail);
        calorie.setText(Math.round(f.getEnergy_kcal())+" calories");

        TextView weight = (TextView)findViewById(R.id.weight_detail);
        weight.setText("Weight : "+f.getWeight_g()+" g");

        TextView protein = (TextView)findViewById(R.id.protein_detail);
        protein.setText("Protein : "+f.getProtein_g()+" g");

        TextView carbos = (TextView)findViewById(R.id.carbohydrates_detail);
        carbos.setText("Carbs : "+f.getCarbohydrate_g()+" g");

        TextView cholesterol = (TextView)findViewById(R.id.cholesterol_detail);
        cholesterol.setText("Cholesterol : "+f.getCholesterol_mg()+" mg");

        TextView calcium = (TextView)findViewById(R.id.calcium_detail);
        calcium.setText(f.getCalcium_mg()+" mg calcium");

        TextView iron = (TextView)findViewById(R.id.iron_detail);
        iron.setText(f.getIron_mg()+" mg iron");

        TextView sodium = (TextView)findViewById(R.id.sodium_detail);
        sodium.setText(f.getSodium_mg()+" mg sodium");

        TextView potassium = (TextView)findViewById(R.id.potassium_detail);
        potassium.setText(f.getPotassium_mg()+" mg potassium");

        TextView magnesium = (TextView)findViewById(R.id.magnesium_detail);
        magnesium.setText(f.getMagnesium_mg()+" mg magnesium");

        TextView phosphorus = (TextView)findViewById(R.id.phosphorus_detail);
        phosphorus.setText(f.getPhosphorus_mg()+" mg phosphorus");

        TextView totalFat = (TextView)findViewById(R.id.total_fat_detail);
        totalFat.setText("Total fat : "+f.getTotal_fat_g()+" g");

        TextView satFat = (TextView)findViewById(R.id.saturated_fat_detail);
        satFat.setText("Sat fat : "+f.getSaturated_fat_g()+" g");

        TextView monoFat = (TextView)findViewById(R.id.monounsaturated_fat_detail);
        monoFat.setText("Mono fat : "+f.getMonounsaturated_fat_g()+" g");

        TextView polyFat = (TextView)findViewById(R.id.polyunsaturated_fat_detail);
        polyFat.setText("Poly fat : "+f.getPolyunsaturated_fat_g()+" g");

        TextView transFat = (TextView)findViewById(R.id.trans_fat_detail);
        transFat.setText("Trans fat : "+f.getTrans_fat_g()+" g");

        TextView vitaminA = (TextView)findViewById(R.id.vitamin_a_detail);
        vitaminA.setText("Vitamin A : "+f.getVitamin_a()+" mg");

        TextView vitaminE = (TextView)findViewById(R.id.vitamin_e_detail);
        vitaminE.setText("Vitamin E : "+f.getVitamin_e_mg()+" mg");

        TextView beta = (TextView)findViewById(R.id.beta_carotene_detail);
        beta.setText("Beta Carotene : "+f.getBeta_carotene_g()+" g");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fats_detail, menu);
        return true;
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
