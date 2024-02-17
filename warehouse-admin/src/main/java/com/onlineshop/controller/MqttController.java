package com.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.onlineshop.MqttGateway;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class MqttController {
	@Autowired
	MqttGateway mqtGateway;

	@PostMapping("/add-item-to-db")
	public ResponseEntity<?> publish(@RequestBody String mqttMessage){
		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
			// mqtGateway.sendToMqtt(convertObject.get("message").toString(), convertObject.get("topic").toString());
			// return ResponseEntity.ok("Success");

			MqttClient client = new MqttClient("tcp://mosquitto:1883", MqttClient.generateClientId());
			client.connect();

			String id = convertObject.get("id").toString();
			id = id.replaceAll("^\"|\"$", "");
			String topic = "item_back_in_stock" + "_" + id;
			String msg = convertObject.get("data").toString();

			System.out.println("Publish to topic: " + topic);
			System.out.println("Publish data: " + msg);

			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());
			client.publish(topic, message);
			client.disconnect();

			return ResponseEntity.ok("Success");
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

}
