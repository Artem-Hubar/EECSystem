package org.example.mqtt;

import junit.framework.TestCase;
import org.example.entity.Device;
import org.example.service.inflexdb.InflexDBRepository;

import java.util.ArrayList;
import java.util.List;

public class DeviceGetMethodTest extends TestCase {
    public void testGetMQTTTopic() {
        InflexDBRepository inflexDBRepository = new InflexDBRepository();
        List<Device> devices = inflexDBRepository.getAllDevices();
        for (Device device : devices){
            System.out.println(device.getMqttTopic());
        }
    }

    public void testGetMQTTPayload() {
        InflexDBRepository inflexDBRepository = new InflexDBRepository();
        List<Device> devices = inflexDBRepository.getAllDevices();
        for (Device device : devices){
            System.out.println(device.getMqttPayload());
        }
    }

    public void testGetInflex() {
        InflexDBRepository inflexDBRepository = new InflexDBRepository();
        List<Device> devices = inflexDBRepository.getAllDevices();
        for (Device device : devices){
            System.out.println(device.toInfluxDBLineProtocol());
        }
    }
}
