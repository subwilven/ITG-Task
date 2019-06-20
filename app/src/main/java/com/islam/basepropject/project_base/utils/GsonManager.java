package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GsonManager {

    //TODO need to be tested
    //you should ovveride toString in the POJO to store only the values you waint
    public static <T> String convertListToString(List<T> items) {
        Objects.requireNonNull(items);

        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (T item : items) {
            objStrings.add(gson.toJson(item));
        }
        return TextUtils.join("‚‗‚", objStrings);
    }

    //TODO need to be tested
    public static <T> List<T> convertStringToList(String string) {
        Objects.requireNonNull(string);

        Gson gson = new Gson();

        // retrieve the stored string and split it into array
        ArrayList<String> objStrings =
                new ArrayList<>(Arrays.asList(TextUtils.split(string, "‚‗‚")));

        ArrayList<T> items = new ArrayList<>();
        Type type = new TypeToken<T>(){}.getType();

        for (String jObjString : objStrings) {
            T value = gson.fromJson(jObjString,type);
            items.add(value);
        }
        return items;
    }

}
