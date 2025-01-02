package org.example.client.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.client.service.DoubleStringConverter;
import org.example.entity.Device;

import java.lang.reflect.Field;

public class FullDeviceController {
    @FXML
    public Label deviceId;
    @FXML
    private VBox deviceDetailsBox;  // VBox для отображения полей устройства


    public void setDevice(Object device) {
        this.device = (Device) device;
        deviceId.setText(String.format("%s\nDeviceId: %s",  this.device.getDeviceType(),this.device.getSensorId()));
        populateDeviceDetails();
    }

    private Device device;

    public FullDeviceController() {

    }

    private void populateDeviceDetails() {
        try {
            if (device != null) {
                deviceDetailsBox.getChildren().clear();
                Device deviceEnitity = device;
                // Используем рефлексию для получения всех полей устройства
                Field[] fields = deviceEnitity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);  // Разрешаем доступ к приватным полям
                    try {
                        // Проверяем, является ли поле StringProperty или DoubleProperty
                        Property<?> property = (Property<?>) field.get(deviceEnitity);
                        if (property instanceof StringProperty) {
                            // Создаем текстовое поле для StringProperty
                            Label label = new Label(field.getName() + ": ");
                            TextField textField = new TextField();
                            // Биндим значение свойства к TextField
                            textField.textProperty().bindBidirectional(((StringProperty) property));
                            deviceDetailsBox.getChildren().addAll(label, textField);
                        } else if (property instanceof DoubleProperty) {
                            // Создаем текстовое поле для DoubleProperty
                            Label label = new Label(field.getName() + ": ");
                            TextField textField = new TextField();
                            textField.textProperty().bindBidirectional(((DoubleProperty) property), new DoubleStringConverter());
                            deviceDetailsBox.getChildren().addAll(label, textField);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
