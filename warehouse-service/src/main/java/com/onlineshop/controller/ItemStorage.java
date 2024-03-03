package com.onlineshop.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class ItemStorage {
    private static final Map<String, JsonObject> itemDatabase = new HashMap<>();

    public static void addItem(String id, JsonObject itemData) {
        itemDatabase.put(id, itemData);
    }

    public static JsonObject getItem(String id) {
        return itemDatabase.get(id);
    }

    public static void removeItem(String id) {
        itemDatabase.remove(id);
    }

    public static Map<String, JsonObject> getAllItems() {
        return new HashMap<>(itemDatabase);
    }
}
