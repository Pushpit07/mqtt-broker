package com.onlineshop.controller;

import com.onlineshop.controller.ItemStorage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class MqttController {

	@PostMapping("/add-item-to-db")
	public ResponseEntity<?> addItemToDb(@RequestBody String mqttMessage){
		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);

			MqttClient client = new MqttClient("tcp://mosquitto:1883", MqttClient.generateClientId());
			client.connect();

			String id = convertObject.get("id").toString();
			id = id.replaceAll("^\"|\"$", "");
            JsonObject dataObject = convertObject.getAsJsonObject("data");

			// perform add to db operation
			ItemStorage.addItem(id, dataObject);

			String country = dataObject.get("country").getAsString();
            String city = dataObject.get("city").getAsString();
            String category = dataObject.get("category").getAsString();
            
			String topic = "item/" + country + "/" + city + "/" + category + "/" + id + "/back_in_stock";
			String msg = convertObject.get("data").toString();

			System.out.println("Publish to topic: " + topic);
			System.out.println("Publish data: " + msg);

			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());
			// publish event to topic
			client.publish(topic, message);
			client.disconnect();

			return ResponseEntity.ok("Success");
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("fail");
		}
	}

}
