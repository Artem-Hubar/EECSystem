package org.example.service.inflexdb;

import com.influxdb.client.InfluxDBClient;

import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class InflexDBRepository {
    private final String URL = "http://localhost:8086";
    private final String TOKEN = "UzGyKeMA-1Vy1mMNGWjdQSCbIi4MUzfdSlO0J2iVBCqXH-LJOI6pn5MYN9Nh1hAzKTaArKIAz3B3A_9Ce9iePQ==";
    private final String ORG = "nure";
    private final String BUCKET = "eecsystem";
    private final InfluxDBClient CLIENT = InfluxDBClientFactory.create(URL, TOKEN.toCharArray(), ORG, BUCKET);
    private final WriteApiBlocking WRITE_API = CLIENT.getWriteApiBlocking();

    public void writeData(Device device) {
        String lineProtocol = device.toInfluxDBLineProtocol();
        WRITE_API.writeRecord(WritePrecision.S, lineProtocol);
    }

    public List<Device> getAllDevices() {
        QueryApi queryApi = CLIENT.getQueryApi();

        String fluxQuery = getAllDeviceFluxQuery();
        List<FluxTable> tables = queryApi.query(fluxQuery);

        Map<String, Device> deviceMap = new HashMap<>();
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                String measurement = record.getMeasurement();
                String deviceId = (String) record.getValueByKey("device");
                String field = (String) record.getValueByKey("_field");
                Object value = record.getValueByKey("_value");
                String keyMap = String.format("%s/%s", measurement, deviceId);
                Device device = deviceMap.get(keyMap);
                device = getOrCreateDevice(device, measurement);
                if (device != null) {
                    device.setSensorId(deviceId);
                    deviceMap.put(keyMap, device);
                }
                handleDevice(device, field, value, deviceId);
            }
        }
        return deviceMap.values().stream().toList();
    }

    private String getAllDeviceFluxQuery() {
        QueryCreator queryCreator = new QueryCreator();
        return  queryCreator.getAllDeviceFluxQuery(BUCKET);
    }
private String getDeviceByIdFluxQuery(String measurement, String deviceId) {
        QueryCreator queryCreator = new QueryCreator();
        return  queryCreator.getDeviceByIdFluxQuery(BUCKET, measurement, deviceId);
    }


    private void handleDevice(Device device, String field, Object value, String deviceId) {
        if (device != null) {
            DeviceHandler.handleField(device, field, value);
        } else {
            System.err.println("Устройство с deviceId " + deviceId + " не найдено или не создано.");
        }
    }

    private @Nullable Device getOrCreateDevice(Device device, String measurement) {
        if (device == null) {
            DeviceType deviceType = DeviceType.fromType(measurement);
            if (deviceType != null) {
                device = deviceType.createDevice();
            }
        }
        return device;
    }

    public Optional<Device> getDeviceById(DeviceType deviceType, String sensorId) {
        QueryApi queryApi = CLIENT.getQueryApi();

        // Формируем Flux-запрос для конкретного устройства
        String fluxQuery = getDeviceByIdFluxQuery(deviceType.getMeasurement(), sensorId);

        // Выполняем запрос
        List<FluxTable> tables = queryApi.query(fluxQuery);

        // Процесс обработки результата
        Device device = null;

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                String recordMeasurement = record.getMeasurement();
                String recordDeviceId = (String) record.getValueByKey("device");
                String field = (String) record.getValueByKey("_field");
                Object value = record.getValueByKey("_value");

                // Если deviceId совпадает с нужным, создаём или получаем устройство
                if (recordDeviceId.equals(sensorId)) {
                    device = getOrCreateDevice(device, recordMeasurement);
                    if (device != null) {
                        device.setSensorId(recordDeviceId); // Устанавливаем ID устройства
                    }
                    handleDevice(device, field, value, recordDeviceId);
                }
            }
        }

        return Optional.ofNullable(device);
    }
}
