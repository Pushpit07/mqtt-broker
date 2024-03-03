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

			String topic = "order_item";
			
			client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();

            client.connect(options);

            if (client.isConnected()) {
                client.setCallback(new MqttCallback() {
                    public void messageArrived(String _topic, MqttMessage message) throws Exception {
                        System.out.println("\ntopic: " + _topic);
                        System.out.println("qos: " + message.getQos());
                        System.out.println("data: " + new String(message.getPayload()) + "\n");

                        placeOrder(message);
                    }

                    public void connectionLost(Throwable cause) {
                        System.out.println("connectionLost: " + cause.getMessage());
                    }

                    public void deliveryComplete(IMqttDeliveryToken token) {
                        System.out.println("deliveryComplete: " + token.isComplete());
                    }
                });

                client.subscribe(topic, subQos);
                System.out.println("Subscribed to topic: " + topic);
            }
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    private void placeOrder(MqttMessage message) {
        try {
            String warehouseUpdateTopic = "warehouse_update";
            client.publish(warehouseUpdateTopic, message);
            // Add code to place order here
            // Check for payment and update order status
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
