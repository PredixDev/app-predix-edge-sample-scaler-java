package com.ge.predix.edge.sample;

/**
 * Created by 212573925 on 7/11/18.
 */
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 212307911
 *
 */
@Component
@ComponentScan(basePackages = "com.ge.predix.edge.sample")
public class SampleAppSubscr {
	private static final Logger log = LoggerFactory.getLogger(SampleAppSubscr.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(SampleAppSubscr.class);
			SampleAppSubscr subscr = context.getBean(SampleAppSubscr.class);
			subscr.subscribeAndScale(args);

		} catch (IllegalArgumentException e) {
			log.error("Check that your environment variables are set", e);
			// swallow error, we are in main
		} catch (MqttException e) {
			log.error("main - reason " + e.getReasonCode(), e);
		} catch (NullPointerException e) {
			log.error("main - Check that your environment variables are set", e);
			// swallow error, we are in main
		} catch (Throwable e) {
			log.error("main - unexpected error", e);
			// swallow error, we are in main
		} finally {
			if ( context != null )
				((AnnotationConfigApplicationContext) context).close();
		}
	}

	private void subscribeAndScale(String[] args) throws MqttException, MqttSecurityException {
		MqttClient subscribeClient = null;
		String subTopic = null;
		String broker = null;
		String clientId = null;

		if (args.length > 0) {
			if (args[0].equals("local")) {
				// If running locally, set system properties manually
				System.setProperty("PUB_CLIENT_ID", "JavaSampleAppPubClient");
				System.setProperty("SUB_CLIENT_ID", "JavaSampleAppSubClient");
				System.setProperty("BROKER", "tcp://127.0.0.1:1883");
				System.setProperty("PUB_TOPIC", "timeseries_data");
				System.setProperty("SUB_TOPIC", "app_data");
				//System.setProperty("TAG_NAME", "My.App.DOUBLE1");
			}
		} else {
			// otherwise, set the properties based on the env. variables defined in the
			// docker-compose file
			System.setProperty("PUB_CLIENT_ID", System.getenv("PUB_CLIENT_ID"));
			System.setProperty("SUB_CLIENT_ID", System.getenv("SUB_CLIENT_ID"));
			System.setProperty("BROKER", System.getenv("BROKER"));
			System.setProperty("PUB_TOPIC", System.getenv("PUB_TOPIC"));
			System.setProperty("SUB_TOPIC", System.getenv("SUB_TOPIC"));
			//System.setProperty("TAG_NAME", System.getenv("TAG_NAME"));
		}
		log.info(System.getProperties().toString());
		subTopic = System.getProperty("SUB_TOPIC");
		broker = System.getProperty("BROKER");
		clientId = System.getProperty("SUB_CLIENT_ID");

		subscribeClient = new MqttClient(broker, clientId);
		subscribeClient.setCallback(new SampleCallback());
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		log.info("Connecting to broker: " + broker);

		subscribeClient.connect(connOpts);
		log.info("Connected");

		subscribeClient.subscribe(subTopic);
	}
}
