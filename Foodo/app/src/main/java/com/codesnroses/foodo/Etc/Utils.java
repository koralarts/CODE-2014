package com.codesnroses.foodo.Etc;

import android.app.Application;
import android.util.Log;

import com.codesnroses.foodo.Activity.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Zhan on 15-02-21.
 */
public class Utils extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public JSONArray readJSONFile(String file){
        String jsonString = null;
        JSONArray jsonArray = null;
        try{
            //open the input stream to the file
            InputStream inputStream = getAssets().open(file);

            int size = inputStream.available();

            //array that will store the data
            byte[] bytes = new byte[size];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            jsonString = new String(bytes,"UTF-8");
            jsonArray = new JSONArray(jsonString);
            Log.d("json size", "json array size is " + jsonArray.length());
        }catch(IOException ex){
            ex.printStackTrace();
            return null;
        }catch(JSONException x){
            x.printStackTrace();
            return null;
        }
        return jsonArray;
    }
}
