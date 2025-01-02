package org.example.service;

import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.example.service.inflexdb.InflexDBRepository;

import java.util.List;
import java.util.Optional;

public class InflexDBService{

    private final InflexDBRepository inflexDBRepository;

    public InflexDBService() {
        this.inflexDBRepository = new InflexDBRepository();
    }

    public void writeData(Device device) {
       inflexDBRepository.writeData(device);
    }

    public Optional<Device> getDeviceById(DeviceType deviceType, String deviceId){
        return inflexDBRepository.getDeviceById(deviceType, deviceId);
    }

    public List<Device> getAllDevices() {
       return inflexDBRepository.getAllDevices();
    }

}
