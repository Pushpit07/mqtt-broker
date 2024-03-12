package com.onlineshop.runner;

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

			String topic1 = "order_item";
            String topic2 = "order_item_compensation";
			
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
                            placeOrder(message);
                        } else if (topic.equals(topic2)) {
                            placeOrderCompensatingAction(message);
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
                System.out.println("Subscribed to topic: " + topic1);
                System.out.println("Subscribed to topic: " + topic2);
            }
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    private void placeOrder(MqttMessage message) {
        try {
            //
            // Payment logic here
            //
            // If payment fails, publish to compensation topic
            String error = getErrorFromMessage(message);
            if ("payment_failed".equals(error)) {
               throw new IllegalStateException("Payment failed: " + error);
            }
            // Else publish to warehouse update topic
            String warehouseUpdateTopic = "warehouse_update";
            client.publish(warehouseUpdateTopic, message);
        } catch(Exception ex) {
            try {
                // If payment fails, publish to compensation topic
                String placeOrderCompensationTopic = "order_item_compensation";
                client.publish(placeOrderCompensationTopic, message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    private void placeOrderCompensatingAction(MqttMessage message) {
        try {
            System.out.println("Compensation action for placing order: " + message);
            // Refund logic here
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
