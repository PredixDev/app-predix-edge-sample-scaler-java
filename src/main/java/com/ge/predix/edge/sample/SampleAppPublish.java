package com.ge.predix.edge.sample;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 212573925 on 7/11/18.
 */
public class SampleAppPublish {
	private static final Logger log = LoggerFactory.getLogger(SampleAppPublish.class);

	static String broker = System.getProperty("BROKER");
	static String clientId = System.getProperty("PUB_CLIENT_ID"); // give this a name of your choosing;
	static String pubTopic = System.getProperty("PUB_TOPIC"); // name of topic we will publish transformed data
	// to/cloud_gateway is listening for
	int qos = Integer.getInteger(System.getProperty("MESSAGE_QOS")); // name of topic we will publish transformed data
	private static MqttClient pubClient = null;
	static {
		try {
			pubClient = new MqttClient(broker, clientId);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			log.debug("PubClient connecting to broker: " + broker);

			pubClient.connect(connOpts);
			log.debug("PubClient Connected");
		} catch (MqttException e) {
			log.error("reason=" + e.getReasonCode(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param message
	 */
	public static void publish(MqttMessage message) {
		int qos = 1;
		try {
			if (!pubClient.isConnected()) {
				log.info("publishClient not connected, reconnecting");
				pubClient.connect();
			}
				
			message.setQos(qos);
			pubClient.publish(pubTopic, message);
			log.debug("Message published");
		} catch (MqttException e) {
			log.error("reason=" + e.getReasonCode(), e);
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			log.error("Check that your environment variables are set", e);
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			log.error("Check that your environment variables are set", e);
			throw new RuntimeException(e);
		}
	}
}
