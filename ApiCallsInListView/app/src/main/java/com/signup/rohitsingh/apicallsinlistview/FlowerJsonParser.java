package com.signup.rohitsingh.apicallsinlistview;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitsingh on 19/08/15.
 */
public class FlowerJsonParser {

    static public ArrayList<FlowerDataJsonModel> getFlowerJson (String response) {

        ArrayList<FlowerDataJsonModel> listOfFlowers = new ArrayList<FlowerDataJsonModel>();

        try {

            JSONArray arrayOfCategories = new JSONArray(response);
            for (int i = 0; i < arrayOfCategories.length(); i ++)
            {

                JSONObject jsonObject = arrayOfCategories.getJSONObject(i);
                FlowerDataJsonModel flowerDataJsonModel = new FlowerDataJsonModel();

                flowerDataJsonModel.setProductId(jsonObject.getInt("productId"));
                flowerDataJsonModel.setCategory(jsonObject.getString("category"));
                flowerDataJsonModel.setName(jsonObject.getString("name"));
                flowerDataJsonModel.setPhoto(jsonObject.getString("photo"));
                flowerDataJsonModel.setPrice(jsonObject.getDouble("price"));

                listOfFlowers.add(flowerDataJsonModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return listOfFlowers;
    }

}
