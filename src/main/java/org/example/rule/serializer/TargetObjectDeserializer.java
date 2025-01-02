package org.example.rule.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.*;
import org.example.factory.DeviceFactory;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedHashMap;

public class TargetObjectDeserializer extends JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);


        if (node.isNumber()) {
            return node.numberValue();
        } else if (node.isTextual()) {
            return node.textValue();
        } else if (node.isBoolean()) {
            return node.booleanValue();
        }

        if (isGenerator(node)) {
            return getGenerator(node);
        } else if (isCurrentSensor(node)) {
            return getCurrentLineSensor(node);
        } else if (isTransformer(node)) {
            return getTransformer(node);
        } else if (isSwitchBoard(node)) {
            return getSwitchBoard(node);
        } else if (isMQTTPublisher(node)) {
            return getMqttPublisher(node);
        } else {
            return parser.getCodec().treeToValue(node, LinkedHashMap.class);
        }
    }




    private boolean isSwitchBoard(JsonNode node) {
        return node.has("sensorId") &&
                node.has("current");
    }

    private Object getSwitchBoard(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double current = getDoubleField(node, "current");
        return new SwitchBoard(sensorId, current);
    }

    private boolean isGenerator(JsonNode node) {
        return node.has("sensorId") &&
                node.has("voltage") &&
                node.has("current") &&
                node.has("isWorking");
    }

    private Object getGenerator(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double voltage = getDoubleField(node, "voltage");
        Double current = getDoubleField(node, "current");
        boolean isWorking = getBooleanField(node, "isWorking");
        return new Generator(sensorId, voltage, current, isWorking);
    }

    private boolean isCurrentSensor(JsonNode node) {
        return node.has("sensorId") &&
                node.has("voltage") &&
                node.has("current");
    }

    private @NotNull CurrentLineSensor getCurrentLineSensor(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double voltage = getDoubleField(node, "voltage");
        Double current = getDoubleField(node, "current");
        return new CurrentLineSensor(sensorId, voltage, current);
    }

    private boolean isTransformer(JsonNode node) {
        return node.has("sensorId") &&
                node.has("turnsRation");
    }


    private @NotNull Transformer getTransformer(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double turnsRatio = getDoubleField(node, "turnsRation");
        Transformer transformer = (Transformer) DeviceFactory.getDevice(DeviceType.TRANSPORTER).get();
        transformer.setSensorId(sensorId);
        transformer.setTurnsRation(turnsRatio);
        return transformer;
    }

    private boolean isMQTTPublisher(JsonNode node) {
        return node.has("clientId");
    }

    private @NotNull MQTTPublisher getMqttPublisher(JsonNode node) {
        return MQTTPublisherFactory.getPublisher(getStringField(node, "clientId"));
    }

    private @NotNull Double getDoubleField(JsonNode node, String doubleFieldName) {
        return Double.valueOf(getStringField(node, doubleFieldName));
    }

    private boolean getBooleanField(JsonNode node, String booleanFieldName) {
        return Boolean.parseBoolean(getStringField(node,booleanFieldName));
    }

    private String getStringField(JsonNode node, String sensorId) {
        return String.valueOf(node.get(sensorId)).replace("\"", "");
    }

}
