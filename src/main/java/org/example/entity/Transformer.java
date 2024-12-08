package org.example.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class Transformer extends Device{
    private StringProperty turnsRatio = new SimpleStringProperty();

    @Override
    public String toString() {
        return "Transformer{" +
                "id= " + super.getSensorId()+
                "turnsRation='" + turnsRatio.get() + '\'' +
                '}';
    }

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "transporter";
        String tags = "device=" + super.getSensorId();
        String fields = "turnsRation=" + turnsRatio.get();
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    public String getTurnsRation() {
        return turnsRatio.get();
    }

    public StringProperty turnsRatioProperty() {
        return turnsRatio;
    }

    public void setTurnsRation(String turnsRatio) {
        this.turnsRatio.set(turnsRatio);
    }

    @Override
    public String getMqttPayload() {
        return "{\"turnsRation\":\"" + turnsRatio.get() + "\"}";
    }

    @Override
    public String getMqttTopic() {
        return "client/transformer/" + super.getSensorId()+"/data";
    }


}
