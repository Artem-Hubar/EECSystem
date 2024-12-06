package org.example.mqtt;

import junit.framework.TestCase;
import org.example.entity.Transformer;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

public class MQTTPublisherTest extends TestCase {
    public void testMQTTWrite() {
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("testClient");
        Transformer transporter = new Transformer();
        transporter.setSensorId("device1");
        transporter.setTurnsRation("2");
        mqttPublisher.writeData(transporter);
    }
}
