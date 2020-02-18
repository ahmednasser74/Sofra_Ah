package com.example.sofra.data.local;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.sofra.data.model.restaurantLogin.AuthRestaurantData;
import com.google.gson.Gson;

public class SharedPreference {

    private static SharedPreferences sharedPreferences = null;
    public static String RESTAURANT_DATA_TOKEN = "RESTAURANT_DATA_TOKEN";
    public static String RESTAURANT_DATA = "RESTAURANT_DATA";
    public static String API_TOKEN = "api_token";
    public static String USER_PASSWORD = "USER_PASSWORD";
    public static String REMEMBER = "REMEMBER";
    public static String USER = "USER";
    public static String RESTAURANT = "RESTAURANT";

    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", activity.MODE_PRIVATE);
        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static String LoadData(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static boolean LoadBoolean(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static void SaveData(Activity activity, String data_Key, Object data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String StringData = gson.toJson(data_Value);
            editor.putString(data_Key, StringData);
            editor.commit();
        }
    }


    public static AuthRestaurantData loadRestaurantData(Activity activity) {
        AuthRestaurantData authRestaurantData = null;

        Gson gson = new Gson();
        authRestaurantData = gson.fromJson(LoadData(activity, RESTAURANT_DATA_TOKEN), AuthRestaurantData.class);

        return authRestaurantData;
    }

    public static void Clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
