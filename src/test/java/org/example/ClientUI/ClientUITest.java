package org.example.ClientUI;


import junit.framework.TestCase;
import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.example.service.InflexDBService;
import org.example.service.inflexdb.InflexDBRepository;

import java.util.Optional;

public class ClientUITest extends TestCase {
    public void testName() {
        InflexDBService inflexDBService = new InflexDBService();
        InflexDBRepository inflexDBRepository = new InflexDBRepository();
        Optional<Device> device = inflexDBRepository.getDeviceById(DeviceType.CURRENT_LINE_SENSOR, "consumer1");
        device.ifPresent(Device::updateDevice);
    }
}
