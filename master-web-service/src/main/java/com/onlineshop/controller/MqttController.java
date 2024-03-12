package com.onlineshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

@RestController
public class MqttController {

	@PostMapping("/purchase-item")
	public ResponseEntity<?> purchaseItem(@RequestBody String mqttMessage){
		try {
			System.out.println("\nReceived purchase request\n");
			System.out.println("\n\nInitiating purchase...\n\n");

			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);

			// publish event to topic
			MqttClient client = new MqttClient("tcp://mosquitto:1883", MqttClient.generateClientId());
			client.connect();

			String id = convertObject.get("id").toString();
			id = id.replaceAll("^\"|\"$", "");
			String topic = "check_item_in_warehouse";

			JsonObject dataObject = convertObject.getAsJsonObject("data");
			String name = dataObject.get("name").getAsString();
			int quantity = dataObject.get("quantity").getAsInt();
			String category = dataObject.get("category").getAsString();
			String country = dataObject.get("country").getAsString();
			String city = dataObject.get("city").getAsString();
			JsonElement errorElement = dataObject.get("error");
			String error;
			// Check if the "error" property exists and is not null
			if (errorElement != null) {
				error = errorElement.getAsJsonPrimitive().getAsString();
			} else {
				// Handle the case where "error" is not present or is null
				error = null;
			}

			// Create a new JSON object
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("id", id);
			resultObject.addProperty("name", name);
			resultObject.addProperty("category", category);
			resultObject.addProperty("country", country);
			resultObject.addProperty("city", city);
			resultObject.addProperty("quantity", quantity);
			if (error != null) {
				resultObject.addProperty("error", error);
			}

			String msg = resultObject.toString();

			System.out.println("\nPublish to topic: " + topic);
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
