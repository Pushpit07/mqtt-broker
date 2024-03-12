package com.onlineshop.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemStorage {
    private static final Map<String, JsonObject> itemDatabase = new HashMap<>();

    public static void addItem(String id, JsonObject itemData) {
        itemDatabase.put(id, itemData);
    }

    public static JsonObject getItemById(String id) {
        return itemDatabase.get(id);
    }

    public static void removeItem(String id) {
        itemDatabase.remove(id);
    }

    public static List<Map<String, Object>> getAllItems() {
        List<Map<String, Object>> itemList = new ArrayList<>();
    
        for (Map.Entry<String, JsonObject> entry : itemDatabase.entrySet()) {
            String key = entry.getKey();
            JsonObject value = entry.getValue();
    
            // Convert JsonObject values to types compatible with Jackson
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", key);
            for (Map.Entry<String, com.google.gson.JsonElement> element : value.entrySet()) {
                String propertyName = element.getKey();
                com.google.gson.JsonElement propertyValue = element.getValue();
    
                // Convert property values to types compatible with Jackson
                Object convertedValue = convertToJacksonCompatibleType(propertyValue);
                itemMap.put(propertyName, convertedValue);
            }
    
            itemList.add(itemMap);
        }
    
        return itemList;
    }

    private static Object convertToJacksonCompatibleType(com.google.gson.JsonElement value) {
        if (value.isJsonPrimitive()) {
            if (value.getAsJsonPrimitive().isNumber()) {
                return value.getAsJsonPrimitive().getAsNumber();
            } else if (value.getAsJsonPrimitive().isBoolean()) {
                return value.getAsJsonPrimitive().getAsBoolean();
            } else if (value.getAsJsonPrimitive().isString()) {
                return value.getAsJsonPrimitive().getAsString();
            }
        }
        // Handle other types as needed
    
        return null;
    }

    public static int getQuantity(String id) {
        JsonObject itemData = itemDatabase.get(id);

        if (itemData != null && itemData.has("quantity")) {
            return itemData.get("quantity").getAsInt();
        } else {
            // Handle the case where the item or quantity property is not found
            return -1; // Or throw an exception, depending on your requirements
        }
    }
}
