package org.example.inflexdb;

import junit.framework.TestCase;
import org.example.entity.Device;
import org.example.entity.CurrentLineSensor;
import org.example.service.inflexdb.InflexDBService;

public class InflexDBServiceTest extends TestCase {
    public void testWriteData() {
        CurrentLineSensor device = new CurrentLineSensor();
        device.setVoltage(201);
        device.setCurrent(3);
        device.setSensorId("testDevice");
        InflexDBService inflexDBService = new InflexDBService();
        for (int i =0 ; i<10; i++){
            inflexDBService.writeData(device);

        }
    }
}
