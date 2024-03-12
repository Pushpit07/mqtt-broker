package com.onlineshop.runner;

import com.onlineshop.controller.ItemStorage;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private MqttClient client;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Code to be executed at startup
        try {
			String broker = "tcp://mosquitto:1883";
			String clientId = MqttClient.generateClientId();
            int subQos = 2;

            String topic1 = "check_item_in_warehouse";
			String topic2 = "warehouse_update";
            String topic3 = "warehouse_update_compensation";
			
			client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();

            client.connect(options);

            if (client.isConnected()) {
                client.setCallback(new MqttCallback() {
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println("\ntopic: " + topic);
                        System.out.println("qos: " + message.getQos());
                        System.out.println("data: " + new String(message.getPayload()) + "\n");

                        if (topic.equals(topic1)) {
                            checkWarehouse(message);
                        } else if (topic.equals(topic2)) {
                            updateWarehouse(message);
                        } else if (topic.equals(topic3)) {
                            updateWarehouseCompensatingAction(message);
                        }
                    }

                    public void connectionLost(Throwable cause) {
                        System.out.println("connectionLost: " + cause.getMessage());
                    }

                    public void deliveryComplete(IMqttDeliveryToken token) {
                        // System.out.println("deliveryComplete: " + token.isComplete());
                    }
                });

                client.subscribe(topic1, subQos);
                client.subscribe(topic2, subQos);
                client.subscribe(topic3, subQos);
                System.out.println("Subscribed to topic: " + topic1);
                System.out.println("Subscribed to topic: " + topic2);
                System.out.println("Subscribed to topic: " + topic3);
            }
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    private void checkWarehouse(MqttMessage message) {
        try {
            // Parse the incoming message payload 
            String payload = new String(message.getPayload());
            JsonObject orderData = new Gson().fromJson(payload, JsonObject.class);

            // Extract required data from the payload
            String itemId = orderData.get("id").getAsString();
            int requestedQuantity = orderData.get("quantity").getAsInt();
    
            // Retrieve the item from the warehouse
            JsonObject foundItem = ItemStorage.getItemById(itemId);
    
            if (foundItem == null) {
                // Item with the given ID not found
                System.out.println("Item with ID " + itemId + " not found in the warehouse.");
                // TODO: Add error handling, e.g., publish an error message to a specific topic
                return;
            }
    
            // Check if the quantity in the warehouse is enough
            int availableQuantity = ItemStorage.getQuantity(itemId);
    
            if (availableQuantity < requestedQuantity) {
                // Insufficient quantity in the warehouse
                System.out.println("Insufficient quantity for item " + itemId + " in the warehouse.");
                // TODO: Add error handling, e.g., publish an error message to a specific topic
                return;
            }
    
            // Sufficient quantity in the warehouse, proceed with the order
            System.out.println("Item " + itemId + " found in the warehouse with sufficient quantity.");
            // TODO: Add success handling, e.g., publish a success message to a specific topic
    
            // Assuming an order success topic
            String orderItemTopic = "order_item";
            client.publish(orderItemTopic, message);
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO: Add generic error handling, e.g., publish an error message to a specific topic
        }
    }

    private void updateWarehouse(MqttMessage message) {
        try {
            System.out.println("Updating item in warehouse: " + message);
            //
            // Perform the update operation
            //
            // Parse the incoming message payload 
            String payload = new String(message.getPayload());
            JsonObject updateData = new Gson().fromJson(payload, JsonObject.class);
            
            // Extract required data from the payload
            String itemId = updateData.get("id").getAsString();
            int quantityToSubtract = updateData.get("quantity").getAsInt();

            // Retrieve the item from the warehouse
            JsonObject itemData = ItemStorage.getItemById(itemId);
            // Retrieve the current quantity
            int currentQuantity = itemData.get("quantity").getAsInt();

            // Perform the update operation (subtract quantity)
            int newQuantity = currentQuantity - quantityToSubtract;
            itemData.addProperty("quantity", newQuantity);

            // If update fails, publish to compensation topic
            String error = getErrorFromMessage(message);
            if ("warehouse_update_failed".equals(error)) {
               throw new IllegalStateException("Warehouse update failed: " + error);
            }
            // Else publish to deliver item topic
            String topic = "deliver_item";
            client.publish(topic, message);
        } catch(Exception ex) {
            try {
                // If update fails, publish to compensation topic
                String warehouseUpdateCompensationTopic = "warehouse_update_compensation";
                client.publish(warehouseUpdateCompensationTopic, message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateWarehouseCompensatingAction(MqttMessage message) {
        try {
            System.out.println("Compensation action for updating item in warehouse: " + message);
            // Revert back the update operation (quantity)
            // Parse the incoming message payload
            String payload = new String(message.getPayload());
            JsonObject updateData = new Gson().fromJson(payload, JsonObject.class);

            // Extract required data from the payload
            String itemId = updateData.get("id").getAsString();
            int quantityToAdd = updateData.get("quantity").getAsInt();

            // Retrieve the item from the warehouse
            JsonObject itemData = ItemStorage.getItemById(itemId);
            // Retrieve the current quantity
            int currentQuantity = itemData.get("quantity").getAsInt();

            // Perform the update operation (add quantity)
            int newQuantity = currentQuantity + quantityToAdd;
            itemData.addProperty("quantity", newQuantity);

            // Publish to compensation topic
            String placeOrderCompensationTopic = "order_item_compensation";
            client.publish(placeOrderCompensationTopic, message);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getErrorFromMessage(MqttMessage message) {
        // Parse the incoming message payload 
        String payload = new String(message.getPayload());
        JsonObject orderData = new Gson().fromJson(payload, JsonObject.class);

        if (orderData.has("error")) {
            return orderData.get("error").getAsString();
        } else {
            return null;
        }
    }
}
