package com.codesnroses.foodo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesnroses.foodo.Model.Achievement;
import com.codesnroses.foodo.R;

import java.util.ArrayList;

/**
 * Created by Zhan on 15-02-22.
 */
public class AchievementAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Achievement> aLists;

    public AchievementAdapter(Activity activity, ArrayList<Achievement> aLists) {
        this.activity = activity;
        this.aLists = aLists;
    }

    @Override
    public int getCount() {
        return this.aLists.size();
    }

    @Override
    public Object getItem(int index) {
        return aLists.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.achievement_item, null);
        }

        //Getting item data for the row
        Achievement i = aLists.get(position);

        //Set Achievement title
        TextView title = (TextView) convertView.findViewById(R.id.achievement_title);
        title.setText(i.getTitle());

        //Set Description
        TextView description = (TextView) convertView.findViewById(R.id.achievement_desc);
        description.setText(i.getDescription());

        //Set isAchievement star on/off
        ImageView isAchieved = (ImageView) convertView.findViewById(R.id.achievements_star);

        Boolean check = i.getIsAchieved();
        if (check) {
            isAchieved.setImageResource(R.drawable.ic_star_on);
        } else {
            isAchieved.setImageResource(R.drawable.ic_star_off);
        }

        return convertView;
    }
}