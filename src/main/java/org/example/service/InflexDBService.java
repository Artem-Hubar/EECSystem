package org.example.service;

import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;
import org.example.entity.Transformer;
import org.example.service.inflexdb.InflexDBRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InflexDBService{

    private final InflexDBRepository inflexDBRepository;

    public InflexDBService() {
        this.inflexDBRepository = new InflexDBRepository();
    }

    public void writeData(Device device) {
       inflexDBRepository.writeData(device);
    }

    public List<Device> getAllDevices() {
       return inflexDBRepository.getAllDevices();
    }

}
