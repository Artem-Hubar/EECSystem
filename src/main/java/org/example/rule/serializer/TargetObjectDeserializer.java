package org.example.rule.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;
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

        if (isCurrentSensor(node)) {
            return getCurrentLineSensor(node);
        } else if (isTransformer(node)) {
            return getTransformer(node);
        } else if (isMQTTPublisher(node)) {
            return getMqttPublisher(node);
        } else {
            return parser.getCodec().treeToValue(node, LinkedHashMap.class);
        }
    }

    private boolean isMQTTPublisher(JsonNode node) {
        return node.has("clientId");
    }

    private @NotNull MQTTPublisher getMqttPublisher(JsonNode node) {
        // Это объект MQTTPublisher
        return MQTTPublisherFactory.getPublisher(getStringField(node, "clientId"));
    }

    private @NotNull Transformer getTransformer(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double turnsRatio = getDoubleField(node, "turnsRation");
        return new Transformer(sensorId, turnsRatio);
    }

    private @NotNull CurrentLineSensor getCurrentLineSensor(JsonNode node) {
        String sensorId = getStringField(node, "sensorId");
        Double voltage = getDoubleField(node, "voltage");
        Double current = getDoubleField(node, "current");
        return new CurrentLineSensor(sensorId, voltage, current);
    }

    private String getStringField(JsonNode node, String sensorId) {
        return String.valueOf(node.get(sensorId)).replace("\"", "");
    }

    private @NotNull Double getDoubleField(JsonNode node, String voltage) {
        return Double.valueOf(getStringField(node, voltage));
    }

    private boolean isTransformer(JsonNode node) {
        return node.has("sensorId") && node.has("turnsRation");
    }

    private boolean isCurrentSensor(JsonNode node) {
        return node.has("sensorId") && node.has("voltage") && node.has("current");
    }
}
