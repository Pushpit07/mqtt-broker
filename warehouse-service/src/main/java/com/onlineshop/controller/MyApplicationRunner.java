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
                        } else {
                            updateWarehouse(message);
                        }
                    }

                    public void connectionLost(Throwable cause) {
                        System.out.println("connectionLost: " + cause.getMessage());
                    }

                    public void deliveryComplete(IMqttDeliveryToken token) {
                        System.out.println("deliveryComplete: " + token.isComplete());
                    }
                });

                client.subscribe(topic1, subQos);
                client.subscribe(topic2, subQos);
                System.out.println("Subscribed to topic: " + topic1);
                System.out.println("Subscribed to topic: " + topic2);
            }
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    private void checkWarehouse(MqttMessage message) {
        // TODO: Add error if item with id is not found
        // TODO: Add error if quantity is not enough
        // TODO: Add success if item is found and quantity is enough
        try {
            // System.out.println("getAllItems: " + ItemStorage.getAllItems());
            String orderItemTopic = "order_item";
            client.publish(orderItemTopic, message);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateWarehouse(MqttMessage message) {
        try {
            // Implement custom logic here
            System.out.println("Updating item in warehouse: " + message);
            deliverItem(message);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deliverItem(MqttMessage message) {
        try {
            String topic = "deliver_item";
            client.publish(topic, message);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
