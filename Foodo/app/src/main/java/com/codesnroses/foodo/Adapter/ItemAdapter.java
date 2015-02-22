package com.codesnroses.foodo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhan on 15-02-21.
 */
public class ItemAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Fats> fatLists;

    public ItemAdapter(Activity activity, ArrayList<Fats> fatLists){
        this.activity = activity;
        this.fatLists = fatLists;
    }

    @Override
    public int getCount(){
        return this.fatLists.size();
    }

    @Override
    public Object getItem(int index){
        return fatLists.get(index);
    }

    @Override
    public long getItemId(int index){
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item,null);
        }

        //Getting item data for the row
        Fats i = fatLists.get(position);

        //Set Food name
        TextView foodName = (TextView) convertView.findViewById(R.id.item_food_name);
        foodName.setText(i.getFood_name());

        //Set Serving size
        TextView servingSize = (TextView) convertView.findViewById(R.id.item_serving_size);
        servingSize.setText("Portion : "+i.getMeasure());

        //Set calories
        TextView calories = (TextView) convertView.findViewById(R.id.item_calories);

        long c = Math.round(i.getEnergy_kcal());
        String cal = (c<=1)?c+" calorie":c+" calories";
        calories.setText(cal);

        return convertView;
    }
}
