package org.example.client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.entity.Transformer;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;


public class MainSceneController {

    @FXML
    private Button mainButton;

    @FXML
    private TextField turnsRation;

    @FXML
    private String baseTurnsRation;

    @FXML
    private StringProperty textContent = new SimpleStringProperty("1");

    @FXML
    public void initialize() {
        turnsRation.setText("1");
    }
    @FXML
    private void buttonClicked() {
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("GUIClientPusher");
        Transformer transporter = new Transformer();
        transporter.setSensorId("device1");
        transporter.setTurnsRation(Double.parseDouble(turnsRation.getText()));
        mqttPublisher.writeData(transporter);
    }
}
