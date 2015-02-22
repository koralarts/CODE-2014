package com.codesnroses.foodo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.codesnroses.foodo.Activity.FatsDetail;
import com.codesnroses.foodo.Adapter.ItemAdapter;
import com.codesnroses.foodo.Model.Fats;
import com.codesnroses.foodo.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;

public abstract class BaseFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    protected ItemAdapter itemAdapter;

    protected View rootView;
    protected ArrayList<Fats> fullList = new ArrayList<Fats>();
    protected ArrayList<Fats> adapterList = new ArrayList<Fats>();

    protected String searchText = "";
    protected String jsonFile = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (fullList.isEmpty()) {
            putFatsObjectIntoList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_base,container,false);

        //Put all Fats object into the list View
        itemAdapter = new ItemAdapter(getActivity(), adapterList);

        attachListeners();

        //Set adapter for the list view
        ListView myItemList = (ListView)rootView.findViewById(R.id.listView);
        myItemList.setAdapter(itemAdapter);

        itemAdapter.notifyDataSetChanged();

        return rootView;
    }

    //Sorting buttons
    public void attachListeners(){
        SearchView search = (SearchView) rootView.findViewById(R.id.search);
        search.setOnQueryTextListener(this);
        search.setQuery(searchText, false);


        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_sort);
        spinner.setOnItemSelectedListener(this);

        ListView myItemList = (ListView) rootView.findViewById(R.id.listView);
        myItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fats f = (Fats) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(rootView.getContext(), FatsDetail.class);

                String selectedFatsJson = new Gson().toJson(f);
                intent.putExtra("SelectedItem", selectedFatsJson);
                startActivity(intent);
            }
        });
    }

    public abstract void putFatsObjectIntoList();

    /**
     * Reads the fats.json file and returns the jsonArray object
     * @return
     */
    protected JSONArray getFatObjects(){
        String jsonString = null;
        JSONArray jsonArray = null;
        try{
            //open the input stream to the file
            InputStream inputStream = getActivity().getAssets().open(jsonFile);

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchText = newText;

        adapterList.clear();
        if (newText == null || newText.isEmpty()) {
            adapterList.addAll(fullList);
        } else {
            for (Iterator<Fats> it = fullList.iterator(); it.hasNext(); ) {
                Fats item = it.next();
                String foodName = item.getFood_name();
                boolean found = Pattern.compile(Pattern.quote(newText), Pattern.CASE_INSENSITIVE).matcher(foodName).find();
                if (found) {
                    adapterList.add(item);
                }
            }
        }

        sortItems();

        itemAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortItems();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sortItems() {
        Spinner sortView = (Spinner) rootView.findViewById(R.id.spinner_sort);

        String selectedItem = sortView.getSelectedItem().toString();
        switch (selectedItem) {
            case "A-Z":
                Collections.sort(adapterList, Fats.FatComparatorAtoZ);
                break;
            case "Z-A":
                Collections.sort(adapterList, Fats.FatComparatorZtoA);
                break;
            case "Calories (Low to High)":
                Collections.sort(adapterList, Fats.FatComparatorCalUp);
                break;
            case "Calories (High to Low)":
                Collections.sort(adapterList, Fats.FatComparatorCalDown);
                break;
        }

        itemAdapter.notifyDataSetChanged();
    }
}
