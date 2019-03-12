package com.ge.predix.edge.sample;

import java.lang.management.ManagementFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 212573925 on 7/13/18.
 */
public class SampleCallback implements MqttCallback {
	private static final Logger log = LoggerFactory.getLogger(SampleCallback.class);

	//private String tagName = System.getProperty("TAG_NAME"); // tag name of interest;
	private static final Runtime runtime = Runtime.getRuntime();
	private static int messageCount = 0;
	private static long messageCountMillis = 0;

	public void messageArrived(String topic, MqttMessage message) {
		// SAMPLE PAYLOAD JSON:
		// {"messageId":"flex-pipe","body":[{"datapoints":[[1531517440076,true,3]],"name":"My.App.BOOL1","attributes":{"machine_type":"opcua"}}]}

		try {
			long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis - messageCountMillis > 1000) {
				messageCountMillis = currentTimeMillis;
				messageCount = 0;
			}
			log.debug("A message has arrived on topic: " + topic + "messageCount/second=" + ++messageCount
					+ " threadCount=" + Thread.activeCount());

			String payload = message.toString();
			JSONObject obj = new JSONObject(payload);

			log.debug("ORIG MSG PAYLOAD=" + obj);

			JSONObject newObj = scaleData(obj);

			byte[] newPayload = newObj.toString().getBytes();
			MqttMessage newMessage = new MqttMessage(newPayload);

			SampleAppPublish.publish(newMessage);

			if (messageCount % 100 == 0)
				printMemory();
		} catch (IllegalArgumentException e) {
			log.error("Check that your environment variables are set", e);
			// this is a boundary, swallow error
		} catch (Throwable e) {
			log.error("messageArrived, UnexpectedError", e);
			// this is a boundary, swallow error
		}
	}

	public void connectionLost(Throwable e) {
		log.error("Connection Lost", e);
	}

	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}

	/**
	 * -
	 */
	@SuppressWarnings("nls")
	public static void printMemory() {

		int mb = 1024 * 1024;

		log.debug("##### Heap utilization statistics [MB] #####");

		// Print used memory
		log.debug("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		log.debug("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		log.debug("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		log.debug("Max Memory:" + runtime.maxMemory() / mb);

		// Print # of Threads
		log.debug("Threads:" + ManagementFactory.getThreadMXBean().getThreadCount());
	}

	/**
	 * Takes in a JSON object and a tag name, and if the tag name matches, scales it
	 * by 1000
	 *
	 * @param messageAsJson  
	 * @return -
	 */
	public static JSONObject scaleData(JSONObject messageAsJson) {
		int messageSize = messageAsJson.getJSONArray("body").length();

		for (int i = 0; i < messageSize; i++) {
			// get value of time series data entry
			JSONObject jsonObject = messageAsJson.getJSONArray("body").getJSONObject(i);
			Object oldValueObj = jsonObject.getJSONArray("datapoints").getJSONArray(0).get(1);
			Double oldValue = Double.valueOf(oldValueObj.toString());

			// multiply data value by 1000
			Double newValue = oldValue * 1000;
			jsonObject.getJSONArray("datapoints").getJSONArray(0).put(1, newValue);

			// modify message name
			String msgName = jsonObject.getString("name");
			String newName = msgName.concat(".scaled_x_1000");
			jsonObject.put("name", newName);

			log.debug("MODIFIED MSG PAYLOAD");
			log.debug(messageAsJson.toString());
		}

		return messageAsJson;
	}

}
