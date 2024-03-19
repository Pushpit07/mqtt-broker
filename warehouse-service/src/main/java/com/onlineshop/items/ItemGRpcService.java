package com.onlineshop.controller;

import com.onlineshop.grpc.itemGrpc.itemImplBase;
import com.onlineshop.grpc.Item.GetItemsRequest;
import com.onlineshop.grpc.Item.GetItemsResponse;
import com.onlineshop.grpc.Item.AddItemRequest;
import com.onlineshop.grpc.Item.AddItemResponse;
import com.onlineshop.grpc.Item.ItemData;
import com.onlineshop.grpc.Item.DeleteItemRequest;
import com.onlineshop.grpc.Item.DeleteItemResponse;

import io.grpc.stub.StreamObserver;

import com.onlineshop.controller.ItemStorage;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.util.JsonFormat;


public class ItemGRpcService extends itemImplBase {

    @Override
	public void getAllItems(GetItemsRequest request, StreamObserver<GetItemsResponse> responseObserver) {
		System.out.println("\ngRPC: get all items from db");
		
		GetItemsResponse.Builder response = GetItemsResponse.newBuilder();
		// Logic to get all items from db
		try {
			// perform get all items from db operation
			List<Map<String, Object>> allItems = ItemStorage.getAllItems();
			// Convert the List to a JSON string using Jackson ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(allItems);

			System.out.println("\nGet all items from db: " + jsonString);
			// return success message
			response.setResponseCode(0).setResponseMessage(jsonString);
		} catch(Exception ex) {
			ex.printStackTrace();
			response.setResponseCode(100).setResponseMessage("INVALID REQUEST");
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

	@Override
	public void addItem(AddItemRequest request, StreamObserver<AddItemResponse> responseObserver) {
		System.out.println("\ngRPC: add item to db");

		AddItemResponse.Builder response = AddItemResponse.newBuilder();
		try {
			// Convert Protocol Buffers message to JSON string
			String jsonString = JsonFormat.printer().print(request);

			JsonObject convertObject = new Gson().fromJson(jsonString, JsonObject.class);

			MqttClient client = new MqttClient("tcp://mosquitto:1883", MqttClient.generateClientId());
			client.connect();

			String id = request.getId();
			id = id.replaceAll("^\"|\"$", "");
			JsonObject dataObject = convertObject.getAsJsonObject("data");

			// perform add to db operation
			ItemStorage.addItem(id, dataObject);
			System.out.println("\nItem with id: " + id + " added to db");

			String country = dataObject.get("country").getAsString();
			String city = dataObject.get("city").getAsString();
			String category = dataObject.get("category").getAsString();
			
			String topic = "item/" + country + "/" + city + "/" + category + "/" + id + "/back_in_stock";
			String msg = convertObject.get("data").toString();

			System.out.println("\nPublish to topic: " + topic);
			System.out.println("Publish data: " + msg);

			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());
			// publish event to topic
			client.publish(topic, message);
			client.disconnect();

			response.setResponseCode(0).setResponseMessage("Success");
		} catch(Exception ex) {
			ex.printStackTrace();
			response.setResponseCode(100).setResponseMessage("INVALID REQUEST");
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

	@Override
	public void removeItem(DeleteItemRequest request, StreamObserver<DeleteItemResponse> responseObserver) {
		System.out.println("\ngRPC: add item to db");

		DeleteItemResponse.Builder response = DeleteItemResponse.newBuilder();
		try {
			// Convert Protocol Buffers message to JSON string
			String jsonString = JsonFormat.printer().print(request);

			JsonObject convertObject = new Gson().fromJson(jsonString, JsonObject.class);

			MqttClient client = new MqttClient("tcp://mosquitto:1883", MqttClient.generateClientId());
			client.connect();

			String id = request.getId();
			id = id.replaceAll("^\"|\"$", "");

			// Check if item is present in db
			if(ItemStorage.getItemById(id) == null) {
				System.out.println("\nItem with id: " + id + " not found in db");
				response.setResponseCode(100).setResponseMessage("INVALID REQUEST: Item not found in db");
			} else {
				// perform remove from db operation
				ItemStorage.removeItem(id);
				System.out.println("\nItem with id: " + id + " removed from db");

				response.setResponseCode(0).setResponseMessage("Success");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			response.setResponseCode(100).setResponseMessage("INVALID REQUEST");
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}

}