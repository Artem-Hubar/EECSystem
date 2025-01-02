package org.example.mqtt;

import junit.framework.TestCase;
import org.example.entity.Device;
import org.example.entity.Generator;
import org.example.entity.SwitchBoard;
import org.example.entity.Transformer;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;
import org.jetbrains.annotations.NotNull;

public class MQTTPublisherTest extends TestCase {
    public void testMQTTWrite() {
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("testClient");
        Device device = getSwitchBoard();
        mqttPublisher.writeData(device);
    }

    private Device getTransformer() {
        Transformer transformer = new Transformer();
        transformer.setSensorId("device1");
        transformer.setTurnsRation(1.0);
        return transformer;
    }

    private @NotNull Generator getGenerator() {
        Generator generator = new Generator();
        generator.setSensorId("device1");
        generator.setVoltage(220);
        generator.setCurrent(5.0);
        generator.setIsWorking(true);
        return generator;
    }

    private Device getSwitchBoard(){
        SwitchBoard switchBoard = new SwitchBoard();
        switchBoard.setSensorId("device2");
        switchBoard.setCurrent(120);
        return switchBoard;
    }
}
