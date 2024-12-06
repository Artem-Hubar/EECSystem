package org.example.service.inflexdb;

import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;
import org.example.entity.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InflexDBService implements InflexDBRepository {
    @Override
    public void writeData(Device device) {
        String lineProtocol = device.toInfluxDBLineProtocol();
        writeApi.writeRecord(WritePrecision.S, lineProtocol);
    }

    @Override
    public List<Device> getAllDevices() {
        QueryApi queryApi = client.getQueryApi();

        // Запрос всех данных из бакета
        String fluxQuery = String.format(
                "from(bucket: \"%s\") " +
                        "|> range(start: -30d) " + // Определяем временной диапазон
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"current_line_sensor\" or r[\"_measurement\"] == \"transporter\") " +
                        "|> last()",
                bucket
        );

        List<FluxTable> tables = queryApi.query(fluxQuery);

        Map<String, Device> deviceMap = new HashMap<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                String measurement = record.getMeasurement();
                String deviceId = (String) record.getValueByKey("device");
                String field = (String) record.getValueByKey("_field");
                Object value = record.getValueByKey("_value");

                // Проверяем, есть ли устройство уже в карте
                Device device = deviceMap.get(deviceId);

                // Создаем экземпляр устройства, если оно еще не добавлено
                if (device == null) {
                    if ("transporter".equals(measurement)) {
                        device = new Transformer();
                    } else if ("current_line_sensor".equals(measurement)) {
                        device = new CurrentLineSensor();
                    }
                    if (device != null) {
                        device.setSensorId(deviceId);
                        deviceMap.put(deviceId, device);
                    }
                }

                // Присваиваем значения на основе поля (_field)
                if (device != null) {
                    if (device instanceof Transformer && "turnsRation".equals(field)) {
                        ((Transformer) device).setTurnsRation(String.valueOf(value));
                    } else if (device instanceof CurrentLineSensor) {
                        if ("voltage".equals(field)) {
                            ((CurrentLineSensor) device).setVoltage(value != null ? ((Number) value).doubleValue() : 0.0);
                        }
                        if ("current".equals(field)) {
                            ((CurrentLineSensor) device).setCurrent(value != null ? ((Number) value).doubleValue() : 0.0);
                        }
                    }
                }
            }
        }

        // Возвращаем список устройств
        return new ArrayList<>(deviceMap.values());
    }

}
