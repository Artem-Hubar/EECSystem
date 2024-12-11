package org.example.client.service;

import org.example.client.entity.CurrentSensorUI;
import org.example.entity.CurrentLineSensor;
import org.example.service.InflexDBService;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ObjectService {
    public static List<Object> loadObjects() {
        InflexDBService inflexDBService = new InflexDBService();
        List<Object> objects = new ArrayList<>(inflexDBService.getAllDevices());
        objects.add(MQTTPublisherFactory.getPublisher("testClient"));
        return objects;
    }
}
