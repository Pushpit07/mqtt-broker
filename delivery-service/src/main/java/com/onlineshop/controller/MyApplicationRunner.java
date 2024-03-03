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

            String topic = "deliver_item";
			
			client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();

            client.connect(options);

            if (client.isConnected()) {
                client.setCallback(new MqttCallback() {
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println("\ntopic: " + topic);
                        System.out.println("qos: " + message.getQos());
                        System.out.println("data: " + new String(message.getPayload()) + "\n");
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
}
