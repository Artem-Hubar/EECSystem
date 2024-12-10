package org.example;

import org.example.service.InflexDBService;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.util.ArrayList;
import java.util.List;

public class ObjectService {
    public static List<Object> loadObjects() {
        List<Object> objects = new ArrayList<>();
        InflexDBService inflexDBService = new InflexDBService();
        objects.addAll(inflexDBService.getAllDevices());
        objects.add(MQTTPublisherFactory.getPublisher("testClient"));
        return objects;
    }
}
