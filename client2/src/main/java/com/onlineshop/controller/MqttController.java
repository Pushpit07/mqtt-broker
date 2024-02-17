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

@RestController
public class MqttController {

	@PostMapping("/subscribe-to-item")
	public ResponseEntity<?> publish(@RequestBody String mqttMessage){
		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
			String broker = "tcp://mosquitto:1883";
			String clientId = MqttClient.generateClientId();
			String topic = "item_back_in_stock";
			int subQos = 2;
			String msg = convertObject.get("message").toString();
			
			MqttClient client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();
            client.connect(options);

			if (client.isConnected()) {
                client.setCallback(new MqttCallback() {
                    public void messageArrived(String _topic, MqttMessage message) throws Exception {
                        System.out.println("\ntopic: " + _topic);
                        System.out.println("qos: " + message.getQos());
                        System.out.println("message: " + new String(message.getPayload()));
                    }

                    public void connectionLost(Throwable cause) {
                        System.out.println("connectionLost: " + cause.getMessage());
                    }

                    public void deliveryComplete(IMqttDeliveryToken token) {
                        System.out.println("deliveryComplete: " + token.isComplete());
                    }
                });

                client.subscribe(topic, subQos);
            }

            // client.disconnect();
            // client.close();
			return ResponseEntity.ok("Success");
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

}