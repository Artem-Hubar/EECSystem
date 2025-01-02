package org.example.client.service;

import org.example.entity.Device;
import org.example.service.InflexDBService;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.util.ArrayList;
import java.util.List;

public class ObjectService {
    // Единственный экземпляр класса
    private static ObjectService instance;
    private final List<Object> objects = new ArrayList<>();

    // Приватный конструктор для предотвращения создания экземпляра извне
    public ObjectService() {
        InflexDBService inflexDBService = new InflexDBService();
        objects.addAll(inflexDBService.getAllDevices());

        Runnable runnable = () -> {
            while (true) {
                List<Object> objectsCopy = new ArrayList<>(objects);
                objectsCopy.forEach(object -> {
                    try {
                        if (object instanceof Device device) {
                            device.updateDevice();
                        }
                    } catch (Exception e) {
                        System.err.println("Error updating device: " + e.getMessage());
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        objects.add(MQTTPublisherFactory.getPublisher("testClient"));
    }

    // Метод для получения единственного экземпляра класса
    public static synchronized ObjectService getInstance() {
        if (instance == null) {
            instance = new ObjectService();
        }
        return instance;
    }

    // Метод для получения объектов
    public List<Object> getObjects() {
        return new ArrayList<>(objects);
    }
}
