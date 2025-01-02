package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.service.DeviceUpdater;
import org.example.service.InflexDBService;
import org.example.subscriber.MQTTPayloadBuilder;
import org.example.utlity.ClassMethodService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Optional;


public abstract class Device {
    private final StringProperty sensorId = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    @Getter
    @Setter
    protected DeviceType deviceType;

    public void updateDevice(){
        DeviceUpdater deviceUpdater = new DeviceUpdater();
        deviceUpdater.updateDevice(this,deviceType, getSensorId());
    }

    @JsonIgnore
    public String toInfluxDBLineProtocol(){
        String measurement = deviceType.getMeasurement();
        String tags = "device=" + getSensorId();
        String fields = getInfluxDBFields(this);
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    private String getInfluxDBFields(Device device) {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (int posF = 0 ; posF<fields.length; posF++){
            Field field = fields[posF];
            ClassMethodService classMethodService = new ClassMethodService();
            Method method = classMethodService.getGetterMethodByFieldName(field.getName(),this.getClass());
            Object value;
            try {
                value = method.invoke(device);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            String mqttFieldString = String.format("%s=%s",field.getName(), value);
            stringBuilder.append(mqttFieldString);
            if (posF < fields.length-1){
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }



    @JsonIgnore
    public String getMqttPayload() throws RuntimeException {
        MQTTPayloadBuilder mqttPayloadBuilder = new MQTTPayloadBuilder();
        return mqttPayloadBuilder.getPayloadBuilder(this);
    }

    @JsonIgnore
    public String getMqttTopic() {
        return String.format("client/%s/%s/data", deviceType.getMeasurement(), getSensorId());
    }

    public String getSensorId() {
        return sensorId.get();
    }

    public void setSensorId(String sensorId) {
        this.sensorId.set(sensorId);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
