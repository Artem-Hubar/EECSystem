package org.example.service.inflexdb;

import com.influxdb.client.domain.WritePrecision;
import org.example.entity.Device;

public class InflexDBService implements InflexDBRepository {
    @Override
    public void writeData(Device device) {
        String lineProtocol = device.toInfluxDBLineProtocol();
        writeApi.writeRecord(WritePrecision.S, lineProtocol);
    }
}
