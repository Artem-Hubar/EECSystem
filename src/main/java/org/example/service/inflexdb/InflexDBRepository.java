package org.example.service.inflexdb;
import com.influxdb.client.InfluxDBClient;

import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import org.example.entity.Device;

import java.util.List;


public interface InflexDBRepository {
    String url = "http://localhost:8086"; // URL вашего InfluxDB сервера
    String token = "UzGyKeMA-1Vy1mMNGWjdQSCbIi4MUzfdSlO0J2iVBCqXH-LJOI6pn5MYN9Nh1hAzKTaArKIAz3B3A_9Ce9iePQ==";         // Ваш токен
    String org = "nure";             // Ваша организация
    String bucket = "eecsystem";       // Ваш бакет
    InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    WriteApiBlocking writeApi =  client.getWriteApiBlocking();

    void writeData(Device device);

    List<Device> getAllDevices();
}
