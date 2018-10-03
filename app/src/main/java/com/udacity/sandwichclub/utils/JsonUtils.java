package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.optJSONObject("name");

            String mainName = nameObject.optString("mainName");

            JSONArray  alsoKnownAsJsonArray = nameObject.optJSONArray("alsoKnownAs");

            List<String> alsoKnownAsList = new ArrayList<>();

            if(alsoKnownAsJsonArray != null){
                for( int i=0 ; i< alsoKnownAsJsonArray.length() ; i++){
                    alsoKnownAsList.add(alsoKnownAsJsonArray.optString(i));
                }
            }

            String placeOfOrigin = jsonObject.optString("placeOfOrigin");

            String description = jsonObject.optString("description");

            String image = jsonObject.optString("image");

            JSONArray ingredientsJsonArray = jsonObject.optJSONArray("ingredients");

            List<String> ingredientsJsonList = new ArrayList<>();

            if(ingredientsJsonArray != null){
                for( int i=0 ; i< ingredientsJsonArray.length() ; i++){
                    ingredientsJsonList.add(ingredientsJsonArray.optString(i));
                }
            }

            return new Sandwich(mainName, alsoKnownAsList , placeOfOrigin, description, image, ingredientsJsonList);

        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the Sandwich Json Results");
        }
        return null;
    }
}
