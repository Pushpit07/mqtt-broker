package com.onlineshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Set;

@RestController
public class MqttController {

    private Set<String> subscribedTopics = new HashSet<>();
    private MqttClient client;

	@PostMapping("/subscribe-to-item")
	public ResponseEntity<?> subscribeToItem(@RequestBody String mqttMessage) {
		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
			String broker = "tcp://mosquitto:1883";
			String clientId = MqttClient.generateClientId();
            int subQos = 2;

            String id;
            String topic = "item";
            JsonObject dataObject = convertObject.getAsJsonObject("data");

            if(convertObject.has("id")) {
                id = convertObject.get("id").toString();
                id = id.replaceAll("^\"|\"$", "");
            } else{
                id = null;
            } 

            if (dataObject.has("country")) {
                String country = dataObject.get("country").getAsString();
                topic = topic + "/" + country;
            } else {
                topic = topic + "/+";
            }

            if (dataObject.has("city")) {
                String city = dataObject.get("city").getAsString();
                topic = topic + "/" + city;
            } else {
                topic = topic + "/+";
            }

            if (dataObject.has("category")) {
                String category = dataObject.get("category").getAsString();
                topic = topic + "/" + category;
            } else {
                topic = topic + "/+";
            }

            if (id != null) {
                topic = topic + "/" + id + "/back_in_stock";
            } else {
                topic = topic + "/+" + "/back_in_stock";
            }  
			
			client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();

            if (!subscribedTopics.contains(topic)) {
                client.connect(options);

                if (client.isConnected()) {
                    client.setCallback(new MqttCallback() {
                        public void messageArrived(String _topic, MqttMessage message) throws Exception {
                            System.out.println("\nCurrently Subscribed Topics: " + subscribedTopics);
                            System.out.println("\ntopic: " + _topic);
                            System.out.println("qos: " + message.getQos());
                            System.out.println("data: " + new String(message.getPayload()) + "\n");
                        }

                        public void connectionLost(Throwable cause) {
                            System.out.println("connectionLost: " + cause.getMessage());
                        }

                        public void deliveryComplete(IMqttDeliveryToken token) {
                            // System.out.println("deliveryComplete: " + token.isComplete());
                        }
                    });

                    client.subscribe(topic, subQos);
                    subscribedTopics.add(topic);
                    System.out.println("Subscribed to topic: " + topic);
                }
            } else {
                System.out.println("Already subscribed to topic: " + topic);
            }

			return ResponseEntity.ok("Success");
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

    @PostMapping("/unsubscribe-from-item")
    public ResponseEntity<?> unsubscribeFromItem(@RequestBody String mqttMessage) {
        try {
            JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
            
            String id;
            String topic = "item";
            JsonObject dataObject = convertObject.getAsJsonObject("data");

            if(convertObject.has("id")) {
                id = convertObject.get("id").toString();
                id = id.replaceAll("^\"|\"$", "");
            } else{
                id = null;
            } 

            if (dataObject.has("country")) {
                String country = dataObject.get("country").getAsString();
                topic = topic + "/" + country;
            } else {
                topic = topic + "/+";
            }

            if (dataObject.has("city")) {
                String city = dataObject.get("city").getAsString();
                topic = topic + "/" + city;
            } else {
                topic = topic + "/+";
            }

            if (dataObject.has("category")) {
                String category = dataObject.get("category").getAsString();
                topic = topic + "/" + category;
            } else {
                topic = topic + "/+";
            }

            if (id != null) {
                topic = topic + "/" + id + "/back_in_stock";
            } else {
                topic = topic + "/+" + "/back_in_stock";
            }

            if (subscribedTopics.contains(topic)) {
                if (client.isConnected()) {
                    client.unsubscribe(topic);
                    client.disconnect();
                    client.close();
                    subscribedTopics.remove(topic);
                    System.out.println("Unsubscribed from topic: " + topic);
                }
            } else {
                System.out.println("Not subscribed to topic: " + topic);
            }

            return ResponseEntity.ok("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

}