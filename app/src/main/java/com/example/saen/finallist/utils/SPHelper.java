package com.example.saen.finallist.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.saen.finallist.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SPHelper {
    private static String FILE_NAME = "myList";
    private static String KEY = "myList";
    public static void save(Context context, List<Item> itemList) {
        String jsonStr = new Gson().toJson(itemList);
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY, jsonStr);
        edit.commit();
    }

    public static List<Item> getItemList(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        String jsonStr = sp.getString(KEY, "[]");
        List<Item> itemList = new Gson().fromJson(jsonStr, new TypeToken<List<Item>>() {
        }.getType());
        return itemList;
    }

    public static void addNode (Context context, Item data) {
        List<Item> list = getItemList(context);
        list.add(data);
        save(context, list);
    }

    public static void delNode(Context context, Item item) {
        List<Item> list = getItemList(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(item.getId())) {
                list.remove(i);
                break;
            }
        }
        save(context, list);
    }

    public static void modify(Context context, Item item) {
        List<Item> list = getItemList(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(item.getId())) {
                list.get(i).setName(item.getName());
                break;
            }
        }
        save(context, list);
    }
}
